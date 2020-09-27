package com.edencity.customer.service;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.edencity.customer.App;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.pojo.FuncResult;
import com.edencity.customer.util.HttpsClient;
import com.edencity.customer.util.JsonUtil;

public class WebService {
    public static final int ERROR_NEED_AUTH = 1;
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_OTHER = 3;
    public static final int ERROR_NOT_REGISTER = 4; //未注册

    private String serverUrl = "http://114.215.147.90:8086";
    //    private String serverUrl="http://192.168.31.4:8080";
    private HttpsClient httpsClient;


    /**
     * 发送手机验证码
     *
     * @param phone
     * @param type  regist,login,forget,modify中的一种
     * @return
     */
    public FuncResult<Object> sendVerifyCode(String phone, String type) {
        try {
            Map<String, String> params = new HashMap<>(1);
            params.put("message", String.format(Locale.CHINA, "{\"type\":\"%s\",\"phone\":\"%s\"}", type, phone));

            String result = httpsClient.post(serverUrl + "/verification/get", params);
            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }
            JSONObject retJo = JsonUtil.parseObject(result);
            if (retJo == null || JsonUtil.getString(retJo, "errcode") == null) {
                return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
            }
            String code = JsonUtil.getString(retJo, "errcode");
            if ("0".equals(code)) {
                return new FuncResult<>(0, null);
            } else if ("-7".equals(code)) {
                return new FuncResult<>(ERROR_NOT_REGISTER, null);
            } else {
                return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "errmsg"));
            }

        } catch (IOException e) {
            return new FuncResult<>(ERROR_NETWORK, "系统服务无法访问，请您检查网络后重试");
        } catch (Exception e) {
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    /**
     * 登录系统
     *
     * @param phone
     * @param verifyCode
     * @param authType   0=登录 1=注册
     * @return
     */
    public FuncResult<Customer> login(String phone, String verifyCode, int authType) {
        try {
            Map<String, String> params = new HashMap<>(1);
            params.put("message", String.format(Locale.CHINA, "{\"userType\":\"1\",\"loginType\":\"1\",\"phone\":\"%s\",\"verificationCode\":\"%s\"}", phone, verifyCode));

            String result = httpsClient.post(serverUrl + (authType == 0 ? "/login" : "/customer/register"), params);
            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }
            JSONObject retJo = JsonUtil.parseObject(result);
            if (retJo == null || JsonUtil.getString(retJo, "errcode") == null) {
                return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
            }

            if (!"0".equals(JsonUtil.getString(retJo, "errcode"))) {
                if ("-3".equals(JsonUtil.getString(retJo, "errcode"))) {
                    return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "errmsg", "您的登录信息已经过期，请重新登录"));
                }
                return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "errmsg"));
            }
            retJo = JsonUtil.getObject(retJo, "extraMap");
            if (retJo == null || !retJo.has("user")) {
                return new FuncResult<>(ERROR_OTHER, "无效的手机号，请重新输入");
            }

            Customer user = new Customer();
            user.phone = phone;
            user.ticket = JsonUtil.getString(retJo, "ticket");

            retJo = JsonUtil.getObject(retJo, "user");
            user.userName = JsonUtil.getString(retJo, "userName");
            user.userId = JsonUtil.getString(retJo, "userId");
            user.idCardNo = JsonUtil.getString(retJo, "idCardNo");
            user.nickName = JsonUtil.getString(retJo, "nickName");
            user.userType = JsonUtil.getString(retJo, "userType");
            user.accountStutas = JsonUtil.getString(retJo, "accountStatus");
            user.userStutas = JsonUtil.getString(retJo, "userStatus");

            JSONObject cusInfo = JsonUtil.getObject(retJo, "customer");
            if (cusInfo != null) {
                user.userAvatar = JsonUtil.getString(cusInfo, "photo");
            }
            return new FuncResult<>(0, null, user);
        } catch (IOException e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_NETWORK, "系统服务无法访问，请您检查网络后重试");
        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    /**
     * 同步用户信息，用户的预付款的卡号和余额
     *
     * @param user
     * @return
     */
    public FuncResult<Boolean> synCustomerInfo(Customer user) {
        try {
            Map<String, String> params = new HashMap<>(1);
            params.put("message", String.format(Locale.CHINA, "{\"userId\":\"%s\",\"ticket\":\"%s\"}", user.userId, user.ticket));

            String result = httpsClient.post(serverUrl + "/epay/synCustomer", params);
            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }
            JSONObject retJo = JsonUtil.parseObject(result);
            if (retJo == null || JsonUtil.getString(retJo, "errcode") == null) {
                return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
            }

            if (!"0".equals(JsonUtil.getString(retJo, "errcode"))) {
                if ("-3".equals(JsonUtil.getString(retJo, "errcode"))) {
                    return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "errmsg", "您的登录信息已经过期，请重新登录"));
                }
                return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "errmsg"));
            }
            retJo = JsonUtil.getObject(retJo, "extraMap");
            if (retJo == null || !retJo.has("customer")) {
                return new FuncResult<>(ERROR_OTHER, "无效的用户");
            }

            retJo = JsonUtil.getObject(retJo, "customer");

            user.phone = JsonUtil.getString(retJo, "phone");

            user.accountStutas = JsonUtil.getString(retJo, "accountStatus", user.accountStutas);
            user.userName = JsonUtil.getString(retJo, "userName");
            user.idCardNo = JsonUtil.getString(retJo, "idCardNo");
            user.nickName = JsonUtil.getString(retJo, "nickName");
            user.userAvatar = JsonUtil.getString(retJo, "photo");
            user.userStutas = JsonUtil.getString(retJo, "userStatus");

            user.userApprovalCode = JsonUtil.getString(retJo, "userApproval");
            user.userApprovalDesc = JsonUtil.getString(retJo, "userApprovalName");

            user.cardNo = JsonUtil.getString(retJo, "cardCode");
            user.cardId = JsonUtil.getString(retJo, "cardId");

            user.cardBalance = JsonUtil.getString(retJo, "sumBalance");

            return new FuncResult<>(0, null);
        } catch (IOException e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_NETWORK, "系统服务无法访问，请您检查网络后重试");
        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    /**
     * 更新用户头像
     *
     * @param imageFile
     * @return 新头像的地址
     */
    public FuncResult<String> updateAvatar(String userId, String ticket, File imageFile) {
        try {
            Map<String, String> params = new HashMap<>(1);
            params.put("message", String.format(Locale.CHINA, "{\"userId\":\"%s\",\"ticket\":\"%s\"}", userId, ticket));

            String result = httpsClient.upload(serverUrl + "/customer/edit", params, "image/jpeg", imageFile);
            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }
            JSONObject retJo = JsonUtil.parseObject(result);
            if (retJo == null || JsonUtil.getString(retJo, "errcode") == null) {
                return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
            }

            if (!"0".equals(JsonUtil.getString(retJo, "errcode"))) {
                if ("-3".equals(JsonUtil.getString(retJo, "errcode"))) {
                    return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "errmsg", "您的登录信息已经过期，请重新登录"));
                }
                return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "errmsg"));
            }
            retJo = JsonUtil.getObject(retJo, "extraMap");
            if (retJo == null || !retJo.has("customer")) {
                return new FuncResult<>(ERROR_OTHER, "更新头像出错，请重试");
            }

            retJo = JsonUtil.getObject(retJo, "customer");
            return new FuncResult<>(0, null, JsonUtil.getString(retJo, "photo"));
        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    /**
     * 更新用户认证信息
     *
     * @param userId
     * @param ticket
     * @param userName
     * @param idcardNo
     * @param frontImage
     * @param backImage
     * @return
     */
    public FuncResult<Boolean> updateUserApproval(String userId, String ticket, String userName, String idcardNo,
                                                  File frontImage, File backImage) {
        try {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ticket", ticket);
            hashMap.put("userId", userId);
            hashMap.put("userName", userName);
            hashMap.put("idCardNo", idcardNo);
            hashMap.put("app_id", "edencity_1");
            hashMap.put("nonce", "1");

            String result = httpsClient.upload(AppContant.BASE_URL + "/api/customer/certificate",
                    hashMap, "image/png", "frontImage", frontImage, "backImage", backImage);
            Log.e("result",result);
            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }

            Gson gson = new Gson();
            BaseResult baseResult = gson.fromJson(result, BaseResult.class);

            return new FuncResult<>(baseResult.getResult_code(),baseResult.getResult_msg());


        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }
 /**
     * 更新用户认证信息
     *
     * @param userId
     * @param ticket
     * @param userName
     * @param idcardNo
     * @param header
     * @return
     */
    public FuncResult<Boolean> updateUserApproval(String userId, String ticket, String userName, String idcardNo,
                                                  File header) {
        try {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ticket", ticket);
            hashMap.put("userId", userId);
            hashMap.put("name", userName);
            hashMap.put("idCardNo", idcardNo);
            hashMap.put("app_id", "edencity_1");
            hashMap.put("nonce", "1");

            String result = httpsClient.upload(AppContant.BASE_URL + "/api/customer/identity/auth",
                    hashMap, "image/png", "faceImg", header);

            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }

            Gson gson = new Gson();
            BaseResult baseResult = gson.fromJson(result, BaseResult.class);
            return new FuncResult<>(baseResult.getResult_code(),baseResult.getResult_msg());


        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, e.getMessage());
        }
    }


    public FuncResult<Boolean> userFeedBack(String content, String fileKey1, File file1,
                                            String fileKey2, File file2, String filekey3, File file3) {
        try {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ticket", App.getSp().getString("ticket"));
            hashMap.put("userId", App.getSp().getString("userId"));
            hashMap.put("content", content);
            hashMap.put("source", String.valueOf(1));
            hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");

            String result2;
            if (file1 != null && file1.exists()) {
                if (file2 != null && file2.exists()) {
                    if (file3 != null && file3.exists()) {
                        result2 = httpsClient.feedback(AppContant.BASE_URL + "/api/customer/feedback/create", hashMap,
                                fileKey1, file1, fileKey2, file2, filekey3, file3);

                        /*if (result == null) {
                            return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                        }

                        JSONObject retJo = JsonUtil.parseObject(result);
                        if (retJo == null || JsonUtil.getString(retJo, "result_code") == null) {
                            return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
                        }

                        if (!"0".equals(JsonUtil.getString(retJo, "result_code"))) {
                            if ("-3".equals(JsonUtil.getString(retJo, "result_code"))) {
                                return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "result_msg", "您的登录信息已经过期，请重新登录"));
                            }
                            return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "result_msg"));
                        }
                        return new FuncResult<>(0, null);*/
                    } else {
                        result2 = httpsClient.feedback(AppContant.BASE_URL + "/api/customer/feedback/create", hashMap,
                                fileKey1, file1, fileKey2, file2, null, null);
                      /*  if (result == null) {
                            return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                        }
                        Log.e("http", result);
                        JSONObject retJo = JsonUtil.parseObject(result);
                        if (retJo == null || JsonUtil.getString(retJo, "result_code") == null) {
                            return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
                        }

                        if (!"0".equals(JsonUtil.getString(retJo, "result_code"))) {
                            if ("-3".equals(JsonUtil.getString(retJo, "result_code"))) {
                                return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "result_msg", "您的登录信息已经过期，请重新登录"));
                            }
                            return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "result_msg"));
                        }
                    }
                    return new FuncResult<>(0, null);*/
                    }
                } else {
                    result2 = httpsClient.feedback(AppContant.BASE_URL + "/api/customer/feedback/create", hashMap,
                            fileKey1, file1, null, null, null, null);
                /*    Log.e("http", result);
                    if (result == null) {
                        return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                    }
                    JSONObject retJo = JsonUtil.parseObject(result);
                    if (retJo == null || JsonUtil.getString(retJo, "result_code") == null) {
                        return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
                    }

                    if (!"0".equals(JsonUtil.getString(retJo, "result_code"))) {
                        if ("-3".equals(JsonUtil.getString(retJo, "result_code"))) {
                            return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "result_msg", "您的登录信息已经过期，请重新登录"));
                        }
                        return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "result_msg"));
                    }
                }
                return new FuncResult<>(0, null);*/
                }
            } else {
                result2 = httpsClient.feedback(AppContant.BASE_URL + "/api/customer/feedback/create", hashMap,
                        null, null, null, null, null, null);

               /* if (result == null) {
                    return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject retJo = JsonUtil.parseObject(result);
                if (retJo == null || JsonUtil.getString(retJo, "result_code") == null) {
                    return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
                }

                if (!"0".equals(JsonUtil.getString(retJo, "result_code"))) {
                    if ("-3".equals(JsonUtil.getString(retJo, "result_code"))) {
                        return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "result_msg", "您的登录信息已经过期，请重新登录"));
                    }
                    return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "result_msg"));
                }
                return new FuncResult<>(0, null);*/
            }

            Gson gson = new Gson();
            BaseResult baseResult = gson.fromJson(result2, BaseResult.class);
            Log.e("result",baseResult.toString());
            return new FuncResult<>(baseResult.getResult_code(),baseResult.getResult_msg());
        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    //更新用户信息
    public FuncResult<Boolean> updateUserMsg(String fileKey1, File file1,String nickName) {
        try {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ticket",App.getSp().getString("ticket"));
            hashMap.put("userId", App.getSp().getString("userId"));
            hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");

            if (file1 != null && file1.exists()) {
                if (nickName != null && !nickName.equals("")) {
                        String result = httpsClient.updateUserMsg(AppContant.BASE_URL + "/api/customer/edit", hashMap,
                                fileKey1, file1,nickName);
                        if (result == null) {
                            return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                        }
                        Log.e("http", result);
                    JSONObject jsonObject = new JSONObject(result);
                    return new FuncResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));

                } else {
                    String result = httpsClient.updateUserMsg(AppContant.BASE_URL + "/api/customer/edit", hashMap,
                            fileKey1, file1, null);
                    if (result == null) {
                        return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                    }
                    Log.e("http", result);
                    JSONObject jsonObject = new JSONObject(result);
                    return new FuncResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));
                }
            } else {
                String result = httpsClient.updateUserMsg(AppContant.BASE_URL + "/api/customer/edit", hashMap,
                        null, null, nickName);
                if (result == null) {
                    return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
                }
                Log.e("http", result);
                JSONObject jsonObject = new JSONObject(result);
                return new FuncResult<>(jsonObject.getInt("result_code"), jsonObject.getString("result_msg"));
            }

        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }


    /**
     * 微信支付请求
     *
     * @param userId
     * @param ticket
     * @param cardNo
     * @param totalAmount 单位为分
     * @return
     */
    public FuncResult<JSONObject> onWeixinPayRequest(String userId, String ticket, String cardNo, int totalAmount) {
        try {
            Map<String, String> params = new HashMap<>(1);
            params.put("message", String.format(Locale.CHINA, "{\"userId\":\"%s\",\"ticket\":\"%s\",\"cardId\":\"%s\",\"totalAmount\":\"%d\"}", userId, ticket, cardNo, totalAmount));

            String result = httpsClient.post(serverUrl + "/wechatpay/unifiedorder", params);
            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }
            JSONObject retJo = JsonUtil.parseObject(result);
            if (retJo == null || JsonUtil.getString(retJo, "errcode") == null) {
                return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
            }

            if (!"0".equals(JsonUtil.getString(retJo, "errcode"))) {
                if ("-3".equals(JsonUtil.getString(retJo, "errcode"))) {

                    return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "errmsg", "您的登录信息已经过期，请重新登录"));
                }
                return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "errmsg"));
            }
            retJo = JsonUtil.getObject(retJo, "extraMap");
            if (retJo == null || !retJo.has("prepay_id")) {
                return new FuncResult<>(ERROR_OTHER, "支付请求出错，请重试");
            }

            return new FuncResult<>(0, null, retJo);
        } catch (IOException e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_NETWORK, "系统服务无法访问，请您检查网络后重试");
        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }

    /**
     * 支付宝认证请求
     *
     * @param userId
     * @param ticket
     * @param cardNo
     * @param totalAmount
     * @return
     */
    public FuncResult<JSONObject> onAliPayRequest(String userId, String ticket, String cardNo, BigDecimal totalAmount) {
        try {
            Map<String, String> params = new HashMap<>(1);
            params.put("message", String.format(Locale.CHINA, "{\"userId\":\"%s\",\"ticket\":\"%s\",\"cardId\":\"%s\",\"totalAmount\":\"%.2f\"}", userId, ticket, cardNo, totalAmount));

            String result = httpsClient.post(serverUrl + "/epay/alipay/gen", params);
            if (result == null) {
                return new FuncResult<>(ERROR_OTHER, "系统服务无法访问，请重试");
            }
            JSONObject retJo = JsonUtil.parseObject(result);
            if (retJo == null || JsonUtil.getString(retJo, "errcode") == null) {
                return new FuncResult<>(ERROR_OTHER, "服务返回的数据无效，请重试");
            }

            if (!"0".equals(JsonUtil.getString(retJo, "errcode"))) {
                if ("-3".equals(JsonUtil.getString(retJo, "errcode"))) {
                    return new FuncResult<>(ERROR_NEED_AUTH, JsonUtil.getString(retJo, "errmsg", "您的登录信息已经过期，请重新登录"));
                }
                return new FuncResult<>(ERROR_OTHER, JsonUtil.getString(retJo, "errmsg"));
            }
            retJo = JsonUtil.getObject(retJo, "extraMap");
            if (retJo == null || !retJo.has("prePayMessage")) {
                return new FuncResult<>(ERROR_OTHER, "支付请求出错，请重试");
            }

            return new FuncResult<>(0, null, retJo);
        } catch (IOException e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_NETWORK, "系统服务无法访问，请您检查网络后重试");
        } catch (Exception e) {
            Log.e("edencity", e.getMessage(), e);
            return new FuncResult<>(ERROR_OTHER, "系统处理出现错误，请重试");
        }
    }



    public void setHttpsClient(HttpsClient httpsClient) {
        this.httpsClient = httpsClient;
    }
}

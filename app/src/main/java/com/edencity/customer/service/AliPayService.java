package com.edencity.customer.service;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edencity.customer.App;
import com.edencity.customer.util.DateUtil;

public class AliPayService {

    public static AliPayService instance;

    public static AliPayService getInstance(){
        if (instance == null){
            instance = new AliPayService();
        }
        return instance;
    }

    /**
     * 请求支付宝支付
     * @param activity
     * @param callback
     * @return
     */
    public String sendPayRequest(final Activity activity,final String payRequestParams, final Handler.Callback callback){
        try {

//            Map<String, String> params = buildOrderParamMap(totalAmount,outTradeNo,product);
//            String orderParam = buildOrderParam(params);
//            final String orderInfo = orderParam + "&" + sign;
            App.execute(()->{
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(payRequestParams, true);
                Message msg=Message.obtain();
                if ("9000".equals(result.get("resultStatus"))){ //支付成功
                    msg.what=0;
                }else {
                    //支付失败
                    msg.what=1;
                    msg.obj=result.get("memo");
                }

                callback.handleMessage(msg);
            });
            return null;
        }catch (Exception e){
            return "请求支付宝支付失败，请重试";
        }
    }


    /**
     * 构造支付订单参数列表
     * @return
     */
    public Map<String, String> buildOrderParamMap(int totalAmount,String outTradeNo,String product) {
        Map<String, String> keyValues = new HashMap<String, String>();

        //应用ID
        String appId = "wxf6ed4c8b0a27e560";
        keyValues.put("app_id", appId);

        keyValues.put("biz_content", "{\"timeout_express\":\"5m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""+totalAmount+"\",\"subject\":\"1\",\"body\":\""+product+"\",\"out_trade_no\":\"" + outTradeNo +  "\"}");

        keyValues.put("charset", "utf-8");

        keyValues.put("method", "alipay.trade.app.pay");

        boolean rsa2 = true;
        keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

        keyValues.put("timestamp", DateUtil.getLooseDateTime(new Date()));

        keyValues.put("version", "1.0");
        return keyValues;
    }


    /**
     * 构造支付订单参数信息
     *
     * @param map
     * 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }
}

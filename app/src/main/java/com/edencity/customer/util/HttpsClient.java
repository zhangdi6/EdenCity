package com.edencity.customer.util;


import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 紫钢 on 2016/10/13.
 */
public class HttpsClient {

    private OkHttpClient httpClient;
    //传输超时时间，默认30秒
    private int connectTimeout = 15;
    private int readTimeout = 30;
    private int writeTimeout = 30;

    private String lastError;

    //初始化为http客户端
    public void initHttpClient() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();

    }

    //初始化成默认的接受可信证书的客户端
    public void initHttpsClient() {
        try {
            final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain,
                                               final String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(final X509Certificate[] chain,
                                               final String authType) throws CertificateException {
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("TLS");

            sslContext.init(null, certs, new SecureRandom());
            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String url) throws Exception {
        Request.Builder bd = new Request.Builder()
                .url(url)
                .get();
        Response response = null;
        try {
            response = httpClient.newCall(bd.build()).execute();
            String result = response.body().string();
            return result;
        } finally {
            if (response != null)
                response.body().close();
        }
    }

    public String get(String url, Map<String, String> params) throws Exception {
        Request.Builder bd = new Request.Builder()
                .url(genUrl(url, params))
                .get();

        Response response = null;
        try {
            response = httpClient.newCall(bd.build()).execute();
            String result = response.body().string();
            return result;
        } finally {
            if (response != null)
                response.body().close();
        }

    }

    public void get(String url, Callback cb) {
        Request.Builder bd = new Request.Builder()
                .url(url)
                .get();
        httpClient.newCall(bd.build()).enqueue(cb);
    }

    public void get(String url, Map<String, String> params, Callback cb) {
        Request.Builder bd = new Request.Builder()
                .url(genUrl(url, params))
                .get();
        httpClient.newCall(bd.build()).enqueue(cb);
    }


    public String post(String url, Map<String, String> params) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            if (key == null || params.get(key) == null)
                continue;
            //追加表单信息
            builder.add(key, params.get(key));
        }

        Request.Builder bd = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .post(builder.build());
        Response response = null;
        try {
            response = httpClient.newCall(bd.build()).execute();
            String result = response.body().string();
            return result;
        } finally {
            if (response != null)
                response.body().close();
        }
    }

    /**
     * 务必关闭ResponseBody
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public ResponseBody postWithRetBody(String url, Map<String, String> params) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            //追加表单信息
            builder.add(key, params.get(key));
        }

        Request.Builder bd = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .post(builder.build());
        Response response = null;
        response = httpClient.newCall(bd.build()).execute();
        return response.body();
    }

    public String postJson(String url, String data) throws Exception {
        Request.Builder bd = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json;charset=UTF-8")
                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data));


        Response response = null;
        try {
            response = httpClient.newCall(bd.build()).execute();
            String result = response.body().string();
            return result;
        } finally {
            if (response != null)
                response.body().close();
        }
    }

    public boolean download(String url, File desFile) {
        Request.Builder bd = new Request.Builder()
                .url(url)
                .get();
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            if (!desFile.exists()) {
                desFile.createNewFile();
            }
            fos = new FileOutputStream(desFile);
            ResponseBody body = httpClient.newCall(bd.build()).execute().body();
            is = body.byteStream();
            byte[] buffer = new byte[100000];
            while (true) {
                int len = is.read(buffer);
                if (len <= 0) break;
                fos.write(buffer, 0, len);
            }

            return true;
        } catch (IOException e) {
            lastError = e.getMessage();
        } finally {
            try {
                if (fos != null) fos.close();
                if (is != null) is.close();
            } catch (IOException ignored) {

            }

        }
        return false;
    }

    public String upload(String url, Map param, String fileType, File file1) {
        try {
            MultipartBody.Builder mb = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            if (param != null && param.size() > 0) {
                Iterator iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    mb.addFormDataPart(key, val);
                }
            }
            if (file1 != null && file1.exists()) {
                mb.addFormDataPart("file", file1.getName(), RequestBody.create(MediaType.parse(fileType), file1));
            }

            Request request = new Request.Builder()
                    .url(url)
                    .post(mb.build())
                    .build();

            Response response = httpClient.newCall(request).execute();
            return response.isSuccessful() ? response.body().string() : null;

        } catch (MalformedURLException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("httpsclient", e.getMessage(), e);
        }
        return null;
    }



    public String feedback(String url, Map param, String fileType1, File evidence1, String fileType2, File evidence2, String fileType3, File evidence3) {
        try {
            MultipartBody.Builder mb = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            HashMap<String, Object> hashMap = new HashMap<>();
            if (param != null && param.size() > 0) {
                Iterator iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    mb.addFormDataPart(key, val);
                    hashMap.put(key, val);
                }
            }
            if (evidence1 != null && evidence1.exists()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(fileType1, evidence1.getName(), RequestBody.create(MediaType.parse("image/png"), evidence1));
                /* hashMap.put(fileType1,part);*/
                mb.addFormDataPart(fileType1, evidence1.getName(), RequestBody.create(MediaType.parse("image/png"), evidence1));
            }
            if (evidence2 != null && evidence2.exists()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(fileType2, evidence2.getName(), RequestBody.create(MediaType.parse("image/png"), evidence2));
                /* hashMap.put(fileType2, part);*/
                mb.addFormDataPart(fileType2, evidence2.getName(), RequestBody.create(MediaType.parse("image/png"), evidence2));
            }
            if (evidence3 != null && evidence3.exists()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(fileType3, evidence3.getName(), RequestBody.create(MediaType.parse("image/png"), evidence3));
                /*hashMap.put(fileType3, part);*/
                mb.addFormDataPart(fileType3, evidence3.getName(), RequestBody.create(MediaType.parse("image/png"), evidence3));
            }
            //将除sign外所有参数an字母顺序排序，末尾加上key
            String sign = ParamsUtils.getSign(hashMap);
            try {
                //加密
                hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            } catch (Exception e) {
                e.printStackTrace();
            }

            mb.addFormDataPart("sign", SHA1Utils.strToSHA1(sign));
            Request request = new Request.Builder()
                    .url(url)
                    .post(mb.build())
                    .build();

            Response response = httpClient.newCall(request).execute();
            /*Log.d("msg",response.body().string());*/
            return  response.body().string() ;

        } catch (MalformedURLException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("httpsclient", e.getMessage(), e);
        }
        return null;
    }

    public String updateUserMsg(String url, Map param, String photoName, File photo,String nickName) {
        try {
            MultipartBody.Builder mb = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            HashMap<String, Object> hashMap = new HashMap<>();
            if (param != null && param.size() > 0) {
                Iterator iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    mb.addFormDataPart(key, val);
                    hashMap.put(key, val);
                }
            }
            if (nickName!=null && !nickName.equals("")){
                mb.addFormDataPart("nickName", nickName);
                hashMap.put("nickName", nickName);
            }
            if (photo != null && photo.exists()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(photoName, photo.getName(), RequestBody.create(MediaType.parse("image/png"), photo));
                /* hashMap.put(fileType1,part);*/
                mb.addFormDataPart(photoName, photo.getName(), RequestBody.create(MediaType.parse("image/png"), photo));
            }

            //将除sign外所有参数an字母顺序排序，末尾加上key
            String sign = ParamsUtils.getSign(hashMap);
            try {
                //加密
                hashMap.put("sign", SHA1Utils.strToSHA1(sign));
                Log.e("http", sign);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mb.addFormDataPart("sign", SHA1Utils.strToSHA1(sign));
            Request request = new Request.Builder()
                    .url(url)
                    .post(mb.build())
                    .build();

            Response response = httpClient.newCall(request).execute();
            return response.isSuccessful() ? response.body().string() : null;

        } catch (MalformedURLException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("httpsclient", e.getMessage(), e);
        }
        return null;
    }

    public String upload(String url, Map param, String fileType, String fileField1, File file1, String fileField2, File file2) {
        try {
            MultipartBody.Builder mb = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            HashMap<String, Object> hashMap = new HashMap<>();
            if (param != null && param.size() > 0) {
                Iterator iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    mb.addFormDataPart(key, val);
                    hashMap.put(key, val);
                }
            }
            if (file1 != null && file1.exists()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(fileField1, file1.getName(), RequestBody.create(MediaType.parse(fileType), file1));
                /*hashMap.put(fileField1, part);*/
                mb.addFormDataPart(fileField1, file1.getName(), RequestBody.create(MediaType.parse(fileType), file1));
            }
            if (file2 != null && file2.exists()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(fileField2, file2.getName(), RequestBody.create(MediaType.parse(fileType), file2));
                /*hashMap.put(fileField2, part);*/
                mb.addFormDataPart(fileField2, file2.getName(), RequestBody.create(MediaType.parse(fileType), file2));
            }
            //将除sign外所有参数an字母顺序排序，末尾加上key
            String sign = ParamsUtils.getSign(hashMap);

            mb.addFormDataPart("sign", SHA1Utils.strToSHA1(sign));
            Request request = new Request.Builder()
                    .url(url)
                    .post(mb.build())
                    .build();

            Response response = httpClient.newCall(request).execute();

            return response.body().string();

        } catch (MalformedURLException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("httpsclient", e.getMessage(), e);
        }
        return null;
    }

    public String upload(String url, Map param, String fileType, String fileField1, File file1) {
        try {
            MultipartBody.Builder mb = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            HashMap<String, Object> hashMap = new HashMap<>();
            if (param != null && param.size() > 0) {
                Iterator iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    mb.addFormDataPart(key, val);
                    hashMap.put(key, val);
                }
            }
            if (file1 != null && file1.exists()) {
                mb.addFormDataPart(fileField1, file1.getName(), RequestBody.create(MediaType.parse(fileType), file1));
            }

            Log.e("httpsclient",hashMap.toString());
            //将除sign外所有参数an字母顺序排序，末尾加上key
            String sign = ParamsUtils.getSign(hashMap);

            mb.addFormDataPart("sign", SHA1Utils.strToSHA1(sign));
            Request request = new Request.Builder()
                    .url(url)
                    .post(mb.build())
                    .build();

            Response response = httpClient.newCall(request).execute();
            return response.body().string();

        } catch (MalformedURLException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("httpsclient", e.getMessage(), e);
        } catch (Exception e) {
            Log.e("httpsclient", e.getMessage(), e);
        }
        return null;
    }

    private String genUrl(String url, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!url.contains("?"))
            sb.append("?");
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    /**
     * 设置传输超时时间
     *
     * @param readTimeout 传输时长，默认30秒
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getLastError() {
        return lastError;
    }
}

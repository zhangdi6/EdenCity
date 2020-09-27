package com.edencity.customer.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.edencity.customer.App;
import com.edencity.customer.data.AppContant;

// Created by Ardy on 2020/1/6.
public class ParamsUtils {


    public static int sign = 0;

    public static String getSign(HashMap<String,Object>map){

        TreeSet<String> objects1 = new TreeSet<>();
        for(Map.Entry<String, Object> entry: map.entrySet()) {
            objects1.add(entry.getKey()+"="+entry.getValue());
        }
        String s = objects1.toString();
        String s1 = s.replaceAll(", ","&");
        String substring = s1.substring(1, s1.length() - 1);
        //将所有参数排序后按 "aa=bbb&cc=nnn&dd=ppp&key=edencity_2"的格式拼接成sign参数
        String s2 = substring + "&key="+ AppContant.APPKEY;
        return s2;
    }

    //无参数的
    //此方法用来免去公共参数的添加和麻烦的加密转换
    public static HashMap getParamsMap(){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("userId", App.getSp().getString("userId"));
        hashMap.put("ticket", App.getSp().getString("ticket"));
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
      return hashMap;
    }
    //一个参数的
    public static HashMap getParamsMap(String paramKey , Object paramValue){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("userId", App.getSp().getString("userId"));
        hashMap.put("ticket", App.getSp().getString("ticket"));
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        hashMap.put(paramKey, paramValue);
      return hashMap;
    }
    //两个参数的
    public static HashMap getParamsMap(String paramKey1 , Object paramValue1 , String paramKey2 , Object paramValue2 ){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("userId", App.getSp().getString("userId"));
        hashMap.put("ticket", App.getSp().getString("ticket"));
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        hashMap.put(paramKey1, paramValue1);
        hashMap.put(paramKey2, paramValue2);
      return hashMap;
    }
    //三个参数的
    public static HashMap getParamsMap(String paramKey1 , Object paramValue1 ,
                                        String paramKey2 , Object paramValue2,String paramKey3 , Object paramValue3 ){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("userId", App.getSp().getString("userId"));
        hashMap.put("ticket",App.getSp().getString("ticket"));
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        hashMap.put(paramKey1, paramValue1);
        hashMap.put(paramKey2, paramValue2);
        hashMap.put(paramKey3, paramValue3);
      return hashMap;
    }
    public static HashMap getParamsMapWithNoId(String paramKey1, Object paramValue1) {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("app_id", AppContant.APPID);
            if (paramKey1!=null && !paramKey1.equals("")){
                hashMap.put(paramKey1, paramValue1);
            }
            hashMap.put("nonce", "1");
            return hashMap;
    }

    public static HashMap getParamsMapWithNoId2(String paramKey1, Object paramValue1) {

        if (sign == 1){

        }else{
            sign ++;
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("app_id", AppContant.APPID);
            hashMap.put(paramKey1, paramValue1);
            hashMap.put("nonce", "1");

            return hashMap;
        }
        return new HashMap();
    }
}

package com.edencity.customer.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by 紫钢 on 2016/1/25.
 */
public class JsonUtil {
    public static final int INVALID_INT = 0;
    public static JSONObject parseObject(String str){
        if (str==null || str.length()==0 || str.charAt(0)!='{'){
            return null;
        }
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            //try again
            str=str.replace('\n',' ');
            try {
                return new JSONObject(str);
            } catch (JSONException ignored) {
            }
            Log.e("json",e.toString());
        }
        return null;
    }

    public static JSONArray parseArray(String str){
        if (str==null || str.length()==0 || str.charAt(0)!='['){
            return null;
        }
        try {
            return new JSONArray(str);
        } catch (JSONException e) {
            //try again
            str=str.replace('\n',' ');
            try {
                return new JSONArray(str);
            } catch (JSONException ignored) {
            }
            Log.e("json",e.toString());
        }
        return null;
    }

    public static int getInt(JSONObject obj,String key){
        if (obj==null || key==null) return INVALID_INT;
        try {
            if (obj.has(key)  && !obj.isNull(key)){
                return obj.getInt(key);
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return INVALID_INT;
    }

    public static long getLong(JSONObject obj,String key){
        if (obj==null || key==null) return INVALID_INT;
        try {
            if (obj.has(key)  && !obj.isNull(key)){
                return obj.getLong(key);
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return 0;
    }

    public static int getInt(JSONObject obj,String key,int defaultValue){
        if (obj==null || key==null) return defaultValue;
        try {
            if (obj.has(key)  && !obj.isNull(key)){
                return obj.getInt(key);
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return defaultValue;
    }

    public static double getDouble(JSONObject obj,String key){
        if (obj==null || key==null) return 0;
        try {
            if (obj.has(key)  && !obj.isNull(key)){
                return obj.getDouble(key);
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return 0;
    }

    public static String getString(JSONObject obj,String key){
        if (obj==null || key==null) return null;
        try {
            if (obj.has(key) && !obj.isNull(key)){
                String v=obj.getString(key);
                if (v!=null && v.length()>0)
                    return v;
            }
        } catch (Exception e) {
            Log.e("json",e.toString());
        }
        return null;
    }
    public static String getString(JSONObject obj,String key,String defaultValue){
        if (obj==null || key==null) return defaultValue;
        try {
            if (obj.has(key)  && !obj.isNull(key)){
                String v=obj.getString(key);
                if (v!=null && v.length()>0)
                    return v;
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return defaultValue;
    }

    public static JSONArray getArray(JSONObject obj,String key){
        if (obj==null || key==null) return null;
        try {
            if (obj.has(key)  && !obj.isNull(key)){
                return obj.getJSONArray(key);
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return null;
    }

    public static JSONObject getObject(JSONObject obj,String key){
        if (obj==null || key==null) return null;
        try {
            if (obj.has(key)  && !obj.isNull(key)){
                return obj.getJSONObject(key);
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return null;
    }

    public static JSONObject getObject(JSONArray obj,int index){
        if (obj==null || index>=obj.length())
            return null;
        try {
            return obj.getJSONObject(index);
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return null;
    }

    /**
     * 网络中通过字符串传递浮点数
     * @param obj
     * @param key
     * @param defaultValue
     * @return
     */
    public static BigDecimal getDecimal(JSONObject obj, String key, BigDecimal defaultValue){
        try {
            if (obj!=null && obj.has(key)  && !obj.isNull(key)){
                Object value=obj.get(key);
                if(value instanceof String){
                    return new BigDecimal((String)value);
                }else if(value instanceof Number){
                    return new BigDecimal(((Number)value).toString());
                }
            }
        } catch (JSONException e) {
            Log.e("json",e.toString());
        }
        return defaultValue;
    }

    public static String escapeString(String value){
        if (value==null || value.length()<1)
            return null;
        StringBuilder sb=new StringBuilder();
        int length = value.length();
        String replacement=null;
        for (int i = 0; i < length; i++) {
            replacement=null;
            char c = value.charAt(i);
            if (c < 128) {
                switch (c){
                    case '\t': replacement="\\t";break;
                    case '\b': replacement="\\b";break;
                    case '\n': replacement="\\n";break;
                    case '\r': replacement="\\r";break;
                    case '\f': replacement="\\f";break;
                    case '"': replacement="\\\"";break;
                    case '\\': replacement="\\\\";break;
                }
            } else if (c == '\u2028') {
                replacement = "\\u2028";
            } else if (c == '\u2029') {
                replacement = "\\u2029";
            } else {

            }
            if (replacement == null) {
                sb.append(c);
            } else {
                sb.append(replacement);
            }
        }
        return sb.toString();
    }

    public static String deleteString(String value){
        if (value==null || value.length()<1)
            return null;
        value=value.replace("\"", "");
        value=value.replace("\n", "");
        value=value.replace("\r", "");
        return value;
    }

}

package com.edencity.customer.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

/**
 * Created by zigang on 2016/6/15.
 */
public class StringUtil {
    public static int parseInt(String value){
        try{
            if (value==null || value.length()==0)
                return 0;
            return Integer.parseInt(value);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public static long parseLong(String value,long defaultValue){
        try{
            if (value==null || value.length()==0)
                return defaultValue;
            return Long.parseLong(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static double parseDouble(String value,double defalutValue){
        try{
            if (value==null || value.length()==0)
                return defalutValue;
            return Double.parseDouble(value);
        }catch (NumberFormatException e){
            return defalutValue;
        }
    }

    public static int parseInt(String value,int defaultValue){
        try{
            if (value==null || value.length()==0)
                return defaultValue;
            return Integer.parseInt(value);
        }catch (NumberFormatException e){
            return defaultValue;
        }
    }

    public static BigDecimal parseDecimal(String value,BigDecimal defaultValue){
        try{
            if (value==null || value.length()==0)
                return defaultValue;
            return new BigDecimal(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    public static String formatDecimal(BigDecimal value){
        try{
            if (value==null)
                return "0.00";
            return String.format(Locale.CHINA,"%.2f",value);
        }catch (Exception e){
            return "0.00";
        }
    }

    /**
     * 添加 - 号
     * @param value
     * @param addMinus
     * @return
     */
    public static String formatDecimal(BigDecimal value,boolean addMinus){
        try{
            if (value==null)
                return "0.00";
            if(addMinus){
                return String.format(value.doubleValue()>0?"-%.2f":"%.2f",value);
            }else{
                return String.format("%.2f",value);
            }
        }catch (Exception e){
            return "0.00";
        }
    }

    /**
     * 获取到bigdecimal的整数部分
     * @param bd
     * @param multiply
     * @return
     */
    public static String getIntStringFromDecimal(BigDecimal bd,int multiply) {
        if (multiply == 1) {
            return String.format(Locale.CHINA,"%.0f", bd.setScale(0, BigDecimal.ROUND_HALF_UP));
        } else {
            return String.format(Locale.CHINA,"%.0f", bd.multiply(new BigDecimal(multiply)).setScale(0, BigDecimal.ROUND_HALF_UP));
        }
    }

    public static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }

    public static String delReturnChar(String req){
        req=req.replace('\r',' ');
        req=req.replace('\n',' ');
        return req;
    }

    /**
     *
     * @param req
     * @return
     */
    public static String wrapString(String req,int charsMaxRow){
        if (req==null || req.length()<=charsMaxRow)
            return req;
        StringBuilder sb=new StringBuilder(req.substring(0,charsMaxRow));
        int curPos=charsMaxRow;
        while (true){
            sb.append("\n");
            if (req.length()>curPos+charsMaxRow){
                sb.append(req.substring(curPos,curPos+charsMaxRow));
                curPos+=charsMaxRow;
            }else {
                sb.append(req.substring(curPos));
                break;
            }
        }
        return sb.toString();
    }

    public static String genGetUrl(String url,Map<String,String> map){
        if (url==null || map==null || map.size()==0)
            return url;

        StringBuilder sb=new StringBuilder();
        sb.append(url);
        if (!url.contains("?"))
            sb.append("?");
        boolean first=true;
        for(Map.Entry<String,String> entry:map.entrySet()){
            if (first){
                first=false;
            }else {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    public static String subString(String string,int maxLen){
        if (string==null)
            return null;
        if (string.length()>maxLen){
            return string.substring(0,maxLen-1)+"...";
        }
        return string;
    }
    public static String genUrl(String url,String param){
        if (url==null) return null;
        return url.contains("?")?url+"&"+param:url+"?"+param;
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(value.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查对象是否为数字型字符串,包含负数开头的。
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        char[] chars = obj.toString().toCharArray();
        int length = chars.length;
        if(length < 1)
            return false;

        int i = 0;
        if(length > 1 && chars[0] == '-')
            i = 1;

        for (; i < length; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 把通用字符编码的字符串转化为汉字编码。
     */
    public static String unicodeToChinese(String unicode) {
        StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); i++) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }

    /**
     * 过滤不可见字符
     */
    public static String stripNonValidXMLCharacters(String input) {
        if (input == null || ("".equals(input)))
            return "";
        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < input.length(); i++) {
            current = input.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    /**
     * 清除磁卡的前导和后缀
     * @param data 数据
     * @param channel 1:track1 2:track2 3:track3
     * @return
     */
    public static String trimMsrData(String data,int channel){
        if (data==null || data.length()<1)
            return "";
        int index=data.indexOf(';');
        if (index>=0){
            data=data.substring(index+1);
        }
        index=data.indexOf('?');
        if (index>0){
            data=data.substring(0,index);
        }
        return data;
    }


    /**
     * 去除二维码中的特殊字符
     * @param data
     * @return
     */
    public static String trimQrcode(String data){
        if (data==null || data.length()<1)
            return "";
        data=data.replace("\r","");
        data=data.replace("\n","");
        return data.trim();
    }

    public static String addWhiteSpaceAfter(String obj,int len,int spaceCount){
        if (obj.length()>=len || spaceCount<1)
            return obj;
        StringBuilder sb=new StringBuilder(obj);
        int len2=(len-obj.length())*spaceCount;
        for (int i=0;i<=len2;i++){
            sb.append(" ");
        }
        return sb.toString();
    }

}

package com.edencity.customer.data;

public class AppContant {


    //线上线下开关
    //0 线下  1 线上
    public static int TYPE = 1;

    public static final String DEBUG= "http://pay.edencity.cn";
    public static final String DEBUG2= "http://192.168.31.191:8082";
    public static final String RELEASE= "http://pay.edencitybrand.com";

    public static final String WebUrl1= "http://121.42.184.173:8093/";
    public static final String WebUrl2= "http://h5.edencitybrand.com/";
    public static final String APKURL= "https://edencity-app.oss-cn-beijing.aliyuncs.com/apk/customer.apk";
    public static final String WebUrl= TYPE== 0 ? WebUrl1:WebUrl2;
    public static  String BASE_URL = TYPE== 0 ? DEBUG2:RELEASE;

    //微信
    public static final String WXAPPID = "wxf6ed4c8b0a27e560";
    public static final String APPSERCRET = "11b9bf6d8686c53dd3c1152e5e1630c1";
   //友盟
    public static final String YOUMENG_APPKEY = "5e53854d0cafb288db000017";
    public static final String YOUMENG_MSG_SERCRET = "58e1f653e55f65ec8196512e71b8b771";
    public static final String YOUMENG_APP_MASTER_SERCRET = "usnkzkijsctitnou5tf8whu6orva5mgz";
    //小米
    public static final String XIAOMI_APPID = "2882303761518347066";
    public static final String XIAOMI_KEY = "5471834757066";
    public static final String XIAOMI_SECRET = "GqGwgK8gZDs7uqa8kQmfNw==";

    public static final String APPID = "edencity_1";
    public static final String APPKEY = "edencity_2";

    //腾讯bugly
    public static final String BUGLY_APPID = "db6324f09e";
    public static final String BUGLY_APPKEY = "18139425-ff04-4e74-b04b-403d621e4956";
}

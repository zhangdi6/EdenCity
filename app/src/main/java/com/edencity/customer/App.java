package com.edencity.customer;

import android.content.Context;

import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

/*import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;*/
import com.edencity.customer.base.MyRequestInterceptor;
import com.edencity.customer.base.MyResponseInterceptor;
import com.fanjun.httpclient.httpcenter.Config;
import com.fanjun.httpclient.httpcenter.HttpCenter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.socialize.PlatformConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.edencity.customer.data.AppContant;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.service.WebService;
import com.edencity.customer.util.DeeSpUtil;
import com.edencity.customer.util.HttpsClient;
import com.edencity.customer.util.Logger;
import com.edencity.customer.util.PreferencesUtil;
import com.edencity.customer.view.LoadingDialog;

import org.android.agoo.xiaomi.MiPushRegistar;

import okhttp3.OkHttpClient;

import static anet.channel.bytes.a.TAG;

public class App extends MultiDexApplication {

    private static App defaultApp;
    private WebService mWebService;
    private HttpsClient mHttpsClient;
    private PreferencesUtil preferences;


    private DeeSpUtil spUtil;
    private Customer customer;
    //全局缓存
    private HashMap<String, Object> cache;
    private ExecutorService mExecService;

    private OkHttpClient httpClient = new OkHttpClient();
    private UserMsgEntity userMsg;
    public static Typeface medium;
    public static Typeface normal;
    public static IWXAPI mWxApi;
   /* public static LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();*/
    @Override
    public void onCreate() {
        super.onCreate();
        defaultApp = this;
        registerToWx();
        DeeSpUtil.init(this, "edencity", Context.MODE_PRIVATE);
        spUtil = DeeSpUtil.getInstance();

        // 自定义字体，在自定义控件里频繁创建typeface会导致加载卡顿所以在application里加载
        medium = Typeface.createFromAsset(getAssets(), "fonts/medium.otf");
        normal = Typeface.createFromAsset(getAssets(), "fonts/normal.otf");

        //友盟
        PlatformConfig.setWeixin(AppContant.WXAPPID, AppContant.APPSERCRET);
        UMConfigure.init(this, AppContant.YOUMENG_APPKEY,
                "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                AppContant.YOUMENG_MSG_SERCRET);
        UMConfigure.setLogEnabled(false);
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);

        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //服务端控制声音
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {


            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
                mPushAgent.addAlias(getSp().getString("userId"), "PERSONID", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean isSuccess, String message) {
                        Log.i(TAG, isSuccess+"");
                    }
                });
               //别名绑定，将某一类型的别名ID绑定至某设备，老的绑定设备信息被覆盖，别名ID和deviceToken是一对一的映射关系
                mPushAgent.setAlias(getSp().getString("userId"), "PERSONID", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean isSuccess, String message) {
                        Log.i(TAG, isSuccess+"");
                    }
                });
             //移除别名ID
                /*mPushAgent.deleteAlias(getSp().getString("userId"), "PERSONID", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean isSuccess, String message) {
                    }
                });*/
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        //bugly
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        /* // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(context, AppContant.BUGLY_APPID, true);//正式环境设置成false*/
        //小米推送
        MiPushRegistar.register(this, AppContant.XIAOMI_APPID, AppContant.XIAOMI_KEY);

        mHttpsClient = new HttpsClient();
        mHttpsClient.initHttpsClient();
        mWebService = new WebService();
        mWebService.setHttpsClient(mHttpsClient);

        cache = new HashMap<>();
        //设置debug模式，默认为false，设置为true后，发请求，过滤"RxHttp"能看到请求日志
        /*RxHttp.setDebug(true);*/
        Logger.setDebug(true);
        HttpCenter.create(this,
                Config.ini()
                        .charset("utf-8")
                        .proxyHost("")
                        .proxyPort(0)
                        .threadPool(10)
                        .connectTimeout(20*1000)
        .requestInterceptor(new MyRequestInterceptor())
                .responseInterceptor(new MyResponseInterceptor())
        );

    }

    private String getProcessName(int myPid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + myPid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    private void registerToWx() {
        mWxApi = WXAPIFactory.createWXAPI(this, AppContant.WXAPPID, false);
        mWxApi.registerApp(AppContant.WXAPPID);
    }

    public static App defaultApp() {
        return defaultApp;
    }

    public static WebService webService() {
        if (defaultApp.mWebService == null)
            defaultApp.mWebService = new WebService();
        return defaultApp.mWebService;
    }

    public static HttpsClient httpClient() {
        if (defaultApp.mHttpsClient == null) {
            defaultApp.mHttpsClient = new HttpsClient();
            defaultApp.mHttpsClient.initHttpsClient();
        }
        return defaultApp.mHttpsClient;
    }


    public static DeeSpUtil getSp() {
        return defaultApp.spUtil;
    }


    public static UserMsgEntity userMsg() {
        if (defaultApp.userMsg!=null){
            return defaultApp.userMsg;
        }
        return new UserMsgEntity();
    }


    public void saveUserMsg(UserMsgEntity user) {

        if (user == null) {
            spUtil.remove("userId");
            spUtil.remove("ticket");
        } else {
            UserMsgEntity userMsgEntity = new UserMsgEntity(user.getCustomer(), user.getAccount());
            this.userMsg = userMsgEntity;
        }
    }


    public static void setCache(String key, Object value) {
        defaultApp.cache.put(key, value);
    }

    public static void removeCache(String key) {
        defaultApp.cache.remove(key);
    }

    public static Object getCache(String key) {
        return defaultApp.cache.get(key);
    }

    /**
     * 执行任务
     *
     * @param task
     * @return
     */
    public static boolean execute(Runnable task) {
        try {
            if (defaultApp.mExecService == null) {
                defaultApp.mExecService = Executors.newCachedThreadPool();
            }
            defaultApp.mExecService.execute(task);
            return true;
        } catch (Exception e) {
            LoadingDialog.hideLoading();
            Log.e("edencity", e.getMessage(), e);
            return false;
        }

    }

}

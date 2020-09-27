package com.edencity.customer.data;

import android.content.Intent;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.edencity.customer.App;
import com.edencity.customer.BuildConfig;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DataService {

    private static final long DEFAULT_TIMEOUT = 20000;
    private static volatile LoginService mLoginApiService;
    private static volatile HomeService mHomeApiService;
    private static volatile UserService mUserApiService;
    private static  DataService dataService;

    public static DataService getInstance(){
        if (dataService==null){
            return new DataService();
        }
        return dataService;
    }
     public static LoginService getService() {
           if (mLoginApiService == null) {
                synchronized (DataService.class) {
                       if (mLoginApiService == null) {
                              HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                              if (BuildConfig.DEBUG) {
                                  logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                              } else {
                                  logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                              }

                              final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                                      .addInterceptor(logging)
                                      .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                      .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                      .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

                              Retrofit retrofit = new Retrofit.Builder()
                                      .client(httpClient.build())
                                      .addConverterFactory(GsonConverterFactory.create())
                                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                      .baseUrl(AppContant.BASE_URL)
                                      .build();
                           mLoginApiService = retrofit.create(LoginService.class);
                       }
                }
           }
           return mLoginApiService;
     }

     public static HomeService getHomeService() {
           if (mHomeApiService == null) {
                synchronized (DataService.class) {
                       if (mHomeApiService == null) {
                              HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                              if (BuildConfig.DEBUG) {
                                  logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                              } else {
                                  logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                              }

                              final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                                      .addInterceptor(logging)
                                      .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                      .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                                      .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

                              Retrofit retrofit = new Retrofit.Builder()
                                      .client(httpClient.build())
                                      .addConverterFactory(GsonConverterFactory.create())
                                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                      .baseUrl(AppContant.BASE_URL)
                                      .build();
                           mHomeApiService = retrofit.create(HomeService.class);
                       }
                }
           }
           return mHomeApiService;
     }

     public static UserService getUserService() {
           if (mUserApiService == null) {
                synchronized (DataService.class) {
                       if (mUserApiService == null) {
                              HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                              if (BuildConfig.DEBUG) {
                                  logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                              } else {
                                  logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                              }

                              final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                                      .addInterceptor(logging)
                                      .retryOnConnectionFailure(false)
                                      .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                                      .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                                      .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

                              Retrofit retrofit = new Retrofit.Builder()
                                      .client(httpClient.build())
                                      .addConverterFactory(ScalarsConverterFactory.create())
                                      .addConverterFactory(GsonConverterFactory.create())
                                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                      .baseUrl(AppContant.BASE_URL)
                                      .build();
                           mUserApiService = retrofit.create(UserService.class);
                       }
                }
           }
           return mUserApiService;

     }

    public void syncUser(String userId, String ticket, IBaseCallBack<UserMsgEntity> callBack) {

            //更新用户信息
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("userId", userId);
            hashMap.put("ticket", ticket);
            hashMap.put("app_id", AppContant.APPID);
            hashMap.put("nonce", "1");
            String sign = ParamsUtils.getSign(hashMap);
            try {
                hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            } catch (Exception e) {
                e.printStackTrace();
            }

            getUserService().sysnUserDesc(hashMap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResult<UserMsgEntity>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResult<UserMsgEntity> baseResult) {

                            if (baseResult != null && baseResult.getResult_code() == 0) {
                                Log.d("bbbbb", baseResult.toString());
                                callBack.onSuccess(baseResult.getData());
                            } else if (baseResult.getResult_code()== -3){
                               AdiUtils.loginOut();
                            }else {
                                callBack.onFail(baseResult.getResult_msg()==null?"请求失败":baseResult.getResult_msg());
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("bbbbb", e.getMessage());
                            callBack.onFail(e.getMessage()==null?"请求失败":e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }


}

package com.edencity.customer.data;


import java.util.Map;


import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.CheckUpdateEntity;
import com.edencity.customer.entity.UserLoginEntity;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {


    //查询是否有可更新版本
    //type
    @POST("api/customer/app/version")
    @FormUrlEncoded
    Observable<BaseResult<CheckUpdateEntity>>checkUpdate(@FieldMap Map<String,Object>map);

    /*
    *
    * 登录注册相关
    *
    */


    //获取验证码 phone
    // type (login,regist,forget,modify)
    @POST("api/customer/verificationCode/get")
    @FormUrlEncoded
    Observable<BaseResult> get(@FieldMap Map<String,Object>map);

    //注册
    // phone
    // verificationCode
    // password
    @POST("api/customer/register")
    @FormUrlEncoded
    Observable<BaseResult<UserLoginEntity>>register(@FieldMap Map<String,Object>map);

    //验证验证码是否正确
    //phone
    //verificationCode
    @POST("api/customer/verificationCode/validate")
    @FormUrlEncoded
    Observable<BaseResult>verifyVCode(@FieldMap Map<String,Object>map);

    //验证码登录
    // phone
    // verificationCode
    @POST("api/customer/verificationCode/login")
    @FormUrlEncoded
    Observable<BaseResult<UserLoginEntity>>verifyLogin(@FieldMap Map<String,Object>map);

    //手机号密码登录
    // phone
    // password
    @POST("api/customer/password/login")
    @FormUrlEncoded
    Observable<BaseResult<UserLoginEntity>>normalLogin(@FieldMap Map<String,Object>map);

    //忘记、修改密码
    // phone
    // verificationCode
    // password
    @POST("api/customer/password/reset")
    @FormUrlEncoded
    Observable<BaseResult>resetLoginPwd(@FieldMap Map<String,Object>map);

    //忘记、修改支付密码
    // phone
    // verificationCode
    // payPassword
    // userId
    // ticket
    @POST("api/customer/payPassword/reset")
    @FormUrlEncoded
    Observable<BaseResult>resetPayPwd(@FieldMap Map<String,Object>map);

    //获取充值规则
    // ticket
    // userId
    @POST("api/customer/charge/getCurrentRule")
    @FormUrlEncoded
    Observable<BaseResult>getRechargeRule(@FieldMap Map<String,Object>map);

}

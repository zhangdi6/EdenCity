package com.edencity.customer.data;

// Created by Ardy on 2020/1/7.

import java.util.Map;

import com.edencity.customer.entity.ActiveGetHistoryEntity;
import com.edencity.customer.entity.ActiveProductListEntity;
import com.edencity.customer.entity.AliPayEntity;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.BillOrderEntity;
import com.edencity.customer.entity.BuyHistoryEntity;
import com.edencity.customer.entity.CodeEntity;
import com.edencity.customer.entity.ConvertListEntity;
import com.edencity.customer.entity.FeedBackListEntity;
import com.edencity.customer.entity.MsgListEntity;
import com.edencity.customer.entity.UnReadCountEntity;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.entity.WeChatPayEntity;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UserService {

    /*
     *
     * 用户相关
     *
     */

    //获取用户账单列表
    // userId
    // ticket
    // pageNum
    // pageSize
    @POST("api/customer/accountChangeRecordList")
    @FormUrlEncoded
    Observable<BaseResult>getUserOrder(@FieldMap Map<String,Object>map);

    //同步用户信息
    // userId
    // ticket
    @POST("api/customer/sync")
    @FormUrlEncoded
    Observable<BaseResult<UserMsgEntity>>sysnUserDesc(@FieldMap Map<String,Object>map);

    //修改用户信息
    // nickName
    // gender
    // birthday
    // userId
    // ticket
    @POST("api/customer/edit")
    @FormUrlEncoded
    Observable<BaseResult>editUserDesc(@FieldMap Map<String,Object>map);

    //实名认证
    // userId
    // ticket
    // userName
    // idCard
    // frontImage
    // backImage
    @POST("api/customer/certificate")
    @Multipart
    Observable<BaseResult>userCertificate(@PartMap Map<String,Object>map);

    //充值 微信支付
    // userId
    // ticket
    // chargeAmount
    @POST("api/customer/charge/weichatpay")
    @FormUrlEncoded
    Observable<BaseResult<WeChatPayEntity>>wechatPay(@FieldMap Map<String,Object>map);

    //充值 阿里支付
    // userId
    // ticket
    // chargeAmount
    @POST("api/customer/charge/alipay")
    @FormUrlEncoded
    Observable<BaseResult<AliPayEntity>>aliPay(@FieldMap Map<String,Object>map);

    //开通会员 微信支付
    // userId
    // ticket
    @POST("api/customer/member/establish/weichatpay")
    @FormUrlEncoded
    Observable<BaseResult<WeChatPayEntity>>wechatOpenPay(@FieldMap Map<String,Object>map);

    //开通会员 阿里支付
    // userId
    // ticket
    @POST("api/customer/member/establish/alipay")
    @FormUrlEncoded
    Observable<BaseResult<AliPayEntity>>aliOpenPay(@FieldMap Map<String,Object>map);

    //续费会员 微信支付
    // userId
    // ticket
    @POST("api/customer/member/renew/weichatpay")
    @FormUrlEncoded
    Observable<BaseResult<WeChatPayEntity>>wechatRePay(@FieldMap Map<String,Object>map);

    //续费会员 阿里支付
    // userId
    // ticket
    @POST("api/customer/member/renew/alipay")
    @FormUrlEncoded
    Observable<BaseResult<AliPayEntity>>aliRePay(@FieldMap Map<String,Object>map);

   //扫码支付
    // userId
    // ticket
    // chargeAmount
    @POST("api/customer/pay")
    @FormUrlEncoded
    Observable<BaseResult<AliPayEntity>>codePay(@FieldMap Map<String,Object>map);

    /*
    用户反馈
    userID
    ticket
    content
    contact
    source
    evidence1
    evidence2
    evidence3
    */
    @POST("api/customer/feedback/create")
    @Multipart
    Observable<BaseResult> userFeedBack(@PartMap Map<String, Object>map);


    /*我的兑换券
     userId
     ticket
     convertStatus
    */
    @POST("api/customer/activity/convertedList")
    @FormUrlEncoded
    Observable<BaseResult<ConvertListEntity>>userConcert(@FieldMap Map<String,Object>map);


    /*我的活跃值商品列表
     userId
     ticket
    */
    @POST("api/customer/activity/convertedGoodsList")
    @FormUrlEncoded
    Observable<BaseResult<ActiveProductListEntity>>getConvertedGoodsList(@FieldMap Map<String,Object>map);

    /*生成兑换券
     userId
     ticket
     goodsId 商品Id
     convertedAmount 数量'
    */
    @POST("api/customer/activity/genConvertedQRcode")
    @FormUrlEncoded
    Observable<BaseResult<CodeEntity>>getGenConvertedQRcode(@FieldMap Map<String,Object>map);


    //活跃值增减记录
    @POST("api/customer/activityObtainRecord")
    @FormUrlEncoded
    Observable<BaseResult<ActiveGetHistoryEntity>>getObtainRecord(@FieldMap Map<String,Object>map);

    //用户账单
    @POST("api/customer/accountChangeRecordList")
    @FormUrlEncoded
    Observable<BaseResult<BillOrderEntity>>userAccountList(@FieldMap Map<String,Object>map);


    //会员购买记录
    @POST("api/customer/membershipRecordList")
    @FormUrlEncoded
    Observable<BaseResult<BuyHistoryEntity>>vipBuyHistory(@FieldMap Map<String,Object>map);

    //获取未读消息数量
    @POST("api/customer/message/unReadCount")
    @FormUrlEncoded
    Observable<BaseResult<UnReadCountEntity>>getUnReadCount(@FieldMap Map<String,Object>map);

    //获取未读消息记录
    @POST("api/customer/message/list")
    @FormUrlEncoded
    Observable<BaseResult<MsgListEntity>>getMsg(@FieldMap Map<String,Object>map);

    //获取用户反馈记录
    @POST("api/customer/feedback/list")
    @FormUrlEncoded
    Observable<BaseResult<FeedBackListEntity>>getUserFeedBackHistory(@FieldMap Map<String,Object>map);

    //标记已读 messageId
    @POST("api/customer/message/setMessageStatusReaded")
    @FormUrlEncoded
    Observable<BaseResult>setReaded(@FieldMap Map<String,Object>map);

    //全部标记已读
    @POST("api/customer/message/setAllMessageStatusReaded")
    @FormUrlEncoded
    Observable<BaseResult>setAllReaded(@FieldMap Map<String,Object>map);
}

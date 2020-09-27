package com.edencity.customer.data;

// Created by Ardy on 2020/1/7.

import java.util.Map;

import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.CustomerEvent;
import com.edencity.customer.entity.HomeBannerEntity;
import com.edencity.customer.entity.HomeShopListEntity;
import com.edencity.customer.entity.PayResultEntity;
import com.edencity.customer.entity.ProductDetailEntity;
import com.edencity.customer.entity.QrEntity;
import com.edencity.customer.entity.ShopDetailEntity;
import com.edencity.customer.entity.SignAmoutEntity;
import com.edencity.customer.entity.SignInEntity;
import com.edencity.customer.entity.TabEntity;
import com.edencity.customer.entity.TabItemEntity;
import com.edencity.customer.entity.TeamEntity;
import com.edencity.customer.entity.VipGoodEntity;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HomeService {

    /*
     *
     * 首页相关
     *
     */

    //获取首页banner
    @POST("api/customer/banner/list")
    @FormUrlEncoded
    Observable<BaseResult<HomeBannerEntity>> getHomeBanner(@FieldMap Map<String, Object> map);

   //获取首页活动列表
    @POST("api/customer/customerEvent")
    @FormUrlEncoded
    Observable<BaseResult<CustomerEvent>> customerEvent(@FieldMap Map<String, Object> map);


    //获取首页商家列表
    @POST("api/customer/homeStore/list")
    @FormUrlEncoded
    Observable<BaseResult<HomeShopListEntity>> getHomeShopList(@FieldMap Map<String, Object> map);

    //查看签到记录
    @POST("api/customer/getSignInRecord")
    @FormUrlEncoded
    Observable<BaseResult> getSigninHistory(@FieldMap Map<String, Object> map);

    //签到
    @POST("api/customer/signIn")
    @FormUrlEncoded
    Observable<BaseResult<SignAmoutEntity>> signIn(@FieldMap Map<String, Object> map);

    //获取当天签到状态
    @POST("api/customer/getTodaySignStatus")
    @FormUrlEncoded
    Observable<BaseResult<SignInEntity>> getTatalStatu(@FieldMap Map<String, Object> map);

    //商家详情
    @POST("api/customer/store/detail")
    @FormUrlEncoded
    Observable<BaseResult<ShopDetailEntity>> getShopDetail(@FieldMap Map<String, Object> map);

    //获取付款码
    @POST("api/customer/qrcode/gen")
    @FormUrlEncoded
    Observable<BaseResult<QrEntity>> getQrCode(@FieldMap Map<String, Object> map);

    //获取商家信息
    @POST("api/customer/store/detail")
    @FormUrlEncoded
    Observable<BaseResult> getStoreDetail(@FieldMap Map<String, Object> map);

    //获取活跃值商品详情
    @POST("api/customer/convertedGoods/detail")
    @FormUrlEncoded
    Observable<BaseResult<ProductDetailEntity>> getProductDetail(@FieldMap Map<String, Object> map);

    //查询被扫码的结果
    @POST("api/customer/pay/result")
    @FormUrlEncoded
    Observable<BaseResult<PayResultEntity>> getResult(@FieldMap Map<String, Object> map);

    //获取充值优惠
    @POST("api/customer/charge/effectiveRuleList")
    @FormUrlEncoded
    Observable<VipGoodEntity> getVipGood(@FieldMap Map<String, Object> map);

    //获取指定用户充值优惠
    @POST("api/customer/charge/getCurrentRule")
    @FormUrlEncoded
    Observable<BaseResult> getTotalVipGood(@FieldMap Map<String, Object> map);

    //获取首页创业团队列表
    @POST("api/customer/entryTeams")
    @FormUrlEncoded
    Observable<BaseResult<TeamEntity>> getTeamList(@FieldMap Map<String,Object>map);

    //获取大消费业态界面tab
    @POST("api/customer/categoryList")
    @FormUrlEncoded
    Observable<BaseResult<TabEntity>> getXiaofeiTabList(@FieldMap Map<String,Object>map);

    //获取大消费业态界面列表
    @POST("api/customer/itemizeList")
    @FormUrlEncoded
    Observable<BaseResult<TabItemEntity>> getXiaofeiRlvList(@FieldMap Map<String,Object>map);
}
package com.edencity.customer.service;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.edencity.customer.data.AppContant;

public class WeixinPayService {

    public static WeixinPayService instance;
    private IWXAPI api;
    //应用ID
    private final String appId="wxf6ed4c8b0a27e560";

    public static WeixinPayService getInstance(){
        if (instance == null){
            instance = new WeixinPayService();
        }
        return instance;
    }

    public static IWXAPI getApi(Context context){
        if (instance.api==null){
            instance.api=WXAPIFactory.createWXAPI(context, instance.appId);
            instance.api.registerApp(instance.appId);
        }
        return instance.api;
    }
    /**
     * 发送支付请求
     * @param context
     * @param mchId 商户id
     * @param prePayId
     * @param nonceStr
     * @param timestamp
     * @param sign
     * @return
     */
    public String sendPayRequest(Context context,String mchId,String prePayId,String nonceStr,String timestamp,String sign){
        try {
            if (api==null){
                api=WXAPIFactory.createWXAPI(context, AppContant.WXAPPID,true);
                api.registerApp(appId);
            }else{
                api=WXAPIFactory.createWXAPI(context, null);
                api.registerApp(AppContant.WXAPPID);
            }
           if (api.isWXAppInstalled()){
                return "请您先安装微信，然后才能使用微信支付";
            }

            PayReq req = new PayReq();
            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
            req.appId			= appId;
            req.partnerId		= mchId;
            req.prepayId		= prePayId;
            req.nonceStr		= nonceStr;
            req.timeStamp		= timestamp;
            //默认
            req.packageValue	= "Sign=WXPay";
            req.sign			= sign;
            api.sendReq(req);
            return null;
        }catch (Exception e){
            return "请求微信支付失败，请重试";
        }
    }


}

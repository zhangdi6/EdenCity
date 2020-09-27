package com.edencity.customer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.edencity.customer.App;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.util.AdiUtils;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    //6297027628aae82afe9ba628519b0146
    //59ba7fe4c72d44f9086fce23de5ce6bd
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, AppContant.WXAPPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            ///支付
            if (0 == baseResp.errCode) {
                onPaySuccess();
            } else if (-1 == baseResp.errCode) {
                onPayFailed();
            } else if (-2 == baseResp.errCode) {
                onPayCancel();
            }
        }
    }

    private void onPayCancel() {
        AdiUtils.showToast( "支付取消");
        finish();
        overridePendingTransition(0, 0);
        /*EventBus.getDefault().postSticky(new EventPay("cancel"));*/

    }

    private void onPayFailed() {
        AdiUtils.showToast( "支付失败");
        finish();
        overridePendingTransition(0, 0);
        /*EventBus.getDefault().postSticky(new EventPay("failed"));*/
    }
    private void onPaySuccess() {
        /*AdiUtils.showToast( "支付成功");*/
        EventBus.getDefault().post(new EventPay("success"));
        finish();
        overridePendingTransition(0, 0);

    }
}

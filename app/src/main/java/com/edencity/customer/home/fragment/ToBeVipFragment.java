package com.edencity.customer.home.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.edencity.customer.entity.VipGoodEntity;
import com.edencity.customer.fragment.PayResultFragment2;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.home.adapter.MyVipLevelAdapter;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.wxapi.EventPay;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.alipay.PayResult;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.AliPayEntity;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.WeChatPayEntity;
import com.edencity.customer.home.adapter.RechargeTypeAdapter;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.SHA1Utils;
import com.edencity.customer.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToBeVipFragment extends BaseFragment2 {


    private RecyclerView mRlvType;
    private RechargeTypeAdapter adapter;
    private SmartRefreshLayout mSmart;
    private IWXAPI wxapi;
    private String sn_ali;
    private String userVipLevel;
    private RecyclerView mRlv;
    private LinearLayout ll;
    private TextView level_desc;
    /*private LinearLayout ll2;

    private LinearLayout ll4;
    private MyNormalTextView tv1;
    private MyNormalTextView tv2;
    private MyNormalTextView tv3;
    private MyNormalTextView tv4;
    private MyNormalTextView tv5;
    private MyNormalTextView tv6;
    private MyNormalTextView tv7;
    private MyNormalTextView tv8;*/


    public ToBeVipFragment() {
        // Required empty public constructor
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    ((MainActivity)getActivity()).start(PayResultFragment2.newInstance(true,"支付成功",""));
                }else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    AdiUtils.showToast(payResult.getMemo());
                }
            }


        }
    };
    public static ToBeVipFragment getInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        ToBeVipFragment toBeVipFragment = new ToBeVipFragment();
        toBeVipFragment.setArguments(bundle);
        return toBeVipFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_to_be, container, false);
        EventBus.getDefault().register(this);
        if (1== getArguments().getInt("type")){
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            StatusBarCompat.changeToLightStatusBar(getActivity());
        }
        initView(inflate);
        initPayType();
        return inflate;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getData2();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(EventPay eventPay){
        if (eventPay.getResult()!=null && !eventPay.getResult().equals("")){
            if (eventPay.getResult().equals("success")){
                ((MainActivity)getActivity()).start(PayResultFragment2.newInstance(true,"支付成功",""));
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void getData2() {
        HashMap hashMap = ParamsUtils.getParamsMapWithNoId(null, null);
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getVipGood(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VipGoodEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VipGoodEntity o) {

                        Log.e("vip",o.toString());
                        if (o.getResult_code()==0 && o.getData()!=null && o.getData().size()>0){
                            List<VipGoodEntity.DataBean> data = o.getData();
                            String orNot = data.get(1).getActivityOrNot();
                            if ("false".equals(orNot)){
                                level_desc.setVisibility(View.GONE);
                            }else{
                                level_desc.setVisibility(View.VISIBLE);
                                level_desc.setText("另：成为二级会员，充值预付卡成功后将再根据个人" +
                                        "活跃值额外获得伊甸果返赠，返赠的伊甸果数量为活跃值的"+data.get(1).getActivityPercent()+"%");
                            }
                            if (data.size()>=2){
                                if (data.get(1)!=null && data.get(1).getItemList()!=null && data.get(1).getItemList().size()>0){
                                    List<VipGoodEntity.DataBean.ItemListBean> itemList = data.get(1).getItemList();

                                    ll.setVisibility(View.GONE);
                                    mRlv.setVisibility(View.VISIBLE);
                                    mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    mRlv.setHasFixedSize(true);
                                    mRlv.setNestedScrollingEnabled(false);
                                    MyVipLevelAdapter adapter = new MyVipLevelAdapter();
                                    adapter.addData(itemList);
                                    mRlv.setAdapter(adapter);
                                }else{
                                    ll.setVisibility(View.VISIBLE);
                                    mRlv.setVisibility(View.GONE);
                                }
                            }else{
                                ll.setVisibility(View.VISIBLE);
                                mRlv.setVisibility(View.GONE);
                            }

                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else{
                            ll.setVisibility(View.VISIBLE);
                            mRlv.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ll.setVisibility(View.VISIBLE);
                        mRlv.setVisibility(View.GONE);
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }
                    }
                });
    }

    private ArrayList<BaseDebug> getList(List<VipGoodEntity.DataBean> data) {

            if (data.size()>=2){
                VipGoodEntity.DataBean dataBean = data.get(1);
                List<VipGoodEntity.DataBean.ItemListBean> itemList = dataBean.getItemList();
                ArrayList<BaseDebug> objects = new ArrayList<>();
                for (int i = 0; i < itemList.size(); i++) {
                    VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                    if (i==0){
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",R.mipmap.zeng1,(int)listBean.getPerBonus()+""));
                    }else if (i==1){
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",R.mipmap.zeng2,(int)listBean.getPerBonus()+""));
                    }else if (i==2){
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",R.mipmap.zeng3,(int)listBean.getPerBonus()+""));
                    }else{
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",R.mipmap.zeng4,(int)listBean.getPerBonus()+""));
                    }
                }
                return objects;

        }
        return null;
    }

    public void popp(){
       pop();
    }

    private void initView(View inflate) {
        ImageView mBack = inflate.findViewById(R.id.back);
        MyMediumTextView level = inflate.findViewById(R.id.level);
         mRlv = inflate.findViewById(R.id.rlv);
         mSmart = inflate.findViewById(R.id.smart);
        ll  = inflate.findViewById(R.id.ll);
       /* LinearLayout ll1 = inflate.findViewById(R.id.ll1);

        ll3  = inflate.findViewById(R.id.ll3);
        ll4  = inflate.findViewById(R.id.ll4);
        tv1  = inflate.findViewById(R.id.tv1);
        tv2  = inflate.findViewById(R.id.tv2);
        tv3  = inflate.findViewById(R.id.tv3);
        tv4  = inflate.findViewById(R.id.tv4);
        tv5  = inflate.findViewById(R.id.tv5);
        tv6  = inflate.findViewById(R.id.tv6);
        tv7  = inflate.findViewById(R.id.tv7);
        tv8  = inflate.findViewById(R.id.tv8);*/

         level_desc = inflate.findViewById(R.id.level_desc);
        mBack.setOnClickListener(v -> pop());

        MyNormalTextView mText_blue = inflate.findViewById(R.id.blue_text);

        mRlvType = inflate.findViewById(R.id.rlv_type);
        MyMediumTextView mTv_pay = inflate.findViewById(R.id.pay);

        mTv_pay.setOnClickListener(v -> onPay());
        level.setText("二级汇员特享");
        /*level_desc.setText("另：成为二级会员，充值预付卡成功后将再根据个人活跃值额外获得伊甸果返赠，返赠的伊甸果数量为活跃值的5%");*/
         userVipLevel = App.userMsg().getCustomer().getUserVipLevel();
         if (userVipLevel!=null && userVipLevel.equals("普通汇员")){
             String str = "（开通成功立即赠送365伊甸果）";
             SpannableString spannableString = new SpannableString(str);
             ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ResUtils.getColor(getContext(), R.color.blue_nomal));
             spannableString.setSpan(foregroundColorSpan,str.indexOf("赠"),str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
             mText_blue.setText(spannableString);
         }else if (userVipLevel!=null && userVipLevel.equals("付费汇员")){
             String str = "（续费成功立即赠送365伊甸果）";
             SpannableString spannableString = new SpannableString(str);
             ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ResUtils.getColor(getContext(), R.color.blue_nomal));
             spannableString.setSpan(foregroundColorSpan,str.indexOf("赠"),str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
             mText_blue.setText(spannableString);
           /*  level.setText("三级汇员特享");
             level_desc.setText("另：成为三级会员，充值预付卡成功后将再根据个人活跃值额外获得伊甸币返赠，返赠的伊甸币数量为活跃值的5%");*/
         }else if (userVipLevel!=null && userVipLevel.equals("尊享汇员")){

         }

         mSmart.setOnRefreshListener(new OnRefreshListener() {
             @Override
             public void onRefresh(RefreshLayout refreshLayout) {
                 getData2();
             }
         });


    }

    private void onPay() {
        int checkedPosition = adapter.getCheckedPosition();

        if (userVipLevel!=null && userVipLevel.equals("普通汇员")){
            //微信支付
            if (checkedPosition==0){
                onWeixinPay();
            }else{
                //支付宝
                onAliPay();
            }
        }else{
            //微信支付
            if (checkedPosition==0){
                onWeixinRePay();
            }else{
                //支付宝
                onAliRePay();
            }
        }

    }

    private void onAliPay(){
        LoadingDialog.showLoading(getChildFragmentManager());

        HashMap hashMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().aliOpenPay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<AliPayEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<AliPayEntity> baseResult) {
                        LoadingDialog.hideLoading();
                        Log.e("card", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            sn_ali = baseResult.getData().getPrePayMessage();
                            Runnable payRunnable = () -> {
                                PayTask alipay = new PayTask(getActivity());
                                Map<String, String> result = alipay.payV2(sn_ali, true);
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("card", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    private void onAliRePay(){
        LoadingDialog.showLoading(getChildFragmentManager());

        HashMap hashMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().aliRePay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<AliPayEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<AliPayEntity> baseResult) {
                        LoadingDialog.hideLoading();
                        Log.e("card", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            sn_ali = baseResult.getData().getPrePayMessage();
                            Runnable payRunnable = () -> {
                                PayTask alipay = new PayTask(getActivity());
                                Map<String, String> result = alipay.payV2(sn_ali, true);
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("card", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void onWeixinPay(){
        LoadingDialog.showLoading(getChildFragmentManager());

        HashMap hashMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().wechatOpenPay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<WeChatPayEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<WeChatPayEntity> baseResult) {
                        LoadingDialog.hideLoading();
                        Log.e("card", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            WeChatPayEntity data = baseResult.getData();

                            if (!App.mWxApi.isWXAppInstalled()){
                                AdiUtils.showToast("请您先安装微信，然后才能使用微信支付");
                            }else{

                                wxapi = WXAPIFactory.createWXAPI(getActivity(), null);
                                wxapi.registerApp(AppContant.WXAPPID);
                                PayReq req = new PayReq();
                                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                                req.appId			= data.getAppid();
                                req.partnerId		= data.getMch_id();
                                req.prepayId		=  data.getPrepay_id();
                                req.nonceStr		= data.getNonce_str();
                                req.timeStamp		= data.getTimestamp();
                                //默认
                                req.packageValue	= "Sign=WXPay";
                                req.sign			= data.getSign();
                                wxapi.sendReq(req);

                            }

                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            AdiUtils.showToast("支付失败，"+(baseResult.getResult_msg()==null?"请您重试":baseResult.getResult_msg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("card", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    private void onWeixinRePay(){

        LoadingDialog.showLoading(getChildFragmentManager());

        HashMap hashMap = ParamsUtils.getParamsMap();

        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().wechatRePay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<WeChatPayEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<WeChatPayEntity> baseResult) {
                        LoadingDialog.hideLoading();
                        Log.e("card", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            WeChatPayEntity data = baseResult.getData();

                            if (!App.mWxApi.isWXAppInstalled()){

                                AdiUtils.showToast("请您先安装微信，然后才能使用微信支付");

                            }else{

                                wxapi = WXAPIFactory.createWXAPI(getActivity(), null);
                                wxapi.registerApp(AppContant.WXAPPID);
                                PayReq req = new PayReq();
                                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                                req.appId			= data.getAppid();
                                req.partnerId		= data.getMch_id();
                                req.prepayId		=  data.getPrepay_id();
                                req.nonceStr		= data.getNonce_str();
                                req.timeStamp		= data.getTimestamp();
                                //默认
                                req.packageValue	= "Sign=WXPay";
                                req.sign			= data.getSign();
                                wxapi.sendReq(req);

                            }

                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            AdiUtils.showToast("支付失败，"+(baseResult.getResult_msg()==null?"请您重试":baseResult.getResult_msg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("card", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    private void initPayType() {
        ArrayList<BaseDebug> objects = new ArrayList<>();
        objects.add(new BaseDebug("微信支付",R.mipmap.check_blue,R.mipmap.check_gray,
                true,R.mipmap.wechat_green,R.mipmap.wechat_gray));
        objects.add(new BaseDebug("支付宝支付",R.mipmap.check_blue,R.mipmap.check_gray,
                false,R.mipmap.ali_blue,R.mipmap.ali_gay));

        mRlvType.setHasFixedSize(true);
        mRlvType.setNestedScrollingEnabled(false);
        mRlvType.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RechargeTypeAdapter();
        mRlvType.setAdapter(adapter);
        adapter.addData(objects);
        adapter.onItemCheck(position -> adapter.changeState(position));

    }

}

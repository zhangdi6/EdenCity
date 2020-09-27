package com.edencity.customer.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.entity.VipGoodEntity;
import com.edencity.customer.fragment.PayResultFragment2;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.wxapi.EventPay;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edencity.customer.App;
import com.edencity.customer.R;

import com.edencity.customer.alipay.PayResult;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.AliPayEntity;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.RechargeMoneyEntity;
import com.edencity.customer.entity.WeChatPayEntity;
import com.edencity.customer.home.adapter.RechargeMoneyAdapter;
import com.edencity.customer.home.adapter.RechargeTypeAdapter;
import com.edencity.customer.home.fragment.ToBeVipFragment;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;

import com.edencity.customer.view.LoadingDialog;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

public class CardRechageActivity extends SupportActivity implements View.OnClickListener{

    /**
     * 您的会员再有9天即将到期，到期后将无法再享受二级会员权益
     */
    private MyNormalTextView mNatification;
    /**
     * 立即续费
     */
    private MyMediumTextView mGoOnMoney;
    /**
     * 3000
     */
    private MyMediumTextView mRechargeTotalMoney;
    private RecyclerView mRlvPrice;
    private RecyclerView mRlvType;
    //支付方式
    private RechargeTypeAdapter adapter;
    //充值金额
    private RechargeMoneyAdapter adapter2;
    private IWXAPI wxapi;
    private String sn_ali;
    @SuppressLint("HandlerLeak")
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
                    AdiUtils.showToast("支付成功");
                    loadRootFragment(R.id.sss,PayResultFragment2.newInstance(true,"充值成功",price+"伊甸果"));

                }else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    AdiUtils.showToast(payResult.getMemo());
                }
            }


        }
    };
    private ToBeVipFragment instance;
    private int canBack = 0;

    private float price;
    private ArrayList<RechargeMoneyEntity> objects = new ArrayList<>();
    private CardView card_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this,Color.WHITE);
        EventBus.getDefault().register(this);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_card_rechage);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        initView();
        initList();
        initPayType();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressedSupport() {
        if (canBack==0){
            super.onBackPressedSupport();
        }else{
            instance.popp();
            canBack=0;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onReceiver(EventPay eventPay){
        if (eventPay!=null && eventPay.getResult()!=null && !eventPay.getResult().equals("")){
            if (eventPay.getResult().equals("success")){
                loadRootFragment(R.id.sss,PayResultFragment2.newInstance(true,"充值成功",price+"伊甸果"));
            }
        }
    }

    private void initPayType() {
        ArrayList<BaseDebug> objects = new ArrayList<>();
        objects.add(new BaseDebug("微信支付",R.mipmap.check_blue,R.mipmap.check_gray,
                true,R.mipmap.wechat_green,R.mipmap.wechat_gray));
        objects.add(new BaseDebug("支付宝支付",R.mipmap.check_blue,R.mipmap.check_gray,
                false,R.mipmap.ali_blue,R.mipmap.ali_gay));

        mRlvType.setHasFixedSize(true);
        mRlvType.setNestedScrollingEnabled(false);
        mRlvType.setLayoutManager(new LinearLayoutManager(CardRechageActivity.this));
        adapter = new RechargeTypeAdapter();
        mRlvType.setAdapter(adapter);
        adapter.addData(objects);
        adapter.onItemCheck(position -> adapter.changeState(position));
    }

    private void initList() {

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
                                if (App.userMsg()!=null && App.userMsg().getCustomer()!=null &&
                                        App.userMsg().getCustomer().getUserVipLevel().equals("普通汇员")){
                                    List<VipGoodEntity.DataBean> data = o.getData();
                                    Log.e("dataa",data.toString());


                                    if (getList(o.getData())!=null){
                                        objects.addAll(getList(o.getData())) ;
                                    }

                                }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null &&
                                        App.userMsg().getCustomer().getUserVipLevel().equals("付费汇员")){
                                    if (getList(o.getData())!=null){
                                        objects.addAll(getList2(o.getData())) ;
                                    }

                                }else {

                                }
                                mRlvPrice.setHasFixedSize(true);
                                mRlvPrice.setNestedScrollingEnabled(false);
                                mRlvPrice.setLayoutManager(new GridLayoutManager(CardRechageActivity.this,3));
                                adapter2 = new RechargeMoneyAdapter();
                                mRlvPrice.setAdapter(adapter2);
                                adapter2.addData(objects);
                                adapter2.onItemCheck(position -> adapter2.changeState(position));
                            }else if (o.getResult_code()== -3){
                                AdiUtils.loginOut();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


    }
    private ArrayList<RechargeMoneyEntity> getList(List<VipGoodEntity.DataBean> data) {

        if (data.size()>=1){
            VipGoodEntity.DataBean dataBean = data.get(0);
            if (dataBean!=null && dataBean.getItemList()!=null && dataBean.getItemList().size()>0){
                List<VipGoodEntity.DataBean.ItemListBean> itemList = dataBean.getItemList();
                ArrayList<RechargeMoneyEntity> objects = new ArrayList<>();
                objects.add(new RechargeMoneyEntity((int)itemList.get(0).getPerAmount()+"","赠"+(int)itemList.get(0).getPerBonus(),true));
                for (int i = 1; i < itemList.size(); i++) {
                    VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                    objects.add(new RechargeMoneyEntity((int)listBean.getPerAmount()+"","赠"+(int)listBean.getPerBonus(),false));
                }
                return objects;
            }
        }else{
            Log.e("dataa","没数据");

        }
        return null;
    }
    private ArrayList<RechargeMoneyEntity> getList2(List<VipGoodEntity.DataBean> data) {

        if (data.size()>=2){
            VipGoodEntity.DataBean dataBean = data.get(1);
            if (dataBean!=null && dataBean.getItemList()!=null && dataBean.getItemList().size()>0){
                List<VipGoodEntity.DataBean.ItemListBean> itemList = dataBean.getItemList();
                ArrayList<RechargeMoneyEntity> objects = new ArrayList<>();
                objects.add(new RechargeMoneyEntity((int)itemList.get(0).getPerAmount()+"","赠"+(int)itemList.get(0).getPerBonus(),true));
                for (int i = 1; i < itemList.size(); i++) {
                    VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                    objects.add(new RechargeMoneyEntity((int)listBean.getPerAmount()+"","赠"+(int)listBean.getPerBonus(),false));
                }
                return objects;
            }

        }else{
            Log.e("dataa","没数据");

        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarCompat.setStatusBarColor(this,Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);

        DataService.getInstance().syncUser(App.getSp().getString("userId"), App.getSp().getString("ticket"), new IBaseCallBack<UserMsgEntity>() {
            @Override
            public void onSuccess(UserMsgEntity data) {
                App.defaultApp().saveUserMsg(data);

                if (data.getCustomer().getUserVipLevel().equals("普通汇员")){
                    mNatification.setText("开通付费会员，充值预付卡将享受更大优惠!");
                    mGoOnMoney.setText("立即开通");
                }else{
                    long time = data.getCustomer().getEdittime().getTime();
                    long time1 = new Date().getTime();
                    long l = (long) 30 * (long) 86400000;
                    if (time-time1<=l){
                        card_layout.setVisibility(View.GONE);
                    }else {
                        card_layout.setVisibility(View.VISIBLE);
                        mNatification.setText("续费付费会员，充值预付卡将享受更大优惠!");
                        mGoOnMoney.setText("立即续费");
                    }


                }
                if (App.userMsg()!=null && App.userMsg().getAccount()!=null ){
                    mRechargeTotalMoney.setText(App.userMsg().getAccount().getTotalBalance()+"");
                }
            }

            @Override
            public void onFail(String msg) {
                /*App.defaultApp().saveUserMsg(null);*/
            }
        });
    }


    private void initView() {
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        /**
         * 预付卡充值
         */
        MyMediumTextView mTitle = findViewById(R.id.title);
        mNatification =  findViewById(R.id.natification);
        mGoOnMoney = findViewById(R.id.go_on_money);
        mGoOnMoney.setOnClickListener(this);
        /**
         * 预付卡金额
         */
        MyNormalTextView mRechagePrice = findViewById(R.id.rechage_price);
        ConstraintLayout mCard = findViewById(R.id.card);
        mCard.setOnClickListener(this);
        mRechargeTotalMoney =  findViewById(R.id.recharge_total_money);
        mRlvPrice = (RecyclerView) findViewById(R.id.rlv_price);
        mRlvType = (RecyclerView) findViewById(R.id.rlv_type);
        SmartRefreshLayout mSmart = (SmartRefreshLayout) findViewById(R.id.smart);
        /**
         * 立即支付
         */
        MyMediumTextView mPayNow = findViewById(R.id.pay);
         card_layout = findViewById(R.id.card_layout);
        mPayNow.setOnClickListener(this);
        //跑马灯
        mNatification.setSelected(true);
        //设置下划线
        mGoOnMoney.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        mGoOnMoney.getPaint().setAntiAlias(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
                //立即支付
            case R.id.pay:
                //微信支付
                int checkedPosition = adapter2.getCheckedPosition();
                //充值金额
                 price = Float.parseFloat(adapter2.mList.get(checkedPosition).getPrice().trim());
                if (adapter.getCheckedPosition()==0){
                    onWeixinPay(price);
                }else{//支付宝支付
                    onAliPay(price);
                }
              /*  Bundle bundle = new Bundle();
                bundle.putInt("type",0);
                addFragment(getSupportFragmentManager(), PayResultFragment.class,R.id.frag,bundle);*/
                break;
                //立即续费
            case R.id.go_on_money:
                if (!ButtonUtils.isFastDoubleClick(R.id.go_on_money)){
                    instance = ToBeVipFragment.getInstance(0);
                    canBack = 1;
                    loadRootFragment(R.id.sss,instance );
                }
                break;
            case R.id.card:
                if (!ButtonUtils.isFastDoubleClick(R.id.card)){
                    Intent intent = new Intent(this, CardActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

    /**
     * 进行支付
     * @param money 单位为元
     */
    private void onWeixinPay(float money){
        LoadingDialog.showLoading(getSupportFragmentManager());

        HashMap hashMap = ParamsUtils.getParamsMap("chargeAmount", money);
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().wechatPay(hashMap)
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
                            }else if (baseResult.getResult_code()== -3){
                                AdiUtils.loginOut();
                            }else{

                                wxapi = WXAPIFactory.createWXAPI(CardRechageActivity.this, null);
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

                        } else {
                            AdiUtils.showToast("支付失败，"+(baseResult.getResult_msg()==null?"请您重试":baseResult.getResult_msg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e)   {
                        Log.e("card", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void onAliPay(float money){
        LoadingDialog.showLoading(getSupportFragmentManager());
        HashMap hashMap = ParamsUtils.getParamsMap("chargeAmount", money);
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().aliPay(hashMap)
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
                                PayTask alipay = new PayTask(CardRechageActivity.this);
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
}

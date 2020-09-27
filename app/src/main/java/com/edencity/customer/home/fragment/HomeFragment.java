package com.edencity.customer.home.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.edencity.customer.entity.ShopDetailEntity;
import com.edencity.customer.fragment.PayCodeFragment;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.home.activity.ScanFailedActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.ButtonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.loader.ImageLoader;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.edencity.customer.App;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.NormalWebActivity;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.HomeBannerEntity;
import com.edencity.customer.entity.HomeShopListEntity;
import com.edencity.customer.home.activity.CardRechageActivity;
import com.edencity.customer.home.activity.InviriteWebActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.fragment.ScanPayFragment;
import com.edencity.customer.home.activity.ShopActivity;
import com.edencity.customer.home.activity.SignInActivity;
import com.edencity.customer.home.adapter.HomeListAdapter;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.pojo.EventMessage;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.Logger;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment2 {


    //付款吗
    private RelativeLayout pay_scan;
    //扫一扫
    private RelativeLayout scan_search;
    private LinearLayout h5;
    //充值
    private RelativeLayout rechargr;
    //签到
    private RelativeLayout sign_in;
    //邀请
    private RelativeLayout invitation;
    private Banner banner;
    //列表
    private RecyclerView rlv_shop;
    private HomeListAdapter homeListAdapter;

    private BaseDialog baseDialog;
    private int sign = 0 ;
    private SmartRefreshLayout smart;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_new_home, container, false);
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
        initView(inflate);
        initLogic();
        initData();
        getDataList();
        return inflate;
    }

    private void initRules() {

        HashMap hashMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getService().getRechargeRule(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        Log.e("llll", baseResult.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("llll", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    @Override
    public void onSupportVisible() {
        super.onSupportVisible();


    }


    //获取商户列表
    private void getDataList() {

        HashMap hashMap = ParamsUtils.getParamsMap();
        //将除sign外所有参数an字母顺序排序，末尾加上key
        String sign = ParamsUtils.getSign(hashMap);
        try {
            //加密
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getHomeService().getHomeShopList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<HomeShopListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<HomeShopListEntity> homeShopListEntityBaseResult) {
                        if (smart.isRefreshing()){
                            smart.finishRefresh();
                        }
                        Logger.d(homeShopListEntityBaseResult.toString());
                        if (homeShopListEntityBaseResult.getResult_code()==0
                        &&homeShopListEntityBaseResult.getData()!=null
                        &&homeShopListEntityBaseResult.getData().getProviderList()!=null
                        &&homeShopListEntityBaseResult.getData().getProviderList().size()>0){

                            rlv_shop.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            homeListAdapter = new HomeListAdapter(getContext());
                            rlv_shop.setHasFixedSize(true);
                            rlv_shop.setNestedScrollingEnabled(false);
                            homeListAdapter.addData(homeShopListEntityBaseResult.getData().getProviderList());

                            rlv_shop.setAdapter(homeListAdapter);

                            homeListAdapter.onClick(position -> {

                                Intent intent = new Intent(getActivity(), ShopActivity.class);
                                intent.putExtra("id",homeShopListEntityBaseResult.getData().
                                        getProviderList().get(position).getProviderId());
                                startActivity(intent);
                            });


                        } else if (homeShopListEntityBaseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    //点击事件
    private void initLogic() {
        h5.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.h5)){
                Intent intent = new Intent(getActivity(), NormalWebActivity.class);
                intent.putExtra("url","https://v.qq.com/x/page/g0946ai43lr.html");
                startActivity(intent);
            }
        });
        //付款码
        pay_scan.setOnClickListener(view -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.pay_scan)){
                if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && !App.userMsg().getCustomer().isUserApprovaled()) {
                    //设置自定义视图
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_name_check, null);
                    ImageView head_img=contentView.findViewById(R.id.head_img);
                    ImageView close=contentView.findViewById(R.id.close);
                    MyNormalTextView desc1=contentView.findViewById(R.id.desc1);
                    MyNormalTextView desc2=contentView.findViewById(R.id.desc2);
                    MyMediumTextView go=contentView.findViewById(R.id.go);
                    //没有实名认证
                    if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {


                        desc1.setText(R.string.had_no_veirify);
                        desc2.setVisibility(View.VISIBLE);
                        head_img.setImageResource(R.drawable.name_check1);
                        desc2.setText(R.string.please_goto_verify);
                        go.setText("去认证>");

                        go.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });


                    }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                        head_img.setImageResource(R.drawable.name_check1);
                        desc1.setText(R.string.verify_failed);
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);
                        go.setText("去认证>");
                        go.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                    }else {//审核中
                        //设置自定义视图
                        head_img.setImageResource(R.drawable.name_check2);
                        desc1.setText(R.string.verifing);
                        desc2.setVisibility(View.GONE);
                        go.setText("我知道了");
                        go.setOnClickListener(v -> {
                            baseDialog.dismiss();
                        });
                    }
                    //视图中view点击事件
                    close.setOnClickListener(v -> {
                        //BaseDialog对象声明成全局，调用方便
                        baseDialog.dismiss();
                    });
                    baseDialog = new BaseDialog(getActivity(), contentView, Gravity.CENTER);
                    baseDialog.show();

                }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && App.userMsg().getCustomer().isUserApprovaled()){//认证了
                    ((MainActivity)getActivity()).start(PayCodeFragment.newInstance());

                }
            }
            //;*/
        });

        //扫一扫
        scan_search.setOnClickListener(view -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.scan_search)){
                if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && !App.userMsg().getCustomer().isUserApprovaled()) {
                    //没有实名认证
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_name_check, null);
                    ImageView head_img=contentView.findViewById(R.id.head_img);
                    ImageView close=contentView.findViewById(R.id.close);
                    MyNormalTextView desc1=contentView.findViewById(R.id.desc1);
                    MyNormalTextView desc2=contentView.findViewById(R.id.desc2);
                    MyMediumTextView go=contentView.findViewById(R.id.go);

                    if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {
                        //设置自定义视图
                        desc1.setText(R.string.had_no_veirify);
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");

                        go.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });

                    }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){
                        //设置自定义视图

                        head_img.setImageResource(R.drawable.name_check1);
                        desc1.setText(R.string.verify_failed);
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);

                        go.setText("去认证>");

                        go.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });

                    }else {//审核中

                        desc1.setText(R.string.verifing);
                        desc2.setVisibility(View.GONE);
                        go.setText("我知道了");
                        head_img.setImageResource(R.drawable.name_check2);
                        //视图中view点击事件
                        go.setOnClickListener(v -> baseDialog.dismiss());

                    }
                    close.setOnClickListener(v -> {
                        //BaseDialog对象声明成全局，调用方便
                        baseDialog.dismiss();
                    });
                    baseDialog = new BaseDialog(getActivity(), contentView, Gravity.CENTER);
                    baseDialog.show();
                }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && App.userMsg().getCustomer().isUserApprovaled()){
                    //认证了
                    ((MainActivity) getActivity()).doQrcodeScan();
                }
            }

        });

        //预付卡充值
        rechargr.setOnClickListener(view -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.rechargr)){
                if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && !App.userMsg().getCustomer().isUserApprovaled()) {
                    //设置自定义视图
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_name_check, null);
                    ImageView head_img=contentView.findViewById(R.id.head_img);
                    MyMediumTextView go=contentView.findViewById(R.id.go);
                    ImageView close=contentView.findViewById(R.id.close);
                    MyNormalTextView desc1=contentView.findViewById(R.id.desc1);
                    MyNormalTextView desc2=contentView.findViewById(R.id.desc2);

                    //没有实名认证
                    if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {
                        desc1.setText(R.string.had_no_veirify);
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");

                        go.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                    }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                        desc1.setText(R.string.verify_failed);
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);
                        //视图中view点击事件
                        go.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                    }else  {//审核中

                        head_img.setImageResource(R.drawable.name_check2);
                        desc1.setText(R.string.verifing);
                        desc2.setVisibility(View.GONE);
                        go.setText("我知道了");
                        go.setOnClickListener(v -> baseDialog.dismiss());

                    }
                    close.setOnClickListener(v -> {
                        //BaseDialog对象声明成全局，调用方便
                        baseDialog.dismiss();
                    });
                    baseDialog = new BaseDialog(getActivity(), contentView, Gravity.CENTER);
                    baseDialog.show();

                }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null  && App.userMsg().getCustomer().isUserApprovaled()){
                    //认证了
                    Intent intent = new Intent(getActivity(), CardRechageActivity.class);
                    startActivity(intent);
                }
            }

        });

        //签到领值
        sign_in.setOnClickListener(v -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.sign_in)){
                if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && !App.userMsg().getCustomer().isUserApprovaled()) {
                    //设置自定义视图
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_name_check, null);
                    ImageView head_img=contentView.findViewById(R.id.head_img);
                    MyMediumTextView go=contentView.findViewById(R.id.go);
                    ImageView close=contentView.findViewById(R.id.close);
                    MyNormalTextView desc1=contentView.findViewById(R.id.desc1);
                    MyNormalTextView desc2=contentView.findViewById(R.id.desc2);

                    //没有实名认证
                    if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {
                        desc1.setText(R.string.had_no_veirify);
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        go.setOnClickListener(view -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                    }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                        desc1.setText(R.string.verify_failed);
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);
                        //视图中view点击事件
                        go.setOnClickListener(view -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                    }else  {//审核中

                        head_img.setImageResource(R.drawable.name_check2);
                        desc1.setText(R.string.verifing);
                        desc2.setVisibility(View.GONE);
                        go.setText("我知道了");
                        go.setOnClickListener(view -> baseDialog.dismiss());

                    }
                    close.setOnClickListener(view -> {
                        //BaseDialog对象声明成全局，调用方便
                        baseDialog.dismiss();
                    });
                    baseDialog = new BaseDialog(getActivity(), contentView, Gravity.CENTER);
                    baseDialog.show();

                }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null  && App.userMsg().getCustomer().isUserApprovaled()){
                    //认证了
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                }
            }

        });

        //邀请有礼
        invitation.setOnClickListener(v -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.invitation)){
                if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && !App.userMsg().getCustomer().isUserApprovaled()) {
                    //设置自定义视图
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_name_check, null);
                    ImageView head_img=contentView.findViewById(R.id.head_img);
                    MyMediumTextView go=contentView.findViewById(R.id.go);
                    ImageView close=contentView.findViewById(R.id.close);
                    MyNormalTextView desc1=contentView.findViewById(R.id.desc1);
                    MyNormalTextView desc2=contentView.findViewById(R.id.desc2);

                    //没有实名认证
                    if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {
                        desc1.setText(R.string.had_no_veirify);
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.please_goto_verify);
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        go.setOnClickListener(view -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);

                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                    }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                        desc1.setText(R.string.verify_failed);
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText(R.string.verifing);
                        //视图中view点击事件

                        go.setOnClickListener(view -> {
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                    }else  {//审核中

                        head_img.setImageResource(R.drawable.name_check2);
                        desc1.setText(R.string.verifing);
                        desc2.setVisibility(View.GONE);
                        go.setText("我知道了");
                        go.setOnClickListener(view -> baseDialog.dismiss());

                    }
                    close.setOnClickListener(view -> {
                        //BaseDialog对象声明成全局，调用方便
                        baseDialog.dismiss();
                    });
                    baseDialog = new BaseDialog(getActivity(), contentView, Gravity.CENTER);
                    baseDialog.show();

                }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null  && App.userMsg().getCustomer().isUserApprovaled()){
                    //认证了
                    Intent intent = new Intent(getActivity(), InviriteWebActivity.class);
                    startActivity(intent);
                    /*Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                    startActivity(intent);*/
                }
            }

        });

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
                getDataList();
            }
        });
    }

    private void initData() {

        //将以上参数排序，拼接keySecret
        HashMap hashMap = ParamsUtils.getParamsMapWithNoId("bannerType", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            //加密
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
            Log.e("bbbb",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getHomeBanner(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<HomeBannerEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<HomeBannerEntity> baseResult) {


                        if (baseResult.getResult_code() == 0) {

                            Log.e("bbbb",baseResult.toString());
                            HomeBannerEntity data = baseResult.getData();
                            List<HomeBannerEntity.BannerListBean> bannerList = data.getBannerList();
                            //设置轮播图
                            ArrayList<String> objects = new ArrayList<>();
                            for (int i = 0; i < bannerList.size(); i++) {
                                objects.add(bannerList.get(i).getBannerImg());
                            }
                            banner.setImages(objects)
                                    .setImageLoader(new ImageLoader() {
                                @Override
                                public void displayImage(Context context, Object path, ImageView imageView) {

                                    Glide.with(context).load((String)path).into(imageView);
                                }
                            })
                                    .setDelayTime(3000)
                                    .setIndicatorGravity(Gravity.CENTER)
                                    .setIndicatorGravity(Gravity.BOTTOM)
                                    .setBannerStyle(BannerConfig.CUSTOM_INDICATOR)
                                    .setOnBannerListener(position -> {
                                        Intent intent = new Intent(getContext(), NormalWebActivity.class);
                                        intent.putExtra("url", bannerList.get(0).getBannerContentUrl());
                                        startActivity(intent);
                                    }).start();
                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                            Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            App.defaultApp().startActivity(intent);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void initView(View inflate) {
        pay_scan = inflate.findViewById(R.id.pay_scan);
        scan_search = inflate.findViewById(R.id.scan_search);
        h5 = inflate.findViewById(R.id.h5);
        rechargr = inflate.findViewById(R.id.rechargr);
        banner = inflate.findViewById(R.id.banner);
        /*banner_img = inflate.findViewById(R.id.banner_img);*/
        sign_in = inflate.findViewById(R.id.sign_in);
        invitation = inflate.findViewById(R.id.invitation);
        rlv_shop = inflate.findViewById(R.id.rlv_shop);
        smart = inflate.findViewById(R.id.smart);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage message) {
            if (message.type == EventMessage.EVENT_QRCODE && message.data != null) {
                onScanPay((String) message.data);
            }
    }



    private void onScanPay(String qrcode) {
            //扫码支付，检查扫到的二维码是否是伊甸城的有效商户
            final String[] codes = qrcode.split("\\$");
           Log.e("sss",codes.toString());
            if (codes.length != 3 || !"edencity".equals(codes[0]) || !"edencity".equals(codes[2])) {
                Intent intent = new Intent(getActivity(), ScanFailedActivity.class);
                intent.putExtra("result",qrcode);
                startActivity(intent);
                return;
            }
            Log.e("sss",codes[1]);
            getStroeDetail(codes[1]);

    }

    @Override
    public void onDestroyView() {
        if (banner!=null){
            banner.stopAutoPlay();
        }

        super.onDestroyView();

    }

    private void getStroeDetail(String qrcode) {
        //将以上参数排序，拼接keySecret
        HashMap hashMap = ParamsUtils.getParamsMapWithNoId2("providerId", qrcode);
        Log.e("result",qrcode);
        if (hashMap .size()!=0){
            String sign = ParamsUtils.getSign(hashMap);

            try {
                //加密
                hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            } catch (Exception e) {
                e.printStackTrace();
            }

            DataService.getHomeService().getShopDetail(hashMap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResult<ShopDetailEntity>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResult<ShopDetailEntity> baseResult) {
                            Log.e("result",baseResult.toString());
                            if (baseResult.getResult_code() == 0 ) {
                                if (baseResult.getData()!=null &&
                                        baseResult.getData().getProviderDetail()!=null){
                                    ((MainActivity) getActivity()).start
                                            (ScanPayFragment.newInstance(baseResult.getData().getProviderDetail().getProviderId(),
                                                    baseResult.getData().getProviderDetail().getStoreName(),
                                                    baseResult.getData().getProviderDetail().getStoreFacadeImg()));
                                } else if (baseResult.getResult_code()== -3){
                                    AdiUtils.loginOut();
                                }else{
                                    AdiUtils.showToast("没有扫描到商家");
                                    Intent intent = new Intent(getActivity(), ScanFailedActivity.class);
                                    intent.putExtra("result",qrcode);
                                    startActivity(intent);
                                    ParamsUtils.sign = 0;
                                }
                            }else{
                                AdiUtils.showToast(baseResult.getResult_msg());
                                Intent intent = new Intent(getActivity(), ScanFailedActivity.class);
                                intent.putExtra("result",qrcode);
                                startActivity(intent);
                                ParamsUtils.sign = 0;
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("result",e.getMessage());
                            Intent intent = new Intent(getActivity(), ScanFailedActivity.class);
                            intent.putExtra("result",qrcode);
                            startActivity(intent);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

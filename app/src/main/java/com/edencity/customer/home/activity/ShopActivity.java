package com.edencity.customer.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edencity.customer.App;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;

import com.xw.banner.loader.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.edencity.customer.R;

import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;

import com.edencity.customer.custum.flowlayout.FlowLayout;
import com.edencity.customer.custum.flowlayout.TagAdapter;
import com.edencity.customer.custum.flowlayout.TagFlowLayout;
import com.edencity.customer.data.DataService;

import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.ShopDetailEntity;
import com.edencity.customer.home.adapter.ShopProductList;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShopActivity extends BaseActivity {

    private Banner mVpBanner;

    /**
     * 1
     */

    private TextView mTotalIndex;

    /**
     * /5
     */

    private TextView mAllIndex;
    private TagFlowLayout mTagRlv;
    /**
     * 11:00-14:30 ,
     */
    private MyNormalTextView mBeginTime;

    /**
     * 营业中
     */
    private MyNormalTextView mShopState;
    /**
     * 距地铁呐呐呐，c口步行500米
     */
    private TextView mDistanceTrain;
    /**
     * 我是商家地址地址地址地址我是商家地址
     */
    private MyMediumTextView mShopAddress;
    /**
     * 我是商家名称
     */
    private MyMediumTextView mShopName;
    private RecyclerView mRlvPhoto;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    private List<String> mList = new ArrayList<>();
    private List<String> mLabelList = new ArrayList<>();
    private List<String> mImgList = new ArrayList<>();
    private String shopId;
    private BaseDialog baseDialog;
    private SmartRefreshLayout mSmart;
    private double latitude;
    private double longitude;
    private TwoBallRotationProgressBar loading;
    private String pNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
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
        shopId = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_shop);
        initView();
        initLogic();
    }


    private void initLogic() {

        loading.startAnimator();
        HashMap hashmap = ParamsUtils.getParamsMapWithNoId("providerId", shopId);
        String sign = ParamsUtils.getSign(hashmap);

        try {
            hashmap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getShopDetail(hashmap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<ShopDetailEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<ShopDetailEntity> o) {
                        Log.e("sign",o.toString());
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }
                        if (o.getResult_code()==0 && o.getData()!=null){
                            ShopDetailEntity.ProviderDetailBean bean = o.getData().getProviderDetail();
                            mShopName.setText(bean.getStoreName()==null?"":bean.getStoreName());
                            mBeginTime.setText(bean.getBusinessTime()==null?"":bean.getBusinessTime());
                            mShopAddress.setText(bean.getStoreDetailAddress()==null?"":bean.getStoreDetailAddress());
                            mDistanceTrain.setText(bean.getStoreAddressTag()==null?"":bean.getStoreAddressTag());
                            mShopState.setText(bean.getBusinessStatusName()==null?"":bean.getBusinessStatusName());

                            pNum = "4006789038";
                            //经纬度

                            latitude = bean.getStoreLatitude();
                            longitude = bean .getStoreLongitude();
                            String installationService = bean.getInstallationService();

                            //设施条件标签列表
                            if (installationService!=null && !installationService.equals("")){

                                if (installationService.contains(",")){
                                    mLabelList.clear();
                                    String[] split = installationService.split(",");
                                    for (int i = 0; i < split.length; i++) {
                                        mLabelList.add(split[i]);
                                    }
                                }else {
                                    mLabelList.clear();
                                    mLabelList.add(installationService);
                                }

                                mTagRlv.setAdapter(new TagAdapter<String>(mLabelList) {
                                    @Override
                                    public View getView(FlowLayout parent, int position, String o) {
                                        MyNormalTextView tv = (MyNormalTextView) LayoutInflater.
                                                from(ShopActivity.this).inflate(R.layout.item_tag_shop, mTagRlv,
                                                false);
                                        tv.setText(o);
                                        return tv;
                                    }
                                });

                            }
                            //图片列表
                            String detaliImg = bean.getDetailImg();
                            if (detaliImg!=null && !detaliImg.equals("")){

                                if (detaliImg.contains(",")){
                                    mImgList.clear();
                                    String[] split = detaliImg.split(",");
                                    for (int i = 0; i < split.length; i++) {
                                        mImgList.add(split[i]);
                                    }
                                }else {
                                    mImgList.clear();
                                    mImgList.add(detaliImg);
                                }
                                mRlvPhoto.setLayoutManager(new LinearLayoutManager(ShopActivity.this));
                                ShopProductList shopProductListAdapter = new ShopProductList(mImgList,ShopActivity.this);
                                mRlvPhoto.setHasFixedSize(true);
                                mRlvPhoto.setNestedScrollingEnabled(false);
                                mRlvPhoto.setAdapter(shopProductListAdapter);
                                shopProductListAdapter.notifyDataSetChanged();
                            }
                            String displayImg = bean.getDisplayImg();

                            if (displayImg!=null && !displayImg.equals("")){

                                if (displayImg.contains(",")){
                                    mList.clear();
                                    String[] img = displayImg.split(",");
                                    for (int i = 0; i < img.length; i++) {
                                        mList.add(img[i]);
                                    }
                                }else{
                                    mList.clear();
                                    mList.add(displayImg);
                                }

                                //初始化头部轮播图
                                mVpBanner.setImages(mList).setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        String path1 = (String) path;
                                        RequestOptions requestOptions = RequestOptions.centerCropTransform();
                                        if (context!=null && path1!=null && !path1.equals("")){
                                            Glide.with(context).load(path1).apply(requestOptions).into(imageView);
                                        }
                                    }
                                }).setDelayTime(3000)
                                        .isAutoPlay(false)
                                        .setBannerStyle(BannerConfig.CUSTOM_INDICATOR)
                                        .start().setOnPageChangeListener(onPageChangeListener);
                            }
                            mTotalIndex.setText(1+"");
                            mAllIndex.setText("/"+mList.size()+"");

                        } else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                        loading.stopAnimator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.stopAnimator();
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }
                        Log.e("sign",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    private void startBaiduMap() {
        Intent intent = null;
        try {
            intent = Intent.getIntent("intent://map/direction?destination=latlng:"+ latitude
                    + "," + longitude + "|name:&origin=" + "我的位置" + "&mode=driving?ion=" +
                    "我的位置"+ "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isInstallByread("com.baidu.BaiduMap")) {
            startActivity(intent); // 启动调用
        } else {
            Toast.makeText(ShopActivity.this, "没有安装百度地图客户端", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVpBanner == null ){
        }else{
            mVpBanner.stopAutoPlay();
        }


    }

    //移动APP调起Android高德地图方式
    private void startGaoDeMap() {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://navi?sourceApplication=amap&lat=" + latitude
                        + "&lon=" + longitude + "&dev=1&style=0"));
        intent.setPackage("com.autonavi.minimap");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (isInstallByread("com.autonavi.minimap")) {
            startActivity(intent);
        } else {
            Toast.makeText(ShopActivity.this, "没有安装高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }

    private void initView() {
        mVpBanner = (Banner) findViewById(R.id.vp_banner);
        loading =  findViewById(R.id.loading);
        mTotalIndex = (TextView) findViewById(R.id.total_index);
        mAllIndex = (TextView) findViewById(R.id.all_index);
        ImageView mImageView9 = (ImageView) findViewById(R.id.imageView9);
        mTagRlv =  findViewById(R.id.tag_flv);
        mBeginTime = (MyNormalTextView) findViewById(R.id.begin_time);
        mShopState = (MyNormalTextView) findViewById(R.id.shop_state);
        ImageView mNativeLogo = (ImageView) findViewById(R.id.native_logo);
        ImageView mPhone = (ImageView) findViewById(R.id.phone);
        mDistanceTrain = (TextView) findViewById(R.id.distance_train);
        mShopAddress = (MyMediumTextView) findViewById(R.id.shop_address);
        mShopName = (MyMediumTextView) findViewById(R.id.shop_name);
        mSmart =  findViewById(R.id.smart);

        mSmart.setOnRefreshListener(refreshLayout -> initLogic());

        mRlvPhoto = (RecyclerView) findViewById(R.id.rlv_photo);
        ImageView mViewThree = (ImageView) findViewById(R.id.view_three);
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(v -> finish());

        mPhone.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.phone)){
                View inflate = LayoutInflater.from(ShopActivity.this).inflate(R.layout.dialog_call, null);
                MyMediumTextView phone = inflate.findViewById(R.id.phone);
                phone.setText("400-678-9038 ?");
                MyMediumTextView sure = inflate.findViewById(R.id.sure);
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + pNum);
                        intent.setData(data);
                        startActivity(intent);
                        baseDialog.dismiss();

                    }
                });
                MyMediumTextView cancle = inflate.findViewById(R.id.cancle);

                cancle.setOnClickListener(v14 -> baseDialog.dismiss());

                baseDialog = new BaseDialog(ShopActivity.this, inflate, Gravity.CENTER);
                baseDialog.show();
            }
        });

        mNativeLogo.setOnClickListener(v -> {
            View inflate = LayoutInflater.from(ShopActivity.this).inflate(R.layout.ditu, null);
            MyMediumTextView baidu = inflate.findViewById(R.id.baidu);
            RelativeLayout ll = inflate.findViewById(R.id.ll);
            MyMediumTextView gaode =  inflate.findViewById(R.id.gaode);

            baidu.setOnClickListener(v12 -> {
                startBaiduMap();
                baseDialog.dismiss();
            });

            gaode.setOnClickListener(v1 -> {
                startGaoDeMap();
                baseDialog.dismiss();
            });

            ll.setOnClickListener(v13 -> baseDialog.dismiss());
            baseDialog = new BaseDialog(ShopActivity.this, inflate, Gravity.BOTTOM);
            baseDialog.show();

        });
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                mTotalIndex.setText(i+1+"");
                mAllIndex.setText("/"+mList.size()+"");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };
    }
}

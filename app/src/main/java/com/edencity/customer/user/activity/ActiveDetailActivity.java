package com.edencity.customer.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.ActiveProductListEntity;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.CodeEntity;
import com.edencity.customer.entity.ProductDetailEntity;
import com.edencity.customer.home.adapter.ActiveDetailAdapter;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.DisplayInfoUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.SHA1Utils;
import com.umeng.message.PushAgent;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.dtr.zxing.utils.ZXingUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

public class ActiveDetailActivity extends SupportActivity {

    private Banner mBanner;
    private RecyclerView mRlv;
    private List<String> mImgList;
    private List<String> mList;
    private TwoBallRotationProgressBar loading;
    /**
     * 20元
     */
    private MyNormalTextView mItemPrice;
    /**
     * 两个
     */
    private MyNormalTextView mItemTag;
    /**
     * 这里是SD卡哈
     */
    private TextView mItemName;
    /**
     * 所需活跃值
     */
    private MyNormalTextView mItemNeedActive;
    private TwoBallRotationProgressBar mLoading;

    private ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean bean;
    private TextView mGet;
    private int count = 1;
    private BaseDialog baseDialog;
    private BaseDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_active_detail);
        initView();

        Intent intent = getIntent();
        if (intent != null) {
            bean = (ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean)intent.getSerializableExtra("bean");

            if (!"".equals(bean.getGoodsId())) {
                initData(bean.getGoodsId());
            }

            if (!bean.isConvertable() && bean.getCauseOf()!=null && !bean.getCauseOf().equals("")){
                mGet.setEnabled(false);
                mGet.setBackgroundColor(Color.parseColor("#D2D0D0"));
                mGet.setText("领取");
            }else{
                if (bean.getHadGetCount()<bean.getAllCount()){
                    mGet.setEnabled(true);
                    mGet.setBackgroundColor(Color.parseColor("#26A5FF"));
                    mGet.setText("领取");
                    if (bean.getCauseOf().equals("库存不足")){
                        mGet.setText("领取");
                        mGet.setEnabled(false);
                        mGet.setBackgroundColor(Color.parseColor("#D2D0D0"));
                    }
                }else{
                    mGet.setText("领取");
                    mGet.setEnabled(false);
                    mGet.setBackgroundColor(Color.parseColor("#D2D0D0"));
                }
            }

            mItemPrice.setText(bean.getPrice()+"元");
            mItemPrice.setPaintFlags(mItemPrice.getPaintFlags() |
                    Paint.STRIKE_THRU_TEXT_FLAG);
            mItemNeedActive.setText("所需活跃值"+bean.getActivityNeeded()+"点");
            /*mItemTag.setText(tag);*/
            mItemName.setText(bean.getGoodsName());
            mItemTag.setText(bean.getGoodsSpecification()==null?"":bean.getGoodsSpecification());
        }
        initLogic();

    }

    private void initData(String convertedGoodsId) {
        loading.startAnimator();
        HashMap paramsMap = ParamsUtils.getParamsMap("convertedGoodsId", convertedGoodsId);
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getProductDetail(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<ProductDetailEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<ProductDetailEntity> o) {

                        Log.e("oooo", o.toString());
                        if (o.getResult_code() == 0 && o.getData() != null && o.getData().getGoodsDetail() != null) {
                            String detaliImg = o.getData().getGoodsDetail().getGoodsDetail();
                            //图片列表
                            if (detaliImg != null && !detaliImg.equals("")) {

                                if (detaliImg.contains(",")) {
                                    String[] split = detaliImg.split(",");
                                    for (String s : split) {
                                        mImgList.add(s);
                                    }
                                } else {
                                    mImgList.add(detaliImg);
                                }
                                ActiveDetailAdapter shopProductListAdapter = new ActiveDetailAdapter();
                                mRlv.setAdapter(shopProductListAdapter);
                                shopProductListAdapter.addData(mImgList);
                            }
                            String displayImg = o.getData().getGoodsDetail().getGoodsDisplay();
                            if (displayImg != null && !displayImg.equals("")) {
                                if (displayImg.contains(",")) {
                                    String[] img = displayImg.split(",");
                                    for (String s : img) {
                                        mList.add(s);
                                    }
                                } else {
                                    mList.add(displayImg);
                                }

                                //初始化头部轮播图
                                mBanner.setImages(mList).setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        String path1 = (String) path;
                                        RequestOptions requestOptions = RequestOptions.centerCropTransform();
                                        if (context!=null){
                                            Glide.with(context).load(path1).apply(requestOptions).into(imageView);
                                        }
                                    }
                                }).setDelayTime(3000)
                                        .isAutoPlay(true)
                                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                                        .start();
                            }
                        } else if (o.getResult_code() == -3) {
                            AdiUtils.loginOut();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        loading.stopAnimator();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    private void initLogic() {
        mImgList = new ArrayList<>();
        mList = new ArrayList<>();
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.setHasFixedSize(true);
        mRlv.setNestedScrollingEnabled(false);

        mGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGet();
            }
        });
    }

    private void initGet() {
        View inflate = getLayoutInflater().inflate(R.layout.getproductdolog, null);
        //数量
        MyMediumTextView product_count = inflate.findViewById(R.id.product_count);
        TextView tag = inflate.findViewById(R.id.tag);
        product_count.setText(count+"");
        //图片
        ImageView product_iv = inflate.findViewById(R.id.product_iv);
        Glide.with(ActiveDetailActivity.this).load(bean.getListImg()).into(product_iv);
        //价格
        MyNormalTextView product_price = inflate.findViewById(R.id.product_price);
        product_price.setText(bean.getPrice()+"");
        product_price.setPaintFlags(product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //加
        TextView btn_add = inflate.findViewById(R.id.btn_add);
        TextView shop_name = inflate.findViewById(R.id.textView25);
        shop_name.setText(bean.getGoodsName());
        ConstraintLayout null_area = inflate.findViewById(R.id.null_area);
        //生成兑换券
        MyMediumTextView btn_make = inflate.findViewById(R.id.btn_make);
        btn_make.setOnClickListener(v -> {
            baseDialog.dismiss();
            showProductCode(count,bean.getGoodsId());

        });
        null_area.setOnClickListener(v -> baseDialog.dismiss());
        btn_add.setOnClickListener(v -> {
            if (count >= bean.getAllCount()-bean.getHadGetCount()) {
                tag.setVisibility(View.VISIBLE);
                count++;
                btn_make.setEnabled(false);
            } else {
                tag.setVisibility(View.GONE);
                btn_make.setEnabled(true);
                count++;
            }
            product_count.setText(count + "");
        });
        ImageView close = inflate.findViewById(R.id.close);
        //减
        TextView btn_reduce = inflate.findViewById(R.id.btn_reduce);
        btn_reduce.setOnClickListener(v -> {
            if (count <= 1) {

            } else {
                if (count==bean.getAllCount()-bean.getHadGetCount()+1){
                    tag.setVisibility(View.GONE);
                    btn_make.setEnabled(true);
                }
                count--;
            }
            product_count.setText(count + "");
        });

        close.setOnClickListener(v -> baseDialog.dismiss());
        baseDialog = new BaseDialog(ActiveDetailActivity.this, inflate, Gravity.CENTER);
        baseDialog.show();
    }


    private void showProductCode(int count, String goodsId) {

        HashMap hashMap = ParamsUtils.getParamsMap("goodsId", goodsId, "convertedAmount", count);
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
            Log.e("active",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().getGenConvertedQRcode(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<CodeEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<CodeEntity> result) {
                        Log.e("active",result.toString());
                        if (result.getResult_code()==0){
                            CodeEntity data = result.getData();
                            if (bean.getHadGetCount()<bean.getAllCount()){
                                mGet.setEnabled(true);
                                mGet.setBackgroundColor(Color.parseColor("#26A5FF"));
                                int i = bean.getHadGetCount() + count;
                                bean.setHadGetCount(i);
                                if (i >=bean.getAllCount()){
                                    mGet.setText("已领完");
                                    mGet.setEnabled(false);
                                    mGet.setBackgroundColor(Color.parseColor("#D2D0D0"));
                                }else{
                                    mGet.setEnabled(true);
                                    mGet.setText("领取");
                                }
                            }
                            showCodeDialog(data.getConvertedQRCode());
                        }else if (result.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else{
                            AdiUtils.showToast(result.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("active",e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //生成兑换券的弹窗
    private void showCodeDialog(String convertedQRCode) {
        float dimens = ResUtils.getDimens(R.dimen.dp_200);
        int i = DisplayInfoUtils.getInstance().dp2pxInt(dimens);
        Bitmap qrImage = ZXingUtil.createQRImage(convertedQRCode, i, i);

        View inflate = getLayoutInflater().inflate(R.layout.getproduct_code_dialog, null);

        MyMediumTextView dis = inflate.findViewById(R.id.dis);

        ImageView product_code = inflate.findViewById(R.id.product_code);

        product_code.setImageBitmap(qrImage);
        MyMediumTextView my_card = inflate.findViewById(R.id.my_card);
        ConstraintLayout null_area = inflate.findViewById(R.id.null_area);
        ImageView close = inflate.findViewById(R.id.close);

        null_area.setOnClickListener(v -> dialog.dismiss());
        close.setOnClickListener(v -> dialog.dismiss());
        dis.setOnClickListener(v -> dialog.dismiss());
        my_card.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(ActiveDetailActivity.this, MyCertificateActivity.class);
            startActivity(intent);
        });

        dialog = new BaseDialog(ActiveDetailActivity.this, inflate, Gravity.CENTER);
        dialog.show();
    }


    private void initView() {
        ImageView mBack = findViewById(R.id.back);
        mBack.setOnClickListener(v -> finish());
        mBanner = findViewById(R.id.banner);
        mGet = findViewById(R.id.get);
        loading = findViewById(R.id.loading);
        mRlv = findViewById(R.id.rlv);
        mItemPrice = (MyNormalTextView) findViewById(R.id.item_price);
        mItemTag = (MyNormalTextView) findViewById(R.id.item_tag);
        mItemName = (TextView) findViewById(R.id.item_name);
        mItemNeedActive = (MyNormalTextView) findViewById(R.id.item_need_active);
        mLoading = (TwoBallRotationProgressBar) findViewById(R.id.loading);
    }
}

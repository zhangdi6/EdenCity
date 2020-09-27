package com.edencity.customer.user.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import cn.dtr.zxing.utils.ZXingUtil;

import com.edencity.customer.base.NormalWebActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.user.adapter.ActiveProductAdapter2;
import com.edencity.customer.util.ButtonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.ActiveProductListEntity;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.CodeEntity;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.user.adapter.ActiveProductAdapter;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.DisplayInfoUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActiveValueActivity extends BaseActivity {

    private MyMediumTextView mTv_active_value;
    private SmartRefreshLayout mSmart;
    private BaseDialog dialog;
    private ActiveProductAdapter2 activeProductAdapter;

    private BaseDialog baseDialog;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_active_value);
        initView();
    }

    private void getProductList(int type) {
        HashMap hashMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
            Log.e("active",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().getConvertedGoodsList(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<ActiveProductListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<ActiveProductListEntity> result) {
                        Log.e("active",result.toString());
                        ActiveProductListEntity data = result.getData();

                        if (result.getResult_code()==0 && data!=null){
                            List<ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean> goods =
                                    data.getCurrentMembershipConvertedAvailableGoods();
                            Log.e("tagg","99行"+goods.size());
                            List<ActiveProductListEntity.CurrentActivityConvertedAvailableGoodsBean> goods2 =
                                    data.getCurrentActivityConvertedAvailableGoods();
                            Log.e("tagg","102行"+goods2.size());
                            if (goods2.size()==0){
                                for (int j = 0; j < goods.size(); j++) {

                                    ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean bean1 = goods.get(j);
                                        bean1.setConvertable(false);
                                    String membershipNeeded = bean1.getMembershipNeeded();



                                    Log.e("tagggg", "107行" + j);
                                    bean1.setCauseOf("已售完");
                                    bean1.setConvertedFrequency("y");
                                    if (bean1.getMinActivityNeeded() > App.userMsg().getCustomer().getActivityAmount()){
                                        bean1.setCauseOf("活跃值不足");
                                        Log.e("tagggg", "107行" + bean1.getMinActivityNeeded());
                                    }
                                    if (bean1.getAmount()<=0){
                                        bean1.setCauseOf("库存不足");
                                    }
                                    goods.set(j,bean1);
                                }
                            }else{
                                for (int i = 0; i < goods2.size(); i++) {
                                    Log.e("tagg","104行"+i);
                                    ActiveProductListEntity.CurrentActivityConvertedAvailableGoodsBean bean = goods2.get(i);
                                    for (int j = 0; j < goods.size(); j++) {
                                        Log.e("tagg","107行"+j);
                                        ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean bean1 = goods.get(j);
                                        if (bean.getGoodsId().equals(bean1.getGoodsId())){

                                            bean1.setCauseOf("未知");
                                            if (bean.getHadConverted()<bean.getConvertedLimited()){
                                                bean1.setConvertable(true);
                                            }else{
                                                bean1.setConvertable(false);
                                                bean1.setCauseOf("已售完");

                                            }
                                            bean1.setAllCount(bean.getConvertedLimited());
                                            bean1.setHadGetCount(bean.getHadConverted());
                                            bean1.setConvertedFrequency(bean.getConvertedFrequency());
                                            if (bean1.getMinActivityNeeded() > App.userMsg().getCustomer().getActivityAmount()){
                                                bean1.setCauseOf("活跃值不足");
                                            }
                                            if (bean1.getAmount()<=0){
                                                bean1.setCauseOf("库存不足");
                                            }
                                            goods.set(j,bean1);
                                        }else{

                                            if (!goods2.toString().contains(bean1.getGoodsId())){
                                                bean1.setCauseOf("已售完");
                                                bean1.setConvertedFrequency("y");
                                                if (bean1.getMinActivityNeeded() > App.userMsg().getCustomer().getActivityAmount()){
                                                    bean1.setCauseOf("活跃值不足");
                                                    Log.e("tagggg", "107行" + bean1.getMinActivityNeeded());
                                                }
                                                if (bean1.getAmount()<=0){
                                                    bean1.setCauseOf("库存不足");
                                                }
                                                goods.set(j,bean1);
                                            }
                                        }
                                    }

                                }
                            }

                            if (type==1){
                                Log.e("gadd",goods.toString());
                                activeProductAdapter.addData(goods);

                            }else{
                                activeProductAdapter.addNewData(goods);
                            }

                            activeProductAdapter.onItemClick(position -> {
                                Intent intent = new Intent(ActiveValueActivity.this, ActiveDetailActivity.class);
                                intent.putExtra("bean",goods.get(position));
                                /*intent.putExtra("name",goods.get(position).getGoodsName());
                                intent.putExtra("price",goods.get(position).getPrice()+"");
                                intent.putExtra("tag",goods.get(position).getGoodsId());
                                intent.putExtra("allcount",goods.get(position).getGoodsId());
                                intent.putExtra("tag",goods.get(position).getGoodsId());
                                intent.putExtra("tag2",goods.get(position).getGoodsSpecification());
                                intent.putExtra("active",goods.get(position).getActivityNeeded()+"");*/
                                startActivity(intent);
                            });
                            activeProductAdapter.onItemGetClick(position -> {

                                View inflate = getLayoutInflater().inflate(R.layout.getproductdolog, null);
                                //数量
                                MyMediumTextView product_count = inflate.findViewById(R.id.product_count);
                                TextView tag = inflate.findViewById(R.id.tag);
                                product_count.setText(count+"");
                                //图片
                                ImageView product_iv = inflate.findViewById(R.id.product_iv);
                                Glide.with(ActiveValueActivity.this).load(goods.get(position).getListImg()).into(product_iv);
                                //价格
                                MyNormalTextView product_price = inflate.findViewById(R.id.product_price);
                                product_price.setText(goods.get(position).getPrice()+"");
                                product_price.setPaintFlags(product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                //加
                                TextView btn_add = inflate.findViewById(R.id.btn_add);
                                TextView shop_name = inflate.findViewById(R.id.textView25);
                                shop_name.setText(goods.get(position).getGoodsName());
                                ConstraintLayout null_area = inflate.findViewById(R.id.null_area);
                                //生成兑换券
                                MyMediumTextView btn_make = inflate.findViewById(R.id.btn_make);
                                btn_make.setOnClickListener(v -> {
                                    baseDialog.dismiss();
                                    showProductCode(count,goods.get(position).getGoodsId());

                                });
                                null_area.setOnClickListener(v -> baseDialog.dismiss());
                                btn_add.setOnClickListener(v -> {
                                    if (count >= goods.get(position).getAllCount()-goods.get(position).getHadGetCount()) {
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
                                        if (count==goods.get(position).getAllCount()-goods.get(position).getHadGetCount()+1){
                                            tag.setVisibility(View.GONE);
                                            btn_make.setEnabled(true);
                                        }
                                        count--;
                                    }
                                    product_count.setText(count + "");
                                });

                                close.setOnClickListener(v -> baseDialog.dismiss());
                                baseDialog = new BaseDialog(ActiveValueActivity.this, inflate, Gravity.CENTER);
                                baseDialog.show();
                            });
                            if (mSmart.isRefreshing()){
                                mSmart.finishRefresh();
                            }else if (mSmart.isLoading()){
                                mSmart.finishLoadMore();
                            }
                        }else if (result.getResult_code()== -3){
                            AdiUtils.loginOut();
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

    private void initView() {
        RecyclerView mRlv = (RecyclerView) findViewById(R.id.rlv_active_value);
        ImageView mBack = (ImageView) findViewById(R.id.back);

        MyMediumTextView mGetActive = (MyMediumTextView) findViewById(R.id.getactive);
        mBack.setOnClickListener(v -> finish());
        mTv_active_value = (MyMediumTextView) findViewById(R.id.active_value);
        MyMediumTextView mTv_get_history = (MyMediumTextView) findViewById(R.id.get_history);
        mSmart = (SmartRefreshLayout) findViewById(R.id.smart);

        mTv_get_history.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.get_history)){
                Intent intent = new Intent(ActiveValueActivity.this, NormalWebActivity.class);
                intent.putExtra("url","https://epi.edencity.com/getRule.html");
                startActivity(intent);
            }
        });

        mGetActive.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.getactive)){
                Intent intent = new Intent(ActiveValueActivity.this, GetActiveActivity.class);
                startActivity(intent);
            }
        });

        mTv_active_value.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.active_value)){
                Intent intent = new Intent(ActiveValueActivity.this, GetActiveHistoryActivity.class);
                startActivity(intent);
            }
        });
        mRlv.setLayoutManager(new GridLayoutManager(this, 2));
         activeProductAdapter = new ActiveProductAdapter2();
        mRlv.setAdapter(activeProductAdapter);
        mRlv.setHasFixedSize(true);
        mRlv.setNestedScrollingEnabled(false);
        activeProductAdapter.notifyDataSetChanged();

        mSmart.setOnRefreshListener(refreshLayout -> getProductList(1));
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
                            getProductList(1);
                            sysn();
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
            Intent intent = new Intent(ActiveValueActivity.this, MyCertificateActivity.class);
            startActivity(intent);
        });

        dialog = new BaseDialog(ActiveValueActivity.this, inflate, Gravity.CENTER);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sysn();
        getProductList(1);
    }

    private void sysn() {
        DataService.getInstance().syncUser(App.getSp().getString("userId"),App.getSp().getString("ticket"), new IBaseCallBack<UserMsgEntity>() {
            @Override
            public void onSuccess(UserMsgEntity data) {
                App.defaultApp().saveUserMsg(data);
                UserMsgEntity userMsg = App.userMsg();
                int activityAmount = userMsg.getCustomer().getActivityAmount();
                mTv_active_value.setText(activityAmount == 0 ? "0点" : activityAmount + "点");
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}

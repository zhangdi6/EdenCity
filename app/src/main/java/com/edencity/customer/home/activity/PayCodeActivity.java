package com.edencity.customer.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.QrEntity;
import com.edencity.customer.fragment.PayResultFragment2;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.DisplayInfoUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.QRCodeUtil;
import com.edencity.customer.util.SHA1Utils;
import com.umeng.message.PushAgent;

import java.util.HashMap;
import java.util.Timer;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

public class PayCodeActivity extends SupportActivity {


    private Timer mCodeTimer;
    private Timer mPayQueryTimer;

    //服务器生成的二维码
    private String curQrcode;
    private String curTimestamp;
    private CountDownTimer countDownTimer;

    private ImageView mImageQrcode;
    private Bitmap qrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this,Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_pay_code);
        initView();
    }

    private void initView() {
        ImageButton mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        LinearLayout mLl = (LinearLayout) findViewById(R.id.ll);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mImageQrcode = (ImageView) findViewById(R.id.image_qrcode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeCode();
    }

    private void makeCode() {
        HashMap paramsMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getQrCode(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<QrEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<QrEntity> o) {

                        if (o.getResult_code()==0 && o.getData()!=null){
                            QrEntity data = o.getData();
                            curQrcode = data.getQrcode();
                            curTimestamp = data.getStub();
                            getQrcode();
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

    private void getQrcode(){
        if (curQrcode!=null && !curQrcode.equals("")){
             qrImage = QRCodeUtil.createQRCode(curQrcode+"%"+curTimestamp,
                    DisplayInfoUtils.getInstance().dp2pxInt(420),
                    DisplayInfoUtils.getInstance().dp2pxInt(420),
                    null);
            Glide.with(PayCodeActivity.this).load(qrImage).into(mImageQrcode);
            time();
            timeResult();
        }

    }

    private void timeResult() {
        CountDownTimer timerResult = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                checkResult();
            }
        };
        timerResult.start();
    }

    private void checkResult() {
        HashMap paramsMap = ParamsUtils.getParamsMap("timestamp",curTimestamp);
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getResult(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult o) {

                        if (o.getResult_code()==0){
                            loadRootFragment(R.id.ll,PayResultFragment2.newInstance(true,"支付成功",
                                    ""));
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            loadRootFragment(R.id.ll,PayResultFragment2.newInstance(false,"支付失败",
                                    "失败原因："+o.getResult_msg()));
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

    private void time(){
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                makeCode();
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer!=null){
            countDownTimer.cancel();

        }
        if (qrImage != null ){
            qrImage.recycle();
        }
        super.onDestroy();
    }
}

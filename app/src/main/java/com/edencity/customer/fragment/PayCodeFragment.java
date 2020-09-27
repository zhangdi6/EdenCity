package com.edencity.customer.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.edencity.customer.App;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.PayResultEntity;
import com.edencity.customer.entity.QrEntity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.DisplayInfoUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.QRCodeUtil;
import com.edencity.customer.util.SHA1Utils;

import java.util.HashMap;
import java.util.Timer;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayCodeFragment extends BaseFragment2 {


    private Timer mCodeTimer;
    private Timer mPayQueryTimer;

    //服务器生成的二维码
    private String curQrcode;
    private String curTimestamp;
    private CountDownTimer countDownTimer;

    private ImageView mImageQrcode;
    private Bitmap qrImage;
    private CountDownTimer timerResult;

    public static PayCodeFragment newInstance() {
        return new PayCodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StatusBarCompat.changeToLightStatusBar(getActivity());
        View view = inflater.inflate(R.layout.activity_pay_code, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ImageButton mBtnBack = (ImageButton) view.findViewById(R.id.btn_back);
        LinearLayout mLl = (LinearLayout) view.findViewById(R.id.ll);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mImageQrcode = (ImageView) view.findViewById(R.id.image_qrcode);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
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

                        if (o.getResult_code() == 0 && o.getData() != null) {
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

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();

        if (timerResult != null) {
            timerResult.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (qrImage != null) {
            qrImage.recycle();
        }
    }


    private void getQrcode() {
        if (curQrcode != null && !curQrcode.equals("")) {
            qrImage = QRCodeUtil.createQRCode(curQrcode + "$" + curTimestamp,
                    DisplayInfoUtils.getInstance().dp2pxInt(420),
                    DisplayInfoUtils.getInstance().dp2pxInt(420),
                    null);
            Glide.with(getActivity()).load(qrImage).into(mImageQrcode);
            time();
            timeResult();

        }
    }

    private void timeResult() {
        timerResult = new CountDownTimer(3000, 1000) {
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
        HashMap paramsMap = ParamsUtils.getParamsMap("timestamp", curTimestamp);
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
                .subscribe(new Observer<BaseResult<PayResultEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<PayResultEntity> o) {

                        Log.e("aaa", o.toString());
                        if (o.getResult_code() == 0 && o.getData() != null && o.getData().getPayResult()!=null) {
                            pop();
                            ((MainActivity)getActivity()).start(PayResultFragment2.newInstance(true,"支付成功",
                                    ""));
                            timerResult.cancel();
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        } else {
                            timeResult();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("aaa", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void time() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                getQrcode();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onViewItemClicked(View view) {
        if (view.getId() == R.id.btn_back) {
            pop();
        }
    }
}

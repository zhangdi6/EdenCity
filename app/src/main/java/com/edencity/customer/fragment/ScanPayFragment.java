package com.edencity.customer.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.user.activity.UpdatePayPwdActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.Logger;
import com.edencity.customer.util.NetUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import com.fanjun.httpclient.httpcenter.Config;
import com.fanjun.httpclient.httpcenter.HttpCenter;
import com.fanjun.httpclient.httpcenter.Request;
import com.fanjun.httpclient.httpcenter.RequestListener;
import com.fanjun.httpclient.httpcenter.Response;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.edencity.customer.App;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.home.activity.CardRechageActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.R;
import com.edencity.customer.util.StringUtil;
import com.edencity.customer.util.ToastUtil;
import com.edencity.customer.view.LoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanPayFragment extends BaseFragment2 {

    @BindView(R.id.text_store_name)
    TextView storeNameView;
    @BindView(R.id.edit_fee)
    EditText totalFeeEdit;
    @BindView(R.id.image_store)
    ImageView storeImageView;
    @BindView(R.id.btn_submit)
    Button mBtn;

    private String providerId;
    private String storeName;
    private String storePhoto;
    private BaseDialog baseDialog;

    private EditText et_verify_code_1;
    private EditText et_verify_code_2;
    private EditText et_verify_code_3;
    private EditText et_verify_code_4;
    private EditText et_verify_code_5;
    private EditText et_verify_code_6;
    private EditText curEditText;
    private BaseDialog baseDialog2;
    private MyNormalTextView mTag;
    private float totalBalance;

    public ScanPayFragment() {
        // Required empty public constructor
    }

    public static ScanPayFragment newInstance(String providerId,String storeName,String storePhoto){
        ScanPayFragment fragment = new ScanPayFragment();
        Bundle args = new Bundle();
        args.putString("store_name", storeName);
        args.putString("provider_id", providerId);
        args.putString("store_photo", storePhoto);
        fragment.setArguments(args);
        return fragment;
    }
   public static ScanPayFragment newInstance(){

        ScanPayFragment fragment = new ScanPayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeName = getArguments().getString("store_name");
            providerId = getArguments().getString("provider_id");
            storePhoto = getArguments().getString("store_photo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View view= inflater.inflate(R.layout.fragment_scan_pay, container, false);
        ButterKnife.bind(this,view);
        if (storeName!=null){
            storeNameView.setText(storeName);
        }
        if (storePhoto!=null){
            Picasso.with(getContext()).load(storePhoto).into(storeImageView);
        }
        return view;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.changeToLightStatusBar(getActivity());
        totalFeeEdit.requestFocus();
        totalFeeEdit.addTextChangedListener(textWatcher);

    }
    //处理验证码输入
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    totalFeeEdit.setText(s);
                    totalFeeEdit.setSelection(s.length());
                }
            } else {
                if (s.length() > 8) {
                    totalFeeEdit.setText(s.subSequence(0, 8));
                    totalFeeEdit.setSelection(s.length() - 1);
                }
            }

            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                totalFeeEdit.setText(s);
                totalFeeEdit.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    totalFeeEdit.setText(s.subSequence(0, 1));
                    totalFeeEdit.setSelection(1);
                    return;
                }
            }

            if (s.toString().equals("0")
                    || s.toString().equals("0.0")
                    || s.toString().equals("0.00")
                    || s.toString().equals("")
                    || s.toString().equals("0.")){
                mBtn.setEnabled(false);
                mBtn.setBackgroundResource(R.drawable.text_bg_gray);
            }else{
                mBtn.setEnabled(true);
                mBtn.setBackgroundResource(R.drawable.text_bg_blue);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        ParamsUtils.sign = 0;
    }

    @Override
    public void onViewItemClicked(View view) {
        if (view.getId()==R.id.btn_back){
            pop();
        }else if (view.getId()==R.id.btn_submit){
            if (!ButtonUtils.isFastDoubleClick(R.id.btn_submit)){
                totalBalance =  App.userMsg().getAccount().getTotalBalance();
                String s = totalFeeEdit.getText().toString();

                //如果余额不足
                if (Float.parseFloat(s) > totalBalance){
                    initDialogNoMoney(totalBalance+"");
                }else{
                    //如果有支付密码
                    if ( App.userMsg().getCustomer().getPayPassword().equals("true")){
                        //将以上参数排序，拼接keySecret
                        payPassword();

                        //没有支付密码
                    }else{
                        View inflate = getLayoutInflater().inflate(R.layout.dialog_no_paypwd, null);
                        RelativeLayout close = inflate.findViewById(R.id.ll);

                        MyMediumTextView sure = inflate.findViewById(R.id.sure);

                        MyMediumTextView cancle = inflate.findViewById(R.id.cancle);
                        close.setOnClickListener(v -> baseDialog.dismiss());
                        cancle.setOnClickListener(v -> baseDialog.dismiss());

                        sure.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), UpdatePayPwdActivity.class);
                            startActivity(intent);
                            baseDialog.dismiss();
                        });
                        baseDialog = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
                        baseDialog.show();                }
                }
            }
        }
    }

    private void payPassword() {

        View inflate = getLayoutInflater().inflate(R.layout.dialog_pay_pwd, null);
        ImageView close = inflate.findViewById(R.id.close);
        RelativeLayout null_layout = inflate.findViewById(R.id.null_layout);
        et_verify_code_1 = inflate.findViewById(R.id.et_verify_code_1);
        et_verify_code_2 = inflate.findViewById(R.id.et_verify_code_2);
        et_verify_code_3 = inflate.findViewById(R.id.et_verify_code_3);
        et_verify_code_4 = inflate.findViewById(R.id.et_verify_code_4);
        et_verify_code_5 = inflate.findViewById(R.id.et_verify_code_5);
        EditText ee = inflate.findViewById(R.id.ee);
        et_verify_code_6 = inflate.findViewById(R.id.et_verify_code_6);
        EditText et_verify_code_7 = inflate.findViewById(R.id.et_verify_code_7);

        initEditText();
         mTag = inflate.findViewById(R.id.tag_text);
        MyNormalTextView mForget = inflate.findViewById(R.id.forget);
        mForget.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdatePayPwdActivity.class);
            startActivity(intent);
            baseDialog2.dismiss();
        });
        MyMediumTextView price = inflate.findViewById(R.id.price);
        price.setText(totalFeeEdit.getText().toString());
        mTag.setVisibility(View.GONE);
        close.setOnClickListener(v -> baseDialog2.dismiss());

        baseDialog2 = new BaseDialog(getActivity(), inflate, Gravity.BOTTOM);
        baseDialog2.show();
        et_verify_code_1.postDelayed(new Runnable() {
            @Override
            public void run() {
                baseDialog2.showKeyboard(ee);
                et_verify_code_1.requestFocus();
            }
        },300);
    }


    private void initEditText() {
        //处理验证码输入
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0 && curEditText != null) {
                    if (curEditText.getId() == R.id.et_verify_code_1) {
                        et_verify_code_2.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_2) {
                        et_verify_code_3.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_3) {
                        et_verify_code_4.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_4) {
                        et_verify_code_5.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_5) {
                        et_verify_code_6.requestFocus();
                    } else if (curEditText.getId() == R.id.et_verify_code_6) {
                        LoadingDialog.showLoading(getChildFragmentManager());
                        if (NetUtils.isConnected()){
                            if (NetUtils.isNetworkAvailable(getContext())){
                                onVerifyCode(totalFeeEdit.getText().toString());
                            }else{
                                LoadingDialog.hideLoading();
                                AdiUtils.showToast("您当前的网络不可用");
                            }
                        }else{
                            LoadingDialog.hideLoading();
                            AdiUtils.showToast("您当前还未连接网络");
                        }


                    }
                }
            }
        };

        View.OnKeyListener onKeyListener = (view, i, keyEvent) -> {
            if (view instanceof EditText) {
                EditText curEdit = (EditText) view;
                if (i == KeyEvent.KEYCODE_DEL) {
                    if (curEdit.getText().length() > 0) {
                        curEdit.setText(null);
                    } else {
                        if (curEdit.getId() == R.id.et_verify_code_2) {
                            et_verify_code_1.setText(null);
                            et_verify_code_1.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_3) {
                            et_verify_code_2.setText(null);
                            et_verify_code_2.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_4) {
                            et_verify_code_3.setText(null);
                            et_verify_code_3.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_5) {
                            et_verify_code_4.setText(null);
                            et_verify_code_4.requestFocus();
                        } else if (curEdit.getId() == R.id.et_verify_code_6) {
                            et_verify_code_5.setText(null);
                            et_verify_code_5.requestFocus();
                        }
                    }
                } else if (i == KeyEvent.KEYCODE_ENTER) {

                    LoadingDialog.showLoading(getChildFragmentManager());

                    if (NetUtils.isConnected()){
                        if (NetUtils.isNetworkAvailable(getContext())){
                            onVerifyCode(totalFeeEdit.getText().toString());
                        }else{
                            LoadingDialog.hideLoading();
                            AdiUtils.showToast("您当前的网络不可用");
                        }
                    }else{
                        LoadingDialog.hideLoading();
                        AdiUtils.showToast("您当前还未连接网络");
                    }

                }
            }
            return false;
        };

        et_verify_code_1.setOnKeyListener(onKeyListener);
        et_verify_code_2.setOnKeyListener(onKeyListener);
        et_verify_code_3.setOnKeyListener(onKeyListener);
        et_verify_code_4.setOnKeyListener(onKeyListener);
        et_verify_code_5.setOnKeyListener(onKeyListener);
        et_verify_code_6.setOnKeyListener(onKeyListener);

        et_verify_code_1.addTextChangedListener(textWatcher);
        et_verify_code_2.addTextChangedListener(textWatcher);
        et_verify_code_3.addTextChangedListener(textWatcher);
        et_verify_code_4.addTextChangedListener(textWatcher);
        et_verify_code_5.addTextChangedListener(textWatcher);
        et_verify_code_6.addTextChangedListener(textWatcher);

        View.OnFocusChangeListener fouceListener = (view, b) -> {
            if (b && view instanceof EditText) {
                curEditText = (EditText) view;
            }
        };
        et_verify_code_5.setOnFocusChangeListener(fouceListener);
        et_verify_code_6.setOnFocusChangeListener(fouceListener);
        et_verify_code_3.setOnFocusChangeListener(fouceListener);
        et_verify_code_4.setOnFocusChangeListener(fouceListener);
        et_verify_code_1.setOnFocusChangeListener(fouceListener);
        et_verify_code_2.setOnFocusChangeListener(fouceListener);
    }

    private void onVerifyCode(String totalPrice) {
        final String code = et_verify_code_1.getText().toString()
                +et_verify_code_2.getText().toString()
                +et_verify_code_3.getText().toString()
                +et_verify_code_4.getText().toString()
                +et_verify_code_5.getText().toString()
                +et_verify_code_6.getText().toString();
        onPay(code,totalPrice);
    }

    private void initDialogNoMoney(String totalBanlance) {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_no_money, null);
        ImageView close = inflate.findViewById(R.id.close);
        //充值
        TextView recharge = inflate.findViewById(R.id.go_to_recherge);
        //剩余伊甸币

        TextView total_money = inflate.findViewById(R.id.money_total);
        close.setOnClickListener(v -> baseDialog.dismiss());
        total_money.setText("剩余"+totalBanlance+"伊甸果");
        recharge.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CardRechageActivity.class);
            startActivity(intent);
            baseDialog.dismiss();
        });
        baseDialog = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
        baseDialog.show();
    }


    private void onPay(String payPassword , String totalPrice){

        BigDecimal payFee = StringUtil.parseDecimal(totalPrice, BigDecimal.ZERO);
        if (payFee.compareTo(BigDecimal.ZERO)<=0){
            ToastUtil.showToast(getContext(),"请输入有效的支付金额");
            LoadingDialog.hideLoading();
            return;
        }

        HashMap hashMap = ParamsUtils.getParamsMap("dealAmount", totalPrice
                ,"payPassword",payPassword,"providerId",providerId);

        String sign = ParamsUtils.getSign(hashMap);
        try {
            //加密
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getUserService().codePay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {

                        LoadingDialog.hideLoading();
                        if (baseResult.getResult_code() == 0) {

                            mTag.setVisibility(View.GONE);
                            baseDialog2.dismiss();
                            Log.e("bbbb",baseResult.toString());
                            startWithPop(PayResultFragment2.newInstance(true,"支付成功",totalPrice+"伊甸果"));
                            ((MainActivity)getActivity()).hideSoftKeyboard();
                        }else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else if (baseResult.getResult_code()==-1 && baseResult.getResult_msg().equals("支付密码错误")){
                            mTag.setVisibility(View.VISIBLE);
                            et_verify_code_1.requestFocus();
                            et_verify_code_2.setText(null);
                            et_verify_code_1.setText(null);
                            et_verify_code_3.setText(null);
                            et_verify_code_4.setText(null);
                            et_verify_code_5.setText(null);
                            et_verify_code_6.setText(null);

                        }else if (baseResult.getResult_code()==-1 && baseResult.getResult_msg().equals("账户余额不足")){
                            baseDialog2.dismiss();
                            initDialogNoMoney(totalBalance+"");


                        }else {
                            baseDialog2.dismiss();
                            ((MainActivity)getActivity()).hideSoftKeyboard();
                            ((MainActivity) getActivity()).start(PayResultFragment2.
                                    newInstance(false,"支付失败","失败原因："+baseResult.getResult_msg()));

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        baseDialog2.dismiss();
                        LoadingDialog.hideLoading();
                        AdiUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

/*
    private void onPay(String payPassword , String totalPrice){
        baseDialog2.dismiss();
        BigDecimal payFee = StringUtil.parseDecimal(totalPrice, BigDecimal.ZERO);
        if (payFee.compareTo(BigDecimal.ZERO)<=0){
            ToastUtil.showToast(getContext(),"请输入有效的支付金额");
            LoadingDialog.hideLoading();
            return;
        }
        HashMap hashMap = ParamsUtils.getParamsMap("dealAmount", totalPrice
                ,"payPassword",payPassword,"providerId",providerId);

        String sign = ParamsUtils.getSign(hashMap);

        try {
            HttpCenter.POST(Request.ini(Response.class)
                    .url(AppContant.BASE_URL+"/api/customer/pay")
                    //您的请求参数
                    .putParams("dealAmount", totalPrice)
                    .putParams("payPassword", payPassword)
                    .putParams("providerId", providerId)
                    .putParams("userId", App.getSp().getString("userId"))
                    .putParams("ticket", App.getSp().getString("ticket"))
                    .putParams("sign", SHA1Utils.strToSHA1(sign))
                    .putParams("app_id", AppContant.APPID)
                    .putParams("nonce", "1")
                    //默认值为JSON，可供选择的还有FORM
                    .postType(Request.FORM)
                    //回调
                    .requestListener(new RequestListener<Response>() {
                        @Override
                        public void response(Response response) {
                            String result = response.getErrorMsg();
                            int  code = response.getCode();

                            Log.e("resultag",result+code+response.getResult());
                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }


      *//*  HashMap hashMap = ParamsUtils.getParamsMap("dealAmount", totalPrice
                ,"payPassword",payPassword,"providerId",providerId);

        String sign = ParamsUtils.getSign(hashMap);
        try {
            //加密
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }*//*
       *//* DataService.getUserService().codePay(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult baseResult) {

                        LoadingDialog.hideLoading();
                        if (baseResult.getResult_code() == 0) {
                            mTag.setVisibility(View.GONE);
                            Log.e("bbbb",baseResult.toString());
                            startWithPop(PayResultFragment2.newInstance(true,"支付成功",totalPrice+"伊甸果"));
                            ((MainActivity)getActivity()).hideSoftKeyboard();
                        }else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else if (baseResult.getResult_code()==-1 && baseResult.getResult_msg().equals("支付密码错误")){
                            mTag.setVisibility(View.VISIBLE);
                            et_verify_code_1.requestFocus();
                            et_verify_code_2.setText(null);
                            et_verify_code_1.setText(null);
                            et_verify_code_3.setText(null);
                            et_verify_code_4.setText(null);
                            et_verify_code_5.setText(null);
                            et_verify_code_6.setText(null);

                        }else if (baseResult.getResult_code()==-1 && baseResult.getResult_msg().equals("账户余额不足")){
                            initDialogNoMoney(totalBalance+"");

                        }else {
                            ((MainActivity)getActivity()).hideSoftKeyboard();
                            ((MainActivity) getActivity()).start(PayResultFragment2.newInstance(false,"支付失败","失败原因："+baseResult.getResult_msg()));

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoadingDialog.hideLoading();
                        AdiUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });*//*


    }*/


}

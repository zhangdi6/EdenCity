package com.edencity.customer.login.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseEventMsg;
import com.edencity.customer.base.BaseFragment;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.base.NormalWebActivity;
import com.edencity.customer.data.AppContant;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.UserLoginEntity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.DeeSpUtil;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmoutAndVerifyLoginFragment extends BaseFragment implements View.OnClickListener {


    private ImageView back;
    private TextView tab_amout;
    private TextView tab_verify;
    private EditText et_phone2;
    private EditText et_phone;
    private TextView getVerify;
    private EditText et_pwd;
    private EditText et_verify;
    private LinearLayout phone_layout;
    private LinearLayout verify_layoutr;
    private TextView tv_agreeement;
    private TextView tv_agreeement2;

    public AmoutAndVerifyLoginFragment() {
        // Required empty public constructor
    }


    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(getLayoutId(), container, false);
        initView(inflate);
        initLogic();

        return inflate;
    }

    private void initLogic() {
        back.setOnClickListener(this);
        tab_amout.setOnClickListener(this);
        tab_verify.setOnClickListener(this);
        verifyLogin();


    }


    private void verifyLogin() {

        tab_amout.setTextColor(ResUtils.getColor(getActivity(),R.color.blue_nomal));
        tab_verify.setTextColor(Color.parseColor("#999999"));
        tab_amout.setTextSize(21);
        tab_verify.setTextSize(18);
        phone_layout.setVisibility(View.GONE);
        verify_layoutr.setVisibility(View.VISIBLE);

    }

    private void amoutLogin(){
        tab_amout.setTextColor(Color.parseColor("#999999"));
        tab_verify.setTextColor(ResUtils.getColor(getActivity(),R.color.blue_nomal));
        tab_amout.setTextSize(18);
        tab_verify.setTextSize(21);
        phone_layout.setVisibility(View.VISIBLE);
        verify_layoutr.setVisibility(View.GONE);


    }
    private void initView(View inflate) {
        back = inflate.findViewById(R.id.back);
        tab_amout = inflate.findViewById(R.id.tab_amout);
        tab_verify = inflate.findViewById(R.id.tab_verify);
        et_phone = (EditText)inflate.findViewById(R.id.et_phone);
        et_phone2 = (EditText) inflate.findViewById(R.id.et_phone2);
        et_verify = (EditText)inflate.findViewById(R.id.et_verify);
        tv_agreeement = (TextView)inflate.findViewById(R.id.tv_agreeement);
        tv_agreeement2 = (TextView)inflate.findViewById(R.id.tv_agreeement2);
        et_pwd = (EditText)inflate.findViewById(R.id.et_pwd);
        /*    private TextView go_to_register;*/
        TextView login = (TextView) inflate.findViewById(R.id.login);
        login.setOnClickListener(this);

        TextView forget_pwd = (TextView) inflate.findViewById(R.id.forget_pwd);
        forget_pwd.setOnClickListener(this);
        getVerify = (TextView)inflate.findViewById(R.id.getVerify);
        phone_layout = (LinearLayout)inflate.findViewById(R.id.phone_layout);
        verify_layoutr = (LinearLayout)inflate.findViewById(R.id.verify_layout);

        AdiUtils.showDeleteButton(et_phone,1,null);
        AdiUtils.showDeleteButton(et_phone2,1,null);
        AdiUtils.showDeleteButton(et_pwd,2,null);
        AdiUtils.showDeleteButton(et_verify,3,null);

        getVerify.setOnClickListener(v -> {
            if (AdiUtils.isPhone(et_phone2.getText().toString())){
                getVerify();

            }
        });
        tv_agreeement.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.tv_agreeement)){

                Intent intent = new Intent(getActivity(), NormalWebActivity.class);
                intent.putExtra("url",AppContant.WebUrl+"cCommercialAgreement.html");
                startActivity(intent);
            }
        });
        tv_agreeement2.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.tv_agreeement2)){
                Intent intent = new Intent(getActivity(), NormalWebActivity.class);
                intent.putExtra("url",AppContant.WebUrl+"cPrivacyProtocol.html");
                startActivity(intent);
            }
        });
    }

    private void time(){
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getVerify.setText("重新发送("+millisUntilFinished/1000+"秒)");
                getVerify.setEnabled(false);
            }

            @Override
            public void onFinish() {
                getVerify.setText("重新发送");
                getVerify.setEnabled(true);
            }
        };
        countDownTimer.start();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_amout_and_verify_login;
    }

    private void initAmoutLogin(String phone, String password) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("password", password);
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
            Log.e("llll", hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getService().normalLogin(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserLoginEntity> >() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserLoginEntity> baseResult) {
                        Log.e("amout", baseResult.toString());
                        if (baseResult != null && baseResult.getResult_code() == 0) {
                            AdiUtils.showToast("登录成功");
                            DeeSpUtil.getInstance().putString("ticket",baseResult.getData().getTicket());
                            DeeSpUtil.getInstance().putString("userId",baseResult.getData().getUserId());


                            Intent intent1 = new Intent(getActivity(), MainActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);
                            getActivity().finish();
                        } else {
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("amout", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //验证码登录
            case R.id.tab_amout:
                verifyLogin();
                break;
                //手机号登录
            case R.id.tab_verify:
                amoutLogin();
                break;
                //返回
            case R.id.back:

                break;
                //去注册
           /* case R.id.go_to_register:
                Intent intent2 = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent2);
                break;*/
            case R.id.login:
                //账号登录
                if (phone_layout.getVisibility()==View.VISIBLE){
                    if (AdiUtils.isPhone(et_phone.getText().toString())){
                        if (AdiUtils.isPassword(et_pwd.getText().toString())){
                            initAmoutLogin(et_phone.getText().toString(),et_pwd.getText().toString());
                        }
                    }
                }else{
                    //验证码登录
                    if (AdiUtils.isPhone(et_phone2.getText().toString())){
                        if (AdiUtils.isVerify(et_verify.getText().toString())){
                            initVerifyLogin(et_phone2.getText().toString(),et_verify.getText().toString());
                        }
                    }
                }

                break;
                //忘记密码
            case R.id.forget_pwd:
                EventBus.getDefault().post(new BaseEventMsg("","forget","",0));
                break;

        }
    }


    private void getVerify() {
        initVerify("login", et_phone2.getText().toString(), new IBaseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                AdiUtils.showToast(data);
                time();
            }

            @Override
            public void onFail(String msg) {
                AdiUtils.showToast(msg);
            }
        });

    }

    private void initVerifyLogin(String phone, String verify) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        hashMap.put("verificationCode", verify);
        hashMap.put("app_id", AppContant.APPID);
        hashMap.put("nonce", "1");
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getService().verifyLogin(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UserLoginEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UserLoginEntity> baseResult) {
                        Log.e("Amout", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {
                            DeeSpUtil.getInstance().putString("ticket",baseResult.getData().getTicket());
                            DeeSpUtil.getInstance().putString("userId",baseResult.getData().getUserId());
                            AdiUtils.showToast("登录成功");
                            Intent intent1 = new Intent(getActivity(), MainActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);
                            getActivity().finish();
                        } else{
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Amout", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

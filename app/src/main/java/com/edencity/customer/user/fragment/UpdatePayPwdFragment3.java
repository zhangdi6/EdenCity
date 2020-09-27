package com.edencity.customer.user.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePayPwdFragment3 extends BaseFragment {


    private EditText et_verify_code_1;
    private EditText et_verify_code_2;
    private EditText et_verify_code_3;
    private EditText et_verify_code_4;
    private EditText et_verify_code_5;
    private EditText et_verify_code_6;
    private MyNormalTextView mTag;
    private EditText curEditText;
    private String verify;
    private String vCode;

    public UpdatePayPwdFragment3() {
        // Required empty public constructor
    }


    @Override
    protected boolean isNeedToAddBackStack() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(getLayoutId(), container, false);
        Bundle bundle = getArguments();
        if (bundle!=null){
            verify = bundle.getString("verify");
            vCode = bundle.getString("vCode");
        }
        initView(inflate);
        et_verify_code_1.requestFocus();
        return inflate;
    }

    private void initView(View inflate) {
        et_verify_code_1 = inflate.findViewById(R.id.et_verify_code_1);
        et_verify_code_2 = inflate.findViewById(R.id.et_verify_code_2);
        et_verify_code_3 = inflate.findViewById(R.id.et_verify_code_3);
        et_verify_code_4 = inflate.findViewById(R.id.et_verify_code_4);
        et_verify_code_5 = inflate.findViewById(R.id.et_verify_code_5);
        et_verify_code_6 = inflate.findViewById(R.id.et_verify_code_6);
        mTag = inflate.findViewById(R.id.tag_text);
        mTag.setVisibility(View.GONE);

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
                        onVerifyCode();
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
                    onVerifyCode();
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

    private void onVerifyCode() {
        final String code = et_verify_code_1.getText().toString()
                +et_verify_code_2.getText().toString()
                +et_verify_code_3.getText().toString()
                +et_verify_code_4.getText().toString()
                +et_verify_code_5.getText().toString()
                +et_verify_code_6.getText().toString();
        if (code.equals(vCode)){
            update(vCode);
        }else{
            mTag.setVisibility(View.VISIBLE);
            et_verify_code_1.setBackgroundResource(R.drawable.bg_verify_code_edit_red);
            et_verify_code_2.setBackgroundResource(R.drawable.bg_verify_code_edit_red);
            et_verify_code_3.setBackgroundResource(R.drawable.bg_verify_code_edit_red);
            et_verify_code_4.setBackgroundResource(R.drawable.bg_verify_code_edit_red);
            et_verify_code_5.setBackgroundResource(R.drawable.bg_verify_code_edit_red);
            et_verify_code_6.setBackgroundResource(R.drawable.bg_verify_code_edit_red);
            mTag.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onActivityBackPress();

                }
            },1000);
        }
    }

    private void update(String vCode) {

        HashMap paramsMap = ParamsUtils.getParamsMap("phone", App.userMsg().getCustomer().getPhone(),
                "verificationCode",verify,"payPassword",vCode);
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getService().resetPayPwd(paramsMap)
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
                            App.userMsg().getCustomer().setPayPassword("true");
                            getActivity().finish();
                            AdiUtils.showToast("支付密码设置成功");
                        }
                        else {
                            AdiUtils.showToast(o.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AdiUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onActivityBackPress() {
       inti();
        super.onActivityBackPress();
    }

    private void inti() {
        mTag.setVisibility(View.GONE);
        et_verify_code_1.setBackgroundResource(R.drawable.bg_verify_code_edit);
        et_verify_code_2.setBackgroundResource(R.drawable.bg_verify_code_edit);
        et_verify_code_3.setBackgroundResource(R.drawable.bg_verify_code_edit);
        et_verify_code_4.setBackgroundResource(R.drawable.bg_verify_code_edit);
        et_verify_code_5.setBackgroundResource(R.drawable.bg_verify_code_edit);
        et_verify_code_6.setBackgroundResource(R.drawable.bg_verify_code_edit);
        et_verify_code_1.setText("");
        et_verify_code_2.setText("");
        et_verify_code_3.setText("");
        et_verify_code_4.setText("");
        et_verify_code_5.setText("");
        et_verify_code_6.setText("");
        et_verify_code_1.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_pay_pwd_fragment3;
    }

}

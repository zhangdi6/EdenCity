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

import org.greenrobot.eventbus.EventBus;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseEventMsg;
import com.edencity.customer.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePayPwdFragment2 extends BaseFragment {


    private EditText et_verify_code_1;
    private EditText et_verify_code_2;
    private EditText et_verify_code_3;
    private EditText et_verify_code_4;
    private EditText et_verify_code_5;
    private EditText et_verify_code_6;
    private EditText curEditText;
    private String verify;

    public UpdatePayPwdFragment2() {
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


        //处理验证码输入
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0 && curEditText!=null){
                    if (curEditText.getId() == R.id.et_verify_code_1){
                        et_verify_code_2.requestFocus();
                    }else if (curEditText.getId() == R.id.et_verify_code_2){
                        et_verify_code_3.requestFocus();
                    }else if (curEditText.getId() == R.id.et_verify_code_3){
                        et_verify_code_4.requestFocus();
                    }else if (curEditText.getId() == R.id.et_verify_code_4){
                        et_verify_code_5.requestFocus();
                    }else if (curEditText.getId() == R.id.et_verify_code_5){
                        et_verify_code_6.requestFocus();
                    }else if (curEditText.getId() == R.id.et_verify_code_6){
                        onVerifyCode();
                    }
                }
            }
        };

        View.OnKeyListener onKeyListener = (view, i, keyEvent) -> {
            if (view instanceof EditText){
                EditText curEdit = (EditText)view;
                if (i == KeyEvent.KEYCODE_DEL) {
                    if (curEdit.getText().length()>0){
                        curEdit.setText(null);
                    }else {
                        if (curEdit.getId() == R.id.et_verify_code_2){
                            et_verify_code_1.setText(null);
                            et_verify_code_1.requestFocus();
                        }else if (curEdit.getId() == R.id.et_verify_code_3){
                            et_verify_code_2.setText(null);
                            et_verify_code_2.requestFocus();
                        }else if (curEdit.getId() == R.id.et_verify_code_4){
                            et_verify_code_3.setText(null);
                            et_verify_code_3.requestFocus();
                        }else if (curEdit.getId() == R.id.et_verify_code_5){
                            et_verify_code_4.setText(null);
                            et_verify_code_4.requestFocus();
                        }else if (curEdit.getId() == R.id.et_verify_code_6){
                            et_verify_code_5.setText(null);
                            et_verify_code_5.requestFocus();
                        }
                    }
                }else if (i == KeyEvent.KEYCODE_ENTER){
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

        View.OnFocusChangeListener fouceListener= (view, b) -> {
            if (b && view instanceof EditText) {
                curEditText = (EditText)view;
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

        final String vCode = et_verify_code_1.getText().toString()
                +et_verify_code_2.getText().toString()
                +et_verify_code_3.getText().toString()
                +et_verify_code_4.getText().toString()
                +et_verify_code_5.getText().toString()
                +et_verify_code_6.getText().toString();

        EventBus.getDefault().post(new BaseEventMsg("2","pay",vCode,verify));
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
        return R.layout.fragment_update_pay_pwd_fragment2;
    }

}

package com.edencity.customer.view;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.edencity.customer.R;

public class PaymentSelectDialog  extends DialogFragment {


    private Handler.Callback listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.dialog_payment_select, container);

        View.OnClickListener vcl= view1 -> {
            dismissAllowingStateLoss();
            if (listener!=null
                    && (view1.getId() == R.id.layout_weixin || view1.getId() == R.id.layout_alipay)){
                Message msg=Message.obtain();
                msg.obj= view1.getId()==R.id.layout_weixin?"weixin" :"alipay";
                listener.handleMessage(msg);
            }
        };
        view.findViewById(R.id.layout_weixin).setOnClickListener(vcl);
        view.findViewById(R.id.layout_alipay).setOnClickListener(vcl);
        view.findViewById(R.id.btn_close).setOnClickListener(vcl);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM; // 显示在底部
        params.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度填充满屏
        window.setAttributes(params);

        int color = ContextCompat.getColor(getActivity(), android.R.color.transparent);
        window.setBackgroundDrawable(new ColorDrawable(color));
    }

    public void setListener(Handler.Callback listener) {
        this.listener = listener;
    }
}
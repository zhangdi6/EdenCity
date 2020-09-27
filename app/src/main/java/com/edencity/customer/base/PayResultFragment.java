package com.edencity.customer.base;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edencity.customer.R;
import com.edencity.customer.custum.statubar.StatusBarCompat;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayResultFragment extends BaseFragment {


    private int type;

    public PayResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());
        View inflate = inflater.inflate(getLayoutId(), container, false);
        Bundle arguments = getArguments();
        if (arguments!=null){
            type = arguments.getInt("type");
        }
         //type==0.支付成功无密码
        //type==1 充值成功
        //type==2 支付成功有密码
        initView(inflate);
        return inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay_result2;
    }

    private void initView(View inflate) {

        TextView desc = inflate.findViewById(R.id.desc);
        TextView btn_ok = inflate.findViewById(R.id.btn_ok);
        TextView go_to_setpwd = inflate.findViewById(R.id.go_to_setpwd);
        TextView pay_type = inflate.findViewById(R.id.pay_type);

        if (type==0){
            pay_type.setText("支付成功");
            go_to_setpwd.setVisibility(View.VISIBLE);
            go_to_setpwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else if (type==1){
            pay_type.setText("充值成功");
            go_to_setpwd.setVisibility(View.GONE);
        }else if (type==2){
            pay_type.setText("支付成功");
            go_to_setpwd.setVisibility(View.GONE);
        }
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

}

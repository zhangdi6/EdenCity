package com.edencity.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;

public class PayResultFragment2 extends BaseFragment2 {

    @BindView(R.id.image_result)
    ImageView imageResult;
    @BindView(R.id.text_result)
    MyNormalTextView textResult;
    @BindView(R.id.text_price)
    MyMediumTextView text_price;
    @BindView(R.id.ok)
    MyMediumTextView ok;

    private boolean paySuccess;
    private String payResult;
    private boolean firstLaunch=true;
    private String pay_tag;

    public PayResultFragment2() {
    }

    public static PayResultFragment2 newInstance(boolean isSuccess, String payResult ,String tag) {
        PayResultFragment2 fragment = new PayResultFragment2();
        Bundle args = new Bundle();
        args.putBoolean("pay_status", isSuccess);
        if (payResult!=null){
            args.putString("pay_result", payResult);
        }
        if (tag!=null){
            args.putString("pay_tag", tag);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paySuccess = getArguments().getBoolean("pay_status");
            payResult = getArguments().getString("pay_result");
            pay_tag = getArguments().getString("pay_tag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pay_result, container, false);
        ButterKnife.bind(this,view);
        if (paySuccess){
            imageResult.setImageResource(R.drawable.recharge_ok);
            textResult.setText(payResult==null?"支付成功":payResult);

        }else {
            imageResult.setImageResource(R.drawable.failure);
            textResult.setText(payResult==null?"支付失败":payResult);
        }
        if (pay_tag!=null && !pay_tag.equals("")){
            text_price.setText(pay_tag);
            text_price.setVisibility(View.VISIBLE);
        }else{
            text_price.setVisibility(View.GONE);
        }

        initView();
        return view;
    }

    private void initView() {
        ok.setOnClickListener(v -> pop());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (firstLaunch){
            firstLaunch=false;
        }
    }

    /**
     * 响应界面的点击事件
     * @param view 点击的视图
     */
    public void onViewItemClicked(View view) {
        if (view.getId()==R.id.btn_back){
            pop();
        }
    }
}

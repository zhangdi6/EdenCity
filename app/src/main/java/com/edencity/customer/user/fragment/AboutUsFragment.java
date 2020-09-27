package com.edencity.customer.user.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.util.AdiUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends BaseFragment2 {


    @BindView(R.id.btn_back)
    ImageButton mBack;
    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.version)
    TextView mVersion;
    @BindView(R.id.textView5)
    TextView mText;


    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, view);
        initVIew();
        return view;
    }


    private void initVIew() {
        mBack.setOnClickListener(v -> pop());
        mVersion.setText("版本V"+ AdiUtils.getAppVersionName(getContext()));
        mText.setText("        伊甸城 APP ，借助移动终端向用户提供各项功能服务、传递伊甸城品牌价值。目前伊甸城已实现预付卡、支付等生活服务功能。用户可以通过 伊甸城 APP 实现汇员等级提升，获取、消费伊甸果，享受消费折扣，并通过签到功能获得相应积分，进行积分兑换，获取精美礼品。让伊甸城 APP 带您一起探索伊甸城这座乐园!");
    }


}

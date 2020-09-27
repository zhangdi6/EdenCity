package com.edencity.customer.user.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.home.activity.CardRechageActivity;
import com.edencity.customer.home.activity.InviriteWebActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.home.activity.SignInActivity;
import com.edencity.customer.home.fragment.ToBeVipFragment;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.user.activity.GetActiveActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.NetUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetActiveFragment extends BaseFragment2 implements View.OnClickListener {

    private BaseDialog baseDialog;

    public static GetActiveFragment getInstance(){
        return new GetActiveFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_get_active, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.setStatusBarColor(getActivity(), Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(getActivity());
    }

    private void initView(View inflate) {
        ImageView mBack = (ImageView) inflate.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        /**
         * +50点
         */
        MyNormalTextView mRegister = (MyNormalTextView) inflate.findViewById(R.id.register);
        mRegister.setOnClickListener(this);
        /**
         * +30点
         */
        MyNormalTextView mReallyName = (MyNormalTextView) inflate.findViewById(R.id.really_name);
        mReallyName.setOnClickListener(this);
        /**
         * +充值金额的10%的活跃值/次
         */
        MyNormalTextView mRecharge = (MyNormalTextView) inflate.findViewById(R.id.recharge);
        MyMediumTextView tovip =  inflate.findViewById(R.id.tovip);
        if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && App.userMsg().getCustomer().getUserVipLevel()!=null){
            if(App.userMsg().getCustomer().getUserVipLevel().equals("普通汇员")){
                tovip.setText("开通汇员");
            }else{
                tovip.setText("续费汇员");
            }
       }
        mRecharge.setOnClickListener(this);
        /**
         * +消费金额的10%的活跃值/次
         */
        MyNormalTextView mBuy = (MyNormalTextView) inflate.findViewById(R.id.buy);
        mBuy.setOnClickListener(this);

        MyNormalTextView yaoqing = (MyNormalTextView) inflate.findViewById(R.id.yaoqing);
        yaoqing.setOnClickListener(this);
        /**
         * +按签到天数获得对应活跃值
         */
        MyNormalTextView mSignIn = (MyNormalTextView) inflate.findViewById(R.id.sign_in);
        mSignIn.setOnClickListener(this);
        /**
         * +60点
         */
        MyNormalTextView mToBeVip = (MyNormalTextView) inflate.findViewById(R.id.to_be_vip);
        mToBeVip.setOnClickListener(this);
        /**
         * +10点/次
         */
        MyNormalTextView mGetProduct = (MyNormalTextView) inflate.findViewById(R.id.get_product);
        mGetProduct.setOnClickListener(this);
        /**
         * +5点/次，意见被采纳后另赠30点活跃值
         */
        MyNormalTextView mFeedback = (MyNormalTextView) inflate.findViewById(R.id.feedback);
        mFeedback.setOnClickListener(this);
        SmartRefreshLayout mSmart = (SmartRefreshLayout) inflate.findViewById(R.id.smart);
        mSmart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                getActivity().finish();
                break;
            //实名认证
            case R.id.really_name:
                break;
            //充值
            case R.id.recharge:
                Intent intent = new Intent(getContext(), CardRechageActivity.class);
                startActivity(intent);
                break;
            //消费
            case R.id.buy:
                Intent intent3 = new Intent(getContext(), MainActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                break;
            //签到
            case R.id.sign_in:
                Intent intent2 = new Intent(getContext(), SignInActivity.class);
                startActivity(intent2);
                break;
                //邀请
            case R.id.yaoqing:
                if (!NetUtils.isNetworkAvailable(getActivity())){
                    AdiUtils.showToast("您的网络状态很差，请检查网络再试");
                }else {
                    if (!ButtonUtils.isFastDoubleClick(R.id.yaoqing)){
                        if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && !App.userMsg().getCustomer().isUserApprovaled()) {
                            //设置自定义视图
                            View contentView = getLayoutInflater().inflate(R.layout.dialog_name_check, null);
                            ImageView head_img=contentView.findViewById(R.id.head_img);
                            MyMediumTextView go=contentView.findViewById(R.id.go);
                            ImageView close=contentView.findViewById(R.id.close);
                            MyNormalTextView desc1=contentView.findViewById(R.id.desc1);
                            MyNormalTextView desc2=contentView.findViewById(R.id.desc2);

                            //没有实名认证
                            if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {
                                desc1.setText(R.string.had_no_veirify);
                                desc2.setVisibility(View.VISIBLE);
                                desc2.setText(R.string.please_goto_verify);
                                head_img.setImageResource(R.drawable.name_check1);
                                go.setText("去认证>");
                                go.setOnClickListener(view -> {
                                    Intent intent1 = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent1);
                                    baseDialog.dismiss();
                                });
                            }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                                desc1.setText(R.string.verify_failed);
                                head_img.setImageResource(R.drawable.name_check1);
                                go.setText("去认证>");
                                desc2.setVisibility(View.VISIBLE);
                                desc2.setText(R.string.verifing);
                                //视图中view点击事件

                                go.setOnClickListener(view -> {
                                    Intent intent1 = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent1);
                                    baseDialog.dismiss();
                                });
                            }else  {//审核中

                                head_img.setImageResource(R.drawable.name_check2);
                                desc1.setText(R.string.verifing);
                                desc2.setVisibility(View.GONE);
                                go.setText("我知道了");
                                go.setOnClickListener(view -> baseDialog.dismiss());

                            }
                            close.setOnClickListener(view -> {
                                //BaseDialog对象声明成全局，调用方便
                                baseDialog.dismiss();
                            });
                            baseDialog = new BaseDialog(getActivity(), contentView, Gravity.CENTER);
                            baseDialog.show();

                        }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null  && App.userMsg().getCustomer().isUserApprovaled()){
                            //认证了
                            Intent intent1 = new Intent(getActivity(), InviriteWebActivity.class);
                            startActivity(intent1);
                        }
                    }
                };
                break;
            //开通会员
            case R.id.to_be_vip:

                start(ToBeVipFragment.getInstance(1));
                break;
            //领取活跃值商品
            case R.id.get_product:
                getActivity().finish();
                break;
            //意见反馈
            case R.id.feedback:
                start(FeedbackFragment.newInstance(1));
                break;

        }
    }
}

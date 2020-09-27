package com.edencity.customer.home.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.user.fragment.IdentityVerifyFragment;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.DeeSpUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.squareup.picasso.Picasso;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.pojo.Customer;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VipFragment extends BaseFragment2 {


    //通知
    private MyNormalTextView mNatification;
    //立即续费
    private MyMediumTextView mGoOnMoney;
    //头像
    private CircleImageView mHeader_img;
    //昵称
    private MyMediumTextView mUserName;
    //有效期至
    private MyNormalTextView mDate;
    //vip等级
    private MyNormalTextView vip_top;
    private String userVipLevel;
    private BaseDialog baseDialog;
    private MyNormalTextView btn_go;
    private MyMediumTextView tv_go;

    public VipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_vip, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.cancelLightStatusBar(getActivity());
        String userId = DeeSpUtil.getInstance().getString("userId");
        String ticket = DeeSpUtil.getInstance().getString("ticket");
        Log.e("ppp",userId+"--"+ticket);
        DataService.getInstance().syncUser(userId,
                ticket, new IBaseCallBack<UserMsgEntity>() {
                    @Override
                    public void onSuccess(UserMsgEntity data) {
                        Log.d("splash", data.toString());
                        App.defaultApp().saveUserMsg(data);
                        if (data.getCustomer() != null && data.getCustomer().getUserVipLevel() != null) {
                            if ("普通汇员".equals(data.getCustomer().getUserVipLevel())) {
                                mNatification.setText("开通付费汇员，充值预付卡将享受更大优惠!");
                                mGoOnMoney.setText("立即开通");
                                tv_go.setText("开通成功立即赠送365伊甸果");
                                btn_go.setText("365.00/年 立即开通");
                            } else {
                                mNatification.setText("续费付费会员，充值预付卡将享受更大优惠!");
                                mGoOnMoney.setText("立即续费");
                                tv_go.setText("续费成功立即赠送365伊甸果");
                                btn_go.setText("365.00/年 立即续费");
                            }
                        }

                        sysnState();
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
    }

    private void initView(View inflate) {
        //会员规则
        MyNormalTextView mTv_rules = inflate.findViewById(R.id.rules);
        SmartRefreshLayout mSmartRefresh = inflate.findViewById(R.id.smart);
        mGoOnMoney = inflate.findViewById(R.id.go_on_money);
        mNatification = inflate.findViewById(R.id.natification);
        btn_go = inflate.findViewById(R.id.btn_go);
        tv_go = inflate.findViewById(R.id.tv_go);
        //开通续费
        RelativeLayout mBtn_goon_recharge = inflate.findViewById(R.id.goon_noney);
        mHeader_img = inflate.findViewById(R.id.image_avatar);
        mUserName = inflate.findViewById(R.id.textView12);
        mDate = inflate.findViewById(R.id.date);
        vip_top = inflate.findViewById(R.id.vip_top);
        //购买历史
        MyNormalTextView mBuy_history = inflate.findViewById(R.id.buy_history);

        //跑马灯
        mNatification.setSelected(true);
        //设置下划线
        mGoOnMoney.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        mGoOnMoney.getPaint().setAntiAlias(true);

        mGoOnMoney.setOnClickListener(v -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.go_on_money)){
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
                        desc1.setText("您还没有实名认证，为保证您的资金安全，请先进行实名认证");
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText("认证通过后即获得30点活跃值，快去认证吧~");
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        go.setOnClickListener(view -> {
                            ((MainActivity)getActivity()).start(IdentityVerifyFragment.newInstance());
                            baseDialog.dismiss();
                        });
                    }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                        desc1.setText("您的实名认证未通过，请重新认证~");
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText("认证通过后即获得30点活跃值，快去认证吧~");
                        //视图中view点击事件
                        go.setOnClickListener(view -> {
                            ((MainActivity)getActivity()).start(IdentityVerifyFragment.newInstance());
                            baseDialog.dismiss();
                        });
                    }else  {//审核中

                        head_img.setImageResource(R.drawable.name_check2);
                        desc1.setText("身份认证中，认证通过才可进行充值及付款哦~");
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
                    ((MainActivity) getActivity()).start(ToBeVipFragment.getInstance(0));
                }
            }

        });

        mBtn_goon_recharge.setOnClickListener(v -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.goon_noney)){
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
                        desc1.setText("您还没有实名认证，为保证您的资金安全，请先进行实名认证");
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText("认证通过后即获得30点活跃值，快去认证吧~");
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        go.setOnClickListener(view -> {
                            ((MainActivity)getActivity()).start(IdentityVerifyFragment.newInstance());
                            baseDialog.dismiss();
                        });
                    }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                        desc1.setText("您的实名认证未通过，请重新认证~");
                        head_img.setImageResource(R.drawable.name_check1);
                        go.setText("去认证>");
                        desc2.setVisibility(View.VISIBLE);
                        desc2.setText("认证通过后即获得30点活跃值，快去认证吧~");
                        //视图中view点击事件
                        go.setOnClickListener(view -> {
                            ((MainActivity)getActivity()).start(IdentityVerifyFragment.newInstance());
                            baseDialog.dismiss();
                        });
                    }else  {//审核中

                        head_img.setImageResource(R.drawable.name_check2);
                        desc1.setText("身份认证中，认证通过才可进行充值及付款哦~");
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
                    ((MainActivity) getActivity()).start(ToBeVipFragment.getInstance(0));
                }
            }


        });

        mTv_rules.setOnClickListener(v -> {

            if (!ButtonUtils.isFastDoubleClick(R.id.rules)){

            }
        });

        mBuy_history.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.buy_history)){
                ((MainActivity) getActivity()).start(BuyHistoryFragment.getInstance());
            }
        }
        );

    }

    private void sysnState() {
        mUserName.setText(App.userMsg().getCustomer().getNickName());
        if (App.userMsg().getCustomer().getPortrait()!=null && !App.userMsg().getCustomer().getPortrait().equals("")){
            Picasso.with(getContext()).load(App.userMsg().getCustomer().getPortrait()).into(mHeader_img);
        }
        if (App.userMsg().getCustomer().getUserApprovalStatus()== Customer.USER_APPROVAL_OK){
            vip_top.setText(App.userMsg().getCustomer().getUserVipLevel());
            mDate.setVisibility(View.VISIBLE);
            mDate.setText("有效期至："+App.userMsg().getCustomer().getMemberEndTimeFormat());
        }else{
            vip_top.setText(App.userMsg().getCustomer().getUserApprovalStatusDesc());
            mDate.setVisibility(View.GONE);
        }
    }

}

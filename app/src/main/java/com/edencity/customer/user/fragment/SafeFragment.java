package com.edencity.customer.user.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.user.activity.UpdateLoginPwdActivity;
import com.edencity.customer.user.activity.UpdatePayPwdActivity;
import com.edencity.customer.util.ResUtils;

public class SafeFragment extends BaseFragment2 {

    @BindView(R.id.text_name_state)
    TextView name_state;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.pay)
    TextView pay;

    public SafeFragment() {
        // Required empty public constructor
    }

    public static SafeFragment newInstance(){
        return new SafeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_safe, container, false);
        ButterKnife.bind(this,view);
        initView();
        /*EventBus.getDefault().register(this);*/
        return view;
    }

    private void initView() {
        UserMsgEntity userMsg = App.userMsg();
        if (userMsg!=null && userMsg.getCustomer()!=null &&
                userMsg.getCustomer().getUserApprovalStatusDesc()!=null){
            if (userMsg.getCustomer().getUserApprovalStatusDesc().equals("已认证")){
                name_state.setCompoundDrawablesWithIntrinsicBounds(ResUtils.getDrawable(R.mipmap.ok),null,
                        null,null);
            }
            name_state.setText(userMsg.getCustomer().getUserApprovalStatusDesc());
        }
        if (userMsg!=null && userMsg.getCustomer()!=null ){
           if (userMsg.getCustomer().getPassword().equals("true")){
               login.setText("修改登录密码");
           }else{
               login.setText("设置登录密码");
           }
            if (userMsg.getCustomer().getPayPassword().equals("true")){
                pay.setText("修改支付密码");
            }else{
                pay.setText("设置支付密码");
            }
        }

    }

    @Override
    public void onViewItemClicked(View view) {
        switch (view.getId()){
            case R.id.btn_back: {
                pop();
            }break;
            //实名认证
            case R.id.text_name_state: {
                if (!App.userMsg().getCustomer().isUserApprovaled()) {
                    //没有实名认证
                    if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {

                        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                        startActivity(intent);

                    } else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){
                       //审核失败
                        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                        startActivity(intent);
                    }else{

                    }
                }else{//认证了

                }
            }break;
            //支付密码
            case R.id.update_pay_pwd: {
                Intent intent = new Intent(getContext(), UpdatePayPwdActivity.class);
                startActivity(intent);
            }break;
            //登录密码
            case R.id.update_login_pwd: {
                Intent intent = new Intent(getContext(), UpdateLoginPwdActivity.class);
                startActivity(intent);
            }break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*EventBus.getDefault().unregister(this);*/
    }
}

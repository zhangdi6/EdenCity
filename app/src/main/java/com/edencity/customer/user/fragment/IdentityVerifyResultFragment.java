package com.edencity.customer.user.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.pojo.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentityVerifyResultFragment extends BaseFragment2 {

    @BindView(R.id.text_result)
    TextView resultText;
    @BindView(R.id.image_result)
    ImageView resultImage;
    @BindView(R.id.btn_verify)
    Button verifyButton;

    public IdentityVerifyResultFragment() {
        // Required empty public constructor
    }

    public static IdentityVerifyResultFragment newInstance(){
        return new IdentityVerifyResultFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_identity_verify_result, container, false);
        ButterKnife.bind(this,view);
        switch (App.userMsg().getCustomer().getUserApprovalStatus()){
            case Customer.USER_APPROVAL_NOT:
                resultText.setText("您还没有进行实名认证");
                verifyButton.setVisibility(View.VISIBLE);
                break;
            case Customer.USER_APPROVAL_OK:
                resultImage.setImageResource(R.drawable.id_verify_success);
                resultText.setText("您还没有进行实名认证");
                break;
            case Customer.USER_APPROVAL_WAIT:
            case Customer.USER_APPROVAL_PROCESS:
                resultImage.setImageResource(R.drawable.id_verify_process);
                resultText.setText("您的实名认证信息正在审核，请稍候");
                break;
            case Customer.USER_APPROVAL_FAIL:
                resultImage.setImageResource(R.drawable.id_verify_fail);
                resultText.setText("您的实名认证未通过："+App.userMsg().getCustomer().getUserApprovalStatusDesc());
                verifyButton.setVisibility(View.VISIBLE);
                break;
        }
        return view;
    }

    @Override
    public void onViewItemClicked(View view) {
        switch (view.getId()){
            case R.id.btn_back: {
                pop();
            }break;
            case R.id.btn_verify: {
                pop();
                start(IdentityVerifyFragment.newInstance());
            }break;
        }
    }

}

package com.edencity.customer.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.IDCardUtil;
import com.edencity.customer.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NameCardFragment extends BaseFragment {

    @BindView(R.id.edit_name)
    EditText mNameView;
    @BindView(R.id.edit_card_no)
    EditText mCardNoView;
    @BindView(R.id.next)
    TextView mNext;
    public NameCardFragment() {
        // Required empty public constructor
    }

    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_name_card;
    }

    private void initView( ) {

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick(R.id.next)){
                    hideSoftKeyboard();
                    final String userName = mNameView.getText().toString().trim();
                    final String idNo = mCardNoView.getText().toString().trim();

                    if (userName.length() < 2 || userName.length() > 5) {
                        ToastUtil.showToast(getContext(), "请输入正确的姓名");
                        return;
                    }
                    if (idNo.length() != 18 || !IDCardUtil.isValid(idNo)) {
                        ToastUtil.showToast(getContext(), "请输入正确的身份证号");
                        return;
                    }

                    ((AuthenticationActivity)getActivity()).changePage(2,userName,
                            idNo);

                }
            }
        });
    }


}

package com.edencity.customer.user.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.App;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.R;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.pojo.EventMessage;
import com.edencity.customer.pojo.FuncResult;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.IDCardUtil;
import com.edencity.customer.util.ToastUtil;
import com.edencity.customer.view.MyAlertDialog;
import com.edencity.customer.view.LoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentityVerifyFragment extends BaseFragment2 {

    @BindView(R.id.edit_name)
    EditText mNameView;
    @BindView(R.id.edit_card_no)
    EditText mCardNoView;
    @BindView(R.id.image_id_front)
    ImageView mFrontImage;
    @BindView(R.id.image_id_back)
    ImageView mBackImage;

    private String mFrontImagePath;
    private String mBackImagePath;


    private boolean mIsFrontImageClicked;

    public IdentityVerifyFragment() {
    }

    public static IdentityVerifyFragment newInstance() {
        return new IdentityVerifyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity_verify, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.changeToLightStatusBar(getActivity());
    }

    @Override
    public void onViewItemClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back: {
                pop();
            }
            break;
           /* case R.id.btn_why_id_verify: {
                //显示使用协议

            }break;*/
            case R.id.image_id_front: {
                mIsFrontImageClicked = true;
                ((MainActivity) getActivity()).getPhoto(3, 2, 1280, 800);
            }
            break;
            case R.id.image_id_back: {
                mIsFrontImageClicked = false;
                ((MainActivity) getActivity()).getPhoto(3, 2, 1280, 800);
            }
            break;
            case R.id.btn_submit: {
                onSubmit();
            }
            break;
        }
    }

    private void onSubmit() {
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

        if (mFrontImagePath == null || mBackImagePath == null) {
            ToastUtil.showToast(getContext(), "请选择身份证照片");
            return;
        }

        LoadingDialog.showLoading(getFragmentManager());

        App.execute(()->{
            final FuncResult fr=App.webService().updateUserApproval(
                    App.getSp().getString("userId"),
                    App.getSp().getString("ticket"),
                    userName,
                    idNo,
                    new File(mFrontImagePath),new File(mBackImagePath));
            getActivity().runOnUiThread(()->{
                LoadingDialog.hideLoading();
                if (fr.code==0){
                    App.userMsg().getCustomer().setUserApproval("1010011411");
                    MyAlertDialog.showAlert(getFragmentManager(),"您的身份信息已经提交成功，请耐心等待审核完成。", dialogInterface -> {
                        pop();
                    });
                }else if (fr.code == -3){
                    AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                    Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.defaultApp().startActivity(intent);
                }else {
                    ToastUtil.showToast(getContext(),fr.msg);
                }
            });
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoSuccess(EventMessage message) {
        try {
            if (message.type == EventMessage.EVENT_PHOTO && message.data != null) {
                Bitmap image = BitmapFactory.decodeFile((String) message.data);
                if (mIsFrontImageClicked) {
                    mFrontImagePath = (String) message.data;
                    mFrontImage.setImageBitmap(image);
                } else {
                    mBackImagePath = (String) message.data;
                    mBackImage.setImageBitmap(image);
                }
            }
        } catch (Exception e) {
            ToastUtil.showToast(getContext(), "处理图像出现错误，请您重试");
        }
    }
}

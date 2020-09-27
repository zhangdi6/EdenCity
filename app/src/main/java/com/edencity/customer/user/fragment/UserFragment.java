package com.edencity.customer.user.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.home.activity.CardActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.App;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.UnReadCountEntity;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.home.activity.CardRechageActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.R;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.user.activity.ActiveValueActivity;
import com.edencity.customer.user.activity.FeedMsgActivity;
import com.edencity.customer.user.activity.MyCertificateActivity;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment2 implements View.OnClickListener {

    @BindView(R.id.user_phone)
    MyMediumTextView mNameTextView;
    @BindView(R.id.text_user_approval_result)
    MyNormalTextView mApproval;
    //充值
    @BindView(R.id.recharge)
    MyNormalTextView mBtn_recharge;
    //活跃值
    @BindView(R.id.active_value)
    MyNormalTextView mActive_value;
    //活跃值下说明
    @BindView(R.id.result_desc)
    MyNormalTextView mResult_desc;

    @BindView(R.id.text_cash)
    MyMediumTextView mCashTextView;

    @BindView(R.id.image_avatar)
    ImageView mAvatarView;
    @BindView(R.id.user_edit_phone)
    ImageView mEdit;

    @BindView(R.id.layout_security)
    LinearLayout mLayout;
    @BindView(R.id.layout_chage_card)
    LinearLayout mChange_card;
    @BindView(R.id.layout_order)
    LinearLayout mOrder;
    @BindView(R.id.layout_feedback)
    LinearLayout mFeedback;
    @BindView(R.id.layout_will_yes)
    LinearLayout mWill_yes;
    @BindView(R.id.layout_about_us)
    LinearLayout aboutUs;

    private String avatarLoaded;
    @BindView(R.id.btn_bill_list)
    ImageView mBtn_setting;
    @BindView(R.id.card)
    RelativeLayout mCard;
    @BindView(R.id.see)
    ImageView mCb_see;
    @BindView(R.id.smart)
    RefreshLayout mSamrt;

    @BindView(R.id.btn_bill_msg)
    ImageView mBtn_msg;
    @BindView(R.id.tag_text)
    TextView mTag;
    private int unReadMessageCount;
    private boolean isCheck = false;
    private BaseDialog baseDialog;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(){
        return new UserFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,view);
        boolean firstLaunch = true;
        initView();
        /*String phone = userMsg.getCustomer().getPhone();
        String replace = phone.replace(phone.substring(3, 7), "****");*/

        return view;

    }

    private void sysnState() {

        UserMsgEntity userMsg = App.userMsg();
        if (userMsg.getCustomer().getUserApprovalStatus()== Customer.USER_APPROVAL_OK){
            mApproval.setText(App.userMsg().getCustomer().getUserVipLevel());
            mResult_desc.setVisibility(View.GONE);
        }else{
            mApproval.setOnClickListener(v -> {
                //审核失败
                if (!ButtonUtils.isFastDoubleClick(R.id.text_user_approval_result)){
                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                    startActivity(intent);
                }

            });
            mApproval.setText(userMsg.getCustomer().getUserApprovalStatusDesc());
            mResult_desc.setVisibility(View.VISIBLE);
        }
        mNameTextView.setText(userMsg.getCustomer().getNickName());
        if (App.userMsg().getCustomer().getPortrait()!=null && !App.userMsg().getCustomer().getPortrait().equals("")){
            Picasso.with(getContext()).load(App.userMsg().getCustomer().getPortrait()).into(mAvatarView);
        }
        mActive_value.setText(userMsg.getCustomer().getActivityAmount()+"点活跃值>");
        if (isCheck){
            mCb_see.setImageResource(R.mipmap.see_open);
            if (App.userMsg()!=null && App.userMsg().getAccount()!=null ){
                    mCashTextView.setText(App.userMsg().getAccount().getTotalBalance()+"");
            }

        }else{
            mCb_see.setImageResource(R.mipmap.see_close);
            mCashTextView.setText("****");

          /*  ArrayList<String> objects = new ArrayList<>();

            String s = "";
            if (objects.size()>0){
                s = "[在线]";
                for (int i = 0; i < objects.size() ; i++) {
                    if (i+1 == objects.size()){
                        s = s + objects.get(i);
                    }else{
                        s = s + objects.get(i) + "/n" + "[在线]";
                    }
                }
            }

            mTag.setText(s);*/
        }
    }

    private void initView() {
        mLayout.setOnClickListener(this);
        mChange_card.setOnClickListener(this);
        mBtn_setting.setOnClickListener(this);
        mCard.setOnClickListener(this);
        mActive_value.setOnClickListener(this);
        mBtn_recharge.setOnClickListener(this);
        mWill_yes.setOnClickListener(this);
        mFeedback.setOnClickListener(this);
        mOrder.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mBtn_msg.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.btn_bill_msg)){
                Intent intent = new Intent(getActivity(), FeedMsgActivity.class);
                intent.putExtra("type",unReadMessageCount);
                startActivity(intent);
            }
        });

        mAvatarView.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.image_avatar)){
                ((MainActivity) getActivity()).start(ProfileFragment.newInstance());
            }
        });

        mCb_see.setOnClickListener(v -> {
            if (isCheck){
                isCheck = false ;
                mCb_see.setImageResource(R.mipmap.see_close);
                mCashTextView.setText("****");
            }else{
                isCheck = true;
                mCb_see.setImageResource(R.mipmap.see_open);
                if (App.userMsg()!=null && App.userMsg().getAccount()!=null ){
                    mCashTextView.setText(App.userMsg().getAccount().getTotalBalance()+"");
                }
            }
        });
    if (isCheck){
        mCb_see.setImageResource(R.mipmap.see_open);
        if (App.userMsg()!=null && App.userMsg().getAccount()!=null ){
            mCashTextView.setText(App.userMsg().getAccount().getTotalBalance()+"");
        }
    }else{

        mCb_see.setImageResource(R.mipmap.see_close);
        mCashTextView.setText("****");
    }

    mSamrt.setOnRefreshListener(new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            init();
        }
    });
    }
  @Override
    public void onSupportVisible() {
        super.onSupportVisible();
      init();
  }

    private void init() {
        DataService.getInstance().syncUser(App.getSp().getString("userId"),
                App.getSp().getString("ticket"), new IBaseCallBack<UserMsgEntity>() {
          @Override
          public void onSuccess(UserMsgEntity data) {
              if (mSamrt.isRefreshing()){
                  mSamrt.finishRefresh();
              }
              Log.d("splash",data.toString());
              App.defaultApp().saveUserMsg(data);
              sysnState();
          }

          @Override
          public void onFail(String msg) {

          }
      });

        getNotReadMsg();
    }

    private void getNotReadMsg() {

        HashMap paramsMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getUserService().getUnReadCount(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<UnReadCountEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<UnReadCountEntity> o) {

                        Log.e("user",o.toString());
                        if (o.getResult_code()==0 && o.getData()!=null){

                             unReadMessageCount = o.getData().getUnReadMessageCount();
                            if (unReadMessageCount!=0){
                                mTag.setVisibility(View.VISIBLE);
                                if (unReadMessageCount > 99){
                                    mTag.setText("99+");
                                }else {
                                    mTag.setText(unReadMessageCount + "");
                                }
                            }else{
                                mTag.setVisibility(View.GONE);

                            }
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("user",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //安全中心
            case R.id.layout_security: {
               /* switch (App.curUser().getUserApprovalStatus()) {
                    case Customer.USER_APPROVAL_NOT:
                        ((MainActivity) getActivity()).start(IdentityVerifyFragment.newInstance());
                        break;
                    case Customer.USER_APPROVAL_OK:
                        ToastUtil.showToast(getContext(), "您已经通过了实名认证");
                        break;
                    case Customer.USER_APPROVAL_WAIT:
                    case Customer.USER_APPROVAL_PROCESS:
                        ToastUtil.showToast(getContext(), "您的实名认证信息正在审核，请稍候");
                        break;
                    case Customer.USER_APPROVAL_FAIL:
                        ((MainActivity) getActivity()).start(IdentityVerifyResultFragment.newInstance());
                        break;

                }*/
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_security)){
                    ((MainActivity) getActivity()).start(SafeFragment.newInstance());
                }

            }
            break;
            //设置
            case R.id.btn_bill_list: {
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_bill_list)){
                    ((MainActivity) getActivity()).start(SettingFragment.newInstance());
                }

            }

            break;
            //我的预付卡
            case R.id.card:
                if (!ButtonUtils.isFastDoubleClick(R.id.card)){

                    Intent intent = new Intent(getContext(), CardActivity.class);
                    startActivity(intent);
                }
                break;
            //活跃值
            case R.id.active_value:
                if (!ButtonUtils.isFastDoubleClick(R.id.active_value)){
                    Intent intent1 = new Intent(getActivity(), ActiveValueActivity.class);
                    startActivity(intent1);
                }
                break;
                //实名认证
            case R.id.text_user_approval_result:
                if (!ButtonUtils.isFastDoubleClick(R.id.text_user_approval_result)){

                }
                break;
                //编辑资料
            case R.id.user_edit_phone:
                if (!ButtonUtils.isFastDoubleClick(R.id.user_edit_phone)){
                    ((MainActivity) getActivity()).start(ProfileFragment.newInstance());
                }

                break;
                //我的兑换券
            case R.id.layout_chage_card:
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_chage_card)){
                    Intent intent2 = new Intent(getActivity(), MyCertificateActivity.class);
                    startActivity(intent2);
                }

                break;
                //意见反馈
            case R.id.layout_feedback:
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_feedback)){
                    ((MainActivity) getActivity()).start(FeedbackFragment.newInstance(0));
                }

                break;
                //我的预订
            case R.id.layout_will_yes:
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_will_yes)){

                }
                break;
            //我的账单
            case R.id.layout_order:
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_order)){
                    ((MainActivity) getActivity()).start(BillFragment.newInstance());
                }

            break;
            //充值
            case R.id.recharge:
                if (!ButtonUtils.isFastDoubleClick(R.id.recharge)){
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
                                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                startActivity(intent);
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
                                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                startActivity(intent);
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
                        Intent intent = new Intent(getActivity(), CardRechageActivity.class);
                        startActivity(intent);
                    }
                }
                break;
                //关于我们
            case R.id.layout_about_us:
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_about_us)){
                    ((MainActivity) getActivity()).start(AboutUsFragment.newInstance());
                }

                break;
        }
    }
}

package com.edencity.customer.user.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.AliPayEntity;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.home.activity.CardRechageActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.pojo.EventMessage;
import com.edencity.customer.pojo.FuncResult;
import com.edencity.customer.user.activity.DetailAddressActivityActivity;
import com.edencity.customer.user.activity.UpdateUserNameActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.DateFormatUtils;
import com.edencity.customer.util.DeeSpUtil;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.RegexUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.SHA1Utils;
import com.edencity.customer.util.ToastUtil;
import com.edencity.customer.view.LoadingDialog;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.squareup.picasso.Picasso;
import com.weigan.loopview.LoopView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment2 {

    @BindView(R.id.profile_image)
    ImageView avatarView;

    @BindView(R.id.text_name)
    TextView nameTextView;
    @BindView(R.id.text_phone)
    TextView phoneTextView;

    @BindView(R.id.text_idcard_no)
    TextView idCardNoTextView;
    @BindView(R.id.text_vip)
    TextView text_vip;
   /* @BindView(R.id.text_sex)
    TextView sexTextView;*/

    @BindView(R.id.text_state)
    TextView nameState;
    @BindView(R.id.text_sex)
    TextView mTextSex;
    @BindView(R.id.layout_sex)
    LinearLayout mLayoutSex;
    @BindView(R.id.text_birthday)
    TextView mTextBirthday;
    @BindView(R.id.layout_date)
    LinearLayout mLayoutDate;
    @BindView(R.id.text_address)
    TextView mTextAddress;
    @BindView(R.id.layout_address)
    LinearLayout mLayoutAddress;
    @BindView(R.id.text_detail_address)
    TextView mTextDetailAddress;
    @BindView(R.id.layout_detail_address)
    LinearLayout mLayoutDetailAddress;
    private View view;
    private BaseDialog sexDialog;
    private String yearStr = "";
    private String monthStr = "";
    private String dayStr = "";
    private BaseDialog timeDialog2;
    private String mBirth = "";
    CityPickerView mPicker=new CityPickerView();
    /*@BindView(R.id.text_birthday)
    TextView birthdayTextView;*/

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        /*Customer user=App.curUser();*/
        UserMsgEntity user = App.userMsg();
        mPicker.init(getContext());
        if (user.getCustomer().getPortrait() != null && user.getCustomer().getPortrait().startsWith("http")) {
            Picasso.with(getContext()).load(user.getCustomer().getPortrait()).into(avatarView);
        }
        if (user.getCustomer().getNickName() != null && user.getCustomer().getNickName().length() > 0) {
            nameTextView.setText(user.getCustomer().getNickName());
        }
        phoneTextView.setText(RegexUtils.hidePhone(user.getCustomer().getPhone()));
       /* if (user.getCustomer().getIdCardNo()!=null && user.getCustomer().getIdCardNo().length()==18){
            idCardNoTextView.setText(user.getCustomer().getIdCardNo().substring(0,6)+"********"+user.getCustomer().getIdCardNo().substring(14));
            //计算性别，年龄和生日
           *//* ageTextView.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(user.idCardNo.substring(6,10))));*//*
            birthdayTextView.setText(user.getCustomer().getIdCardNo().substring(6,14));
            sexTextView.setText(Integer.parseInt(String.valueOf(user.getCustomer().getIdCardNo().charAt(16)))%2==0?"女":"男");
        }*/
        if (user.getCustomer().getGender()!=null){
           mTextSex.setText(user.getCustomer().getGender().equals("1")?"男":"女");
           mTextSex.setTextColor(Color.parseColor("#666666"));
        }
        if (user.getCustomer().getBirthdayFormat()!=null){
            mTextBirthday.setText("".equals(user.getCustomer().getBirthdayFormat())?"请选择":user.getCustomer().getBirthdayFormat());
            mTextBirthday.setTextColor("".equals(user.getCustomer().getBirthdayFormat())?Color.parseColor("#999999"):Color.parseColor("#666666"));
        }
        if (user.getCustomer().getAera()!=null){
            mTextAddress.setText("".equals(user.getCustomer().getAera())?"请选择":user.getCustomer().getAera().replace(",","-"));
            mTextAddress.setTextColor("".equals(user.getCustomer().getAera())?Color.parseColor("#999999"):Color.parseColor("#666666"));

        }


        if (user.getCustomer().getDetailAddress()!=null){
            mTextDetailAddress.setText("".equals(user.getCustomer().getDetailAddress())?"请填写详细地址":user.getCustomer().getDetailAddress());
            mTextDetailAddress.setTextColor(Color.parseColor("#666666"));
        }else{
            mTextDetailAddress.setTextColor(Color.parseColor("#999999"));
            mTextDetailAddress.setText("请填写详细地址");
        }

        if (user.getCustomer().getUserApprovalStatusDesc().equals("已认证")) {

            text_vip.setText(App.userMsg().getCustomer().getUserVipLevel()
            .equals("普通汇员")?"普通汇员":App.userMsg().getCustomer().getUserVipLevel()+"(有效期至"+
                    user.getCustomer().getMemberEndTimeFormat()+")");
            String idCardNo = App.userMsg().getCustomer().getIdCardNo();
            String replace = idCardNo.replace(idCardNo.substring(4, 14), "**********");
            idCardNoTextView.setText(replace);
            nameState.setCompoundDrawablesWithIntrinsicBounds(ResUtils.getDrawable(R.mipmap.ok), null,
                    null, null);

        } else {
            text_vip.setText("暂无");
            idCardNoTextView.setText("暂无");
            nameState.setText(user.getCustomer().getUserApprovalStatusDesc());
            nameState.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    null, null);
        }
        initAddressDialog();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onViewItemClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back: {
                pop();
            }
            break;
            //头像
            case R.id.layout_avatar: {
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_avatar)) {
                    ((MainActivity) getActivity()).getPhoto(1, 1, 200, 200);
                }
            }
            break;
            //昵称
            case R.id.layout_user: {
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_user)) {
                    Intent intent = new Intent(getActivity(), UpdateUserNameActivity.class);
                    if (nameTextView.getText().toString() == null || !nameTextView.getText().toString().equals("")) {
                        intent.putExtra("name", nameTextView.getText().toString().trim());
                    }
                    startActivityForResult(intent, 200);
                }

            }
            break;

            //实名状态
            case R.id.layout_name: {
                if (!ButtonUtils.isFastDoubleClick(R.id.layout_name)) {
                    if (!App.userMsg().getCustomer().isUserApprovaled()) {
                        //没有实名认证
                        if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_NOT) {

                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);

                        } else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL) {
                            //审核失败
                            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    } else {//认证了


                    }
                }

            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 400) {
            nameTextView.setText(data.getStringExtra("namee"));
        }
        if (requestCode==100 && resultCode ==300){
            String address = data.getStringExtra("address");

            mTextDetailAddress.setText(address);
            mTextDetailAddress.setTextColor(Color.parseColor("#333333"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoSuccess(EventMessage message) {
        try {
            if (message.type == EventMessage.EVENT_PHOTO && message.data != null) {
                Bitmap image = BitmapFactory.decodeFile((String) message.data);
                avatarView.setImageBitmap(image);
                LoadingDialog.showLoading(getFragmentManager());
                App.execute(() -> {
                    final FuncResult fr = App.webService().updateUserMsg("portrait", new File((String) message.data),
                            nameTextView.getText().toString().trim());
                    getActivity().runOnUiThread(() -> {
                        LoadingDialog.hideLoading();
                        if (fr.code == 0) {
                           /* App.curUser().userAvatar= (String) fr.data;
                            Picasso.with(getContext()).load((String) fr.data).into(avatarView);*/
                            AdiUtils.showToast("修改成功");
                            Log.e("user", fr.toString());
                        } else if (fr.code == 1) {
                            AdiUtils.showToast(fr.msg);
                            App.defaultApp().saveUserMsg(null);
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else if (fr.code == -3) {
                            AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                            Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            App.defaultApp().startActivity(intent);
                        } else {
                            Log.e("user", fr == null ? "" : fr.toString());
                            ToastUtil.showToast(getContext(), fr.msg == null ? "更新用户信息出错，请重试" : fr.msg);
                        }
                    });
                });
            }
        } catch (Exception e) {
            ToastUtil.showToast(getContext(), "处理图像出现错误，请您重试");
        }
    }

    @OnClick({R.id.layout_sex, R.id.layout_date, R.id.layout_address, R.id.layout_detail_address})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.layout_sex:
                initSexDialog();
                break;
            case R.id.layout_date:
                initBirthDayDialog();
                break;
            case R.id.layout_address:
                initAddressDialog();
                mPicker.showCityPicker();
                break;
            case R.id.layout_detail_address:
                Intent intent = new Intent(getContext(), DetailAddressActivityActivity.class);

                startActivityForResult(intent,100);
                break;
        }
    }

    private void initAddressDialog() {

        CityConfig cityConfig = new CityConfig.Builder()
                .title(" ")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#FFFFFF")//标题栏背景色
                .confirTextColor("#3287F6")//确认按钮文字颜色
                .confirmText("确定")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#333333")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(5)//显示item的数量
                .province(DeeSpUtil.getInstance().getString("province")==null?"山西省":DeeSpUtil.getInstance().getString("province"))//默认显示的省份
                .city(DeeSpUtil.getInstance().getString("city")==null?"晋中市":DeeSpUtil.getInstance().getString("city"))//默认显示省份下面的城市
                .district(DeeSpUtil.getInstance().getString("district")==null?"榆次区":DeeSpUtil.getInstance().getString("district"))//默认显示省市下面的区县数据
                .provinceCyclic(false)//省份滚轮是否可以循环滚动
                .cityCyclic(false)//城市滚轮是否可以循环滚动
                .districtCyclic(false)//区县滚轮是否循环滚动
                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
                .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                .drawShadows(false)//滚轮不显示模糊效果
                .setLineColor("#f5f5f5")//中间横线的颜色
                .setLineHeigh(1)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();

       //设置自定义的属性配置
        mPicker.setConfig(cityConfig);
        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                //省份province
                //城市city
                //地区district
                DeeSpUtil.getInstance().putString("province",province.getName());
                DeeSpUtil.getInstance().putString("city",city.getName());
                DeeSpUtil.getInstance().putString("district",district.getName());

                String address = province.getName() +","+ city.getName() +","+district.getName();
                mTextAddress.setText(province.getName() +"-"+ city.getName() +"-"+district.getName());
                mTextAddress.setTextColor(Color.parseColor("#666666"));
                update("aera",address);
            }

            @Override
            public void onCancel() {

            }
        });

    }

    private void initBirthDayDialog() {
            View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_birth, null);
            TextView cancle = inflate.findViewById(R.id.cancle);
            TextView sure = inflate.findViewById(R.id.sure);
            //年份
            LoopView loop = inflate.findViewById(R.id.loop);
            //月份
            LoopView  loop2 = inflate.findViewById(R.id.loop2);
            //日
            LoopView  loop3 = inflate.findViewById(R.id.loop3);
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> list2 = new ArrayList<>();
            ArrayList<String> list3 = new ArrayList<>();
            sure.setOnClickListener(v -> {
                //点击确定获取到年份loopview下标，再根据下标取集合对应的年份字符
                int selectedItem = loop.getSelectedItem();
                yearStr = list.get(selectedItem);  // yearStr就是2010。2020这样的字符串,以下同理
                int selectedItem2 = loop2.getSelectedItem();
                monthStr = list2.get(selectedItem2);
                int selectedItem3 = loop3.getSelectedItem();
                dayStr = list3.get(selectedItem3);

                //此处我上传了选择的生日,不用管，你也可以在这里上传，如果需要
                mBirth = yearStr+"-"+monthStr+"-"+dayStr;
                update("birthday",mBirth);

                //给textview设置
                mTextBirthday.setText(mBirth);
                mTextBirthday.setTextColor(Color.parseColor("#666666"));
                timeDialog2 .dismiss();
            });

            //年份的loopview不用循环展示
            loop.setNotLoop();
            //从1950开始，到今年2020 ，所有年份都添加到集合里
            for (int i = 1950; i <= 2020; i++) {
                list.add(i+"");
            }

            //一年12个月，如果是单位数的话。要加个0.比如01,02,03月 ，为了方便dateFormat转换
            for (int i = 1; i <=12; i++) {
                if (i < 10){
                    list2.add("0"+i);
                }else{
                    list2.add(i+"");
                }
            }
            //日期，直接每个月都给31天
            for (int i = 1; i <=31; i++) {
                if (i < 10){
                    list3.add("0"+i);
                }else{
                    list3.add(i+"");
                }
            }

            //设置数据
            loop.setItems(list);
            loop2.setItems(list2);
            loop3.setItems(list3);

            //此处我判断了sp里的生日是不是为空，如果为空，则表示第一次进入软件，就给loopview默认从最早开始，如果不为空，则表示之前设置过
        //那这里点击弹窗就展示之前设置好的日期，方便修改
        if (App.userMsg()!=null && App.userMsg().getCustomer()!=null && App.userMsg().getCustomer().getBirthday()!=null){
            long time = App.userMsg().getCustomer().getBirthday().getTime();
            String s = DateFormatUtils.longToDate(time,DateFormatUtils.FORMAT_4);
            String[] split = s.split("-");
            //设置刚展示弹窗时要展示的那一条
            loop2.setInitPosition(list2.indexOf(split[1]));
            loop3.setInitPosition(list3.indexOf(split[2]));
            loop.setInitPosition(list.indexOf(split[0]));
        }


        //监听选择到的某一条条目，并记录
        loop.setListener(index -> yearStr = list.get(index));
            loop2.setListener(index -> monthStr = list2.get(index));
            loop3.setListener(index -> dayStr = list3.get(index));

            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeDialog2.dismiss();
                }
            });


            if (timeDialog2==null){
                timeDialog2 = new BaseDialog(_mActivity, inflate, Gravity.BOTTOM);
            }
            timeDialog2.show();

    }

    private void initSexDialog() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sex, null);
        TextView nvn = inflate.findViewById(R.id.nan);
        TextView nv = inflate.findViewById(R.id.nv);
        TextView cancle = inflate.findViewById(R.id.cancle);


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexDialog.dismiss();
            }
        });

        nvn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update("gender","1");
                sexDialog.dismiss();
                mTextSex.setText("男");
                mTextSex.setTextColor(Color.parseColor("#666666"));
            }
        });
        nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update("gender","2");
                sexDialog.dismiss();
                mTextSex.setText("女");
                mTextSex.setTextColor(Color.parseColor("#666666"));
            }
        });
        if (sexDialog==null){
            sexDialog = new BaseDialog(getActivity(), inflate, Gravity.BOTTOM);
        }
        sexDialog.show();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    private void update(String str1, String str2) {
        HashMap hashMap = ParamsUtils.getParamsMap(str1, str2);
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));

            Log.e("card",hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().editUserDesc(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<AliPayEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<AliPayEntity> baseResult) {

                        Log.e("card", baseResult.toString());
                        if (baseResult.getResult_code() == 0) {

                        } else if (baseResult.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else {
                            AdiUtils.showToast(baseResult.getResult_msg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AdiUtils.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

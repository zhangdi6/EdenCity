package com.edencity.customer.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.DayEntity;
import com.edencity.customer.entity.SignAmoutEntity;
import com.edencity.customer.entity.SignInEntity;
import com.edencity.customer.entity.WeekEntity;
import com.edencity.customer.home.adapter.DayAdapter;
import com.edencity.customer.home.adapter.WeekAdapter;

import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.user.activity.ActiveValueActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.CalenderUtilss;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 260点活跃度
     */
    private TextView mTvActive;
    /**
     * 立即签到
     */
    private MyMediumTextView mBtnSignIn;
    private RecyclerView mRlv_week;
    private Calendar calendar;
    private RecyclerView mRlv_day;
    //当今年
    private int year;
    //当今月
    private int month;
    //今天是周几
    private int dayOfWeek;
    private int dayOfMonth;
    //本月第一天在第一行几列
    private int firstDayOfWeek;
    private DayAdapter dayAdapter;
    private int monthOfDay;
    private int activityAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_sign_in);
         calendar = Calendar.getInstance();
        initView();
        initRlvWeek();
        initRlvDay();
        getSignHistory();

        Log.e("count_year",year+"");
        Log.e("count_month",month+"");
        Log.e("count_day",dayOfMonth+"");
        Log.e("count_fistday",dayOfWeek+"");
        Log.e("count_monthday",monthOfDay+"");

    }

    private void getTotalDayStatu() {

        HashMap paramsMap = ParamsUtils.getParamsMap();
        String sign = ParamsUtils.getSign(paramsMap);
        try {
            paramsMap.put("sign",SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getHomeService().getTatalStatu(paramsMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<SignInEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<SignInEntity> result) {
                       Log.e("sign",result.toString());
                       if (result.getResult_code()==0){
                           if (result.getData().getTodaySignStatus().equals("1")){
                               dayAdapter.sysnState(true,dayOfMonth,firstDayOfWeek);
                               mBtnSignIn.setBackgroundResource(R.drawable.yellow_gray);
                               mBtnSignIn.setEnabled(false);
                               mBtnSignIn.setText("已签到");
                           } else if (result.getResult_code()== -3){
                               AdiUtils.loginOut();
                           }else{
                               dayAdapter.sysnState(false,dayOfMonth,firstDayOfWeek);
                               mBtnSignIn.setBackgroundResource(R.drawable.yellow);
                               mBtnSignIn.setEnabled(true);
                               mBtnSignIn.setText("立即签到");
                           }
                       }else{
                           AdiUtils.showToast(result.getResult_msg());
                       }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("sign",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRlvDay() {
        ArrayList<DayEntity> objects = new ArrayList<>();
        //获取这个月有几天
         monthOfDay = CalenderUtilss.getMonthOfDay(year, month);
        //本月第一天在哪个位置
        calendar.set(Calendar.DAY_OF_MONTH, 1);
         firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < firstDayOfWeek ; i++) {
            objects.add(new DayEntity(0,false,false));
        }
        Log.e("sign",firstDayOfWeek+"---");

        for (int i = 1; i <= monthOfDay; i++) {
            objects.add(new DayEntity(i,false,false));
        }
        mRlv_day.setLayoutManager(new GridLayoutManager(this,7));
         dayAdapter = new DayAdapter();
        dayAdapter.addData(objects);
        mRlv_day.setAdapter(dayAdapter);
         dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    }

    private void initRlvWeek() {
        ArrayList<WeekEntity> objects = new ArrayList<>();
        objects.add(new WeekEntity(1,"日",false));
        objects.add(new WeekEntity(2,"一",false));
        objects.add(new WeekEntity(3,"二",false));
        objects.add(new WeekEntity(4,"三",false));
        objects.add(new WeekEntity(5,"四",false));
        objects.add(new WeekEntity(6,"五",false));
        objects.add(new WeekEntity(7,"六",false));
        mRlv_week.setLayoutManager(new GridLayoutManager(this,7));
        WeekAdapter weekAdapter = new WeekAdapter();
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e("sign",dayOfWeek+"");
        weekAdapter.addData(objects);
        mRlv_week.setAdapter(weekAdapter);
        weekAdapter.check(dayOfWeek);

    }

    private void getSignHistory() {
        HashMap hashMap = ParamsUtils.getParamsMap();
        //将除sign外所有参数an字母顺序排序，末尾加上key
        String sign = ParamsUtils.getSign(hashMap);
        try {
            //加密
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getHomeService().getSigninHistory(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult result) {
                        String s = result.getData().toString();
                        if (result.getResult_code()==0){
                            dayAdapter.sysnState(s,dayOfMonth,year,month,firstDayOfWeek);
                            getTotalDayStatu();
                        } else if (result.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("sign",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        mTvActive = (TextView) findViewById(R.id.tv_active);
        ImageView mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        /**
         * 2020/1
         */
        TextView mTvDate = (TextView) findViewById(R.id.tv_date);
        /**
         * (已连续签到3天)
         */
        MyMediumTextView mTvDayCount = (MyMediumTextView) findViewById(R.id.tv_day_count);
        mBtnSignIn = (MyMediumTextView) findViewById(R.id.btn_sign_in);
        mRlv_week = (RecyclerView) findViewById(R.id.rlv_week);
        mRlv_day = (RecyclerView) findViewById(R.id.rlv_day);
        mBtnSignIn.setOnClickListener(this);

         activityAmount = App.userMsg().getCustomer().getActivityAmount();
        mTvActive.setText(activityAmount+"点活跃值");
         year = calendar.get(Calendar.YEAR);
         month = calendar.get(Calendar.MONTH)+1;
        mTvDate.setText(year+"/"+month);


        mTvActive.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.tv_active)){
                Intent intent = new Intent(SignInActivity.this, ActiveValueActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.btn_sign_in:
                if (!ButtonUtils.isFastDoubleClick(R.id.tv_active)){
                    signin();
                }
                break;
        }
    }

    private void signin() {
        HashMap hashMap = ParamsUtils.getParamsMap();
        //将除sign外所有参数an字母顺序排序，末尾加上key
        String sign = ParamsUtils.getSign(hashMap);
        try {
            //加密
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getHomeService().signIn(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<SignAmoutEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<SignAmoutEntity> result) {
                        Log.d("sign",result.toString());

                        if (result.getResult_code()==0){
                            AdiUtils.showToast("签到成功");
                            dayAdapter.checkToday(dayOfMonth);
                            activityAmount = activityAmount+result.getData().getSignAmount();
                            mTvActive.setText(activityAmount+"点活跃值");
                            mBtnSignIn.setBackgroundResource(R.drawable.yellow_gray);
                            mBtnSignIn.setEnabled(false);
                            mBtnSignIn.setText("已签到");
                        }else if (result.getResult_code()==-1){
                            AdiUtils.showToast("今天已经签到过了哦~请明天再来吧");
                        } else if (result.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("sign",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}

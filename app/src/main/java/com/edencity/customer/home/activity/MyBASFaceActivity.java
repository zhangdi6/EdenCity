package com.edencity.customer.home.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edencity.customer.R;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.icbc.bas.face.acitivity.BASFaceBaseActivity;
import com.icbc.bas.face.base.BASFaceBaseConfig;
import com.icbc.bas.face.base.BASFaceConfig;
import com.icbc.bas.face.base.Result;
import com.icbc.bas.face.utils.I18NUtils;
import com.icbc.bas.face.utils.LOG;
import com.icbc.bas.face.view.CustomViewPager;
import com.icbc.bas.face.view.RoundProgressBar;
import com.icbc.bas.face.view.RoundProgressBarWidthNumber;
import com.libface.icbc.library.FaceInfo;
import com.umeng.message.PushAgent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.cloudwalk.FaceInterface;


import cn.cloudwalk.util.LogUtils;


public class MyBASFaceActivity extends BASFaceBaseActivity {


    private static final int UPDATE_PROGRESS_CODE = 1;
    private boolean mFinishFlag = false;
    // 是否显示图标提示
    protected boolean mIsTipsEnable;
    //是否显示人脸入框提示
    protected boolean mIsFaceTipsEnable;

    //设置语言版本
    //protected int mLiveLanguage;

    //面具图片
    private ImageView mMaskImageView;

    private ImageView mMaskBackImageView;
    //
    private CustomViewPager mViewPager;
    private ArrayList<View> liveViewList;


    protected RelativeLayout mBottomRelativeLayout;

    //活检结果图标
    private ImageView mResultImageView;
    //活检动作图片
    private ImageView mStepImageView;
    //活检动作提示
    private TextView mStepTextView;
    //关闭按钮
    private ImageView mCloseImageView;
    //活检倒计时
    private RoundProgressBarWidthNumber mStepRoundProgressBarWidthNumber;
    //检测结果动画倒计时
    private RoundProgressBar mCircleRoundProgressBar;

    //结果页面布局
    private RelativeLayout mResultRelativeLayout;
    private int progress = 0;
    private Handler mProgressHandler = null;

    private RelativeLayout mRootRelativeLayout;


    private boolean lastHaveFace = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.LOGI("fushui", "bas activity onCreate...");
        PushAgent.getInstance(this).onAppStart();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        StatusBarCompat.changeToLightStatusBar(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setRootLayout() {
        setContentView(R.layout.activity_bascustom_face);
    }

    @Override
    protected void initParameters(){
        super.initParameters();
        mProgressHandler = new ProgressHandler(this);

        Intent intent = getIntent();
        if (intent != null) {
            // 设置是否显示动作提示图标
            mIsTipsEnable = intent.getBooleanExtra(BASFaceConfig.BAS_LIVE_TIPS_ENABLE, true);

            //设置是否显示人脸入框提示
            mIsFaceTipsEnable = intent.getBooleanExtra(BASFaceConfig.BAS_LIVE_FACETIPS_ENABLE,true);

            //设置sdk内部语言
            mLiveLanguage = intent.getIntExtra(BASFaceConfig.BAS_LIVE_LANGUAGE, BASFaceConfig.DEFAULT_LIVE_LANGUAGE);
            LogUtils.LOGI("fushui", " lang = " + mLiveLanguage);


            I18NUtils.changeLanguage(mLiveLanguage, MyBASFaceActivity.this);
            //toSetLanguage(mLiveLanguage);

            //虽然没有直接调用过Config类的setupConfig，但是调用了父类BaseConfig中的setupConfig，子类重写父类的方法，调用父类的方法时会调用子类重写之后的方法，所以可以取指
            LOG.d("[参数] >> 动作的提示图标: %s.", mIsTipsEnable ? "显示" : "隐藏");

            LOG.d("[参数] >> 动作的文本提示图标: %s.", mIsFaceTipsEnable ? "显示" : "隐藏");

            String config = intent.getStringExtra(BASFaceBaseConfig.PARAMS_FROM_INVOKER);
            if(config != null) {
                LOG.d("[参数] >> 全部配置参数为: %s.", config);
            }
        }
    }

    public void finishCheck(boolean isSuccess, Bundle userData) {
        LogUtils.LOGI("fushui", "BASFaceActivity finishCheck...");

        drawTick(isSuccess);
        if(!isSuccess){
            deleteResultFiles();
        }
        if(!mFinishFlag){
            if(mResultIntent == null) {
                mResultIntent = new Intent();
            }
            mResultIntent.putExtra(Result.BAS_RESULT_SUCCESS, isSuccess);
            // 调用方在ILiveCheckCallback实例中添加的返回数据，在这里添加到mResultIntent中
            if(userData != null){
                mResultIntent.putExtra(Result.USER_RESULT_DATA, userData);
            }
            setResult(Activity.RESULT_OK, mResultIntent);
            //showResult();
            // finish();
            mFinishFlag = true;
        }
    }


    @Override
    protected void onMotionUpdate(int index, final int motion){
        super.onMotionUpdate(index, motion);


        currentStep = index;
        View mCurrentLiveView = liveViewList.get(currentStep);
        mStepRoundProgressBarWidthNumber.setVisibility(View.VISIBLE);
        mStepRoundProgressBarWidthNumber.setMax(mMotionTimeOut);
        mStepRoundProgressBarWidthNumber.setProgress(mMotionTimeOut);
        mStepImageView = (ImageView) mCurrentLiveView.findViewById(R.id.cloudwalk_face_step_img);
        mStepTextView = (TextView) mCurrentLiveView.findViewById(R.id.cloudwalk_face_step_tv);
        LogUtils.LOGI("fushui", " index face = " + index +
                " motion22 = " + motion);

        setMotionView(motion);
        mViewPager.setCurrentItem(currentStep, true);
    }



    //初始化UI
    @Override
    protected void initializeView() {
        super.initializeView();
        initViewById();
        initStepViews();
        setUpMask(false);
        //addChildrenView(null);
    }


    private void initViewById(){
        mBottomRelativeLayout = (RelativeLayout) findViewById(R.id.bottom_rl);

        if(!mIsTipsEnable){
            mBottomRelativeLayout.setVisibility(View.INVISIBLE);
        }
        mResultRelativeLayout = (RelativeLayout)findViewById(R.id.rl_result);
        mCircleRoundProgressBar = (RoundProgressBar) findViewById(R.id.pb_circle);
        mMaskImageView = (ImageView)findViewById(R.id.top_iv);
        mResultImageView = (ImageView)findViewById(R.id.iv_result);
        mMaskBackImageView = (ImageView)findViewById(R.id.top_iv_back) ;
        // ViewPager
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mStepRoundProgressBarWidthNumber = (RoundProgressBarWidthNumber) findViewById(R.id.cloudwalk_face_step_procress);
        mStepRoundProgressBarWidthNumber.setTextColor(getResources().getColor(R.color.text_gray));
        mStepRoundProgressBarWidthNumber.setReachedBarColor(getResources().getColor(R.color.text_gray));
        mStepRoundProgressBarWidthNumber.setUnReachedBarColor(getResources().getColor(R.color.text_gray));
        mStepRoundProgressBarWidthNumber.setUnReachedProgressBarHeight(10);
        mStepRoundProgressBarWidthNumber.setReachedProgressBarHeight(5);
        mStepRoundProgressBarWidthNumber.setCircleTextSize(20);

        mCloseImageView = (ImageView)findViewById(R.id.bt_close_detect);
        mCloseImageView .setImageResource(R.drawable.ic_bas_ui_close);
        mCloseImageView .setColorFilter(getResources().getColor(R.color.text_gray));
        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRootRelativeLayout = (RelativeLayout)findViewById(R.id.rl_root_bas);

        int mCircleHasFace = getResources().getColor(R.color.face_circle_green);
        int mCircleNoFace = getResources().getColor(R.color.face_circle_red);


        if(mAlignCountDownEnable){
            mStepRoundProgressBarWidthNumber.setVisibility(View.VISIBLE);
            mStepRoundProgressBarWidthNumber.setMax(mAlignCountDuration);
            //mStepRoundProgressBarWidthNumber.setProgress(mAlignCountDuration);
        }
        //toSetLanguage(mLiveLanguage);
    }


    private void addStepView(View view) {
        liveViewList.add(view);
        totalStep++;
    }


    public void addChildrenView(View view){
        if(mRootRelativeLayout != null){
            mRootRelativeLayout.addView(view);
        }
    }

    private void initStepViews() {

        // viewList
        LayoutInflater lf = getLayoutInflater().from(this);
        liveViewList = new ArrayList<View>();
        View view;
        // 检测人脸item
        view = lf.inflate(R.layout.cloudwalk_layout_facedect_bas_step_start, null);
        addStepView(view);
        // 活体item
        int size = execLiveList.size();
        for (int i = 0; i < size; i++) {
            view = lf.inflate(R.layout.cloudwalk_layout_facedect_bas_step, null);
            addStepView(view);
        }

        //
        LiveViewPagerAdapter viewPagerAdapter = new LiveViewPagerAdapter(liveViewList);
        mViewPager.setAdapter(viewPagerAdapter);

    }

    private class LiveViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public LiveViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    private void setMotionView(int motion){
        switch (motion) {
            case FaceInterface.LivessType.LIVESS_HEAD_LEFT:
                mStepImageView.setImageResource(R.drawable.cloudwalk_left_anim);
                mStepTextView.setText(R.string.cloudwalk_live_headleft);
                //活检动作的动画
                AnimationDrawable animationDrawable = (AnimationDrawable) mStepImageView.getDrawable();
                animationDrawable.start();

                break;
            case FaceInterface.LivessType.LIVESS_HEAD_RIGHT:
                mStepImageView.setImageResource(R.drawable.cloudwalk_right_anim);
                mStepTextView.setText(R.string.cloudwalk_live_headright);
                animationDrawable = (AnimationDrawable) mStepImageView.getDrawable();
                animationDrawable.start();

                break;
            case FaceInterface.LivessType.LIVESS_MOUTH:
                mStepImageView.setImageResource(R.drawable.cloudwalk_mouth_anim);
                mStepTextView.setText(R.string.cloudwalk_live_mouth);
                animationDrawable = (AnimationDrawable) mStepImageView.getDrawable();
                animationDrawable.start();

                break;
            case FaceInterface.LivessType.LIVESS_EYE:
                mStepImageView.setImageResource(R.drawable.cloudwalk_eye_anim);
                mStepTextView.setText(R.string.cloudwalk_live_eye);
                animationDrawable = (AnimationDrawable) mStepImageView.getDrawable();
                animationDrawable.start();

                break;
            default:
                break;
        }
    }





    @Override
    protected void onResume() {
        LogUtils.LOGI("fushui", "bas activity onResume...");
        lastHaveFace = false;
        if(mStepRoundProgressBarWidthNumber != null && !mAlignCountDownEnable){
            mStepRoundProgressBarWidthNumber.setVisibility(View.GONE);
        }
        super.onResume();

    }


    @Override
    protected void stopDetect() {
        LogUtils.LOGI("fushui ", "base111 onPause.");
        //super.stopDetect();

//        setVisibility(mCloseImageView, View.INVISIBLE);
//        setVisibility(mStepImageView, View.INVISIBLE);
//        setVisibility(mStepTextView, View.INVISIBLE);
    }


    private void showResult(){
        LogUtils.LOGI("fushui", "showResult...");
        setVisibility(mBottomRelativeLayout, View.INVISIBLE);
        setVisibility(mCwDetectPreview, View.INVISIBLE);
        setVisibility(mMaskImageView, View.INVISIBLE);
        setVisibility(mMaskBackImageView, View.INVISIBLE);
        setVisibility(mCloseImageView, View.INVISIBLE);
        //setVisibility(mStepImageView, View.INVISIBLE);
        //setVisibility(mStepTextView, View.INVISIBLE);
        setVisibility(mStepRoundProgressBarWidthNumber, View.INVISIBLE);
    }


    protected void drawTick(final boolean isTick) {
        showPreview(false);
        LogUtils.LOGI("fushui", "drawTick..." + isTick);
        setVisibility(mResultRelativeLayout, View.VISIBLE);
        if(isTick){
            if(mResultImageView != null){
                mResultImageView.setImageResource(R.drawable.cloudwalk_gou);
            }
            if(mCircleRoundProgressBar != null){
                mCircleRoundProgressBar.setArcColor(getResources().getColor(R.color.face_result_ok));
            }
        }else{
            if(mResultImageView != null){
                mResultImageView.setImageResource(R.drawable.cloudwalk_fail);
            }
            if(mCircleRoundProgressBar != null){
                mCircleRoundProgressBar.setArcColor(getResources().getColor(R.color.face_result_fail));
            }
        }
        changeCircle();
    }


    private void changeCircle() {
        if(mCircleRoundProgressBar != null){
            mCircleRoundProgressBar.setMax(100);
        }
        progress--;

        if(mProgressHandler != null){
            mProgressHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS_CODE, 500);
        }

    }



    static class ProgressHandler extends Handler{
        private final WeakReference<MyBASFaceActivity> mActivity;

        public ProgressHandler(MyBASFaceActivity activity) {
            mActivity = new WeakReference<MyBASFaceActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MyBASFaceActivity activity = mActivity.get();
            if (activity == null) {
                return;
            }

            if (msg.what == 1) {
                activity.mCircleRoundProgressBar.setProgress(activity.progress);
                if (Math.abs(activity.progress) <= activity.mCircleRoundProgressBar.getMax()) {
                    activity.progress = activity.progress - 2;
                    activity.mProgressHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS_CODE, 5);
                }else{
                    activity.finish();
                }
            }
        }
    }


    @Override
    protected void onMotionError(int errorCode) {
        super.onMotionError(errorCode);
        LogUtils.LOGI("fushui", "errorCode222 " + errorCode);


//        //系统，设备的异常直接finish掉当前页面
//        if(LiveCheckError.BAS_ERROR_UNSUPPORTED_DEVICE == errorCode){
//            finish();
//            return;
//        }
        switch(mCurrentRotation){
            case Surface.ROTATION_0:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
        }

        //finishCheck(false);
    }

    //兼容旧接口，原有博宏的倒计时为float类型，所以这里需要转换一次
    @Override
    protected void onMotionCountDownTick(float progress){
        super.onMotionCountDownTick(progress);
        LogUtils.LOGI("fushui", " progress111 " + progress);
        mStepRoundProgressBarWidthNumber.setProgress((int)progress);
    }

    protected void onAlignCountDownTick(float count) {
        super.onAlignCountDownTick(count);
        LogUtils.LOGI("fushui", " base onAlignCountDownTick = " + count);
        mStepRoundProgressBarWidthNumber.setProgress((int)count);
    }

    @Override
    protected void onMotionCountDownEnd(){
        super.onMotionCountDownEnd();
        LogUtils.LOGI("fushui", " bas onMotionCountDownEnd  ... ");
    }


    //准备阶段错误码
    @Override
    protected void onMotionAlignError(int errorCode){
        super.onMotionAlignError(errorCode);
        LogUtils.LOGI("fushui", " bas base onMotionAlignError ..." + errorCode);
    }

    @Override
    public void onMotionAlign(FaceInfo.FaceState faceState
            , FaceInfo.FaceDistance faceDistance) {
        super.onMotionAlign(faceState, faceDistance);
        LogUtils.LOGI("fushui", "bas  onMotionAlign... ");
        setUpMask(faceState == FaceInfo.FaceState.NORMAL);
    }

    protected void onMotionState(FaceInfo.FaceState state, FaceInfo.FaceDistance distance, Rect rect) {
        super.onMotionState(state, distance, rect);
        LogUtils.LOGI("fushui", "bas  onMotionState... ");
        setUpMask(state == FaceInfo.FaceState.NORMAL);
    }


    @Override
    protected void onMotionCompleted(byte[] data, String videoPath){
        super.onMotionCompleted(data, videoPath);
        LogUtils.LOGI("fushui", " bas base onMotionCompleted videoPath..." + videoPath);
    }


    /**
     * -----(END)重载活体检测流程的关键节点回调方法(END)-----
     **/

    //设置预览框外面是红色圈还是绿色全
    protected void setUpMask(boolean haveFace) {
        if(lastHaveFace == haveFace){
            return;
        }
        lastHaveFace = haveFace;
        if(mMaskBackImageView != null) {
            if (haveFace) {
                //mMaskBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_bas_mask_circle_have_face));
                //mMaskImageView.clearColorFilter();
                //mMaskImageView.setColorFilter(mCircleHasFace);
            } else {
                //mMaskBackImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_bas_mask_circle_no_face));
                //mMaskImageView.clearColorFilter();
                //mMaskImageView.setColorFilter(mCircleNoFace);
            }
        }
    }


    @Override
    protected void onPause() {
        LogUtils.LOGI("fushui", "bas activity onPause... ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogUtils.LOGI("fushui", "bas activity onStop... ");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.LOGI("fushui", "bas activity onDestroy... ");
        super.onDestroy();
    }


    @Override
    protected void showPreview(boolean isShowPreview)
    {
        super.showPreview(isShowPreview);
        int state = isShowPreview ? View.VISIBLE : View.INVISIBLE;
//        setVisibility(mTipsIcon, mIsTipsEnable ? state : View.INVISIBLE);
//        setVisibility(mCircleTimeView, state);
//        setVisibility(mFaceTips,state);
//        setVisibility(tv_noface,state);
//        setVisibility(mprogressbar,state);
        LogUtils.LOGI("fushui", "showResult...");
        setVisibility(mBottomRelativeLayout, state);
//        setVisibility(mCwDetectPreview, View.INVISIBLE);
        setVisibility(mMaskImageView, state);
        setVisibility(mMaskBackImageView, state);
        setVisibility(mCloseImageView, state);
        //setVisibility(mStepImageView, View.INVISIBLE);
        //setVisibility(mStepTextView, View.INVISIBLE);
        setVisibility(mStepRoundProgressBarWidthNumber, state);
    }
}


package com.edencity.customer.user.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.home.fragment.ToBeVipFragment;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.user.activity.FeedHistoryActivity;
import com.edencity.customer.util.AdiUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.pojo.EventMessage;
import com.edencity.customer.pojo.FuncResult;
import com.edencity.customer.user.adapter.FullyGridLayoutManager;
/*import cn.zhongyu.edencity.user.adapter.GridImageAdapter;*/

import com.edencity.customer.user.adapter.GridImageAdapter;
import com.edencity.customer.util.ToastUtil;
import com.edencity.customer.view.LoadingDialog;

import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends BaseFragment2 {

    @BindView(R.id.edit_content)
    EditText contentEdit;
    @BindView(R.id.rlv_feedback)
    RecyclerView mRlvFeedBack;
    @BindView(R.id.feed)
    TextView mFeed;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_back)
    ImageButton mBack;

    @BindView(R.id.label_word_count)
    TextView wordCount;
    private boolean isCommitable = false;
    private BaseDialog baseDialog;

    //这个集合用来放媒体文件，包括图片，视频，音频都可以
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int chooseMode = PictureMimeType.ofImage();
    private int themeId;
    private int mPosition;
    private Retrofit retrofit;

    public static FeedbackFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        FeedbackFragment toBeVipFragment = new FeedbackFragment();
        toBeVipFragment.setArguments(bundle);
        return toBeVipFragment;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        if (1== getArguments().getInt("type")){
            Window window = getActivity().getWindow();
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
            StatusBarCompat.changeToLightStatusBar(getActivity());
        }
        wordCount.setText("0 / 120");
        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 120) {
                    wordCount.setText(editable.length() + " / 120");
                    wordCount.setTextColor(Color.RED);
                } else {
                    wordCount.setText(editable.length() + " / 120");
                    wordCount.setTextColor(Color.parseColor("#666666"));
                    isCommitable = false;
                }

            }
        });
        initView();
        return view;
    }

    //弹出弹窗
    private void initDialog() {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_feed_success, null);
        TextView btn_ok = inflate.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(v -> baseDialog.dismiss());
        baseDialog = new BaseDialog(getActivity(), inflate, Gravity.CENTER);
        baseDialog.show();
        contentEdit.setText("");
        selectList = new ArrayList<>();
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        //themeId必须设置
        themeId = R.style.picture_default_style;
        initRv();
        mFeed.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), FeedHistoryActivity.class);
            startActivity(intent);
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener =
            new GridImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {
                    boolean mode = true;
                    if (mode) {
                        // 进入相册 以下是例子：不需要的api可以不写
                        // 进入相册 以下是例子：用不到的api可以不写
                        PictureSelector.create(getActivity())
                                .openGallery(chooseMode)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                .theme(themeId)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                .maxSelectNum(3)// 最大图片选择数量 int
                                .minSelectNum(1)// 最小选择数量 int
                                .imageSpanCount(4)// 每行显示个数 int
                                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                .previewImage(true)// 是否可预览图片 true or false
                                .previewVideo(false)// 是否可预览视频 true or false
                                .enablePreviewAudio(false) // 是否可播放音频 true or false
                                .isCamera(true)// 是否显示拍照按钮 true or false
                                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                .sizeMultiplier(1.0f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                                .enableCrop(false)// 是否裁剪 true or false
                                .compress(true)// 是否压缩 true or false
                                /* .glideOverride(76,76)*/// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                                .isGif(false)// 是否显示gif图片 true or false
                                //.compressSavePath(getPath())//压缩图片保存地址
                                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                .openClickSound(false)// 是否开启点击声音 true or false
                                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                                .minimumCompressSize(100)// 小于100kb的图片不压缩

                                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                .cropWH(1, 1)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                                .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
                                .videoQuality(0)// 视频录制质量 0 edor 1 int
                                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                                .recordVideoSecond(60)//视频秒数录制 默认60s int
                                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                    }
                }
            };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage message) {
        if (message.type == EventMessage.EVENT_FEEDBACK) {
            Intent data = (Intent) message.data;
            // 图片选择，把选好的图片存入集合，展示在缩略图列表
            selectList = PictureSelector.obtainMultipleResult(data);
            adapter.setList(selectList);
            //刷新适配器
            adapter.notifyDataSetChanged();

        }
    }

    //初始化照片选择器
    private void initRv() {
        //存放缩略图的列表
        FullyGridLayoutManager manager =
                new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mRlvFeedBack.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener, 0);
        //设置集合数据
        adapter.setList(selectList);
        //设置最大选择数量
        //最大选择文件数，此时我们需求最大传四张图片
        int maxSelectNum = 3;
        adapter.setSelectMax(maxSelectNum);
        mRlvFeedBack.setAdapter(adapter);

        adapter.setOnItemClickListener((position, v) -> {
            //缩略图选择列表子条目点击事件
            LocalMedia media = selectList.get(position);
            String pictureType = media.getPictureType();
            int mediaType = PictureMimeType.pictureToVideo(pictureType);
            switch (mediaType) {
                case 1:
                    // 预览图片
                    PictureSelector.create(getActivity())
                            .externalPicturePreview(position, selectList);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(getActivity()).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(getActivity()).externalPictureAudio(media.getPath());
                    break;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

     private File  getFileFormPosition(int position){
       LocalMedia media = selectList.get(position);
       String path = "";
       if (media.isCut() && !media.isCompressed()) {
           // 裁剪过
           path = media.getCutPath();
       } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
           // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
           path = media.getCompressPath();
       } else {
           // 原图
           path = media.getPath();
       }
       return new File(path);
    }


    private void onSubmit() {
        final String msg = contentEdit.getText().toString();
        if (msg.length() < 16) {
            ToastUtil.showToast(getContext(), "请您输入至少大于15个字的反馈");
            return;
        }
        if (msg.length() > 120) {
            ToastUtil.showToast(getContext(), "反馈内容不能超过120个字");
            return;
        }
      /*  LoadingDialog.showLoading(getFragmentManager());

        HashMap<String, Object> hashMap = new HashMap<>();
        String ticket = App.userMsg().getTicket();
        hashMap.put("ticket",  ticket);
        Log.e("content", App.userMsg().ticket);
        hashMap.put("userId",  App.userMsg().getCustomer().getUserId());
        hashMap.put("content",   msg);*/
        LoadingDialog.showLoading(getFragmentManager());

        if (selectList.size()==1){

            App.execute(()->{
                final FuncResult fr=App.webService().userFeedBack(contentEdit.getText().toString()
                        ,"evidence1",getFileFormPosition(0),null,null,null,null);
                getActivity().runOnUiThread(()->{
                    LoadingDialog.hideLoading();
                    Log.d("tsg",fr.toString());


                    if (fr.code==0){
                        initDialog();
                    }else if (fr.code== -3){
                        AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                        Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        App.defaultApp().startActivity(intent);
                    }else {
                        ToastUtil.showToast(getContext(),fr.msg);
                    }
                });
            });
        }else if (selectList.size()==2){
            App.execute(()->{
                final FuncResult fr=App.webService().userFeedBack(contentEdit.getText().toString()
                        ,"evidence1",getFileFormPosition(0),"evidence2",getFileFormPosition(1),null,null);
                getActivity().runOnUiThread(()->{
                    LoadingDialog.hideLoading();
                    if (fr.code==0){
                        initDialog();
                    }else if (fr.code== -3){
                        AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                        Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        App.defaultApp().startActivity(intent);
                    }else {
                        ToastUtil.showToast(getContext(),fr.msg);
                    }
                });
            });
        }else if (selectList.size()==3){
            App.execute(()->{
                final FuncResult fr=App.webService().userFeedBack(contentEdit.getText().toString()
                        ,"evidence1",getFileFormPosition(0),"evidence2",getFileFormPosition(1),"evidence3",getFileFormPosition(2));
                getActivity().runOnUiThread(()->{
                    LoadingDialog.hideLoading();
                    if (fr.code==0){
                        initDialog();
                    }else if (fr.code== -3){
                        AdiUtils.showToast("您的登录信息已经失效，请重新登录！");
                        Intent intent = new Intent(App.defaultApp(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        App.defaultApp().startActivity(intent);
                    }else {
                        ToastUtil.showToast(getContext(),fr.msg);
                    }
                });
            });
        }else if (selectList.size()==0){
            App.execute(()->{
                final FuncResult fr=App.webService().userFeedBack(contentEdit.getText().toString()
                        ,null,null,null,null,null,null);
                getActivity().runOnUiThread(()->{
                    LoadingDialog.hideLoading();
                    if (fr.code==0){
                       initDialog();
                    }else if (fr.code== -3){
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


    }

}

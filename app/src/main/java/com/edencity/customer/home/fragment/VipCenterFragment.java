package com.edencity.customer.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.base.NormalWebActivity;
import com.edencity.customer.custum.ControlViewPager;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.entity.VipGoodEntity;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.home.activity.InviriteWebActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.home.adapter.VipLevelListAdapter;
import com.edencity.customer.home.adapter.VipVpAdapter;
import com.edencity.customer.home.vipbanner.CardAdapter;
import com.edencity.customer.home.vipbanner.CardScaleHelper;
import com.edencity.customer.home.vipbanner.SpeedRecyclerView;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.user.activity.ActiveValueActivity;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.DeeSpUtil;
import com.edencity.customer.util.NetUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class VipCenterFragment extends BaseFragment2 {


    private SpeedRecyclerView mBanner;
    private CardScaleHelper mCardScaleHelper;
    private CardAdapter cardAdapter;

 /*   private ControlViewPager mVp;*/
    private SmartRefreshLayout mSmart;
    /*private MyNormalTextView tag;*/
    private MyMediumTextView tv_go;
    private ImageView invilatid;
    private BaseDialog baseDialog2;
    private MyNormalTextView rlue;
    private BaseDialog baseDialog;
    private RecyclerView mRlv;
    private VipLevelListAdapter listAdapter;
    private ImageView img_tag;
    private int type = 0;
    private TextView tag;

    public VipCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.userMsg()!=null && App.userMsg().getCustomer() !=null){
              if ("普通汇员".equals(App.userMsg().getCustomer().getUserVipLevel())){
                type = 0;
            }else{
                type = 1;
            }
        }else{

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_vip_center, container, false);

        initVIew(inflate);
        initData();
        return inflate;
    }

    private void initData() {


        HashMap hashMap = ParamsUtils.getParamsMapWithNoId(null, null);
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataService.getHomeService().getVipGood(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VipGoodEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(VipGoodEntity o) {

                        Log.e("vip",o.toString());
                        if (o.getResult_code()==0 && o.getData()!=null && o.getData().size()>0){
                            initBanner(o.getData());
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (mSmart.isRefreshing()){
                            mSmart.finishRefresh();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        StatusBarCompat.changeToLightStatusBar(getActivity());
        String userId = DeeSpUtil.getInstance().getString("userId");
        String ticket = DeeSpUtil.getInstance().getString("ticket");
        initRlv(userId, ticket);

    }


    private void getBannerData() {

        if (App.userMsg().getCustomer().getMemberTypeName().equals("付费汇员")){
            mCardScaleHelper.setCurrentItemPos(1);
        }else if (App.userMsg().getCustomer().getMemberTypeName().equals("普通汇员")){
            mCardScaleHelper.setCurrentItemPos(0);
        }else{
            mCardScaleHelper.setCurrentItemPos(2);
        }

    }

    private void initBanner(List<VipGoodEntity.DataBean> data) {

        tag.setVisibility(View.GONE);
        tv_go.setVisibility(View.GONE);
        img_tag.setVisibility(View.GONE);
        mRlv.setVisibility(View.VISIBLE);
        initType(type,data);


        mBanner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
                    // DES: 找出当前可视Item位置
                    RecyclerView.LayoutManager layoutManager = mBanner.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        int mFirstVisiblePosition = linearManager.findFirstVisibleItemPosition();
                        int mLastVisiblePosition = linearManager.findLastVisibleItemPosition();
                        if (mFirstVisiblePosition==0 && mLastVisiblePosition ==1){
                            /*mVp.setCurrentItem(0);*/
                            tag.setVisibility(View.GONE);
                            tv_go.setVisibility(View.GONE);
                            img_tag.setVisibility(View.GONE);
                            type = 0;
                            mRlv.setVisibility(View.VISIBLE);

                        }else if (mFirstVisiblePosition==1 && mLastVisiblePosition ==2){
                            /*mVp.setCurrentItem(2);*/
                            tag.setVisibility(View.GONE);
                            tv_go.setVisibility(View.GONE);
                            img_tag.setVisibility(View.VISIBLE);
                            mRlv.setVisibility(View.GONE);

                        }else if (mFirstVisiblePosition==0 &&mLastVisiblePosition==2){
                            /*mVp.setCurrentItem(1);*/
                            img_tag.setVisibility(View.GONE);
                            tag.setVisibility(View.VISIBLE);
                            tv_go.setVisibility(View.VISIBLE);
                            mRlv.setVisibility(View.VISIBLE);
                            type = 1;
                        }
                        initType(type,data);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        //邀请有礼
        invilatid.setOnClickListener(v -> {
            if (!NetUtils.isNetworkAvailable(getActivity())){
                AdiUtils.showToast("您的网络状态很差，请检查网络再试");
            }else {
                if (!ButtonUtils.isFastDoubleClick(R.id.invilatid)){
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
                            desc1.setText(R.string.had_no_veirify);
                            desc2.setVisibility(View.VISIBLE);
                            desc2.setText(R.string.please_goto_verify);
                            head_img.setImageResource(R.drawable.name_check1);
                            go.setText("去认证>");
                            go.setOnClickListener(view -> {
                                if (!ButtonUtils.isFastDoubleClick(R.id.go)){
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent);
                                    baseDialog2.dismiss();
                                }
                            });
                        }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                            desc1.setText(R.string.verify_failed);
                            head_img.setImageResource(R.drawable.name_check1);
                            go.setText("去认证>");
                            desc2.setVisibility(View.VISIBLE);
                            desc2.setText(R.string.please_goto_verify);
                            //视图中view点击事件
                            go.setOnClickListener(view -> {
                                if (!ButtonUtils.isFastDoubleClick(R.id.go)){
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent);
                                    baseDialog2.dismiss();
                                }
                            });
                        }else  {//审核中

                            head_img.setImageResource(R.drawable.name_check2);
                            desc1.setText(R.string.verifing);
                            desc2.setVisibility(View.GONE);
                            go.setText("我知道了");
                            go.setOnClickListener(view -> baseDialog2.dismiss());

                        }
                        close.setOnClickListener(view -> {
                            //BaseDialog对象声明成全局，调用方便
                            baseDialog2.dismiss();
                        });
                        baseDialog2 = new BaseDialog(getActivity(), contentView, Gravity.CENTER);
                        baseDialog2.show();

                    }else if (App.userMsg()!=null && App.userMsg().getCustomer()!=null  && App.userMsg().getCustomer().isUserApprovaled()){
                        //认证了
                        Intent intent = new Intent(getActivity(), InviriteWebActivity.class);
                        startActivity(intent);
                    }
                }
            }


        });

        tv_go.setOnClickListener(v -> {
            if (!NetUtils.isNetworkAvailable(getActivity())){
                AdiUtils.showToast("您的网络状态很差，请检查网络再试");
            }else {
                if (!ButtonUtils.isFastDoubleClick(R.id.tv_go)){
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
                                if (!ButtonUtils.isFastDoubleClick(R.id.go)){
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent);
                                    baseDialog.dismiss();
                                }

                            });
                        }else if (App.userMsg().getCustomer().getUserApprovalStatus() == Customer.USER_APPROVAL_FAIL){

                            desc1.setText("您的实名认证未通过，请重新认证~");
                            head_img.setImageResource(R.drawable.name_check1);
                            go.setText("去认证>");
                            desc2.setVisibility(View.VISIBLE);
                            desc2.setText("认证通过后即获得30点活跃值，快去认证吧~");
                            //视图中view点击事件
                            go.setOnClickListener(view -> {
                                if (!ButtonUtils.isFastDoubleClick(R.id.go)){
                                    Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                                    startActivity(intent);
                                    baseDialog.dismiss();
                                }

                            });
                        }else  {//审核中

                            head_img.setImageResource(R.drawable.name_check2);
                            desc1.setText("身份认证中，认证通过才可进行充值及付款哦~");
                            desc2.setVisibility(View.GONE);
                            go.setText("我知道了");
                            go.setOnClickListener(v1 -> {
                                if (!ButtonUtils.isFastDoubleClick(R.id.go)){
                                    baseDialog.dismiss();
                                }
                            });

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
            }


        });
    }


    private void initType(int type,List<VipGoodEntity.DataBean> data) {

            VipGoodEntity.DataBean dataBean = data.get(type);
            if (dataBean!=null && dataBean.getItemList()!=null){
                List<VipGoodEntity.DataBean.ItemListBean> itemList = dataBean.getItemList();
                ArrayList<BaseDebug> objects = new ArrayList<>();

                for (int i = 0; i < itemList.size(); i++) {
                    if (i==0){
                        VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                        objects.add(new BaseDebug((int)listBean.getPerAmount()+"",
                                "",R.drawable.cold1,(int)listBean.getPerBonus()+""));
                    }else if (i==1){
                        VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                        objects.add(new BaseDebug((int)listBean.getPerAmount()+"",
                                "",R.drawable.cold2,(int)listBean.getPerBonus()+""));
                    }else if (i==2){
                        VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                        objects.add(new BaseDebug((int)listBean.getPerAmount()+"",
                                "",R.drawable.cold3,(int)listBean.getPerBonus()+""));
                    }else{
                        VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                        objects.add(new BaseDebug((int)listBean.getPerAmount()+"",
                                "",R.drawable.cold4,(int)listBean.getPerBonus()+""));
                    }

                }
                if (objects!=null && objects.size()>0){
                    listAdapter.addData(objects);
                }
            }


    }


    private void initVIew(View inflate) {

        mBanner  = inflate.findViewById(R.id.banner);
        /*mVp  = inflate.findViewById(R.id.vp);*/
        tag  = inflate.findViewById(R.id.tag);
        tv_go  = inflate.findViewById(R.id.tv_go);
        img_tag  = inflate.findViewById(R.id.img_tag);
        mRlv  = inflate.findViewById(R.id.rlv);
        rlue  = inflate.findViewById(R.id.rlue);
        invilatid  = inflate.findViewById(R.id.invilatid);
        mSmart  = inflate.findViewById(R.id.smart);

        mSmart.setOnRefreshListener(refreshLayout -> {
            initData();
            String userId = DeeSpUtil.getInstance().getString("userId");
            String ticket = DeeSpUtil.getInstance().getString("ticket");
            initRlv(userId, ticket);
        });
        rlue.setOnClickListener(v -> {
         if (!ButtonUtils.isFastDoubleClick(R.id.rlue)){
             Intent intent = new Intent(getContext(), NormalWebActivity.class);
             intent.putExtra("url"," https://epi.edencity.com/memberRule.html");
             startActivity(intent);
         }
        });

        mRlv.setNestedScrollingEnabled(false);
        mRlv.setLayoutManager(new GridLayoutManager(getContext(),4));
        listAdapter = new VipLevelListAdapter();
        mRlv.setAdapter(listAdapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        mBanner.setLayoutManager(linearLayoutManager);
        cardAdapter = new CardAdapter();
        mBanner.setAdapter(cardAdapter);
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.attachToRecyclerView(mBanner);

        ArrayList<BaseDebug> mList = new ArrayList<>();
        mList.add(new BaseDebug("1","",R.drawable.card,""));
        mList.add(new BaseDebug("2","",R.drawable.card2,""));
        mList.add(new BaseDebug("3","",R.drawable.card3,""));

        cardAdapter.addData(mList);
        cardAdapter.onItemClick(i -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.buy_history)){
                ((MainActivity) getActivity()).start(BuyHistoryFragment.getInstance());
            }
        });
    }
    private void initRlv(String userId, String ticket) {
        DataService.getInstance().syncUser(userId,
                ticket, new IBaseCallBack<UserMsgEntity>() {
                    @Override
                    public void onSuccess(UserMsgEntity data) {
                        if (data.getCustomer() != null && data.getCustomer().getUserVipLevel() != null) {
                            if (data.getCustomer().getUserVipLevel().equals("普通汇员")) {
                                tag.setText("开通赠送365伊甸果");
                                tv_go.setText("¥365.00/年  立即开通");
                            } else {
                                tag.setText("续费赠送365伊甸果");
                                tv_go.setText("¥365.00/年  立即续费");
                            }
                        }
                        if (App.userMsg().getCustomer().equals(data)){

                        }else{
                            Log.d("splash", data.toString());
                            App.defaultApp().saveUserMsg(data);
                            getBannerData();
                            cardAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
    }
}

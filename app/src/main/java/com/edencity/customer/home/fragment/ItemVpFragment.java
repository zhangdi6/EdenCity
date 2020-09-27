package com.edencity.customer.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.IBaseCallBack;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.entity.VipGoodEntity;
import com.edencity.customer.home.activity.AuthenticationActivity;
import com.edencity.customer.home.activity.MainActivity;
import com.edencity.customer.home.adapter.VipLevelListAdapter;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.pojo.Customer;
import com.edencity.customer.util.AdiUtils;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.DeeSpUtil;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.SHA1Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemVpFragment extends Fragment {


    private int type;
    private RecyclerView mRlv;

    private VipLevelListAdapter listAdapter;
    /*private MyMediumTextView mCharge;
    private MyNormalTextView mTag;*/
    private ImageView mImgTag;
    private BaseDialog baseDialog;

    public ItemVpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_item_vp2, container, false);
        Bundle arguments = getArguments();
        if (arguments!=null){
            type = arguments.getInt("type");
        }
        initView(inflate);
        getListData();
        return inflate;
    }

    private void getListData() {

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
                            ArrayList<BaseDebug> list = getList(o.getData());
                            if (list!=null && list.size()>0){
                                listAdapter.addData(list);
                            }
                        }else if (o.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private ArrayList<BaseDebug> getList(List<VipGoodEntity.DataBean> data) {
        //一级汇员
        if (type==0){
            VipGoodEntity.DataBean dataBean = data.get(0);
            List<VipGoodEntity.DataBean.ItemListBean> itemList = dataBean.getItemList();
            ArrayList<BaseDebug> objects = new ArrayList<>();
            for (int i = 0; i < itemList.size(); i++) {
                VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                if (i==0){
                    objects.add(new BaseDebug(listBean.getPerAmount()+"",
                            "",R.mipmap.zeng1,(int)listBean.getPerBonus()+""));
                }else if (i==1){
                    objects.add(new BaseDebug(listBean.getPerAmount()+"",
                            "",0,(int)listBean.getPerBonus()+""));
                }else if (i==2){
                    objects.add(new BaseDebug(listBean.getPerAmount()+"",
                            "",0,(int)listBean.getPerBonus()+""));
                }else{
                    objects.add(new BaseDebug(listBean.getPerAmount()+"",
                            "",0,(int)listBean.getPerBonus()+""));
                }
            }
            return objects;
            //二级汇员
        }else if (type==1){
            if (data.size()>=2){
                VipGoodEntity.DataBean dataBean = data.get(1);
                List<VipGoodEntity.DataBean.ItemListBean> itemList = dataBean.getItemList();
                ArrayList<BaseDebug> objects = new ArrayList<>();
                for (int i = 0; i < itemList.size(); i++) {
                    VipGoodEntity.DataBean.ItemListBean listBean = itemList.get(i);
                    if (i==0){
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",0,(int)listBean.getPerBonus()+""));
                    }else if (i==1){
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",0,(int)listBean.getPerBonus()+""));
                    }else if (i==2){
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",0,(int)listBean.getPerBonus()+""));
                    }else{
                        objects.add(new BaseDebug(listBean.getPerAmount()+"",
                                "",0,(int)listBean.getPerBonus()+""));
                    }
                }
                return objects;
            }else{
                /*mTv.setVisibility(View.VISIBLE);*/
                mRlv.setVisibility(View.GONE);
                mImgTag.setVisibility(View.VISIBLE);
           /*     mTag.setVisibility(View.GONE);
                mCharge.setVisibility(View.GONE);*/
            }

        }else{

        }
        return null;
    }

    private void initView(View inflate) {
        mRlv = inflate.findViewById(R.id.rlv);

        mImgTag = inflate.findViewById(R.id.img_tag);
        /*mTv = inflate.findViewById(R.id.tv);*/
        if (type==2){
            mImgTag.setVisibility(View.VISIBLE);
            mRlv.setVisibility(View.GONE);
           /* mTag.setVisibility(View.GONE);
            mCharge.setVisibility(View.GONE);*/

        }else if (type==0){
            mImgTag.setVisibility(View.GONE);
            mRlv.setVisibility(View.VISIBLE);
           /* mTag.setVisibility(View.GONE);
            mCharge.setVisibility(View.GONE);*/

        }else{
            mImgTag.setVisibility(View.GONE);
            mRlv.setVisibility(View.VISIBLE);
           /* mTag.setVisibility(View.VISIBLE);
            mCharge.setVisibility(View.VISIBLE);*/

        }
        mRlv.setNestedScrollingEnabled(false);
        mRlv.setLayoutManager(new GridLayoutManager(getContext(),4));
        listAdapter = new VipLevelListAdapter();
        mRlv.setAdapter(listAdapter);

    }


}

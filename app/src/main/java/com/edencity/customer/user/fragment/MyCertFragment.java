package com.edencity.customer.user.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.dtr.zxing.utils.ZXingUtil;

import com.edencity.customer.App;
import com.edencity.customer.custum.TwoBallRotationProgressBar;
import com.edencity.customer.login.activity.LoginActivity;
import com.edencity.customer.util.AdiUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

import com.edencity.customer.R;
import com.edencity.customer.base.BaseDialog;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.data.DataService;
import com.edencity.customer.entity.BaseResult;
import com.edencity.customer.entity.ConvertListEntity;
import com.edencity.customer.user.adapter.MyCertListAdapter;
import com.edencity.customer.util.DisplayInfoUtils;
import com.edencity.customer.util.ParamsUtils;
import com.edencity.customer.util.ResUtils;
import com.edencity.customer.util.SHA1Utils;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCertFragment extends BaseFragment2 {


    private SmartRefreshLayout mSmart;
    private RecyclerView mRlv;
    private int type;
    private MyCertListAdapter adapter;
    private ImageView mImag;
    private BaseDialog baseDialog;
    private ConstraintLayout mLoadFail;
    private MyNormalTextView nTag;
    private TwoBallRotationProgressBar loading;

    public MyCertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my_cert, container, false);
        Bundle bundle = getArguments();
        if (bundle!=null){
            type = bundle.getInt("tag");
        }
        initView(inflate);
        initDataList();
        return inflate;
    }

    private void initDataList() {


        loading.startAnimator();
        HashMap hashMap;
        if (type>=0){
             hashMap = ParamsUtils.getParamsMap("convertedStatus", type);
        }else{
            hashMap =ParamsUtils.getParamsMap();
        }
        String sign = ParamsUtils.getSign(hashMap);
        try {
            hashMap.put("sign", SHA1Utils.strToSHA1(sign));
            Log.e("cert", hashMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataService.getUserService().userConcert(hashMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<ConvertListEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResult<ConvertListEntity> result) {

                        Log.e("cert",result.toString());
                        if (result.getResult_code()==0 && result.getData()!=null
                                &&result.getData().getConvertedList()!=null
                                &&result.getData().getConvertedList().size()>0){
                            ConvertListEntity data = result.getData();
                            ArrayList<ConvertListEntity.ConvertedListBean> list =
                                    (ArrayList<ConvertListEntity.ConvertedListBean>) data.getConvertedList();

                            adapter = new MyCertListAdapter(type);
                            mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
                            mRlv.setAdapter(adapter);


                            adapter.addData(list);
                            adapter.onItemClick(postion ->
                                    initCertCodeDialog(list.get(postion).getActivityConvertedId())
                            );
                            mSmart.setVisibility(View.VISIBLE);
                            mLoadFail.setVisibility(View.GONE);

                        }else if (result.getResult_code()== -3){
                            AdiUtils.loginOut();
                        }else{
                            mLoadFail.setVisibility(View.VISIBLE);
                            nTag.setText("还没有兑换记录哦~");
                            mImag.setVisibility(View.GONE);
                            mSmart.setVisibility(View.GONE);
                        }
                        loading.stopAnimator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cert",e.getMessage());
                        mLoadFail.setVisibility(View.VISIBLE);
                        loading.stopAnimator();
                        nTag.setText("还没有兑换记录哦~");
                        mImag.setVisibility(View.GONE);
                        mSmart.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initCertCodeDialog(String Qcode) {
        float dimens = ResUtils.getDimens(R.dimen.dp_200);
        int i = DisplayInfoUtils.getInstance().dp2pxInt(dimens);
        Bitmap qrImage = ZXingUtil.createQRImage(Qcode,i,
                i);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.cert_code_dialog, null);
        ImageView close = inflate.findViewById(R.id.close);
        ImageView code = inflate.findViewById(R.id.iv_code);
        code.setImageBitmap(qrImage);
        ConstraintLayout null_area = inflate.findViewById(R.id.null_area);
        close.setOnClickListener(v -> baseDialog.dismiss());
        null_area.setOnClickListener(v -> baseDialog.dismiss());
         baseDialog = new BaseDialog(getContext(), inflate, Gravity.CENTER);
         baseDialog.show();
    }

    private void initView(View inflate) {
        mSmart = inflate.findViewById(R.id.smart);
        mRlv = inflate.findViewById(R.id.rlv_cert);
        mImag = inflate.findViewById(R.id.img);
        loading = inflate.findViewById(R.id.loading);
        mLoadFail = inflate.findViewById(R.id.loadfail);
        nTag = inflate.findViewById(R.id.tag_text);
    }

}

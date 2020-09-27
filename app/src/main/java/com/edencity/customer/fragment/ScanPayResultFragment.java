package com.edencity.customer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseFragment2;
import com.edencity.customer.util.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanPayResultFragment extends BaseFragment2 {

    @BindView(R.id.text_store_name)
    TextView storeNameView;
    @BindView(R.id.text_fee)
    TextView totalFeeView;
    @BindView(R.id.image_store)
    ImageView storeImageView;


    private String storeName;
    private String totalFee;
    private String storePhoto;

    public ScanPayResultFragment() {
        // Required empty public constructor
    }

    public static ScanPayResultFragment newInstance(String storeName, BigDecimal fee,String storePhoto){
        ScanPayResultFragment fragment = new ScanPayResultFragment();
        Bundle args = new Bundle();
        args.putString("store_name", storeName);
        args.putString("total_fee", "¥ "+StringUtil.formatDecimal(fee));
        args.putString("store_photo", storePhoto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeName = getArguments().getString("store_name");
            totalFee = getArguments().getString("total_fee");
            storePhoto = getArguments().getString("store_photo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_scan_pay_result, container, false);
        ButterKnife.bind(this,view);
        storeNameView.setText(storeName);
        totalFeeView.setText(totalFee);
        if (storePhoto!=null){
            Picasso.with(getContext()).load(storePhoto).into(storeImageView);
        }else {
            storeImageView.setImageResource(R.drawable.icon_logo);
        }

        return view;
    }

    @Override
    public void onViewItemClicked(View view) {
        if (view.getId()==R.id.btn_ok){
            pop();
        }
    }
}

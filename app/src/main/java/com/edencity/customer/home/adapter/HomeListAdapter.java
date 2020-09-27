package com.edencity.customer.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.RoundImageView4dp;
import com.edencity.customer.entity.HomeShopListEntity;
import com.edencity.customer.util.ButtonUtils;

public class HomeListAdapter extends RecyclerView.Adapter {

    private  Context mContext;
    private ArrayList<HomeShopListEntity.ProviderListBean>mList = new ArrayList<>();
    private onItemClick ono;

    public HomeListAdapter(Context activity) {
        this.mContext = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_list, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        viewHolder1.item_title.setText(mList.get(i).getStoreName());
        if (mList.get(i).getStoreDetailAddress()!=null && !mList.get(i).getStoreDetailAddress().equals("")){
            viewHolder1.tv_address.setText(mList.get(i).getStoreDetailAddress());
        }else{
            viewHolder1.tv_address.setText("暂无位置信息");
        }

        Glide.with(mContext).load(mList.get(i).getStoreFacadeImg()).into(viewHolder1.item_iv);

        viewHolder1.ll.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.ll)){
                if (ono!=null){
                    ono.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(List<HomeShopListEntity.ProviderListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }
    public void addNewData(List<HomeShopListEntity.ProviderListBean> objects) {
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RoundImageView4dp item_iv;
        private final MyMediumTextView item_title;
        private final MyNormalTextView tv_address;
        private final RelativeLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_iv  = itemView.findViewById(R.id.item_iv);
            ll  = itemView.findViewById(R.id.ll);
            item_title= itemView.findViewById(R.id.item_title);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }
    public interface onItemClick{
        void onClick(int position);
    }
    public void onClick(onItemClick itemClick){
        ono = itemClick;
    }
    }

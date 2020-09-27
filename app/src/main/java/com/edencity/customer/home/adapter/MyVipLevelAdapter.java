package com.edencity.customer.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.VipGoodEntity;

import java.util.ArrayList;
import java.util.List;

// Created by Ardy on 2020/4/18.
public class MyVipLevelAdapter extends RecyclerView.Adapter {

    private ArrayList<VipGoodEntity.DataBean.ItemListBean>mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vip, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv1.setText("充"+mList.get(i).getPerAmount()+"元");
        holder.tv2.setText("返赠"+mList.get(i).getPerBonus()+"伊甸果");

    }

    @Override
    public int getItemCount() {
        return mList.size()> 0 ? mList.size():0;
    }

    public void addData(List<VipGoodEntity.DataBean.ItemListBean> itemList) {
        mList.clear();
        mList.addAll(itemList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MyNormalTextView tv1;
        private final MyNormalTextView tv2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
        }
    }
}

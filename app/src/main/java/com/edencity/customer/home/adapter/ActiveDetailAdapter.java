package com.edencity.customer.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edencity.customer.R;

import java.util.ArrayList;
import java.util.List;

// Created by Ardy on 2020/4/2.
public class ActiveDetailAdapter extends RecyclerView.Adapter {

    private  List<String> mImgList = new ArrayList<>();

    public ActiveDetailAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_img_list2, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        Glide.with(viewHolder1.mImg.getContext()).load(mImgList.get(i)).into(viewHolder1.mImg);
    }

    @Override
    public int getItemCount() {
        return mImgList.size()>0?mImgList.size():0;
    }

    public void addData(List<String> mList) {
        mImgList.clear();
        mImgList.addAll(mList);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.img);
        }
    }
}

package com.edencity.customer.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.edencity.customer.R;
import com.luck.picture.lib.photoview.PhotoView;

// Created by Ardy on 2020/2/27.

public class ShopProductList extends RecyclerView.Adapter {

    private final List<String> mImgList;
    private final Context shopActivity;


    public ShopProductList(List<String> mImgList, Context shopActivity) {

        this.mImgList = mImgList;
        this.shopActivity = shopActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_img_list, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        Glide.with(shopActivity).load(mImgList.get(i)).into(viewHolder1.mImg);
    }

    @Override
    public int getItemCount() {
        return mImgList.size()>0?mImgList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.img);
        }
    }
}

package com.edencity.customer.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.entity.BaseDebug;

// Created by Ardy on 2020/1/7.

public class CardListAapter extends RecyclerView.Adapter {

    private ArrayList<BaseDebug> mList = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context mContext = viewGroup.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_card, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder view = (ViewHolder) viewHolder;
        BaseDebug baseDebug = mList.get(i);
        view.tv.setText(baseDebug.getTitle());
        view.tv_id.setText(baseDebug.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(ArrayList<BaseDebug> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MyMediumTextView tv;
        private final MyMediumTextView tv_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv  = itemView.findViewById(R.id.recharge_total_money);
            tv_id  = itemView.findViewById(R.id.card_id);

        }
    }
}

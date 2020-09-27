package com.edencity.customer.home.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.edencity.customer.R;
import com.edencity.customer.entity.WeekEntity;


// Created by Ardy on 2020/2/6.
public class WeekAdapter extends RecyclerView.Adapter {

    private ArrayList<WeekEntity> mLsi = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_week, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        viewHolder1.tv.setText(mLsi.get(i).getDay());
        if (mLsi.get(i).isCheck()){
            viewHolder1.tv.setTextColor(Color.parseColor("#70ABF8"));
        }else{
            viewHolder1.tv.setTextColor(Color.parseColor("#333333"));

        }
    }

    @Override
    public int getItemCount() {
        return mLsi.size();
    }

    public void addData(ArrayList<WeekEntity> objects) {
        mLsi.clear();
        mLsi.addAll(objects);
        notifyDataSetChanged();
    }

    public void check(int i) {
        for (int j = 1; j <= mLsi.size(); j++) {
            if (i == j){
                mLsi.get(j-1).setCheck(true);
            }else{
                mLsi.get(j-1).setCheck(false);
            }
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           tv = itemView.findViewById(R.id.item);
        }
    }
}

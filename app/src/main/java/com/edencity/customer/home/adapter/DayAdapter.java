package com.edencity.customer.home.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.edencity.customer.R;
import com.edencity.customer.entity.DayEntity;


// Created by Ardy on 2020/2/6.
public class DayAdapter extends RecyclerView.Adapter {

    private ArrayList<DayEntity> mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_day, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        holder.item.setText(mList.get(i).getId() + "");
        if (mList.get(i).getId() == 0) {
            holder.item.setVisibility(View.GONE);
        } else {
            holder.item.setVisibility(View.VISIBLE);
            if (mList.get(i).isSignIn()) {
                holder.item.setTextColor(Color.WHITE);
                holder.item.setBackgroundResource(R.drawable.shape_calender_tv);
            } else {
                if (mList.get(i).isHasSignIn()) {
                    holder.item.setTextColor(Color.parseColor("#999999"));
                    holder.item.setBackgroundResource(R.drawable.shape_calender_tv_little_blue);
                } else {
                    holder.item.setTextColor(Color.parseColor("#999999"));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() : 0;
    }

    public void addData(ArrayList<DayEntity> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public void checkToday(int dayOfMonth) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId() == dayOfMonth) {
                mList.get(i).setSignIn(true);
            } else {

            }
        }
        notifyDataSetChanged();
    }

    public void sysnState(boolean isSign, int today, int count) {
        for (int i = 1; i <= today; i++) {
            if (mList.get(i - 2 + count).getId() == today) {
                mList.get(i - 2 + count).setSignIn(isSign);
            }
        }
        notifyDataSetChanged();
    }

    //json 数据 ,today 今天是几号 ,year 当今年份 ,month 月份, count 这个月有几个空白位置
    public void sysnState(String json, int today, int year, int month, int count) {

        try {
            JSONObject jsonObject = new JSONObject(json);

            String mon;
            if (month < 10) {
                mon = "0" + month;
            } else {
                mon = month + "";
            }
            for (int i = 1; i <= today; i++) {
                long type;
                if (i < 10) {
                    String s = year + "-" + mon + "-0" + i;
                    Log.e("sign---", s);
                    type = jsonObject.getLong(s);
                } else {
                    String s = year + "-" + mon + "-" + i;
                    Log.e("sign---", s);
                    type = jsonObject.getLong(s);
                }

                if (type == 0) {
                    mList.get(i - 2 + count).setHasSignIn(false);
                } else {
                    mList.get(i - 2 + count).setHasSignIn(true);
                    if (mList.get(i-2+count).getId()==today){
                        mList.get(i-2+count).setSignIn(false);
                        mList.get(i-2+count).setHasSignIn(false);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
        }
    }
}

package com.edencity.customer.user.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.BillOrderEntity;
import com.edencity.customer.util.AdiUtils;


import java.util.ArrayList;
import java.util.List;

// Created by Ardy on 2020/3/28.
public class BillAdapter extends RecyclerView.Adapter {
    private ArrayList<BillOrderEntity.AccountChangeRecordListBean.ListBean> mList = new ArrayList<>();
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_order, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        holder.setIsRecyclable(false);
        BillOrderEntity.AccountChangeRecordListBean.ListBean bean = mList.get(i);
        if (bean.getChangeType().equals("0")){
            holder.price.setText("+"+bean.getChangeBalance());
            holder.price.setTextColor(Color.parseColor("#3287F6"));
        }else{
            holder.price.setText("-"+bean.getChangeBalance());
            holder.price.setTextColor(Color.parseColor("#F2B822"));
        }


        if (bean.getChangeTypeAndRecodeNumber()!=null){
            if (bean.getChangeTypeAndRecodeNumber().contains("$$")){
                String number = bean.getChangeTypeAndRecodeNumber();

                String[] s = number.split("[$][$]");
                for (int j = 0; j <s.length ; j++) {
                    Log.e("ttt",s[j]);
                }
                if (s.length==2){
                    holder.shop_head.setVisibility(View.GONE);
                    holder.shop_name.setText(s[0]);
                    holder.order_number.setText(s[1]);
                }else if (s.length==3){
                    holder.shop_head.setVisibility(View.VISIBLE);
                    holder.shop_name.setText(s[1]);
                    holder.order_number.setText(s[2]);
                    RequestOptions requestOptions = new RequestOptions().circleCrop().placeholder(R.mipmap.shop_icon).error(R.mipmap.shop_icon);
                    Glide.with(holder.itemView.getContext()).load(s[0]).apply(requestOptions).into(holder.shop_head);
                }else{
                    holder.shop_name.setText(bean.getChangeTypeAndRecodeNumber());
                    holder.shop_head.setVisibility(View.GONE);
                    holder.order_number.setText("");
                }
            }else{
                holder.shop_name.setText(bean.getChangeTypeAndRecodeNumber());
                holder.shop_head.setVisibility(View.GONE);
                holder.order_number.setText("");
            }
        }



        holder.time_tag.setText(bean.getCreatetime());

    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(List<BillOrderEntity.AccountChangeRecordListBean.ListBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addNewData(List<BillOrderEntity.AccountChangeRecordListBean.ListBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MyNormalTextView time_tag;
        private final MyMediumTextView shop_name;
        private final ImageView shop_head;
        private final MyMediumTextView price;
        private final MyNormalTextView order_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_head = itemView.findViewById(R.id.shop_head);
            time_tag = itemView.findViewById(R.id.time);
            order_number = itemView.findViewById(R.id.order_number);
            price = itemView.findViewById(R.id.price);
        }
    }
}

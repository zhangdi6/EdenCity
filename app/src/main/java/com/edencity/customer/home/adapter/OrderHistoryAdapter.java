package com.edencity.customer.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.BuyHistoryEntity;


// Created by Ardy on 2020/1/20.
public class OrderHistoryAdapter extends RecyclerView.Adapter {

    private ArrayList<BuyHistoryEntity.MembershipRecordListBean> mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_histrory, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        BuyHistoryEntity.MembershipRecordListBean listBean = mList.get(i);
        if (listBean.getRecordType().equals("1")){
            holder.mTag.setText("买");
            if (listBean.getMemberStartTime()!=null && !listBean.getMemberStartTime().equals("")){
                holder.begin_time.setText("购买时间："+listBean.getMemberStartTime());
            }
        }else if (listBean.getRecordType().equals("2")){
            holder.mTag.setText("赠");
            if (listBean.getMemberStartTime()!=null && !listBean.getMemberStartTime().equals("")){
                holder.begin_time.setText("赠送时间："+listBean.getMemberStartTime());
            }

        }else{
            holder.mTag.setText("续");
            if (listBean.getMemberStartTime()!=null && !listBean.getMemberStartTime().equals("")){
                holder.begin_time.setText("续费时间："+listBean.getMemberStartTime());
            }
        }
        holder.mOder_type.setText(listBean.getMembershipTypeName()+"年卡");
        holder.look_price.setText("¥"+listBean.getCost());
        holder.end.setText("有效期至："+listBean.getMemberEndTime());
    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(List<BuyHistoryEntity.MembershipRecordListBean> membershipRecordList) {
        mList.clear();
        mList.addAll(membershipRecordList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MyMediumTextView mTag;
        private final MyMediumTextView mOder_type;
        private final MyMediumTextView look_price;
        private final MyNormalTextView begin_time;
        private final MyNormalTextView end;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTag = itemView.findViewById(R.id.tag_text);
            mOder_type =  itemView.findViewById(R.id.order_type);
            begin_time = itemView.findViewById(R.id.begin);
            end = itemView.findViewById(R.id.end);
            look_price = itemView.findViewById(R.id.look_price);

        }
    }

}

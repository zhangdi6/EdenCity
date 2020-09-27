package com.edencity.customer.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.RechargeMoneyEntity;


// Created by Ardy on 2020/1/6.
public class RechargeMoneyAdapter extends RecyclerView.Adapter {

    public ArrayList<RechargeMoneyEntity>mList = new ArrayList<>();
    private Context mContext ;
    private onItemCheckedListener ono;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_recharge_money, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        RechargeMoneyEntity entity = mList.get(i);
        ViewHolder view= (ViewHolder) viewHolder;
        if (entity.isCheck()){
            view.root.setBackgroundResource(R.drawable.stroke_blue);
            view.tv_price.setTextColor(mContext.getResources().getColor(R.color.blue_nomal));
        }else{
            view.root.setBackgroundResource(R.drawable.stroke_gray);
            view.tv_price.setTextColor(Color.parseColor("#999999"));
        }
        view.tv_price.setText(entity.getPrice()+"元");
        view.tv_top.setText(entity.getTop_price());
        if (i== mList.size()-1){
            view.tv_top.setBackgroundResource(R.drawable.shape_bg_red);
        }else{
            view.tv_top.setBackgroundResource(R.drawable.shape_bg_blue);
        }
        view.itemView.setOnClickListener(v -> {
            if (ono != null ){
                ono.onItemCheck(i);
            }
        });
    }

    public int getCheckedPosition(){
        for (int i = 0; i < mList.size(); i++) {
            RechargeMoneyEntity rechargeMoneyEntity = mList.get(i);
            if (rechargeMoneyEntity.isCheck()){
                return i;
            }
        }
        return -1;
    }
    public void changeState(int position){
        for (int i = 0; i < mList.size() ; i++) {
            RechargeMoneyEntity entity = mList.get(i);
            if (i==position){
                entity.setCheck(true);
            }else{
                entity.setCheck(false);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(ArrayList<RechargeMoneyEntity> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MyMediumTextView tv_price;
        private final MyNormalTextView tv_top;
        private final ConstraintLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            tv_top = itemView.findViewById(R.id.tv_top);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
    public interface onItemCheckedListener{
        void onItemCheck(int position);
    }
    public void onItemCheck(onItemCheckedListener listener){
        ono = listener ;
    }
}

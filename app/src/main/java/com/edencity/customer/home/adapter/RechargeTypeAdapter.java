package com.edencity.customer.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.BaseDebug;


// Created by Ardy on 2020/1/6.
public class RechargeTypeAdapter extends RecyclerView.Adapter {

    private ArrayList<BaseDebug>mList = new ArrayList<>();
    private Context mContext ;
    private onItemCheckedListener ono;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_pay_type, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        BaseDebug entity = mList.get(i);
        ViewHolder view= (ViewHolder) viewHolder;
        if (entity.isChecked()){
            view.root.setBackgroundResource(R.drawable.shape_line_blue_30);
            view.tv.setTextColor(mContext.getResources().getColor(R.color.blue_nomal));
            view.icon.setImageResource(entity.getIcon_check());
            view.check.setImageResource(entity.getImg_check());
        }else{
            view.root.setBackgroundColor(Color.WHITE);
            view.tv.setTextColor(Color.parseColor("#999999"));
            view.icon.setImageResource(entity.getIcon_def());
            view.check.setImageResource(entity.getImg_def());
        }
        view.tv.setText(entity.getTitle());

        view.itemView.setOnClickListener(v -> {
            if (ono != null ){
                ono.onItemCheck(i);
            }
        });
    }

    public int getCheckedPosition(){
        for (int i = 0; i < mList.size(); i++) {
            BaseDebug rechargeMoneyEntity = mList.get(i);
            if (rechargeMoneyEntity.isChecked()){
                return i;
            }
        }
        return -1;
    }
    public void changeState(int position){
        for (int i = 0; i < mList.size() ; i++) {
            BaseDebug entity = mList.get(i);
            if (i==position){
                entity.setChecked(true);
            }else{
                entity.setChecked(false);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(ArrayList<BaseDebug> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MyNormalTextView tv;
        private final ImageView icon;
        private final ImageView check;
        private final ConstraintLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            icon = itemView.findViewById(R.id.icon);
            tv = itemView.findViewById(R.id.tv);
            check = itemView.findViewById(R.id.check);
        }
    }
    public interface onItemCheckedListener{
        void onItemCheck(int position);
    }
    public void onItemCheck(onItemCheckedListener listener){
        ono = listener ;
    }
}

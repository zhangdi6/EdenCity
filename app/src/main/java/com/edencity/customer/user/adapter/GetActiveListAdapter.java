package com.edencity.customer.user.adapter;

import android.content.Context;
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
import com.edencity.customer.entity.ActiveGetHistoryEntity;


// Created by Ardy on 2020/2/3.
public class GetActiveListAdapter extends RecyclerView.Adapter {

    private ArrayList<ActiveGetHistoryEntity.ActivityObtainRecordBean> mList = new ArrayList<>();
    private onItemClickListener ono;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View inflate =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_get_active, viewGroup,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        viewHolder1.item_time.setText(mList.get(i).getCreatetime());
        viewHolder1.item_type.setText(mList.get(i).getSourceTypeName()==null?"注册":mList.get(i).getSourceTypeName());
        viewHolder1.item_up.setText("+"+mList.get(i).getChangeAmount()+"点");
        if (ono!=null){
            ono.onItemClick(i);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(List<ActiveGetHistoryEntity.ActivityObtainRecordBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final MyNormalTextView item_time;
        private final MyNormalTextView item_up;
        private final MyMediumTextView item_type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_time  = itemView.findViewById(R.id.tv_time);
            item_type= itemView.findViewById(R.id.tv_type);
            item_up = itemView.findViewById(R.id.textView23);
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void onItemClick(onItemClickListener listener){
        ono = listener ;
    }
}

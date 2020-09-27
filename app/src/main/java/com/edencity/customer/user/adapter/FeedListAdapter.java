package com.edencity.customer.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.FeedBackListEntity;

import java.util.ArrayList;
import java.util.List;


// Created by Ardy on 2020/2/25.
public class FeedListAdapter extends RecyclerView.Adapter {

    public ArrayList<FeedBackListEntity.FeedbackListBean> mList = new ArrayList<>();
    private onItemClickLisener ono;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed_list, viewGroup,false);
            return new ViewHolder1(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

           ViewHolder1 holder = (ViewHolder1) viewHolder;
        FeedBackListEntity.FeedbackListBean listBean = mList.get(i);
        holder.desc.setText(listBean.getContent());
           holder.time.setText(listBean.getCreatetime());
           if (listBean.getReplyContent()!=null && !listBean.getReplyContent().equals("")){
               holder.feed_desc.setText(listBean.getReplyContent());
               holder.feed_desc.setVisibility(View.VISIBLE);
               holder.feed_ok.setVisibility(View.VISIBLE);
           }else{
               holder.feed_desc.setVisibility(View.GONE);
               holder.feed_ok.setVisibility(View.GONE);
           }

           if (listBean.getEvidence1()!=null && !listBean.getEvidence1().equals("")){
               Glide.with(holder.desc.getContext()).load(listBean.getEvidence1()).into(holder.img1);
               holder.img1.setVisibility(View.VISIBLE);
           }else {
               holder.img1.setVisibility(View.GONE);
           }
           if (listBean.getEvidence2()!=null && !listBean.getEvidence2().equals("")){
               Glide.with(holder.desc.getContext()).load(listBean.getEvidence2()).into(holder.img2);
               holder.img2.setVisibility(View.VISIBLE);
           }else{
               holder.img2.setVisibility(View.GONE);
           }
           if (listBean.getEvidence3()!=null && !listBean.getEvidence3().equals("")){
               Glide.with(holder.desc.getContext()).load(listBean.getEvidence3()).into(holder.img3);
               holder.img3.setVisibility(View.VISIBLE);
           }else{
               holder.img3.setVisibility(View.GONE);
           }
           holder.itemView.setOnClickListener(v -> {
               if (ono!=null){
                   ono.onItemClick(i);
               }
           });

    }


    @Override
    public int getItemCount() {
        return mList.size()==0?0:mList.size();
    }

    public void addData(List<FeedBackListEntity.FeedbackListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public void addNewData(List<FeedBackListEntity.FeedbackListBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder1 extends RecyclerView.ViewHolder {



        private final MyNormalTextView time;
        private final TextView desc;
        private final TextView feed_desc;
        private final MyMediumTextView feed_ok;
        private final ImageView img3;
        private final ImageView img1;
        private final ImageView img2;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);


            time = itemView.findViewById(R.id.time);
            desc = itemView.findViewById(R.id.desc);
            feed_desc = itemView.findViewById(R.id.feed_desc);
            feed_ok = itemView.findViewById(R.id.feed_ok);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
        }
    }


    public interface onItemClickLisener{
        void onItemClick(int i);
    }
    public void onItemClick(onItemClickLisener lisener){
        ono =lisener;
    }
}

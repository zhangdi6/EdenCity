package com.edencity.customer.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.MsgListEntity;


// Created by Ardy on 2020/2/25.
public class FeedMsgAdapter extends RecyclerView.Adapter {

    public ArrayList<MsgListEntity.MessageListBean.ListBean> mList = new ArrayList<>();
    private onItemClickLisener ono;
    private int a;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i==1){
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed_msg, viewGroup,false);
            return new ViewHolder1(inflate);
        }else{
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_foot, viewGroup,false);
            return new ViewHolder2(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);

        if (itemViewType==1){
           ViewHolder1 holder = (ViewHolder1) viewHolder;
           holder.desc.setText(mList.get(i).getContent());
           holder.time.setText(mList.get(i).getCreatetime());
           holder.title.setText(mList.get(i).getMessageType());
           if (!mList.get(i).getMessageStatus().equals("0")){
               holder.tag.setVisibility(View.GONE);
           }else{
               holder.tag.setVisibility(View.VISIBLE);
           }
           holder.itemView.setOnClickListener(v -> {
               if (ono!=null){
                   ono.onItemClick(i);
               }
           });
        }else{
            ViewHolder2 holder = (ViewHolder2) viewHolder;
            holder.foot_text.setText("没有更多内容了");
        }
    }

    @Override
    public int getItemViewType(int position) {
       if (mList.get(position).getCreatetime()!=null){
           return 1;
       }else{
           return 2;
       }
    }

    @Override
    public int getItemCount() {
        return mList.size()==0?0:mList.size();
    }

    public void addData(List<MsgListEntity.MessageListBean.ListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public void addNewData(List<MsgListEntity.MessageListBean.ListBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public boolean isHadFootView(){
        if (a == 1){
            return true;
        }else {
            return false;
        }
    }
    public void addFootView(List<MsgListEntity.MessageListBean.ListBean> list) {
        a ++ ;
        mList.addAll(list);
        mList.add(new MsgListEntity.MessageListBean.ListBean("1"));
        notifyDataSetChanged();
    }

    public void changeAllState() {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setMessageStatus("1");
        }
        notifyDataSetChanged();
    }

    public void changeState(int i) {
        mList.get(i).setMessageStatus("1");
        notifyItemChanged(i);
    }


    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private final MyMediumTextView title;
        private final TextView tag;
        private final MyNormalTextView time;
        private final MyNormalTextView desc;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            tag = itemView.findViewById(R.id.tag);
            time = itemView.findViewById(R.id.time);
            desc = itemView.findViewById(R.id.desc);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        private final MyNormalTextView foot_text;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            foot_text = itemView.findViewById(R.id.foot_text);

        }
    }

    public interface onItemClickLisener{
        void onItemClick(int i);
    }
    public void onItemClick(onItemClickLisener lisener){
        ono =lisener;
    }
}

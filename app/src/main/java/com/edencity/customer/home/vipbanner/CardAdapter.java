package com.edencity.customer.home.vipbanner;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.util.ResUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// Created by Ardy on 2020/4/6.
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<BaseDebug> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    private onItemClick ono;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        holder.mImageView.setImageResource(mList.get(position).getResource());
        holder.buy_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ono!=null){
                    ono.onItemClick(position);
                }
            }
        });
        holder.name.setText(App.userMsg().getCustomer().getNickName());
        if (App.userMsg().getCustomer().getPortrait()!=null && !App.userMsg().getCustomer().getPortrait().equals("")){
            Picasso.with(holder.image_header.getContext()).load(App.userMsg().getCustomer().getPortrait()).into(holder.image_header);
        }

            switch (mList.get(position).getTitle()) {
                case "1":
                    if (App.userMsg().getCustomer().getMemberTypeName().equals("普通汇员")){
                        /*holder.tag.setText("当前等级");*/
                        holder.tag.setVisibility(View.VISIBLE);
                        holder.tag.setImageResource(R.drawable.taggg2);
                    }else{
                        holder.tag.setVisibility(View.GONE);
                    }
                    holder.level.setText("普通汇员");
                    holder.time.setVisibility(View.GONE);
                    holder.level.setTextColor(Color.parseColor("#85899F"));
                    holder.buy_history.setVisibility(View.GONE);
                    break;
                case "2":
                    /*holder.tag.setVisibility(View.VISIBLE);*/
                    holder.level.setTextColor(Color.parseColor("#2A70B6"));
                    if (App.userMsg().getCustomer().getMemberTypeName().equals("付费汇员")){
                        holder.level.setText("付费汇员");
                        holder.buy_history.setVisibility(View.VISIBLE);
                        /*holder.tag.setText("当前等级");*/
                        holder.tag.setVisibility(View.VISIBLE);
                        holder.tag.setImageResource(R.drawable.taggg2);
                        holder.time.setVisibility(View.VISIBLE);
                        holder.time.setText("有效期至："+App.userMsg().getCustomer().getMemberEndTimeFormat());
                    }else if (App.userMsg().getCustomer().getMemberTypeName().equals("尊享汇员")){
                        holder.level.setText("付费汇员");
                        /*holder.tag.setText("待解锁");*/
                        holder.tag.setVisibility(View.GONE);
                        holder.buy_history.setVisibility(View.GONE);
                        /*holder.tag.setCompoundDrawablesWithIntrinsicBounds(ResUtils.getDrawable(R.mipmap.suo),null,null,null);*/
                        holder.time.setVisibility(View.GONE);
                    }else{
                        holder.level.setText("付费汇员");
                        /*holder.tag.setText("待解锁");*/
                        holder.tag.setVisibility(View.VISIBLE);
                        holder.tag.setImageResource(R.drawable.taggg1);
                        holder.buy_history.setVisibility(View.GONE);
                        /*holder.tag.setCompoundDrawablesWithIntrinsicBounds(ResUtils.getDrawable(R.mipmap.suo),null,null,null);*/
                        holder.time.setVisibility(View.GONE);
                    }
                    break;
                case "3":
                    if (App.userMsg().getCustomer().getMemberTypeName().equals("尊享汇员")){
                        holder.level.setText("尊享汇员");
                        holder.tag.setImageResource(R.drawable.taggg2);
                        holder.buy_history.setVisibility(View.GONE);
                        holder.tag.setVisibility(View.VISIBLE);
                        holder.level.setTextColor(Color.parseColor("#FFDC9E"));
                        holder.time.setVisibility(View.GONE);
                        holder.buy_history.setVisibility(View.GONE);
                    }else{
                        holder.level.setText("尊享汇员");
                        holder.tag.setImageResource(R.drawable.taggg1);
                        holder.buy_history.setVisibility(View.GONE);
                        /*holder.tag.setVisibility(View.GONE);*/
                        holder.level.setTextColor(Color.parseColor("#FFDC9E"));
                        holder.time.setVisibility(View.VISIBLE);
                        holder.buy_history.setVisibility(View.GONE);
                    }

                    break;
            }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(ArrayList<BaseDebug> List) {
        mList .clear();
        mList.addAll(List);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        private final CircleImageView image_header;
        private final MyMediumTextView name;
        private final MyMediumTextView level;
        private final MyNormalTextView buy_history;
        private final ImageView tag;
        private final MyNormalTextView time;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageview);
            image_header = (CircleImageView) itemView.findViewById(R.id.image_header);
            name = (MyMediumTextView) itemView.findViewById(R.id.name);
            level = (MyMediumTextView) itemView.findViewById(R.id.level);
            tag = (ImageView) itemView.findViewById(R.id.tagg);
            time = (MyNormalTextView) itemView.findViewById(R.id.time);
            buy_history = (MyNormalTextView) itemView.findViewById(R.id.buy_history);
        }

    }

    public interface onItemClick{
        void onItemClick(int i);
    }

    public void onItemClick(onItemClick onItemClick){
        ono = onItemClick;
    }
}
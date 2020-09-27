package com.edencity.customer.home.adapter;

/* Created by AdScar
    on 2020/9/22.
      */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edencity.customer.R;
import com.edencity.customer.custum.RoundImageView4dp;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.CustomerEvent;

import java.util.ArrayList;
import java.util.List;

public class RlvImaAdapter extends RecyclerView.Adapter {

    private ArrayList<CustomerEvent.CustomerEventsBean> mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg_rlvimg, viewGroup, false);
       return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

       ViewHolder holder = (ViewHolder) viewHolder;
        CustomerEvent.CustomerEventsBean baseEntity = mList.get(i);


           Glide.with(holder.itemView.getContext()).load(baseEntity.getEventImg()).into(holder.img);


           holder.img.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onn.onClick(i);
               }
           });
    }

    @Override
    public int getItemCount() {
       return Math.max(mList.size(), 0);
    }

    public void addData(List<CustomerEvent.CustomerEventsBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

             private final RoundImageView4dp img;

             public ViewHolder(View itemView) {
                 super(itemView);
                 img = itemView.findViewById(R.id.img);

             }
         }

              private onItemClickListener onn;

              public interface onItemClickListener{
                  void onClick(int position);
              }
              public void onClick(onItemClickListener listener){
                  onn = listener;
              }
}

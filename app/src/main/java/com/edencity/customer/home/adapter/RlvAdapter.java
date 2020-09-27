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
import com.edencity.customer.entity.BaseDebug;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;


public class RlvAdapter extends RecyclerView.Adapter {

    private ArrayList<BaseDebug> mList = new ArrayList<>();
    LinkedList<String> objects = new LinkedList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msg_function, viewGroup, false);
       return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

       ViewHolder holder = (ViewHolder) viewHolder;
        BaseDebug baseEntity = mList.get(i);

        if (baseEntity.getSubtitle().equals("2")){
           holder.layout.setVisibility(View.GONE);
           holder.yidain.setVisibility(View.VISIBLE);


       }else{
           holder.layout.setVisibility(View.VISIBLE);
           holder.yidain.setVisibility(View.GONE);
           Glide.with(holder.itemView.getContext()).load(baseEntity.getResource()).into(holder.logo);
           holder.title.setText(baseEntity.getTitle());
       }
       holder.logo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (onn!=null){
                   onn.onClick(i);
               }
           }
       });
    }

    @Override
    public int getItemCount() {
       return Math.max(mList.size(), 0);
    }

    public void addData(ArrayList<BaseDebug> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

             private final ImageView logo;
             private final TextView title;
             private final ImageView yidain;
             private final RelativeLayout layout;

             public ViewHolder(View itemView) {
                 super(itemView);
                 logo = itemView.findViewById(R.id.logo);
                 title = itemView.findViewById(R.id.title);
                 layout = itemView.findViewById(R.id.layout);
                 yidain = itemView.findViewById(R.id.yidain);
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

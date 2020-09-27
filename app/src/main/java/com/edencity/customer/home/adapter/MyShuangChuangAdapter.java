package com.edencity.customer.home.adapter;

/* Created by AdScar
    on 2020/5/28.
      */

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
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.TabItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MyShuangChuangAdapter extends RecyclerView.Adapter {

       private ArrayList<TabItemEntity.ItemizeListBean> mList = new ArrayList<>();

       @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_team_more, viewGroup, false);
          return new ViewHolder(inflate);
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

          ViewHolder holder = (ViewHolder) viewHolder;
           TabItemEntity.ItemizeListBean baseEntity = mList.get(i);
          Glide.with(holder.itemView.getContext()).load(baseEntity.getItemizePicture()).into(holder.img);
          holder.title.setText(baseEntity.getItemizeName());

       }

       @Override
       public int getItemCount() {
          return Math.max(mList.size(), 0);
       }



    public void addData(List<TabItemEntity.ItemizeListBean> itemizeList) {
        mList.clear();
        mList.addAll(itemizeList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

                private final ImageView img;
                private final MyMediumTextView title;


                public ViewHolder(View itemView) {
                    super(itemView);
                   img = itemView.findViewById(R.id.img);
                   title = itemView.findViewById(R.id.tv);

                }
            }
}

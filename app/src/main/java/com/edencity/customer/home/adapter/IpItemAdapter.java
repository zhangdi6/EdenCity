package com.edencity.customer.home.adapter;

/* Created by AdScar
    on 2020/5/26.
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
import com.edencity.customer.entity.ActiveProductListEntity;
import com.edencity.customer.entity.BaseDebug;

import java.util.ArrayList;
import java.util.List;

public class IpItemAdapter extends RecyclerView.Adapter {

    private ArrayList<ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean>mList = new ArrayList<>();

       @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ip, viewGroup, false);
          return new ViewHolder(inflate);
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

          ViewHolder holder = (ViewHolder) viewHolder;
           ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean goodsBean = mList.get(i);
          Glide.with(holder.itemView.getContext()).load(goodsBean.getListImg()).into(holder.img);
          holder.title.setText(goodsBean.getGoodsName());
          holder.itemView.setOnClickListener(v -> {
              if (onn!=null){
                  onn.onClick(i);
              }
          });
       }

       @Override
       public int getItemCount() {
          return Math.max(mList.size(), 0);
       }

    public void addData(List<ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

                private final ImageView img;
                private final TextView title;
                private final TextView btn;


                public ViewHolder(View itemView) {
                    super(itemView);
                   img = itemView.findViewById(R.id.iv);
                   title = itemView.findViewById(R.id.tv);
                    btn = itemView.findViewById(R.id.btn);
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

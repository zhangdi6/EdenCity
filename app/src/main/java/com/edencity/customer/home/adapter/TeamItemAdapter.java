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
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.TeamEntity;

import java.util.ArrayList;
import java.util.List;

public class TeamItemAdapter extends RecyclerView.Adapter {

     private ArrayList<TeamEntity.EntryTeamsBean> mList = new ArrayList<>();
     private int type;

    public TeamItemAdapter(int i) {
        this.type = i;
    }

    @NonNull
       @Override
       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         if (type==1){
             View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_team, viewGroup, false);
             return new ViewHolder(inflate);
         }else{
             View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_team2, viewGroup, false);
             return new ViewHolder1(inflate);
         }
       }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (type==1){
            ViewHolder holder = (ViewHolder) viewHolder;
            TeamEntity.EntryTeamsBean baseEntity = mList.get(i);
            Glide.with(holder.itemView.getContext()).load(baseEntity.getTeamHeadPicture()).into(holder.img);
            holder.title.setText(baseEntity.getTeamName());
            holder.location.setText(baseEntity.getTeamDesc());
        }else{
            ViewHolder1 holder = (ViewHolder1) viewHolder;
            TeamEntity.EntryTeamsBean baseEntity = mList.get(i);
            Glide.with(holder.itemView.getContext()).load(baseEntity.getTeamHeadPicture()).into(holder.img);
            holder.title.setText(baseEntity.getTeamName());
            holder.location.setText(baseEntity.getTeamDesc());
        }


          viewHolder.itemView.setOnClickListener(v -> {
              if (onn!=null){
                  onn.onClick(i);
              }
          });
       }

       @Override
       public int getItemCount() {
          return Math.max(mList.size(), 0);
       }

    public void addData(List<TeamEntity.EntryTeamsBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

                private final ImageView img;
                private final TextView title;
            private final MyNormalTextView location;

                public ViewHolder(View itemView) {
                    super(itemView);
                   img = itemView.findViewById(R.id.iv);
                   title = itemView.findViewById(R.id.tv);
                    location = itemView.findViewById(R.id.location);
                }
            }
    class ViewHolder1 extends RecyclerView.ViewHolder{

                private final ImageView img;
                private final MyMediumTextView title;
                private final MyNormalTextView location;


                public ViewHolder1(View itemView) {
                    super(itemView);
                   img = itemView.findViewById(R.id.iv);
                   title = itemView.findViewById(R.id.tv);
                    location = itemView.findViewById(R.id.location);
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

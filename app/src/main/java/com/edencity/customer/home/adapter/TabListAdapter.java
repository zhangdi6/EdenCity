package com.edencity.customer.home.adapter;

/* Created by AdScar
    on 2020/5/20.
      */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.RoundImageView10dp;
import com.edencity.customer.entity.BaseDebug;

import java.util.ArrayList;

public class TabListAdapter extends RecyclerView.Adapter {

    private ArrayList<BaseDebug> mList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tab_card, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder view = (ViewHolder) viewHolder;
        BaseDebug baseDebug = mList.get(i);
        Glide.with(view.itemView.getContext()).load(baseDebug.getResource()).into(view.img);
        view.tv.setText(baseDebug.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void addData(ArrayList<BaseDebug> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView img;
        private final MyNormalTextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img  = itemView.findViewById(R.id.img);
            tv  = itemView.findViewById(R.id.tv_tt);

        }
    }
}

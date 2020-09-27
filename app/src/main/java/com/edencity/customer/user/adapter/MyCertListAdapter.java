package com.edencity.customer.user.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.custum.RoundImageView4dp;
import com.edencity.customer.entity.ConvertListEntity;
import com.edencity.customer.util.ButtonUtils;
import com.edencity.customer.util.DateFormatUtils;


// Created by Ardy on 2020/2/12.

public class MyCertListAdapter extends RecyclerView.Adapter {

    private ArrayList<ConvertListEntity.ConvertedListBean>mList = new ArrayList<>();
    private onItemClickListener ono;
    private int type;

    public MyCertListAdapter(int type) {

        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_certlist, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

       ViewHolder holder = (ViewHolder) viewHolder;
        ConvertListEntity.ConvertedListBean baseDebug = mList.get(i);


        holder.shop_count.setText("数量："+baseDebug.getConvertedAmount());


        if (type==0){
            holder.shop_tag.setVisibility(View.GONE);
            String createtime = baseDebug.getValidtime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatUtils.FORMAT_6, Locale.CHINA);
            try {
                Date parse = simpleDateFormat.parse(createtime);
                String format = new SimpleDateFormat(DateFormatUtils.FORMAT_5, Locale.CHINA).format(parse);
                holder.shop_time.setText("有效期至："+createtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if (type == 1){
            holder.shop_tag.setVisibility(View.GONE);
            String createtime = baseDebug.getConvertedTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatUtils.FORMAT_6, Locale.CHINA);
            try {
                Date parse = simpleDateFormat.parse(createtime);
                String format = new SimpleDateFormat(DateFormatUtils.FORMAT_5, Locale.CHINA).format(parse);
                holder.shop_time.setText("使用时间："+createtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if (type ==2){
            holder.shop_tag.setVisibility(View.GONE);
            String createtime = baseDebug.getValidtime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatUtils.FORMAT_6, Locale.CHINA);
            try {
                Date parse = simpleDateFormat.parse(createtime);
                String format = new SimpleDateFormat(DateFormatUtils.FORMAT_5, Locale.CHINA).format(parse);
                holder.shop_time.setText("有效期至："+createtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            holder.shop_tag.setVisibility(View.VISIBLE);
            if (baseDebug.getConvertedStatus().equals("0") && !baseDebug.getConvertedStatusName().contains("过期")){
                holder.shop_tag.setTextColor(Color.parseColor("#F59D07"));
                holder.shop_tag.setText("待使用");
            }else if (baseDebug.getConvertedStatus().equals("1")){
                holder.shop_tag.setTextColor(Color.parseColor("#333333"));
                holder.shop_tag.setText("已使用");
            }else if (baseDebug.getConvertedStatus().equals("2")||baseDebug.getConvertedStatusName().contains("过期")){
                holder.shop_tag.setTextColor(Color.parseColor("#999999"));
                holder.shop_tag.setText("已过期");
            }else{

            }

            if (baseDebug.getConvertedTime()==null || baseDebug.getConvertedTime().equals("")){
                String createtime = baseDebug.getValidtime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatUtils.FORMAT_6, Locale.CHINA);
                try {
                    Date parse = simpleDateFormat.parse(createtime);
                    String format = new SimpleDateFormat(DateFormatUtils.FORMAT_5, Locale.CHINA).format(parse);
                    holder.shop_time.setText("有效期至："+createtime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                String createtime = baseDebug.getConvertedTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatUtils.FORMAT_6, Locale.CHINA);
                try {
                    Date parse = simpleDateFormat.parse(createtime);
                    String format = new SimpleDateFormat(DateFormatUtils.FORMAT_5, Locale.CHINA).format(parse);
                    holder.shop_time.setText("使用时间："+createtime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }


        holder.shop_name.setText(baseDebug.getGoodsName());


        holder.shop_cert_code.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.shop_cert_code)){
                if (ono!=null){
                    ono.onItemClick(i);
                }
            }
        });
        Glide.with(holder.itemView.getContext()).load(baseDebug.getListImg()).into(holder.shop_img);
        Glide.with(holder.itemView.getContext()).load(R.mipmap.cert_code).into(holder.shop_cert_code);

    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public void addData(ArrayList<ConvertListEntity.ConvertedListBean> objects) {
        mList.clear();
        mList.addAll(objects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RoundImageView4dp shop_img;
        private final MyMediumTextView shop_name;
        private final MyNormalTextView shop_time;

        private final MyNormalTextView shop_count;
        private final MyNormalTextView shop_tag;
        private final ImageView shop_cert_code;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_img = itemView.findViewById(R.id.shop_img);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_time = itemView.findViewById(R.id.shop_time);

            shop_count = itemView.findViewById(R.id.shop_count);
            shop_tag = itemView.findViewById(R.id.shop_tag);
            shop_cert_code = itemView.findViewById(R.id.shop_cert_code);

        }
    }
    public interface onItemClickListener{
        void onItemClick(int postion);
    }
    public void onItemClick(onItemClickListener listener){
        ono = listener;
    }
}

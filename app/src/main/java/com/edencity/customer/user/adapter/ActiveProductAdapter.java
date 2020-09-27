package com.edencity.customer.user.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.ActiveProductListEntity;
import com.edencity.customer.util.ButtonUtils;


// Created by Ardy on 2020/2/3.
public class ActiveProductAdapter extends RecyclerView.Adapter {

    private Context context;
    private onItemGetClickListener onItemGetClick;

    private ArrayList<ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean>mList = new ArrayList<>();
    private onItemClickListener onItemClick;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         context = viewGroup.getContext();
        View inflate =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_active_product, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder1 = (ViewHolder) viewHolder;
        ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean goodsBean = mList.get(i);
        viewHolder1.item_name.setText(goodsBean.getGoodsName());
        if (goodsBean.getGoodsType().equals("1")){
            viewHolder1.item_pricw.setText(goodsBean.getDiscount()+"折");
        }else{
            viewHolder1.item_pricw.setText(goodsBean.getPrice()+"元");
            viewHolder1.item_pricw.setPaintFlags(viewHolder1.item_pricw.getPaintFlags() |
                    Paint.STRIKE_THRU_TEXT_FLAG);
        }

        Glide.with(context).load(goodsBean.getListImg()).into(viewHolder1.item_iv);

        Log.e("tag",goodsBean.toString());
        if (!goodsBean.isConvertable() && goodsBean.getCauseOf()!=null && !goodsBean.getCauseOf().equals("")){
            viewHolder1.get.setEnabled(false);
            viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);
            viewHolder1.get.setText(goodsBean.getCauseOf());


        }else{
            if (goodsBean.getHadGetCount()<goodsBean.getAllCount()){
                viewHolder1.get.setEnabled(true);
                viewHolder1.get.setBackgroundResource(R.drawable.text_bg_blue);
                viewHolder1.get.setText("领取 "+goodsBean.getHadGetCount()+"/"+
                        goodsBean.getAllCount());
            }else{
                viewHolder1.get.setText("领取 "+goodsBean.getAllCount()+"/"+
                        goodsBean.getAllCount());
                viewHolder1.get.setEnabled(false);
                viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);
            }

        }

        viewHolder1.get.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.get)){
            if (onItemGetClick!=null){
                onItemGetClick.onItemGetClick(i);
            }
            }

        });
        viewHolder1.mLl.setOnClickListener(v -> {
            if (!ButtonUtils.isFastDoubleClick(R.id.ll)){
                if (onItemClick!=null){
                    onItemClick.onItemClick(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Math.max(mList.size(), 0);
    }

    public void addData(List<ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean> goods
                        ) {
        mList.clear();
        mList.addAll(goods);
        notifyDataSetChanged();
    }



    public void addNewData(List<ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean> goods) {
        mList.addAll(goods);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView item_iv;
        private final MyNormalTextView get;
        private final MyNormalTextView item_pricw;
        private final TextView item_name;
        private final RelativeLayout mLl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_iv  = itemView.findViewById(R.id.item_iv);
            item_pricw= itemView.findViewById(R.id.item_price);
            get= itemView.findViewById(R.id.get);
            item_name = itemView.findViewById(R.id.item_name);
            mLl = itemView.findViewById(R.id.ll);
        }
    }

    public interface onItemGetClickListener{
        void onItemGetClick(int position);
    }
    public void onItemGetClick(onItemGetClickListener listener){
        onItemGetClick = listener ;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void onItemClick(onItemClickListener listener){
        onItemClick = listener ;
    }
}

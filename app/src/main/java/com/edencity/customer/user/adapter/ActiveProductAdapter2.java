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
import com.edencity.customer.R;
import com.edencity.customer.custum.MyNormalTextView;
import com.edencity.customer.entity.ActiveProductListEntity;
import com.edencity.customer.util.ButtonUtils;

import java.util.ArrayList;
import java.util.List;


// Created by Ardy on 2020/2/3.
public class ActiveProductAdapter2 extends RecyclerView.Adapter {

    private Context context;
    private onItemGetClickListener onItemGetClick;

    private ArrayList<ActiveProductListEntity.CurrentMembershipConvertedAvailableGoodsBean>mList = new ArrayList<>();
    private onItemClickListener onItemClick;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         context = viewGroup.getContext();
        View inflate =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_active_product2, null);
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



        if (!goodsBean.isConvertable()){
            Log.e("tag",goodsBean.toString());
            if (goodsBean.getAllCount() <= goodsBean.getHadGetCount()){
                viewHolder1.get.setText("领取");
                viewHolder1.get.setEnabled(false);
                viewHolder1.item_gray.setVisibility(View.VISIBLE);
                viewHolder1.item_gray_iv.setImageResource(R.mipmap.yuelingwan);
                String m = goodsBean.getConvertedFrequency().equals("m") ? "月" : "年";
                viewHolder1.item_gray_tv.setText("本"+ m +"\n"+"已领取");
                viewHolder1.tag.setVisibility(View.GONE);
                viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);

            }

                if (goodsBean.getCauseOf().equals("活跃值不足")){
                    viewHolder1.get.setText("领取");
                    viewHolder1.get.setEnabled(false);
                    viewHolder1.item_gray.setVisibility(View.GONE);
                    viewHolder1.tag.setVisibility(View.VISIBLE);
                    viewHolder1.tag.setText("满"+goodsBean.getMinActivityNeeded()+"点活跃值可领取");
                    viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);
                }else if (goodsBean.getCauseOf().equals("库存不足")){
                    viewHolder1.get.setText("领取");
                    viewHolder1.get.setEnabled(false);
                    viewHolder1.item_gray.setVisibility(View.VISIBLE);
                    viewHolder1.item_gray_iv.setImageResource(R.mipmap.yilingwan);
                    viewHolder1.item_gray_tv.setText("库存不足");
                    viewHolder1.tag.setVisibility(View.GONE);
                    viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);
                }




        }else{
            Log.e("tag2",goodsBean.toString());
            if (goodsBean.getHadGetCount()<goodsBean.getAllCount()){
                viewHolder1.get.setEnabled(true);
                viewHolder1.get.setBackgroundResource(R.drawable.text_bg_blue);
                /*viewHolder1.get.setText("领取 "+goodsBean.getHadGetCount()+"/"+
                        goodsBean.getAllCount());*/
                viewHolder1.get.setText("领取");
                viewHolder1.item_gray.setVisibility(View.GONE);
                viewHolder1.tag.setVisibility(View.GONE);

                if (goodsBean.getCauseOf().equals("库存不足")){
                    viewHolder1.get.setText("领取");
                    viewHolder1.get.setEnabled(false);
                    viewHolder1.item_gray.setVisibility(View.VISIBLE);
                    viewHolder1.item_gray_iv.setImageResource(R.mipmap.yilingwan);
                    viewHolder1.item_gray_tv.setText("库存不足");
                    viewHolder1.tag.setVisibility(View.GONE);
                    viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);
                }

            }else{


                    viewHolder1.get.setText("领取");
                    viewHolder1.get.setEnabled(false);
                    viewHolder1.item_gray.setVisibility(View.VISIBLE);
                    viewHolder1.item_gray_iv.setImageResource(R.mipmap.yuelingwan);

                    String m = goodsBean.getConvertedFrequency().equals("m") ? "月" : "年";
                    viewHolder1.item_gray_tv.setText("本"+ m +"已领取");
                    viewHolder1.tag.setVisibility(View.GONE);
                    viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);


                if (goodsBean.getCauseOf().equals("活跃值不足")){
                    viewHolder1.get.setText("领取");
                    viewHolder1.get.setEnabled(false);
                    viewHolder1.item_gray.setVisibility(View.GONE);
                    viewHolder1.tag.setVisibility(View.VISIBLE);
                    viewHolder1.tag.setText("满"+goodsBean.getMinActivityNeeded()+"点活跃值可领取");
                    viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);
                }else if (goodsBean.getCauseOf().equals("库存不足")){
                    viewHolder1.get.setText("领取");
                    viewHolder1.get.setEnabled(false);
                    viewHolder1.item_gray.setVisibility(View.VISIBLE);
                    viewHolder1.item_gray_iv.setImageResource(R.mipmap.yilingwan);
                    viewHolder1.item_gray_tv.setText("库存不足");
                    viewHolder1.tag.setVisibility(View.GONE);
                    viewHolder1.get.setBackgroundResource(R.drawable.text_bg_gray);
                }


            }


        }

        /*viewHolder1.item_need_active.setText("所需活跃值"+goodsBean.getActivityNeeded()+"点");*/

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
        private final RelativeLayout item_gray;
        private final ImageView item_gray_iv;
        private final TextView item_gray_tv;
        private final MyNormalTextView item_need_active;
        private final TextView tag;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_iv  = itemView.findViewById(R.id.item_iv);
            item_pricw= itemView.findViewById(R.id.item_price);
            get= itemView.findViewById(R.id.get);
            item_name = itemView.findViewById(R.id.item_name);
            item_gray = itemView.findViewById(R.id.item_gray);
            item_gray_iv = itemView.findViewById(R.id.item_gray_iv);
            item_gray_tv = itemView.findViewById(R.id.item_gray_tv);
            mLl = itemView.findViewById(R.id.ll);
            item_need_active = (MyNormalTextView)itemView.findViewById(R.id.item_need_active);
            tag = itemView.findViewById(R.id.tag);

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

package com.edencity.customer.home.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.edencity.customer.App;
import com.edencity.customer.R;
import com.edencity.customer.base.BaseActivity;
import com.edencity.customer.custum.MyMediumTextView;
import com.edencity.customer.custum.statubar.StatusBarCompat;
import com.edencity.customer.entity.BaseDebug;
import com.edencity.customer.entity.UserMsgEntity;
import com.edencity.customer.home.adapter.CardListAapter;

public class CardActivity extends BaseActivity {

    private RecyclerView mRlvCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //改变状态栏背景颜色
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        //改变状态栏字体颜色
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_card);
        initView();
        initData();
    }

    private void initData() {
        ArrayList<BaseDebug> list = new ArrayList<>();
        if (App.userMsg()!=null && App.userMsg().getAccount()!=null
                && App.userMsg().getAccount().getCardList()!=null
                && App.userMsg().getAccount().getCardList().size()>0){
            List<UserMsgEntity.AccountBean.CardListBean> cardList = App.userMsg().getAccount().getCardList();
            for (int i = 0; i < cardList.size(); i++) {
                list.add(new BaseDebug(cardList.get(i).getTotalBalance()+"",
                        cardList.get(i).getCardCode()));
            }
        }

        mRlvCard.setLayoutManager(new LinearLayoutManager(this));
        CardListAapter cardListAapter = new CardListAapter();
        mRlvCard.setAdapter(cardListAapter);
        cardListAapter.addData(list);
    }

    private void initView() {
        ImageView mBack = findViewById(R.id.back);
        /**
         * 预付卡
         */
        MyMediumTextView mTitle = findViewById(R.id.title);
        mRlvCard = findViewById(R.id.rlv_card);
        mBack.setOnClickListener(v -> finish());
    }
}

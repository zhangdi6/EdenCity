package com.edencity.customer.entity;

// Created by Ardy on 2020/1/6.
public class RechargeMoneyEntity {
    private String price ;
    private String top_price ;
    private boolean check ;

    public RechargeMoneyEntity(String price, String top_price, boolean check) {
        this.price = price;
        this.top_price = top_price;
        this.check = check;
    }

    @Override
    public String toString() {
        return "RechargeMoneyEntity{" +
                "price='" + price + '\'' +
                ", top_price='" + top_price + '\'' +
                ", check=" + check +
                '}';
    }

    public RechargeMoneyEntity() {
        super();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTop_price() {
        return top_price;
    }

    public void setTop_price(String top_price) {
        this.top_price = top_price;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

package com.edencity.customer.entity;

// Created by Ardy on 2020/2/6.
public class WeekEntity {
    private int id;
    private String day;
    private boolean isCheck ;

    public WeekEntity(int id ,String day, boolean isCheck) {
        this.id= id;
        this.day = day;
        this.isCheck = isCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

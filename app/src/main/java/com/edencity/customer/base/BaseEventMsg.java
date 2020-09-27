package com.edencity.customer.base;

// Created by Ardy on 2020/1/13.
public class BaseEventMsg {
    public String msg;
    public String type;
    public String paramStr;
    public String paramStr2;
    public int paramInt;

    public BaseEventMsg(String msg, String type, String paramStr, int paramInt) {
        this.msg = msg;
        this.type = type;
        this.paramStr = paramStr;
        this.paramInt = paramInt;
    }



    public BaseEventMsg(String msg, String type, String paramStr, String paramStr2) {
        this.msg = msg;
        this.type = type;
        this.paramStr = paramStr;
        this.paramStr2 = paramStr2;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public int getParamInt() {
        return paramInt;
    }

    public void setParamInt(int paramInt) {
        this.paramInt = paramInt;
    }

    public String getParamStr2() {
        return paramStr2;
    }

    public void setParamStr2(String paramStr2) {
        this.paramStr2 = paramStr2;
    }
}

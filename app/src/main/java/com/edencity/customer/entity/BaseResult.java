package com.edencity.customer.entity;

// Created by Ardy on 2020/1/6.

public class BaseResult<T> {

    /**
     * result_code : -7
     * result_msg : 该手机号不是注册用户,请先进行注册
     */

    private int result_code;
    private String result_msg;
    private T data;

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "result_code=" + result_code +
                ", result_msg='" + result_msg + '\'' +
                ", data=" + data +
                '}';
    }
}

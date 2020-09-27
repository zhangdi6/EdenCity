package com.edencity.customer.pojo;

public class FuncResult<T> {
    public int code;
    public String msg;
    public T data;

    public FuncResult(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public FuncResult(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    @Override
    public String toString() {
        return "FuncResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

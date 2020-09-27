package com.edencity.customer.entity;

// Created by Ardy on 2020/1/15.

public class WeChatPayEntity {

    /**
     * nonce_str : EMYaCpgMOPI41CQB
     * device_info : 9b2b407da09e466a9b6364c6a3c065be
     * appid : wxf6ed4c8b0a27e560
     * sign : 428CC32359380A66F4A132627122FFCD
     * trade_type : APP
     * return_msg : OK
     * result_code : SUCCESS
     * mch_id : 1534479611
     * return_code : SUCCESS
     * prepay_id : wx151536078052239e1c0c46681734902700
     * timestamp : 1579073766
     */

    private String nonce_str;
    private String device_info;
    private String appid;
    private String sign;
    private String trade_type;
    private String return_msg;
    private String result_code;
    private String mch_id;
    private String return_code;
    private String prepay_id;
    private String timestamp;

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WeChatPayEntity{" +
                "nonce_str='" + nonce_str + '\'' +
                ", device_info='" + device_info + '\'' +
                ", appid='" + appid + '\'' +
                ", sign='" + sign + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", result_code='" + result_code + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", return_code='" + return_code + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

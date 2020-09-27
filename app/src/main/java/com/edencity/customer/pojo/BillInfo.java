package com.edencity.customer.pojo;

public class BillInfo {
    public static final int TYPE_ALL = 0;   //全部
    public static final int TYPE_SCAN = 1;  //顾客扫商户二维码
    public static final int TYPE_QRCODE = 2; //商户扫顾客二维码

    //预付卡、小程序
    public String cardType;
    public String payStatus;

    //是否是充值单  true:充值  false:消费
    public boolean isDeposit;

    public String payTypeName;
    public int payType;
    public String payFee;
    public String orderNo;
    public String orderTime;

    public static String getTypeName(int type){
        switch (type){
            case 0:
                return "全部";
            case 1:
                return "扫码支付";
            case 2:
                return "二维码支付";
            default:
                return "未知";
        }
    }
}

package com.edencity.customer.entity;

// Created by Ardy on 2020/3/24.
public class PayResultEntity {

    /**
     * payResult : {"mydealId":"916d60d0f4d5446786a47d410a427f5a","dealtime":"2020-03-24 10:18:54","orderId":"01202003241018541751","dealAmount":0.01,"payMethod":"二维码收款（用户被扫）","userId":"3efd3603b59c4ad28829042919f98e6a","dealInfo":"db09c94fc8d44e9c97664494c9ac7970"}
     */

    private PayResultBean payResult;

    public PayResultBean getPayResult() {
        return payResult;
    }

    public void setPayResult(PayResultBean payResult) {
        this.payResult = payResult;
    }

    public static class PayResultBean {
        /**
         * mydealId : 916d60d0f4d5446786a47d410a427f5a
         * dealtime : 2020-03-24 10:18:54
         * orderId : 01202003241018541751
         * dealAmount : 0.01
         * payMethod : 二维码收款（用户被扫）
         * userId : 3efd3603b59c4ad28829042919f98e6a
         * dealInfo : db09c94fc8d44e9c97664494c9ac7970
         */

        private String mydealId;
        private String dealtime;
        private String orderId;
        private double dealAmount;
        private String payMethod;
        private String userId;
        private String dealInfo;

        @Override
        public String toString() {
            return "PayResultBean{" +
                    "mydealId='" + mydealId + '\'' +
                    ", dealtime='" + dealtime + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", dealAmount=" + dealAmount +
                    ", payMethod='" + payMethod + '\'' +
                    ", userId='" + userId + '\'' +
                    ", dealInfo='" + dealInfo + '\'' +
                    '}';
        }

        public String getMydealId() {
            return mydealId;
        }

        public void setMydealId(String mydealId) {
            this.mydealId = mydealId;
        }

        public String getDealtime() {
            return dealtime;
        }

        public void setDealtime(String dealtime) {
            this.dealtime = dealtime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getDealAmount() {
            return dealAmount;
        }

        public void setDealAmount(double dealAmount) {
            this.dealAmount = dealAmount;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDealInfo() {
            return dealInfo;
        }

        public void setDealInfo(String dealInfo) {
            this.dealInfo = dealInfo;
        }
    }

    @Override
    public String toString() {
        return "PayResultEntity{" +
                "payResult=" + payResult +
                '}';
    }
}

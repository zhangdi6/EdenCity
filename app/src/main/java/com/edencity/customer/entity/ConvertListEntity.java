package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/2/14.

public class ConvertListEntity {

    private List<ConvertedListBean> convertedList;

    public List<ConvertedListBean> getConvertedList() {
        return convertedList;
    }

    public void setConvertedList(List<ConvertedListBean> convertedList) {
        this.convertedList = convertedList;
    }

    public static class ConvertedListBean {
        /**
         * createtime : 2020-02-13 22:31:12
         * convertedAmount : 1
         * activityConvertedId : bc2a04a241924004a7b07f8de33e65ba
         * convertedGoodsId : c79d9c7e601e4701a0ccf8cf43bdaa7f
         * convertedStatus : 0
         * validtime : 2020-03-14 22:31:12
         * userId : d6ec2f0a24af4196862a6ddc3332a328
         * goodsName : 测试代金券
         * listImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/activity/goods/nulllistImg/1581561614110_1556809471837.jpeg
         */

        private String createtime;
        private int convertedAmount;
        private String activityConvertedId;
        private String convertedStatusName;
        private String convertedGoodsId;
        private String convertedStatus;
        private String validtime;
        private String convertedTime;
        private String userId;
        private String goodsName;
        private String listImg;

        public String getCreatetime() {
            return createtime;
        }

        public String getConvertedTime() {
            return convertedTime;
        }

        public void setConvertedTime(String convertedTime) {
            this.convertedTime = convertedTime;
        }

        public String getConvertedStatusName() {
            return convertedStatusName;
        }

        public void setConvertedStatusName(String convertedStatusName) {
            this.convertedStatusName = convertedStatusName;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getConvertedAmount() {
            return convertedAmount;
        }

        public void setConvertedAmount(int convertedAmount) {
            this.convertedAmount = convertedAmount;
        }

        public String getActivityConvertedId() {
            return activityConvertedId;
        }

        public void setActivityConvertedId(String activityConvertedId) {
            this.activityConvertedId = activityConvertedId;
        }

        public String getConvertedGoodsId() {
            return convertedGoodsId;
        }

        public void setConvertedGoodsId(String convertedGoodsId) {
            this.convertedGoodsId = convertedGoodsId;
        }

        public String getConvertedStatus() {
            return convertedStatus;
        }

        public void setConvertedStatus(String convertedStatus) {
            this.convertedStatus = convertedStatus;
        }

        public String getValidtime() {
            return validtime;
        }

        public void setValidtime(String validtime) {
            this.validtime = validtime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getListImg() {
            return listImg;
        }

        public void setListImg(String listImg) {
            this.listImg = listImg;
        }
    }
}

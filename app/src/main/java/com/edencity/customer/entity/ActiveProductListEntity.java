package com.edencity.customer.entity;

// Created by Ardy on 2020/2/13.

import java.io.Serializable;
import java.util.List;

public class ActiveProductListEntity implements Serializable {

    private static final long serialVersionUID = -8601470685062559424L;
    private List<CurrentMembershipConvertedAvailableGoodsBean> currentMembershipConvertedAvailableGoods;
    private List<CurrentActivityConvertedAvailableGoodsBean> currentActivityConvertedAvailableGoods;

    public List<CurrentMembershipConvertedAvailableGoodsBean> getCurrentMembershipConvertedAvailableGoods() {
        return currentMembershipConvertedAvailableGoods;
    }

    public void setCurrentMembershipConvertedAvailableGoods(List<CurrentMembershipConvertedAvailableGoodsBean> currentMembershipConvertedAvailableGoods) {
        this.currentMembershipConvertedAvailableGoods = currentMembershipConvertedAvailableGoods;
    }

    public List<CurrentActivityConvertedAvailableGoodsBean> getCurrentActivityConvertedAvailableGoods() {
        return currentActivityConvertedAvailableGoods;
    }

    public void setCurrentActivityConvertedAvailableGoods(List<CurrentActivityConvertedAvailableGoodsBean> currentActivityConvertedAvailableGoods) {
        this.currentActivityConvertedAvailableGoods = currentActivityConvertedAvailableGoods;
    }

    @Override
    public String toString() {
        return "ActiveProductListEntity{" +
                "currentMembershipConvertedAvailableGoods=" + currentMembershipConvertedAvailableGoods +
                ", currentActivityConvertedAvailableGoods=" + currentActivityConvertedAvailableGoods +
                '}';
    }

    public static class CurrentMembershipConvertedAvailableGoodsBean implements Serializable{
        private static final long serialVersionUID = 8250866004572121677L;

        @Override
        public String toString() {
            return "CurrentMembershipConvertedAvailableGoodsBean{" +
                    "amount=" + amount +
                    ", GoodsType='" + GoodsType + '\'' +
                    ", unit='" + unit + '\'' +
                    ", UNIT='" + UNIT + '\'' +
                    ", convertedFrequency='" + convertedFrequency + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    ", price=" + price +
                    ", discount=" + discount +
                    ", minActivityNeeded=" + minActivityNeeded +
                    ", goodsName='" + goodsName + '\'' +
                    ", membershipNeeded='" + membershipNeeded + '\'' +
                    ", goodsSpecification='" + goodsSpecification + '\'' +
                    ", listImg='" + listImg + '\'' +
                    ", convertable=" + convertable +
                    ", causeOf='" + causeOf + '\'' +
                    ", hadGetCount=" + hadGetCount +
                    ", activityNeeded=" + activityNeeded +
                    ", allCount=" + allCount +
                    '}';
        }

        /**
         * amount : 200
         * GoodsType : 2
         * unit : 张
         * UNIT : 张
         * goodsId : c79d9c7e601e4701a0ccf8cf43bdaa7f
         * price : 20
         * discount : 100
         * goodsName : 测试代金券
         * listImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/activity/goods/
         * nulllistImg/1581561614110_1556809471837.jpeg
         */

        private int amount;
        private String GoodsType;
        private String unit;
        private String UNIT;
        private String convertedFrequency = "y";

        private String goodsId;
        private int price;
        private int discount;
        private int minActivityNeeded;
        private String goodsName;
        private String membershipNeeded;
        private String goodsSpecification;
        private String listImg;
        private boolean convertable; //是否可领取
        private String causeOf = "";   //不可领取的原因

        private int hadGetCount; //已领取
        private int activityNeeded; //需要的活跃值
        private int allCount;  //总数量


        public int getMinActivityNeeded() {
            return minActivityNeeded;
        }

        public void setMinActivityNeeded(int minActivityNeeded) {
            this.minActivityNeeded = minActivityNeeded;
        }

        public String getConvertedFrequency() {
            return convertedFrequency;
        }

        public void setConvertedFrequency(String convertedFrequency) {
            this.convertedFrequency = convertedFrequency;
        }

        public String getCauseOf() {
            return causeOf;
        }

        public int getActivityNeeded() {
            return activityNeeded;
        }

        public void setActivityNeeded(int activityNeeded) {
            this.activityNeeded = activityNeeded;
        }

        public void setCauseOf(String causeOf) {
            this.causeOf = causeOf;
        }

        public boolean isConvertable() {
            return convertable;
        }

        public String getGoodsSpecification() {
            return goodsSpecification;
        }

        public String getMembershipNeeded() {
            return membershipNeeded;
        }

        public void setGoodsSpecification(String goodsSpecification) {
            this.goodsSpecification = goodsSpecification;
        }

        public void setMembershipNeeded(String membershipNeeded) {
            this.membershipNeeded = membershipNeeded;
        }

        public int getHadGetCount() {
            return hadGetCount;
        }

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public void setHadGetCount(int hadGetCount) {
            this.hadGetCount = hadGetCount;
        }

        public void setConvertable(boolean convertable) {
            this.convertable = convertable;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getGoodsType() {
            return GoodsType;
        }

        public void setGoodsType(String GoodsType) {
            this.GoodsType = GoodsType;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUNIT() {
            return UNIT;
        }

        public void setUNIT(String UNIT) {
            this.UNIT = UNIT;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
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

    public static class CurrentActivityConvertedAvailableGoodsBean implements Serializable{
        private static final long serialVersionUID = 3426198119708825691L;

        @Override
        public String toString() {
            return "CurrentActivityConvertedAvailableGoodsBean{" +
                    "convertedFrequency='" + convertedFrequency + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    ", convertedLimited=" + convertedLimited +
                    ", activityNeeded=" + activityNeeded +
                    ", hadConverted=" + hadConverted +
                    '}';
        }

        /**
         * convertedFrequency : m
         * goodsId : c79d9c7e601e4701a0ccf8cf43bdaa7f
         * convertedLimited : 3
         * activityNeeded : 100
         * hadConverted : 0
         */


        private String convertedFrequency;
        private String goodsId;
        private int convertedLimited;
        private int activityNeeded;
        private int hadConverted;

        public String getConvertedFrequency() {
            return convertedFrequency;
        }

        public void setConvertedFrequency(String convertedFrequency) {
            this.convertedFrequency = convertedFrequency;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public int getConvertedLimited() {
            return convertedLimited;
        }

        public void setConvertedLimited(int convertedLimited) {
            this.convertedLimited = convertedLimited;
        }

        public int getActivityNeeded() {
            return activityNeeded;
        }

        public void setActivityNeeded(int activityNeeded) {
            this.activityNeeded = activityNeeded;
        }

        public int getHadConverted() {
            return hadConverted;
        }

        public void setHadConverted(int hadConverted) {
            this.hadConverted = hadConverted;
        }
    }
}

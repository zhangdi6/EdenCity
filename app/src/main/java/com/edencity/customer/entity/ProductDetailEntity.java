package com.edencity.customer.entity;

// Created by Ardy on 2020/4/2.
public class ProductDetailEntity {

    /**
     * goodsDetail : {"amount":0,"convertedGoodsId":"65d930d758454b0a9749530116126d68","createby":"","createtime":null,"discount":0,"editby":"","edittime":null,"goodsDetail":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193933306_131.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193933730_IMG_0126.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193933745_IMG_1274.jpg,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193942331_IMG_1330.jpg","goodsDisplay":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDisplay/1585193932832_IMG_0237.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDisplay/1585193932849_IMG_0016.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDisplay/1585193932866_IMG_0065.JPG","goodsName":"","goodsSkuCode":"","goodsSpecification":"","goodsStatus":"","goodsType":"","listImg":"","lockAmount":0,"price":0,"priceType":"","unit":"","validPeriod":0}
     */

    private GoodsDetailBean goodsDetail;

    public GoodsDetailBean getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(GoodsDetailBean goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public static class GoodsDetailBean {
        /**
         * amount : 0
         * convertedGoodsId : 65d930d758454b0a9749530116126d68
         * createby :
         * createtime : null
         * discount : 0
         * editby :
         * edittime : null
         * goodsDetail : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193933306_131.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193933730_IMG_0126.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193933745_IMG_1274.jpg,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDetail/1585193942331_IMG_1330.jpg
         * goodsDisplay : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDisplay/1585193932832_IMG_0237.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDisplay/1585193932849_IMG_0016.JPG,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/converted/goods/65d930d758454b0a9749530116126d68goodsDisplay/1585193932866_IMG_0065.JPG
         * goodsName :
         * goodsSkuCode :
         * goodsSpecification :
         * goodsStatus :
         * goodsType :
         * listImg :
         * lockAmount : 0
         * price : 0
         * priceType :
         * unit :
         * validPeriod : 0
         */

        private int amount;
        private String convertedGoodsId;
        private String createby;
        private String createtime;
        private int discount;
        private String editby;
        private String edittime;
        private String goodsDetail;
        private String goodsDisplay;
        private String goodsName;
        private String goodsSkuCode;
        private String goodsSpecification;
        private String goodsStatus;
        private String goodsType;
        private String listImg;
        private int lockAmount;
        private int price;
        private String priceType;
        private String unit;
        private int validPeriod;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getConvertedGoodsId() {
            return convertedGoodsId;
        }

        public void setConvertedGoodsId(String convertedGoodsId) {
            this.convertedGoodsId = convertedGoodsId;
        }

        public String getCreateby() {
            return createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public String getEditby() {
            return editby;
        }

        public void setEditby(String editby) {
            this.editby = editby;
        }

        public String getEdittime() {
            return edittime;
        }

        public void setEdittime(String edittime) {
            this.edittime = edittime;
        }

        public String getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(String goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public String getGoodsDisplay() {
            return goodsDisplay;
        }

        public void setGoodsDisplay(String goodsDisplay) {
            this.goodsDisplay = goodsDisplay;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsSkuCode() {
            return goodsSkuCode;
        }

        public void setGoodsSkuCode(String goodsSkuCode) {
            this.goodsSkuCode = goodsSkuCode;
        }

        public String getGoodsSpecification() {
            return goodsSpecification;
        }

        public void setGoodsSpecification(String goodsSpecification) {
            this.goodsSpecification = goodsSpecification;
        }

        public String getGoodsStatus() {
            return goodsStatus;
        }

        public void setGoodsStatus(String goodsStatus) {
            this.goodsStatus = goodsStatus;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public String getListImg() {
            return listImg;
        }

        public void setListImg(String listImg) {
            this.listImg = listImg;
        }

        public int getLockAmount() {
            return lockAmount;
        }

        public void setLockAmount(int lockAmount) {
            this.lockAmount = lockAmount;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getValidPeriod() {
            return validPeriod;
        }

        public void setValidPeriod(int validPeriod) {
            this.validPeriod = validPeriod;
        }
    }
}

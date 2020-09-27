package com.edencity.customer.entity;

// Created by Ardy on 2020/4/1.
public class ShopEntity {

    /**
     * providerDetail : {"businessStatusName":"暂停营业","storeTel":"13552126154","storeFacadeImg":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299storeFacadeImg/1585193289421_WechatIMG82.jpeg","businessStatus":"1002010751","businessTime":null,"storeDetailAddress":"伊甸城","storeAddressTag":"田森汇","storeLatitude":39.916527,"storeLongitude":116.397128,"providerId":"e9486bdc9d10491f9c9896e7b4838cf9","detailImg":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299detailImg/1585380027032_coco详情.jpeg,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299detailImg/1585380027045_coco详情1.png","installationService":null,"storeName":"CoCo都可","displayImg":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299displayImg/1585380027069_coco-1.jpeg","aera":null}
     */

    private ProviderDetailBean providerDetail;

    @Override
    public String toString() {
        return "ShopEntity{" +
                "providerDetail=" + providerDetail +
                '}';
    }

    public ProviderDetailBean getProviderDetail() {
        return providerDetail;
    }

    public void setProviderDetail(ProviderDetailBean providerDetail) {
        this.providerDetail = providerDetail;
    }

    public static class ProviderDetailBean {
        /**
         * businessStatusName : 暂停营业
         * storeTel : 13552126154
         * storeFacadeImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299storeFacadeImg/1585193289421_WechatIMG82.jpeg
         * businessStatus : 1002010751
         * businessTime : null
         * storeDetailAddress : 伊甸城
         * storeAddressTag : 田森汇
         * storeLatitude : 39.916527
         * storeLongitude : 116.397128
         * providerId : e9486bdc9d10491f9c9896e7b4838cf9
         * detailImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299detailImg/1585380027032_coco详情.jpeg,http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299detailImg/1585380027045_coco详情1.png
         * installationService : null
         * storeName : CoCo都可
         * displayImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/manage/5c5e8cdb2b7442c79f0d3a38a0dad299displayImg/1585380027069_coco-1.jpeg
         * aera : null
         */

        private String businessStatusName;
        private String storeTel;
        private String storeFacadeImg;
        private String businessStatus;
        private String businessTime;
        private String storeDetailAddress;
        private String storeAddressTag;
        private long storeLatitude;
        private long storeLongitude;
        private String providerId;
        private String detailImg;
        private String installationService;
        private String storeName;
        private String displayImg;
        private String aera;

        @Override
        public String toString() {
            return "ProviderDetailBean{" +
                    "businessStatusName='" + businessStatusName + '\'' +
                    ", storeTel='" + storeTel + '\'' +
                    ", storeFacadeImg='" + storeFacadeImg + '\'' +
                    ", businessStatus='" + businessStatus + '\'' +
                    ", businessTime='" + businessTime + '\'' +
                    ", storeDetailAddress='" + storeDetailAddress + '\'' +
                    ", storeAddressTag='" + storeAddressTag + '\'' +
                    ", storeLatitude=" + storeLatitude +
                    ", storeLongitude=" + storeLongitude +
                    ", providerId='" + providerId + '\'' +
                    ", detailImg='" + detailImg + '\'' +
                    ", installationService='" + installationService + '\'' +
                    ", storeName='" + storeName + '\'' +
                    ", displayImg='" + displayImg + '\'' +
                    ", aera='" + aera + '\'' +
                    '}';
        }

        public String getBusinessStatusName() {
            return businessStatusName;
        }

        public void setBusinessStatusName(String businessStatusName) {
            this.businessStatusName = businessStatusName;
        }

        public String getStoreTel() {
            return storeTel;
        }

        public void setStoreTel(String storeTel) {
            this.storeTel = storeTel;
        }

        public String getStoreFacadeImg() {
            return storeFacadeImg;
        }

        public void setStoreFacadeImg(String storeFacadeImg) {
            this.storeFacadeImg = storeFacadeImg;
        }

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public String getBusinessTime() {
            return businessTime;
        }

        public void setBusinessTime(String businessTime) {
            this.businessTime = businessTime;
        }

        public String getStoreDetailAddress() {
            return storeDetailAddress;
        }

        public void setStoreDetailAddress(String storeDetailAddress) {
            this.storeDetailAddress = storeDetailAddress;
        }

        public String getStoreAddressTag() {
            return storeAddressTag;
        }

        public void setStoreAddressTag(String storeAddressTag) {
            this.storeAddressTag = storeAddressTag;
        }

        public long getStoreLatitude() {
            return storeLatitude;
        }

        public void setStoreLatitude(long storeLatitude) {
            this.storeLatitude = storeLatitude;
        }

        public long getStoreLongitude() {
            return storeLongitude;
        }

        public void setStoreLongitude(long storeLongitude) {
            this.storeLongitude = storeLongitude;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getDetailImg() {
            return detailImg;
        }

        public void setDetailImg(String detailImg) {
            this.detailImg = detailImg;
        }

        public String getInstallationService() {
            return installationService;
        }

        public void setInstallationService(String installationService) {
            this.installationService = installationService;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getDisplayImg() {
            return displayImg;
        }

        public void setDisplayImg(String displayImg) {
            this.displayImg = displayImg;
        }

        public String getAera() {
            return aera;
        }

        public void setAera(String aera) {
            this.aera = aera;
        }
    }
}

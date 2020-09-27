package com.edencity.customer.entity;

// Created by Ardy on 2020/2/27.
public class ShopDetailEntity {

    @Override
    public String toString() {
        return "ShopDetailEntity{" +
                "providerDetail=" + providerDetail +
                '}';
    }

    /**
     * providerDetail : {"businessStatusName":"营业中","storeFacadeImg":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/bbc57ae84abf4234a60a03b314e2797c/logo/1561425868412_画板 copy 6@3x.JPG","businessStatus":"1002010750","businessTime":"周一至周五：8时至24时","storeDetailAddress":"伊甸城4号楼2层1001","storeAddressTag":"临近伊甸城主城区","storeLatitude":32.0000054,"storeLongitude":47.200001,"providerId":"1234567812345678123456781ceshi01","detailImg":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/bbc57ae84abf4234a60a03b314e2797c/logo/1561425868412_画板 copy 6@3x.JPG","installationService":"可停车,有雅座","storeName":"必胜客伊甸城店","displayImg":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/bbc57ae84abf4234a60a03b314e2797c/logo/1561425868412_画板 copy 6@3x.JPG","aera":"大学城"}
     */

    private ProviderDetailBean providerDetail;

    public ProviderDetailBean getProviderDetail() {
        return providerDetail;
    }

    public void setProviderDetail(ProviderDetailBean providerDetail) {
        this.providerDetail = providerDetail;
    }

    public static class ProviderDetailBean {
        @Override
        public String toString() {
            return "ProviderDetailBean{" +
                    "businessStatusName='" + businessStatusName + '\'' +
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

        /**
         * businessStatusName : 营业中
         * storeFacadeImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/bbc57ae84abf4234a60a03b314e2797c/logo/1561425868412_画板 copy 6@3x.JPG
         * businessStatus : 1002010750
         * businessTime : 周一至周五：8时至24时
         * storeDetailAddress : 伊甸城4号楼2层1001
         * storeAddressTag : 临近伊甸城主城区
         * storeLatitude : 32.0000054
         * storeLongitude : 47.200001
         * providerId : 1234567812345678123456781ceshi01
         * detailImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/bbc57ae84abf4234a60a03b314e2797c/logo/1561425868412_画板 copy 6@3x.JPG
         * installationService : 可停车,有雅座
         * storeName : 必胜客伊甸城店
         * displayImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/bbc57ae84abf4234a60a03b314e2797c/logo/1561425868412_画板 copy 6@3x.JPG
         * aera : 大学城
         */


        private String businessStatusName;
        private String storeFacadeImg;
        private String businessStatus;
        private String businessTime;
        private String storeDetailAddress;
        private String storeAddressTag;
        private double storeLatitude;
        private double storeLongitude;
        private String providerId;
        private String detailImg;
        private String installationService;
        private String storeName;
        private String displayImg;
        private String aera;

        public String getBusinessStatusName() {
            return businessStatusName;
        }

        public void setBusinessStatusName(String businessStatusName) {
            this.businessStatusName = businessStatusName;
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

        public double getStoreLatitude() {
            return storeLatitude;
        }

        public void setStoreLatitude(double storeLatitude) {
            this.storeLatitude = storeLatitude;
        }

        public double getStoreLongitude() {
            return storeLongitude;
        }

        public void setStoreLongitude(double storeLongitude) {
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

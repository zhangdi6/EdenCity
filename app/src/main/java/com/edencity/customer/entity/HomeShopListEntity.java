package com.edencity.customer.entity;

// Created by Ardy on 2020/1/7.

import java.util.List;

public class HomeShopListEntity {


    private List<ProviderListBean> providerList;

    public List<ProviderListBean> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<ProviderListBean> providerList) {
        this.providerList = providerList;
    }

    public static class ProviderListBean {
        /**
         * providerId : 1234567812345678123456781ceshi01
         * storeFacadeImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/brand/bbc57ae84abf4234a60a03b314e2797c/logo/1561425868412_画板 copy 6@3x.JPG
         * storeName : 必胜客伊甸城店
         * aera : 大学城
         */

        private String providerId;
        private String storeFacadeImg;
        private String storeName;
        private String storeDetailAddress;
        private String aera;

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getStoreFacadeImg() {
            return storeFacadeImg;
        }

        public String getStoreDetailAddress() {
            return storeDetailAddress;
        }

        public void setStoreDetailAddress(String storeDetailAddress) {
            this.storeDetailAddress = storeDetailAddress;
        }

        public void setStoreFacadeImg(String storeFacadeImg) {
            this.storeFacadeImg = storeFacadeImg;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getAera() {
            return aera;
        }

        public void setAera(String aera) {
            this.aera = aera;
        }
    }
}

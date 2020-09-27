package com.edencity.customer.entity;

// Created by Ardy on 2020/1/7.

import java.util.List;

public class HomeBannerEntity {

    private List<BannerListBean> bannerList;

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public static class BannerListBean {
        /**
         * bannerId : 123
         * bannerContentUrl : http://www.baidu.com
         * bannerImg : https://edencity-cdn.oss-cn-qingdao.aliyuncs.com/banner/banner11%402x.png
         */

        private String bannerId;
        private String bannerContentUrl;
        private String bannerImg;

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
        }

        public String getBannerContentUrl() {
            return bannerContentUrl;
        }

        public void setBannerContentUrl(String bannerContentUrl) {
            this.bannerContentUrl = bannerContentUrl;
        }

        public String getBannerImg() {
            return bannerImg;
        }

        public void setBannerImg(String bannerImg) {
            this.bannerImg = bannerImg;
        }
    }
}

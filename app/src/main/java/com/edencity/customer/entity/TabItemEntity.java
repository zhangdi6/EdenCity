package com.edencity.customer.entity;

/* Created by AdScar
    on 2020/5/30.
      */

import java.util.List;

public class TabItemEntity {

    private List<ItemizeListBean> itemizeList;

    public List<ItemizeListBean> getItemizeList() {
        return itemizeList;
    }

    public void setItemizeList(List<ItemizeListBean> itemizeList) {
        this.itemizeList = itemizeList;
    }

    public static class ItemizeListBean {
        /**
         * itemizePicture : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/itemizePicture/0dc71319de3f4286b962dda80ccd57fc/itemizePicture/1590820815648_组 11.jpg
         * itemizeName : 影视
         * location : 2层
         * detailUrl : null
         */

        private String itemizePicture;
        private String itemizeName;
        private String location;
        private Object detailUrl;

        public String getItemizePicture() {
            return itemizePicture;
        }

        public void setItemizePicture(String itemizePicture) {
            this.itemizePicture = itemizePicture;
        }

        public String getItemizeName() {
            return itemizeName;
        }

        public void setItemizeName(String itemizeName) {
            this.itemizeName = itemizeName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Object getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(Object detailUrl) {
            this.detailUrl = detailUrl;
        }
    }
}

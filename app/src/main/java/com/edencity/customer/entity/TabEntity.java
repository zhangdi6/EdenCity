package com.edencity.customer.entity;

/* Created by AdScar
    on 2020/5/30.
      */

import java.util.List;

public class TabEntity {

    private List<CategoryListBean> categoryList;

    public List<CategoryListBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBean> categoryList) {
        this.categoryList = categoryList;
    }

    public static class CategoryListBean {
        /**
         * categoryValue : 娱乐
         * categoryId : 1002010810
         */

        private String categoryValue;
        private String categoryId;

        public String getCategoryValue() {
            return categoryValue;
        }

        public void setCategoryValue(String categoryValue) {
            this.categoryValue = categoryValue;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
    }
}

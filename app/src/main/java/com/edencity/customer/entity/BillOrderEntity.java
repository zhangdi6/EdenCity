package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/3/23.
public class BillOrderEntity {

    /**
     * accountChangeRecordList : {"endRow":1,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":false,"lastPage":8,"list":[{"changeBalance":0.01,"createtime":"2020-03-23 18:53:27","changeType":"1","changeRecordId":"e126310675ee45d29a08be253084a3df","changeTypeAndRecodeNumber":null}],"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"nextPage":2,"orderBy":"","pageNum":1,"pageSize":1,"pages":18,"prePage":0,"size":1,"startRow":1,"total":18}
     */

    private AccountChangeRecordListBean accountChangeRecordList;

    public AccountChangeRecordListBean getAccountChangeRecordList() {
        return accountChangeRecordList;
    }

    public void setAccountChangeRecordList(AccountChangeRecordListBean accountChangeRecordList) {
        this.accountChangeRecordList = accountChangeRecordList;
    }

    public static class AccountChangeRecordListBean {
        /**
         * endRow : 1
         * firstPage : 1
         * hasNextPage : true
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : false
         * lastPage : 8
         * list : [{"changeBalance":0.01,"createtime":"2020-03-23 18:53:27","changeType":"1","changeRecordId":"e126310675ee45d29a08be253084a3df","changeTypeAndRecodeNumber":null}]
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7,8]
         * nextPage : 2
         * orderBy :
         * pageNum : 1
         * pageSize : 1
         * pages : 18
         * prePage : 0
         * size : 1
         * startRow : 1
         * total : 18
         */

        private int endRow;
        private int firstPage;
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private int lastPage;
        private int navigatePages;
        private int nextPage;
        private String orderBy;
        private int pageNum;
        private int pageSize;
        private int pages;
        private int prePage;
        private int size;
        private int startRow;
        private int total;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * changeBalance : 0.01
             * createtime : 2020-03-23 18:53:27
             * changeType : 1
             * changeRecordId : e126310675ee45d29a08be253084a3df
             * changeTypeAndRecodeNumber : null
             */

            private double changeBalance;
            private String createtime;
            private String changeType;
            private String changeRecordId;
            private String changeTypeAndRecodeNumber;

            public double getChangeBalance() {
                return changeBalance;
            }

            public void setChangeBalance(double changeBalance) {
                this.changeBalance = changeBalance;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getChangeType() {
                return changeType;
            }

            public void setChangeType(String changeType) {
                this.changeType = changeType;
            }

            public String getChangeRecordId() {
                return changeRecordId;
            }

            public void setChangeRecordId(String changeRecordId) {
                this.changeRecordId = changeRecordId;
            }

            public String getChangeTypeAndRecodeNumber() {
                return changeTypeAndRecodeNumber;
            }

            public void setChangeTypeAndRecodeNumber(String changeTypeAndRecodeNumber) {
                this.changeTypeAndRecodeNumber = changeTypeAndRecodeNumber;
            }
        }
    }
}

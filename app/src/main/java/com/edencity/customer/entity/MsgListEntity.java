package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/3/25.

public class MsgListEntity {

    /**
     * messageList : {"endRow":10,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":false,"lastPage":2,"list":[{"messageStatus":"0","createtime":"2020-03-25 15:30:51","messageType":"充值通知","myMessageId":"74f7c56010b042ee8af4df032f49f23d","userId":"3efd3603b59c4ad28829042919f98e6a","content":"主人，小伊很高兴地通知您于2020-03-25 15:30:51充值的100.01伊甸币已到账，请查收！"},{"messageStatus":"0","createtime":"2020-03-25 15:29:55","messageType":"消费通知","myMessageId":"500a65ffef7d4e2f8e0e301c7201ddd3","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告主人，您于2020-03-25 15:29:54消费0.3伊甸币"},{"messageStatus":"0","createtime":"2020-03-25 15:16:26","messageType":"消费通知","myMessageId":"63ba5811cc4d48b490c641979984c13c","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告主人，您于2020-03-25 15:16:26消费0.8伊甸币"},{"messageStatus":"0","createtime":"2020-03-25 15:09:51","messageType":"消费通知","myMessageId":"09b5d0909caa43a4860aaf488387e673","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告主人，您于2020-03-25 15:09:51消费0.6伊甸币"},{"messageStatus":"0","createtime":"2020-03-25 14:59:55","messageType":"消费通知","myMessageId":"a7ff2ed64f1e4db18390d4309481cb28","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告主人，您于2020-03-25 14:59:55消费0.1伊甸币"},{"messageStatus":"0","createtime":"2020-03-25 14:55:51","messageType":"消费通知","myMessageId":"1b8652131a7a439b803ebbefd17742a3","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告主人，您于2020-03-25 14:55:51消费0.1伊甸币"},{"messageStatus":"0","createtime":"2020-03-25 14:44:48","messageType":"消费通知","myMessageId":"ba80415cc07d464e9174c8943d936277","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告主人，您于2020-03-25 14:44:48消费0.01伊甸币"},{"messageStatus":"0","createtime":"2020-03-25 14:40:20","messageType":"充值通知","myMessageId":"26465c55e5bc4d13906f2cd7da04676c","userId":"3efd3603b59c4ad28829042919f98e6a","content":"主人，小伊很高兴地通知您于2020-03-25 14:40:20充值的100.01伊甸币已到账，请查收！"},{"messageStatus":"0","createtime":"2020-03-25 14:17:40","messageType":"活跃值到账通知","myMessageId":"527ca2b70c69404aaf017c036b81881b","userId":"3efd3603b59c4ad28829042919f98e6a","content":"主人，小伊恭喜您成功兑换商品，获赠10点活跃值已到账，请注意查收哟！"},{"messageStatus":"0","createtime":"2020-03-25 14:00:32","messageType":"充值通知","myMessageId":"78b64493932a4d2c9a351eb425f21f35","userId":"3efd3603b59c4ad28829042919f98e6a","content":"主人，小伊很高兴地通知您于2020-03-25 14:00:32充值的100.01伊甸币已到账，请查收！"}],"navigatePages":8,"navigatepageNums":[1,2],"nextPage":2,"orderBy":"","pageNum":1,"pageSize":10,"pages":2,"prePage":0,"size":10,"startRow":1,"total":18}
     */

    private MessageListBean messageList;

    public MessageListBean getMessageList() {
        return messageList;
    }

    @Override
    public String toString() {
        return "MsgListEntity{" +
                "messageList=" + messageList +
                '}';
    }

    public void setMessageList(MessageListBean messageList) {
        this.messageList = messageList;
    }

    public static class MessageListBean {
        @Override
        public String toString() {
            return "MessageListBean{" +
                    "endRow=" + endRow +
                    ", firstPage=" + firstPage +
                    ", hasNextPage=" + hasNextPage +
                    ", hasPreviousPage=" + hasPreviousPage +
                    ", isFirstPage=" + isFirstPage +
                    ", isLastPage=" + isLastPage +
                    ", lastPage=" + lastPage +
                    ", navigatePages=" + navigatePages +
                    ", nextPage=" + nextPage +
                    ", orderBy='" + orderBy + '\'' +
                    ", pageNum=" + pageNum +
                    ", pageSize=" + pageSize +
                    ", pages=" + pages +
                    ", prePage=" + prePage +
                    ", size=" + size +
                    ", startRow=" + startRow +
                    ", total=" + total +
                    ", list=" + list +
                    ", navigatepageNums=" + navigatepageNums +
                    '}';
        }

        /**
         * endRow : 10
         * firstPage : 1
         * hasNextPage : true
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : false
         * lastPage : 2
         * list : [{"messageStatus":"0","createtime":"2020-03-25 15:30:51","messageType":"
         * 充值通知","myMessageId":"74f7c56010b042ee8af4df032f49f23d","userId":"3efd36
         * 03b59c4ad28829042919f98e6a","content":"主人，小伊很高兴地通知您于2020-03-25 15:3
         * 0:51充值的100.01伊甸币已到账，请查收！"},{"messageStatus":"0","createtime":"202
         *
         * 0-03-25 15:29:55","messageType":"消费通知","myMessageId":"500a65ffef7d4e2f8e
         * 0e301c7201ddd3","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告
         * 主人，您于2020-03-25 15:29:54消费0.3伊甸币"},{"messageStatus":"0","create
         * time":"2020-03-25 15:16:26","messageType":"消费通知","myMessageId":"63ba58
         * 11cc4d48b490c641979984c13c","userId":"3efd3603b59c4ad28829042919f98e6a","conte
         * nt":"报告主人，您于2020-03-25 15:16:26消费0.8伊甸币"},{"messageStatus":"0","crea
         * tetime":"2020-03-25 15:09:51","messageType":"消费通知","myMessageId":"09b5d0909c
         * aa43a4860aaf488387e673","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报
         * 告主人，您于2020-03-25 15:09:51消费0.6伊甸币"},{"messageStatus":"0","createtime":"
         * 2020-03-25 14:59:55","messageType":"消费通知","myMessageId":"a7ff2ed64f1e4db18390
         * d4309481cb28","userId":"3efd3603b59c4ad28829042919f98e6a","content":"报告主人，您于2020-
         * 03-25 14:59:55消费0.1伊甸币"},{"messageStatus":"0","createtime":"2020-03-25 14:55:51","me
         * ssageType":"消费通知","myMessageId":"1b8652131a7a439b803ebbefd17742a3","userId":"3efd360
         * 3b59c4ad28829042919f98e6a","content":"报告主人，您于2020-03-25 14:55:51消费0.1伊甸币"},{"
         * messageStatus":"0","createtime":"2020-03-25 14:44:48","messageType":"消费通知","myMessag
         * eId":"ba80415cc07d464e9174c8943d936277","userId":"3efd3603b59c4ad28829042919f98e6a","co
         * ntent":"报告主人，您于2020-03-25 14:44:48消费0.01伊甸币"},{"messageStatus":"0","createti
         * me":"2020-03-25 14:40:20","messageType":"充值通知","myMessageId":"26465c55e5bc4d13906f2c
         *
         * d7da04676c","userId":"3efd3603b59c4ad28829042919f98e6a","content":"主人，小伊很高兴地通知您于2020-03-25 14:40:20充值的100.01伊甸币已到账，请查收！"},{"messageStatus":"0","createtime":"2020-03-25 14:17:40","messageType":"活跃值到账通知","myMessageId":"527ca2b70c69404aaf017c036b81881b","userId":"3efd3603b59c4ad28829042919f98e6a","content":"主人，小伊恭喜您成功兑换商品，获赠10点活跃值已到账，请注意查收哟！"},{"messageStatus":"0","createtime":"2020-03-25 14:00:32","messageType":"充值通知","myMessageId":"78b64493932a4d2c9a351eb425f21f35","userId":"3efd3603b59c4ad28829042919f98e6a","content":"主人，小伊很高兴地通知您于2020-03-25 14:00:32充值的100.01伊甸币已到账，请查收！"}]
         * navigatePages : 8
         *
         *
         * navigatepageNums : [1,2]
         * nextPage : 2
         * orderBy :
         * pageNum : 1
         * pageSize : 10
         * pages : 2
         * prePage : 0
         * size : 10
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
             * messageStatus : 0
             * createtime : 2020-03-25 15:30:51
             * messageType : 充值通知
             * myMessageId : 74f7c56010b042ee8af4df032f49f23d
             * userId : 3efd3603b59c4ad28829042919f98e6a
             * content : 主人，小伊很高兴地通知您于2020-03-25 15:30:51充值的100.01伊甸币已到账，请查收！
             */

            private String messageStatus;
            private String createtime;
            private String messageType;
            private String myMessageId;
            private String userId;
            private String content;

            public ListBean(String messageStatus) {
                this.messageStatus = messageStatus;
            }

            public String getMessageStatus() {
                return messageStatus;
            }

            public void setMessageStatus(String messageStatus) {
                this.messageStatus = messageStatus;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getMessageType() {
                return messageType;
            }

            public void setMessageType(String messageType) {
                this.messageType = messageType;
            }

            public String getMyMessageId() {
                return myMessageId;
            }

            public void setMyMessageId(String myMessageId) {
                this.myMessageId = myMessageId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}

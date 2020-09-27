package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/3/25.
public class FeedBackListEntity {

    private List<FeedbackListBean> feedbackList;

    public List<FeedbackListBean> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<FeedbackListBean> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public static class FeedbackListBean {
        /**
         * summary : null
         * dealtime : null
         * note : null
         * createtime : 2020-03-25 17:12:32
         * feedbackId : 0920d089ee2446518d09f7747923b7da
         * acceptStatus : 0
         * source : 1
         * dealStatus : 0
         * userId : 3efd3603b59c4ad28829042919f98e6a
         * content : %gfygjbjbuvgctcbkbjb
         * contact : null
         * evidence1 : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/feedbak%2Fcustomer%2F3efd3603b59c4ad28829042919f98e6a%2F20200325%2F1585127516969792.png
         * evidence2 : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/feedbak%2Fcustomer%2F3efd3603b59c4ad28829042919f98e6a%2F20200325%2Fopen_screen_sub_title_img_1429.png
         * replyContent : null
         * evidence3 : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/feedbak%2Fcustomer%2F3efd3603b59c4ad28829042919f98e6a%2F20200325%2Fopen_screen_title_img_1429.png
         */

        private Object summary;
        private Object dealtime;
        private Object note;
        private String createtime;
        private String feedbackId;
        private String acceptStatus;
        private String source;
        private String dealStatus;
        private String userId;
        private String content;
        private Object contact;
        private String evidence1;
        private String evidence2;
        private String replyContent;
        private String evidence3;

        public Object getSummary() {
            return summary;
        }

        public void setSummary(Object summary) {
            this.summary = summary;
        }

        public Object getDealtime() {
            return dealtime;
        }

        public void setDealtime(Object dealtime) {
            this.dealtime = dealtime;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getFeedbackId() {
            return feedbackId;
        }

        public void setFeedbackId(String feedbackId) {
            this.feedbackId = feedbackId;
        }

        public String getAcceptStatus() {
            return acceptStatus;
        }

        public void setAcceptStatus(String acceptStatus) {
            this.acceptStatus = acceptStatus;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDealStatus() {
            return dealStatus;
        }

        public void setDealStatus(String dealStatus) {
            this.dealStatus = dealStatus;
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

        public Object getContact() {
            return contact;
        }

        public void setContact(Object contact) {
            this.contact = contact;
        }

        public String getEvidence1() {
            return evidence1;
        }

        public void setEvidence1(String evidence1) {
            this.evidence1 = evidence1;
        }

        public String getEvidence2() {
            return evidence2;
        }

        public void setEvidence2(String evidence2) {
            this.evidence2 = evidence2;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getEvidence3() {
            return evidence3;
        }

        public void setEvidence3(String evidence3) {
            this.evidence3 = evidence3;
        }
    }
}

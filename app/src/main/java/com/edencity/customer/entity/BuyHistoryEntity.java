package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/3/26.
public class BuyHistoryEntity {

    private List<MembershipRecordListBean> membershipRecordList;

    public List<MembershipRecordListBean> getMembershipRecordList() {
        return membershipRecordList;
    }

    public void setMembershipRecordList(List<MembershipRecordListBean> membershipRecordList) {
        this.membershipRecordList = membershipRecordList;
    }

    public static class MembershipRecordListBean {
        /**
         * recordNumber : 01202003261016355859
         * memberEndTime : 2021-03-26 10:16:05
         * cost : 0.01
         * recordType : 1
         * nickName : 你有什么可豪横的
         * userId : 3efd3603b59c4ad28829042919f98e6a
         * userCode : YDCU044555284
         * payTypeName : 支付宝
         * payType : 1010010501
         * phone : 176*****083
         * membershipTypeName : 二级汇员
         * memberStartTime : 2020-03-26 10:16:05
         * memberRecordId : 202003261016156981
         * recordTypeName : 购买
         */

        private String recordNumber;
        private String memberEndTime;
        private double cost;
        private String recordType;
        private String nickName;
        private String userId;
        private String userCode;
        private String payTypeName;
        private String payType;
        private String phone;
        private String membershipTypeName;
        private String memberStartTime;
        private String memberRecordId;
        private String recordTypeName;

        public String getRecordNumber() {
            return recordNumber;
        }

        public void setRecordNumber(String recordNumber) {
            this.recordNumber = recordNumber;
        }

        public String getMemberEndTime() {
            return memberEndTime;
        }

        public void setMemberEndTime(String memberEndTime) {
            this.memberEndTime = memberEndTime;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public String getRecordType() {
            return recordType;
        }

        public void setRecordType(String recordType) {
            this.recordType = recordType;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getPayTypeName() {
            return payTypeName;
        }

        public void setPayTypeName(String payTypeName) {
            this.payTypeName = payTypeName;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMembershipTypeName() {
            return membershipTypeName;
        }

        public void setMembershipTypeName(String membershipTypeName) {
            this.membershipTypeName = membershipTypeName;
        }

        public String getMemberStartTime() {
            return memberStartTime;
        }

        public void setMemberStartTime(String memberStartTime) {
            this.memberStartTime = memberStartTime;
        }

        public String getMemberRecordId() {
            return memberRecordId;
        }

        public void setMemberRecordId(String memberRecordId) {
            this.memberRecordId = memberRecordId;
        }

        public String getRecordTypeName() {
            return recordTypeName;
        }

        public void setRecordTypeName(String recordTypeName) {
            this.recordTypeName = recordTypeName;
        }
    }
}

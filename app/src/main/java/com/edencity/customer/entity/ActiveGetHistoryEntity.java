package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/2/14.
public class ActiveGetHistoryEntity {

    private List<ActivityObtainRecordBean> activityObtainRecord;

    public List<ActivityObtainRecordBean> getActivityObtainRecord() {
        return activityObtainRecord;
    }

    public void setActivityObtainRecord(List<ActivityObtainRecordBean> activityObtainRecord) {
        this.activityObtainRecord = activityObtainRecord;
    }

    public static class ActivityObtainRecordBean {
        /**
         * createtime : 2020-02-14 13:28:21
         * sourceType : 1000000459
         * sourceTypeName : 签到
         * changeType : 1
         * activityRecordId : fc200c4a62094374a7061154c84c2f4b
         * changeAmount : 1
         */

        private String createtime;
        private String sourceType;
        private String sourceTypeName;
        private String changeType;
        private String activityRecordId;
        private int changeAmount;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public String getSourceTypeName() {
            return sourceTypeName;
        }

        public void setSourceTypeName(String sourceTypeName) {
            this.sourceTypeName = sourceTypeName;
        }

        public String getChangeType() {
            return changeType;
        }

        public void setChangeType(String changeType) {
            this.changeType = changeType;
        }

        public String getActivityRecordId() {
            return activityRecordId;
        }

        public void setActivityRecordId(String activityRecordId) {
            this.activityRecordId = activityRecordId;
        }

        public int getChangeAmount() {
            return changeAmount;
        }

        public void setChangeAmount(int changeAmount) {
            this.changeAmount = changeAmount;
        }
    }
}

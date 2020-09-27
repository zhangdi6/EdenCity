package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/4/7.
public class VipGoodEntity {

    /**
     * result_code : 0
     * result_msg : success
     * data : [{"activityOrNot":"","activityOrNotName":"",
     * "activityPercent":0,"bonusAmount":0,"bonusType":"2","bonusTypeName":"",
     * "createby":"1231231","createbyName":"","createtime":"2020-04-03 18:18:
     * 47.0","cycleId":"1002010220","editby":"","edittime":"","effectiveStatus":
     * "1","effectiveStatusName":"已启用","endTime":"2020-05-30 00:00:00.0","formul
     * a":"","itemList":[{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"43e4bddfdd104b64a73e8f93a27092a4","note":"","perAmount":100,"perBonus":5,"ruleId":"b49408c1b1a24901803636c99dacb76c"},{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"768cd66710334ff8b0745c87959d7741","note":"","perAmount":200,"perBonus":10,"ruleId":"b49408c1b1a24901803636c99dacb76c"},{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"6c06fa611f4749b296687eb784a571eb","note":"","perAmount":300,"perBonus":15,"ruleId":"b49408c1b1a24901803636c99dacb76c"},{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"6a240b8cb7b647fcafb9e192ff2f88ff","note":"","perAmount":500,"perBonus":25,"ruleId":"b49408c1b1a24901803636c99dacb76c"}],"membershipTypeName":"一级汇员","note":"","perAmount":0,"perBonus":0,"ruleId":"b49408c1b1a24901803636c99dacb76c","ruleInfo":"","ruleLabel":"","ruleName":"","score":0,"standardAmount":0,"standardPercent":0,"startTime":"2020-03-31 00:00:00.0"},{"activityOrNot":"","activityOrNotName":"","activityPercent":0,"bonusAmount":0,"bonusType":"2","bonusTypeName":"","createby":"1231231","createbyName":"","createtime":"2020-04-06 18:23:45.0","cycleId":"1002010221","editby":"","edittime":"","effectiveStatus":"1","effectiveStatusName":"已启用","endTime":"2020-05-30 00:00:00.0","formula":"","itemList":[{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"5d96b50ec00246ad9b1816ddc4b475b0","note":"","perAmount":0.01,"perBonus":200,"ruleId":"0d2c0273f47948afa2f12b583c9f6489"},{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"3df3e1affa00458cbe650fa968747366","note":"","perAmount":0.03,"perBonus":300,"ruleId":"0d2c0273f47948afa2f12b583c9f6489"},{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"8a4781ca4da64a7987986a26737f6f0a","note":"","perAmount":0.05,"perBonus":500,"ruleId":"0d2c0273f47948afa2f12b583c9f6489"},{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"f0c28041cdd542fbaf1837d68e490431","note":"","perAmount":0.1,"perBonus":1000,"ruleId":"0d2c0273f47948afa2f12b583c9f6489"}],"membershipTypeName":"二级汇员","note":"","perAmount":0,"perBonus":0,"ruleId":"0d2c0273f47948afa2f12b583c9f6489","ruleInfo":"","ruleLabel":"","ruleName":"","score":0,"standardAmount":0,"standardPercent":0,"startTime":"2020-03-31 00:00:00.0"},null]
     */

    private int result_code;
    private String result_msg;
    private List<DataBean> data;

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VipGoodEntity{" +
                "result_code=" + result_code +
                ", result_msg='" + result_msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    ", itemList=" + itemList +
                    '}';
        }

        /**
         * activityOrNot :
         * activityOrNotName :
         * activityPercent : 0
         * bonusAmount : 0
         * bonusType : 2
         * bonusTypeName :
         * createby : 1231231
         * createbyName :
         * createtime : 2020-04-03 18:18:47.0
         * cycleId : 1002010220
         * editby :
         * edittime :
         * effectiveStatus : 1
         * effectiveStatusName : 已启用
         * endTime : 2020-05-30 00:00:00.0
         * formula :
         * itemList : [{"createby":"","createtime":null,"editby":"",
         * "edittime":null,"itemId":"43e4bddfdd104b64a73e8f93a27092a4",
         * "note":"","perAmount":100,"perBonus":5,"ruleId":"b49408c1b1a249018
         * 03636c99dacb76c"},{"createby":"","createtime":null,"editby":"","edittime":null
         * ,"itemId":"768cd66710334ff8b0745c87959d7741","note":"","perAmount":200,"perBonus":10,"rul
         * eId":"b49408c1b1a24901803636c99dacb76c"},{"createby":"","createtime":null,"editby":"","edittime"
         * :null,"itemId":"6c06fa611f4749b296687eb784a571eb","note":"","perAmount":300,"perBonus":15,"ruleId":"b49408c1b1a
         * 24901803636c99dacb76c"},{"createby":"","createtime":null,"editby":"","edittime":null,"itemId":"6a240b8cb7b647fcafb9e192ff2f88ff","note":"","perAmount":500,"perBonus":25,"ruleId":"b49408c1b1a24901803636c99dacb76c"}]
         * membershipTypeName : 一级汇员
         * note :
         * perAmount : 0
         * perBonus : 0
         * ruleId : b49408c1b1a24901803636c99dacb76c
         * ruleInfo :
         * ruleLabel :
         * ruleName :
         * score : 0
         * standardAmount : 0
         * standardPercent : 0
         * startTime : 2020-03-31 00:00:00.0
         */


        private List<ItemListBean> itemList;

        private String activityOrNot;
        private String activityOrNotName;
        private float activityPercent;

        public float getActivityPercent() {
            return activityPercent;
        }

        public String getActivityOrNot() {
            return activityOrNot;
        }

        public void setActivityOrNot(String activityOrNot) {
            this.activityOrNot = activityOrNot;
        }

        public String getActivityOrNotName() {
            return activityOrNotName;
        }

        public void setActivityOrNotName(String activityOrNotName) {
            this.activityOrNotName = activityOrNotName;
        }

        public void setActivityPercent(float activityPercent) {
            this.activityPercent = activityPercent;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean {
            /**
             * createby :
             * createtime : null
             * editby :
             * edittime : null
             * itemId : 43e4bddfdd104b64a73e8f93a27092a4
             * note :
             * perAmount : 100
             * perBonus : 5
             * ruleId : b49408c1b1a24901803636c99dacb76c
             */


            private String createby;
            private String editby;
            private String itemId;
            private String note;
            private float perAmount;
            private float perBonus;
            private String ruleId;

            @Override
            public String toString() {
                return "ItemListBean{" +
                        "createby='" + createby + '\'' +
                        ", editby='" + editby + '\'' +
                        ", itemId='" + itemId + '\'' +
                        ", note='" + note + '\'' +
                        ", perAmount=" + perAmount +
                        ", perBonus=" + perBonus +
                        ", ruleId='" + ruleId + '\'' +
                        '}';
            }

            public String getCreateby() {
                return createby;
            }

            public void setCreateby(String createby) {
                this.createby = createby;
            }



            public String getEditby() {
                return editby;
            }

            public void setEditby(String editby) {
                this.editby = editby;
            }



            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public float getPerAmount() {
                return perAmount;
            }

            public void setPerAmount(float perAmount) {
                this.perAmount = perAmount;
            }

            public float getPerBonus() {
                return perBonus;
            }

            public void setPerBonus(float perBonus) {
                this.perBonus = perBonus;
            }

            public String getRuleId() {
                return ruleId;
            }

            public void setRuleId(String ruleId) {
                this.ruleId = ruleId;
            }
        }
    }
}

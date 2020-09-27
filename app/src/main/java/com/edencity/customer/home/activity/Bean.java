package com.edencity.customer.home.activity;

// Created by Ardy on 2020/1/17.
public class Bean {

    /**
     * statusCode : 0
     * message : 登录成功
     * error : null
     * data : {"infoCount":4,"userInfo":{"registered":true,"inviteCode":"656562","invitedCode":"我被邀请码","inviteCount":0,"pbsAmount":0,"backuppbsAmount":0,"lastHarvestAmount":0,"backuplastHarvestAmount":0,"pbsFrozen":0,"backuppbsFrozen":0,"combat":0,"crystalAmount":0,"crystalTime":0,"usdtAmount":0,"pbsDepositAmount":0,"pbsTranAmount":0,"pbsDrawLockedAmount":0,"regionNode":0,"hatchAmount":0,"hatchDrawTime":0,"vipLevel":0,"vipStartDate":0,"vipExpireDate":0,"rebateCrystal":6799.5999999999985,"crystalDrawLockedAmount":0,"highNode":0,"crystalDrawTime":0,"crystalDrawZfbTime":0,"crystalDrawZfbToBankTime":0,"amountCrowdfund":0,"crowdFundLevel":2,"lastMarkTime":0,"isKeruyun":true,"blackCard":1,"blackCardJoinTime":1579180173743,"blackCardExpiredTime":1610716173743,"_id":5999,"phone":"13102437172","vrfCode":"用了就过期","countryCode":"86","vcSendTime":0,"createdAt":"2019-12-04T09:56:55+08:00","updatedAt":"2020-01-17T17:23:56+08:00","__v":0,"password":"$2a$10$MhpNYKcO23dx/Y0VAYCv1ePs4f4sAsOf1Keinx6CYFme7bvuwFpUi","basicInfo":"5de712720c35f84fa3e06c38","clientIp":"114.248.75.228","signupTime":1575424626861,"statisticsInfo":"5de712720c35f84fa3e06c37","deviceType":"iOS"},"basicInfo":{"company":"个体","position":"自由职业者","gender":0,"professionalIdentity":15,"showEdit":1,"seePrice":0,"friendPrice":0,"positionTitle":"自由职业者","maidianPrice":0,"isRewarded":false,"_id":"5de712720c35f84fa3e06c38","userId":5999,"userInfo":5999,"createdAt":"2019-12-04T09:57:06+08:00","updatedAt":"2020-01-17T16:46:40+08:00","__v":0,"realName":"qqq","headImg":"","selfDesc":""},"statisticsInfo":{"basicCount":0,"blockchainCount":0,"workCount":0,"educationCount":0,"photoCount":0,"crystalCount":0,"crystalAmount":0,"_id":"5de710ea0c35f84fa3e05c0e","userId":5999,"createdAt":"2019-12-04T09:50:34+08:00","updatedAt":"2019-12-04T09:50:34+08:00","__v":0}}
     * csrfToken : null
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyZWdpc3RlcmVkIjp0cnVlLCJpbnZpdGVDb2RlIjoiNjU2NTYyIiwiaW52aXRlZENvZGUiOiLmiJHooqvpgoDor7fnoIEiLCJpbnZpdGVDb3VudCI6MCwicGJzQW1vdW50IjowLCJiYWNrdXBwYnNBbW91bnQiOjAsImxhc3RIYXJ2ZXN0QW1vdW50IjowLCJiYWNrdXBsYXN0SGFydmVzdEFtb3VudCI6MCwicGJzRnJvemVuIjowLCJiYWNrdXBwYnNGcm96ZW4iOjAsImNvbWJhdCI6MCwiY3J5c3RhbEFtb3VudCI6MCwiY3J5c3RhbFRpbWUiOjAsInVzZHRBbW91bnQiOjAsInBic0RlcG9zaXRBbW91bnQiOjAsInBic1RyYW5BbW91bnQiOjAsInBic0RyYXdMb2NrZWRBbW91bnQiOjAsInJlZ2lvbk5vZGUiOjAsImhhdGNoQW1vdW50IjowLCJoYXRjaERyYXdUaW1lIjowLCJ2aXBMZXZlbCI6MCwidmlwU3RhcnREYXRlIjowLCJ2aXBFeHBpcmVEYXRlIjowLCJyZWJhdGVDcnlzdGFsIjo2Nzk5LjU5OTk5OTk5OTk5ODUsImNyeXN0YWxEcmF3TG9ja2VkQW1vdW50IjowLCJoaWdoTm9kZSI6MCwiY3J5c3RhbERyYXdUaW1lIjowLCJjcnlzdGFsRHJhd1pmYlRpbWUiOjAsImNyeXN0YWxEcmF3WmZiVG9CYW5rVGltZSI6MCwiYW1vdW50Q3Jvd2RmdW5kIjowLCJjcm93ZEZ1bmRMZXZlbCI6MiwibGFzdE1hcmtUaW1lIjowLCJpc0tlcnV5dW4iOnRydWUsImJsYWNrQ2FyZCI6MSwiYmxhY2tDYXJkSm9pblRpbWUiOjE1NzkxODAxNzM3NDMsImJsYWNrQ2FyZEV4cGlyZWRUaW1lIjoxNjEwNzE2MTczNzQzLCJfaWQiOjU5OTksInBob25lIjoiMTMxMDI0MzcxNzIiLCJ2cmZDb2RlIjoi55So5LqG5bCx6L-H5pyfIiwiY291bnRyeUNvZGUiOiI4NiIsInZjU2VuZFRpbWUiOjAsImNyZWF0ZWRBdCI6IjIwMTktMTItMDRUMDk6NTY6NTUrMDg6MDAiLCJ1cGRhdGVkQXQiOiIyMDIwLTAxLTE3VDE3OjIzOjU2KzA4OjAwIiwiX192IjowLCJwYXNzd29yZCI6IiQyYSQxMCRNaHBOWUtjTzIzZHgvWTBWQVlDdjFlUHM0ZjRzQXNPZjFLZWlueDZDWUZtZTdidnV3RnBVaSIsImJhc2ljSW5mbyI6IjVkZTcxMjcyMGMzNWY4NGZhM2UwNmMzOCIsImNsaWVudElwIjoiMTE0LjI0OC43NS4yMjgiLCJzaWdudXBUaW1lIjoxNTc1NDI0NjI2ODYxLCJzdGF0aXN0aWNzSW5mbyI6IjVkZTcxMjcyMGMzNWY4NGZhM2UwNmMzNyIsImRldmljZVR5cGUiOiJpT1MifQ.occgw-3HV0hQPe961aO2PYmQuu6jSXEM6i7qcj9YTko
     * rcToken : OG+dF5AO5GqYL/ltfzy1HzZ0Mx9GBihhxWIwGTWO1izYldWJ+eRnxS3TQUyqRgSpNRH4HC8TRP2RI4RjWBEk1UcMy7mA+iae
     */

    private int statusCode;
    private String message;
    private Object error;
    private DataBean data;
    private Object csrfToken;
    private String token;
    private String rcToken;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(Object csrfToken) {
        this.csrfToken = csrfToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRcToken() {
        return rcToken;
    }

    public void setRcToken(String rcToken) {
        this.rcToken = rcToken;
    }

    public static class DataBean {
        /**
         * infoCount : 4
         * userInfo : {"registered":true,"inviteCode":"656562","invitedCode":"我被邀请码","inviteCount":0,"pbsAmount":0,"backuppbsAmount":0,"lastHarvestAmount":0,"backuplastHarvestAmount":0,"pbsFrozen":0,"backuppbsFrozen":0,"combat":0,"crystalAmount":0,"crystalTime":0,"usdtAmount":0,"pbsDepositAmount":0,"pbsTranAmount":0,"pbsDrawLockedAmount":0,"regionNode":0,"hatchAmount":0,"hatchDrawTime":0,"vipLevel":0,"vipStartDate":0,"vipExpireDate":0,"rebateCrystal":6799.5999999999985,"crystalDrawLockedAmount":0,"highNode":0,"crystalDrawTime":0,"crystalDrawZfbTime":0,"crystalDrawZfbToBankTime":0,"amountCrowdfund":0,"crowdFundLevel":2,"lastMarkTime":0,"isKeruyun":true,"blackCard":1,"blackCardJoinTime":1579180173743,"blackCardExpiredTime":1610716173743,"_id":5999,"phone":"13102437172","vrfCode":"用了就过期","countryCode":"86","vcSendTime":0,"createdAt":"2019-12-04T09:56:55+08:00","updatedAt":"2020-01-17T17:23:56+08:00","__v":0,"password":"$2a$10$MhpNYKcO23dx/Y0VAYCv1ePs4f4sAsOf1Keinx6CYFme7bvuwFpUi","basicInfo":"5de712720c35f84fa3e06c38","clientIp":"114.248.75.228","signupTime":1575424626861,"statisticsInfo":"5de712720c35f84fa3e06c37","deviceType":"iOS"}
         * basicInfo : {"company":"个体","position":"自由职业者","gender":0,"professionalIdentity":15,"showEdit":1,"seePrice":0,"friendPrice":0,"positionTitle":"自由职业者","maidianPrice":0,"isRewarded":false,"_id":"5de712720c35f84fa3e06c38","userId":5999,"userInfo":5999,"createdAt":"2019-12-04T09:57:06+08:00","updatedAt":"2020-01-17T16:46:40+08:00","__v":0,"realName":"qqq","headImg":"","selfDesc":""}
         * statisticsInfo : {"basicCount":0,"blockchainCount":0,"workCount":0,"educationCount":0,"photoCount":0,"crystalCount":0,"crystalAmount":0,"_id":"5de710ea0c35f84fa3e05c0e","userId":5999,"createdAt":"2019-12-04T09:50:34+08:00","updatedAt":"2019-12-04T09:50:34+08:00","__v":0}
         */

        private int infoCount;
        private UserInfoBean userInfo;
        private BasicInfoBean basicInfo;
        private StatisticsInfoBean statisticsInfo;

        public int getInfoCount() {
            return infoCount;
        }

        public void setInfoCount(int infoCount) {
            this.infoCount = infoCount;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public BasicInfoBean getBasicInfo() {
            return basicInfo;
        }

        public void setBasicInfo(BasicInfoBean basicInfo) {
            this.basicInfo = basicInfo;
        }

        public StatisticsInfoBean getStatisticsInfo() {
            return statisticsInfo;
        }

        public void setStatisticsInfo(StatisticsInfoBean statisticsInfo) {
            this.statisticsInfo = statisticsInfo;
        }

        public static class UserInfoBean {
            /**
             * registered : true
             * inviteCode : 656562
             * invitedCode : 我被邀请码
             * inviteCount : 0
             * pbsAmount : 0
             * backuppbsAmount : 0
             * lastHarvestAmount : 0
             * backuplastHarvestAmount : 0
             * pbsFrozen : 0
             * backuppbsFrozen : 0
             * combat : 0
             * crystalAmount : 0
             * crystalTime : 0
             * usdtAmount : 0
             * pbsDepositAmount : 0
             * pbsTranAmount : 0
             * pbsDrawLockedAmount : 0
             * regionNode : 0
             * hatchAmount : 0
             * hatchDrawTime : 0
             * vipLevel : 0
             * vipStartDate : 0
             * vipExpireDate : 0
             * rebateCrystal : 6799.5999999999985
             * crystalDrawLockedAmount : 0
             * highNode : 0
             * crystalDrawTime : 0
             * crystalDrawZfbTime : 0
             * crystalDrawZfbToBankTime : 0
             * amountCrowdfund : 0
             * crowdFundLevel : 2
             * lastMarkTime : 0
             * isKeruyun : true
             * blackCard : 1
             * blackCardJoinTime : 1579180173743
             * blackCardExpiredTime : 1610716173743
             * _id : 5999
             * phone : 13102437172
             * vrfCode : 用了就过期
             * countryCode : 86
             * vcSendTime : 0
             * createdAt : 2019-12-04T09:56:55+08:00
             * updatedAt : 2020-01-17T17:23:56+08:00
             * __v : 0
             * password : $2a$10$MhpNYKcO23dx/Y0VAYCv1ePs4f4sAsOf1Keinx6CYFme7bvuwFpUi
             * basicInfo : 5de712720c35f84fa3e06c38
             * clientIp : 114.248.75.228
             * signupTime : 1575424626861
             * statisticsInfo : 5de712720c35f84fa3e06c37
             * deviceType : iOS
             */

            private boolean registered;
            private String inviteCode;
            private String invitedCode;
            private int inviteCount;
            private int pbsAmount;
            private int backuppbsAmount;
            private int lastHarvestAmount;
            private int backuplastHarvestAmount;
            private int pbsFrozen;
            private int backuppbsFrozen;
            private int combat;
            private int crystalAmount;
            private int crystalTime;
            private int usdtAmount;
            private int pbsDepositAmount;
            private int pbsTranAmount;
            private int pbsDrawLockedAmount;
            private int regionNode;
            private int hatchAmount;
            private int hatchDrawTime;
            private int vipLevel;
            private int vipStartDate;
            private int vipExpireDate;
            private double rebateCrystal;
            private int crystalDrawLockedAmount;
            private int highNode;
            private int crystalDrawTime;
            private int crystalDrawZfbTime;
            private int crystalDrawZfbToBankTime;
            private int amountCrowdfund;
            private int crowdFundLevel;
            private int lastMarkTime;
            private boolean isKeruyun;
            private int blackCard;
            private long blackCardJoinTime;
            private long blackCardExpiredTime;
            private int _id;
            private String phone;
            private String vrfCode;
            private String countryCode;
            private int vcSendTime;
            private String createdAt;
            private String updatedAt;
            private int __v;
            private String password;
            private String basicInfo;
            private String clientIp;
            private long signupTime;
            private String statisticsInfo;
            private String deviceType;

            public boolean isRegistered() {
                return registered;
            }

            public void setRegistered(boolean registered) {
                this.registered = registered;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }

            public String getInvitedCode() {
                return invitedCode;
            }

            public void setInvitedCode(String invitedCode) {
                this.invitedCode = invitedCode;
            }

            public int getInviteCount() {
                return inviteCount;
            }

            public void setInviteCount(int inviteCount) {
                this.inviteCount = inviteCount;
            }

            public int getPbsAmount() {
                return pbsAmount;
            }

            public void setPbsAmount(int pbsAmount) {
                this.pbsAmount = pbsAmount;
            }

            public int getBackuppbsAmount() {
                return backuppbsAmount;
            }

            public void setBackuppbsAmount(int backuppbsAmount) {
                this.backuppbsAmount = backuppbsAmount;
            }

            public int getLastHarvestAmount() {
                return lastHarvestAmount;
            }

            public void setLastHarvestAmount(int lastHarvestAmount) {
                this.lastHarvestAmount = lastHarvestAmount;
            }

            public int getBackuplastHarvestAmount() {
                return backuplastHarvestAmount;
            }

            public void setBackuplastHarvestAmount(int backuplastHarvestAmount) {
                this.backuplastHarvestAmount = backuplastHarvestAmount;
            }

            public int getPbsFrozen() {
                return pbsFrozen;
            }

            public void setPbsFrozen(int pbsFrozen) {
                this.pbsFrozen = pbsFrozen;
            }

            public int getBackuppbsFrozen() {
                return backuppbsFrozen;
            }

            public void setBackuppbsFrozen(int backuppbsFrozen) {
                this.backuppbsFrozen = backuppbsFrozen;
            }

            public int getCombat() {
                return combat;
            }

            public void setCombat(int combat) {
                this.combat = combat;
            }

            public int getCrystalAmount() {
                return crystalAmount;
            }

            public void setCrystalAmount(int crystalAmount) {
                this.crystalAmount = crystalAmount;
            }

            public int getCrystalTime() {
                return crystalTime;
            }

            public void setCrystalTime(int crystalTime) {
                this.crystalTime = crystalTime;
            }

            public int getUsdtAmount() {
                return usdtAmount;
            }

            public void setUsdtAmount(int usdtAmount) {
                this.usdtAmount = usdtAmount;
            }

            public int getPbsDepositAmount() {
                return pbsDepositAmount;
            }

            public void setPbsDepositAmount(int pbsDepositAmount) {
                this.pbsDepositAmount = pbsDepositAmount;
            }

            public int getPbsTranAmount() {
                return pbsTranAmount;
            }

            public void setPbsTranAmount(int pbsTranAmount) {
                this.pbsTranAmount = pbsTranAmount;
            }

            public int getPbsDrawLockedAmount() {
                return pbsDrawLockedAmount;
            }

            public void setPbsDrawLockedAmount(int pbsDrawLockedAmount) {
                this.pbsDrawLockedAmount = pbsDrawLockedAmount;
            }

            public int getRegionNode() {
                return regionNode;
            }

            public void setRegionNode(int regionNode) {
                this.regionNode = regionNode;
            }

            public int getHatchAmount() {
                return hatchAmount;
            }

            public void setHatchAmount(int hatchAmount) {
                this.hatchAmount = hatchAmount;
            }

            public int getHatchDrawTime() {
                return hatchDrawTime;
            }

            public void setHatchDrawTime(int hatchDrawTime) {
                this.hatchDrawTime = hatchDrawTime;
            }

            public int getVipLevel() {
                return vipLevel;
            }

            public void setVipLevel(int vipLevel) {
                this.vipLevel = vipLevel;
            }

            public int getVipStartDate() {
                return vipStartDate;
            }

            public void setVipStartDate(int vipStartDate) {
                this.vipStartDate = vipStartDate;
            }

            public int getVipExpireDate() {
                return vipExpireDate;
            }

            public void setVipExpireDate(int vipExpireDate) {
                this.vipExpireDate = vipExpireDate;
            }

            public double getRebateCrystal() {
                return rebateCrystal;
            }

            public void setRebateCrystal(double rebateCrystal) {
                this.rebateCrystal = rebateCrystal;
            }

            public int getCrystalDrawLockedAmount() {
                return crystalDrawLockedAmount;
            }

            public void setCrystalDrawLockedAmount(int crystalDrawLockedAmount) {
                this.crystalDrawLockedAmount = crystalDrawLockedAmount;
            }

            public int getHighNode() {
                return highNode;
            }

            public void setHighNode(int highNode) {
                this.highNode = highNode;
            }

            public int getCrystalDrawTime() {
                return crystalDrawTime;
            }

            public void setCrystalDrawTime(int crystalDrawTime) {
                this.crystalDrawTime = crystalDrawTime;
            }

            public int getCrystalDrawZfbTime() {
                return crystalDrawZfbTime;
            }

            public void setCrystalDrawZfbTime(int crystalDrawZfbTime) {
                this.crystalDrawZfbTime = crystalDrawZfbTime;
            }

            public int getCrystalDrawZfbToBankTime() {
                return crystalDrawZfbToBankTime;
            }

            public void setCrystalDrawZfbToBankTime(int crystalDrawZfbToBankTime) {
                this.crystalDrawZfbToBankTime = crystalDrawZfbToBankTime;
            }

            public int getAmountCrowdfund() {
                return amountCrowdfund;
            }

            public void setAmountCrowdfund(int amountCrowdfund) {
                this.amountCrowdfund = amountCrowdfund;
            }

            public int getCrowdFundLevel() {
                return crowdFundLevel;
            }

            public void setCrowdFundLevel(int crowdFundLevel) {
                this.crowdFundLevel = crowdFundLevel;
            }

            public int getLastMarkTime() {
                return lastMarkTime;
            }

            public void setLastMarkTime(int lastMarkTime) {
                this.lastMarkTime = lastMarkTime;
            }

            public boolean isIsKeruyun() {
                return isKeruyun;
            }

            public void setIsKeruyun(boolean isKeruyun) {
                this.isKeruyun = isKeruyun;
            }

            public int getBlackCard() {
                return blackCard;
            }

            public void setBlackCard(int blackCard) {
                this.blackCard = blackCard;
            }

            public long getBlackCardJoinTime() {
                return blackCardJoinTime;
            }

            public void setBlackCardJoinTime(long blackCardJoinTime) {
                this.blackCardJoinTime = blackCardJoinTime;
            }

            public long getBlackCardExpiredTime() {
                return blackCardExpiredTime;
            }

            public void setBlackCardExpiredTime(long blackCardExpiredTime) {
                this.blackCardExpiredTime = blackCardExpiredTime;
            }

            public int get_id() {
                return _id;
            }

            public void set_id(int _id) {
                this._id = _id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getVrfCode() {
                return vrfCode;
            }

            public void setVrfCode(String vrfCode) {
                this.vrfCode = vrfCode;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }

            public int getVcSendTime() {
                return vcSendTime;
            }

            public void setVcSendTime(int vcSendTime) {
                this.vcSendTime = vcSendTime;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getBasicInfo() {
                return basicInfo;
            }

            public void setBasicInfo(String basicInfo) {
                this.basicInfo = basicInfo;
            }

            public String getClientIp() {
                return clientIp;
            }

            public void setClientIp(String clientIp) {
                this.clientIp = clientIp;
            }

            public long getSignupTime() {
                return signupTime;
            }

            public void setSignupTime(long signupTime) {
                this.signupTime = signupTime;
            }

            public String getStatisticsInfo() {
                return statisticsInfo;
            }

            public void setStatisticsInfo(String statisticsInfo) {
                this.statisticsInfo = statisticsInfo;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }
        }

        public static class BasicInfoBean {
            /**
             * company : 个体
             * position : 自由职业者
             * gender : 0
             * professionalIdentity : 15
             * showEdit : 1
             * seePrice : 0
             * friendPrice : 0
             * positionTitle : 自由职业者
             * maidianPrice : 0
             * isRewarded : false
             * _id : 5de712720c35f84fa3e06c38
             * userId : 5999
             * userInfo : 5999
             * createdAt : 2019-12-04T09:57:06+08:00
             * updatedAt : 2020-01-17T16:46:40+08:00
             * __v : 0
             * realName : qqq
             * headImg :
             * selfDesc :
             */

            private String company;
            private String position;
            private int gender;
            private int professionalIdentity;
            private int showEdit;
            private int seePrice;
            private int friendPrice;
            private String positionTitle;
            private int maidianPrice;
            private boolean isRewarded;
            private String _id;
            private int userId;
            private int userInfo;
            private String createdAt;
            private String updatedAt;
            private int __v;
            private String realName;
            private String headImg;
            private String selfDesc;

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getProfessionalIdentity() {
                return professionalIdentity;
            }

            public void setProfessionalIdentity(int professionalIdentity) {
                this.professionalIdentity = professionalIdentity;
            }

            public int getShowEdit() {
                return showEdit;
            }

            public void setShowEdit(int showEdit) {
                this.showEdit = showEdit;
            }

            public int getSeePrice() {
                return seePrice;
            }

            public void setSeePrice(int seePrice) {
                this.seePrice = seePrice;
            }

            public int getFriendPrice() {
                return friendPrice;
            }

            public void setFriendPrice(int friendPrice) {
                this.friendPrice = friendPrice;
            }

            public String getPositionTitle() {
                return positionTitle;
            }

            public void setPositionTitle(String positionTitle) {
                this.positionTitle = positionTitle;
            }

            public int getMaidianPrice() {
                return maidianPrice;
            }

            public void setMaidianPrice(int maidianPrice) {
                this.maidianPrice = maidianPrice;
            }

            public boolean isIsRewarded() {
                return isRewarded;
            }

            public void setIsRewarded(boolean isRewarded) {
                this.isRewarded = isRewarded;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(int userInfo) {
                this.userInfo = userInfo;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getSelfDesc() {
                return selfDesc;
            }

            public void setSelfDesc(String selfDesc) {
                this.selfDesc = selfDesc;
            }
        }

        public static class StatisticsInfoBean {
            /**
             * basicCount : 0
             * blockchainCount : 0
             * workCount : 0
             * educationCount : 0
             * photoCount : 0
             * crystalCount : 0
             * crystalAmount : 0
             * _id : 5de710ea0c35f84fa3e05c0e
             * userId : 5999
             * createdAt : 2019-12-04T09:50:34+08:00
             * updatedAt : 2019-12-04T09:50:34+08:00
             * __v : 0
             */

            private int basicCount;
            private int blockchainCount;
            private int workCount;
            private int educationCount;
            private int photoCount;
            private int crystalCount;
            private int crystalAmount;
            private String _id;
            private int userId;
            private String createdAt;
            private String updatedAt;
            private int __v;

            public int getBasicCount() {
                return basicCount;
            }

            public void setBasicCount(int basicCount) {
                this.basicCount = basicCount;
            }

            public int getBlockchainCount() {
                return blockchainCount;
            }

            public void setBlockchainCount(int blockchainCount) {
                this.blockchainCount = blockchainCount;
            }

            public int getWorkCount() {
                return workCount;
            }

            public void setWorkCount(int workCount) {
                this.workCount = workCount;
            }

            public int getEducationCount() {
                return educationCount;
            }

            public void setEducationCount(int educationCount) {
                this.educationCount = educationCount;
            }

            public int getPhotoCount() {
                return photoCount;
            }

            public void setPhotoCount(int photoCount) {
                this.photoCount = photoCount;
            }

            public int getCrystalCount() {
                return crystalCount;
            }

            public void setCrystalCount(int crystalCount) {
                this.crystalCount = crystalCount;
            }

            public int getCrystalAmount() {
                return crystalAmount;
            }

            public void setCrystalAmount(int crystalAmount) {
                this.crystalAmount = crystalAmount;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }
    }
}

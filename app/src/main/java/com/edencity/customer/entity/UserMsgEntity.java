package com.edencity.customer.entity;

import java.util.List;

// Created by Ardy on 2020/1/13.

public class UserMsgEntity {

    public static final int USER_APPROVAL_NOT = 0; //未认证
    public static final int USER_APPROVAL_OK = 1;  //已认证
    public static final int USER_APPROVAL_WAIT = 2;  //等待审核
    public static final int USER_APPROVAL_PROCESS = 3;  //审核中
    public static final int USER_APPROVAL_FAIL = 4;  //审核失败
    /**
     * customer : {"accountStatus":"1002010501","activityAmount":319,"alipay":"","authPatten":"","birthday":null,"checkInTimes":6,"createby":"1e06e976dfcf484792c119a1fe411eae","createtime":{"date":26,"day":4,"hours":10,"minutes":51,"month":2,"seconds":28,"time":1585191088000,"timezoneOffset":-480,"year":120},"editby":"","edittime":null,"gender":"","idCardNo":"410326200008145013","idCardObverse":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/identitycard%2Fcustomer%2F1e06e976dfcf484792c119a1fe411eae1585202015785_snap1585201975282.jpg","idCardReverse":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/identitycard%2Fcustomer%2F1e06e976dfcf484792c119a1fe411eae1585202016893_snap1585201981336.jpg","memberEndTime":{"date":26,"day":5,"hours":14,"minutes":14,"month":2,"seconds":52,"time":1616739292000,"timezoneOffset":-480,"year":121},"memberEndTimeFormat":"2021-03-26","memberStartTime":{"date":26,"day":4,"hours":14,"minutes":14,"month":2,"seconds":52,"time":1585203292000,"timezoneOffset":-480,"year":120},"memberStartTimeFormat":"2020-03-26","memberType":"1002010221","memberTypeName":"二级汇员","mobile":"","nickName":"你有什么可豪横的","note":"","openid":"","password":"true","payPassword":"true","personId":"03200326gs92","phone":"17630360083","portrait":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/userportrait1585202231926_snap1585202201075.jpg","qq":"","unionid":"","userAccount":"","userApproval":"1010011412","userCode":"YDCU347181280","userId":"1e06e976dfcf484792c119a1fe411eae","userInfo":"","userName":"张迪","userSerial":0,"userStatus":"1010011402","userType":"1","wechat":""}
     * account : {"accountId":"0dbe9826e3894646a7edceba107bdfe8","cardList":[{"bonusBalance":0,"cardBalance":0,"cardCode":"YDC20200326137240","cardId":"1698983cdfb4470681f35b58818c2b31","cardStatus":"","cardType":"1","issueTime":{"date":26,"day":4,"hours":10,"minutes":51,"month":2,"seconds":28,"time":1585191088000,"timezoneOffset":-480,"year":120},"presentUser":"1e06e976dfcf484792c119a1fe411eae","totalBalance":0},{"bonusBalance":0,"cardBalance":0,"cardCode":"YDC20200326679963","cardId":"e9a32e8f1d674e06bbec65d81fff4d26","cardStatus":"","cardType":"2","issueTime":{"date":26,"day":4,"hours":10,"minutes":51,"month":2,"seconds":28,"time":1585191088000,"timezoneOffset":-480,"year":120},"presentUser":"1e06e976dfcf484792c119a1fe411eae","totalBalance":0}],"chargeRemain":0,"giftRemain":0,"totalBalance":0,"totalCharge":0,"totalConsume":0,"totalGift":0,"userId":"1e06e976dfcf484792c119a1fe411eae"}
     */

    private CustomerBean customer;
    private AccountBean account;

    public UserMsgEntity() {
        super();
    }

    public UserMsgEntity( CustomerBean customer, AccountBean account) {
        this.customer = customer;
        this.account = account;
    }
    public CustomerBean getCustomer() {
        if (customer!=null){
            return customer;
        }else{
            return new CustomerBean();
        }
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public AccountBean getAccount() {
        if (account!=null){
            return account;
        }else{
            return new AccountBean();
        }

    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public static class CustomerBean {
        /**
         * accountStatus : 1002010501
         * activityAmount : 319
         * alipay :
         * authPatten :
         * birthday : null
         * checkInTimes : 6
         * createby : 1e06e976dfcf484792c119a1fe411eae
         * createtime : {"date":26,"day":4,"hours":10,"minutes":51,"month":2,"seconds":28,"time":1585191088000,"timezoneOffset":-480,"year":120}
         * editby :
         * edittime : null
         * gender :
         * idCardNo : 410326200008145013
         * idCardObverse : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/identitycard%2Fcustomer%2F1e06e976dfcf484792c119a1fe411eae1585202015785_snap1585201975282.jpg
         * idCardReverse : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/identitycard%2Fcustomer%2F1e06e976dfcf484792c119a1fe411eae1585202016893_snap1585201981336.jpg
         * memberEndTime : {"date":26,"day":5,"hours":14,"minutes":14,"month":2,"seconds":52,"time":1616739292000,"timezoneOffset":-480,"year":121}
         * memberEndTimeFormat : 2021-03-26
         * memberStartTime : {"date":26,"day":4,"hours":14,"minutes":14,"month":2,"seconds":52,"time":1585203292000,"timezoneOffset":-480,"year":120}
         * memberStartTimeFormat : 2020-03-26
         * memberType : 1002010221
         * memberTypeName : 二级汇员
         * mobile :
         * nickName : 你有什么可豪横的
         * note :
         * openid :
         * password : true
         * payPassword : true
         * personId : 03200326gs92
         * phone : 17630360083
         * portrait : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/userportrait1585202231926_snap1585202201075.jpg
         * qq :
         * unionid :
         * userAccount :
         * userApproval : 1010011412
         * userCode : YDCU347181280
         * userId : 1e06e976dfcf484792c119a1fe411eae
         * userInfo :
         * userName : 张迪
         * userSerial : 0
         * userStatus : 1010011402
         * userType : 1
         * wechat :
         */

        private String accountStatus;
        private int activityAmount;
        private String alipay;
        private String authPatten;
        private BirthDayBean birthday;
        private String birthdayFormat;
        private int checkInTimes;
        private String createby;
        private CreatetimeBean createtime;
        private String editby;
        private EditTimeBean edittime;
        private String gender;
        private String idCardNo;
        private String idCardObverse;
        private String idCardReverse;
        private MemberEndTimeBean memberEndTime;
        private String memberEndTimeFormat;
        private MemberStartTimeBean memberStartTime;
        private String memberStartTimeFormat;
        private String memberType;
        private String memberTypeName;
        private String mobile;
        private String nickName;
        private String note;
        private String openid;
        private String password;
        private String payPassword;
        private String personId;
        private String phone;
        private String portrait;
        private String qq;
        private String unionid;
        private String userAccount;
        private String userApproval;
        private String userCode;
        private String userId;
        private String userInfo;
        private String userName;
        private int userSerial;
        private String userStatus;
        private String userType;
        private String wechat;
        private String aera;
        private String detailAddress;


        /*
         *1010011401:未认证
         *xx 02：已认证
         *xx 11：审核中
         *xx 10：待审核
         *xx 12：通过
         *xx 13：未通过
         */


    public String getAccountStatus() {
        if (accountStatus!=null){
            return accountStatus;
        }else{
            return "";
        }
        }

        public String getAera() {
            return aera;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setAera(String aera) {
            this.aera = aera;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public void setAccountStatus(String accountStatus) {
            this.accountStatus = accountStatus;
        }

        public int getActivityAmount() {
            if (activityAmount!=0){
                return activityAmount;
            }else{
                return 0;
            }
        }

        public void setActivityAmount(int activityAmount) {
            this.activityAmount = activityAmount;
        }

        public String getAlipay() {
            if (alipay!=null){
                return alipay;
            }else{
                return "";
            }
        }

        public String getBirthdayFormat() {
            return birthdayFormat;
        }

        public void setBirthdayFormat(String birthdayFormat) {
            this.birthdayFormat = birthdayFormat;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getAuthPatten() {
            if (authPatten!=null){
                return authPatten;
            }else{
                return "";
            }
        }

        public void setAuthPatten(String authPatten) {
            this.authPatten = authPatten;
        }

        public BirthDayBean getBirthday() {
            if (birthday!=null){
                return birthday;
            }else{
                return new BirthDayBean();
            }
        }

        public void setBirthday(BirthDayBean birthday) {
            this.birthday = birthday;
        }

        public int getCheckInTimes() {
            return checkInTimes;
        }

        public void setCheckInTimes(int checkInTimes) {
            this.checkInTimes = checkInTimes;
        }

        public String getCreateby() {
            if (createby!=null){
                return createby;
            }else{
                return "";
            }
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public CreatetimeBean getCreatetime() {
            if (createtime!=null){
                return createtime;
            }else{
                return new CreatetimeBean();
            }
    }

        public void setCreatetime(CreatetimeBean createtime) {
            this.createtime = createtime;
        }

        public String getEditby() {
        if (editby!=null){
            return editby;
        }else{
            return "";
        }
        }

        public void setEditby(String editby) {
            this.editby = editby;
        }

        public EditTimeBean getEdittime() {
            if (edittime!=null){
                return edittime;
            }else{
                return new EditTimeBean();
            }
        }

        public void setEdittime(EditTimeBean edittime) {
            this.edittime = edittime;
        }

        public String getGender() {
            if (gender!=null){
                return gender;
            }else{
                return "";
            }
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIdCardNo() {
            if (idCardNo!=null){
                return idCardNo;
            }else{
                return "";
            }
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public String getIdCardObverse() {
            if (idCardObverse!=null){
                return idCardObverse;
            }else{
                return "";
            }
        }

        public void setIdCardObverse(String idCardObverse) {
            this.idCardObverse = idCardObverse;
        }

        public String getIdCardReverse() {
            if (idCardReverse!=null){
                return idCardReverse;
            }else{
                return "";
            }
        }

        public void setIdCardReverse(String idCardReverse) {
            this.idCardReverse = idCardReverse;
        }

        public MemberEndTimeBean getMemberEndTime() {
            if (memberEndTime!=null){
                return memberEndTime;
            }else{
                return new MemberEndTimeBean();
            }
        }

        public void setMemberEndTime(MemberEndTimeBean memberEndTime) {
            this.memberEndTime = memberEndTime;
        }

        public String getMemberEndTimeFormat() {
            if (memberEndTimeFormat!=null){
                return memberEndTimeFormat;
            }else{
                return "";
            }
        }

        public void setMemberEndTimeFormat(String memberEndTimeFormat) {
            this.memberEndTimeFormat = memberEndTimeFormat;
        }

        public MemberStartTimeBean getMemberStartTime() {
            if (memberStartTime!=null){
                return memberStartTime;
            }else{
                return new MemberStartTimeBean();
            }

        }

        public void setMemberStartTime(MemberStartTimeBean memberStartTime) {
            this.memberStartTime = memberStartTime;
        }

        public String getMemberStartTimeFormat() {
            if (memberStartTimeFormat!=null){
                return memberStartTimeFormat;
            }else{
                return "";
            }

        }

        public void setMemberStartTimeFormat(String memberStartTimeFormat) {
            this.memberStartTimeFormat = memberStartTimeFormat;
        }

        public String getMemberType() {
            if (memberType!=null){
                return memberType;
            }else{
                return "";
            }
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getMemberTypeName() {
            if (memberTypeName!=null){
                return memberTypeName;
            }else{
                return "";
            }
        }

        public void setMemberTypeName(String memberTypeName) {
            this.memberTypeName = memberTypeName;
        }

        public String getMobile() {
            if (mobile!=null){
                return mobile;
            }else{
                return "";
            }
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            if (nickName!=null){
                return nickName;
            }else{
                return "";
            }
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getNote() {
            if (note!=null){
                return note;
            }else{
                return "";
            }
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getOpenid() {
            if (openid!=null){
                return openid;
            }else{
                return "";
            }
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getPassword() {
            if (password!=null){
                return password;
            }else{
                return "";
            }
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPayPassword() {
            if (payPassword!=null){
                return payPassword;
            }else{
                return "";
            }
        }

        public void setPayPassword(String payPassword) {
            this.payPassword = payPassword;
        }

        public String getPersonId() {
            if (personId!=null){
                return personId;
            }else{
                return "";
            }
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getPhone() {
            if (phone!=null){
                return phone;
            }else{
                return "";
            }
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPortrait() {
            if (portrait!=null){
                return portrait;
            }else{
                return "";
            }
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getQq() {
            if (qq!=null){
                return qq;
            }else{
                return "";
            }
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getUnionid() {
            if (unionid!=null){
                return unionid;
            }else{
                return "";
            }
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getUserAccount() {
            if (userAccount!=null){
                return userAccount;
            }else{
                return "";
            }
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserApproval() {
            if (userApproval!=null){
                return userApproval;
            }else{
                return "";
            }
        }

        public void setUserApproval(String userApproval) {
            this.userApproval = userApproval;
        }

        public String getUserCode() {
            if (userCode!=null){
                return userCode;
            }else{
                return "";
            }
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUserId() {
            if (userId!=null){
                return userId;
            }else{
                return "";
            }
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserInfo() {
            if (userInfo!=null){
                return userInfo;
            }else{
                return "";
            }
        }

        public void setUserInfo(String userInfo) {
            this.userInfo = userInfo;
        }

        public String getUserName() {
            if (userName!=null){
                return userName;
            }else{
                return "";
            }
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserSerial() {
            return userSerial;
        }

        public void setUserSerial(int userSerial) {
            this.userSerial = userSerial;
        }

        public String getUserStatus() {
            if (userStatus!=null){
                return userStatus;
            }else{
                return "";
            }
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserType() {
            if (userType!=null){
                return userType;
            }else{
                return "";
            }
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getWechat() {
            if (wechat!=null){
                return wechat;
            }else{
                return "";
            }
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }
        public boolean isAccountNeedUpdate;

        //实名认证状态和结果说明
        public boolean isAccountValid() {
            return "1002010501".equals(getAccountStatus());
        }

        public boolean isUserApprovaled() {
            return "1010011402".equals(getUserStatus());
        }

        //是否认证通过
        public int getUserApprovalStatus() {
            if (getUserApproval() == null) {
                return USER_APPROVAL_NOT;
            }
            switch (getUserApproval()) {
                case "1010011401":
                case "1010011410":
                    return USER_APPROVAL_NOT;
                case "1010011402":
                case "1010011412":
                    return USER_APPROVAL_OK;
                //            case "1010011410":
                //                return USER_APPROVAL_WAIT;
                case "1010011411":
                    return USER_APPROVAL_PROCESS;
                case "1010011413":
                    return USER_APPROVAL_FAIL;
            }
            return USER_APPROVAL_NOT;
        }


        public String getUserApprovalStatusDesc() {
            if (getUserApproval() == null) {
                return "1010011401".equals(getUserStatus()) ? "未认证" : "已认证";
            }
            switch (userApproval) {
                case "1010011401":
                case "1010011410":
                    return "未认证";
                case "1010011402":
                case "1010011412":
                    return "已认证";
                case "1010011411":
                    return "审核中";
                case "1010011413":
                    return "认证失败";
            }
            return "未认证";
        }

        public String getUserVipLevel() {
            if (memberType!=null){
                switch (memberType) {
                    case "1002010220":
                        return "普通汇员";
                    case "1002010221":
                        return "付费汇员";
                    case "1002010222":
                        return "尊享汇员";
                }
            }
            return "";
        }

        public static class CreatetimeBean {
            /**
             * date : 26
             * day : 4
             * hours : 10
             * minutes : 51
             * month : 2
             * seconds : 28
             * time : 1585191088000
             * timezoneOffset : -480
             * year : 120
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
     public static class BirthDayBean {
            /**
             * date : 26
             * day : 4
             * hours : 10
             * minutes : 51
             * month : 2
             * seconds : 28
             * time : 1585191088000
             * timezoneOffset : -480
             * year : 120
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }

        public static class MemberEndTimeBean {
            /**
             * date : 26
             * day : 5
             * hours : 14
             * minutes : 14
             * month : 2
             * seconds : 52
             * time : 1616739292000
             * timezoneOffset : -480
             * year : 121
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
      public static class EditTimeBean {
            /**
             * date : 26
             * day : 5
             * hours : 14
             * minutes : 14
             * month : 2
             * seconds : 52
             * time : 1616739292000
             * timezoneOffset : -480
             * year : 121
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }

        public static class MemberStartTimeBean {
            /**
             * date : 26
             * day : 4
             * hours : 14
             * minutes : 14
             * month : 2
             * seconds : 52
             * time : 1585203292000
             * timezoneOffset : -480
             * year : 120
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }

    public static class AccountBean {
        /**
         * accountId : 0dbe9826e3894646a7edceba107bdfe8
         * cardList : [{"bonusBalance":0,"cardBalance":0,"cardCode":"YDC20200326137240","cardId":"1698983cdfb4470681f35b58818c2b31","cardStatus":"","cardType":"1","issueTime":{"date":26,"day":4,"hours":10,"minutes":51,"month":2,"seconds":28,"time":1585191088000,"timezoneOffset":-480,"year":120},"presentUser":"1e06e976dfcf484792c119a1fe411eae","totalBalance":0},{"bonusBalance":0,"cardBalance":0,"cardCode":"YDC20200326679963","cardId":"e9a32e8f1d674e06bbec65d81fff4d26","cardStatus":"","cardType":"2","issueTime":{"date":26,"day":4,"hours":10,"minutes":51,"month":2,"seconds":28,"time":1585191088000,"timezoneOffset":-480,"year":120},"presentUser":"1e06e976dfcf484792c119a1fe411eae","totalBalance":0}]
         * chargeRemain : 0
         * giftRemain : 0
         * totalBalance : 0
         * totalCharge : 0
         * totalConsume : 0
         * totalGift : 0
         * userId : 1e06e976dfcf484792c119a1fe411eae
         */

        private String accountId;
        private float chargeRemain;
        private float giftRemain;
        private float totalBalance;
        private float totalCharge;
        private float totalConsume;
        private float totalGift;
        private String userId;
        private List<CardListBean> cardList;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public float getChargeRemain() {
            return chargeRemain;
        }

        public void setChargeRemain(float chargeRemain) {
            this.chargeRemain = chargeRemain;
        }

        public float getGiftRemain() {
            return giftRemain;
        }

        public void setGiftRemain(float giftRemain) {
            this.giftRemain = giftRemain;
        }

        public float getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(float totalBalance) {
            this.totalBalance = totalBalance;
        }

        public float getTotalCharge() {
            return totalCharge;
        }

        public void setTotalCharge(float totalCharge) {
            this.totalCharge = totalCharge;
        }

        public float getTotalConsume() {
            return totalConsume;
        }

        public void setTotalConsume(float totalConsume) {
            this.totalConsume = totalConsume;
        }

        public float getTotalGift() {
            return totalGift;
        }

        public void setTotalGift(float totalGift) {
            this.totalGift = totalGift;
        }

        public String getUserId() {
            if (userId!=null){
                return userId;
            }else{
                return "";
            }
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<CardListBean> getCardList() {
            return cardList;
        }

        public void setCardList(List<CardListBean> cardList) {
            this.cardList = cardList;
        }

        public static class CardListBean {
            /**
             * bonusBalance : 0
             * cardBalance : 0
             * cardCode : YDC20200326137240
             * cardId : 1698983cdfb4470681f35b58818c2b31
             * cardStatus :
             * cardType : 1
             * issueTime : {"date":26,"day":4,"hours":10,"minutes":51,"month":2,"seconds":28,"time":1585191088000,"timezoneOffset":-480,"year":120}
             * presentUser : 1e06e976dfcf484792c119a1fe411eae
             * totalBalance : 0
             */

            private float bonusBalance;
            private float cardBalance;
            private String cardCode;
            private String cardId;
            private String cardStatus;
            private String cardType;
            private IssueTimeBean issueTime;
            private String presentUser;
            private float totalBalance;

            public float getBonusBalance() {
                return bonusBalance;
            }

            public void setBonusBalance(float bonusBalance) {
                this.bonusBalance = bonusBalance;
            }

            public float getCardBalance() {
                return cardBalance;
            }

            public void setCardBalance(float cardBalance) {
                this.cardBalance = cardBalance;
            }

            public String getCardCode() {
                if (cardCode!=null){
                    return cardCode;
                }else{
                    return "";
                }
            }

            public void setCardCode(String cardCode) {
                this.cardCode = cardCode;
            }

            public String getCardId() {
                if (cardId!=null){
                    return cardId;
                }else{
                    return "";
                }
            }

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public String getCardStatus() {
                if (cardStatus!=null){
                    return cardStatus;
                }else{
                    return "";
                }
            }

            public void setCardStatus(String cardStatus) {
                this.cardStatus = cardStatus;
            }

            public String getCardType() {
                if (cardType!=null){
                    return cardType;
                }else{
                    return "";
                }
            }

            public void setCardType(String cardType) {
                this.cardType = cardType;
            }

            public IssueTimeBean getIssueTime() {
                return issueTime;
            }

            public void setIssueTime(IssueTimeBean issueTime) {
                this.issueTime = issueTime;
            }

            public String getPresentUser() {
                if (presentUser!=null){
                    return presentUser;
                }else{
                    return "";
                }
            }

            public void setPresentUser(String presentUser) {
                this.presentUser = presentUser;
            }

            public float getTotalBalance() {
                return totalBalance;
            }

            public void setTotalBalance(int totalBalance) {
                this.totalBalance = totalBalance;
            }

            public static class IssueTimeBean {
                /**
                 * date : 26
                 * day : 4
                 * hours : 10
                 * minutes : 51
                 * month : 2
                 * seconds : 28
                 * time : 1585191088000
                 * timezoneOffset : -480
                 * year : 120
                 */

                private int date;
                private int day;
                private int hours;
                private int minutes;
                private int month;
                private int seconds;
                private long time;
                private int timezoneOffset;
                private int year;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }
        }

    }
}



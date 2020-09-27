package com.edencity.customer.pojo;

public class Customer {

    public static final int USER_APPROVAL_NOT = 0; //未认证
    public static final int USER_APPROVAL_OK = 1;  //已认证
    public static final int USER_APPROVAL_WAIT = 2;  //等待审核
    public static final int USER_APPROVAL_PROCESS = 3;  //审核中
    public static final int USER_APPROVAL_FAIL = 4;  //审核失败

    //认证信息
    public String userId;

    public String ticket;

    //1002010501:启用 //1002010502停用
    public String accountStutas;
    /**
     * 1010011401:未认证
     * xx 02：已认证
     * xx 11：审核中
     * xx 10：待审核
     * xx 12：通过
     * xx 13：未通过
     */
    public String userStutas;
    public String idCardNo;
    public String nickName;
    public String phone;
    public String userName;
    public String userType;
    public String userAvatar;

    //预付卡号、余额
    public String cardNo;
    public String cardId;
    public String cardBalance;

    //实名认证状态和结果说明
    public String userApprovalCode;
    public String userApprovalDesc;

    public boolean isAccountNeedUpdate;

    public boolean isAccountValid(){
        return "1002010501".equals(accountStutas);
    }

    public boolean isUserApprovaled(){
        return "1010011402".equals(userStutas);
    }

    //是否认证通过
    public int getUserApprovalStatus(){
        if (userApprovalCode==null){
            return USER_APPROVAL_NOT;
        }
        switch (userApprovalCode){
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

    public String getUserApprovalStatusDesc(){
        if (userApprovalCode==null){
            return "1010011401".equals(userStutas)?"未认证":"已认证";
        }
        switch (userApprovalCode){
            case "1010011401":
            case "1010011410":
                return "未认证";
            case "1010011402":
            case "1010011412":
                return "已认证";
//            case "1010011410":
//                return "审核中";
            case "1010011411":
                return "审核中";
            case "1010011413":
                return "认证失败";
        }
        return "未认证";
    }
}

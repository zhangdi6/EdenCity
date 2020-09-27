package com.edencity.customer.pojo;

import java.math.BigDecimal;
import java.util.List;

public class DepositRule {
    //赠送模式 1=按比例赠送 2=满赠
    public int bonusType;

    public List<DepositItem> items;

    public static class DepositItem{
        //金额
        public BigDecimal money;
        //赠送
        public String bonus;
    }
}


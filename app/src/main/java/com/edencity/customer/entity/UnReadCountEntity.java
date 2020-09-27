package com.edencity.customer.entity;

// Created by Ardy on 2020/2/25.
public class UnReadCountEntity {
    private int unReadMessageCount;

    public int getUnReadMessageCount() {
        return unReadMessageCount;
    }

    public void setUnReadMessageCount(int unReadMessageCount) {
        this.unReadMessageCount = unReadMessageCount;
    }

    @Override
    public String toString() {
        return "UnReadCountEntity{" +
                "unReadMessageCount=" + unReadMessageCount +
                '}';
    }
}

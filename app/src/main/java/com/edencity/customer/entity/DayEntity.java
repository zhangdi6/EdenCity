package com.edencity.customer.entity;

// Created by Ardy on 2020/2/6.
public class DayEntity {
    private int id;
    private boolean isSignIn;
    private boolean isHasSignIn;

    public DayEntity(int id, boolean isSignIn, boolean isHasSignIn) {
        this.id = id;
        this.isSignIn = isSignIn;
        this.isHasSignIn = isHasSignIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public void setSignIn(boolean signIn) {
        isSignIn = signIn;
    }

    public boolean isHasSignIn() {
        return isHasSignIn;
    }

    public void setHasSignIn(boolean hasSignIn) {
        isHasSignIn = hasSignIn;
    }
}

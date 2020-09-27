package com.edencity.customer.service;

public interface PhotoResultListener {
    void onPhotoSuccess(String path);
    void onPhotoFailed(String error);
}

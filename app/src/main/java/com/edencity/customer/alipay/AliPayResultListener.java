package com.edencity.customer.alipay;

public interface AliPayResultListener {
  void onPaySuccess();

  void onPaying();

  void onPayFail();

  void onPayCancel();

  void onPayConnectError();
}

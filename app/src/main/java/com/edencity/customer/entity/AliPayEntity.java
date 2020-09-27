package com.edencity.customer.entity;

// Created by Ardy on 2020/1/15.

public class AliPayEntity {

    /**
     * prePayMessage : alipay_sdk=alipay-sdk-java-3.7.4.ALL&app_id=2019060465460311&biz_content=%7B%22out_trade_no%22%3A%22202001150414531166%22%2C%22passback_params%22%3A%22charge%2C9b2b407da09e466a9b6364c6a3c065be%2C75.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%BC%9A%E5%91%98%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221500%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fpay.edencity.com%2Finner%2FeCoin%2FgetAlipayNotify&sign=jQnJEsjLQyY9xW5YbTNwdkBlTz8XzQXqlGjsBgGhnfiPS604F5KmyK%2Fi1on0zAlcjlWQZZ4EKf0S6fhE0wwAZjAU0%2FjCV4bWP2IXCeWKAZKvwOmZPukG10fipjatltzyy31HcrBuKLzKXqqpjseBauq8Gn2QiBK9wJWx7MUr2niYZ4%2BqAhcLc8o36wUnbLyRyZW8laKfawaPNmX6%2BxN7Im9cQDDeWs8rs7ZnxdL%2Bw4a%2BYeJbPmNIfmIFEK5yiSWZ%2FNRNttWxFiGL1U3rk3LCBDQG71qvwsLeZWBvTqYeP954Wuu7cX4j5mrS9MwGkOLhUIY3%2F5SmaIy42geTyTH7wg%3D%3D&sign_type=RSA2&timestamp=2020-01-15+16%3A14%3A53&version=1.0
     */

    private String prePayMessage;

    public String getPrePayMessage() {
        return prePayMessage;
    }

    public void setPrePayMessage(String prePayMessage) {
        this.prePayMessage = prePayMessage;
    }
}

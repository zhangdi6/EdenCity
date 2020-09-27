package com.edencity.customer.entity;

/* Created by AdScar
    on 2020/9/22.
      */

import java.util.List;

public class CustomerEvent {

    private List<CustomerEventsBean> customerEvents;

    public List<CustomerEventsBean> getCustomerEvents() {
        return customerEvents;
    }

    public void setCustomerEvents(List<CustomerEventsBean> customerEvents) {
        this.customerEvents = customerEvents;
    }

    public static class CustomerEventsBean {
        /**
         * eventId : 1
         * eventUrl : http://www.baidu.com
         * eventName : zhongqiu
         * eventImg : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/banner/customer/null_banner3.jpg
         */

        private String eventId;
        private String eventUrl;
        private String eventName;
        private String eventImg;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEventUrl() {
            return eventUrl;
        }

        public void setEventUrl(String eventUrl) {
            this.eventUrl = eventUrl;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventImg() {
            return eventImg;
        }

        public void setEventImg(String eventImg) {
            this.eventImg = eventImg;
        }
    }
}

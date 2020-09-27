package com.edencity.customer.base;

import com.fanjun.httpclient.httpcenter.Request;
import com.fanjun.httpclient.httpcenter.Response;
import com.fanjun.httpclient.interceptor.ResponseInterceptor;

public class MyResponseInterceptor implements ResponseInterceptor {

        @Override
        public <T extends Response> T paramsMachining(Request request, T response) {
            //response中包含了状态、头、数据包、错误消息等，你可以根据自己的需求重组，但一定要返回一个继承自Response的类
            return null;
        }
    }
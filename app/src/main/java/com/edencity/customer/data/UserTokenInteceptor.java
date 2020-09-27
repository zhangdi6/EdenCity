package com.edencity.customer.data;


import java.io.IOException;

import com.edencity.customer.util.DeeSpUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class UserTokenInteceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = DeeSpUtil.getInstance().getString("token");
        if (token!=null){
            Request request = chain.request();
            request.newBuilder()

                    .build();
            return chain.proceed(request);
        }else{
            Request request = chain.request();
            request.newBuilder()

                    .build();
            return chain.proceed(request);
        }

    }
}

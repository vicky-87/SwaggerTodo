package com.example.swaggertodo.api.helper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterception implements Interceptor {
    private String token;

    public AuthInterception(String token){
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", token)
                .method(original.method(), original.body());
        Request request = builder.build();
        return null;
    }
}

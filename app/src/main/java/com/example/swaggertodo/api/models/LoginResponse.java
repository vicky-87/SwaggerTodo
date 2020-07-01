package com.example.swaggertodo.api.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
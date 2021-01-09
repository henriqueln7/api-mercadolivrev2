package com.mercadolivre.apimlv2.security;

public class AuthLoginResponse {
    private String token;

    public AuthLoginResponse(String jwt) {
        this.token = jwt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

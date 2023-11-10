package com.hcc.requests;

public class JwtTokenRequest {

    private String accessToken;

    public JwtTokenRequest() {
    }

    public JwtTokenRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

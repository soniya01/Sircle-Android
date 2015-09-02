package com.app.sircle.WebService;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by soniya on 02/09/15.
 */
public class AuthenticationData {

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("expires_in")
    public Date expiresIn;

    @SerializedName("token_type")
    public String tokenType;

    public String scope;

    @SerializedName("refresh_token")
    public String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

package com.app.sircle.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 5/23/16.
 */
public class Customer {

    @SerializedName("customer_details")
    private String details;

    @SerializedName("auth_token")
    private String authToken;


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

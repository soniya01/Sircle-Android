package com.snaptech.poorishaadi.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 5/23/16.
 */
public class Customer {

    @SerializedName("customer_details")
    private String details;

    @SerializedName("auth_token")
    private String authToken;


    @SerializedName("method_type")
    private String methodType;

    @SerializedName("login_status")
    private String loginStatus;

    @SerializedName("show_setting")
    private String showSettings;


    @SerializedName("customer_type")
    private String customerType;



    //admin/parent/staff

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

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public  void setShowSettings(String showSettings)
    {
        this.showSettings = showSettings;
    }

    public String getShowSettings() {
        return showSettings;
    }

    public  void setCustomerType(String customerType)
    {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }
}

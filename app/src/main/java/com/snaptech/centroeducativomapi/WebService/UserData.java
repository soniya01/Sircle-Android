package com.snaptech.centroeducativomapi.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 02/09/15.
 */
public class UserData {

    @SerializedName("user_id")
    public String userId;

    @SerializedName("user_type_id")
    public String userTypeId;

    @SerializedName("user_type")
    public String userType;

    public String email;

    public AuthenticationData oauth;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AuthenticationData getOauth() {
        return oauth;
    }

    public void setOauth(AuthenticationData oauth) {
        this.oauth = oauth;
    }

//       @SerializedName("customer_details")
//        public String customerDetails;
//
//    @SerializedName("auth_token")
//    public String authToken;
//
//
//        public String getCustomerDetails() {
//        return customerDetails;
//    }
//
//    public void setCustomerDetails(String customerDetails) {
//        this.customerDetails = customerDetails;
//    }
//
//
//    public String getAuthToken() {
//        return authToken;
//    }
//
//    public void setAuthToken(String authToken) {
//        this.authToken = authToken;
//    }


}

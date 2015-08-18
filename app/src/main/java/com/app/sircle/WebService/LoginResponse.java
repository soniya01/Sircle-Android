package com.app.sircle.WebService;

/**
 * Created by soniya on 18/08/15.
 */
public class LoginResponse {
    public int userTypeId;
    public String message;
    public boolean status;

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

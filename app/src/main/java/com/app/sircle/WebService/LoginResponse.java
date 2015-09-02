package com.app.sircle.WebService;

/**
 * Created by soniya on 18/08/15.
 */
public class LoginResponse {

    public UserData data;
    public String message;
    public int status;

    public UserData getUserData() {
        return data;
    }

    public void setUserData(UserData data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

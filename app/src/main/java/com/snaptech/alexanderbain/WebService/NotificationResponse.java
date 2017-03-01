package com.snaptech.alexanderbain.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 24/08/15.
 */
public class NotificationResponse {

    @SerializedName("code")
    public int status;
    public String message;
    public NotificationResponseData data;

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

    public NotificationResponseData getData() {
        return data;
    }

    public void setData(NotificationResponseData data) {
        this.data = data;
    }
}

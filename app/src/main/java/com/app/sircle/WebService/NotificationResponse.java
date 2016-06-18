package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Notification;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

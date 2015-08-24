package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 24/08/15.
 */
public class NotificationResponse {

    public int code;
    public List<Notification> message = new ArrayList<Notification>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Notification> getMessage() {
        return message;
    }

    public void setMessage(List<Notification> message) {
        this.message = message;
    }
}

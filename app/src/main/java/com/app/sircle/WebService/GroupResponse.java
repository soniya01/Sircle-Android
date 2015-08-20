package com.app.sircle.WebService;

import com.app.sircle.UI.Model.NotificationGroups;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 21/08/15.
 */
public class GroupResponse {

    public int code;
    public List<NotificationGroups> message = new ArrayList<NotificationGroups>();


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<NotificationGroups> getMessage() {
        return message;
    }

    public void setMessage(List<NotificationGroups> message) {
        this.message = message;
    }

}

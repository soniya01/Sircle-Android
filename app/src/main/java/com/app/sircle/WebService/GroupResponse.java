package com.app.sircle.WebService;

import com.app.sircle.UI.Model.NotificationGroups;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 21/08/15.
 */
public class GroupResponse {

    @SerializedName("code")
    public int status;

    GroupResponseData data;

//    public List<NotificationGroups> data = new ArrayList<NotificationGroups>();

    public String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public GroupResponseData getData() {
        return data;
    }

    public void setData(GroupResponseData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

package com.app.sircle.UI.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by mosesafonso on 28/07/15.
 */
public class NotificationGroups {

    @SerializedName("group_name")
    public String name;

    @SerializedName("group_id")
    public String id;

    public boolean active;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

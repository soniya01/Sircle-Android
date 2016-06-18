package com.app.sircle.WebService;

import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.NotificationGroups;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosesafonso on 28/05/16.
 */
public class GroupResponseData {


    public List<NotificationGroups> groups = new ArrayList<NotificationGroups>();


    public List<NotificationGroups> getGroups() {
        return groups;
    }

    public void setGroups(List<NotificationGroups> groups) {
        this.groups = groups;
    }
}

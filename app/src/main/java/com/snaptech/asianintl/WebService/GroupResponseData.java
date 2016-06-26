package com.snaptech.asianintl.WebService;

import com.snaptech.asianintl.UI.Model.NotificationGroups;

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

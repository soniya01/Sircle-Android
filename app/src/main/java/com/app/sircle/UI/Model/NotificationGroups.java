package com.app.sircle.UI.Model;

import java.util.Date;

/**
 * Created by mosesafonso on 28/07/15.
 */
public class NotificationGroups {
    public String name;
    public String id;
    //public Date created_on;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public Date getCreatedDate() {
//        return created_on;
//    }
//
//    public void setCreatedDate(Date date) {
//        this.created_on = date;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

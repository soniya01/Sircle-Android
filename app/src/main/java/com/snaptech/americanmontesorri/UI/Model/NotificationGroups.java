package com.snaptech.americanmontesorri.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mosesafonso on 28/07/15.
 */
public class NotificationGroups {


//            "sort_order": "0",
//
//            "description": "",
//            "select": false,
//            "is_staff": false

    //@SerializedName("group_name")
    public String name;

    @SerializedName("customer_group_id")
    public String id;

    @SerializedName("select")
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

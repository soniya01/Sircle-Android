package com.grayjam.sircle.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 14/09/15.
 */
public class EventCategory {


    @SerializedName("category_id")
    public String id;
    @SerializedName("name")
    public String category;

//    @SerializedName("created_on")
//    public String createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public String getCreatedOn() {
//        return createdOn;
//    }
//
//    public void setCreatedOn(String createdOn) {
//        this.createdOn = createdOn;
//    }
}

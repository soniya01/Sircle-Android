package com.app.sircle.UI.Model;


import com.google.gson.annotations.SerializedName;

public class Links  {

    //links -> (Array) name, url, created_on, time_string, favicon

    public String name;
    public String url;

    @SerializedName("created_on")
    public String createdOn;

    @SerializedName("time_string")
    public String timeString;

    @SerializedName("favicon")
    public String favIcon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getFavIcon() {
        return favIcon;
    }

    public void setFavIcon(String favIcon) {
        this.favIcon = favIcon;
    }
}

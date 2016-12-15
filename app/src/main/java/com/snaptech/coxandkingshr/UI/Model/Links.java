package com.snaptech.coxandkingshr.UI.Model;


import com.google.gson.annotations.SerializedName;

public class Links  {

    //links -> (Array) name, url, created_on, time_string, favicon

    @SerializedName("link_name")
    public String name;

    @SerializedName("link_url")
    public String url;

    @SerializedName("date_added")
    public String createdOn;

    @SerializedName("link_id")
    public String linkID;

//    @SerializedName("favicon")
//    public String favIcon;

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

    public String getLinkID() {
        return linkID;
    }

    public void setLinkID(String linkID) {
        this.linkID = linkID;
    }
//
//    public String getFavIcon() {
//        return favIcon;
//    }
//
//    public void setFavIcon(String favIcon) {
//        this.favIcon = favIcon;
//    }
}

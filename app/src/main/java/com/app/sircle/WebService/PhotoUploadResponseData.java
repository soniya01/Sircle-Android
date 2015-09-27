package com.app.sircle.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 28/09/15.
 */
public class PhotoUploadResponseData {

    public String thumbnailUrl;
    public String url;

    @SerializedName("image_name")
    public String name;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

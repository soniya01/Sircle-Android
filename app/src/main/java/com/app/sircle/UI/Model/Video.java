package com.app.sircle.UI.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by soniya on 24/07/15.
 */
public class Video {

    public String name;

    @SerializedName("url")
    public String videoEmbedURL;

    @SerializedName("video_thumb")
    public String videoThumbURL;

    @SerializedName("time_string")
    public String time;

    @SerializedName("created_on")
    public String publishDate;

    public String getVideoEmbedURL() {
        return videoEmbedURL;
    }

    public void setVideoEmbedURL(String videoEmbedURL) {
        this.videoEmbedURL = videoEmbedURL;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getVideoThumbURL() {
        return videoThumbURL;
    }

    public void setVideoThumbURL(String videoThumbURL) {
        this.videoThumbURL = videoThumbURL;
    }
}
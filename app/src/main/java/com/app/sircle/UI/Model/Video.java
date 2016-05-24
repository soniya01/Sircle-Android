package com.app.sircle.UI.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by soniya on 24/07/15.
 */
public class Video {

    @SerializedName("caption_name")
    public String name;

    @SerializedName("video_url")
    public String videoEmbedURL;

//    @SerializedName("video_thumb")
//    public String videoThumbURL;
//
//    @SerializedName("time_string")
//    public String time;

    @SerializedName("date")
    public String publishDate;

//    @SerializedName("video_from")
//    public String videoType;

    @SerializedName("file_id")
    public String videoId;


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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }



//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//
//
//    public String getVideoThumbURL() {
//        return videoThumbURL;
//    }
//
//    public void setVideoThumbURL(String videoThumbURL) {
//        this.videoThumbURL = videoThumbURL;
//    }
//
//    public String getVideoType() {
//        return videoType;
//    }
//
//    public void setVideoType(String videoType) {
//        this.videoType = videoType;
//    }



}
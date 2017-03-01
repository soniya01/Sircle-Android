package com.snaptech.colegiobosques.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 24/07/15.
 */
public class Video {


    @SerializedName("caption_name")
    public String name;

    @SerializedName("video_url")
    public String videoEmbedURL;

    @SerializedName("thumbnail")
    public String videoThumbURL;
//
    @SerializedName("file_id")
    public String fileId;

    @SerializedName("fullsize")
    public String fullsize;

    @SerializedName("parsed_url")
    public String parsedUrl;

    @SerializedName("date")
    public String publishDate;

    @SerializedName("video_type")
    public String videoType;

    @SerializedName("video_id")
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



    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFullsize() {
        return fullsize;
    }

    public void setFullsize(String fullsize) {
        this.fullsize = fullsize;
    }

    public String getParsedUrl() {
        return parsedUrl;
    }

    public void setParsedUrl(String parsedUrl) {
        this.parsedUrl = parsedUrl;
    }


//
    public String getVideoThumbURL() {
        return videoThumbURL;
    }

    public void setVideoThumbURL(String videoThumbURL) {
        this.videoThumbURL = videoThumbURL;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }



}
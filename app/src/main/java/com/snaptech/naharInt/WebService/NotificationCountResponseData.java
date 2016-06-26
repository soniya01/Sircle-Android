package com.snaptech.naharInt.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 24/09/15.
 */
public class NotificationCountResponseData {

    @SerializedName("noti_count")
    public String notCount;

    @SerializedName("doc_count")
    public String docCount;

    @SerializedName("video_count")
    public String videoCount;

    @SerializedName("link_count")
    public String linkCount;

    @SerializedName("news_count")
    public String newsCount;

    public String getNotCount() {
        return notCount;
    }

    public void setNotCount(String notCount) {
        this.notCount = notCount;
    }

    public String getDocCount() {
        return docCount;
    }

    public void setDocCount(String docCount) {
        this.docCount = docCount;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public String getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(String linkCount) {
        this.linkCount = linkCount;
    }

    public String getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(String newsCount) {
        this.newsCount = newsCount;
    }
}

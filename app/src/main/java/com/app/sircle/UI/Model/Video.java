package com.app.sircle.UI.Model;

import java.util.Date;

/**
 * Created by soniya on 24/07/15.
 */
public class Video {

    public boolean status;
    public String videoEmbedURL;
    public String videoSource;
    public Date publishDate;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getVideoEmbedURL() {
        return videoEmbedURL;
    }

    public void setVideoEmbedURL(String videoEmbedURL) {
        this.videoEmbedURL = videoEmbedURL;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}

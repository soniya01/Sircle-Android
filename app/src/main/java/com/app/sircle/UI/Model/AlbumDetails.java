package com.app.sircle.UI.Model;

import java.util.Date;

/**
 * Created by soniya on 27/07/15.
 */
public class AlbumDetails {
    public int photoID;
    public String photoCaption;
    public String photoThumbURL;
    public String photoLargeURL;
    public Date publishDate;

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getPhotoCaption() {
        return photoCaption;
    }

    public void setPhotoCaption(String photoCaption) {
        this.photoCaption = photoCaption;
    }

    public String getPhotoThumbURL() {
        return photoThumbURL;
    }

    public void setPhotoThumbURL(String photoThumbURL) {
        this.photoThumbURL = photoThumbURL;
    }

    public String getPhotoLargeURL() {
        return photoLargeURL;
    }

    public void setPhotoLargeURL(String photoLargeURL) {
        this.photoLargeURL = photoLargeURL;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}

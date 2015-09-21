package com.app.sircle.UI.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by soniya on 27/07/15.
 */
public class AlbumDetails {

    @SerializedName("caption")
    public String photoCaption;

    @SerializedName("thumburl")
    public String photoThumbURL;

    @SerializedName("url")
    public String photoLargeURL;

    @SerializedName("photo_width")
    public int photoWidth;

    @SerializedName("photo_height")
    public int photoHeight;


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

    public int getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(int photoWidth) {
        this.photoWidth = photoWidth;
    }

    public int getPhotoHeight() {
        return photoHeight;
    }

    public void setPhotoHeight(int photoHeight) {
        this.photoHeight = photoHeight;
    }
}

package com.app.sircle.UI.Model;

import java.util.Date;

/**
 * Created by soniya on 26/07/15.
 */
public class Photo {

    public int albumID;
    public String albumTitle;
    public int numberOfPhotos;
    public String albumCoverImageURL;
    public Date publishDate;

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public void setNumberOfPhotos(int numberOfPhotos) {
        this.numberOfPhotos = numberOfPhotos;
    }

    public String getAlbumCoverImageURL() {
        return albumCoverImageURL;
    }

    public void setAlbumCoverImageURL(String albumCoverImageURL) {
        this.albumCoverImageURL = albumCoverImageURL;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}

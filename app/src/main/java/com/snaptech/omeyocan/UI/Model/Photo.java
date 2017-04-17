package com.snaptech.omeyocan.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 26/07/15.
 */
public class Photo {


    @SerializedName("album_id")
    public int albumID;

    @SerializedName("album_name")
    public String albumTitle;

    @SerializedName("album_date")
    public String publishDate;

    @SerializedName("file_path")
    public String albumCoverImageURL;


//    @SerializedName("id")
//    public int albumID;
//
//    @SerializedName("name")
//    public String albumTitle;
//
//    @SerializedName("album_display_name")
//    public String albumDisplayName;
//
    @SerializedName("total")
    public int numberOfPhotos;
//
//    @SerializedName("cover_image")
//    public String albumCoverImageURL;
//
//    @SerializedName("created_on")
//    public String publishDate;
//
//    @SerializedName("time_string")
//    public String time;
//
    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
//
    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
//
    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public void setNumberOfPhotos(int numberOfPhotos) {
        this.numberOfPhotos = numberOfPhotos;
    }
//
    public String getAlbumCoverImageURL() {
        return albumCoverImageURL;
    }

    public void setAlbumCoverImageURL(String albumCoverImageURL) {
        this.albumCoverImageURL = albumCoverImageURL;
    }
//
    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

//    public String getAlbumDisplayName() {
//        return albumDisplayName;
//    }
//
//    public void setAlbumDisplayName(String albumDisplayName) {
//        this.albumDisplayName = albumDisplayName;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
}

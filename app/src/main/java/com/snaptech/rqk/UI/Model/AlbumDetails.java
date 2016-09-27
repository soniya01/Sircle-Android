package com.snaptech.rqk.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 27/07/15.
 */
public class AlbumDetails {

    @SerializedName("file_name")
    public String fileName;

    @SerializedName("album_name")
    public String albumName;

    @SerializedName("file_path")
    public String filePath;

    @SerializedName("date")
    public String date;

    @SerializedName("file_id")
    public int fileId;

    @SerializedName("album_id")
    public int albumId;

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getAlbumName()
    {
        return albumName;
    }

    public void setAlbumName(String albumName)
    {
        this.albumName = albumName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;

    }

        public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }


    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

//    @SerializedName("caption")
//    public String photoCaption;
//
//    @SerializedName("thumburl")
//    public String photoThumbURL;
//
//    @SerializedName("url")
//    public String photoLargeURL;
//
//    @SerializedName("photo_width")
//    public int photoWidth;
//
//    @SerializedName("photo_height")
//    public int photoHeight;
//
//
//    public String getPhotoCaption() {
//        return photoCaption;
//    }
//
//    public void setPhotoCaption(String photoCaption) {
//        this.photoCaption = photoCaption;
//    }
//
//    public String getPhotoThumbURL() {
//        return photoThumbURL;
//    }
//
//    public void setPhotoThumbURL(String photoThumbURL) {
//        this.photoThumbURL = photoThumbURL;
//    }
//
//    public String getPhotoLargeURL() {
//        return photoLargeURL;
//    }
//
//    public void setPhotoLargeURL(String photoLargeURL) {
//        this.photoLargeURL = photoLargeURL;
//    }
//
//    public int getPhotoWidth() {
//        return photoWidth;
//    }
//
//    public void setPhotoWidth(int photoWidth) {
//        this.photoWidth = photoWidth;
//    }
//
//    public int getPhotoHeight() {
//        return photoHeight;
//    }
//
//    public void setPhotoHeight(int photoHeight) {
//        this.photoHeight = photoHeight;
//    }
}

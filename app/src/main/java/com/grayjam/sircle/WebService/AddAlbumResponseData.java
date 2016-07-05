package com.grayjam.sircle.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 23/09/15.
 */
public class AddAlbumResponseData {

//            "data": {
//        "method_type": "POST"

    @SerializedName("album_id")
    public String albumId;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}

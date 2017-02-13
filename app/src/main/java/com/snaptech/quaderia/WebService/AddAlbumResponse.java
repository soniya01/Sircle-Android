package com.snaptech.quaderia.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 23/09/15.
 */
public class AddAlbumResponse {

    public String message;

    @SerializedName("code")
    public int status;

    public AddAlbumResponseData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AddAlbumResponseData getData() {
        return data;
    }

    public void setData(AddAlbumResponseData data) {
        this.data = data;
    }
}

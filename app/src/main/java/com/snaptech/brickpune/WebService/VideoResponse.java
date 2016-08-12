package com.snaptech.brickpune.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 18/09/15.
 */
public class VideoResponse {


    @SerializedName("code")
    public int status;
    public String message;
    public VideoResponseData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VideoResponseData getData() {
        return data;
    }

    public void setData(VideoResponseData data) {
        this.data = data;
    }
}

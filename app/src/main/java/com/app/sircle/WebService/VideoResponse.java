package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

package com.snaptech.omeyocan.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 24/08/15.
 */
public class PhotoResponse {


   @SerializedName("code")
    public int status;
    public String message;
    public PhotoResponseData data;

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

    public PhotoResponseData getData() {
        return data;
    }

    public void setData(PhotoResponseData data) {
        this.data = data;
    }
}

package com.snaptech.coxandkingshr.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 28/09/15.
 */
public class PhotoUploadResponse {




    @SerializedName("code")
    public int status;
    public String message;
    public PhotoUploadResponseData data;

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

    public PhotoUploadResponseData getData() {
        return data;
    }

    public void setData(PhotoUploadResponseData data) {
        this.data = data;
    }
}

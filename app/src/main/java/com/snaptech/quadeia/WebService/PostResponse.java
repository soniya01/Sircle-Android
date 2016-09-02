package com.snaptech.quadeia.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 20/09/15.
 */
public class PostResponse {

    @SerializedName("code")
    public int status;
    public String message;

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

}

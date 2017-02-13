package com.snaptech.quaderia.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 22/09/15.
 */
public class EventDetailResponse {


    public String message;
    @SerializedName("code")
    public int status;

    public EventDetailResponseData data;

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

    public EventDetailResponseData getData() {
        return data;
    }

    public void setData(EventDetailResponseData data) {
        this.data = data;
    }
}

package com.app.sircle.WebService;

import java.util.List;

/**
 * Created by soniya on 22/09/15.
 */
public class EventDetailResponse {
    public String message;
    public int status;

    public List<EventDetailResponseData> data;

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

    public List<EventDetailResponseData> getData() {
        return data;
    }

    public void setData(List<EventDetailResponseData> data) {
        this.data = data;
    }
}

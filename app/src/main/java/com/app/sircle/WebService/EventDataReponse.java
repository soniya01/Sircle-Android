package com.app.sircle.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 14/09/15.
 */
public class EventDataReponse {

    public String message;
    public int status;

    @SerializedName("data")
    public EventData eventData;

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

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }
}

package com.snaptech.kapruddin2016.WebService;

import com.snaptech.kapruddin2016.UI.Model.Event;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by soniya on 14/09/15.
 */
public class EventDataReponse {


    public String message;

    @SerializedName("code")
    public int status;

    @SerializedName("data")
    public List<Event> events;



    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

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

//    public EventData getEventData() {
//        return eventData;
//    }
//
//    public void setEventData(EventData eventData) {
//        this.eventData = eventData;
//    }
}

package com.snaptech.montesorriamerican.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 27/09/15.
 */
public class EventDetailType {

    @SerializedName("event_type_id")
    public String eventTypeId;

    @SerializedName("type_name")
    public String eventType;

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}

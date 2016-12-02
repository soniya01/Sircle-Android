package com.snaptech.institutoverdemolina.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 27/09/15.
 */
public class EventRepeatsMonthly {

    public String id;

    @SerializedName("repeats_id")
    public String repeatsId;

    @SerializedName("repeat_type_id")
    public String repeatTypeId;

    @SerializedName("created_on")
    public String createdOn;

    @SerializedName("modified_on")
    public String modifiedOn;

    @SerializedName("delflag")
    public String delFlag;

    @SerializedName("event_id")
    public String eventId;

    @SerializedName("repeat_on")
    public String repOn;

    @SerializedName("repeat_on_id")
    public String repeatOnId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepeatsId() {
        return repeatsId;
    }

    public void setRepeatsId(String repeatsId) {
        this.repeatsId = repeatsId;
    }

    public String getRepeatTypeId() {
        return repeatTypeId;
    }

    public void setRepeatTypeId(String repeatTypeId) {
        this.repeatTypeId = repeatTypeId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRepOn() {
        return repOn;
    }

    public void setRepOn(String repOn) {
        this.repOn = repOn;
    }

    public String getRepeatOnId() {
        return repeatOnId;
    }

    public void setRepeatOnId(String repeatOnId) {
        this.repeatOnId = repeatOnId;
    }
}

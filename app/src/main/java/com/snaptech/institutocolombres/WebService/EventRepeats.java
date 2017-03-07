package com.snaptech.institutocolombres.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 27/09/15.
 */
public class EventRepeats {


    public String id;
    public String name;
    public String type;

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

    @SerializedName("rep_on")
    public String repOn;

    @SerializedName("repeat_every")
    public String repeatEvery;

    @SerializedName("rep_never")
    public String repNever;

    @SerializedName("repeat_end_type_id")
    public String repEndTypeId;

    @SerializedName("rep_after_occurences")
    public String repAfterOccurrences;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRepeatEvery() {
        return repeatEvery;
    }

    public void setRepeatEvery(String repeatEvery) {
        this.repeatEvery = repeatEvery;
    }

    public String getRepNever() {
        return repNever;
    }

    public void setRepNever(String repNever) {
        this.repNever = repNever;
    }

    public String getRepEndTypeId() {
        return repEndTypeId;
    }

    public void setRepEndTypeId(String repEndTypeId) {
        this.repEndTypeId = repEndTypeId;
    }

    public String getRepAfterOccurrences() {
        return repAfterOccurrences;
    }

    public void setRepAfterOccurrences(String repAfterOccurrences) {
        this.repAfterOccurrences = repAfterOccurrences;
    }
}

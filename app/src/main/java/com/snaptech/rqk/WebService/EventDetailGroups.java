package com.snaptech.rqk.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 27/09/15.
 */
public class EventDetailGroups {

    // for groups
//    id: "2"
//    name: "group 1"
//    staff_tag: "0"
//    created_on: "2015-03-10 13:37:31"
//    modified_on: "2015-03-10 13:37:31"
//    delflag: "0"
//    event_id: "3"
//    group_id: "1"
//
//   for event repeats
// id: "1"
//    type: "Daily"
//    created_on: "2014-08-18 15:19:40"
//    modified_on: "2014-08-18 15:19:42"
//    delflag: "0"

    public String id;
    public String name;
    public String type;

    @SerializedName("staff_tag")
    public String staffTag;

    @SerializedName("created_on")
    public String createdOn;

    @SerializedName("modified_on")
    public String modifiedOn;

    @SerializedName("delflag")
    public String delFlag;

    @SerializedName("event_id")
    public String eventId;

    @SerializedName("group_id")
    public String groupId;

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

    public String getStaffTag() {
        return staffTag;
    }

    public void setStaffTag(String staffTag) {
        this.staffTag = staffTag;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

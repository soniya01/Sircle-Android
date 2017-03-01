package com.snaptech.colegiobosques.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 14/09/15.
 */
public class Event {


//            "": "asdfasdf",
//            "": "asdfasdf",
//            "": "01-06-2016 12:00 AM",
//            "": "02-06-2016 11:30 PM",
//            "": "12:00 AM",
//            "": "11:30 PM",
//            "": "M"

    @SerializedName("event_id")
    public String id;

    @SerializedName("category_name")
    public  String categoryName;

//    @SerializedName("unq_id")
//    public String uniqueId;

    @SerializedName("event_title")
    public String title;
    public String event_type;

//    @SerializedName("event_type_id")
//    public String eventTypeId;

//    @SerializedName("icon_id")
//    public int iconId;

    @SerializedName("event_start_date")
    public String startDate;

    @SerializedName("event_end_date")
    public String endDate;

    @SerializedName("event_from")
    public String startTime;

    @SerializedName("end_time")
    public String endTime;

    @SerializedName("event_to")
    public String location;

//    @SerializedName("event_category")
//    public String category;

    @SerializedName("event_description")
    public String detail;

    @SerializedName("event_priority")
    public String eventPriority;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getUniqueId() {
//        return uniqueId;
//    }
//
//    public void setUniqueId(String uniqueId) {
//        this.uniqueId = uniqueId;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getEventTypeId() {
//        return eventTypeId;
//    }
//
//    public void setEventTypeId(String eventTypeId) {
//        this.eventTypeId = eventTypeId;
//    }
//
//    public int getIconId() {
//        return iconId;
//    }
//
//    public void setIconId(int iconId) {
//        this.iconId = iconId;
//    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEventPriority() {
        return eventPriority;
    }

    public void setEventPriority(String eventPriority) {
        this.eventPriority = eventPriority;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }


//      id: "3"
//    unq_id: "74c1247e6b139ee7115a6f78be4d2238"
//    title: "test event"
//    start_date: "Thursday 26 Mar 2015"
//    end_date: "Thursday 26 Mar 2015"
//    start_time: "06:00:00"
//    end_time: "18:00:00"
//    location: "malad"
//    event_category: "Sports"
//    detail: "sports day"
}
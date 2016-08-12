package com.snaptech.brickpune.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 22/09/15.
 */
public class EventDetailResponseData {


    @SerializedName("event_id")
    public String id;

    @SerializedName("event_title")
    public String eventTitle;

    @SerializedName("event_description")
    public String eventDescription;

    @SerializedName("event_location")
    public String eventLocation;

    @SerializedName("event_start_date")
    public String eventStartDate;

    @SerializedName("event_end_date")
    public String eventEndDate;

//    @SerializedName("event_type")
//    public EventDetailType eventType;

//    @SerializedName("event_info")
//    public Event eventInfo;

//    @SerializedName("event_groups")
//    public List<EventDetailGroups> eventGroups;

//    @SerializedName("event_repeats_type")
//    public List<EventDetailGroups> eventRepeatsType;

//    @SerializedName("event_repeats")
//    public List<EventRepeats> eventRepeats;

//    @SerializedName("event_repeats_days")
//    public List<EventRepeatsDays> eventRepeatDays;

//    @SerializedName("event_repeats_months")
//    public List<EventRepeatsDays> eventRepeatMonths;

//    @SerializedName("event_repeats_years")
//    public List<EventRepeatsDays> eventRepeatYears;

//    @SerializedName("event_repeats_weeks")
//    public List<EventRepeatsDays> eventRepeatWeeks;
   // public List<EventRepeatsDays> eventWeekDays;

//    @SerializedName("event_repeats_monthly")
//    public List<EventRepeatsMonthly> eventRepeatsMonthly;

//    @SerializedName("event_repeats_weekly_dys")
//    public List<EventRepeatsMonthly> eventRepeatsWeeklyDys;

    //data -> event_id, event_type (Array), event_info (Obj),
    // event_groups (Array), event_repeats (Array), event_repeats_type (Array),
    // event_repeats_days (Array), event_repeats_weeks (Array), event_repeats_months (Array),
    // event_repeats_years (Array), event_week_days (Array), event_repeats_weekly_dys (Array),
    // event_repeats_monthly (Array)


    //{event_type:event_type_id,title:title,loc:loc,
    // event_cat:event_cat,grp:grp,strdate:strdate,
    // enddate:enddate,strtime:strtime,endtime:endtime,
    // detail:det,rem_days:rem_days,rem_hours:rem_hours,
    // rem_mins:rem_mins,repeats:repeats,repeat_type_id:repeat_type_id,
    // repeat_type:repeat_type_name, repeat_every : number,
    // repeat_end_type_id: end_type_id, repeat_end_type : end_type_text,
    // rep_after_occurence, rep_ondate, repeat_week_days: (Array), grp, repeat_monthly_on


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

//    public EventDetailType getEventType() {
//        return eventType;
//    }
//
//    public void setEventType(EventDetailType eventType) {
//        this.eventType = eventType;
//    }

//    public Event getEventInfo() {
//        return eventInfo;
//    }
//
//    public void setEventInfo(Event eventInfo) {
//        this.eventInfo = eventInfo;
//    }

//    public List<EventDetailGroups> getEventGroups() {
//        return eventGroups;
//    }
//
//    public void setEventGroups(List<EventDetailGroups> eventGroups) {
//        this.eventGroups = eventGroups;
//    }

//    public List<EventDetailGroups> getEventRepeatsType() {
//        return eventRepeatsType;
//    }
//
//    public void setEventRepeatsType(List<EventDetailGroups> eventRepeatsType) {
//        this.eventRepeatsType = eventRepeatsType;
//    }

//    public List<EventRepeats> getEventRepeats() {
//        return eventRepeats;
//    }
//
//    public void setEventRepeats(List<EventRepeats> eventRepeats) {
//        this.eventRepeats = eventRepeats;
//    }

//    public List<EventRepeatsDays> getEventRepeatDays() {
//        return eventRepeatDays;
//    }
//
//    public void setEventRepeatDays(List<EventRepeatsDays> eventRepeatDays) {
//        this.eventRepeatDays = eventRepeatDays;
//    }

//    public List<EventRepeatsDays> getEventRepeatMonths() {
//        return eventRepeatMonths;
//    }
//
//    public void setEventRepeatMonths(List<EventRepeatsDays> eventRepeatMonths) {
//        this.eventRepeatMonths = eventRepeatMonths;
//    }

//    public List<EventRepeatsDays> getEventRepeatYears() {
//        return eventRepeatYears;
//    }
//
//    public void setEventRepeatYears(List<EventRepeatsDays> eventRepeatYears) {
//        this.eventRepeatYears = eventRepeatYears;
//    }

//    public List<EventRepeatsDays> getEventRepeatWeeks() {
//        return eventRepeatWeeks;
//    }
//
//    public void setEventRepeatWeeks(List<EventRepeatsDays> eventRepeatWeeks) {
//        this.eventRepeatWeeks = eventRepeatWeeks;
//    }

//    public List<EventRepeatsMonthly> getEventRepeatsMonthly() {
//        return eventRepeatsMonthly;
//    }
//
//    public void setEventRepeatsMonthly(List<EventRepeatsMonthly> eventRepeatsMonthly) {
//        this.eventRepeatsMonthly = eventRepeatsMonthly;
//    }

//    public List<EventRepeatsMonthly> getEventRepeatsWeeklyDys() {
//        return eventRepeatsWeeklyDys;
//    }
//
//    public void setEventRepeatsWeeklyDys(List<EventRepeatsMonthly> eventRepeatsWeeklyDys) {
//        this.eventRepeatsWeeklyDys = eventRepeatsWeeklyDys;
//    }
}

package com.app.sircle.WebService;

/**
 * Created by soniya on 22/09/15.
 */
public class EventDetailResponseData {

    public String id;
    public String eventType;
    public String eventInfo;
    public String eventGroups;
    public String eventRepeats;
    public String eventRepeatsType;
    public String eventRepeatDays;
    public String eventRepeatMonths;
    public String eventRepeatYears;
    public String eventRepeatWeeks;
    public String eventWeekDays;
    public String eventRepeatsMonthly;
    public String eventRepeatsWeeklyDys;

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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public String getEventGroups() {
        return eventGroups;
    }

    public void setEventGroups(String eventGroups) {
        this.eventGroups = eventGroups;
    }

    public String getEventRepeats() {
        return eventRepeats;
    }

    public void setEventRepeats(String eventRepeats) {
        this.eventRepeats = eventRepeats;
    }

    public String getEventRepeatsType() {
        return eventRepeatsType;
    }

    public void setEventRepeatsType(String eventRepeatsType) {
        this.eventRepeatsType = eventRepeatsType;
    }

    public String getEventRepeatDays() {
        return eventRepeatDays;
    }

    public void setEventRepeatDays(String eventRepeatDays) {
        this.eventRepeatDays = eventRepeatDays;
    }

    public String getEventRepeatMonths() {
        return eventRepeatMonths;
    }

    public void setEventRepeatMonths(String eventRepeatMonths) {
        this.eventRepeatMonths = eventRepeatMonths;
    }

    public String getEventRepeatYears() {
        return eventRepeatYears;
    }

    public void setEventRepeatYears(String eventRepeatYears) {
        this.eventRepeatYears = eventRepeatYears;
    }

    public String getEventRepeatWeeks() {
        return eventRepeatWeeks;
    }

    public void setEventRepeatWeeks(String eventRepeatWeeks) {
        this.eventRepeatWeeks = eventRepeatWeeks;
    }

    public String getEventWeekDays() {
        return eventWeekDays;
    }

    public void setEventWeekDays(String eventWeekDays) {
        this.eventWeekDays = eventWeekDays;
    }

    public String getEventRepeatsMonthly() {
        return eventRepeatsMonthly;
    }

    public void setEventRepeatsMonthly(String eventRepeatsMonthly) {
        this.eventRepeatsMonthly = eventRepeatsMonthly;
    }

    public String getEventRepeatsWeeklyDys() {
        return eventRepeatsWeeklyDys;
    }

    public void setEventRepeatsWeeklyDys(String eventRepeatsWeeklyDys) {
        this.eventRepeatsWeeklyDys = eventRepeatsWeeklyDys;
    }
}

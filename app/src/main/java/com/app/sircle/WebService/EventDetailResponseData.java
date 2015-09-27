package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Event;

import java.util.List;

/**
 * Created by soniya on 22/09/15.
 */
public class EventDetailResponseData {

    public String id;

    public String eventType;
    public Event eventInfo;
    public List<EventDetailGroups> eventGroups;
    public List<EventDetailGroups> eventRepeatsType;
    public List<EventRepeats> eventRepeats;
    public List<EventRepeatsDays> eventRepeatDays;
    public List<EventRepeatsDays> eventRepeatMonths;
    public List<EventRepeatsDays> eventRepeatYears;
    public List<EventRepeatsDays> eventRepeatWeeks;
   // public List<EventRepeatsDays> eventWeekDays;
    public EventRepeatsMonthly eventRepeatsMonthly;
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

    public Event getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(Event eventInfo) {
        this.eventInfo = eventInfo;
    }

    public List<EventDetailGroups> getEventGroups() {
        return eventGroups;
    }

    public void setEventGroups(List<EventDetailGroups> eventGroups) {
        this.eventGroups = eventGroups;
    }

    public List<EventDetailGroups> getEventRepeatsType() {
        return eventRepeatsType;
    }

    public void setEventRepeatsType(List<EventDetailGroups> eventRepeatsType) {
        this.eventRepeatsType = eventRepeatsType;
    }

    public List<EventRepeats> getEventRepeats() {
        return eventRepeats;
    }

    public void setEventRepeats(List<EventRepeats> eventRepeats) {
        this.eventRepeats = eventRepeats;
    }

    public List<EventRepeatsDays> getEventRepeatDays() {
        return eventRepeatDays;
    }

    public void setEventRepeatDays(List<EventRepeatsDays> eventRepeatDays) {
        this.eventRepeatDays = eventRepeatDays;
    }

    public List<EventRepeatsDays> getEventRepeatMonths() {
        return eventRepeatMonths;
    }

    public void setEventRepeatMonths(List<EventRepeatsDays> eventRepeatMonths) {
        this.eventRepeatMonths = eventRepeatMonths;
    }

    public List<EventRepeatsDays> getEventRepeatYears() {
        return eventRepeatYears;
    }

    public void setEventRepeatYears(List<EventRepeatsDays> eventRepeatYears) {
        this.eventRepeatYears = eventRepeatYears;
    }

    public List<EventRepeatsDays> getEventRepeatWeeks() {
        return eventRepeatWeeks;
    }

    public void setEventRepeatWeeks(List<EventRepeatsDays> eventRepeatWeeks) {
        this.eventRepeatWeeks = eventRepeatWeeks;
    }

    public EventRepeatsMonthly getEventRepeatsMonthly() {
        return eventRepeatsMonthly;
    }

    public void setEventRepeatsMonthly(EventRepeatsMonthly eventRepeatsMonthly) {
        this.eventRepeatsMonthly = eventRepeatsMonthly;
    }

    public String getEventRepeatsWeeklyDys() {
        return eventRepeatsWeeklyDys;
    }

    public void setEventRepeatsWeeklyDys(String eventRepeatsWeeklyDys) {
        this.eventRepeatsWeeklyDys = eventRepeatsWeeklyDys;
    }
}

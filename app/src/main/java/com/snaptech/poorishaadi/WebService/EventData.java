package com.snaptech.poorishaadi.WebService;

import com.snaptech.poorishaadi.UI.Model.Event;

import java.util.List;

/**
 * Created by soniya on 14/09/15.
 */
public class EventData {

//    @SerializedName("page_records")
//    public int pageRecords;
//
//    @SerializedName("total_records")
//    public int totalRecords;
//
//    public int page;
    public List<Event> events;


//    public int getPageRecords() {
//        return pageRecords;
//    }
//
//    public void setPageRecords(int pageRecords) {
//        this.pageRecords = pageRecords;
//    }
//
//    public int getTotalRecords() {
//        return totalRecords;
//    }
//
//    public void setTotalRecords(int totalRecords) {
//        this.totalRecords = totalRecords;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

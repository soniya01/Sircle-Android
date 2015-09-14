package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Event;
import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 14/09/15.
 */
public class EventData {

    @SerializedName("page_records")
    public int pageRecords;

    @SerializedName("total_records")
    public int totalRecords;

    public int page;
    public Event events;


    public int getPageRecords() {
        return pageRecords;
    }

    public void setPageRecords(int pageRecords) {
        this.pageRecords = pageRecords;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Event getEvent() {
        return events;
    }

    public void setEvent(Event event) {
        this.events = event;
    }
}

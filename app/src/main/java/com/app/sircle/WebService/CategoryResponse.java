package com.app.sircle.WebService;

import com.app.sircle.UI.Model.EventCategory;

import java.util.List;

/**
 * Created by soniya on 14/09/15.
 */
public class CategoryResponse {

    public String message;
    public int status;

    public List<EventCategory> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<EventCategory> getData() {
        return data;
    }

    public void setData(List<EventCategory> data) {
        this.data = data;
    }
}

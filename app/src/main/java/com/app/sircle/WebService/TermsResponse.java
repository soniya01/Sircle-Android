package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Terms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 18/09/15.
 */
public class TermsResponse {
    public int status;
    public String message;
    public List<Terms> data = new ArrayList<Terms>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Terms> getData() {
        return data;
    }

    public void setData(List<Terms> data) {
        this.data = data;
    }
}

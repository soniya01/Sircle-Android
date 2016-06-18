package com.app.sircle.WebService;

import com.app.sircle.UI.Model.EventCategory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by soniya on 14/09/15.
 */
public class CategoryResponse {



    public String message;
        @SerializedName("code")
    public int status;

    public CategoryResponseData data;

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

    public CategoryResponseData getData() {
        return data;
    }

    public void setData(CategoryResponseData data) {
        this.data = data;
    }
}

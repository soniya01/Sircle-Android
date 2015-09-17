package com.app.sircle.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mosesafonso on 09/08/15.
 */
public class Terms {

    public String id;

    @SerializedName("title")
    public String termTitle;

    @SerializedName("start_date")
    public String termStartDate;

    @SerializedName("start_icon_id")
    public String startIconId;

    @SerializedName("end_date")
    public String termEndDate;

    @SerializedName("end_icon_id")
    public String endIconId;


    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }


    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(String eventTime) {
        this.termEndDate = termEndDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartIconId() {
        return startIconId;
    }

    public void setStartIconId(String startIconId) {
        this.startIconId = startIconId;
    }

    public String getEndIconId() {
        return endIconId;
    }

    public void setEndIconId(String endIconId) {
        this.endIconId = endIconId;
    }
}

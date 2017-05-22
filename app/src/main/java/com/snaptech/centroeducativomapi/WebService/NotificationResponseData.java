package com.snaptech.centroeducativomapi.WebService;

import com.snaptech.centroeducativomapi.UI.Model.Notification;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 20/09/15.
 */
public class NotificationResponseData {

//    @SerializedName("total_records")
//    public int totalRecords;
//    public int page;
//
//    @SerializedName("page_records")
//    public int pageRecords;



    List<Notification> notifications = new ArrayList<Notification>();

    @SerializedName("method_type")
    public String methodType;

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
//
//    public int getPageRecords() {
//        return pageRecords;
//    }
//
//    public void setPageRecords(int pageRecords) {
//        this.pageRecords = pageRecords;
//    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}

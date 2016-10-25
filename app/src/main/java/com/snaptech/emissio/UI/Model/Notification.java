package com.snaptech.emissio.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soniya on 08/08/15.
 */
public class Notification {


    @SerializedName("notification_title")
    public String subject;

    @SerializedName("notification_message")
    public String message;

    @SerializedName("notification_time")
    public String publishDate;

    @SerializedName("notification_id")
    public String Id;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}

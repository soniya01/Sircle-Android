package com.snaptech.montessorideirapuato.WebService;

/**
 * Created by soniya on 24/09/15.
 */
public class NotificationCountResponse {

    public int status;
    public String message;
    public NotificationCountResponseData data;

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

    public NotificationCountResponseData getData() {
        return data;
    }

    public void setData(NotificationCountResponseData data) {
        this.data = data;
    }
}

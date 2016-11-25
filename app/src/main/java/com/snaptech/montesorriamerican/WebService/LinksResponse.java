package com.snaptech.montesorriamerican.WebService;

/**
 * Created by soniya on 18/09/15.
 */
public class LinksResponse {
    public int status;
    public String message;
    public LinksResponseData data ;

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

    public LinksResponseData getData() {
        return data;
    }

    public void setData(LinksResponseData data) {
        this.data = data;
    }
}

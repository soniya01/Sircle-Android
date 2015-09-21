package com.app.sircle.WebService;

import java.util.List;

/**
 * Created by soniya on 21/09/15.
 */
public class AlbumResponse {

    public String message;
    public int status;

    public AlbumResponseData data;

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

    public AlbumResponseData getData() {
        return data;
    }

    public void setData(AlbumResponseData data) {
        this.data = data;
    }
}

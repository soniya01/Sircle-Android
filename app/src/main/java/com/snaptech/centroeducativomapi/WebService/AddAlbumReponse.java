package com.snaptech.centroeducativomapi.WebService;

import java.util.HashMap;

/**
 * Created by soniya on 28/09/15.
 */
public class AddAlbumReponse {

    public int status;
    public String message;
    public HashMap<String, String> data;

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

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}

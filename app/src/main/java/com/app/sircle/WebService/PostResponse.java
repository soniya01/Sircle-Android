package com.app.sircle.WebService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 20/09/15.
 */
public class PostResponse {
    public int status;
    public String message;

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

}

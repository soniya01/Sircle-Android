package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 24/08/15.
 */
public class PhotoResponse {

    public int code;
    public List<Photo> message = new ArrayList<Photo>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Photo> getMessage() {
        return message;
    }

    public void setMessage(List<Photo> message) {
        this.message = message;
    }
}

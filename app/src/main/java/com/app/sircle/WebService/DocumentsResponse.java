package com.app.sircle.WebService;

import com.app.sircle.UI.Model.NewsLetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 24/08/15.
 */
public class DocumentsResponse {

    public int code;
    public String message;
    public DocumentResponseData data;

    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DocumentResponseData getData() {
        return data;
    }

    public void setData(DocumentResponseData data) {
        this.data = data;
    }
}

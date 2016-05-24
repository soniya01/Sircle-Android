package com.app.sircle.WebService;

import com.app.sircle.UI.Model.NewsLetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 24/08/15.
 */
public class DocumentsResponse {

    public int status;
    public String message;
    public DocumentResponseData data;

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

    public DocumentResponseData getData() {
        return data;
    }

    public void setData(DocumentResponseData data) {
        this.data = data;
    }
}

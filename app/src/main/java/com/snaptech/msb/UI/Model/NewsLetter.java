package com.snaptech.msb.UI.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mosesafonso on 26/07/15.
 */
public class NewsLetter {


    @SerializedName("file_id")
    public String id;

    @SerializedName("file_name")
    public String name;

    @SerializedName("file_path")
    public String path;

    @SerializedName("document_name")
    public String docName;

    @SerializedName("newspaper_name")
    public String newspaperName;

    public String date;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getNewspaperName() {
        return newspaperName;
    }

    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

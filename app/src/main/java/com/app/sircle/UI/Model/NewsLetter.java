package com.app.sircle.UI.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by mosesafonso on 26/07/15.
 */
public class NewsLetter {


    public String name;
    @SerializedName("news_file")
    public String newsFile;

    @SerializedName("created_on")
    public String pdfDate;
    @SerializedName("time_string")
    public String pdfTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewsFile() {
        return newsFile;
    }

    public void setNewsFile(String newsFile) {
        this.newsFile = newsFile;
    }

    public String getPdfDate() {
        return pdfDate;
    }

    public void setPdfDate(String pdfDate) {
        this.pdfDate = pdfDate;
    }

    public String getPdfTime() {
        return pdfTime;
    }

    public void setPdfTime(String pdfTime) {
        this.pdfTime = pdfTime;
    }
}

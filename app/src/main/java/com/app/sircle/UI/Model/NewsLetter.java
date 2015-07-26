package com.app.sircle.UI.Model;

import java.util.Date;

/**
 * Created by mosesafonso on 26/07/15.
 */
public class NewsLetter {


    public String pdfUrl;
    public String pdfTitle;
    public String pdfDate;
    public String pdfTime;



    public String getPdfURL() {
        return pdfUrl;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfUrl = pdfURL;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
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

    public void setPdfTime(String pdfTitle) {
        this.pdfTime = pdfTime;
    }
}

package com.app.sircle.WebService;

import com.app.sircle.UI.Model.NewsLetter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 18/09/15.
 */
public class DocumentResponseData {

    @SerializedName("total_records")
    public int totalRecords;
    public int page;

    @SerializedName("page_records")
    public int pageRecords;
    public List<NewsLetter> documents = new ArrayList<NewsLetter>();

    public List<NewsLetter> newsletters = new ArrayList<NewsLetter>();

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageRecords() {
        return pageRecords;
    }

    public void setPageRecords(int pageRecords) {
        this.pageRecords = pageRecords;
    }

    public List<NewsLetter> getDocs() {
        return documents;
    }

    public void setDocs(List<NewsLetter> links) {
        this.documents = links;
    }

    public List<NewsLetter> getNewsLetters() {
        return newsletters;
    }

    public void setNewsLetters(List<NewsLetter> newsLetters) {
        this.newsletters = newsLetters;
    }

}

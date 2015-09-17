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
    List<NewsLetter> documents = new ArrayList<NewsLetter>();

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

    public List<NewsLetter> getLinks() {
        return documents;
    }

    public void setLinks(List<NewsLetter> links) {
        this.documents = links;
    }
}

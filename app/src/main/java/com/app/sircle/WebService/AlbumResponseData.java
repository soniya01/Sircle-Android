package com.app.sircle.WebService;

import com.app.sircle.UI.Model.AlbumDetails;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 21/09/15.
 */
public class AlbumResponseData {

    @SerializedName("total_records")
    public int totalRecords;
    public int page;

    @SerializedName("page_records")
    public int pageRecords;
    List<AlbumDetails> photos = new ArrayList<AlbumDetails>();

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

    public List<AlbumDetails> getPhotos() {
        return photos;
    }

    public void setPhotos(List<AlbumDetails> albumDetailsList) {
        this.photos = albumDetailsList;
    }
}

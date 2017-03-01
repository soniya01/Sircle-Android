package com.snaptech.colegiobosques.WebService;

import com.snaptech.colegiobosques.UI.Model.Photo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 18/09/15.
 */
public class PhotoResponseData {


//    @SerializedName("total_records")
//    public int totalRecords;
//    public int page;
//
//    @SerializedName("page_records")
//    public int pageRecords;
    List<Photo> albums = new ArrayList<Photo>();
    @SerializedName("method_type")
    public String methodType;

//    public int getTotalRecords() {
//        return totalRecords;
//    }
//
//    public void setTotalRecords(int totalRecords) {
//        this.totalRecords = totalRecords;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }
//
//    public int getPageRecords() {
//        return pageRecords;
//    }
//
//    public void setPageRecords(int pageRecords) {
//        this.pageRecords = pageRecords;
//    }

    public List<Photo> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Photo> albums) {
        this.albums = albums;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}

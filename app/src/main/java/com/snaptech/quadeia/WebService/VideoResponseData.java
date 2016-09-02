package com.snaptech.quadeia.WebService;

import com.snaptech.quadeia.UI.Model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 18/09/15.
 */
public class VideoResponseData {

//    @SerializedName("total_records")
//    public int totalRecords;
//    public int page;
//
//    @SerializedName("page_records")
//    public int pageRecords;

    @SerializedName("method_type")
    public String methodType;

    List<Video> videos = new ArrayList<Video>();

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

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}

package com.snaptech.montessorideirapuato.WebService;

import com.snaptech.montessorideirapuato.UI.Model.Links;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 18/09/15.
 */
public class LinksResponseData {

//    @SerializedName("total_records")
//    public int totalRecords;
//    public int page;
//
//    @SerializedName("page_records")
//    public int pageRecords;



    List<Links> links = new ArrayList<Links>();

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

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> albums) {
        this.links = albums;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}

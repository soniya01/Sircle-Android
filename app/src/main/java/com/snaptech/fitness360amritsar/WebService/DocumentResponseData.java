package com.snaptech.fitness360amritsar.WebService;

import com.snaptech.fitness360amritsar.UI.Model.NewsLetter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 18/09/15.
 */
public class DocumentResponseData {

//    @SerializedName("total_records")
//    public int totalRecords;
//
//    public int page;
//
//    @SerializedName("page_records")
//    public int pageRecords;

    @SerializedName("method_type")
    public String methodType;

    public List<NewsLetter> documents = new ArrayList<NewsLetter>();

   // public List<NewsLetter> newsletters = new ArrayList<NewsLetter>();

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


//    public List<NewsLetter> getNewsLetters() {
//        return newsletters;
//    }
//
//    public void setNewsLetters(List<NewsLetter> newsLetters) {
//        this.newsletters = newsLetters;
//    }

    public List<NewsLetter> getDocs() {
        return documents;
    }

    public void setDocs(List<NewsLetter> docs) {
        this.documents = docs;
    }



    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}

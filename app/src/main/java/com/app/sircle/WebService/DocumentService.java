package com.app.sircle.WebService;

import com.app.sircle.UI.Model.NewsLetter;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.Common.RetrofitImplementation;
import com.app.sircle.WebService.Common.WebServiceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 24/08/15.
 */
public class DocumentService {

    public List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    private static RetrofitImplementation retrofitImplementation;

    private static DocumentService sharedInstance;

    public static DocumentService getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new DocumentService();
            retrofitImplementation = new RetrofitImplementation();
        }
        return sharedInstance;
    }

    private DocumentService(){

    }

    public void getAllNewsLetters(HashMap object, final GetNewsWebServiceListener getNewsWebServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.NEWSLETTERS_GET_API_PATH, object, null, DocumentsResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
               // List<NewsLetter> newsLetters = ((DocumentsResponse) responseObject).getData();
                getNewsWebServiceListener.onCompletion(((DocumentsResponse) responseObject), error);
            }
        });
    }

    public void getAllDocs(HashMap object, final GetNewsWebServiceListener getNewsWebServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.DOCUMENTS_GET_API_PATH, object, null, DocumentsResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                getNewsWebServiceListener.onCompletion(((DocumentsResponse) responseObject), error);
            }
        });
    }

    public interface GetNewsWebServiceListener{
        public void onCompletion(DocumentsResponse response, AppError error);
    }

}

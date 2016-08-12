package com.snaptech.brickpune.WebService;

import com.snaptech.brickpune.UI.Model.NewsLetter;
import com.snaptech.brickpune.Utility.AppError;
import com.snaptech.brickpune.Utility.Constants;
import com.snaptech.brickpune.WebService.Common.RetrofitImplementation;
import com.snaptech.brickpune.WebService.Common.WebServiceListener;

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
        retrofitImplementation.executePostWithURL(Constants.NEWSLETTERS_GET_API_PATH, object, null, DocumentsResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
               // List<NewsLetter> newsLetters = ((DocumentsResponse) responseObject).getData();
                getNewsWebServiceListener.onCompletion(((DocumentsResponse) responseObject), error);
            }
        });
    }

    public void getAllDocs(HashMap object, final GetNewsWebServiceListener getNewsWebServiceListener){
        retrofitImplementation.executePostWithURL(Constants.DOCUMENTS_GET_API_PATH, object, null, DocumentsResponse.class, new WebServiceListener() {
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

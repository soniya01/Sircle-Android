package com.app.sircle.Manager;

import com.app.sircle.UI.Model.NewsLetter;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.DocumentService;
import com.app.sircle.WebService.DocumentsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 24/08/15.
 */
public class DocumentManager {

    public List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();

    private static DocumentManager sharedInstance;

    private DocumentManager(){

    }

    public static DocumentManager getSharedInstance(){

        if (sharedInstance == null){
            sharedInstance =  new DocumentManager();
        }
        return sharedInstance;
    }

    public void getAllNewsLetters(HashMap object, final GetNewsManagerListener getNewsManagerListener){
        DocumentService.getSharedInstance().getAllNewsLetters(object, new DocumentService.GetNewsWebServiceListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {
                getNewsManagerListener.onCompletion(response, error);
            }
        });
    }

    public void getAllDocs(HashMap object, final GetNewsManagerListener getNewsManagerListener){
        DocumentService.getSharedInstance().getAllDocs(object, new DocumentService.GetNewsWebServiceListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {
                getNewsManagerListener.onCompletion(response, error);
            }
        });
    }

    public interface GetNewsManagerListener{
        public void onCompletion(DocumentsResponse response, AppError error);
    }

}

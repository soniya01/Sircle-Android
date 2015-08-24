package com.app.sircle.Manager;

import com.app.sircle.UI.Model.NewsLetter;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.DocumentService;

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
            public void onCompletion(List<NewsLetter> newsLetters, AppError error) {
                getNewsManagerListener.onCompletion(newsLetters, error);
            }
        });
    }

    public interface GetNewsManagerListener{
        public void onCompletion(List<NewsLetter> newsLetters, AppError error);
    }
}

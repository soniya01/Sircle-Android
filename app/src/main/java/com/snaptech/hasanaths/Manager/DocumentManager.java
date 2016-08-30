package com.snaptech.hasanaths.Manager;

import com.snaptech.hasanaths.UI.Model.NewsLetter;
import com.snaptech.hasanaths.Utility.AppError;
import com.snaptech.hasanaths.WebService.DocumentService;
import com.snaptech.hasanaths.WebService.DocumentsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 24/08/15.
 */
public class DocumentManager {

    public static List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    public static List<NewsLetter> docsList = new ArrayList<NewsLetter>();

    private static DocumentManager sharedInstance;

    private DocumentManager(){

    }

    public static DocumentManager getSharedInstance(){

        if (sharedInstance == null){
            sharedInstance =  new DocumentManager();
        }
        return sharedInstance;
    }

    public void getAllNewsLetters(HashMap object, final GetDocumentManagerListener getNewsManagerListener){
        DocumentService.getSharedInstance().getAllNewsLetters(object, new DocumentService.GetNewsWebServiceListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {
                if (response != null){
                    if (response.getData() != null && response.getData().getDocs() != null){

                        newsLetterList.clear();
                        newsLetterList = response.getData().getDocs();
                    }
                }

                getNewsManagerListener.onCompletion(response, error);
            }
        });
    }

    public void getAllDocs(HashMap object, final GetDocumentManagerListener getNewsManagerListener){
        DocumentService.getSharedInstance().getAllDocs(object, new DocumentService.GetNewsWebServiceListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {
                if (response != null){
                    if (response.getData() != null && response.getData().getDocs() != null){
                        docsList.clear();
                        docsList = response.getData().getDocs();
                    }
                }

                getNewsManagerListener.onCompletion(response, error);
            }
        });
    }

    public interface GetDocumentManagerListener{
        public void onCompletion(DocumentsResponse response, AppError error);
    }

}

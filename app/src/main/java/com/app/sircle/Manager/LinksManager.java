package com.app.sircle.Manager;

import com.app.sircle.UI.Model.Links;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.LinksResponseData;
import com.app.sircle.WebService.LinksWebService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 25/08/15.
 */
public class LinksManager {

    private static LinksManager sharedInstance;

    private LinksManager(){

    }

    public static LinksManager getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new LinksManager();
        }
        return sharedInstance;
    }

    public void getAllLinks(HashMap object, final LinksManagerListener linksManagerListener){
        LinksWebService.getSharedInstance().getAllLinks(object, new LinksWebService.GetAllLinksServiceListener() {
            @Override
            public void onCompletion(LinksResponseData linksResponseData, AppError error) {
                linksManagerListener.onCompletion(linksResponseData, error);
            }
        });
    }

    public interface LinksManagerListener{
        public void onCompletion(LinksResponseData linksResponseData, AppError error);
    }

    public interface AddLinksManagerListener{
        public void onCompletion(Links links, AppError error);
    }
}
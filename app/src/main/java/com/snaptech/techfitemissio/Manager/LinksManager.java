package com.snaptech.techfitemissio.Manager;

import com.snaptech.techfitemissio.UI.Model.Links;
import com.snaptech.techfitemissio.Utility.AppError;
import com.snaptech.techfitemissio.WebService.LinksResponse;
import com.snaptech.techfitemissio.WebService.LinksWebService;
import com.snaptech.techfitemissio.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 25/08/15.
 */
public class LinksManager {

    public static List<Links> linksList = new ArrayList<>();

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
            public void onCompletion(LinksResponse response, AppError error) {
                if (response != null){
                    if (response.getData() != null && response.getData().getLinks() != null){
                        linksList.clear();
                        linksList = response.getData().getLinks();
                    }
                }
                linksManagerListener.onCompletion(response, error);
            }
        });
    }

    public void addLinks(HashMap object, final AddLinksManagerListener addLinksManagerListener){
        LinksWebService.getSharedInstance().addLinks(object, new LinksWebService.LinkWebServiceListener() {
            @Override
            public void onCompletion(PostResponse response, AppError error) {
                addLinksManagerListener.onCompletion(response, error);
            }
        });
    }

    public interface LinksManagerListener{
        public void onCompletion(LinksResponse response, AppError error);
    }

    public interface AddLinksManagerListener{
        public void onCompletion(PostResponse response, AppError error);
    }
}

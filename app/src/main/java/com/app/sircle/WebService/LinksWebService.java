package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Links;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.Common.RetrofitImplementation;
import com.app.sircle.WebService.Common.WebServiceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 25/08/15.
 */
public class LinksWebService {

    //public static List<Links> linksList = new ArrayList<Links>();
    private static LinksWebService sharedInstance;
    private static RetrofitImplementation retrofitImplementation;

    private LinksWebService(){

    }

    public static LinksWebService getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new LinksWebService();
            retrofitImplementation = new RetrofitImplementation();
        }
        return sharedInstance;
    }

    public void getAllLinks(HashMap object, final GetAllLinksServiceListener getAllLinksServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.LINKS_GET_ALL_API, object, null, LinksResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
              // LinksResponseData linksResponseData = ().getData();
                getAllLinksServiceListener.onCompletion((LinksResponse)responseObject, error);
            }
        });
    }

    public void addLinks(HashMap object, final LinkWebServiceListener linkWebServiceListener){
        retrofitImplementation.executePostWithURL(Constants.ADD_NEW_LINK_API_PATH, object, null, PostResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                linkWebServiceListener.onCompletion((PostResponse)responseObject, error);
            }
        });
    }

    public interface LinkWebServiceListener{
        public void onCompletion(PostResponse response, AppError error);
    }

    public interface GetAllLinksServiceListener{

        public void onCompletion(LinksResponse response, AppError error);
    }

}

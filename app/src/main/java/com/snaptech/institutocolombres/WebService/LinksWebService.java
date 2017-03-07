package com.snaptech.institutocolombres.WebService;

import com.snaptech.institutocolombres.Utility.AppError;
import com.snaptech.institutocolombres.Utility.Constants;
import com.snaptech.institutocolombres.WebService.Common.RetrofitImplementation;
import com.snaptech.institutocolombres.WebService.Common.WebServiceListener;

import java.util.HashMap;

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
        retrofitImplementation.executePostWithURL(Constants.LINKS_GET_ALL_API, object, null, LinksResponse.class, new WebServiceListener() {
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

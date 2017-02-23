package com.snaptech.montessorideirapuato.WebService;

import com.snaptech.montessorideirapuato.Utility.AppError;
import com.snaptech.montessorideirapuato.Utility.Constants;
import com.snaptech.montessorideirapuato.WebService.Common.RetrofitImplementation;
import com.snaptech.montessorideirapuato.WebService.Common.WebServiceListener;

import java.util.HashMap;

/**
 * Created by soniya on 25/08/15.
 */
public class VideoWebService {

    private static VideoWebService sharedInstance;
    private static RetrofitImplementation retrofitImplementation;

    private VideoWebService(){

    }

    public static VideoWebService getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new VideoWebService();
            retrofitImplementation = new RetrofitImplementation();
        }
        return sharedInstance;
    }

    public void getAllVideos(HashMap object, final VideoWebServiceListener videoWebServiceListener){
        retrofitImplementation.executePostWithURL(Constants.VIDEOS_GET_ALL_API, object, null, VideoResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                //List<Video> videoList = (ArrayList<Video>) responseObject;
                videoWebServiceListener.onCompletion((VideoResponse)responseObject, error);
            }
        });
    }

    public interface VideoWebServiceListener{
        public void onCompletion(VideoResponse response, AppError error);
    }
}

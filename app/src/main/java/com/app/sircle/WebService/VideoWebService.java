package com.app.sircle.WebService;

import com.app.sircle.UI.Model.Video;
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
        retrofitImplementation.executeGetWithURL(Constants.VIDEOS_GET_ALL_API, object, null, Video.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                List<Video> videoList = (ArrayList<Video>) responseObject;
                videoWebServiceListener.onCompletion(videoList, error);
            }
        });
    }

    public interface VideoWebServiceListener{
        public void onCompletion(List<Video> videoList, AppError error);
    }
}

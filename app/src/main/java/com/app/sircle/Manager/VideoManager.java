package com.app.sircle.Manager;

import com.app.sircle.UI.Model.Video;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.VideoResponse;
import com.app.sircle.WebService.VideoWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 25/08/15.
 */
public class VideoManager {

    public static List<Video> videoList = new ArrayList<>();

    private static VideoManager sharedInstance;

    private VideoManager(){

    }

    public static VideoManager getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new VideoManager();
        }
        return sharedInstance;
    }

    public void getAllVideos(HashMap object, final VideoManagerListener videoManagerListener){
        VideoWebService.getSharedInstance().getAllVideos(object, new VideoWebService.VideoWebServiceListener() {
            @Override
            public void onCompletion(VideoResponse response, AppError error) {
                if (response != null){
                    if (response.getData() != null && response.getData().getVideos().size() > 0){
                        videoList.clear();
                        videoList = response.getData().getVideos();
                    }
                }
                videoManagerListener.onCompletion(response, error);
            }
        });
    }

    public interface VideoManagerListener{
        public void onCompletion(VideoResponse response, AppError error);
    }


}

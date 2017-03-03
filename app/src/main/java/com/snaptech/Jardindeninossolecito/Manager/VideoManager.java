package com.snaptech.Jardindeninossolecito.Manager;

import com.snaptech.Jardindeninossolecito.UI.Model.Video;
import com.snaptech.Jardindeninossolecito.Utility.AppError;
import com.snaptech.Jardindeninossolecito.WebService.VideoResponse;
import com.snaptech.Jardindeninossolecito.WebService.VideoWebService;

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
                    if (response.getData() != null && response.getData().getVideos() != null){
                     //   videoList.clear();
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

package com.app.sircle.Manager;

import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.PhotoWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 17/08/15.
 */
public class PhotoManager  {

    public List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();

    private static PhotoManager sharedInstance;
    private PhotoManager(){

    }

    public static PhotoManager getSharedInstance(){

        if (sharedInstance == null){
            sharedInstance =  new PhotoManager();
        }
        return sharedInstance;
    }

    public void addNewAlbum(HashMap requestObject, final PhotoManagerListener photoManagerListener){

        PhotoWebService.getSharedInstance().addAlbum(requestObject, new PhotoWebService.PhotoWebServiceListener() {
            @Override
            public void onCompletion(AppError error) {
                photoManagerListener.onCompletion(error);
            }
        });
    }

    public void getImages(HashMap params, final GetPhotosManagerListener getPhotosManagerListener){
        PhotoWebService.getSharedInstance().getPhotos(params, new PhotoWebService.GetPhotoWebServiceListener() {
            @Override
            public void onCompletion(List<AlbumDetails> albumDetailsList, AppError error) {
                getPhotosManagerListener.onCompletion(albumDetailsList, error);
            }
        });
    }

    public interface PhotoManagerListener{
        public void onCompletion(AppError error);
    }

    public interface GetPhotosManagerListener{
        public void onCompletion(List<AlbumDetails> albumDetailsList, AppError error);
    }


}

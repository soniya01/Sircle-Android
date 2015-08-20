package com.app.sircle.WebService;

import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.Photo;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.Common.RetrofitImplementation;
import com.app.sircle.WebService.Common.WebServiceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 17/08/15.
 */
public class PhotoWebService {

    public static List<Photo> photoList = new ArrayList<Photo>();
    public static List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private static PhotoWebService sharedInstance;
    private static RetrofitImplementation retrofitImplementation;

    private PhotoWebService(){

    }

    public static PhotoWebService getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new PhotoWebService();
            retrofitImplementation = new RetrofitImplementation();
        }
        return sharedInstance;
    }

    public void addAlbum(HashMap object, final PhotoWebServiceListener photoWebServiceListener){

        retrofitImplementation.executePostWithURL(Constants.PHOTOS_ADD_NEW_ALBUM_API_PATH, object, null, null, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                photoWebServiceListener.onCompletion(error);
            }
        });
    }

    public void getPhotos(HashMap object, final GetPhotoWebServiceListener getPhotoWebServiceListener){
       retrofitImplementation.executeGetWithURL(Constants.PHOTOS_GET_API_PATH, object, null, AlbumDetails.class, new WebServiceListener() {
           @Override
           public void onCompletion(Object response, AppError error) {
               albumDetailsList = (ArrayList<AlbumDetails>) response;
               if (error.getErrorCode() == AppError.NO_ERROR || error == null){
                   getPhotoWebServiceListener.onCompletion(albumDetailsList, new AppError());
               }else {
                   getPhotoWebServiceListener.onCompletion(albumDetailsList, error);
               }
           }
       });
    }


    public interface PhotoWebServiceListener{
        public void onCompletion(AppError error);
    }

    public interface GetPhotoWebServiceListener{
        public void onCompletion(List<AlbumDetails> albumDetailsList, AppError error);
    }

}

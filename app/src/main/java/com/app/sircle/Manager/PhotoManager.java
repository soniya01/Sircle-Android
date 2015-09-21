package com.app.sircle.Manager;

import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.Photo;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.AlbumResponse;
import com.app.sircle.WebService.PhotoResponse;
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

    public void addNewAlbum(HashMap requestObject, final AddPhotoManagerListener addPhotoManagerListener){

        PhotoWebService.getSharedInstance().addAlbum(requestObject, new PhotoWebService.AddPhotoWebServiceListener() {
            @Override
            public void onCompletion(Photo photo, AppError error) {
                addPhotoManagerListener.onCompletion(photo, error);
            }
        });
    }

    public void getImages(HashMap params, final GetPhotosManagerListener getPhotosManagerListener){
        PhotoWebService.getSharedInstance().getPhotos(params, new PhotoWebService.GetPhotoWebServiceListener() {
            @Override
            public void onCompletion(AlbumResponse response, AppError error) {
                getPhotosManagerListener.onCompletion(response, error);
            }
        });
    }

    public void getAlbums(HashMap params, final GetAlbumsManagerListener albumsManagerListener){
        PhotoWebService.getSharedInstance().getAlbums(params, new PhotoWebService.GetAlbumWebServiceListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {
                albumsManagerListener.onCompletion(response, new AppError());
            }
        });
    }

    public interface AddPhotoManagerListener{
        public void onCompletion(Photo photo, AppError error);
    }


    public interface PhotoManagerListener{
        public void onCompletion(AppError error);
    }

    public interface GetPhotosManagerListener{
        public void onCompletion(AlbumResponse response, AppError error);
    }

    public interface GetAlbumsManagerListener{
        public void onCompletion(PhotoResponse response, AppError error);
    }


}

package com.grayjam.sircle.Manager;

import com.grayjam.sircle.UI.Model.AlbumDetails;
import com.grayjam.sircle.UI.Model.Photo;
import com.grayjam.sircle.Utility.AppError;
import com.grayjam.sircle.WebService.AddAlbumResponse;
import com.grayjam.sircle.WebService.AlbumResponse;
import com.grayjam.sircle.WebService.PhotoResponse;
import com.grayjam.sircle.WebService.PhotoUploadResponse;
import com.grayjam.sircle.WebService.PhotoWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.mime.TypedFile;

/**
 * Created by soniya on 17/08/15.
 */
public class PhotoManager  {

    public static List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    public static List<Photo> albumsList = new ArrayList<Photo>();

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
            public void onCompletion(AddAlbumResponse response, AppError error) {
                addPhotoManagerListener.onCompletion(response, error);
            }
        });
    }

    public void getImages(HashMap params, final GetPhotosManagerListener getPhotosManagerListener){
        PhotoWebService.getSharedInstance().getPhotos(params, new PhotoWebService.GetPhotoWebServiceListener() {
            @Override
            public void onCompletion(AlbumResponse response, AppError error) {
                if (response != null){
                    if (response.getData() != null && response.getData().getAlbum_images() != null){
                        albumDetailsList = response.getData().getAlbum_images();
                    }

                }

                getPhotosManagerListener.onCompletion(response, error);
            }
        });
    }

    public void getAlbums(HashMap params, final GetAlbumsManagerListener albumsManagerListener){
        PhotoWebService.getSharedInstance().getAlbums(params, new PhotoWebService.GetAlbumWebServiceListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {
                if (response != null){
                    if (response.getData() != null && response.getData().getAlbums() != null){
                        //System.out.println("Album List Clear "+ albumsList.size());
                       // albumsList.clear();
                        albumsList = response.getData().getAlbums();
                        System.out.println("Response recieved list count in PM is "+albumsList.size());
                    }
                }
                albumsManagerListener.onCompletion(response, new AppError());
            }
        });
    }

    public void uploadImage(HashMap params, TypedFile image, final PhotoManagerListener photoManagerListener){
        PhotoWebService.getSharedInstance().uploadPhoto(params, image, new PhotoWebService.PhotoWebServiceListener() {
            @Override
            public void onCompletion(PhotoUploadResponse response, AppError error) {
                photoManagerListener.onCompletion(response, error);
            }
        });
    }

    public interface AddPhotoManagerListener{
        public void onCompletion(AddAlbumResponse addAlbumResponse, AppError error);
    }


    public interface PhotoManagerListener{
        public void onCompletion(PhotoUploadResponse response, AppError error);
    }

    public interface GetPhotosManagerListener{
        public void onCompletion(AlbumResponse response, AppError error);
    }

    public interface GetAlbumsManagerListener{
        public void onCompletion(PhotoResponse response, AppError error);
    }


}

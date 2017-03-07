package com.snaptech.institutocolombres.WebService;

import com.snaptech.institutocolombres.UI.Model.AlbumDetails;
import com.snaptech.institutocolombres.UI.Model.Photo;
import com.snaptech.institutocolombres.Utility.AppError;
import com.snaptech.institutocolombres.Utility.Constants;
import com.snaptech.institutocolombres.WebService.Common.RetrofitImplementation;
import com.snaptech.institutocolombres.WebService.Common.WebServiceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.mime.TypedFile;

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

    public void addAlbum(HashMap object, final AddPhotoWebServiceListener addPhotoWebServiceListener){

        retrofitImplementation.executePostWithURL(Constants.PHOTOS_ADD_NEW_ALBUM_API_PATH, object, null, AddAlbumResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {

                addPhotoWebServiceListener.onCompletion((AddAlbumResponse)responseObject, error);
            }
        });
    }

    public void getPhotos(HashMap object, final GetPhotoWebServiceListener getPhotoWebServiceListener){
       retrofitImplementation.executePostWithURL(Constants.PHOTOS_GET_API_PATH, object, null, AlbumResponse.class, new WebServiceListener() {
           @Override
           public void onCompletion(Object response, AppError error) {
               // albumDetailsList = (ArrayList<AlbumDetails>) response;
               if (error.getErrorCode() == AppError.NO_ERROR || error == null) {
                   getPhotoWebServiceListener.onCompletion((AlbumResponse) response, new AppError());
               } else {
                   getPhotoWebServiceListener.onCompletion((AlbumResponse) response, error);
               }
           }
       });
    }

    public void getAlbums(HashMap object, final GetAlbumWebServiceListener getAlbumWebServiceListener){
        retrofitImplementation.executePostWithURL(Constants.PHOTOS_GET_ALBUM_API_PATH, object, null, PhotoResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
//                if (error.getErrorCode() == AppError.NO_ERROR || error == null) {
//                    System.out.println("Some Error ");
//                    getAlbumWebServiceListener.onCompletion((PhotoResponse)responseObject, new AppError());
//                } else {
                    System.out.println("Else No Error");
                    getAlbumWebServiceListener.onCompletion((PhotoResponse)responseObject, error);
               // }
            }
        });
    }

    public void uploadPhoto(HashMap params, TypedFile image, final PhotoWebServiceListener photoWebServiceListener){
//        retrofitImplementation.executePostWithURL(Constants.PHOTOS_ADD_PHOTO_TO_ALBUM_API_PATH, params, null, PhotoUploadResponse.class, new WebServiceListener() {
//            @Override
//            public void onCompletion(Object responseObject, AppError error) {
//                if (error.getErrorCode() == AppError.NO_ERROR || error == null) {
//                    photoWebServiceListener.onCompletion((PhotoUploadResponse)responseObject, new AppError());
//                } else {
//                    photoWebServiceListener.onCompletion((PhotoUploadResponse)responseObject, error);
//                }
//            }
//        });

        retrofitImplementation.executeUploadImageWithURL(Constants.PHOTOS_ADD_PHOTO_TO_ALBUM_API_PATH, params, image, PhotoUploadResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR || error == null) {
                    photoWebServiceListener.onCompletion((PhotoUploadResponse)responseObject, new AppError());
                } else {
                    photoWebServiceListener.onCompletion((PhotoUploadResponse)responseObject, error);
                }
            }
        });
    }

    public interface AddPhotoWebServiceListener{
        public void onCompletion(AddAlbumResponse addAlbumResponse, AppError error);
    }

    public interface PhotoWebServiceListener{
        public void onCompletion(PhotoUploadResponse response, AppError error);
    }

    public interface GetAlbumWebServiceListener{
        public void onCompletion(PhotoResponse photoResponse, AppError error);
    }

    public interface GetPhotoWebServiceListener{
        public void onCompletion(AlbumResponse albumResponse, AppError error);
    }

}

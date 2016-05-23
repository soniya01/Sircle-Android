package com.app.sircle.DownLoader;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.app.sircle.Manager.DocumentManager;
import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.LinksManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.Manager.VideoManager;
import com.app.sircle.UI.Activity.LoginScreen;
import com.app.sircle.UI.Model.Event;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.DocumentsResponse;
import com.app.sircle.WebService.EventDataReponse;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.LinksResponse;
import com.app.sircle.WebService.NotificationResponse;
import com.app.sircle.WebService.PhotoResponse;
import com.app.sircle.WebService.VideoResponse;

import java.util.HashMap;
import java.util.List;

import retrofit.mime.FormUrlEncodedTypedOutput;

/**
 * Created by soniya on 10/10/15.
 */
public class FetchAppData extends AsyncTask<FetchAppData.FetchedDataDelegate, Void, Void> {

private FetchedDataDelegate fetchedDataDelegate;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(FetchedDataDelegate... params) {
        fetchedDataDelegate = params[0];

        PhotoManager.getSharedInstance().albumDetailsList.clear();
        PhotoManager.getSharedInstance().albumsList.clear();
        VideoManager.getSharedInstance().videoList.clear();
        //NotificationManager.groupList.clear();
        NotificationManager.notificationList.clear();
        LinksManager.linksList.clear();
        DocumentManager.docsList.clear();
        DocumentManager.newsLetterList.clear();
        EventManager.getSharedInstance().termsList.clear();
        EventManager.getSharedInstance().eventList.clear();
        EventManager.eventDetail = new Event();

        String grpIdString = "";
        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
            if (i == 0){
                grpIdString = NotificationManager.grpIds.get(i);
            }else {
                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
            }
        }
//        HashMap object = new HashMap();
//        object.put("regId", Constants.GCM_REG_ID);
//
//        NotificationManager.getSharedInstance().getAllGroups(object, new NotificationManager.GroupsManagerListener() {
//            @Override
//            public void onCompletion(GroupResponse groupResponse, AppError error) {
//
//            }
//        });

        HashMap request = new HashMap();
        request.put("regId", Constants.GCM_REG_ID);
        request.put("groupId",grpIdString);
        request.put("page", 1);

        EventManager.getSharedInstance().getAllEvents(request, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {

            }
        });

        EventManager.getSharedInstance().getAllTerms(null, new EventManager.GetAllTermsManagerListener() {
            @Override
            public void onCompletion(List<Terms> termsList, AppError error) {

            }
        });

        DocumentManager.getSharedInstance().getAllDocs(request, new DocumentManager.GetDocumentManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {

            }
        });

        DocumentManager.getSharedInstance().getAllNewsLetters(request, new DocumentManager.GetDocumentManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {

            }
        });

        LinksManager.getSharedInstance().getAllLinks(request, new LinksManager.LinksManagerListener() {
            @Override
            public void onCompletion(LinksResponse response, AppError error) {

            }
        });

        PhotoManager.getSharedInstance().getAlbums(request, new PhotoManager.GetAlbumsManagerListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {

            }
        });

        VideoManager.getSharedInstance().getAllVideos(request, new VideoManager.VideoManagerListener() {
            @Override
            public void onCompletion(VideoResponse response, AppError error) {

            }
        });


        NotificationManager.getSharedInstance().getAllNotifications(request, new NotificationManager.NotificationManagerListener() {
            @Override
            public void onCompletion(NotificationResponse response, AppError error) {

            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        fetchedDataDelegate.fetchDataDone();
    }

    public static abstract class FetchedDataDelegate {
        public abstract void fetchDataDone();
    }
}

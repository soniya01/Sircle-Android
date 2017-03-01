package com.snaptech.colegiobosques.DownLoader;

import android.os.AsyncTask;

import com.snaptech.colegiobosques.Manager.DocumentManager;
import com.snaptech.colegiobosques.Manager.EventManager;
import com.snaptech.colegiobosques.Manager.LinksManager;
import com.snaptech.colegiobosques.Manager.NotificationManager;
import com.snaptech.colegiobosques.Manager.PhotoManager;
import com.snaptech.colegiobosques.Manager.VideoManager;
import com.snaptech.colegiobosques.UI.Model.Event;
import com.snaptech.colegiobosques.UI.Model.Terms;
import com.snaptech.colegiobosques.Utility.AppError;
import com.snaptech.colegiobosques.Utility.Constants;
import com.snaptech.colegiobosques.WebService.DocumentsResponse;
import com.snaptech.colegiobosques.WebService.EventDataReponse;
import com.snaptech.colegiobosques.WebService.LinksResponse;
import com.snaptech.colegiobosques.WebService.NotificationResponse;
import com.snaptech.colegiobosques.WebService.PhotoResponse;
import com.snaptech.colegiobosques.WebService.VideoResponse;

import java.util.HashMap;
import java.util.List;

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
        request.put("page", "1");

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

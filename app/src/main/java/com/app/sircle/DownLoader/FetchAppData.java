package com.app.sircle.DownLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.app.sircle.Manager.DocumentManager;
import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.LinksManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.Manager.VideoManager;
import com.app.sircle.UI.Activity.BaseActivity;
import com.app.sircle.UI.Fragment.HomeFragment;
import com.app.sircle.UI.Model.Event;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.DocumentsResponse;
import com.app.sircle.WebService.EventDataReponse;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.LinksResponse;
import com.app.sircle.WebService.NotificationResponse;
import com.app.sircle.WebService.PhotoResponse;
import com.app.sircle.WebService.VideoResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 10/10/15.
 */
public class FetchAppData extends AsyncTask<FetchAppData.FetchedDataDelegate, Void, Integer> {

    private FetchedDataDelegate fetchedDataDelegate;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    public Activity mActivity;
    public  int count = 0;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this.ringProgressDialog = ProgressDialog.show(HomeFragment.mContext, "", "", true);
    }

    @Override
    protected Integer doInBackground(FetchedDataDelegate... params) {

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
        HashMap object = new HashMap();
        object.put("regId", "id");

        NotificationManager.getSharedInstance().getAllGroups(object, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse groupResponse, AppError error) {
                count++;
            }
        });


        object.put("groupId",grpIdString);
        object.put("page", 1);

        EventManager.getSharedInstance().getAllEvents(object, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
                count++;
            }
        });

        EventManager.getSharedInstance().getAllTerms(null, new EventManager.GetAllTermsManagerListener() {
            @Override
            public void onCompletion(List<Terms> termsList, AppError error) {
                count++;
            }
        });

        DocumentManager.getSharedInstance().getAllDocs(object, new DocumentManager.GetNewsManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {
                count++;
            }
        });

        DocumentManager.getSharedInstance().getAllNewsLetters(object, new DocumentManager.GetNewsManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {
                count++;
            }
        });

        LinksManager.getSharedInstance().getAllLinks(object, new LinksManager.LinksManagerListener() {
            @Override
            public void onCompletion(LinksResponse response, AppError error) {
                count++;
            }
        });

        PhotoManager.getSharedInstance().getAlbums(object, new PhotoManager.GetAlbumsManagerListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {
                count++;
            }
        });

        VideoManager.getSharedInstance().getAllVideos(object, new VideoManager.VideoManagerListener() {
            @Override
            public void onCompletion(VideoResponse response, AppError error) {
                count++;
            }
        });


        NotificationManager.getSharedInstance().getAllNotifications(object, new NotificationManager.NotificationManagerListener() {
            @Override
            public void onCompletion(NotificationResponse response, AppError error) {
                count++;
            }
        });

        if (count == 9){
            return 1;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer param) {

        if (param == 1){
            super.onPostExecute(param);
            if (this.progressDialog != null){
                this.progressDialog.dismiss();
            }

            fetchedDataDelegate.fetchDataDone();
        }

    }

    public interface FetchedDataDelegate {
        public void fetchDataDone();
    }

    public void setRingProgressDialog(ProgressDialog bar){
        this.progressDialog = bar;
        this.progressDialog.show();
    }

    public ProgressDialog getProgressDialog(){
        return this.progressDialog;
    }
}

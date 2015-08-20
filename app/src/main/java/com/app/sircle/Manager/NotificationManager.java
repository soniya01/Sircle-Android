package com.app.sircle.Manager;

import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.Notificationservice;

import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 18/08/15.
 */
public class NotificationManager {

    private static NotificationManager sharedInstance;

    private NotificationManager(){

    }

    public static NotificationManager getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new NotificationManager();
        }
        return sharedInstance;
    }

    public void getAllNotifications(HashMap object, NotificationManagerListener notificationManagerListener){

    }

    public void getAllGroups(final GroupsManagerListener groupsManagerListener){
        Notificationservice.getSharedInstance().getAllGroups(new Notificationservice.GroupsServiceListener() {
            @Override
            public void onCompletion(List<NotificationGroups> notificationGroupsList, AppError error) {
                if (error == null || error.getErrorCode() == AppError.NO_ERROR){
                    groupsManagerListener.onCompletion(notificationGroupsList, new AppError());
                }
                groupsManagerListener.onCompletion(notificationGroupsList, error);
            }
        });

    }


    public interface GroupsManagerListener{
        public void onCompletion(List<NotificationGroups> notificationGroupsList, AppError error);
    }

    public interface NotificationManagerListener{
        public void onCompletion(AppError error);
    }
}

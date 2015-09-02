package com.app.sircle.Manager;

import com.app.sircle.UI.Model.Notification;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.GroupResponse;
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

    public void getAllNotifications(HashMap object, final NotificationManagerListener notificationManagerListener){
            Notificationservice.getSharedInstance().getAllNotifications(object, new Notificationservice.NotificationServiceListener() {
                @Override
                public void onCompletion(List<Notification> notificationList, AppError error) {
                    notificationManagerListener.onCompletion(notificationList, error);
                }
            });
    }

    public void getAllGroups(final GroupsManagerListener groupsManagerListener){
        Notificationservice.getSharedInstance().getAllGroups(new Notificationservice.GroupsServiceListener() {
            @Override
            public void onCompletion(GroupResponse groupResponse, AppError error) {
                if (error == null || error.getErrorCode() == AppError.NO_ERROR){
                    groupsManagerListener.onCompletion(groupResponse, new AppError());
                }
                groupsManagerListener.onCompletion(groupResponse, error);
            }
        });

    }


    public interface GroupsManagerListener{
        public void onCompletion(GroupResponse groupResponse, AppError error);
    }

    public interface NotificationManagerListener{
        public void onCompletion(List<Notification> notifications, AppError error);
    }
}

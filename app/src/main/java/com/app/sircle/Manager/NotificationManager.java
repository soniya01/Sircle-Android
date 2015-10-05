package com.app.sircle.Manager;

import com.app.sircle.UI.Model.Notification;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.NotificationCountResponse;
import com.app.sircle.WebService.NotificationResponse;
import com.app.sircle.WebService.Notificationservice;
import com.app.sircle.WebService.PostResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 18/08/15.
 */
public class NotificationManager {
    public static String[] grpIds;

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
                public void onCompletion(NotificationResponse response, AppError error) {
                    notificationManagerListener.onCompletion(response, error);
                }
            });
    }

    public void getAllGroups(HashMap map, final GroupsManagerListener groupsManagerListener){
        Notificationservice.getSharedInstance().getAllGroups(map, new Notificationservice.GroupsServiceListener() {
            @Override
            public void onCompletion(GroupResponse groupResponse, AppError error) {
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    groupsManagerListener.onCompletion(groupResponse, new AppError());
                }
                groupsManagerListener.onCompletion(groupResponse, error);
            }
        });

    }

    public void getNotificationCount(HashMap map, final NotificationCountManagerListener notificationCountManagerListener){
        Notificationservice.getSharedInstance().getNotificationCount(map, new Notificationservice.NotificationCountServiceListener() {
            @Override
            public void onCompletion(NotificationCountResponse response, AppError error) {
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    notificationCountManagerListener.onCompletion(response, new AppError());
                }
                notificationCountManagerListener.onCompletion(response, error);
            }
        });
    }

    public void updateGroupNotification(HashMap map, final PostManagerListener postManagerListener){
       Notificationservice.getSharedInstance().updateGroupNot(map, new Notificationservice.PostServiceListener() {
           @Override
           public void onCompletion(PostResponse postResponse, AppError error) {
               if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                   postManagerListener.onCompletion(postResponse, new AppError());
               }else {
                   postManagerListener.onCompletion(postResponse, error);
               }

           }
       });
    }

    public void addNotification(HashMap params, final PostManagerListener postManagerListener){
        Notificationservice.getSharedInstance().addNotification(params, new Notificationservice.PostServiceListener() {
            @Override
            public void onCompletion(PostResponse postResponse, AppError error) {
                postManagerListener.onCompletion(postResponse, error);
            }
        });
    }

    public interface PostManagerListener{
        public void onCompletion(PostResponse postResponse, AppError error);
    }

    public interface GroupsManagerListener{
        public void onCompletion(GroupResponse groupResponse, AppError error);
    }

    public interface NotificationManagerListener{
        public void onCompletion(NotificationResponse response, AppError error);
    }

    public interface NotificationCountManagerListener{
        public void onCompletion(NotificationCountResponse response, AppError error);
    }
}

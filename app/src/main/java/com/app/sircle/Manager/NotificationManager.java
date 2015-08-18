package com.app.sircle.Manager;

import com.app.sircle.Utility.AppError;

import java.util.HashMap;

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

    public interface NotificationManagerListener{
        public void onCompletion(AppError error);
    }
}

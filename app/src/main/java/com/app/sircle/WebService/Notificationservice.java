package com.app.sircle.WebService;

import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.Notification;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.Common.RetrofitImplementation;
import com.app.sircle.WebService.Common.WebServiceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 20/08/15.
 */
public class Notificationservice {
    public static List<NotificationGroups> notificationGroupsList = new ArrayList<NotificationGroups>();
    private static Notificationservice sharedInstance;
    private static RetrofitImplementation retrofitImplementation;

    private Notificationservice(){

    }

    public static Notificationservice getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new Notificationservice();
            retrofitImplementation = new RetrofitImplementation();
        }
        return sharedInstance;
    }

    public void getAllGroups(final GroupsServiceListener groupsServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.NOTIFICATION_GET_ALL_GROUPS, null, null, GroupResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                if (response != null){
                    notificationGroupsList = (ArrayList<NotificationGroups>) ((GroupResponse)response).getMessage();
                    if (error.getErrorCode() == AppError.NO_ERROR || error == null){
                        groupsServiceListener.onCompletion(notificationGroupsList, new AppError());
                    }
                    groupsServiceListener.onCompletion(notificationGroupsList, error);
                }else {
                    groupsServiceListener.onCompletion(null, error);
                }

            }
        });
    }

    public void getAllNotifications(HashMap object, final NotificationServiceListener notificationServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.NOTIFICATION_GET_API, null, object, NotificationResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                List<Notification> notifications = ((NotificationResponse) responseObject).message;
                notificationServiceListener.onCompletion(notifications, error);
            }
        });
    }

    public interface NotificationServiceListener{
        public void onCompletion(List<Notification> notificationList, AppError error);
    }

    public interface GroupsServiceListener{
        public void onCompletion(List<NotificationGroups> notificationGroupsList, AppError error);
    }


}

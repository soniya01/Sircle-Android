package com.grayjam.quadeia.Manager;

import com.grayjam.quadeia.UI.Model.Notification;
import com.grayjam.quadeia.UI.Model.NotificationGroups;
import com.grayjam.quadeia.Utility.AppError;
import com.grayjam.quadeia.WebService.GroupResponse;
import com.grayjam.quadeia.WebService.NotificationCountResponse;
import com.grayjam.quadeia.WebService.NotificationResponse;
import com.grayjam.quadeia.WebService.Notificationservice;
import com.grayjam.quadeia.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 18/08/15.
 */
public class NotificationManager {
    public static List<String> grpIds = new ArrayList<>();
    public static List<NotificationGroups> groupList = new ArrayList<>();
    public static List<Notification> notificationList = new ArrayList<>();

    private static NotificationManager sharedInstance;

    private NotificationManager(){

    }

    public static NotificationManager getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new NotificationManager();
        }
        return sharedInstance;
    }

//    public void saveGroupIds (String groupIds,Context context)
//    {
//        SharedPreferences sharedpreferences =context.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(Constants.GROUPIDS_SAVED_PREFS_KEY, groupIds);
//        editor.commit();
//    }

//    public String getGroupIds(Context context)
//    {
//        SharedPreferences sharedpreferences =context.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
//        String groupIds = sharedpreferences.getString(Constants.GROUPIDS_SAVED_PREFS_KEY, null);
//        return groupIds;
//    }

    public void getAllNotifications(HashMap object, final NotificationManagerListener notificationManagerListener){
            Notificationservice.getSharedInstance().getAllNotifications(object, new Notificationservice.NotificationServiceListener() {
                @Override
                public void onCompletion(NotificationResponse response, AppError error) {
                    if (response != null) {
                        if (response.getData() != null ) {
                            notificationList = response.getData().getNotifications();
                        }
                    }
                    notificationManagerListener.onCompletion(response, error);
                }
            });
    }

    public void getAllGroups(HashMap map, final GroupsManagerListener groupsManagerListener){
        Notificationservice.getSharedInstance().getAllGroups(map, new Notificationservice.GroupsServiceListener() {
            @Override
            public void onCompletion(GroupResponse groupResponse, AppError error) {
                if (groupResponse != null){
                    if (groupResponse.getData() != null){
                        groupList.clear();
                        groupList = groupResponse.getData().getGroups();
                    }

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

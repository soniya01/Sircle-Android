package com.snaptech.msb.WebService;

import com.snaptech.msb.Utility.AppError;
import com.snaptech.msb.Utility.Constants;
import com.snaptech.msb.WebService.Common.RetrofitImplementation;
import com.snaptech.msb.WebService.Common.WebServiceListener;

import java.util.HashMap;

/**
 * Created by soniya on 20/08/15.
 */
public class Notificationservice {
    //public static List<NotificationGroups> notificationGroupsList = new ArrayList<NotificationGroups>();
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

    public void getAllGroups(HashMap map,final GroupsServiceListener groupsServiceListener){

        retrofitImplementation.executeGetWithURL(Constants.NOTIFICATION_GET_ALL_GROUPS, map, null, GroupResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                if (response != null){
                    // notificationGroupsList = (ArrayList<NotificationGroups>) ((GroupResponse)response).getMessage();
                    if (error.getErrorCode() == AppError.NO_ERROR || error == null){
                        groupsServiceListener.onCompletion((GroupResponse)response, new AppError());
                    }
                    groupsServiceListener.onCompletion((GroupResponse)response, error);
                }else {
                    groupsServiceListener.onCompletion(null, error);
                }
            }
        });

    }

    public void getAllNotifications(HashMap object, final NotificationServiceListener notificationServiceListener){
        retrofitImplementation.executePostWithURL(Constants.NOTIFICATION_GET_API, object, null, NotificationResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                notificationServiceListener.onCompletion((NotificationResponse) response, error);
            }
        });
    }

    public void getNotificationCount(HashMap object, final  NotificationCountServiceListener notificationCountServiceListener){
        retrofitImplementation.executePostWithURL(Constants.NOTIFICATION_COUNT, object, null, NotificationCountResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                if (response != null) {
                    // notificationGroupsList = (ArrayList<NotificationGroups>) ((GroupResponse)response).getMessage();
                    notificationCountServiceListener.onCompletion((NotificationCountResponse) response, new AppError());
                }
            }
        });
    }

    public void updateGroupNot(HashMap object, final  PostServiceListener postServiceListener){
        retrofitImplementation.executePostWithURL(Constants.GROUP_UPDATE_NOTIFICATION, object, null, PostResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                if (response != null) {
                    if (error.getErrorCode() == AppError.NO_ERROR || error == null) {
                        postServiceListener.onCompletion((PostResponse) response, new AppError());
                    }
                } else {
                    postServiceListener.onCompletion(null, error);}
            }
        });
    }


    public void addNotification(HashMap params, final PostServiceListener postServiceListener){
        retrofitImplementation.executePostWithURL(Constants.NOTIFICATION_ADD_GROUPS, params, params, PostResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
               postServiceListener.onCompletion((PostResponse)responseObject, error);
            }
        });
    }

    public interface NotificationServiceListener{
        public void onCompletion(NotificationResponse response, AppError error);
    }

    public interface NotificationCountServiceListener{
        public void onCompletion(NotificationCountResponse response, AppError error);
    }

    public interface GroupsServiceListener{
        public void onCompletion(GroupResponse groupResponse, AppError error);
    }

    public interface PostServiceListener{
        public void onCompletion(PostResponse postResponse, AppError error);
    }


}

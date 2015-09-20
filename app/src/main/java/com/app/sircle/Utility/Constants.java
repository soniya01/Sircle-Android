package com.app.sircle.Utility;


public class Constants {

    public static final String LOGIN_PREFS_NAME = "LOGIN_PREFS";
    public static final String LOGIN_USERNAME_PREFS_KEY = "username";
    public static final String LOGIN_PASSWORD_PREFS_KEY = "password";
    public static final String NO_NET_CONNECTIVITY_MESSAGE = "Sorry! There was trouble playing this video. PLease check your net connection";

//    Video fragment
    public static final String YOUTUBE_VIDEO_BASE_IMAGE_URL = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_VIDEO_BASE_IMAGE_FILE_URL = "/0.jpg";

    public static final String GCM_REG_ID = "id";
    public static final String BASE_URL = "http://52.3.172.189/testapi/";
    //public static final String BASE_URL = "http://demo.snaptech.in/";
    public static final String LOGIN_API_PATH = "user/user_get/";
    
    public static final String GROUP_UPDATE_ALL_NOTIFICATION = "user/device_group_update_all";
    public static final String GROUP_UPDATE_NOTIFICATION = "user/device_group_update";
    public static final String EVENTS_GET_ALL_TERMS_API_PATH = "user/get_all_terms";//"rest_api/terms/format/json";
    public static final String EVENTS_GET_MONTH_WISE_API_PATH = "user/group_and_month_wise_event";//"/user/group_and_month_wise_event/format/json";
    public static final String EVENTS_GET_ALL_EVENTS_API_PATH ="user/user_allevents";
    public static final String EVENTS_GET_CATEGORY_API_PATH = "user/event_categories";//"rest_api/event_categories/format/json";
    public static final String EVENTS_GET_DETAILS_API_PATH = "/rest_api/event_detail/{Eventid}/format/json";
    public static final String EVENTS_DELETE_API_PATH = "/user/delete_event/";
    public static final String EVENTS_ADD_NEW_EVENT_API_PATH = "user/new_event";
    public static final String NOTIFICATION_GET_ALL_GROUPS = "user/user_groups_new";//"rest_api/groups/format/json";
    public static final String NOTIFICATION_ADD_GROUPS = "user/add_notification";

    public static final String PHOTOS_ADD_NEW_ALBUM_API_PATH ="user/add_album";
    public static final String PHOTOS_GET_API_PATH ="rest_api/photos/{id1}/format/json";
    public static final String PHOTOS_ADD_PHOTO_TO_ALBUM_API_PATH ="user/uploadImage";
    public static final String PHOTOS_ADD_NEW_LINK_API_PATH ="user/add_links";//"user/add_links/format/json";
    public static final String PHOTOS_GET_ALBUM_API_PATH = "user/photos";//"user/album_group/format/json";

    public static final String NEWSLETTERS_GET_API_PATH = "user/newsletter_group";//"user/newsletter_group/format/json";
    public static final String DOCUMENTS_GET_API_PATH = "user/docs_group";//"user/docs_group/format/json";
    public static final String NOTIFICATION_GET_API  = "user/notifications_group";//"user/notifications_group/format/json";
    public static final String LINKS_GET_ALL_API  = "user/links_group";//"user/links_group/format/json";
    public static final String VIDEOS_GET_ALL_API  = "user/videos_group";//"user/videos_group/format/json";

}


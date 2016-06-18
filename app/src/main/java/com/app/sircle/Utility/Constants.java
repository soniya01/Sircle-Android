package com.app.sircle.Utility;


import android.graphics.Bitmap;
import android.os.Environment;

public class Constants {

   public static Bitmap myBitmap;

    public static final String LOGIN_PREFS_NAME = "LOGIN_PREFS";
    public static final String LOGIN_ACCESS_TOKEN_PREFS_KEY = "accessToken";
    public static final String LOGIN_EXPIRES_IN_PREFS_KEY = "expiresIn";
    public static final String LOGIN_LOGGED_IN_PREFS_KEY = "loggedIn";
    public static final String LOGIN_LOGGED_IN_USER_TYPE = "userType";
    public static final String GROUPIDS_SAVED_PREFS_KEY = "savedGroupIDs";
    public static final String NO_NET_CONNECTIVITY_MESSAGE = "Sorry! There was trouble playing this video. PLease check your net connection";

    // photos
    public static final String PHOTO_SAVE_GALLERY_DIR_IMAGE_PATH = Environment.getExternalStorageDirectory() + "/SircleApp/images/";


//    Video fragment
    public static final String YOUTUBE_VIDEO_BASE_IMAGE_URL = "http://img.youtube.com/vi/";
    public static final String YOUTUBE_VIDEO_BASE_IMAGE_FILE_URL = "/0.jpg";

    public static String GCM_REG_ID = "id";
    //public static int isAllChecked = -1;
    public static String dateAvailabe = "";
    public static String term ="";
    public static String termPeriod = "";
    //public static final String BASE_URL = "http://52.3.172.189/testapi/";


    //public static final String BASE_URL = "http://54.251.157.35/event/api/";

    public static final String BASE_URL = "http://msbmumbai.emissioapp.com/api/";

    //public static final String BASE_URL = "http://demo.snaptech.in/";
   // public static final String LOGIN_API_PATH = "user/user_get/";
    public static final String LOGIN_API_PATH = "login";
    public static final String FORGOTPASSWORD_API_PATH = "login/forgot_password";
    public static final String REGISTER_API_PATH = "login/register";

    public static final String LOGOUT_API_PATH = "login/logout";

    
    public static final String GROUP_UPDATE_ALL_NOTIFICATION = "user/device_group_update_all";
    public static final String GROUP_UPDATE_NOTIFICATION = "login/setting";
    public static final String EVENTS_GET_ALL_TERMS_API_PATH = "term";//"rest_api/terms/format/json";
  //  public static final String EVENTS_GET_MONTH_WISE_API_PATH = "user/group_and_month_wise_event";
    public static final String EVENTS_GET_MONTH_WISE_API_PATH = "event";
    //"/user/group_and_month_wise_event/format/json";
    public static final String EVENTS_GET_ALL_EVENTS_API_PATH ="user/user_allevents";
    public static final String EVENTS_GET_CATEGORY_API_PATH = "category";
    public static final String EVENTS_GET_DETAILS_API_PATH ="event/eventDetails";
    //"rest_api/event_categories/format/json";
   // public static final String EVENTS_GET_DETAILS_API_PATH ="user/event_detail" ;//"/rest_api/event_detail/{Eventid}/format/json";
    public static final String EVENTS_DELETE_API_PATH = "event/delete";

    public static final String EVENTS_ADD_NEW_EVENT_API_PATH = "event/add";
   // public static final String EVENTS_ADD_NEW_EVENT_API_PATH = "user/new_event";
    //public static final String NOTIFICATION_GET_ALL_GROUPS = "user/user_groups_new";
    public static final String NOTIFICATION_GET_ALL_GROUPS = "login/setting";//"rest_api/groups/format/json";
    public static final String NOTIFICATION_ADD_GROUPS = "notification/add";
   // public static final String NOTIFICATION_ADD_GROUPS = "user/add_notification";
    public static final String NOTIFICATION_COUNT = "user/get_user_notification_count";

    //public static final String PHOTOS_ADD_NEW_ALBUM_API_PATH ="user/add_album";
    public static final String PHOTOS_ADD_NEW_ALBUM_API_PATH ="file/addAlbum";
    //public static final String PHOTOS_GET_API_PATH ="user/photos";//"rest_api/photos/{id1}/format/json";
    public static final String PHOTOS_GET_API_PATH ="file/getAlbumImages";
    //public static final String PHOTOS_ADD_PHOTO_TO_ALBUM_API_PATH ="user/uploadImage";
    public static final String PHOTOS_ADD_PHOTO_TO_ALBUM_API_PATH ="file/addImage";
   // public static final String ADD_NEW_LINK_API_PATH ="user/add_links";
    public static final String ADD_NEW_LINK_API_PATH ="link/add";//"user/add_links/format/json";
  //  public static final String PHOTOS_GET_ALBUM_API_PATH = "user/album_group";//"user/album_group/format/json";
    public static final String PHOTOS_GET_ALBUM_API_PATH = "file/album";

    //public static final String DOCUMENTS_GET_API_PATH = "user/docs_group";//"user/docs_group/format/json";
    //public static final String NEWSLETTERS_GET_API_PATH = "user/newsletter_group";//"user/newsletter_group/format/json";
    public static final String DOCUMENTS_GET_API_PATH = "file/getDocuments";
    public static final String NEWSLETTERS_GET_API_PATH = "file/getNewsPapers";

  //  public static final String NOTIFICATION_GET_API  = "user/notifications_group";//"user/notifications_group/format/json";

    public static final String NOTIFICATION_GET_API  = "notification";

    // public static final String LINKS_GET_ALL_API  = "user/links_group";//"user/links_group/format/json";

    public static final String LINKS_GET_ALL_API  = "link";

    //public static final String VIDEOS_GET_ALL_API  = "user/videos_group";//"user/videos_group/format/json";
    public static final String VIDEOS_GET_ALL_API = "file/getVideos";

    public static final String GROUP_IDS = "groupIds";
    public static final String TOKEN_TO_SERVER = "tokenToServer";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";


}


package com.snaptech.colegiobosques.UI.cam;

import android.os.Environment;

import java.io.File;

/**
 * Created by Jimit Patel on 24/07/15.
 */
public class Config {

    public static final long IMAGE_THRESHOLD_SIZE = 2L * 1024L * 2024L;

    public static final String DEVICE_ANDROID = "android";

    // Fragment Tag constants
    /**
     * SplashScreen Tag for fragment
     */
    public static final String TAG_SPLASH = "Splash";
    /**
     * Welcome Screen Tag for fragment
     */
    public static final String TAG_WELCOME = "Welcome";

    // API keys. Used for sending request and for bundle internally
    /**
     * email key
     */
    public static final String KEY_EMAIL = "email";

    /**
     * password key
     */
    public static final String KEY_PASSWORD = "password";

    /**
     * confirm password key
     */
    public static final String KEY_CONFIRM_PASSWORD = "confirm";

    /**
     * First Name key
     */
    public static final String KEY_FIRSTNAME = "first_name";

    /**
     * Name key
     */
    public static final String KEY_NAME = "firstname";

    /**
     * Last Name key
     */
    public static final String KEY_LASTNAME = "last_name";

    /**
     * Gender key
     */
    public static final String KEY_GENDER = "gender";

    /**
     * Description key
     */
    public static final String KEY_DESC = "desc";

    /**
     * id key. ID as in facebook, google_plus, twitter
     */
    public static final String KEY_ID = "id";

    /**
     * Provider key. is sign up/in from facebook, twitter, google, email
     */
    public static final String KEY_PROVIDER = "provider";

    // Bundle Key. different from API keys. This is only used for application bundles only and not for APIs
    /**
     * DownloadReceiver Key
     */
    public static final String KEY_RECEIVER = "receiver";

    /**
     * URL key
     */
    public static final String KEY_URL = "url";

    /**
     * Gcm token Registration key
     */
    public static final String KEY_REGISTRATION_ID = "registration_id";

    /**
     * Device Type key
     */
    public static final String KEY_DEVICE_TYPE = "device_type";

    /**
     * Code key
     */
    public static final String KEY_RESPONSE_CODE = "code";

    /**
     * look id key
     */
    public static final String KEY_LOOK_ID = "look_id";

    /**
     * other_wishlist
     */
    public static final String KEY_OTHER_WISHLIST = "other_wishlist";

    /**
     * look page key
     */
    public static final String KEY_PAGE = "page";

    /**
     * username key
     */
    public static final String KEY_USERNAME = "username";

    /**
     * u id key
     */
    public static final String KEY_U_ID = "u_id";

    /**
     * user id key
     */
    public static final String KEY_USER_ID = "user_id";

    /**
     * comment key
     */
    public static final String KEY_COMMENT = "comment";

    /**
     * comment id key
     */
    public static final String KEY_COMMENT_ID = "comment_id";

    /**
     * count key
     */
    public static final String KEY_COUNT = "count";

    /**
     * product id key
     */
    public static final String KEY_PRODUCT_ID = "product_id";

    /**
     * wishlist board id key
     */
    public static final String KEY_BOARD_ID = "board_id";

    /**
     * wishlist new board name key
     */
    public static final String KEY_BOARD_NAME = "board_name";

    /**
     * product position key
     */
    public static final String KEY_PRODUCT_POSITION = "product_position";

    /**
     * shelf id key
     */
    public static final String KEY_SHELF_ID = "shelf_id";

    /**
     * sorce shelf id key
     */
    public static final String KEY_SOURCE_BOARD_ID = "source_board_id";

    /**
     * destination shelf id key
     */
    public static final String KEY_DESTINATION_BOARD_ID = "dest_board_id";

    /**
     * shelf id key
     */
    public static final String KEY_WARDROBE_ID = "wardrobe_id";

    /**
     * product name given to a wardrobe product key
     */
    public static final String KEY_SHELF_PRODUCT_NAME = "caption";

    /**
     * wardrobe new shelf name key
     */
    public static final String KEY_SHELF_NAME = "shelf_name";


    /**
     * add shelf shelname key
     */
    public static final String KEY_SHELF = "shelf";

    /**
     * brand id key
     */
    public static final String KEY_BRAND_ID = "brand_id";

    /**
     * brand key
     */
    public static final String KEY_BRAND = "brand";

    /**
     * brand key
     */
    public static final String KEY_BRAND_NAME = "brand_name";

    /**
     *  position key
     */
    public static final String KEY_POSITION = "position";

    /**
     * Main position key in feed for featured brands
     */
    public static final String KEY_FEED_POSITION = "feed_position";

    /**
     * follow Key
     */
    public static final String KEY_FOLLOW = "follow";

    /**
     * Data key
     */
    public static final String KEY_DATA = "data";

    /**
     * Tag key
     */
    public static final String KEY_TAG = "tag";

    /**
     * TokenId key
     */
    public static final String KEY_TOKENID = "token_id";

    /**
     * Refresh token
     */
    public static final String KEY_REFRESH_TOKEN = "refresh_token";

    /**
     * file key
     */
    public static final String KEY_FILE = "file";

    /**
     * Flag key. This key can be used for determining boolean value
     */
    public static final String KEY_FLAG = "flag";

    /**
     * Question Access type key. This key defines whether question is private or public
     */
    public static final String KEY_QTYPE = "q_type";

    /**
     * Question key.
     */
    public static final String KEY_QUESTION = "question";

    /**
     * Question ID key.
     */
    public static final String KEY_QUESTION_ID = "question_id";

    /**
     * User Acceptance key
     */
    public static final String KEY_USER_ACCEPTANCE = "user_acceptance";

    /**
     * Is only user key
     */
    public static final String KEY_IS_ONLY_USER = "is_only_user";

    /**
     *
     */
    public static final String KEY_IS_SELF_PROFILE = "is_self_profile";

    /**
     * Category Id key
     */
    public static final String KEY_CATEGORY_ID = "category_id";

    /**
     * Price filter key
     */
    public static final String KEY_PRICE = "price_range";

    /**
     * filter by key
     */
    public static final String KEY_FILTER_BY = "filters";

    /**
     * sort by key
     */
    public static final String KEY_SORT_BY = "sort";

    /**
     * order by key
     */
    public static final String KEY_ORDER_BY = "order";

    /**
     * Image key
     */
    public static final String KEY_IMAGE = "image";

    /**
     * Questionnaire's question id key
     */
    public static final String KEY_QUESTIONNAIRE_ID = "custom_field_id";

    /**
     * Questionnaire's answer key
     */
    public static final String KEY_QUESTIONNAIRE_ANSWER = "answer";

    /**
     * Blog posted_date key
     */
    public static final String KEY_POSTED_DATE = "posted_date";

    /**
     * Blog look count key
     */
    public static final String KEY_LOOK_COUNT = "look_count";

    /**
     * Blog Blog url key
     */
    public static final String KEY_BLOG_URL = "href";

    /**
     * Blog product count key
     */
    public static final String KEY_PRODUCT_COUNT = "product_count";

    /**
     * Blog title key
     */
    public static final String KEY_TITLE = "title";

    /**
     * Blog product count key
     */
    public static final String KEY_AUTHOR_NAME = "author_name";

    /**
     * Blog title key
     */
    public static final String KEY_SHORT_DESCRIPTION = "short_description";

    /**
     * Blog thumbnail key
     */
    public static final String KEY_THUMB = "thumb";


    /**
     * profile follower count Key
     */

     public static final String KEY_FOLLOWER_COUNT = "follower_count";

    /**
     * profile follower count Key
     */

    public static final String KEY_WISHIST_COUNT = "wishlist_count";

    /**
     * profile following count Key
     */

    public static final String KEY_FOLLOWING_COUNT = "following_count";

    /**
     * key user
     */
    public static final String KEY_USER = "user";

    /**
     * key look anme
     */
    public static final String KEY_LOOK_NAME = "look_name";




    /**
     * Success message key
     */
    public static final String SUCCESS_MESSAGE = "SUCCESS";

    // API Header keys
    /**
     * Authorization header
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    // API type flags. 10x series is for all APIs
    /**
     * Login API Type. This used for Volley generic class for distinguishing which api was called
     */
    public static final int API_LOGIN = 0x000100;

    // Social button identifier while signup or login. FEEx series for social buttons
    /**
     * Facebook button identifier
     */
    public static final int PROVIDER_FACEBOOK = 0x0FEE01;

    /**
     * Google plus button identifier
     */
    public static final int PROVIDER_GOOGLE = 0x0FEE02;

    /**
     * Twitter button identifier
     */
    public static final int PROVIDER_TWITTER = 0x0FEE03;

    /**
     * Email button identifier
     */
    public static final int PROVIDER_EMAIL = 0x0FEE04;

    //Provider String
    public static final String P_FACEBOOK = "Facebook";
    public static final String P_TWITTER = "Twitter";
    public static final String P_GOOGLE = "Google";

    //Gender constants Fx series
    /**
     * Male gender int
     */
    public static final int GENDER_MALE = 0x0000F0;

    /**
     * Female gender int
     */
    public static final int GENDER_FEMALE = 0x0000F1;

    /**
     * Other gender int
     */
    public static final int GENDER_OTHER = 0x0000F2;

    /**
     * String constant for GENDER_MALE
     */
    public static final String MALE = "Male";

    /**
     * String constant for GENDER_FEMALE
     */
    public static final String FEMALE = "Female";

    /**
     * String constant for GENDER_OTHER
     */
    public static final String OTHER = "Other";

    //API Code
    /**
     * Success API Code
     */
    public static final int CODE_SUCCESS = 200;

    /**
     * Expired token code
     */
    public static final int CODE_TOKEN_EXPIRED = 401;

    /**
     * Not available API code
     */
    public static final int CODE_ERROR_UNAVAILABLE = 404;

    /**
     * Internal API Code
     */
    public static final int CODE_INTERNAL_ERROR = 0;

    /**
     * Cache data return code
     */
    public static final int CODE_CACHED_DATA = 1;

    // API Urls
    /**
     * Domain URL
     */
//    public static final String BASE_DOMAIN = "http://192.168.1.200/styfi";
    public static final String BASE_DOMAIN = "https://styfi.lightbuzz.in";
//    public static final String API_DOMAIN = BASE_DOMAIN + "/api";
    public static final String API_DOMAIN = BASE_DOMAIN + "/apiv2";


//    public static final String API_DOMAIN = "http://192.168.1.200/styfi/api";
//    public static final String API_DOMAIN = "http://demo.stylabs.in/api";
//    public static final String API_DOMAIN = "http://admin.lightbuzz.in/api";

    /**
     * Login API url (non social login)
     */
    public static final String URL_LOGIN = API_DOMAIN + "/account/login";

    /**
     * Logout API url
     */
    public static final String URL_LOGOUT = API_DOMAIN + "/account/login/logout";

    /**
     * Sign Up API url (non social sign up)
     */
    public static final String URL_SIGNUP = API_DOMAIN + "/account/register";

    /**
     * Sign In API url using social id
     */
    public static final String URL_SOCIAL_LOGIN = API_DOMAIN + "/module/social_login_free/register";

    /**
     * Forgot Password API url
     */
    public static final String URL_FORGOTPASSWORD = API_DOMAIN + "/account/forgotten";

    /**
     * Add Comment API url
     */
    public static final String URL_ADD_COMMENT = API_DOMAIN + "/account/look/add_comment";

    /**
     * Delete Comment API url
     */
    public static final String URL_DELETE_COMMENT = API_DOMAIN + "/account/look/delete_comment";

    /**
     * Report Comment API url
     */
    public static final String URL_REPORT_COMMENT = API_DOMAIN + "/account/look/abuse_comment";

    /**
     *
     * Un/Favorite toggle API url
     */
    public static final String URL_TOGGLE_FAVORITE = API_DOMAIN + "/account/look/fav_unfav";

    /**
     * Un/Like toggle API url
     */
    public static final String URL_TOGGLE_LIKE = API_DOMAIN + "/account/look/like_unlike";

    /**
     * View more comments API url
     */
    public static final String URL_MORE_COMMENTS = API_DOMAIN + "/account/look/view_more_comment";

    /**
     * Favorite Looks API url (GET method)
     */
    public static final String URL_LOOKS_FAVORITE = API_DOMAIN + "/account/look/favoriated_looks";

    /**
     * Looks list API url (GET method)
     */
    public static final String URL_LOOK_DETAILS = API_DOMAIN + "/account/look/getlooksDetails";


    /**
     * Lookdetails API
     */
    public static final String URL_LOOKS = API_DOMAIN + "/account/look";

    /**
     * Adding wardrobe API url
     */
    public static final String URL_ADD_WARDROBE = API_DOMAIN + "/account/wardrobe/add_user_wardrobe";

    /**
     * Get wardrobe for a user API url (GET method)
     */
    //public static final String URL_BOARD = API_DOMAIN + "/account/wishlist";

    /**
     * Rename shelf for a user API url
     */
    public static final String URL_RENAME_BOARD = API_DOMAIN + "/account/wishlist/edit_board";

    /**
     * Delete shelf for user API url
     */
    public static final String URL_DELETE_BOARD = API_DOMAIN + "/account/wishlist/deleteBoard";

    /**
     * Delete shelf for user API url
     */
    public static final String URL_GET_BOARD_PRODUCT = API_DOMAIN + "/account/wishlist/get_product";

    /**
     * get all wishlist product API url
     */
    public static final String URL_ALL_WISHLIST_PRODUCT = API_DOMAIN + "/account/wishlist/get_all_product";

    /**
     * get all wardrobe product API url
     */
    public static final String URL_ALL_WARDROBE_PRODUCT = API_DOMAIN + "/account/wardrobe/get_all_user_product";


    /**
     *switchShelf for use API url
     */
    public static final String URL_SWITCH_BOARD = API_DOMAIN + "/account/wishlist/switch_product";

    /**
     * Create Shelf API url
     */
    public static final String URL_CREATE_BOARD = API_DOMAIN + "/account/wishlist/create_board";

    /**
     * Get Shelf for a user API url (GET method)
     */
    public static final String URL_BOARD = API_DOMAIN + "/account/wishlist/";

    /**
     * Add product to board
     */
    public static final String URL_ADD_TO_WISHLIST = API_DOMAIN + "/account/wishlist/add_product";

    /**
     * Delete Shelf Product for user API url (POST method)
     */
    public static final String URL_DELETE_BOARD_PRODUCT = API_DOMAIN + "/account/wishlist/remove_product";

    /**
     * Move to wardrobe
     */
    public static final String URL_MOVE_PRODUCT = API_DOMAIN + "/account/wishlist/move_product";

    /**
     * Get wardrobe for a user API url (GET method)
     */
    public static final String URL_WARDROBE = API_DOMAIN + "/account/wardrobe";

    /**
     * Rename shelf for a user API url
     */
    public static final String URL_RENAME_SHELF = API_DOMAIN + "/account/wardrobe/edit_shelf";

    /**
     * Delete shelf for user API url
     */
    public static final String URL_DELETE_SHELF = API_DOMAIN + "/account/wardrobe/delete_shelf";


    /**
     *switchShelf for use API url
     */
    public static final String URL_SWITCH_SHELF = API_DOMAIN + "/account/wardrobe/switch_product";

    /**
     * Create Shelf API url
     */
    public static final String URL_CREATE_SHELF = API_DOMAIN + "/account/wardrobe/create_shelf";

    /**
     * Get Shelf for a user API url (GET method)
     */
    public static final String URL_SHELF = API_DOMAIN + "/account/wardrobe/get_user_wardrobe";

    /**
     * Delete Shelf Product for user API url (POST method)
     */
    public static final String URL_DELETE_SHELF_PRODUCT = API_DOMAIN + "/account/wardrobe/delete_product";

    /**
     * Get Brnads for user API url (GET method)
     */
    public static final String URL_FOLLOW_BRAND = API_DOMAIN + "/follow_brand/brand/follow_brand";

    /**
     * Follow Brand for user API url (POST method)
     */
    public static final String URL_GET_BRANDS = API_DOMAIN + "/follow_brand/brand";

    /**
     * Get Individual Product Details for user API (GET method)
     */
    public static final String URL_INDIVIDUAL_PRODUCT = API_DOMAIN + "/product/product/getProductDetails";

    /**
     * Get public AskQuery for user API (GET method)
     */
    public static final String URL_ASKQUERIES_PUBLIC = API_DOMAIN + "/question/question";

    /**
     * Get private AskQuery for user API (GET method)
     */
    public static final String URL_GET_FEED = API_DOMAIN + "/account/profile/feed/";

    /**
     * get feed
     */
    public static final String URL_ASKQUERIES_PRIVATE = API_DOMAIN + "/question/question/get_question_answer_by_user";

    /**
     * Blog
     */
    public static final String URL_BLOG = API_DOMAIN + "/index.php?route=blog/post&webview=true";

    /**
     * About
     */
    public static final String URL_ABOUT = BASE_DOMAIN + "/index.php?route=information/information&information_id=4&webview=true";



    /**
     * Adding new query/question to stylist API
     */
    public static final String URL_ADDQUESTION = API_DOMAIN + "/question/question/ask_question";

    /**
     * Get replies for a question API (GET method)
     */
    public static final String URL_GET_QUESTION_DETAILS = API_DOMAIN + "/question/question/get_question_details";

    public static final String URL_ADD_QUESTION_REPLY = API_DOMAIN +"/question/question/make_comment";

    public static final String URL_QUESTION_USER_ACCEPTANCE = API_DOMAIN +"/question/question/user_acceptance";

    public static final String URL_REFRESH_TOKEN = API_DOMAIN + "/account/login/get_new_access_token";

    public static final String URL_TOKEN_CHECK = API_DOMAIN + "/account/login/check_access_token_expire";

    public static final String URL_GET_FILTERS = API_DOMAIN + "/catalog/category/get_leaf_categories";

    public static final String URL_PRODUCT_LIST = API_DOMAIN + "/product/product/get";

    public static final String URL_NEW_PRODUCT_LIST = API_DOMAIN + "/product/product/get_new";

    public static final String URL_GET_BRAND_FEED = API_DOMAIN + "/follow_brand/brand/feed";

    public static final String URL_GET_BRAND_PROFILE = API_DOMAIN + "/follow_brand/brand/detail";

    public static final String URL_GET_USER_DETAILS = API_DOMAIN + "/account/profile/get_user_detail";

    public static final String URL_GET_USER_WISHLIST = API_DOMAIN + "/account/profile/view_wishlist";

    public static final String URL_GET_USER_FOLLOWERS_LIST = API_DOMAIN + "/account/profile/get_followers";

    public static final String URL_GET_USER_FOLLOWING_LIST = API_DOMAIN + "/account/profile/get_following";

    public static final String URL_GET_QUESTIONNAIRE = API_DOMAIN + "/account/questionnaire";

    public static final String URL_QUESTIONNAIRE_RESPONSE = API_DOMAIN + "/account/questionnaire/answer";

    public static final String URL_GET_CATEGORIES_FILTER = API_DOMAIN + "/catalog/category/get_category_filter";

    public static final String URL_LOOK_SHARE = API_DOMAIN + "/index.php?route=account/look/detail&look_id=";

    public static final String URL_TOGGLE_FOLLOW_USER = API_DOMAIN + "/account/profile/follow";

    public static final String URL_PRODUCT_SHARE = API_DOMAIN + "/index.php?route=product/product&product_id=";

    public static final String URL_BLOG_LIST = API_DOMAIN + "/blog/post/getBlogPost";

    public static final String URL_EDIT_PROFILE_IMAGE = API_DOMAIN + "/account/profile/profile_image";

    public static final String URL_SEARCH_LOOK = API_DOMAIN + "/account/look";

    public static final String URL_SEARCH_USER = API_DOMAIN + "/account/profile/searchUser";


    // Disk Cache
    /**
     * Disk Cache filename
     */
    public static final String CACHE_NAME = "styfi_cache";

    public static final String IMG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Pictures" + File.separator + "StyFi";

    /**
     * Number of values per cache entry
     */
    public static final int CACHE_VALUE_COUNT = 1;

    //Access flags
    /**
     * Delete access value. Not modify
     */
    public static final int ACCESS_DELETE = 1;

    /**
     * Modify access value (not delete)
     */
    public static final int ACCESS_MODIFY = 2;

    /**
     * Read access value
     */
    public static final int ACCESS_READ = 4;

    /**
     * Access token key for shared pref
     */
    public static final String KEY_ACCESSTOKEN = "access_token";

    /**
     * Access array
     */
    public static int[] ACCESS = {ACCESS_DELETE,
            ACCESS_MODIFY,
            ACCESS_READ,
            ACCESS_DELETE | Config.ACCESS_MODIFY,
            ACCESS_MODIFY | Config.ACCESS_READ,
            ACCESS_DELETE | Config.ACCESS_READ,
            ACCESS_DELETE | Config.ACCESS_MODIFY | Config.ACCESS_READ
    };

    // Database
    /**
     * ORMLite Config filename, used for generating ORMLite Database Utility file
     */
    public static final String ORMLITE_CONFIG_FILE = "ormlite_config.txt";
    public static final String STATUS_LIKE = "like";
    public static final String STATUS_FAVORITE = "favoraite";


    // Camera related constants
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_COMPRESSION = "compression";
    public static final String KEY_CAM_FACE = "face";
    public static final String KEY_IMG_FORMAT = "img_format";
    public static final String KEY_MEDIAFILE = "mediaFile";
    public static final String KEY_IDENTIFIER = "identifier";
    public static final String KEY_REQUEST_CODE = "request_code";

    public enum CameraFace {
        BACK (0),
        FRONT (1);

        CameraFace(int cameraFace) {
            mCameraFace = cameraFace;
        }

        public int getValue() {
            return mCameraFace;
        }
        final int mCameraFace;
    }
}
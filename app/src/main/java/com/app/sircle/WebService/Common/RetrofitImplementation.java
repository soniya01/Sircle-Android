package com.app.sircle.WebService.Common;

import android.content.Context;

import android.preference.PreferenceManager;
import android.widget.Switch;

import com.app.sircle.Manager.LoginManager;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Common;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.LoginResponse;
import com.app.sircle.WebService.TermsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by soniya on 3/30/15.
 */
public class RetrofitImplementation implements WebServiceProtocol{

    private static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss";
    private String baseURL = Constants.BASE_URL;
    public UrlGenerator generator;
    private static final  HashMap<String,String> urlParams = new HashMap<>();
    private Callback<JsonElement> jsonElementCallback;

    public RetrofitImplementation() {
//        jsonElementCallback = new Callback<JsonElement>() {
//            @Override
//            public void success(JsonElement jsonElement, Response response) {
//                if (!jsonElement.isJsonNull() ){
//
//                    Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
//
//                    if (responseClass != null){
//                        Object object = Common.createObjectForClass(responseClass);
//
//                        if (jsonElement.isJsonArray()){
//                            Type collectionType = new TypeToken<Collection<Object>>(){}.getType();
//                            Collection<Object> data = gson.fromJson(jsonElement, collectionType);
//                            webserviceListener.onCompletion(data, new AppError());
//                        } else {
//                            object = gson.fromJson(jsonElement, responseClass);
//                            webserviceListener.onCompletion(object, new AppError());
//                        }
//                    }
//
//                }
//                webserviceListener.onCompletion(null, new AppError());
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        };
    }

    @Override
    public void executePostWithURL(final String url,final HashMap<String,String> params, Object requestObject,final Class responseClass, final WebServiceListener webserviceListener) {
        // Setup Adapter for Rest Operations
        this.generator = new UrlGenerator(params, url);
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.baseURL + this.generator.subUrl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {

                        if (responseClass != LoginResponse.class) {
                            request.addHeader("Authorization", LoginManager.accessToken);


                        }
                    }
                })
                .setConverter(new GsonCustomConverter(gson))
                .build();

        WebserviceApi postWebservice = restAdapter.create(WebserviceApi.class);

        switch (url){
            case Constants.LOGIN_API_PATH:
                postWebservice.login(params.get("loginId"), params.get("pwd"), params.get("regId"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {

                        if (!jsonElement.isJsonNull() ){

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null){
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()){
                                    Type collectionType = new TypeToken<Collection<Object>>(){}.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    // parsing jsonelement if unauthorised
                                    JsonObject jobject = jsonElement.getAsJsonObject();
                                    jobject = jobject.getAsJsonObject("data");
                                    if (jobject == null){
                                        // responseClass =
                                        webserviceListener.onCompletion(null, new AppError());
                                    }else {
                                        object = gson.fromJson(jsonElement, responseClass);
                                        webserviceListener.onCompletion(object, new AppError());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null){
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        }else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;

            case Constants.NOTIFICATION_ADD_GROUPS:
                postWebservice.postNotification(params.get("subject"), params.get("msg"), params.get("grp"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {

                        // Get Json Response and Parse it to get response in UPPER_CAMEL_CASE
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null) {
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()) {
                                    Type collectionType = new TypeToken<Collection<Object>>() {
                                    }.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null) {
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        } else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });

                break;

            case Constants.GROUP_UPDATE_ALL_NOTIFICATION:
                postWebservice.postWithRegIdAndVal(params.get("regId"), params.get("val"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        // Get Json Response and Parse it to get response in UPPER_CAMEL_CASE
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null) {
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()) {
                                    Type collectionType = new TypeToken<Collection<Object>>() {
                                    }.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }

                        }
                       // webserviceListener.onCompletion(null, new AppError());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null) {
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        } else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;
            case Constants.GROUP_UPDATE_NOTIFICATION:
                postWebservice.postGroupNotification(params.get("regId"), params.get("groupValString"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null) {
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()) {
                                    Type collectionType = new TypeToken<Collection<Object>>() {
                                    }.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }
                        }
                        //webserviceListener.onCompletion(null, new AppError());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null) {
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        } else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;
            case Constants.ADD_NEW_LINK_API_PATH:
                postWebservice.postLink(params.get("name"), params.get("url"), params.get("grp"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null) {
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()) {
                                    Type collectionType = new TypeToken<Collection<Object>>() {
                                    }.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null) {
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        } else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;
            case Constants.PHOTOS_ADD_NEW_ALBUM_API_PATH:
                postWebservice.postAlbum(params.get("albumName"), params.get("grp"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null) {
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()) {
                                    Type collectionType = new TypeToken<Collection<Object>>() {
                                    }.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null) {
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        } else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;
            case Constants.EVENTS_ADD_NEW_EVENT_API_PATH:
                postWebservice.postEvents(params, new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null) {
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()) {
                                    Type collectionType = new TypeToken<Collection<Object>>() {
                                    }.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null) {
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        } else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;
            default:
                postWebservice.post(params.get("eventId"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if (responseClass != null) {
                                Object object = Common.createObjectForClass(responseClass);

                                if (jsonElement.isJsonArray()) {
                                    Type collectionType = new TypeToken<Collection<Object>>() {
                                    }.getType();
                                    Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                    webserviceListener.onCompletion(data, new AppError());
                                } else {
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());

                        // Send empty object
                        if (responseClass != null) {
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        } else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;
        }
    }


    public void executeGetWithURL(  String url, final HashMap<String,String> params,final Object requestObject,final Class responseClass, final WebServiceListener webserviceListener) {
        // Setup Adapter for Rest Operations

        this.generator = new UrlGenerator(params, url);
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
//        final OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
//        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.baseURL + url)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", LoginManager.accessToken);
                        //request.addHeader("Authorization", "3ec8e9ed13ad96b6b979517f5bf34545891f4958");

//                        if (params != null){
//                            for (String key : params.keySet()){
//                                request.addQueryParam(key, String.valueOf(params.get(key)));
//                            }
//                        }
                    }
                })
                .setConverter(new GsonCustomConverter(gson))
                .build();

        WebserviceApi getWebservice = restAdapter.create(WebserviceApi.class);

        getWebservice.get(params, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                if (!jsonElement.isJsonNull()){
                    Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
                    Object object = Common.createObjectForClass(responseClass);

                    if (jsonElement.isJsonArray()){
                        Type collectionType = new TypeToken<Collection<Object>>(){}.getType();
                        Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                        webserviceListener.onCompletion(data, new AppError());
                    }else {

                            JsonObject jobject = jsonElement.getAsJsonObject();
                            try {
                                jobject = jobject.getAsJsonObject("data");
                                object = gson.fromJson(jsonElement, responseClass);
                                webserviceListener.onCompletion(object, new AppError());
                            }catch (Exception e){
                                object = gson.fromJson(jsonElement, responseClass);
                                webserviceListener.onCompletion(object, new AppError());
                            }

                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

                AppError appError = new AppError();
                appError.setErrorCode(getRetrofitErrorcode(error));
                appError.setErrorMessage(error.getLocalizedMessage());

                // Send empty object
                if (responseClass != null){
                    webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                }else {
                    webserviceListener.onCompletion(null, appError);
                }
            }
        });

    }


    @Override
    public void executePutWithURL(final String url,final HashMap<String,String> params ,Object requestObject, final Class responseClass,final  WebServiceListener webserviceListener) {

        this.generator = new UrlGenerator(params, url);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.baseURL + this.generator.subUrl )
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //request.addHeader(Constants.AUTHORIZATION, SignInManager.getSharedInstance().sessionId);
                    }
                })
                .build();

       WebserviceApi putWebservice = restAdapter.create(WebserviceApi.class);

        // Put Request
        putWebservice.put(requestObject, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                if (!jsonElement.isJsonNull()) {
                    Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
                    Object object = Common.createObjectForClass(responseClass);

                    if (jsonElement.isJsonArray()){
                        Type collectionType = new TypeToken<Collection<Object>>(){}.getType();
                        Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                        webserviceListener.onCompletion(data, new AppError());
                    }else {
                        object = gson.fromJson(jsonElement, responseClass);
                        webserviceListener.onCompletion(object, new AppError());
                    }
                }
                //webserviceListener.onCompletion(null, new AppError());
            }

            @Override
            public void failure(RetrofitError error) {

                AppError appError = new AppError();
                appError.setErrorCode(getRetrofitErrorcode(error));
                appError.setErrorMessage(error.getLocalizedMessage());

                // Send empty object
                if (responseClass != null){
                    webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                }else {
                    webserviceListener.onCompletion(null, appError);
                }
            }
        });
    }

    @Override
    public void executeUploadImageWithURL(final String url, final HashMap<String, String> params, final TypedFile typedFile, final Class responseClass, final WebServiceListener webserviceListener) {

        this.generator = new UrlGenerator(params, url);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.baseURL + this.generator.subUrl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", LoginManager.accessToken);
                    }
                })
                .build();

        TypedString id = new TypedString("alb_id");
        TypedString name = new TypedString("caption");

        WebserviceApi uploadImageService = restAdapter.create(WebserviceApi.class);

        uploadImageService.uploadImage(params.get("alb_id"),params.get("caption"), typedFile, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                if (!jsonElement.isJsonNull()) {
                    Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
                    Object object = Common.createObjectForClass(responseClass);

                    if (jsonElement.isJsonArray()){
                        Type collectionType = new TypeToken<Collection<Object>>(){}.getType();
                        Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                        webserviceListener.onCompletion(data, new AppError());
                    }else {
                        object = gson.fromJson(jsonElement, responseClass);
                        webserviceListener.onCompletion(object, new AppError());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

                AppError appError = new AppError();
                appError.setErrorCode(getRetrofitErrorcode(error));
                appError.setErrorMessage(error.getLocalizedMessage());

                // Send empty object
                if (responseClass != null){
                    webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                }else {
                    webserviceListener.onCompletion(null, appError);
                }
            }
        });
    }

    /**
     * Interface for the post , get and put request server api calls
     */
    private interface WebserviceApi {


        @FormUrlEncoded
        @POST("/")
        void login(@Field("loginId") String loginId, @Field("pwd") String password, @Field("regId") String regId, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void post(@Field("eventId") String eventId, Callback<JsonElement> callback);


        @FormUrlEncoded
        @POST("/")
        void postWithRegIdAndVal(@Field("regId") String regId, @Field("val") String subscribeVal, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postGroupNotification(@Field("regId") String regId, @Field("groupValString") String subscribeVal, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postNotification(@Field("subject") String subject, @Field("msg") String msg, @Field("grp") String grpAray, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postLink(@Field("name") String name, @Field("url") String url, @Field("grp") String grpArray, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postAlbum(@Field("albumName") String albumName, @Field("grp") String grpArray, Callback<JsonElement> callback);

        @GET("/")
        void get(@QueryMap HashMap<String, String> params, Callback<JsonElement> callback);


        @PUT("/")
        void put(@Body Object requestObject, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postEvents(@FieldMap Map<String, String> names, Callback<JsonElement> callback);

        @Multipart
        @POST("/")
        void uploadImage(@Part("alb_id")String id, @Part("caption") String title, @Part("files") TypedFile image, Callback<JsonElement> cb);

    }


    /**
     * Retrieves error status for Retrofit error received
     *
     * @param retrofitError - Error object received from server
     * @return int value for the error status received
     */
    private int getRetrofitErrorcode(RetrofitError retrofitError) {

        if (retrofitError.getKind().equals(RetrofitError.Kind.HTTP)) {
            return AppError.WEBSERVICE_HTTP;
        } else if (retrofitError.getKind().equals(RetrofitError.Kind.CONVERSION)) {
            return AppError.WEBSERVICE_CONVERSION;
        } else if (retrofitError.getKind().equals(RetrofitError.Kind.NETWORK)) {
            return AppError.WEBSERVICE_NETWORK;
        } else if (retrofitError.getKind().equals(RetrofitError.Kind.UNEXPECTED)) {
            return AppError.WEBSERVICE_UNEXPECTED;
        } else {
            return AppError.WEBSERVICE_UNKNOWN;
        }
    }
}

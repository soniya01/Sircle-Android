package com.app.sircle.WebService.Common;

import android.widget.Switch;

import com.app.sircle.Manager.LoginManager;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Common;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;

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
                                    object = gson.fromJson(jsonElement, responseClass);
                                    webserviceListener.onCompletion(object, new AppError());
                                }
                            }

                        }
                        webserviceListener.onCompletion(null, new AppError());
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

            case Constants.NOTIFICATION_GET_ALL_GROUPS:
                postWebservice.postWithRegId(Constants.GCM_REG_ID, new Callback<JsonElement>() {
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
                        webserviceListener.onCompletion(null, new AppError());
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
                        webserviceListener.onCompletion(null, new AppError());
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
                postWebservice.postWithRegIdAndGrpId(params.get("regId"), params.get("val"), params.get("groupId"), new Callback<JsonElement>() {
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
                        webserviceListener.onCompletion(null, new AppError());
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
                .setEndpoint(this.baseURL + this.generator.subUrl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization", LoginManager.accessToken);
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
                        object = gson.fromJson(jsonElement, responseClass);
                        webserviceListener.onCompletion(object, new AppError());
                    }
                }
                webserviceListener.onCompletion(null, new AppError());
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
                webserviceListener.onCompletion(null, new AppError());
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
                .setEndpoint(this.baseURL + this.generator.subUrl )
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //request.addHeader(Constants.AUTHORIZATION, SignInManager.getSharedInstance().sessionId);
                    }
                })
                .build();

        WebserviceApi uploadImageService = restAdapter.create(WebserviceApi.class);

        uploadImageService.uploadImage(typedFile, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

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
        void postWithRegId(@Field("regId") String regId, Callback<JsonElement> callback);


        @FormUrlEncoded
        @POST("/")
        void postWithRegIdAndVal(@Field("regId") String regId, @Field("val") String subscribeVal, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postWithRegIdAndGrpId(@Field("regId") String regId, @Field("val") String subscribeVal, @Field("groupId") String groupId, Callback<JsonElement> callback);

        @GET("/")
        void get(@QueryMap HashMap<String, String> params, Callback<JsonElement> callback);


        @PUT("/")
        void put(@Body Object requestObject, Callback<JsonElement> callback);

        @Multipart
        @POST("/")
        void uploadImage(@Part("data") TypedFile image, Callback<JsonElement> cb);

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

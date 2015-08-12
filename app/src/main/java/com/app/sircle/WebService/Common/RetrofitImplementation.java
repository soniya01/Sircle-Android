package com.app.sircle.WebService.Common;

import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Common;
import com.app.sircle.Utility.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Created by soniya on 3/30/15.
 */
public class RetrofitImplementation implements WebServiceProtocol{

    private static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss";
    private String baseURL = Constants.BASE_URL;
    public UrlGenerator generator;
    private static final  HashMap<String,String> urlParams = new HashMap<>();


    @Override
    public void executePostWithURL(final String url,final HashMap<String,String> params, Object requestObject,final Class responseClass, final WebServiceListener webserviceListener) {
        // Setup Adapter for Rest Operations
        this.generator = new UrlGenerator(params, url);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.baseURL + this.generator.subUrl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
//                        if (responseClass != IHHAPI_Session.class) {
//                            request.addHeader(Constants.AUTHORIZATION, SignInManager.getSharedInstance().sessionId);
//                        }
//                        for (String key : params.keySet()) {
//                            request.addPathParam(key, params.get(key).toString());
//                        }
                    }
                })
                .build();

        WebserviceApi postWebservice = restAdapter.create(WebserviceApi.class);

        // Post Request
        postWebservice.post(requestObject, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                // Get Json Response and Parse it to get response in UPPER_CAMEL_CASE

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
                webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
            }
        });
    }


    public void executeGetWithURL(  String url, final HashMap<String,String> params, Object requestObject,final Class responseClass, final WebServiceListener webserviceListener) {
        // Setup Adapter for Rest Operations

        this.generator = new UrlGenerator(params, url);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.baseURL + this.generator.subUrl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                       // request.addHeader(Constants.AUTHORIZATION, SignInManager.getSharedInstance().sessionId);
                    }
                })
                .build();

        WebserviceApi getWebservice = restAdapter.create(WebserviceApi.class);

        getWebservice.get( new Callback<JsonElement>() {
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
                webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
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
                webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
            }
        });
    }


    /**
     * Interface for the post , get and put request server api calls
     */
    private interface WebserviceApi {

        @POST("/")
        void post(@Body Object requestObject, Callback<JsonElement> callback);


        @GET("/")
        void get(Callback<JsonElement> callback);


        @PUT("/")
        void put(@Body Object requestObject, Callback<JsonElement> callback);

    }


    /**
     * Retrieves error code for Retrofit error received
     *
     * @param retrofitError - Error object received from server
     * @return int value for the error code received
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

package com.snaptech.naharInt.WebService.Common;

import android.content.Context;

import android.content.SharedPreferences;

import com.snaptech.naharInt.Utility.AppError;
import com.snaptech.naharInt.Utility.Common;
import com.snaptech.naharInt.Utility.Constants;
import com.snaptech.naharInt.WebService.ForgotPasswordResponse;
import com.snaptech.naharInt.WebService.LoginResponse;
import com.snaptech.naharInt.custom.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
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
//            }ƒ
//        };
    }

    @Override
    public void executePostWithURL(final String url,final HashMap<String,String> params, Object requestObject,final Class responseClass, final WebServiceListener webserviceListener) {
        // Setup Adapter for Rest Operations
        this.generator = new UrlGenerator(params, url);
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();


       // RestAdapter myreAdapter = new RestAdapter().Builder();

      //  RestAdapter adapter = new RestAdapter.Builder().setEndpoint(url).build();



        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.baseURL + this.generator.subUrl)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {

                        if (responseClass != LoginResponse.class) {


                            if (responseClass != ForgotPasswordResponse.class)
                            {

                                SharedPreferences sharedpreferences = App.getAppContext().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                                String accessToken = sharedpreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

                                request.addHeader("Authorization", accessToken);
                                //System.out.println("Token is "+accessToken);
                            }



                           // System.out.println("access "+LoginManager.accessToken);


                        }
                    }
                })
                .setConverter(new GsonCustomConverter(gson))
                .build();

        WebserviceApi postWebservice = restAdapter.create(WebserviceApi.class);




        switch (url){
            case Constants.LOGIN_API_PATH:
               // postWebservice.login(params.get("email"), params.get("password"), params.get("device_token"), params.get("device_type"), new Callback<JsonElement>() {
                postWebservice.login(params.get("email"), params.get("password"), params.get("device_token"), params.get("device_type"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {

                        System.out.println("Output on Login is "+jsonElement.toString());
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
                                  //  JsonArray jsonArray = jobject.getAsJsonArray("data");
                                    jobject = jobject.getAsJsonObject("data");
                                    if (jobject == null){
                                        // responseClass =
                                        webserviceListener.onCompletion(null, new AppError());
                                    }
//                                    else if (jsonArray.size()==0) {
//
//                                        webserviceListener.onCompletion(null, new AppError());
//                                    }
                                    else{

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

            case Constants.FORGOTPASSWORD_API_PATH:
                // postWebservice.login(params.get("email"), params.get("password"), params.get("device_token"), params.get("device_type"), new Callback<JsonElement>() {
                postWebservice.forgotPassword(params.get("email"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {

                        if (jsonElement != null) {

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
                                        // parsing jsonelement if unauthorised
                                        JsonObject jobject = jsonElement.getAsJsonObject();
                                        //  JsonArray jsonArray = jobject.getAsJsonArray("data");
                                        //   jobject = jobject.getAsJsonObject("data");
                                        if (jobject == null) {
                                            // responseClass =
                                            webserviceListener.onCompletion(null, new AppError());
                                        }
//                                    else if (jsonArray.size()==0) {
//
//                                        webserviceListener.onCompletion(null, new AppError());
//                                    }
                                        else {

                                            object = gson.fromJson(jsonElement, responseClass);
                                            webserviceListener.onCompletion(object, new AppError());
                                        }
                                    }
                                }
                            }
                        } else {
                            webserviceListener.onCompletion(null, new AppError());
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

            case Constants.LOGOUT_USER_FORCEFULLY:
                // postWebservice.login(params.get("email"), params.get("password"), params.get("device_token"), params.get("device_type"), new Callback<JsonElement>() {
                postWebservice.checkLogoutStatus(Integer.parseInt(params.get("page")), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {

                        if (jsonElement != null) {

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
                                        // parsing jsonelement if unauthorised
                                        JsonObject jobject = jsonElement.getAsJsonObject();
                                        //  JsonArray jsonArray = jobject.getAsJsonArray("data");
                                        //   jobject = jobject.getAsJsonObject("data");
                                        if (jobject == null) {
                                            // responseClass =
                                            webserviceListener.onCompletion(null, new AppError());
                                        }
//                                    else if (jsonArray.size()==0) {
//
//                                        webserviceListener.onCompletion(null, new AppError());
//                                    }
                                        else {

                                            object = gson.fromJson(jsonElement, responseClass);
                                            webserviceListener.onCompletion(object, new AppError());
                                        }
                                    }
                                }
                            }
                        } else {
                            webserviceListener.onCompletion(null, new AppError());
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


            case Constants.CHECK_USER_LOGOUT:
                // postWebservice.login(params.get("email"), params.get("password"), params.get("device_token"), params.get("device_type"), new Callback<JsonElement>() {
                postWebservice.checkLogoutStatus(Integer.parseInt(params.get("page")),new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {

                        System.out.println("Check user logout, output is "+jsonElement.toString());
                        int code=0;
                        if (jsonElement!=null) {

                            if (!jsonElement.isJsonNull()) {

                                try {
                                    JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                    code=jsonObject.getInt("code");
                                    if(code==401){
                                        Constants.flag_logout=true;
                                        webserviceListener.onCompletion(null, new AppError());
                                    }
                                    else
                                        Constants.flag_logout=false;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                                if(code==200||code==404){
                                if (responseClass != null) {

                                    Object object = Common.createObjectForClass(responseClass);

                                    if (jsonElement.isJsonArray()) {
                                        Type collectionType = new TypeToken<Collection<Object>>() {
                                        }.getType();
                                        Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                                        webserviceListener.onCompletion(data, new AppError());
                                    } else {
                                        // parsing jsonelement if unauthorised
                                        JsonObject jobject = jsonElement.getAsJsonObject();
                                        //  JsonArray jsonArray = jobject.getAsJsonArray("data");
                                        //   jobject = jobject.getAsJsonObject("data");
                                        if (jobject == null) {
                                            // responseClass =
                                            webserviceListener.onCompletion(null, new AppError());
                                        }
//                                    else if (jsonArray.size()==0) {
//
//                                        webserviceListener.onCompletion(null, new AppError());
//                                    }
                                        else {

                                            object = gson.fromJson(jsonElement, responseClass);
                                            webserviceListener.onCompletion(object, new AppError());
                                        }
                                    }
                                }
                            }
                        }
                        }

                        else
                        {
                            webserviceListener.onCompletion(null, new AppError());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        AppError appError = new AppError();
                        appError.setErrorCode(getRetrofitErrorcode(error));
                        appError.setErrorMessage(error.getLocalizedMessage());
                        System.out.println("Inside Failure , code is " +appError.getErrorCode()+" , message : "+error.getLocalizedMessage());
                        // Send empty object
                        if (responseClass != null){
                            webserviceListener.onCompletion(Common.createObjectForClass(responseClass), appError);
                        }else {
                            webserviceListener.onCompletion(null, appError);
                        }
                    }
                });
                break;

            case Constants.REGISTER_API_PATH:
                // postWebservice.login(params.get("email"), params.get("password"), params.get("device_token"), params.get("device_type"), new Callback<JsonElement>() {
                postWebservice.registerUser(params.get("email"),params.get("password"),params.get("confirm_password"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {


                        if (jsonElement!=null) {

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
                                        // parsing jsonelement if unauthorised
                                        JsonObject jobject = jsonElement.getAsJsonObject();
                                        //  JsonArray jsonArray = jobject.getAsJsonArray("data");
                                        //   jobject = jobject.getAsJsonObject("data");
                                        if (jobject == null) {
                                            // responseClass =
                                            webserviceListener.onCompletion(null, new AppError());
                                        }
//                                    else if (jsonArray.size()==0) {
//
//                                        webserviceListener.onCompletion(null, new AppError());
//                                    }
                                        else {

                                            object = gson.fromJson(jsonElement, responseClass);
                                            webserviceListener.onCompletion(object, new AppError());
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            webserviceListener.onCompletion(null, new AppError());
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


            case Constants.DOCUMENTS_GET_API_PATH :
                postWebservice.postApi(Integer.parseInt(params.get("page")) ,new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if(code==200){
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

            case Constants.LINKS_GET_ALL_API:
                postWebservice.postApi(Integer.parseInt(params.get("page")), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if(code==200){
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


            case Constants.NEWSLETTERS_GET_API_PATH:
                postWebservice.postApi(Integer.parseInt(params.get("page")), new Callback<JsonElement>() {

                    int code=0;
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if(code==200){
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

            case Constants.VIDEOS_GET_ALL_API:
                postWebservice.postApi(Integer.parseInt(params.get("page")), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(code==200){
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

            case Constants.NOTIFICATION_GET_API :
                postWebservice.postApi(Integer.parseInt(params.get("page")) ,new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(code==200){
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


            case Constants.PHOTOS_GET_ALBUM_API_PATH :
                postWebservice.postApi(Integer.parseInt(params.get("page")) ,new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(code==200){
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

           //


            case Constants.PHOTOS_GET_API_PATH :
                postWebservice.postAlbumDetailsApi(Integer.parseInt(params.get("album_id")),new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if(code==200){
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



            case Constants.EVENTS_GET_MONTH_WISE_API_PATH :
                postWebservice.postEventDetailsApi(Integer.parseInt(params.get("page")),params.get("filter_month"),params.get("filter_year"),params.get("filter_date"),new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            System.out.println("Event list is "+jsonElement.toString());
                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if(code==200){
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
//{"code":200,"message":"No Error","data":[]} for ph
            //{"code":200,"message":"No Error","data":{"event_id":"93","event_title":"new test app 3","event_description":"","event_location":"","event_start_date":"23-11-2016 1:20 PM","event_end_date":"23-11-2016 1:20 PM","category_id":null,"category_name":"","groups":[{"group_id":"6","group_name":"Football Team"},{"group_id":"5","group_name":"Secondary"},{"group_id":"7","group_name":"Staff"},{"group_id":"1","group_name":"Default"},{"group_id":"4","group_name":"Nursery"},{"group_id":"2","group_name":"Primary"}]}}
            case Constants.EVENTS_GET_DETAILS_API_PATH :
                postWebservice.postEventDetailApi(Integer.parseInt(params.get("event_id")),new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            System.out.println("Event details are "+jsonElement.toString());
                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Json Element is "+jsonElement.toString());
                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if(code==200){
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


            case "notification/add":
                postWebservice.postNotification(params.get("notification_title"), params.get("notification_message"), params.get("group_id"), new Callback<JsonElement>() {
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
                postWebservice.postGroupNotification(params.get("group_id"), new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            System.out.println("Group changes response is "+jsonElement.toString());

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


            case Constants.EVENTS_GET_CATEGORY_API_PATH:

                postWebservice.postApiCategories(1,new Callback<JsonElement>() {
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



            case Constants.LOGOUT_API_PATH:

                postWebservice.postApiCategories(1,new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        if (!jsonElement.isJsonNull()) {

                            int code=0;
                            try {
                                JSONObject jsonObject=new JSONObject(jsonElement.toString());
                                code=jsonObject.getInt("code");
                                if(code==401){
                                    Constants.flag_logout=true;
                                    webserviceListener.onCompletion(null, new AppError());
                                }
                                else
                                    Constants.flag_logout=false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();

                            if(code==200){
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


            case Constants.EVENTS_GET_ALL_TERMS_API_PATH:
                postWebservice.postApiCategories(1,new Callback<JsonElement>() {
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


            case Constants.ADD_NEW_LINK_API_PATH:
                postWebservice.postLink(params.get("link_name"), params.get("link_url"), params.get("group_id"), new Callback<JsonElement>() {
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
                postWebservice.postAlbum(params.get("album_name"), params.get("group_id"), new Callback<JsonElement>() {
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

                            System.out.println("Response after add is "+jsonElement.toString());

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


    public void executeGetWithURL(final String url, final HashMap<String,String> params,final Object requestObject,final Class responseClass, final WebServiceListener webserviceListener) {
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


                        SharedPreferences sharedpreferences = App.getAppContext().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                        String accessToken = sharedpreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

//                        if(!url.equalsIgnoreCase(Constants.FORCED_UPDATE_URL))
//                        request.addHeader("Authorization", "");
//                        else {
                            String accessToken2 = sharedpreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);
                            request.addHeader("Authorization", accessToken2);
                            //request.addHeader("Authorization", "3ec8e9ed13ad96b6b979517f5bf34545891f4958");
//                        }
                    }
                })
                .setConverter(new GsonCustomConverter(gson))
                .build();

        WebserviceApi getWebservice = restAdapter.create(WebserviceApi.class);

        getWebservice.get(params, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                System.out.println("Data is "+jsonElement.toString());

                if (!jsonElement.isJsonNull()){
                    int code=0;
                    try {
                        JSONObject jsonObject=new JSONObject(jsonElement.toString());
                        code=jsonObject.getInt("code");
                        if(code==401){
                            Constants.flag_logout=true;
                            webserviceListener.onCompletion(null, new AppError());
                        }
                        else
                            Constants.flag_logout=false;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_UTC).create();
                    Object object = Common.createObjectForClass(responseClass);

                    if(code==200){
                    if (jsonElement.isJsonArray()){
                        Type collectionType = new TypeToken<Collection<Object>>(){}.getType();
                        Collection<Object> data = gson.fromJson(jsonElement, collectionType);
                        webserviceListener.onCompletion(data, new AppError());
                    }else {

                           // JsonObject jobject = jsonElement.getAsJsonObject();
                            try {
                               // jobject = jobject.getAsJsonObject("data");
                                object = gson.fromJson(jsonElement, responseClass);
                                webserviceListener.onCompletion(object, new AppError());
                            }catch (Exception e){
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

                        SharedPreferences sharedpreferences = App.getAppContext().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                        String accessToken = sharedpreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

                        request.addHeader("Authorization", accessToken);

                        //request.addHeader("Authorization", LoginManager.accessToken);
                    }
                })
                .build();

        TypedString id = new TypedString("album_id");
        TypedString name = new TypedString("caption_name");

        WebserviceApi uploadImageService = restAdapter.create(WebserviceApi.class);

        uploadImageService.uploadImage(params.get("album_id"),params.get("caption_name"), typedFile, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                System.out.println("Response is "+jsonElement.toString());
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
        void postApi(@Field("page") int page, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void linksApi(@Field("page") int page, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postApiCategories(@Field("page") int page,Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postEventDetailApi(@Field("event_id") int page, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postEventDetailsApi(@Field("page") int page,@Field("filter_month") String filterMonth,@Field("filter_year") String filterYear,@Field("filter_date") String filterDate, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postAlbumDetailsApi(@Field("album_id") int albumId, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
      //  void login(@Field("email") String loginId, @Field("password") String password, @Field("device_token") String regId,@Field("device_type") String deviceType ,Callback<JsonElement> callback);
      void login(@Field("email") String loginId, @Field("password") String password, @Field("device_token") String regId, @Field("device_type") String deviceType, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
            //  void login(@Field("email") String loginId, @Field("password") String password, @Field("device_token") String regId,@Field("device_type") String deviceType ,Callback<JsonElement> callback);
        void forgotPassword(@Field("email") String loginId, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
            //  void login(@Field("email") String loginId, @Field("password") String password, @Field("device_token") String regId,@Field("device_type") String deviceType ,Callback<JsonElement> callback);
        void checkLogoutStatus(@Field("page") int page,Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
            //  void login(@Field("email") String loginId, @Field("password") String password, @Field("device_token") String regId,@Field("device_type") String deviceType ,Callback<JsonElement> callback);
        void registerUser(@Field("email") String loginId, @Field("password") String password, @Field("confirm_password") String regId, Callback<JsonElement> callback);




        @FormUrlEncoded
        @POST("/")
        void post(@Field("eventId") String eventId, Callback<JsonElement> callback);


        @FormUrlEncoded
        @POST("/")
        void postWithRegIdAndVal(@Field("regId") String regId, @Field("val") String subscribeVal, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postGroupNotification(@Field("group_id") String subscribeVal, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postNotification(@Field("notification_title") String subject, @Field("notification_message") String msg, @Field("group_id") String grpAray, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postLink(@Field("link_name") String name, @Field("link_url") String url, @Field("group_id") String grpArray, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postAlbum(@Field("album_name") String albumName, @Field("group_id") String grpArray, Callback<JsonElement> callback);

        @GET("/")
        void get(@QueryMap HashMap<String, String> params, Callback<JsonElement> callback);


        @PUT("/")
        void put(@Body Object requestObject, Callback<JsonElement> callback);

        @FormUrlEncoded
        @POST("/")
        void postEvents(@FieldMap Map<String, String> names, Callback<JsonElement> callback);

        @Multipart
        @POST("/")
        void uploadImage(@Part("album_id")String id, @Part("caption_name") String title, @Part("file") TypedFile image, Callback<JsonElement> cb);

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
package com.snaptech.asb.WebService;

import com.snaptech.asb.Utility.AppError;
import com.snaptech.asb.Utility.Constants;
import com.snaptech.asb.WebService.Common.RetrofitImplementation;
import com.snaptech.asb.WebService.Common.WebServiceListener;

import java.util.HashMap;

/**
 * Created by soniya on 12/08/15.
 */
public class LoginService {

    public static void login(HashMap<String, String> params, final GetLoginResponseWebServiceListener getLoginResponseWebServiceListener){

        RetrofitImplementation retrofitImplementation = new RetrofitImplementation();
        retrofitImplementation.executePostWithURL(Constants.LOGIN_API_PATH, params, null,LoginResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                LoginResponse loginResponse = (LoginResponse)response;
                getLoginResponseWebServiceListener.onCompletion(loginResponse, error);
            }
        });
    }

    public static void forgotPassword(HashMap<String, String> params, final GetForgotPasswordWebServiceListener getLoginResponseWebServiceListener){

        RetrofitImplementation retrofitImplementation = new RetrofitImplementation();
        retrofitImplementation.executePostWithURL(Constants.FORGOTPASSWORD_API_PATH, params, null,ForgotPasswordResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                ForgotPasswordResponse loginResponse = (ForgotPasswordResponse) response;
                getLoginResponseWebServiceListener.onCompletion(loginResponse, error);
            }
        });
    }

    public static void registerUser(HashMap<String, String> params, final GetForgotPasswordWebServiceListener getLoginResponseWebServiceListener){

        RetrofitImplementation retrofitImplementation = new RetrofitImplementation();
        retrofitImplementation.executePostWithURL(Constants.REGISTER_API_PATH, params, null,ForgotPasswordResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                ForgotPasswordResponse loginResponse = (ForgotPasswordResponse) response;
                getLoginResponseWebServiceListener.onCompletion(loginResponse, error);
            }
        });
    }


    public static  void loginStatus(final GetLoginResponseWebServiceListener getLoginResponseWebServiceListener){

        RetrofitImplementation retrofitImplementation = new RetrofitImplementation();
        retrofitImplementation.executeGetWithURL(Constants.LOGIN_API_PATH, null, null,LoginResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                LoginResponse loginResponse = (LoginResponse)response;
                getLoginResponseWebServiceListener.onCompletion(loginResponse, error);
            }
        });

    }

    public static void logout(final GetLoginResponseWebServiceListener getLoginResponseWebServiceListener){

//        RetrofitImplementation retrofitImplementation = new RetrofitImplementation();
//        retrofitImplementation.executePostWithURL(Constants.LOGOUT_API_PATH, null, null,PostResponse.class, new WebServiceListener() {
//            @Override
//            public void onCompletion(Object response, AppError error) {
//               // LoginResponse loginResponse = (LoginResponse)response;
//                //getLoginResponseWebServiceListener.onCompletion(loginResponse, error);
//            }
//        });

        RetrofitImplementation retrofitImplementation = new RetrofitImplementation();
        retrofitImplementation.executeGetWithURL(Constants.LOGOUT_API_PATH, null, null,PostResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
               // LoginResponse loginResponse = (LoginResponse)response;
               // getLoginResponseWebServiceListener.onCompletion(loginResponse, error);
            }
        });
    }


    public interface GetLoginResponseWebServiceListener{

        public void onCompletion(LoginResponse loginResponse, AppError error);
    }

    public interface GetForgotPasswordWebServiceListener{

        public void onCompletion(ForgotPasswordResponse loginResponse, AppError error);
    }
}

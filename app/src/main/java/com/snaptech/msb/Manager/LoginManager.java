package com.snaptech.msb.Manager;

import com.snaptech.msb.Utility.AppError;
import com.snaptech.msb.WebService.ForgotPasswordResponse;
import com.snaptech.msb.WebService.LoginResponse;
import com.snaptech.msb.WebService.LoginService;

import java.util.HashMap;

/**
 * Created by soniya on 12/08/15.
 */
public class LoginManager {

    private static LoginManager sharedInstance;
    public static String accessToken;
    public static long expiresIn ;
    public static long loggedInTime;
    private LoginManager(){

    }

    public static LoginManager getSharedInstance(){

        if (sharedInstance == null){
            sharedInstance =  new LoginManager();

        }
        return sharedInstance;
    }

    public void login(HashMap<String,String> params, final LoginManagerListener loginManagerListener){

        LoginService.login(params, new LoginService.GetLoginResponseWebServiceListener() {
            @Override
            public void onCompletion(LoginResponse response, AppError error) {
                loginManagerListener.onCompletion(response, error);
            }
        });
    }

    public void registerUser(HashMap<String,String> params, final ForgotLoginManagerListener loginManagerListener){

        LoginService.registerUser(params, new LoginService.GetForgotPasswordWebServiceListener() {
            @Override
            public void onCompletion(ForgotPasswordResponse response, AppError error) {
                loginManagerListener.onCompletion(response, error);
            }
        });
    }

    public void forgotPassword(HashMap<String,String> params, final ForgotLoginManagerListener loginManagerListener){

        LoginService.forgotPassword(params, new LoginService.GetForgotPasswordWebServiceListener() {
            @Override
            public void onCompletion(ForgotPasswordResponse response, AppError error) {
                loginManagerListener.onCompletion(response, error);
            }
        });
    }


    public void logout(final LoginManagerListener loginManagerListener){

        LoginService.logout(new LoginService.GetLoginResponseWebServiceListener() {
            @Override
            public void onCompletion(LoginResponse response, AppError error) {
               // loginManagerListener.onCompletion(response, error);
            }
        });
    }


    public void loginStatus(final LoginManagerListener loginManagerListener){

        LoginService.loginStatus( new LoginService.GetLoginResponseWebServiceListener() {
            @Override
            public void onCompletion(LoginResponse response, AppError error) {
                loginManagerListener.onCompletion(response, error);
            }
        });
    }


    public interface LoginManagerListener{

        public void onCompletion(LoginResponse response, AppError error);
    }

    public interface ForgotLoginManagerListener{

        public void onCompletion(ForgotPasswordResponse response, AppError error);
    }
}

package com.app.sircle.Manager;

import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.LoginResponse;
import com.app.sircle.WebService.LoginService;

import java.util.HashMap;

/**
 * Created by soniya on 12/08/15.
 */
public class LoginManager {

    private static LoginManager sharedInstance;
    public static String accessToken;
    public long expiresIn;
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


    public interface LoginManagerListener{

        public void onCompletion(LoginResponse response, AppError error);
    }
}

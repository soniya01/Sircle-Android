package com.grayjam.sircle.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.grayjam.sircle.Utility.Constants;
import com.grayjam.sircle.custom.App;

/**
 * Created by mosesafonso on 24/07/16.
 */
public class LogoutManager {

    private static LogoutManager sharedInstance;

    private LogoutManager(){

    }

    public static LogoutManager getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new LogoutManager();
        }
        return sharedInstance;
    }

    public void handleUserLogoutPreferences()
    {
        SharedPreferences loginSharedPrefs = App.getAppContext().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = loginSharedPrefs.edit();
        editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);
        editor.apply();
    }

}

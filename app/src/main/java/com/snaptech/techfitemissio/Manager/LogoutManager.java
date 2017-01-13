package com.snaptech.techfitemissio.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.snaptech.techfitemissio.Utility.Constants;
import com.snaptech.techfitemissio.custom.App;

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

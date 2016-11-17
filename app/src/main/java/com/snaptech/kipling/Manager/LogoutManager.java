package com.snaptech.kipling.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.snaptech.kipling.Utility.Constants;
import com.snaptech.kipling.custom.App;

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
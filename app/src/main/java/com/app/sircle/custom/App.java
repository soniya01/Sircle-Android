package com.app.sircle.custom;

import android.app.Application;
import android.content.Context;

/**
 * Created by mosesafonso on 25/05/16.
 */
public class App extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}

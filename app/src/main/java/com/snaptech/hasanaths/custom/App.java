package com.snaptech.hasanaths.custom;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by mosesafonso on 25/05/16.
 */
public class App extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

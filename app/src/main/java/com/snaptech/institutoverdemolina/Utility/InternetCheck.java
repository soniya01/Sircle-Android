package com.snaptech.institutoverdemolina.Utility;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Aniket on 26-08-2016.
 */
public class InternetCheck {


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
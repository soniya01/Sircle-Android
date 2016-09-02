package com.snaptech.quadeia.custom;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.snaptech.quadeia.Manager.LoginManager;
import com.snaptech.quadeia.Manager.LogoutManager;
import com.snaptech.quadeia.UI.Activity.LoginScreen;
import com.snaptech.quadeia.Utility.AppError;
import com.snaptech.quadeia.Utility.Constants;
import com.snaptech.quadeia.WebService.LogoutStatusResponse;

import java.util.HashMap;

/**
 * Created by mosesafonso on 24/07/16.
 */
public class ApplicationLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private static final String TAG = ApplicationLifecycleHandler.class.getSimpleName();
    private static boolean isInBackground = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Constants.flag=1;
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Constants.flag=1;
        if(isInBackground){
            Log.d(TAG, "app went to foreground");
            isInBackground = false;

            SharedPreferences   loginSharedPreferences = activity.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = loginSharedPreferences.edit();
            String accessToken = loginSharedPreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY,null);

            if (accessToken != null){
                checkUserStatus(activity);
            }



        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int i) {
        if(i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            Log.d(TAG, "app went to background");
            isInBackground = true;
        }
    }

    public void checkUserStatus(final Activity activity)
    {
      final   HashMap object = new HashMap();
        //object.put("regId", Constants.GCM_REG_ID);
        //object.put("groupId", grpIdString);
        object.put("page", "1");


        LoginManager.getSharedInstance().checkUserLogoutStatus(object, new LoginManager.LogoutStatusManagerListener() {
            @Override
            public void onCompletion(LogoutStatusResponse response, AppError error) {

                if (error.getErrorCode() == 0) {
                    // give access to the app features
                    if (response != null){
                        if (response.getStatus() == 200)
                        {


                            if (response.getData().getLogout() ==1)
                            {
                                LoginManager.getSharedInstance().userLogoutforcefully(object, new LoginManager.LogoutStatusManagerListener() {
                                    @Override
                                    public void onCompletion(LogoutStatusResponse response, AppError error) {
                                    }
                                });
                                LogoutManager.getSharedInstance().handleUserLogoutPreferences();
                                Intent loginIntent = new Intent(activity, LoginScreen.class);
                                activity.startActivity(loginIntent);
                                activity.finish();

                            }
                            else
                            {
//                                Intent loginIntent = new Intent(activity, BaseActivity.class);
//                                activity.startActivity(loginIntent);
//                                activity.finish();
                            }

                            // ringProgressDialog.dismiss();
                            // Toast.makeText(ForgotPasswordActivity.this,"Please check your email for a reset link", Toast.LENGTH_SHORT).show();
                            // usernameField.setText("");

                        }else {


                            //   finish();
                            //  ringProgressDialog.dismiss();
                            // usernameField.setText("");

                            //  Toast.makeText(ForgotPasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }else {

                        //  finish();
                        //ringProgressDialog.dismiss();
                        // usernameField.setText("");

                        // Toast.makeText(ForgotPasswordActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                    }
                }else {

                    //  finish();

                    //ringProgressDialog.dismiss();
                    //usernameField.setText("");

                    //Toast.makeText(ForgotPasswordActivity.this, "Check internet connectivity", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
package com.snaptech.tsm.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.snaptech.tsm.R;
import com.snaptech.tsm.Utility.Constants;

import java.util.Date;


public class SplashActivity extends Activity {

    public static final int SPLASH_SCREEN_TIME_OUT = 3000;
    private  Intent loginIntent = null;
    private SharedPreferences loginSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // check if user is already logged in
//        if (checkIfLoggedIn()){
//            intentClass = BaseActivity.class;
//        }else {
//            intentClass =LoginScreen.class;
//        }

        checkIfLoggedIn();

        // add delay to the splash screen

    }


    /**
     * Checks if user is already logged in to the app
     * @return if user is logged in then success else fail
     */
    private void checkIfLoggedIn(){
        loginSharedPreferences = this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String accessToken = loginSharedPreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY,null);

        long loggedIn = 0;
        long expiresIn = 0;

        long lastActivity = new Date().getTime()/1000;
        if (accessToken != null){
          //  loggedIn = loginSharedPreferences.getLong(Constants.LOGIN_LOGGED_IN_PREFS_KEY,0);
          //  expiresIn = loginSharedPreferences.getLong(Constants.LOGIN_EXPIRES_IN_PREFS_KEY, 0);
//            if (((lastActivity) - loggedIn) > expiresIn){
//                editor.putLong(Constants.LOGIN_LOGGED_IN_PREFS_KEY,new Date().getTime()).apply();
//                //editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null).apply();
//                Toast.makeText(this, "Session expired", Toast.LENGTH_SHORT).show();
//                loginIntent = new Intent(this, LoginScreen.class);
//            }
//            else {
                loginIntent = new Intent(this, BaseActivity.class);
            //}
        }else {
            loginIntent = new Intent(this, LoginScreen.class);
        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }
}

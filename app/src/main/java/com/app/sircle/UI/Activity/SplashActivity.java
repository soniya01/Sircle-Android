package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.app.sircle.Manager.LoginManager;
import com.app.sircle.R;
import com.app.sircle.Utility.Constants;

import java.util.Date;


public class SplashActivity extends Activity {

    public static final int SPLASH_SCREEN_TIME_OUT = 3000;
    private  Intent loginIntent = null;


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

        long lastActivity = new Date().getTime();

        if (LoginManager.loggedInTime != 0 ||  LoginManager.expiresIn != 0){
            if (lastActivity - LoginManager.loggedInTime > LoginManager.expiresIn){
                loginIntent = new Intent(this, LoginScreen.class);
            }else {
                loginIntent = new Intent(this, BaseActivity.class);
            }
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

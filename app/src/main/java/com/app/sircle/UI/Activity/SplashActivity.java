package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.app.sircle.R;
import com.app.sircle.Utility.Constants;


public class SplashActivity extends Activity {

    public static final int SPLASH_SCREEN_TIME_OUT = 3000;
    private SharedPreferences loginSharedPreferences;
    private Class intentClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // check if user is already logged in
        if (checkIfLoggedIn()){
            intentClass = BaseActivity.class;
        }else {
            intentClass =LoginScreen.class;
        }

        // add delay to the splash screen
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashActivity.this, intentClass);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }


    /**
     * Checks if user is already logged in to the app
     * @return if user is logged in then success else fail
     */
    private boolean checkIfLoggedIn(){

        loginSharedPreferences = this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        String username = loginSharedPreferences.getString(Constants.LOGIN_USERNAME_PREFS_KEY,"");
        String password = loginSharedPreferences.getString(Constants.LOGIN_PASSWORD_PREFS_KEY,"");

        if (username.equals("") || password.equals("")){
            return false;
        }else {
            return true;
        }

    }

}

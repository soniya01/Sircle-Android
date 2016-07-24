package com.grayjam.sircle.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.Toast;

import com.grayjam.sircle.Manager.LoginManager;
import com.grayjam.sircle.Manager.LogoutManager;
import com.grayjam.sircle.R;
import com.grayjam.sircle.Utility.AppError;
import com.grayjam.sircle.Utility.Constants;
import com.grayjam.sircle.WebService.ForgotPasswordResponse;
import com.grayjam.sircle.WebService.LogoutStatusResponse;

import java.util.Date;
import java.util.HashMap;


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

        if (accessToken != null){
//            loginIntent = new Intent(SplashActivity.this, BaseActivity.class);
//            startActivity(loginIntent);
//            finish();

             checkUserStatus();

            //}
        }else {


            loginIntent = new Intent(this, LoginScreen.class);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(loginIntent);
                    finish();
                }
            }, SPLASH_SCREEN_TIME_OUT);



        }





    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    public void checkUserStatus()
    {
        HashMap object = new HashMap();
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
                                loginIntent = new Intent(SplashActivity.this, LoginScreen.class);

                                LogoutManager.getSharedInstance().handleUserLogoutPreferences();

                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                }, SPLASH_SCREEN_TIME_OUT);


                            }
                            else
                            {
                                loginIntent = new Intent(SplashActivity.this, BaseActivity.class);
                                startActivity(loginIntent);
                                finish();
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

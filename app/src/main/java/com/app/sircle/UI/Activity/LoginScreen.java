package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.GCM.GCMListener;
import com.app.sircle.GCM.RegistrationIntentService;
import com.app.sircle.Manager.LoginManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Model.Notification;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.LoginResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;


public class LoginScreen extends Activity {

    private Button loginButton;
    private EditText passwordEditText;
    private AutoCompleteTextView usernameField;
    private SharedPreferences loginSharedPrefs;
    private Date sessionExpiryDate;
    private LoginResponse loginData;
    private String accessToken;
    ProgressDialog ringProgressDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences.Editor editor;
    // private TextView supportLabel;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      //  supportLabel = (TextView)findViewById(R.id.activity_login_email_address_label);
        passwordEditText = (EditText)findViewById(R.id.activity_login_password_edittext);
        usernameField = (AutoCompleteTextView)findViewById(R.id.activity_login_email_text_view);

        // underlines the email address
        SpannableString content = new SpannableString(getResources().getString(R.string.activity_login_email_address).toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        //supportLabel.setText(content);


        loginButton = (Button)findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Progress Dialog Ring/Loader
                ringProgressDialog = ProgressDialog.show(LoginScreen.this, "", "Signing In", true);

               loginSharedPrefs = LoginScreen.this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                 editor = loginSharedPrefs.edit();
                LoginManager.accessToken = loginSharedPrefs.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(LoginScreen.this);
              //  boolean sentToken = sharedPreferences.getBoolean(Constants.SENT_TOKEN_TO_SERVER, false);
                Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER, null);


                if (Constants.GCM_REG_ID !=  null){
                    ringProgressDialog.dismiss();
                    //Toast.makeText(LoginScreen.this, "User already logged in", Toast.LENGTH_SHORT).show();
                    //Intent homeIntent = new Intent(LoginScreen.this, BaseActivity.class);
                    //startActivity(homeIntent);
                    loginUser();

                }else {
                    // save username and password
                    getGCMToken();
                }
            }
        });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ringProgressDialog.dismiss();
                //mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean(Constants.SENT_TOKEN_TO_SERVER, false);
                Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER,null);
                if (sentToken) {
                    loginUser();


                }else {
                    Toast.makeText(LoginScreen.this, "Some error occurred while registering the app for notification", Toast.LENGTH_SHORT).show();
                }
                //else {
//                        Intent intent_receiver = new Intent(LoginScreen.this, GCMListener.class);
//                        startService(intent_receiver);
//
//                    //Toast.makeText(LoginScreen.this, "Some error occurred while registering the app for notification", Toast.LENGTH_SHORT).show();
//                }
            }
        };
    }

    private void loginUser() {
        HashMap<String, String> loginMap = new HashMap<String, String>();
        loginMap.put("loginId",usernameField.getText().toString());
        loginMap.put("pwd",passwordEditText.getText().toString());
        loginMap.put("regId", Constants.GCM_REG_ID);

        LoginManager.getSharedInstance().login(loginMap, new LoginManager.LoginManagerListener() {
            @Override
            public void onCompletion(LoginResponse response, AppError error) {

                if (error.getErrorCode() == 0) {
                    // give access to the app features
                    if (response != null){
                        if (response.getStatus() == 200)
                        {
                            Intent intent_receiver = new Intent(LoginScreen.this, GCMListener.class);
                            startService(intent_receiver);

                            NotificationManager.grpIds.clear();
                            sessionExpiryDate = new Date();
                            LoginManager.accessToken = response.getUserData().getOauth().getAccessToken();//  //getOauth().getAccessToken();
                            //LoginManager.expiresIn = response.getUserData().getOauth().getExpiresIn();
                            //LoginManager.loggedInTime = new Date().getTime();
                            editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, response.getUserData().getOauth().getAccessToken());
                            editor.putLong(Constants.LOGIN_EXPIRES_IN_PREFS_KEY, response.getUserData().getOauth().getExpiresIn() / 1000);
                            editor.putLong(Constants.LOGIN_LOGGED_IN_PREFS_KEY, new Date().getTime() / 1000);
                            editor.apply();
                            Toast.makeText(LoginScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(LoginScreen.this, SettingsActivity.class);
                            startActivity(homeIntent);

                        }else {
                            ringProgressDialog.dismiss();
                            usernameField.setText("");
                            passwordEditText.setText("");
                            Toast.makeText(LoginScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        ringProgressDialog.dismiss();
                        usernameField.setText("");
                        passwordEditText.setText("");
                        Toast.makeText(LoginScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ringProgressDialog.dismiss();
                    usernameField.setText("");
                    passwordEditText.setText("");
                    Toast.makeText(LoginScreen.this, "Check internet connectivity", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public void getGCMToken(){
        if (checkPlayServices()){
            Intent serviceIntent = new Intent(LoginScreen.this, RegistrationIntentService.class);
            startService(serviceIntent);
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                //Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}

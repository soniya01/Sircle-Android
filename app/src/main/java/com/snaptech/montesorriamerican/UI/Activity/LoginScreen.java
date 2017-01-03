package com.snaptech.montesorriamerican.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.snaptech.montesorriamerican.GCM.GCMListener;
import com.snaptech.montesorriamerican.GCM.RegistrationIntentService;
import com.snaptech.montesorriamerican.Manager.LoginManager;
import com.snaptech.montesorriamerican.Manager.NotificationManager;
import com.snaptech.montesorriamerican.R;
import com.snaptech.montesorriamerican.Utility.AppError;
import com.snaptech.montesorriamerican.Utility.Constants;
import com.snaptech.montesorriamerican.WebService.LoginResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Date;
import java.util.HashMap;


public class LoginScreen extends Activity {

    private Button loginButton;
    private EditText passwordEditText;
    private EditText usernameField;
    private SharedPreferences loginSharedPrefs;
    private Date sessionExpiryDate;
    private LoginResponse loginData;
    private String accessToken;
    ProgressDialog ringProgressDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences.Editor editor;
    TextView signUpTextView,forgotPasswordView;
    // private TextView supportLabel;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      //  supportLabel = (TextView)findViewById(R.id.activity_login_email_address_label);
        passwordEditText = (EditText)findViewById(R.id.activity_login_password_edittext);
        usernameField = (EditText)findViewById(R.id.activity_login_email_text_view);

        signUpTextView = (TextView)findViewById(R.id.signUPText);
        forgotPasswordView = (TextView)findViewById(R.id.forgotPasswordText);

        signUpTextView.setText("¿No tiene una cuenta?, Cree Una.", TextView.BufferType.SPANNABLE);
        Spannable span = (Spannable) signUpTextView.getText();
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#00729C")),23 , 31,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent homeIntent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(homeIntent);

            }
        });


        forgotPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeIntent = new Intent(LoginScreen.this, ForgotPasswordActivity.class);
                startActivity(homeIntent);

            }
        });


        // underlines the email address
       // SpannableString content = new SpannableString(getResources().getString(R.string.activity_login_email_address).toString());
        //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        //supportLabel.setText(content);


        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("Detect", "Enter pressed");
                    ringProgressDialog = ProgressDialog.show(LoginScreen.this, "", "Iniciando Sesión", true);

                    loginSharedPrefs = LoginScreen.this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                    editor = loginSharedPrefs.edit();
                    LoginManager.accessToken = loginSharedPrefs.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(LoginScreen.this);
                    //  boolean sentToken = sharedPreferences.getBoolean(Constants.SENT_TOKEN_TO_SERVER, false);
                    Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER, null);


                    if (Constants.GCM_REG_ID !=  null){

                      //  System.out.println("GCM Token is "+Constants.GCM_REG_ID);
                        //Toast.makeText(LoginScreen.this, "User already logged in", Toast.LENGTH_SHORT).show();
                        //Intent homeIntent = new Intent(LoginScreen.this, BaseActivity.class);
                        //startActivity(homeIntent);
                        loginUser();

                    }else {
                        // save username and password
                        getGCMToken();
                    }
                }
                return false;
            }
        });


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
                  //  ringProgressDialog.dismiss();
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
              //  ringProgressDialog.dismiss();
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


       // loginMap.put("loginId",usernameField.getText().toString());
        //loginMap.put("pwd",passwordEditText.getText().toString());
        //loginMap.put("regId", Constants.GCM_REG_ID);

        loginMap.put("email",usernameField.getText().toString());
        loginMap.put("password",passwordEditText.getText().toString());
        loginMap.put("device_token", Constants.GCM_REG_ID);
        loginMap.put("device_type", "Android");

//        loginMap.put("email",usernameField.getText().toString());
//        loginMap.put("password",passwordEditText.getText().toString());
//        loginMap.put("device_token", Constants.GCM_REG_ID);
      //  loginMap.put("device_type", "android");

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

                            NotificationManager.getSharedInstance().grpIds.clear();
                            sessionExpiryDate = new Date();

                            LoginManager.accessToken = response.getUserData().getAuthToken();

                            editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, response.getUserData().getAuthToken());
                            editor.putString(Constants.LOGIN_LOGGED_IN_USER_TYPE, response.getUserData().getCustomerType());
                            editor.putLong(Constants.LOGIN_EXPIRES_IN_PREFS_KEY, 120000);
                            editor.putLong(Constants.LOGIN_LOGGED_IN_PREFS_KEY, new Date().getTime() / 1000);

//                            editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, response.getUserData().getOauth().getAccessToken());
//                            editor.putString(Constants.LOGIN_LOGGED_IN_USER_TYPE, response.getUserData().getUserType());
//                            editor.putLong(Constants.LOGIN_EXPIRES_IN_PREFS_KEY, response.getUserData().getOauth().getExpiresIn());
//                            editor.putLong(Constants.LOGIN_LOGGED_IN_PREFS_KEY, new Date().getTime() / 1000);


                          //  Toast.makeText(LoginScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                            if (response.getUserData().getCustomerType()!=null) {
                                editor.apply();
                                if (response.getUserData().getCustomerType().equals("admin")) {
                                    Intent homeIntent = new Intent(LoginScreen.this, BaseActivity.class);
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    Intent homeIntent = new Intent(LoginScreen.this, SettingsActivity.class);
                                    startActivity(homeIntent);
                                    finish();
                                }
                            }
                            else
                            {
                                editor.putString(Constants.LOGIN_LOGGED_IN_USER_TYPE, "user");
                                editor.apply();
                                Intent homeIntent = new Intent(LoginScreen.this, SettingsActivity.class);
                                startActivity(homeIntent);
                            }


                        }else {
                            ringProgressDialog.dismiss();
                            usernameField.setText("");
                            passwordEditText.setText("");
                            Toast.makeText(LoginScreen.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        ringProgressDialog.dismiss();
                        usernameField.setText("");
                        passwordEditText.setText("");
                         Toast.makeText(LoginScreen.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ringProgressDialog.dismiss();
                    usernameField.setText("");
                    passwordEditText.setText("");
                    Toast.makeText(LoginScreen.this, "Compruebe internet", Toast.LENGTH_SHORT).show();

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

//    @Override
//    public void onBackPressed() {
//
//    }

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

package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.Manager.LoginManager;
import com.app.sircle.R;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.LoginResponse;

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
    // private TextView supportLabel;

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
                final SharedPreferences.Editor editor = loginSharedPrefs.edit();

                if (LoginManager.accessToken != null){
                    ringProgressDialog.dismiss();
                    Toast.makeText(LoginScreen.this, "User already logged in", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(LoginScreen.this, BaseActivity.class);
                    startActivity(homeIntent);

                }else {
                    // save username and password

                    HashMap<String, String> loginMap = new HashMap<String, String>();
                    loginMap.put("loginId",usernameField.getText().toString());
                    loginMap.put("pwd",passwordEditText.getText().toString());
                    loginMap.put("regId", Constants.GCM_REG_ID);

                    LoginManager.getSharedInstance().login(loginMap, new LoginManager.LoginManagerListener() {
                        @Override
                        public void onCompletion(LoginResponse response, AppError error) {
                            ringProgressDialog.dismiss();
                            if (error.getErrorCode() == 0) {
                                // give access to the app features
                                if (response != null){
                                    if (response.getStatus() == 200)
                                    {
                                        sessionExpiryDate = new Date();
                                        LoginManager.accessToken = response.getUserData().getOauth().getAccessToken();//  //getOauth().getAccessToken();
                                        //editor.putString(Constants.LOGIN_USERNAME_PREFS_KEY, response.getUserData().getOauth().getAccessToken());
                                        //editor.putString(Constants.LOGIN_PASSWORD_PREFS_KEY, passwordEditText.getText().toString());

                                        Toast.makeText(LoginScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                        Intent homeIntent = new Intent(LoginScreen.this, SettingsActivity.class);
                                        startActivity(homeIntent);
                                    }else {
                                        usernameField.setText("");
                                        passwordEditText.setText("");
                                        Toast.makeText(LoginScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    usernameField.setText("");
                                    passwordEditText.setText("");
                                    Toast.makeText(LoginScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                usernameField.setText("");
                                passwordEditText.setText("");
                                Toast.makeText(LoginScreen.this, "Check internet connectivity", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });
    }
}

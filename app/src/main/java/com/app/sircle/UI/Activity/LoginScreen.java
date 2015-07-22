package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.app.sircle.R;
import com.app.sircle.Utility.Constants;


public class LoginScreen extends Activity {

    private Button loginButton;
    private EditText passwordEditText;
    private AutoCompleteTextView usernameField;
    private SharedPreferences loginSharedPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordEditText = (EditText)findViewById(R.id.activity_login_password_edittext);
        usernameField = (AutoCompleteTextView)findViewById(R.id.activity_login_email_text_view);

        loginButton = (Button)findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginSharedPrefs = LoginScreen.this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginSharedPrefs.edit();
                if (usernameField.getText().toString().equals(null) || passwordEditText.getText().toString().equals(null) || usernameField.getText().toString().equals("") || passwordEditText.getText().toString().equals("")){

                }else {

                    // save username and password
                    editor.putString(Constants.LOGIN_USERNAME_PREFS_KEY, usernameField.getText().toString());
                    editor.putString(Constants.LOGIN_PASSWORD_PREFS_KEY, passwordEditText.getText().toString());

                    // give access to the app features
                    Intent homeIntent = new Intent(LoginScreen.this, BaseActivity.class);
                    startActivity(homeIntent);

                }

            }
        });
    }
}

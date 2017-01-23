package com.snaptech.kipling.UI.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.snaptech.kipling.Manager.LoginManager;
import com.snaptech.kipling.R;
import com.snaptech.kipling.Utility.AppError;
import com.snaptech.kipling.Utility.InternetCheck;
import com.snaptech.kipling.WebService.ForgotPasswordResponse;

import java.util.HashMap;

public class SignUpScreen extends AppCompatActivity {

    EditText usernameField,passwordField,confirmPasswordFeild;
    Button loginButton;
    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Regístrarse");

        setContentView(R.layout.activity_sign_up_screen);

        usernameField = (EditText)findViewById(R.id.activity_login_email_text_view);
        passwordField = (EditText)findViewById(R.id.activity_login_password_edittext);
        confirmPasswordFeild = (EditText)findViewById(R.id.confirmPassword);

        confirmPasswordFeild.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    if(InternetCheck.isNetworkConnected(SignUpScreen.this))
                    RegisterUser();
                    else
                        Toast.makeText(SignUpScreen.this,"Compruebe internet",Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        loginButton = (Button)findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(InternetCheck.isNetworkConnected(SignUpScreen.this))
                RegisterUser();
                else
                    Toast.makeText(SignUpScreen.this,"Compruebe internet",Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void RegisterUser()
    {

        ringProgressDialog = ProgressDialog.show(SignUpScreen.this, "", "Registrarse usuario", true);

        HashMap<String, String> loginMap = new HashMap<String, String>();
        loginMap.put("email",usernameField.getText().toString());
        loginMap.put("password",passwordField.getText().toString());
        loginMap.put("confirm_password",confirmPasswordFeild.getText().toString());


        LoginManager.getSharedInstance().registerUser(loginMap, new LoginManager.ForgotLoginManagerListener() {
            @Override
            public void onCompletion(ForgotPasswordResponse response, AppError error) {


                if (error.getErrorCode() == 0) {
                    // give access to the app features
                    if (response != null){
                        if (response.getStatus() == 200)
                        {


                            ringProgressDialog.dismiss();
                           // Toast.makeText(SignUpScreen.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUpScreen.this,"Registro exitoso", Toast.LENGTH_SHORT).show();

                            finish();


                        }else {
                            ringProgressDialog.dismiss();
                            usernameField.setText("");
                            confirmPasswordFeild.setText("");
                            passwordField.setText("");

                            Toast.makeText(SignUpScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        ringProgressDialog.dismiss();
                        usernameField.setText("");
                        confirmPasswordFeild.setText("");
                        passwordField.setText("");
                        Toast.makeText(SignUpScreen.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ringProgressDialog.dismiss();
                    usernameField.setText("");
                    confirmPasswordFeild.setText("");
                    passwordField.setText("");
                    Toast.makeText(SignUpScreen.this, "Compruebe internet", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_links, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view



        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }
}

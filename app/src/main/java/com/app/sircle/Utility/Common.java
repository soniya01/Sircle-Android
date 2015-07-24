package com.app.sircle.Utility;

import android.content.Context;
import android.content.Intent;

import com.app.sircle.R;

/**
 * Created by soniya on 24/07/15.
 */
public class Common {

    public static void sendEmailToSupport(Context context){
        String to = context.getResources().getString(R.string.activity_login_email_address);//toEmail.getText().toString();
        Intent intentEmail = new Intent(Intent.ACTION_SEND);
        intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{ to });
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, " ");
        intentEmail.setType("message/rfc822");
        context.startActivity(Intent.createChooser(intentEmail,
                "Choose an email provider :"));

    }
}

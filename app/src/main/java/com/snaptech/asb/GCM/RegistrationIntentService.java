package com.snaptech.asb.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;


import com.snaptech.asb.R;
import com.snaptech.asb.Utility.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;



/**
 * Created by soniya on 26/10/15.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    String token = "";
    InstanceID instanceID;
    SharedPreferences sharedPreferences;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RegistrationIntentService( ) {
        super(TAG);
    }



    @Override
    protected void onHandleIntent(Intent intent) {


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


            instanceID = InstanceID.getInstance(this);
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.

            new Thread(new Runnable() {
                public void run() {
                    //code
                    try {
                        token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                        // TODO: Implement this method to send any registration to your app's servers.

                        Constants.GCM_REG_ID = token;
                        System.out.println("gcm token is "+Constants.GCM_REG_ID);
                        sharedPreferences.edit().putString(Constants.TOKEN_TO_SERVER, token).apply();
                        sharedPreferences.edit().putBoolean(Constants.SENT_TOKEN_TO_SERVER, true).apply();
                    }catch (Exception e) {
                        System.out.println("exc: "+e);
                        sharedPreferences.edit().putBoolean(Constants.SENT_TOKEN_TO_SERVER, false).apply();
                    }

                    Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
                    LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(registrationComplete);
                }
            }).start();

            // Subscribe to topic channels
            //subscribeTopics(token);
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.

    }

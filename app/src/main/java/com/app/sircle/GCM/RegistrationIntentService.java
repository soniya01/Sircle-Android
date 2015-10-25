package com.app.sircle.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import com.app.sircle.R;
import com.app.sircle.Utility.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by soniya on 26/10/15.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RegistrationIntentService( ) {
        super(TAG);
    }



    @Override
    protected void onHandleIntent(Intent intent) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            // TODO: Implement this method to send any registration to your app's servers.
            Constants.GCM_REG_ID = token;

            // Subscribe to topic channels
            //subscribeTopics(token);

            sharedPreferences.edit().putBoolean(Constants.SENT_TOKEN_TO_SERVER, true).apply();
        } catch (Exception e) {
            sharedPreferences.edit().putBoolean(Constants.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }
}

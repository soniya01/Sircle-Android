package com.snaptech.centroeducativomapi.GCM;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by soniya on 30/10/15.
 */
public class InstanceIDListener extends InstanceIDListenerService {


    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}

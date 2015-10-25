package com.app.sircle.GCM;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddNotificationActivity;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by soniya on 26/10/15.
 */
public class GCMListener extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        String message = data.getString("message");
        Log.d("App", "From: " + from);
        Log.d("App", "Message: " + message);

        sendNotification(message);
    }

    @Override
    public void onMessageSent(String msgId) {
        super.onMessageSent(msgId);
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, AddNotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_voicemail)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify();

        long futureInMillis = SystemClock.elapsedRealtime() + 1000;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}

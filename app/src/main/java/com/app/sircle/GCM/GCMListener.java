package com.app.sircle.GCM;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddNotificationActivity;
import com.app.sircle.UI.Activity.AlbumDetailsActivity;
import com.app.sircle.UI.Activity.BaseActivity;
import com.app.sircle.UI.Activity.EventDetailActivity;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by soniya on 26/10/15.
 */
public class GCMListener extends GcmListenerService {
    private Notification notification;
    int count = 0;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        count++;
        String title = data.getString("title");
        String type = title.split(":")[0];
        String message = data.getString("message");
        String url = data.getString("url");
        Log.d("App", "From: " + from);
        Log.d("App", "Message: " + message);

        sendNotification(data);
    }

    @Override
    public void onMessageSent(String msgId) {
        super.onMessageSent(msgId);
    }

    private void sendNotification(Bundle data) {
        String title = "", eventId = "", url="", albumId, message="";

        Class intentClass = null;
        Intent intent;
        url = data.getString("url");
        title = data.getString("title");
        if (url.equals("notificationsPage")){
            message = data.getString("message");
            title = title + "\n" + message;
            BaseActivity.selectedModuleIndex = 3;
            intentClass = BaseActivity.class;

        }else if(url.equals("eventPage")){
            intentClass = EventDetailActivity.class;
            BaseActivity.selectedModuleIndex = 1;
        }else if(url.equals("albumPage")){
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 1;
        }
        else if(url.equals("albumPage")){
                intentClass = BaseActivity.class;
                BaseActivity.selectedModuleIndex = 5;
            }
        else if (url.equals("PhotoListViewPage")){
                intentClass = AlbumDetailsActivity.class;
                BaseActivity.selectedModuleIndex = 5;
        }

        intent = new Intent(this, intentClass);
        if (url.equals("PhotoListViewPage")){
            albumId = data.getString("albumId");
            intent.putExtra("albumId", albumId);
        }
        if (url.equals("eventPage")){
            try{
                eventId = data.getString("eventId");
                intent.putExtra("eventId", eventId);
            }catch (Exception e){

            }
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                0);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setSmallIcon(android.R.drawable.stat_notify_voicemail)
                    .setContentIntent(pendingIntent)
                    .setContentText(title)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .build();


        }else {
            notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.stat_notify_voicemail)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(title)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentIntent(pendingIntent)
            .build();
        }
        notification.defaults = Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;

        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(count, notification);


//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(android.R.drawable.stat_notify_voicemail)
//                .setContentTitle(getResources().getString(R.string.app_name))
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        android.app.NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify();

//        long futureInMillis = SystemClock.elapsedRealtime() + 1000;
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}

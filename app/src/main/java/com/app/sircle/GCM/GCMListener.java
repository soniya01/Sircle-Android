package com.app.sircle.GCM;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.app.sircle.Manager.LoginManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddNotificationActivity;
import com.app.sircle.UI.Activity.AlbumDetailsActivity;
import com.app.sircle.UI.Activity.BaseActivity;
import com.app.sircle.UI.Activity.EventDetailActivity;
import com.app.sircle.Utility.Constants;
import com.google.android.gms.gcm.GcmListenerService;

import java.util.Set;

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


        SharedPreferences loginSharedPrefs = getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        LoginManager.accessToken = loginSharedPrefs.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        //  boolean sentToken = sharedPreferences.getBoolean(Constants.SENT_TOKEN_TO_SERVER, false);
        Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER, null);
        Set<String> grpIds = sharedPreferences.getStringSet(Constants.GROUP_IDS,null);
        com.app.sircle.Manager.NotificationManager.grpIds.clear();
        com.app.sircle.Manager.NotificationManager.grpIds.addAll(grpIds);


        // Toast.makeText(getBaseContext(), Constants.GCM_REG_ID,Toast.LENGTH_SHORT).show();

        System.out.println("REG"+ Constants.GCM_REG_ID);

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
        }
        else if(url.equals("albumPage")){
                intentClass = BaseActivity.class;
                BaseActivity.selectedModuleIndex = 2;
            }
        else if (url.equals("PhotoListViewPage")){
                intentClass = AlbumDetailsActivity.class;
                BaseActivity.selectedModuleIndex = 2;
        }
        else if (url.equals("documentsPage"))
        {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 5;
        }
        else if (url.equals("newslettersPage"))
        {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 4;
        }
        else if (url.equals("videoPage")) {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 6;
        }
        else if (url.equals("linkPage")) {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 7;
        }

        else {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 1;
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
        notification.defaults =  Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;

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

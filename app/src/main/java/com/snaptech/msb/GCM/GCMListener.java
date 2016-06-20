package com.snaptech.msb.GCM;

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
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.snaptech.msb.Manager.LoginManager;
import com.snaptech.msb.R;
import com.snaptech.msb.UI.Activity.AlbumDetailsActivity;
import com.snaptech.msb.UI.Activity.BaseActivity;
import com.snaptech.msb.UI.Activity.EventDetailActivity;
import com.snaptech.msb.Utility.Constants;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

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

        //Bundle[{payload={"id":23,"other_id":23,"type":"notification"}, title=NOTIFICATION, message=NOTIFICATION: Well, collapse_key=do_not_collapse}]

        String title = data.getString("title");
        String type = data.getString("payload");
        String message = data.getString("message");




       // String url = data.getString("url");


        Log.d("App", "From: " + from);
        Log.d("App", "Message: " + message);

//        String jsonStr = data.getString("payload");
//        JSONObject json =
//
//

      //  String json = {"phonetype":"N95","cat":"WP"};

        try {

            JSONObject obj = new JSONObject(type);

            Log.d("My App", obj.getString("type"));



        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + "\"");
        }


        sendNotification(data);
    }

    @Override
    public void onMessageSent(String msgId) {
        super.onMessageSent(msgId);
    }

    private void sendNotification(Bundle data) {
        String title = "", eventId = "", url = "", Id = "", message = "";


        SharedPreferences loginSharedPrefs = getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        LoginManager.accessToken = loginSharedPrefs.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        //  boolean sentToken = sharedPreferences.getBoolean(Constants.SENT_TOKEN_TO_SERVER, false);
        Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER, null);
       // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(GCMListener.this);
//        Set<String> grpIds = sharedPreferences.getStringSet(Constants.GROUP_IDS,null);
//        com.snaptech.msb.Manager.NotificationManager.grpIds.clear();
//        com.snaptech.msb.Manager.NotificationManager.grpIds.addAll(grpIds);


        // Toast.makeText(getBaseContext(), Constants.GCM_REG_ID,Toast.LENGTH_SHORT).show();

        System.out.println("REG"+ Constants.GCM_REG_ID);


//        String title = data.getString("title");
         String type = data.getString("payload");
//        String message = data.getString("message");


        try {

            JSONObject obj = new JSONObject(type);

            Log.d("My App",obj.getString("type") );

            url = obj.getString("type");
            Id = obj.getString("id");

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + "\"");
        }

        Class intentClass = null;
        Intent intent;

//        Toast.makeText(getBaseContext(), title + "\n" + message,Toast.LENGTH_SHORT).show();
        System.out.println("message: "+ title + "\n" + message);
        title = data.getString("title");
        message = data.getString("message");

        if (url.equals("notification")){

           // title = title + "\n" + message;
            BaseActivity.selectedModuleIndex = 3;
            intentClass = BaseActivity.class;

        }else if(url.equals("calendar")){
            intentClass = EventDetailActivity.class;
            BaseActivity.selectedModuleIndex = 1;
        }
        else if(url.equals("album")){
                intentClass = AlbumDetailsActivity.class;
               // BaseActivity.selectedModuleIndex = 2;
            }
        else if (url.equals("PhotoListViewPage")){
                intentClass = AlbumDetailsActivity.class;
                BaseActivity.selectedModuleIndex = 2;
        }
        else if (url.equals("document"))
        {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 5;
        }
        else if (url.equals("newspaper"))
        {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 4;
        }
        else if (url.equals("video")) {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 6;
        }
        else if (url.equals("link")) {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 7;
        }

        else {
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 1;
        }

        intent = new Intent(this, intentClass);
        intent.putExtra("notificationActivity", BaseActivity.selectedModuleIndex);
        intent.putExtra("albumId", Id);
        intent.putExtra("eventId", Id);

        if (url.equals("PhotoListViewPage")){
//            albumId = data.getString("albumId");
//            intent.putExtra("albumId", albumId);
        }
        if (url.equals("eventPage")){
            try{
                eventId = data.getString("eventId");
                intent.putExtra("eventId", eventId);
            }catch (Exception e){

            }
        }

        //System.out.println("extras: "+ eventId + "\n" + albumId);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setSmallIcon(R.drawable.emissionsloginlogo)
                    .setContentIntent(pendingIntent)
                    .setContentText(title)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .build();


        }else {
            notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.emissionsloginlogo)
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

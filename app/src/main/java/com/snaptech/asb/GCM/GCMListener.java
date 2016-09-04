package com.snaptech.asb.GCM;

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

import com.snaptech.asb.Manager.LoginManager;
import com.snaptech.asb.R;
import com.snaptech.asb.UI.Activity.AlbumDetailsActivity;
import com.snaptech.asb.UI.Activity.BaseActivity;
import com.snaptech.asb.UI.Activity.EventDetailActivity;
import com.snaptech.asb.Utility.Constants;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONObject;

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


        SharedPreferences   loginSharedPreferences = this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
       // SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String accessToken = loginSharedPreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY,null);

        //Bundle[{payload={"id":23,"other_id":23,"type":"notification"}, title=NOTIFICATION, message=NOTIFICATION: Well, collapse_key=do_not_collapse}]

     //   String title = data.getString("title");
      //  String type = data.getString("payload");
      //  String message = data.getString("message");


        //Log.d("App", "From: " + from);
       // Log.d("App", "Message: " + message);


//        try {
//
//            JSONObject obj = new JSONObject(type);
//
//            Log.d("My App", obj.getString("type"));
//
//
//
//        } catch (Throwable t) {
//            Log.e("My App", "Could not parse malformed JSON: \"" + "\"");
//        }

if (accessToken!=null) {
    sendNotification(data);
    System.out.println("GCM Response "+data.toString());
}
    }

    @Override
    public void onMessageSent(String msgId) {
        super.onMessageSent(msgId);
    }

    private void sendNotification(Bundle data) {
        String title = "", eventId = "", url = "", Id = "", message = "";

//Bundle[{google.sent_time=1472639437037, collapse_key=do_not_collapse, default={"default":"default","GCM":{"data":{"notifications":[{"id":235,"other_id":235,"type":"notification","title":"aws","message":"aws msg"}]}}}, google.message_id=0:1472639437042005%882aec69f9fd7ecd}]

        String data2=data.getString("default");
        try{
            JSONObject jsonObject=new JSONObject(data2);
            JSONObject gcmObject=jsonObject.getJSONObject("GCM");
            JSONObject dataObject=gcmObject.getJSONObject("data");
            JSONArray notifications=dataObject.getJSONArray("notifications");
            JSONObject actualdata=notifications.getJSONObject(0);
            title=actualdata.getString("title");

            System.out.println("title is "+title);

        }catch (Exception e){
            e.printStackTrace();
        }
        SharedPreferences loginSharedPrefs = getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        LoginManager.accessToken = loginSharedPrefs.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        //  boolean sentToken = sharedPreferences.getBoolean(Constants.SENT_TOKEN_TO_SERVER, false);
        Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER, null);

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
            System.out.println("Type received of GCM is "+url);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + "\"");
        }

        Class intentClass = null;
        Intent intent;
        String actionBarTitle="";

//        Toast.makeText(getBaseContext(), title + "\n" + message,Toast.LENGTH_SHORT).show();
        System.out.println("message: "+ title + "\n" + message);
       // title = data.getString("title");
        message = data.getString("message");

        if (url.equals("notification")){

           // title = title + "\n" + message;

            BaseActivity.selectedModuleIndex = 3;
            intentClass = BaseActivity.class;
            actionBarTitle="Messages";

        }else if(url.equals("calendar")){
            actionBarTitle="Calendar";
            intentClass = EventDetailActivity.class;
            BaseActivity.selectedModuleIndex = 1;
        }
        else if(url.equals("album")){
            actionBarTitle="Photos";
                intentClass = AlbumDetailsActivity.class;
               // BaseActivity.selectedModuleIndex = 2;
            }
        else if (url.equals("PhotoListViewPage")){
                actionBarTitle="Photos";
                intentClass = AlbumDetailsActivity.class;
                BaseActivity.selectedModuleIndex = 2;
        }
        else if (url.equals("document"))
        {
            actionBarTitle="Documents";
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 5;
        }
        else if (url.equals("newsletter"))
        {
            actionBarTitle="Newsletters";
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 4;
        }
        else if (url.equals("video")) {
            actionBarTitle="Videos";
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 6;
        }
        else if (url.equals("link")) {
            actionBarTitle="Links";
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 7;
        }

        else {
            actionBarTitle="Calendar";
            intentClass = BaseActivity.class;
            BaseActivity.selectedModuleIndex = 1;
        }

        intent = new Intent(this, intentClass);
        intent.putExtra("title",actionBarTitle);
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

        System.out.println("Title to be set is "+title);
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

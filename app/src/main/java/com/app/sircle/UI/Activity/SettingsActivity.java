package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;

import com.app.sircle.UI.Model.Notification;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.PostResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends Activity {

    private ListView notificationListView;
    private NotificationsGroupAdapter notificationsGroupAdapter;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private CheckBox allCheckBox;
    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationListView = (ListView) findViewById(R.id.notificationsGroupListView);
        allCheckBox = (CheckBox) findViewById(R.id.checkAll);
        populateDummyData();

        //notificationListView.setAdapter(notificationsGroupAdapter);
        Button saveButton = (Button)findViewById(R.id.saveGroups);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringProgressDialog = ProgressDialog.show(SettingsActivity.this, "", "", true);
                JSONArray arrayObject = new JSONArray();
                for (int i =0 ; i< notificationGroupList.size(); i++){
                    try {
                        JSONObject object = new JSONObject();
                        object.put("group_id",notificationGroupList.get(i).getId());
                        object.put("val", notificationGroupList.get(i).getActive());

                        arrayObject.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                HashMap map = new HashMap();
                map.put("regId", Constants.GCM_REG_ID);
                map.put("groupValString", arrayObject.toString());


                NotificationManager.getSharedInstance().updateGroupNotification(map, new NotificationManager.PostManagerListener() {
                    @Override
                    public void onCompletion(PostResponse postResponse, AppError error) {
                        ringProgressDialog.dismiss();
                        if (postResponse != null) {
                            Toast.makeText(SettingsActivity.this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            if (postResponse.getStatus() == 200) {
                                Intent homeIntent = new Intent(SettingsActivity.this, BaseActivity.class);
                                startActivity(homeIntent);
                                //finish();
                            }
                        } else {
                            Toast.makeText(SettingsActivity.this, "some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                    // give access to the app features

                }

            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void populateDummyData(){

        HashMap<String, String> map = new HashMap<>();
        map.put("regId", Constants.GCM_REG_ID);
        NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {

                        if (SettingsActivity.this.notificationGroupList.size() > 0) {
                            SettingsActivity.this.notificationGroupList.clear();
                            SettingsActivity.this.notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter.notifyDataSetChanged();
                            // update group notifictaion for all groups
                            //updateAllGroup();

                        } else {
                            //SettingsActivity.this.notificationGroupList.clear();
                            SettingsActivity.this.notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter = new NotificationsGroupAdapter(SettingsActivity.this, notificationGroupList);
                            notificationListView.setAdapter(notificationsGroupAdapter);
                        }

                        Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(SettingsActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}

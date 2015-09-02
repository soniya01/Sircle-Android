package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;

import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.GroupResponse;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Activity {

    private ListView notificationListView;
    private NotificationsGroupAdapter notificationsGroupAdapter;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationListView = (ListView) findViewById(R.id.notificationsGroupListView);
        populateDummyData();
        //notificationsGroupAdapter = new NotificationsGroupAdapter(this, notificationGroupList);

        //notificationListView.setAdapter(notificationsGroupAdapter);
        Button saveButton = (Button)findViewById(R.id.saveGroups);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    // give access to the app features
                    Intent homeIntent = new Intent(SettingsActivity.this, BaseActivity.class);
                    startActivity(homeIntent);
                finish();

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

        NotificationManager.getSharedInstance().getAllGroups(new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR ){
                    if (response.getData() != null ){

                        if (SettingsActivity.this.notificationGroupList.size() > 0){
                            SettingsActivity.this.notificationGroupList.clear();
                            SettingsActivity.this.notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter.notifyDataSetChanged();

                        }else {
                            SettingsActivity.this.notificationGroupList.clear();
                            SettingsActivity.this.notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter = new NotificationsGroupAdapter(SettingsActivity.this, notificationGroupList);
                            notificationListView.setAdapter(notificationsGroupAdapter);
                        }

                        Toast.makeText(SettingsActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SettingsActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(SettingsActivity.this, "Sorry some problem occurred",Toast.LENGTH_SHORT).show();
                }

            }
        });
//
//        NotificationGroups n1 = new NotificationGroups();
//        n1.setName("Group 1");
//
//        notificationGroupList.add(n1);
//        notificationGroupList.add(n1);
//        notificationGroupList.add(n1);
//        notificationGroupList.add(n1);
//        notificationGroupList.add(n1);
//        notificationGroupList.add(n1);
//        notificationGroupList.add(n1);
//        notificationGroupList.add(n1);


    }

    @Override
    public void onBackPressed() {

    }
}

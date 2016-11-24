package com.snaptech.hasanaths.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.hasanaths.Manager.NotificationManager;
import com.snaptech.hasanaths.R;
import com.snaptech.hasanaths.UI.Adapter.NotificationsGroupAdapter;

import com.snaptech.hasanaths.UI.Model.NotificationGroups;
import com.snaptech.hasanaths.Utility.AppError;
import com.snaptech.hasanaths.WebService.GroupResponse;
import com.snaptech.hasanaths.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    String grpIdString = "";
    private ListView notificationListView;
    private NotificationsGroupAdapter notificationsGroupAdapter;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    public static CheckBox allCheckBox;
    ProgressDialog ringProgressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        NotificationManager.grpIds.clear();
        //isAllChecked = true;
       // Constants.isAllChecked = -1;
        notificationListView = (ListView) findViewById(R.id.notificationsGroupListView);
        allCheckBox = (CheckBox) findViewById(R.id.checkAll);
        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // Constants.isAllChecked = 1;
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                       // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.TRUE);
                    }
                } else {
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.FALSE);
                    }
                    //notificationGroupList.get(i).setActive(1);
                   // Constants.isAllChecked = 0;
                }
               // NotificationManager.grpIds.clear();
                notificationsGroupAdapter.notifyDataSetChanged();
            }
        });
        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new NotificationsGroupAdapter(SettingsActivity.this, notificationGroupList);
        notificationListView.setAdapter(notificationsGroupAdapter);
        //populateDummyData();
        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setOnRefreshListener(this);

        if (notificationGroupList.size() <= 0){
            System.out.println("");
           populateDummyData();
        }

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
//
//                                        populateDummyData();
//                                    }
//                                }
//        );

        //notificationListView.setAdapter(notificationsGroupAdapter);
        Button saveButton = (Button)findViewById(R.id.saveGroups);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = 0; i < notificationGroupList.size(); i++) {
                    // listData[i] = listView.getAdapter().getItem(i).toString();
                    // notificationGroupList.get(i).setActive(1);
                    if (notificationGroupList.get(i).getActive()==Boolean.TRUE)
                    {
                        NotificationManager.grpIds.add( notificationGroupList.get(i).getId());
                    }
                }

              //  JSONArray arrayObject = new JSONArray();




                if ( NotificationManager.grpIds.size() > 0){
                    ringProgressDialog = ProgressDialog.show(SettingsActivity.this, "", "", true);
                    for (int i =0 ; i<  NotificationManager.grpIds.size(); i++){
//                        try {
//                            JSONObject object = new JSONObject();
//                            object.put("group_id",NotificationManager.grpIds.get(i));
//                            object.put("val", 1);
//
//                            arrayObject.put(object);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        if (grpIdString.equals(""))
                        {
                            grpIdString = NotificationManager.grpIds.get(i) ;
                        }
                        else {
                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i);
                        }
                    }

                    System.out.println("Group id string is "+grpIdString);
                    HashMap map = new HashMap();
                  //  map.put("regId", Constants.GCM_REG_ID);
                    map.put("group_id", grpIdString);

                    NotificationManager.getSharedInstance().updateGroupNotification(map, new NotificationManager.PostManagerListener() {
                        @Override
                        public void onCompletion(PostResponse postResponse, AppError error) {
                            ringProgressDialog.dismiss();
                            if (postResponse != null) {
                                //Toast.makeText(SettingsActivity.this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (postResponse.getStatus() == 200) {

                                    System.out.println("Response is "+postResponse.getMessage()+" and grpid sent list is "+grpIdString);
                                    String grpIdString = "";
                                    for (int i = 0; i< NotificationManager.grpIds.size(); i++){
                                        if (i == 0){
                                            grpIdString = NotificationManager.grpIds.get(i);
                                        }else {
                                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
                                        }
                                    }

                                  //  NotificationManager.getSharedInstance().saveGroupIds(grpIdString,SettingsActivity.this);

                                    Intent homeIntent = new Intent(SettingsActivity.this, BaseActivity.class);
                                    startActivity(homeIntent);
                                    finish();
                                }
                            } else {
                              //  Toast.makeText(SettingsActivity.this, "some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(SettingsActivity.this, "Please select at least one group", Toast.LENGTH_SHORT).show();
                }
                     // give access to the app features

            }

        });

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view



        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
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

        ringProgressDialog = ProgressDialog.show(SettingsActivity.this, "", "", true);
        HashMap<String, String> map = new HashMap<>();
        //map.put("regId", Constants.GCM_REG_ID);
        NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {
                ringProgressDialog.dismiss();
                //swipeRefreshLayout.setRefreshing(false);
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {
                        if (response.getStatus() == 200){
                            if (response.getData().getGroups().size() > 0){

                                SettingsActivity.this.notificationGroupList.addAll(NotificationManager.groupList);
                                notificationsGroupAdapter.notifyDataSetChanged();
                            }
                        //    Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                          //  Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SettingsActivity.this,"some error occurred",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //Toast.makeText(SettingsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onRefresh() {
        populateDummyData();
    }
}

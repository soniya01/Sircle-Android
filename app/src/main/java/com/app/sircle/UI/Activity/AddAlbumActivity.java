package com.app.sircle.UI.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.AddAlbumResponse;
import com.app.sircle.WebService.GroupResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAlbumActivity extends ActionBarActivity {

    private EditText title;
    private View footerView;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private Button addButton;
    ArrayAdapter<String> arrayAdapter;
    private NotificationsGroupAdapter notificationsGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        addListView = (ListView) findViewById(R.id.activity_add_group_list_view);
        title = (EditText) findViewById(R.id.activity_add_alnum_name_edit_text);

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button) footerView.findViewById(R.id.add_button);
        addButton.setText("Add Album");
        addListView.addFooterView(footerView);

        populateDummyData();

        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String grpIdString = "";
                for (int i = 0; i< NotificationManager.grpIds.size(); i++){
                    if (i == 0){
                        grpIdString = NotificationManager.grpIds.get(i);
                    }else {
                        grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
                    }
                }

                HashMap params = new HashMap();
                params.put("albumName",title.getText().toString());
                params.put("grp",grpIdString);
                PhotoManager.getSharedInstance().addNewAlbum(params, new PhotoManager.AddPhotoManagerListener() {
                    @Override
                    public void onCompletion(AddAlbumResponse addAlbumResponse, AppError error) {
                        if (addAlbumResponse != null) {
                            if (addAlbumResponse.getStatus() == 200) {

                                Intent tabIntent = new Intent(AddAlbumActivity.this, AddPhotoTabbedActivity.class);
                                tabIntent.putExtra("albumId", addAlbumResponse.getData().getAlbumId());
                                startActivity(tabIntent);
                            } else {
                                Toast.makeText(AddAlbumActivity.this, addAlbumResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddAlbumActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

               // finish();
            }
        });
    }

    public void populateDummyData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("regId", Constants.GCM_REG_ID);
        NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {

                        if (AddAlbumActivity.this.notificationGroupList.size() > 0) {
                            AddAlbumActivity.this.notificationGroupList.clear();
                            AddAlbumActivity.this.notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter.notifyDataSetChanged();
                            // update group notifictaion for all groups
                            //updateAllGroup();

                        } else {
                            //SettingsActivity.this.notificationGroupList.clear();
                            AddAlbumActivity.this.notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter = new NotificationsGroupAdapter(AddAlbumActivity.this, notificationGroupList);
                            addListView.setAdapter(notificationsGroupAdapter);

                        }

                        Toast.makeText(AddAlbumActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(SettingsActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddAlbumActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public List<String> getGroupNames() {
        for (NotificationGroups group : this.notificationGroupList){
            groupNames.add(group.getName());
        }
        return groupNames;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseActivity.jumpToFragment){
            String grpIdString = "";
            for (int i = 0; i< NotificationManager.grpIds.size(); i++){
                if (i == 0){
                    grpIdString = NotificationManager.grpIds.get(i);
                }else {
                    grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
                }
            }
            HashMap params = new HashMap();
            params.put("albumName",title.getText().toString());
            params.put("grp",grpIdString);
            PhotoManager.getSharedInstance().addNewAlbum(params, new PhotoManager.AddPhotoManagerListener() {
                @Override
                public void onCompletion(AddAlbumResponse response, AppError error) {
                    if (response != null) {
                        if (response.getStatus() == 200) {
                            Toast.makeText(AddAlbumActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(AddAlbumActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
}}

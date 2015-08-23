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
import com.app.sircle.R;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;

import java.util.ArrayList;
import java.util.List;

public class AddAlbumActivity extends ActionBarActivity {

    private EditText title;
    private View footerView;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private Button addButton;

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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, groupNames);
        addListView.setAdapter(arrayAdapter);
        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tabIntent = new Intent(AddAlbumActivity.this, AddPhotoTabbedActivity.class);
                startActivity(tabIntent);
               // finish();
            }
        });
    }

    public void populateDummyData() {
        NotificationManager.getSharedInstance().getAllGroups(new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(List<NotificationGroups> notificationGroupsList, AppError error) {
                if (error == null || error.getErrorCode() == AppError.NO_ERROR){
                    if (notificationGroupsList != null) {
                        if (notificationGroupsList.size() > 0) {
                            AddAlbumActivity.this.notificationGroupList.clear();
                            AddAlbumActivity.this.notificationGroupList.addAll(notificationGroupsList);
                            getGroupNames();
                        }
                    }
                }else {
                    Toast.makeText(AddAlbumActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        NotificationGroups n1 = new NotificationGroups();
        n1.setName("Group 1");

        notificationGroupList.add(n1);

        groupNames.add("All");
        groupNames.add(notificationGroupList.get(0).getName());
        groupNames.add("Group 2");
        groupNames.add(notificationGroupList.get(0).getName());
        groupNames.add(notificationGroupList.get(0).getName());
        groupNames.add(notificationGroupList.get(0).getName());
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
        if (BaseActivity.jumpToFragment)
            finish();

    }
}

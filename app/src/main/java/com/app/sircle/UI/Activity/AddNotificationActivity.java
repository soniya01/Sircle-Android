package com.app.sircle.UI.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.R;
import com.app.sircle.UI.Fragment.NotificationFragment;
import com.app.sircle.UI.Model.Notification;
import com.app.sircle.UI.Model.NotificationGroups;

import java.util.ArrayList;
import java.util.List;

public class AddNotificationActivity extends ActionBarActivity {

    private EditText title, desc;
    private View footerView;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private Button addButton;
    private TextView descCountLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        addListView = (ListView)findViewById(R.id.activity_add_group_list_view);
        title = (EditText)findViewById(R.id.activity_add_notification_title_edit_text);
        desc = (EditText)findViewById(R.id.activity_add_notification_desc_edit_text);
        descCountLabel = (TextView)findViewById(R.id.activity_add_desc_count);

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button)footerView.findViewById(R.id.add_button);
        addButton.setText("Add Notification");
        addListView.addFooterView(footerView);

        populateDummyData();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, groupNames);
        addListView.setAdapter(arrayAdapter);
        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!desc.getText().toString().trim().equals("") && (title.getText().toString() != null) || !title.getText().toString().trim().equals("")){
                    Toast.makeText(AddNotificationActivity.this, "Added new notification", Toast.LENGTH_SHORT).show();

                    Notification notification = new Notification();
                    notification.setAnnouncementTitle(title.getText().toString());
                    notification.setAnnouncementDesc(desc.getText().toString());
                    NotificationFragment.notificationList.add(notification);
                    finish();
                }else {
                    desc.setText("");
                    Toast.makeText(AddNotificationActivity.this, "Please enter description", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void populateDummyData(){

        NotificationGroups n1 = new NotificationGroups();
        n1.setGroupName("Group 1");

        notificationGroupList.add(n1);

        groupNames.add("All");
        groupNames.add(notificationGroupList.get(0).getGroupName());
        groupNames.add("Group 2");
        groupNames.add(notificationGroupList.get(0).getGroupName());
        groupNames.add(notificationGroupList.get(0).getGroupName());
        groupNames.add(notificationGroupList.get(0).getGroupName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_notification, menu);
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
        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NewsLettersViewAdapter;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Adapter.VideoListViewAdapter;
import com.app.sircle.UI.Model.NewsLetter;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.UI.Model.Video;
import com.app.sircle.Utility.Constants;

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

        populateDummyData();

        notificationListView = (ListView) findViewById(R.id.notificationsGroupListView);
        notificationsGroupAdapter = new NotificationsGroupAdapter(this, notificationGroupList);

        notificationListView.setAdapter(notificationsGroupAdapter);
        Button saveButton = (Button)findViewById(R.id.saveGroups);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    // give access to the app features
                    Intent homeIntent = new Intent(SettingsActivity.this, BaseActivity.class);
                    startActivity(homeIntent);

                }

            });




//        newsLetterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                NewsLetter selectedItem = newsLetterList.get(position);
//
//                Toast.makeText(getActivity(), "File downloaded " + selectedItem.pdfUrl, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), PDFViewer.class);
//                intent.putExtra("PdfUrl", selectedItem.pdfUrl);
//                startActivity(intent);
//            }
//        });

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

    public void populateDummyData(){

        NotificationGroups n1 = new NotificationGroups();
        n1.setGroupName("Group 1");

        notificationGroupList.add(n1);
        notificationGroupList.add(n1);
        notificationGroupList.add(n1);
        notificationGroupList.add(n1);
        notificationGroupList.add(n1);
        notificationGroupList.add(n1);
        notificationGroupList.add(n1);
        notificationGroupList.add(n1);


    }
}

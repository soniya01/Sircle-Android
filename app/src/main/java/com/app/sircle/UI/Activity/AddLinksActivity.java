package com.app.sircle.UI.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.LinksManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Fragment.LinksFragment;
import com.app.sircle.UI.Model.Links;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.LinksResponse;
import com.app.sircle.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddLinksActivity extends ActionBarActivity {

    private EditText title, desc;
    private View footerView;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private Button addButton;
    private NotificationsGroupAdapter notificationsGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_links);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        addListView = (ListView) findViewById(R.id.activity_add_group_list_view);
        title = (EditText) findViewById(R.id.activity_add_link_title_edit_text);
        desc = (EditText) findViewById(R.id.activity_add_link_url_edit_text);

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button) footerView.findViewById(R.id.add_button);
        addListView.addFooterView(footerView);

       if (notificationGroupList.size() <= 0){
           populateDummyData();
       }

        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new NotificationsGroupAdapter(AddLinksActivity.this, notificationGroupList);
        addListView.setAdapter(notificationsGroupAdapter);

        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (URLUtil.isValidUrl(desc.getText().toString()) && (title.getText().toString() != null) || !title.getText().toString().trim().equals("")) {

                    String grpIdString = "";
                    for (int i = 0; i< NotificationManager.grpIds.size(); i++){
                        if (i == 0){
                            grpIdString = NotificationManager.grpIds.get(i);
                        }else {
                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
                        }
                    }

                    HashMap params = new HashMap();
                    params.put("name", title.getText().toString());
                    params.put("url", desc.getText().toString());
                    params.put("grp", grpIdString);
                    LinksManager.getSharedInstance().addLinks(params, new LinksManager.AddLinksManagerListener() {
                        @Override
                        public void onCompletion(PostResponse response, AppError error) {
                            if (response != null) {
                                if (response.getStatus() == 200) {
                                    Toast.makeText(AddLinksActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddLinksActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    finish();
                } else {
                    desc.setText("");
                    Toast.makeText(AddLinksActivity.this, "Please enter valid url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_links, menu);
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
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateDummyData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("regId", Constants.GCM_REG_ID);
        NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {

                        if (response.getData().size() > 0){
                            AddLinksActivity.this.notificationGroupList.addAll(NotificationManager.groupList);
                            notificationsGroupAdapter.notifyDataSetChanged();
                        }

//                        if (AddLinksActivity.this.notificationGroupList.size() > 0) {
//                            AddLinksActivity.this.notificationGroupList.clear();
//                            AddLinksActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter.notifyDataSetChanged();
//                            // update group notifictaion for all groups
//                            //updateAllGroup();
//
//                        } else {
//                            //SettingsActivity.this.notificationGroupList.clear();
//                            AddLinksActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter = new NotificationsGroupAdapter(AddLinksActivity.this, notificationGroupList);
//                            addListView.setAdapter(notificationsGroupAdapter);
//                        }

                        Toast.makeText(AddLinksActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddLinksActivity.this, "Some problem occurred",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddLinksActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

package com.app.sircle.UI.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Fragment.NotificationFragment;
import com.app.sircle.UI.Model.Notification;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddNotificationActivity extends ActionBarActivity {

    private EditText title, desc;
    private View footerView;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private Button addButton;
    private TextView descCountLabel;
    private NotificationsGroupAdapter notificationsGroupAdapter;
    public static CheckBox allCheckBox;
   // public static int isAllChecked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Constants.isAllChecked = -1;

        addListView = (ListView)findViewById(R.id.activity_add_group_list_view);
        allCheckBox = (CheckBox) findViewById(R.id.checkAll);
        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Constants.isAllChecked = 1;
                } else {
                    Constants.isAllChecked = 0;
                }
                NotificationManager.grpIds.clear();
                notificationsGroupAdapter.notifyDataSetChanged();
            }
        });
        setListViewHeightBasedOnChildren(addListView);
        title = (EditText)findViewById(R.id.activity_add_notification_title_edit_text);
        desc = (EditText)findViewById(R.id.activity_add_notification_desc_edit_text);
        descCountLabel = (TextView)findViewById(R.id.activity_add_desc_count);

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button)footerView.findViewById(R.id.add_button);
        addButton.setText("Add Notification");
        addListView.addFooterView(footerView);


        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new NotificationsGroupAdapter(AddNotificationActivity.this, notificationGroupList);
        addListView.setAdapter(notificationsGroupAdapter);
        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (notificationGroupList.size() <= 0){
            populateDummyData();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressBar progressBar = new ProgressBar(AddNotificationActivity.this,null,android.R.attr.progressBarStyleLarge);
                progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                ((LinearLayout)v.getParent().getParent().getParent()).addView(progressBar, layoutParams);
                if (!desc.getText().toString().trim().equals("") && (title.getText().toString() != null) || !title.getText().toString().trim().equals("")){
//                    String grpIdString = "";
//                    for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//                        if (i == 0){
//                            grpIdString = NotificationManager.grpIds.get(i);
//                        }else {
//                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//                        }
//                    }

                    String grpIdString = NotificationManager.getSharedInstance().getGroupIds(AddNotificationActivity.this);

                    HashMap params = new HashMap();
                    params.put("subject",title.getText().toString());
                    params.put("msg",desc.getText().toString());
                    params.put("grp",grpIdString);
                    // add notification api call
                    NotificationManager.getSharedInstance().addNotification(params, new NotificationManager.PostManagerListener() {
                        @Override
                        public void onCompletion(PostResponse postResponse, AppError error) {
                            progressBar.setVisibility(View.GONE);
                            if (postResponse != null) {
                                if (postResponse.getStatus() == 200) {
                                    Toast.makeText(AddNotificationActivity.this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddNotificationActivity.this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddNotificationActivity.this, "Check internet connectivity", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    desc.setText("");
                    Toast.makeText(AddNotificationActivity.this, "Please enter description", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void populateDummyData(){

        HashMap<String, String> map = new HashMap<>();
        map.put("regId", Constants.GCM_REG_ID);
        NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {

                        if (response.getData().size() > 0){
                            AddNotificationActivity.this.notificationGroupList.addAll(NotificationManager.groupList);
                            notificationsGroupAdapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(addListView);
                        }

//                        if (AddNotificationActivity.this.notificationGroupList.size() > 0) {
//                            AddNotificationActivity.this.notificationGroupList.clear();
//                            AddNotificationActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter.notifyDataSetChanged();
//                            // update group notifictaion for all groups
//                            //updateAllGroup();
//
//                        } else {
//                            //SettingsActivity.this.notificationGroupList.clear();
//                            AddNotificationActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter = new NotificationsGroupAdapter(AddNotificationActivity.this, notificationGroupList);
//                            addListView.setAdapter(notificationsGroupAdapter);
//                        }

                        Toast.makeText(AddNotificationActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddNotificationActivity.this, "Some problem occurred", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddNotificationActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
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


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view



        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }


}

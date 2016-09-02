package com.snaptech.quadeia.UI.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.quadeia.Manager.LinksManager;
import com.snaptech.quadeia.Manager.NotificationManager;
import com.snaptech.quadeia.R;
import com.snaptech.quadeia.UI.Adapter.AddGroupAdapter;
import com.snaptech.quadeia.UI.Model.NotificationGroups;
import com.snaptech.quadeia.Utility.AppError;
import com.snaptech.quadeia.Utility.Constants;
import com.snaptech.quadeia.WebService.GroupResponse;
import com.snaptech.quadeia.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddLinksActivity extends ActionBarActivity {

    private EditText title, desc;
    private ProgressDialog progressDialog;
    private View footerView;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private Button addButton;
    private AddGroupAdapter notificationsGroupAdapter;
    public static CheckBox allCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_links);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NotificationManager.grpIds.clear();

        addListView = (ListView) findViewById(R.id.activity_add_group_list_view);
        allCheckBox = (CheckBox) findViewById(R.id.checkAll);
        progressDialog=new ProgressDialog(AddLinksActivity.this);
        //progressDialog.setMessage("Loading");
        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.TRUE);
                    }
                } else {
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.FALSE);
                    }
                }
              //  NotificationManager.grpIds.clear();
                notificationsGroupAdapter.notifyDataSetChanged();
            }
        });
        setListViewHeightBasedOnChildren(addListView);
        title = (EditText) findViewById(R.id.activity_add_link_title_edit_text);
        desc = (EditText) findViewById(R.id.activity_add_link_url_edit_text);

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button) footerView.findViewById(R.id.add_button);
        addListView.addFooterView(footerView);

       if (notificationGroupList.size() <= 0){
           populateDummyData();
       }

        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new AddGroupAdapter(AddLinksActivity.this, notificationGroupList);
        addListView.setAdapter(notificationsGroupAdapter);

        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (URLUtil.isValidUrl(desc.getText().toString()) && (title.getText().toString() != null) || !title.getText().toString().trim().equals("")) {

                    progressDialog.show();
//                     String grpIdString = "";
//                    for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//                        if (i == 0){
//                            grpIdString = NotificationManager.grpIds.get(i);
//                        }else {
//                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//                        }
//                    }

                    String grpIdString = "";

                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        // notificationGroupList.get(i).setActive(1);


                        if (notificationGroupList.get(i).getActive()==Boolean.TRUE)
                        {
                            if (grpIdString.equals(""))
                            {
                                grpIdString = notificationGroupList.get(i).getId() ;
                            }
                            else {
                                grpIdString = grpIdString + "," + notificationGroupList.get(i).getId();
                            }
                            //NotificationManager.grpIds.add( notificationGroupList.get(i).getId());
                        }
                    }

                   // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(AddLinksActivity.this);

                    HashMap params = new HashMap();

                    params.put("link_name", title.getText().toString());

                    String url = desc.getText().toString();

                    if(!url.startsWith("http://")){
                        url = "http://"+url;
                    }

                    params.put("link_url", url);
                    params.put("group_id", grpIdString);
                    title.setText("");
                    desc.setText("");

                    LinksManager.getSharedInstance().addLinks(params, new LinksManager.AddLinksManagerListener() {
                        @Override
                        public void onCompletion(PostResponse response, AppError error) {
                            if (response != null) {
                                System.out.println("Links status is "+response.getStatus()+" and message is "+response.getMessage());

                                if (response.getStatus() == 200) {

                                    progressDialog.dismiss();
                                    Toast.makeText(AddLinksActivity.this, "Link added Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                {

                                    progressDialog.dismiss();
                                    Toast.makeText(AddLinksActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {

                                progressDialog.dismiss();
                                Toast.makeText(AddLinksActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                   // finish();
                } else {

                    Toast.makeText(AddLinksActivity.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
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

                        if (response.getData().getGroups().size() > 0){
                            AddLinksActivity.this.notificationGroupList.addAll(NotificationManager.groupList);
                            notificationsGroupAdapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(addListView);
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

                     //   Toast.makeText(AddLinksActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddLinksActivity.this, "Some problem occurred",Toast.LENGTH_SHORT).show();
                    }

                } else {
                   // Toast.makeText(AddLinksActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


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

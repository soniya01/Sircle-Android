package com.snaptech.Jardindeninossolecito.UI.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.Jardindeninossolecito.Manager.NotificationManager;
import com.snaptech.Jardindeninossolecito.Manager.PhotoManager;
import com.snaptech.Jardindeninossolecito.R;
import com.snaptech.Jardindeninossolecito.UI.Adapter.AddGroupAdapter;
import com.snaptech.Jardindeninossolecito.UI.Model.NotificationGroups;
import com.snaptech.Jardindeninossolecito.UI.cam.activity.CameraActivity;
import com.snaptech.Jardindeninossolecito.Utility.AppError;
import com.snaptech.Jardindeninossolecito.Utility.Constants;
import com.snaptech.Jardindeninossolecito.WebService.AddAlbumResponse;
import com.snaptech.Jardindeninossolecito.WebService.GroupResponse;

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
    private AddGroupAdapter notificationsGroupAdapter;
    ProgressDialog ringProgressDialog;
    public static CheckBox allCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NotificationManager.grpIds.clear();


        addListView = (ListView) findViewById(R.id.activity_add_group_list_view);
        allCheckBox = (CheckBox) findViewById(R.id.checkAll);
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
        title = (EditText) findViewById(R.id.activity_add_alnum_name_edit_text);

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button) footerView.findViewById(R.id.add_button);
        addButton.setText("Agregar Album");
        addListView.addFooterView(footerView);


        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new AddGroupAdapter(AddAlbumActivity.this, notificationGroupList);
        addListView.setAdapter(notificationsGroupAdapter);
        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (notificationGroupList.size() <= 0){
            populateDummyData();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String grpIdString = "";
//                for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//                    if (i == 0){
//                        grpIdString = NotificationManager.grpIds.get(i);
//                    }else {
//                        grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//                    }
//                }
              //  String grpIdString = NotificationManager.getSharedInstance().getGroupIds(AddAlbumActivity.this);

                String title2=title.getText().toString();
                if(title2.length()<=2){

                    Toast.makeText(AddAlbumActivity.this,"Título debe ser al menos de 3 caracteres",Toast.LENGTH_LONG).show();
                    title.requestFocus();
                }
                else {

                    ringProgressDialog = ProgressDialog.show(AddAlbumActivity.this, "", "Adding Album", true);

                    String grpIdString = "";

                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        // notificationGroupList.get(i).setActive(1);


                        if (notificationGroupList.get(i).getActive() == Boolean.TRUE) {
                            if (grpIdString.equals("")) {
                                grpIdString = notificationGroupList.get(i).getId();
                            } else {
                                grpIdString = grpIdString + "," + notificationGroupList.get(i).getId();
                            }
                            //NotificationManager.grpIds.add( notificationGroupList.get(i).getId());
                        }
                    }


                    HashMap params = new HashMap();
                    params.put("album_name", title.getText().toString());
                    params.put("group_id", grpIdString);
                    PhotoManager.getSharedInstance().addNewAlbum(params, new PhotoManager.AddPhotoManagerListener() {
                        @Override
                        public void onCompletion(AddAlbumResponse addAlbumResponse, AppError error) {
                            if (addAlbumResponse != null) {
                                ringProgressDialog.dismiss();
                                if (addAlbumResponse.getStatus() == 200) {

                                    Intent tabIntent = new Intent(AddAlbumActivity.this, CameraActivity.class);
                                    tabIntent.putExtra("albumId", addAlbumResponse.getData().getAlbumId());
                                    startActivity(tabIntent);
                                    finish();
                                } else {
                                    //  Toast.makeText(AddAlbumActivity.this, addAlbumResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AddAlbumActivity.this, "Compruebe internet", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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

                        if (response.getData().getGroups().size() > 0){
                            AddAlbumActivity.this.notificationGroupList.addAll(NotificationManager.groupList);
                            notificationsGroupAdapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(addListView);
                        }

//                        if (AddAlbumActivity.this.notificationGroupList.size() > 0) {
//                            AddAlbumActivity.this.notificationGroupList.clear();
//                            AddAlbumActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter.notifyDataSetChanged();
//                            // update group notifictaion for all groups
//                            //updateAllGroup();
//
//                        } else {
//                            //SettingsActivity.this.notificationGroupList.clear();
//                            AddAlbumActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter = new NotificationsGroupAdapter(AddAlbumActivity.this, notificationGroupList);
//                            addListView.setAdapter(notificationsGroupAdapter);
//
//                        }

                      // Toast.makeText(AddAlbumActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(SettingsActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } else {
                   // Toast.makeText(AddAlbumActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
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
//        if (BaseActivity.jumpToFragment){
//            String grpIdString = "";
//            for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//                if (i == 0){
//                    grpIdString = NotificationManager.grpIds.get(i);
//                }else {
//                    grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//                }
//            }
//            HashMap params = new HashMap();
//            params.put("albumName",title.getText().toString());
//            params.put("grp",grpIdString);
//            PhotoManager.getSharedInstance().addNewAlbum(params, new PhotoManager.AddPhotoManagerListener() {
//                @Override
//                public void onCompletion(AddAlbumResponse response, AppError error) {
//                    if (response != null) {
//                        if (response.getStatus() == 200) {
//                            Toast.makeText(AddAlbumActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    } else {
//                        Toast.makeText(AddAlbumActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//
//    }


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



      //  boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

}

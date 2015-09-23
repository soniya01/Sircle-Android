package com.app.sircle.UI.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.BaseActivity;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SettingsFragment extends Fragment {


    private ListView notificationListView;
    private NotificationsGroupAdapter notificationsGroupAdapter;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    ProgressDialog ringProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_settings, container, false);
       // activity_settings

        View viewFragment = inflater.inflate(R.layout.activity_settings, null , true);

        populateDummyData();

        notificationListView = (ListView)viewFragment.findViewById(R.id.notificationsGroupListView);
        notificationsGroupAdapter = new NotificationsGroupAdapter(getActivity(), notificationGroupList);

        notificationListView.setAdapter(notificationsGroupAdapter);
        Button saveButton = (Button)viewFragment.findViewById(R.id.saveGroups);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringProgressDialog = ProgressDialog.show(getActivity(), "", "", true);
                NotificationManager.getSharedInstance().updateGroupNotification(null, new NotificationManager.PostManagerListener() {
                    @Override
                    public void onCompletion(PostResponse postResponse, AppError error) {
                        ringProgressDialog.dismiss();
                        if (postResponse != null) {
                            Toast.makeText(getActivity(), postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            if (postResponse.getStatus() == 200) {
                                Intent homeIntent = new Intent(getActivity(), BaseActivity.class);
                                startActivity(homeIntent);
                                //finish();
                            }
                        } else {
                            Toast.makeText(getActivity(), "some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });

        return  viewFragment;

    }


    public void populateDummyData(){

            HashMap<String, String> map = new HashMap<>();
            map.put("regId", Constants.GCM_REG_ID);
            NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
                @Override
                public void onCompletion(GroupResponse response, AppError error) {

                    if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                        if (response != null) {

                            if (notificationGroupList.size() > 0) {
                                notificationGroupList.clear();
                                notificationGroupList.addAll(response.getData());
                                notificationsGroupAdapter.notifyDataSetChanged();
                                // update group notifictaion for all groups
                                //updateAllGroup();

                            } else {
                                //SettingsActivity.this.notificationGroupList.clear();
                                notificationGroupList.addAll(response.getData());
                                notificationsGroupAdapter = new NotificationsGroupAdapter(getActivity(), notificationGroupList);
                                notificationListView.setAdapter(notificationsGroupAdapter);
                            }

                            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(SettingsActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


}

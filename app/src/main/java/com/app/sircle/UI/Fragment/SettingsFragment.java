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
import com.app.sircle.WebService.PostResponse;

import java.util.ArrayList;
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

        NotificationGroups n1 = new NotificationGroups();
        n1.setName("Group 1");

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

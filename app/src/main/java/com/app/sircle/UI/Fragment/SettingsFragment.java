package com.app.sircle.UI.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Model.NotificationGroups;

import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends Fragment {


    private ListView notificationListView;
    private NotificationsGroupAdapter notificationsGroupAdapter;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();


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

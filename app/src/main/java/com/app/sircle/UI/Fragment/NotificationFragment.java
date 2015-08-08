package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationListviewAdapter;
import com.app.sircle.UI.Model.Notification;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    private ListView notificationListView;
    private FloatingActionButton floatingActionButton;
    private List<Notification> notificationList = new ArrayList<Notification>();
    private NotificationListviewAdapter notificationListviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListView = (ListView)viewFragment.findViewById(R.id.fragment_notification_listview);
        floatingActionButton = (FloatingActionButton)viewFragment.findViewById(R.id.fab);

        populateDummyData();

        // add button on click to open respective view - only for admin
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // set up custom listview
        notificationListviewAdapter = new NotificationListviewAdapter( notificationList, getActivity());
        notificationListView.setAdapter(notificationListviewAdapter);

        return viewFragment;
    }

    public void populateDummyData(){

        Notification notification = new Notification();
        notification.setAnnouncementDesc("Heavy Rains");
        notification.setAnnouncementTitle("Due to heavy rains school will be closed today");

        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);

    }


}

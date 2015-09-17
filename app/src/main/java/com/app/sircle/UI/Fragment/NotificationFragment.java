package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddNotificationActivity;
import com.app.sircle.UI.Adapter.NotificationListviewAdapter;
import com.app.sircle.UI.Model.Notification;
import com.app.sircle.Utility.AppError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotificationFragment extends Fragment {

    public static List<Notification> notificationList = new ArrayList<Notification>();
    private ListView notificationListView;
    private FloatingActionButton floatingActionButton;
    private NotificationListviewAdapter notificationListviewAdapter;
    private View footerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListView = (ListView) viewFragment.findViewById(R.id.fragment_notification_listview);
        floatingActionButton = (FloatingActionButton) viewFragment.findViewById(R.id.fab);

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        notificationListView.addFooterView(footerView);

        populateDummyData();

        // set up custom listview


        // add button on click to open respective view - only for admin
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notifIntent = new Intent(getActivity(), AddNotificationActivity.class);
                startActivity(notifIntent);
            }
        });

        return viewFragment;
    }

    public void populateDummyData() {

        HashMap object = new HashMap();
        object.put("regId", "id");
        object.put("groupId",1);
        object.put("val", "val");

//        NotificationManager.getSharedInstance().getAllNotifications(object, new NotificationManager.NotificationManagerListener() {
//            @Override
//            public void onCompletion(List<Notification> notifications, AppError error) {
//                notificationListviewAdapter = new NotificationListviewAdapter(notifications, getActivity());
//                notificationListView.setAdapter(notificationListviewAdapter);
//            }
//        });

        Notification notification = new Notification();
        notification.setAnnouncementDesc("Heavy Rains");
        notification.setAnnouncementTitle("Due to heavy rains school will be closed today");

        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);
        notificationList.add(notification);
    }

    @Override
    public void onResume() {
        super.onResume();
        //notificationListviewAdapter.notifyDataSetChanged();
    }
}

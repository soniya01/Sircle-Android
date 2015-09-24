package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddNotificationActivity;
import com.app.sircle.UI.Adapter.NotificationListviewAdapter;
import com.app.sircle.UI.Model.Notification;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.NotificationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotificationFragment extends Fragment {

    public static List<Notification> notificationList = new ArrayList<Notification>();
    private ListView notificationListView;
    private FloatingActionButton floatingActionButton;
    private NotificationListviewAdapter notificationListviewAdapter;
    private View footerView,viewFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListView = (ListView) viewFragment.findViewById(R.id.fragment_notification_listview);
        floatingActionButton = (FloatingActionButton) viewFragment.findViewById(R.id.fab);

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        notificationListView.addFooterView(footerView);
        notificationListviewAdapter = new NotificationListviewAdapter(notificationList, getActivity());
        notificationListView.setAdapter(notificationListviewAdapter);

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
        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ((RelativeLayout)viewFragment).addView(progressBar, layoutParams);

        HashMap object = new HashMap();
        object.put("regId", "id");
        object.put("groupId",1);
        object.put("page", 1);

        NotificationManager.getSharedInstance().getAllNotifications(object, new NotificationManager.NotificationManagerListener() {
            @Override
            public void onCompletion(NotificationResponse data, AppError error) {
                progressBar.setVisibility(View.GONE);
                if (data != null) {
                    if (data.getStatus() == 200){
                        if (data.getData().getNotifications().size() > 0){
                            if (notificationList.size() > 0){
                                notificationList.clear();
                                notificationList.addAll(data.getData().getNotifications());
                                notificationListviewAdapter.notifyDataSetChanged();
                            }else {
                                notificationList.addAll(data.getData().getNotifications());
                                notificationListviewAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //notificationListviewAdapter.notifyDataSetChanged();
    }
}

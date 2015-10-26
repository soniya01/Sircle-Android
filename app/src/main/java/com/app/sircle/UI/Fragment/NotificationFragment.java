package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.NotificationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public  List<Notification> notificationList = new ArrayList<Notification>();
    private ListView notificationListView;
    private FloatingActionButton floatingActionButton;
    private NotificationListviewAdapter notificationListviewAdapter;
    private View footerView,viewFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListView = (ListView) viewFragment.findViewById(R.id.fragment_notification_listview);
        floatingActionButton = (FloatingActionButton) viewFragment.findViewById(R.id.fab);

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        notificationListView.addFooterView(footerView);

        notificationList = NotificationManager.notificationList;
        notificationListviewAdapter = new NotificationListviewAdapter(notificationList, getActivity());
        notificationListView.setAdapter(notificationListviewAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(NotificationFragment.this);

        if (notificationList.size() <= 0){
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);

                                            populateDummyData();
                                        }
                                    }
            );
        }


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
//        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        ((RelativeLayout)viewFragment).addView(progressBar, layoutParams);


        NotificationManager.notificationList.clear();
        String grpIdString = "";
        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
            if (i == 0){
                grpIdString = NotificationManager.grpIds.get(i);
            }else {
                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
            }

        }
        HashMap object = new HashMap();
        object.put("regId", Constants.GCM_REG_ID);
        object.put("groupId",grpIdString);
        object.put("page", 1);

        NotificationManager.getSharedInstance().getAllNotifications(object, new NotificationManager.NotificationManagerListener() {
            @Override
            public void onCompletion(NotificationResponse data, AppError error) {
                //progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (data != null) {
                    if (data.getStatus() == 200){
                        if (data.getData().getNotifications().size() > 0){
                            notificationList.addAll(NotificationManager.notificationList);
                            notificationListviewAdapter.notifyDataSetChanged();

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

    @Override
    public void onRefresh() {
        populateDummyData();
    }
}

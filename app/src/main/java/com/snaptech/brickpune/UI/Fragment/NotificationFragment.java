package com.snaptech.brickpune.UI.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;;
import android.widget.Toast;

import com.snaptech.brickpune.Manager.NotificationManager;
import com.snaptech.brickpune.R;
import com.snaptech.brickpune.UI.Activity.AddNotificationActivity;
import com.snaptech.brickpune.UI.Adapter.NotificationListviewAdapter;
import com.snaptech.brickpune.UI.Model.Notification;
import com.snaptech.brickpune.Utility.AppError;
import com.snaptech.brickpune.Utility.Constants;
import com.snaptech.brickpune.Utility.InternetCheck;
import com.snaptech.brickpune.WebService.NotificationResponse;

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
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount, totalRecord;
    boolean isLoading;
    private SharedPreferences loginSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pageCount = 1;

        viewFragment = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListView = (ListView) viewFragment.findViewById(R.id.fragment_notification_listview);
        floatingActionButton = (FloatingActionButton) viewFragment.findViewById(R.id.fab);

        loginSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if (!userType.equals("admin"))
        {
            floatingActionButton.setVisibility(View.GONE);
        }

//        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
//        notificationListView.addFooterView(footerView, null, false);

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

                                            if(InternetCheck.isNetworkConnected(getActivity()))
                                            populateDummyData();
                                            else
                                                Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
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

        notificationListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                currentScrollState = scrollState;
                isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

//                int lastInScreen = firstVisibleItem + visibleItemCount;
//                if ((lastInScreen == totalItemCount) && !(false)) {
//                  //  Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();
//                    System.out.println("Load More");
//                }
                currentFirstVisibleItem = firstVisibleItem;
                currentVisibleItemCount = visibleItemCount;
            }






        });


        return viewFragment;
    }

    private void isScrollCompleted() {

        if (totalRecord == notificationList.size()){

        }else {
            if (this.currentVisibleItemCount > 0 && this.currentScrollState == 0) {
                /*** In this way I detect if there's been a scroll which has completed ***/
                /*** do the work for load more date! ***/
                System.out.println("Load not");
                if(!isLoading){
                    isLoading = true;
                    System.out.println("Load More");
                    if(InternetCheck.isNetworkConnected(getActivity()))
                    loadMoreData();
                    else
                        Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    public void populateDummyData() {
        pageCount=1;
//        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        ((RelativeLayout)viewFragment).addView(progressBar, layoutParams);


        //NotificationManager.notificationList.clear();
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//
//        }

      //  String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
      //  object.put("regId", Constants.GCM_REG_ID);
       // object.put("groupId",grpIdString);
        object.put("page", "" + pageCount);

       // System.out.println("REG" + Constants.GCM_REG_ID);

        NotificationManager.getSharedInstance().getAllNotifications(object, new NotificationManager.NotificationManagerListener() {
            @Override
            public void onCompletion(NotificationResponse data, AppError error) {
                //progressBar.setVisibility(View.GONE);

                swipeRefreshLayout.setRefreshing(false);
                if (Constants.flag_logout) {

                   // Toast.makeText(getActivity(), "Session Timed Out! Please reopen the app to Login again.", Toast.LENGTH_LONG).show();
                    Constants.flag_logout = false;
                } else {

                    if (data != null) {
                        if (data.getStatus() == 200) {
                            if (data.getData().getNotifications().size() > 0) {
                                //NotificationManager.notificationList.clear()
                                //  totalRecord = data.getData().getTotalRecords();
                                notificationList.clear();
                                notificationList.addAll(NotificationManager.notificationList);
                                notificationListviewAdapter.notifyDataSetChanged();

                            } else {
                                //   Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
       // populateDummyData();
        //notificationListviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (InternetCheck.isNetworkConnected(getActivity()))
        {
            pageCount = 1;
        populateDummyData();
    }
        else{
            Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    public void loadMoreData()
    {
        pageCount = pageCount +1;
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//
//        }

      //  String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
      //  object.put("regId", Constants.GCM_REG_ID);
      //  object.put("groupId",grpIdString);
        object.put("page", ""+pageCount);

        System.out.println("REG" + Constants.GCM_REG_ID);

        NotificationManager.getSharedInstance().getAllNotifications(object, new NotificationManager.NotificationManagerListener() {
            @Override
            public void onCompletion(NotificationResponse data, AppError error) {
                //progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                isLoading = false;
                if (data != null) {
                    if (data.getStatus() == 200){
                        if (data.getData().getNotifications().size() > 0){
                            //NotificationManager.notificationList.clear()
                           // notificationList.clear();
                            notificationList.addAll(NotificationManager.notificationList);
                            notificationListviewAdapter.notifyDataSetChanged();
                        }else {
                        //    Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                       // Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
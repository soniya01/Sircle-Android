package com.snaptech.hasanaths.UI.Fragment;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.hasanaths.Manager.NotificationManager;
import com.snaptech.hasanaths.R;
import com.snaptech.hasanaths.UI.Activity.BaseActivity;
import com.snaptech.hasanaths.UI.Adapter.NotificationsGroupAdapter;
import com.snaptech.hasanaths.UI.Model.NotificationGroups;
import com.snaptech.hasanaths.Utility.AppError;
import com.snaptech.hasanaths.Utility.Constants;
import com.snaptech.hasanaths.WebService.GroupResponse;
import com.snaptech.hasanaths.WebService.PostResponse;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SettingsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private ListView notificationListView;
    private NotificationsGroupAdapter notificationsGroupAdapter;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    ProgressDialog ringProgressDialog;
    CheckBox allCheckBox;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFragment = inflater.inflate(R.layout.activity_settings, null , true);
        notificationListView = (ListView)viewFragment.findViewById(R.id.notificationsGroupListView);

        NotificationManager.grpIds.clear();
       // Constants.isAllChecked = -1;

        allCheckBox = (CheckBox) viewFragment.findViewById(R.id.checkAll);
//        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    Constants.isAllChecked = 1;
//                }else {
//                    Constants.isAllChecked = 0;
//                }
//                NotificationManager.grpIds.clear();
//                notificationsGroupAdapter.notifyDataSetChanged();
//            }
//        });


        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Constants.isAllChecked = 1;
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.TRUE);
                    }
                } else {
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.FALSE);
                    }
                    //notificationGroupList.get(i).setActive(1);
                    // Constants.isAllChecked = 0;
                }
                // NotificationManager.grpIds.clear();
                notificationsGroupAdapter.notifyDataSetChanged();
            }
        });

        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new NotificationsGroupAdapter(getActivity(), notificationGroupList);

        notificationListView.setAdapter(notificationsGroupAdapter);

        //swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setOnRefreshListener(SettingsFragment.this);



        if (notificationGroupList.size() <= 0){
            populateDummyData();
        }
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
//
//                                        populateDummyData();
//                                    }
//                                }
//        );


        Button saveButton = (Button)viewFragment.findViewById(R.id.saveGroups);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ringProgressDialog = ProgressDialog.show(getActivity(), "", "", true);
//                JSONArray arrayObject = new JSONArray();
//
//                if (NotificationManager.grpIds.size() > 0){
//                    for (int i = 0; i < NotificationManager.grpIds.size(); i++) {
//                        try {
//                            JSONObject object = new JSONObject();
//                            object.put("group_id", NotificationManager.grpIds.get(i));
//                            object.put("val", 1);
//
//                            arrayObject.put(object);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    HashMap map = new HashMap();
//                    map.put("regId", Constants.GCM_REG_ID);
//                    map.put("groupValString", arrayObject.toString());
//
//                    NotificationManager.getSharedInstance().updateGroupNotification(map, new NotificationManager.PostManagerListener() {
//                        @Override
//                        public void onCompletion(PostResponse postResponse, AppError error) {
//                            ringProgressDialog.dismiss();
//                            if (postResponse != null) {
//                                Toast.makeText(getActivity(), postResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                                if (postResponse.getStatus() == 200) {
//                                    Intent homeIntent = new Intent(getActivity(), BaseActivity.class);
//                                    startActivity(homeIntent);
//                                }
//                            } else {
//                                Toast.makeText(getActivity(), "some error occurred", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }else {
//                    Toast.makeText(getActivity(), "Please select at least one group", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = 0; i < notificationGroupList.size(); i++) {
                    // listData[i] = listView.getAdapter().getItem(i).toString();
                    // notificationGroupList.get(i).setActive(1);
                    if (notificationGroupList.get(i).getActive()==Boolean.TRUE)
                    {
                        NotificationManager.getSharedInstance().grpIds.add( notificationGroupList.get(i).getId());
                    }
                }

                JSONArray arrayObject = new JSONArray();

                String grpIdString = "";

                if ( NotificationManager.grpIds.size() > 0){
                    ringProgressDialog = ProgressDialog.show(getActivity(), "", "", true);
                    for (int i =0 ; i<  NotificationManager.grpIds.size(); i++){
//                        try {
//                            JSONObject object = new JSONObject();
//                            object.put("group_id",NotificationManager.grpIds.get(i));
//                            object.put("val", 1);
//
//                            arrayObject.put(object);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        if (grpIdString.equals(""))
                        {
                            grpIdString = NotificationManager.grpIds.get(i) ;
                        }
                        else {
                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i);
                        }
                    }

                    HashMap map = new HashMap();
                 //   map.put("regId", Constants.GCM_REG_ID);
                    map.put("group_id", grpIdString);

                    NotificationManager.getSharedInstance().updateGroupNotification(map, new NotificationManager.PostManagerListener() {
                        @Override
                        public void onCompletion(PostResponse postResponse, AppError error) {
                            ringProgressDialog.dismiss();
                            if (postResponse != null) {
                                //Toast.makeText(SettingsActivity.this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (postResponse.getStatus() == 200) {
                                    String grpIdString = "";
                                    for (int i = 0; i< NotificationManager.grpIds.size(); i++){
                                        if (i == 0){
                                            grpIdString = NotificationManager.grpIds.get(i);
                                        }else {
                                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
                                        }
                                    }

                               //     NotificationManager.getSharedInstance().saveGroupIds(grpIdString,getActivity());
                                    System.out.println("Called intent");
                                    loadFragment(getActivity(), new HomeFragment(),"Home",1);
                                    Toast.makeText(getActivity(),"Settings Saved Successfully",Toast.LENGTH_SHORT).show();
//                                    Intent homeIntent = new Intent(getActivity(), BaseActivity.class);
//                                    startActivity(homeIntent);
                                    //getActivity().finish();
                                }
                            } else {
                                Toast.makeText(getActivity(), "some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), "Please select at least one group", Toast.LENGTH_SHORT).show();
                }
                // give access to the app features

            }

        });

        return  viewFragment;

    }


    public void populateDummyData(){
        ringProgressDialog = ProgressDialog.show(getActivity(), "", "", true);
            HashMap<String, String> map = new HashMap<>();
            map.put("regId", Constants.GCM_REG_ID);
            NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
                @Override
                public void onCompletion(GroupResponse response, AppError error) {
                    ringProgressDialog.dismiss();
                    //swipeRefreshLayout.setRefreshing(false);
                    if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                        if (response != null) {
                            if (response.getStatus() == 200){
                                if (response.getData().getGroups().size() > 0) {
                                    notificationGroupList.clear();
                                    notificationGroupList.addAll(NotificationManager.groupList);
                                    notificationsGroupAdapter.notifyDataSetChanged();
                                    // update group notifictaion for all groups
                                    //updateAllGroup();

                               }
// else {
//                                    //SettingsActivity.this.notificationGroupList.clear();
//                                    notificationGroupList.addAll(response.getData());
//                                    notificationsGroupAdapter = new NotificationsGroupAdapter(getActivity(), notificationGroupList);
//                                    notificationListView.setAdapter(notificationsGroupAdapter);
//                                }
                            }else {
                             //   Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Some problem occurred",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                      //  Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    @Override
    public void onRefresh() {
        //populateDummyData();
    }
    private void loadFragment(Context context, Fragment fragment, String title, int position) {


        FragmentManager mFragmentManager;
        FragmentTransaction mFragmentTransaction;

        ((BaseActivity)context)
                .setActionBarTitle(title);


        ((BaseActivity)context)
                .setFalse();

        ((BaseActivity)context)
                .setFragmentName(title);

        ((BaseActivity)context)
                .setDrawerPositionFromHome(position);


        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();


        if (fragment != null) {
            mFragmentTransaction.replace(R.id.main_layout_container, fragment).commit();
        }




    }
}

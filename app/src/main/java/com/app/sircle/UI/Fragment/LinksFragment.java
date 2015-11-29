package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.LinksManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddLinksActivity;
import com.app.sircle.UI.Adapter.LinksListViewAdapter;
import com.app.sircle.UI.Model.Links;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.LinksResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LinksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public List<Links> linksList = new ArrayList<Links>();
    private ListView linksListView;
    private FloatingActionButton floatingActionButton;
    private LinksListViewAdapter linksListViewAdapter;
    private View footerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_links, container, false);

        linksListView = (ListView) viewFragment.findViewById(R.id.fragment_links_listview);
        floatingActionButton = (FloatingActionButton) viewFragment.findViewById(R.id.fab);
        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(LinksFragment.this);

        linksList = LinksManager.linksList;

        linksListViewAdapter = new LinksListViewAdapter(linksList, getActivity());
        linksListView.setAdapter(linksListViewAdapter);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */

        if (linksList.size() <= 0){
            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);

                                            populateDummyData();
                                        }
                                    }
            );
        }


        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        linksListView.addFooterView(footerView);


        // add button on click to open respective view - only for admin
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addLinkIntent = new Intent(getActivity(), AddLinksActivity.class);
                startActivity(addLinkIntent);
            }
        });
        // set up custom listview

//        linksListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });

        return viewFragment;
    }


    private void populateDummyData() {

        String grpIdString = "";
        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
            if (i == 0){
                grpIdString = NotificationManager.grpIds.get(i);
            }else {
                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
            }
        }
        HashMap map = new HashMap();
        map.put("regId", Constants.GCM_REG_ID);
        map.put("groupId", grpIdString);
        map.put("page", 1);

        System.out.print("Map "+map);

        LinksManager.getSharedInstance().getAllLinks(map, new LinksManager.LinksManagerListener() {
            @Override
            public void onCompletion(LinksResponse response, AppError error) {
                swipeRefreshLayout.setRefreshing(false);
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {
                        if (response.getData().getLinks().size() > 0) {
                            LinksFragment.this.linksList.addAll(response.getData().getLinks());
                            linksListViewAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (LinksFragment.this.linksList.size() > 0) {
            linksListViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onRefresh() {
        populateDummyData();
    }
}

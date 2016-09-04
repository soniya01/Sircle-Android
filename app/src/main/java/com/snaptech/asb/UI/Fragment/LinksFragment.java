package com.snaptech.asb.UI.Fragment;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.asb.Manager.LinksManager;
import com.snaptech.asb.R;
import com.snaptech.asb.UI.Activity.AddLinksActivity;
import com.snaptech.asb.UI.Activity.PDFWebViewer;
import com.snaptech.asb.UI.Adapter.LinksListViewAdapter;
import com.snaptech.asb.UI.Model.Links;
import com.snaptech.asb.Utility.AppError;
import com.snaptech.asb.Utility.Constants;
import com.snaptech.asb.Utility.InternetCheck;
import com.snaptech.asb.WebService.LinksResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LinksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener{

    public List<Links> linksList = new ArrayList<Links>();
    private ListView linksListView;
    private FloatingActionButton floatingActionButton;
    private LinksListViewAdapter linksListViewAdapter;
    private View footerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int count = 1, totalRecord = 0, pageRecords = 0;
    boolean isLoading;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount;
    private SharedPreferences loginSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pageCount = 1;
        View viewFragment = inflater.inflate(R.layout.fragment_links, container, false);

        linksListView = (ListView) viewFragment.findViewById(R.id.fragment_links_listview);
        floatingActionButton = (FloatingActionButton) viewFragment.findViewById(R.id.fab);
        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(LinksFragment.this);

        loginSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if (!userType.equals("admin"))
        {
            floatingActionButton.setVisibility(View.GONE);
        }

//        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
//        linksListView.addFooterView(footerView, null, false);

        linksList = LinksManager.linksList;

        linksListViewAdapter = new LinksListViewAdapter(linksList, getActivity());
        linksListView.setAdapter(linksListViewAdapter);
        linksListView.setOnScrollListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */

        if (linksList.size() <= 0){
            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);

                                            populateDummyData(1);
                                        }
                                    }
            );
        }


        // add button on click to open respective view - only for admin
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addLinkIntent = new Intent(getActivity(), AddLinksActivity.class);
                startActivity(addLinkIntent);
            }
        });


        linksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), PDFWebViewer.class);
                intent.putExtra("VideoUrl",linksList.get(position).getUrl());
                intent.putExtra("UrlTitle",linksList.get(position).getName());
                startActivity(intent);




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


    private void populateDummyData(int page) {

        if(InternetCheck.isNetworkConnected(getActivity())) {
            pageCount = 1;
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

            // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

            HashMap map = new HashMap();
            //map.put("regId", Constants.GCM_REG_ID);
            //  map.put("groupId", grpIdString);
            map.put("page", page + "");

            System.out.print("Map " + map);

            LinksManager.getSharedInstance().getAllLinks(map, new LinksManager.LinksManagerListener() {
                @Override
                public void onCompletion(LinksResponse response, AppError error) {
                    isLoading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                        if (response != null) {
                            if (response.getData().getLinks().size() > 0) {
                                //  totalRecord = response.getData().getTotalRecords();
                                // pageRecords =  response.getData().getPageRecords();
                                linksList.clear();
                                linksList.addAll(response.getData().getLinks());
                                linksListViewAdapter.notifyDataSetChanged();

                            } else {
                                //  Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        linksList.clear();
        linksListViewAdapter.notifyDataSetChanged();
        pageCount = 1;
        populateDummyData(1);
//        if (LinksFragment.this.linksList.size() > 0) {
//            linksListViewAdapter.notifyDataSetChanged();
//        }

    }

    @Override
    public void onRefresh() {

        linksList.clear();
        linksListViewAdapter.notifyDataSetChanged();
        populateDummyData(1);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentScrollState = scrollState;
        isScrollCompleted();

//        if (scrollState == SCROLL_STATE_IDLE) {
//            this.count++;
//            if (linksListView.getLastVisiblePosition() >= count - threshold && (totalRecord < this.count * 10)) {
//                Log.i("load more", "loading more data");
//                // Execute LoadMoreDataTask AsyncTask
//               populateDummyData(this.count);
//            }
//        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        currentFirstVisibleItem = firstVisibleItem;
        currentVisibleItemCount = visibleItemCount;
//        int lastInScreen = firstVisibleItem + visibleItemCount;
//        if((lastInScreen == totalItemCount) ){
//            String url = "http://10.0.2.2:8080/CountryWebService" +
//                    "/CountryServlet";
//            //grabURL(url);
//        }else {
//            //count++;
//            //populateDummyData(count);
//        }
    }


    private void isScrollCompleted() {
        if (totalRecord == linksList.size()){

        }else {
            if (this.currentVisibleItemCount > 0 && this.currentScrollState == 0) {
                /*** In this way I detect if there's been a scroll which has completed ***/
                /*** do the work for load more date! ***/
                System.out.println("Load not");
                isLoading=false;
                if(!isLoading){
                    isLoading = true;
                    System.out.println("Load More");
                    loadMoreData();
                    // Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();

                }
            }
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

        //String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
     //   object.put("regId", Constants.GCM_REG_ID);
      //  object.put("groupId",grpIdString);
        object.put("page", pageCount+"");

        System.out.println("REG" + Constants.GCM_REG_ID);
        System.out.println("Page id is "+pageCount);
        LinksManager.getSharedInstance().getAllLinks(object, new LinksManager.LinksManagerListener() {
            @Override
            public void onCompletion(LinksResponse data, AppError error) {
                //progressBar.setVisibility(View.GONE);
                System.out.println("Status is "+data.getStatus()+" and link size is "+data.getData().getLinks().size());
                swipeRefreshLayout.setRefreshing(false);
                //isLoading = false;
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (data != null) {
                        if (data.getData().getLinks().size() > 0) {
                            //  totalRecord = response.getData().getTotalRecords();
                            // pageRecords =  response.getData().getPageRecords();

                            linksList.addAll(LinksManager.linksList);
                            linksListViewAdapter.notifyDataSetChanged();

                        } else {
                            //  Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
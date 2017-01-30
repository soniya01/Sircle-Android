package com.snaptech.naharInt.UI.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.snaptech.naharInt.Manager.DocumentManager;
import com.snaptech.naharInt.R;
import com.snaptech.naharInt.UI.Activity.PDFViewer;
import com.snaptech.naharInt.UI.Adapter.NewsLettersViewAdapter;
import com.snaptech.naharInt.UI.Model.NewsLetter;
import com.snaptech.naharInt.Utility.AppError;
import com.snaptech.naharInt.Utility.Constants;
import com.snaptech.naharInt.Utility.InternetCheck;
import com.snaptech.naharInt.WebService.DocumentsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewsLetterFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener{

    private ListView newsLetterListView;
    private NewsLettersViewAdapter newsLetterListViewAdapter;
    private List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    private View footerView, viewFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean flag_permission_ex_storage=false;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount, totalRecord;
    boolean isLoading;
    private String path="";
    private String name="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_news_letter,
                null, true);
        newsLetterListView = (ListView)viewFragment.findViewById(R.id.fragment_news_list_view);
        newsLetterListView.setOnScrollListener(this);

//        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
//        newsLetterListView.addFooterView(footerView, null, false);

        newsLetterList = DocumentManager.newsLetterList;

        newsLetterListViewAdapter = new NewsLettersViewAdapter(getActivity(), newsLetterList);
        newsLetterListView.setAdapter(newsLetterListViewAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(NewsLetterFragment.this);




        if (newsLetterList.size() <= 0){
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
            //  populateDummyData();
//            swipeRefreshLayout.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            swipeRefreshLayout.setRefreshing(true);
//
//                                            populateDummyData();
//                                        }
//                                    }
//            );

            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);

                                            populateDummyData();
                                        }
                                    }
            );
        }


        newsLetterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsLetter selectedItem = newsLetterList.get(position);

                path=selectedItem.getPath();
                name=selectedItem.getName();
                if(!checkExternalStoragePermission()) {
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    flag_permission_ex_storage=false;
                }
                else
                    flag_permission_ex_storage=true;
                //Toast.makeText(getActivity(), "File downloaded " + selectedItem.getName(), Toast.LENGTH_SHORT).show();

                if(flag_permission_ex_storage) {
                    Intent intent = new Intent(getActivity(), PDFViewer.class);
                    intent.putExtra("PdfUrl", selectedItem.getPath());
                    intent.putExtra("PdfName", selectedItem.getName());
                    startActivity(intent);
                }
//                else{
//
//                   // Toast.makeText(getActivity(),"Please give External Storage permission",Toast.LENGTH_SHORT).show();
//                }
//                Intent intent = new Intent(getActivity(), PDFWebViewer.class);
//                intent.putExtra("PdfUrl",selectedItem.getPath());
//                startActivity(intent);
            }
        });


        return viewFragment;
    }

    public void populateDummyData(){

        if(InternetCheck.isNetworkConnected(getActivity())) {
            pageCount = 1;
            // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

            HashMap map = new HashMap();
            //map.put("regId", Constants.GCM_REG_ID);
            // map.put("groupId", grpIdString);
            map.put("page", "1");

            //  System.out.println("Map " + map);

            DocumentManager.getSharedInstance().getAllNewsLetters(map, new DocumentManager.GetDocumentManagerListener() {
                @Override
                public void onCompletion(DocumentsResponse response, AppError error) {
                    //  progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    if (Constants.flag_logout) {

                        //   Toast.makeText(getActivity(), "Session Timed Out! Please reopen the app to Login again.", Toast.LENGTH_LONG).show();
                        Constants.flag_logout = false;
                    } else {
                        if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                            if (response != null) {
                                if (response.getStatus() == 200) {
                                    if (response.getData().getDocs().size() > 0) {

                                        //totalRecord = response.getData().getTotalRecords();
                                        newsLetterList.clear();
                                        newsLetterList.addAll(DocumentManager.newsLetterList);
                                        newsLetterListViewAdapter.notifyDataSetChanged();

//                                if (NewsLetterFragment.this.newsLetterList.size() == 0){
//                                    NewsLetterFragment.this.newsLetterList.addAll(response.getData().getNewsLetters());
//                                    newsLetterListViewAdapter = new NewsLettersViewAdapter(getActivity(), response.getData().getNewsLetters());
//                                    newsLetterListView.setAdapter(newsLetterListViewAdapter);
//                                }else {
//                                    NewsLetterFragment.this.newsLetterList.clear();
//                                    NewsLetterFragment.this.newsLetterList.addAll(response.getData().getNewsLetters());
//                                    newsLetterListViewAdapter.notifyDataSetChanged();
//                                }
                                    } else {
                                        //  Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(getActivity(), "Sorry! Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        else
            Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // populateDummyData();
    }

    @Override
    public void onRefresh() {


        populateDummyData();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentScrollState = scrollState;
        isScrollCompleted();

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        currentFirstVisibleItem = firstVisibleItem;
        currentVisibleItemCount = visibleItemCount;
    }


    private void isScrollCompleted() {

        System.out.println("Current visible item count "+this.currentVisibleItemCount+" currentScrollState "+this.currentScrollState);
        System.out.println("Scroll completed called");
        if (totalRecord == newsLetterList.size()){
            System.out.println("Scroll completed called inside total record");
        }else {
            if (this.currentVisibleItemCount > 0 && this.currentScrollState == 0) {

                /*** In this way I detect if there's been a scroll which has completed ***/
                /*** do the work for load more date! ***/
                System.out.println("Load not");
                if(!isLoading){
                    isLoading = true;
                    System.out.println("Load More");
                    loadMoreData();
                    // Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();

                }
            }
        }



    }

    public void loadMoreData(){
        pageCount += 1;
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

        //String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
        // object.put("regId", Constants.GCM_REG_ID);
        //object.put("groupId", grpIdString);
        object.put("page", pageCount+"");

        DocumentManager.getSharedInstance().getAllNewsLetters(object, new DocumentManager.GetDocumentManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse data, AppError error) {
                isLoading = false;
                if (data != null) {
                    if (data.getStatus() == 200) {
                        if (data.getData().getDocs().size() > 0) {
                            newsLetterList.addAll(DocumentManager.newsLetterList);
                            newsLetterListViewAdapter.notifyDataSetChanged();

                        } else {
                            //  Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        //  Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkExternalStoragePermission()
    {

        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                System.out.println("First condition");
//                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                System.out.println("Second condition");
                //Log.v(TAG,"Permission is revoked");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            System.out.println("Third condition");
            //permission is automatically granted on sdk<23 upon installation
            // Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if(path!=null) {

                        if(!path.trim().equals("")) {
                            Intent intent = new Intent(getActivity(), PDFViewer.class);
                            intent.putExtra("PdfUrl", path);
                            intent.putExtra("PdfName", name);
                            startActivity(intent);
                        }
                    }
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Toast.makeText(getActivity(),"Please give external storage permission to view pdf.",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}
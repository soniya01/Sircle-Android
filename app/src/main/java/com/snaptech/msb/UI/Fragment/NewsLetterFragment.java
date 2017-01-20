package com.snaptech.msb.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.snaptech.msb.Manager.DocumentManager;
import com.snaptech.msb.R;
import com.snaptech.msb.UI.Activity.PDFViewer;
import com.snaptech.msb.UI.Adapter.NewsLettersViewAdapter;
import com.snaptech.msb.UI.Model.NewsLetter;
import com.snaptech.msb.Utility.AppError;
import com.snaptech.msb.Utility.Constants;
import com.snaptech.msb.Utility.InternetCheck;
import com.snaptech.msb.WebService.DocumentsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewsLetterFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener{

    private ListView newsLetterListView;
    private NewsLettersViewAdapter newsLetterListViewAdapter;
    private List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    private View footerView, viewFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount, totalRecord;
    boolean isLoading;

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

                //Toast.makeText(getActivity(), "File downloaded " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PDFViewer.class);
                intent.putExtra("PdfUrl",selectedItem.getPath());
                intent.putExtra("PdfName",selectedItem.getName());
                startActivity(intent);
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
}
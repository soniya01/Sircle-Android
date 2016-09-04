package com.snaptech.asb.UI.Fragment;


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

import com.snaptech.asb.Manager.DocumentManager;
import com.snaptech.asb.R;
import com.snaptech.asb.UI.Activity.PDFViewer;
import com.snaptech.asb.UI.Adapter.DocumentsViewAdapter;
import com.snaptech.asb.UI.Model.NewsLetter;
import com.snaptech.asb.Utility.AppError;
import com.snaptech.asb.Utility.InternetCheck;
import com.snaptech.asb.WebService.DocumentsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DocumentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener{

    private ListView newsLetterListView;
    private DocumentsViewAdapter newsLetterListViewAdapter;
    private List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    private View footerView, viewFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount, totalRecord;
    boolean isLoading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pageCount = 1;
        viewFragment = inflater.inflate(R.layout.fragment_document,
                null, true);
        //footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        newsLetterListView = (ListView)viewFragment.findViewById(R.id.fragment_news_list_view);
        //newsLetterListView.addFooterView(footerView, null, false);
        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(DocumentFragment.this);

        newsLetterList = DocumentManager.docsList;
        newsLetterListViewAdapter = new DocumentsViewAdapter(getActivity(), newsLetterList);
        newsLetterListView.setAdapter(newsLetterListViewAdapter);
        newsLetterListView.setOnScrollListener(this);

        if (newsLetterList.size() <= 0){
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
//            swipeRefreshLayout.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            swipeRefreshLayout.setRefreshing(true);

                                           // populateDummyData();
//                                        }
//                                    }
//            );

            //populateDummyData();

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

                Toast.makeText(getActivity(), "File downloaded " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PDFViewer.class);
                intent.putExtra("PdfUrl",selectedItem.getPath());
                intent.putExtra("PdfName",selectedItem.getName());
                startActivity(intent);
            }
        });


        return viewFragment;
    }

    public void populateDummyData(){
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

        if(InternetCheck.isNetworkConnected(getActivity())) {
            pageCount = 1;
            //  String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

            HashMap object = new HashMap();
            //object.put("regId", Constants.GCM_REG_ID);
            //object.put("groupId", grpIdString);
            object.put("page", "1");

            DocumentManager.getSharedInstance().getAllDocs(object, new DocumentManager.GetDocumentManagerListener() {
                @Override
                public void onCompletion(DocumentsResponse data, AppError error) {
                    //progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    if (data != null) {
                        if (data.getStatus() == 200) {
                            if (data.getData().getDocs().size() > 0) {
                                // no totalRecord in new aPI call
                                //totalRecord = data.getData().getTotalRecords();
                                newsLetterList.clear();
                                newsLetterList.addAll(DocumentManager.docsList);
                                newsLetterListViewAdapter.notifyDataSetChanged();

                            } else if (data.getStatus() == 401) {
                                //Logout User
                            } else {
                                //   Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        populateDummyData();

        if (DocumentFragment.this.newsLetterList.size() > 0) {
            newsLetterListViewAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onRefresh() {
        newsLetterList.clear();
        newsLetterListViewAdapter.notifyDataSetChanged();
      //  populateDummyData(1);
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

        if (totalRecord == newsLetterList.size()){

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

       // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
        //object.put("regId", Constants.GCM_REG_ID);
        //object.put("groupId", grpIdString);
        object.put("page", pageCount+"");

        DocumentManager.getSharedInstance().getAllDocs(object, new DocumentManager.GetDocumentManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse data, AppError error) {
                isLoading = false;
                if (data != null) {
                    if (data.getStatus() == 200) {
                        if (data.getData().getDocs().size() > 0) {
                            newsLetterList.addAll(DocumentManager.docsList);
                            newsLetterListViewAdapter.notifyDataSetChanged();

                        } else {
                         //   Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

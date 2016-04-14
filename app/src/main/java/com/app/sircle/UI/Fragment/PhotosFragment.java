package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.sircle.Manager.DocumentManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddAlbumActivity;
import com.app.sircle.UI.Activity.AlbumDetailsActivity;
import com.app.sircle.UI.Adapter.PhotosListViewAdapter;
import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.Photo;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.AlbumResponse;
import com.app.sircle.WebService.PhotoResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 23/07/15.
 */
public class PhotosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener{

    private ListView albumListView;
    private PhotosListViewAdapter photosListViewAdapter;
    private List<Photo> photos = new ArrayList<Photo>();
    private View footerView;
    private FloatingActionButton floatingActionButton;
    private View viewFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static int albumId;
    public static String albumName="";
    int totalRecord;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount;
    boolean isLoading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pageCount = 1;
        viewFragment = inflater.inflate(R.layout.fragment_photos, null , true);

        photos = PhotoManager.getSharedInstance().albumsList;
        albumListView = (ListView)viewFragment.findViewById(R.id.fragment_photos_list_view);
        photosListViewAdapter = new PhotosListViewAdapter(getActivity(), photos);
        albumListView.setAdapter(photosListViewAdapter);
        albumListView.setOnScrollListener(this);

        //swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setOnRefreshListener(PhotosFragment.this);

        if (PhotoManager.getSharedInstance().albumsList.size() <= 0){
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
//            swipeRefreshLayout.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            swipeRefreshLayout.setRefreshing(true);

                                            populateDummyData();
//                                        }
//                                    }
//            );
        }

        floatingActionButton = (FloatingActionButton)viewFragment.findViewById(R.id.fab);
        albumListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumId = photos.get(position).getAlbumID();
                albumName = photos.get(position).getAlbumTitle();
                populateAlbumData(photos.get(position).getAlbumID());

            }
        });

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        albumListView.addFooterView(footerView, null, false);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add album api call

                Intent albumIntent = new Intent(getActivity(), AddAlbumActivity.class);
                startActivity(albumIntent);

            }
        });

        return viewFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateDummyData();
    }

    private void populateDummyData() {

        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ((RelativeLayout)viewFragment).addView(progressBar, params);

//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.getSharedInstance().grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

        String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap map = new HashMap();
        map.put("regId", Constants.GCM_REG_ID);
        map.put("groupId", grpIdString);
        map.put("page",1);

        PhotoManager.getSharedInstance().getAlbums(map, new PhotoManager.GetAlbumsManagerListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {
                //isLoading = false;
                progressBar.setVisibility(View.GONE);
                //swipeRefreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.getStatus() == 200) {
                        if (response.getData().getAlbums().size() > 0) {
                          //  photos = response.getData().getAlbums();
                          //  photosListViewAdapter.notifyDataSetChanged();
                            totalRecord = response.getData().getTotalRecords();
                            photos.clear();
                            photos.addAll(PhotoManager.albumsList);
                            photosListViewAdapter.notifyDataSetChanged();

                        }
                    } else {
                            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Check internet connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void populateAlbumData(int albumId){

        HashMap params = new HashMap();
        params.put("album_id",albumId);
        params.put("page",1);

        PhotoManager.getSharedInstance().getImages(params, new PhotoManager.GetPhotosManagerListener() {
            @Override
            public void onCompletion(AlbumResponse response, AppError error) {
                Intent albumIntent = new Intent(getActivity(), AlbumDetailsActivity.class);
                //albumIntent.putExtra("albumId",photos.get(position).getAlbumID());
                //albumIntent.putExtra("albumName",photos.get(position).getAlbumTitle());
                startActivity(albumIntent);
            }
        });
    }


    @Override
    public void onRefresh() {
        //populateDummyData();
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

        if (totalRecord == photos.size()){

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

        pageCount = pageCount +1;
        String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }
        HashMap map = new HashMap();
        map.put("regId", Constants.GCM_REG_ID);
        map.put("groupId", grpIdString);
        map.put("page",pageCount);

        PhotoManager.getSharedInstance().getAlbums(map, new PhotoManager.GetAlbumsManagerListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {
                isLoading = false;
                if (response != null) {
                    if (response.getStatus() == 200) {
                        if (response.getData().getAlbums().size() > 0) {
                            photos.addAll(PhotoManager.albumsList);
                            photosListViewAdapter.notifyDataSetChanged();

                        }
                    } else {
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Check internet connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

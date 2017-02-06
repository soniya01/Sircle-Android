package com.snaptech.ezeego1.UI.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.snaptech.ezeego1.Manager.PhotoManager;
import com.snaptech.ezeego1.R;
import com.snaptech.ezeego1.UI.Activity.AddAlbumActivity;
import com.snaptech.ezeego1.UI.Activity.AlbumDetailsActivity;
import com.snaptech.ezeego1.UI.Adapter.PhotosListViewAdapter;
import com.snaptech.ezeego1.UI.Model.Photo;
import com.snaptech.ezeego1.Utility.AppError;
import com.snaptech.ezeego1.Utility.Constants;
import com.snaptech.ezeego1.WebService.PhotoResponse;

import java.util.ArrayList;
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
    private boolean flag_camera_permission=false;
    private SharedPreferences loginSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pageCount = 1;
        viewFragment = inflater.inflate(R.layout.fragment_photos, null , true);

        photos = PhotoManager.getSharedInstance().albumsList;
        albumListView = (ListView)viewFragment.findViewById(R.id.fragment_photos_list_view);
        photosListViewAdapter = new PhotosListViewAdapter(getActivity(), photos);
        albumListView.setAdapter(photosListViewAdapter);
        albumListView.setOnScrollListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(PhotosFragment.this);

        if (PhotoManager.getSharedInstance().albumsList.size() <= 0){
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
//            swipeRefreshLayout.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            swipeRefreshLayout.setRefreshing(true);

            //   populateDummyData();
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

        floatingActionButton = (FloatingActionButton)viewFragment.findViewById(R.id.fab);

        loginSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if (!userType.equals("admin"))
        {
            floatingActionButton.setVisibility(View.GONE);
        }

        albumListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                albumId = photos.get(position).getAlbumID();
                albumName = photos.get(position).getAlbumTitle();
                populateAlbumData(photos.get(position).getAlbumID());

                System.out.println("Photos "+photos.size());

            }
        });

//        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
//        albumListView.addFooterView(footerView, null, false);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add album api call


                //check permission

                if(!checkCameraPermission()) {
                    flag_camera_permission=false;
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

                }
                else
                    flag_camera_permission=true;

                if(flag_camera_permission) {
                    Intent albumIntent = new Intent(getActivity(), AddAlbumActivity.class);
                    startActivity(albumIntent);
                }
                else{

                    // Toast.makeText(getActivity(),"Please give Camera permission",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return viewFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        // populateDummyData();
    }

    private void populateDummyData() {

        pageCount=1;
//        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        ((RelativeLayout)viewFragment).addView(progressBar, params);

//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.getSharedInstance().grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

        //String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap map = new HashMap();
        //  map.put("regId", Constants.GCM_REG_ID);
        //  map.put("groupId", grpIdString);
        map.put("page","1");

        PhotoManager.getSharedInstance().getAlbums(map, new PhotoManager.GetAlbumsManagerListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {
                //isLoading = false;
                // progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (Constants.flag_logout) {

                    //  Toast.makeText(getActivity(), "Session Timed Out! Please reopen the app to Login again.", Toast.LENGTH_LONG).show();
                    Constants.flag_logout = false;
                } else {
                    if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                        if (response != null) {
                            if (response.getStatus() == 200) {
                                if (response.getData().getAlbums().size() > 0) {
                                    //  photos = response.getData().getAlbums();
                                    //  photosListViewAdapter.notifyDataSetChanged();
                                    //   totalRecord = response.getData().getTotalRecords();
                                    photos.clear();
                                    photos.addAll(PhotoManager.albumsList);
                                    photosListViewAdapter.notifyDataSetChanged();

                                }
                            } else {
                                //  Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Check internet connectivity", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    void populateAlbumData(int albumId){

//        HashMap params = new HashMap();
//        params.put("album_id",albumId);

        // params.put("page","1");

//        PhotoManager.getSharedInstance().getImages(params, new PhotoManager.GetPhotosManagerListener() {
//            @Override
//            public void onCompletion(AlbumResponse response, AppError error) {
//                Intent albumIntent = new Intent(getActivity(), AlbumDetailsActivity.class);
//                //albumIntent.putExtra("albumId",photos.get(position).getAlbumID());
//                //albumIntent.putExtra("albumName",photos.get(position).getAlbumTitle());
//                startActivity(albumIntent);
//            }
//        });
        Intent albumIntent = new Intent(getActivity(), AlbumDetailsActivity.class);
        //albumIntent.putExtra("albumId",photos.get(position).getAlbumID());
        //albumIntent.putExtra("albumName",photos.get(position).getAlbumTitle());
        albumIntent.putExtra("albumId",""+albumId);
        startActivity(albumIntent);

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
        // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }
        HashMap map = new HashMap();
        // map.put("regId", Constants.GCM_REG_ID);
        // map.put("groupId", grpIdString);
        map.put("page",""+pageCount);
        System.out.println("page count in photos fragment is "+pageCount);
        PhotoManager.getSharedInstance().getAlbums(map, new PhotoManager.GetAlbumsManagerListener() {
            @Override
            public void onCompletion(PhotoResponse response, AppError error) {
                isLoading = false;
                System.out.println("Albums list status "+response.getStatus());
                if (response != null) {
                    if (response.getStatus() == 200) {
                        if (response.getData().getAlbums().size() > 0) {

                            photos.addAll(response.getData().getAlbums());
                            photosListViewAdapter.notifyDataSetChanged();

                        }
                    } else {
                        //  Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Check internet connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkCameraPermission()
    {

        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                System.out.println("First condition");
                return true;
            } else {

                System.out.println("Second condition");
                //Log.v(TAG,"Permission is revoked");
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            // Log.v(TAG,"Permission is granted");
            System.out.println("Third condition");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    Intent albumIntent = new Intent(getActivity(), AddAlbumActivity.class);
                    startActivity(albumIntent);
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
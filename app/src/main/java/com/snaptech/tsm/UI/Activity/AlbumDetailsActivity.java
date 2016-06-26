package com.snaptech.tsm.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.snaptech.tsm.Manager.PhotoManager;
import com.snaptech.tsm.R;
import com.snaptech.tsm.UI.Adapter.AlbumDetailsGridAdapter;
import com.snaptech.tsm.UI.Fragment.PhotosFragment;
import com.snaptech.tsm.UI.Model.AlbumDetails;
import com.snaptech.tsm.UI.cam.activity.CameraActivity;
import com.snaptech.tsm.Utility.AppError;
import com.snaptech.tsm.Utility.Constants;
import com.snaptech.tsm.WebService.AlbumResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private GridView albumGridView;
    private AlbumDetailsGridAdapter albumDetailsGridAdapter;
    public List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private FloatingActionButton floatingActionButton;
    public static int albumId;
    public static String albumName="";
  //  public static ProgressDialog ringProgressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount;
            //, totalRecord;
    boolean isLoading;

    private SharedPreferences loginSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        pageCount =1;

        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setOnRefreshListener(this);

        if (getIntent() != null){
            String id = getIntent().getStringExtra("albumId");
            albumId = Integer.parseInt(id);
           // albumName = getIntent().getStringExtra("albumName");
        }

       // albumId =PhotosFragment.albumId;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(PhotosFragment.albumName);


        albumGridView = (GridView)findViewById(R.id.album_details_grid_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

       // ringProgressDialog = ProgressDialog.show(AlbumDetailsActivity.this, "", "", true);

        //albumDetailsList.addAll(PhotoManager.getSharedInstance().albumDetailsList);

//        albumGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//                currentScrollState = scrollState;
//                isScrollCompleted();
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//
////                int lastInScreen = firstVisibleItem + visibleItemCount;
////                if ((lastInScreen == totalItemCount) && !(false)) {
////                  //  Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();
////                    System.out.println("Load More");
////                }
//                currentFirstVisibleItem = firstVisibleItem;
//                currentVisibleItemCount = visibleItemCount;
//            }
//
//
//        });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(AlbumDetailsActivity.this, CameraActivity.class);
                albumIntent.putExtra("albumId", ""+albumId);
                startActivity(albumIntent);
            }
        });

        loginSharedPreferences = getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if (!userType.equals("admin"))
        {
            floatingActionButton.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);
        if (getIntent().getExtras() != null)
        {
            Bundle b = getIntent().getExtras();
            albumId = b.getInt("albumId");
            albumDetailsList.clear();
            pageCount = 0;
            loadMoreData();
//            shouldSelectListViewItem = true;
//            didSelectListViewItemAtIndex(selectedModuleIndex);
        }

    }

    private void isScrollCompleted() {

//        if (totalRecord == albumDetailsList.size()){
//
//        }else {
//            if (this.currentVisibleItemCount > 0 && this.currentScrollState == 0) {
//                /*** In this way I detect if there's been a scroll which has completed ***/
//                /*** do the work for load more date! ***/
//                System.out.println("Load not");
//                if(!isLoading){
//                    isLoading = true;
//                    System.out.println("Load More");
//                    loadMoreData();
//                    // Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }

    }

    public void loadMoreData()
    {
        final ProgressBar progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ((RelativeLayout)findViewById(R.id.swipe_refresh_layout)).addView(progressBar, layoutParams);

        pageCount = pageCount + 1;

        HashMap params = new HashMap();
        params.put("album_id",""+albumId);
       // params.put("page",pageCount);

       // System.out.println("REG" + Constants.GCM_REG_ID);


        PhotoManager.getSharedInstance().getImages(params, new PhotoManager.GetPhotosManagerListener() {
            @Override
            public void onCompletion(AlbumResponse response, AppError error) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    if (response.getStatus() == 200) {
                        if (response.getData().getAlbum_images().size() > 0) {

                            //albumDetailsList = PhotoManager.getSharedInstance().albumDetailsList;
                            //albumDetailsList.addAll(PhotoManager.getSharedInstance().albumDetailsList);

                            // if (albumDetailsList.size() > 0){
                           // albumDetailsList.clear();
                           // totalRecord = response.getData().getTotalRecords();
                            if (albumDetailsList.size() == 0) {
                                albumDetailsList.addAll(response.getData().getAlbum_images());
                                albumDetailsGridAdapter = new AlbumDetailsGridAdapter(AlbumDetailsActivity.this, albumDetailsList);
                                albumGridView.setAdapter(albumDetailsGridAdapter);
                            } else{
                                albumDetailsList.addAll(response.getData().getAlbum_images());
                                albumDetailsGridAdapter.notifyDataSetChanged();
                            }


//                            }else {
//                                albumDetailsList.addAll(response.getData().getPhotos());
//                                albumDetailsGridAdapter = new AlbumDetailsGridAdapter(AlbumDetailsActivity.this, albumDetailsList);
//                                albumGridView.setAdapter(albumDetailsGridAdapter);
//                            }

                        } else {
                           // ringProgressDialog.dismiss();
                           // AlbumDetailsActivity.ringProgressDialog.dismiss();
                         //   Toast.makeText(AlbumDetailsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                       // ringProgressDialog.dismiss();

                       // Toast.makeText(AlbumDetailsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //ringProgressDialog.dismiss();
                   // AlbumDetailsActivity.ringProgressDialog.dismiss();
                    //Toast.makeText(AlbumDetailsActivity.this, "Some problem occurred.", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }



//    void populateDummyData(){
//
//        HashMap params = new HashMap();
//        params.put("album_id",PhotosFragment.albumId);
//        params.put("page",1);
//
//        PhotoManager.getSharedInstance().getImages(params, new PhotoManager.GetPhotosManagerListener() {
//            @Override
//            public void onCompletion(AlbumResponse response, AppError error) {
//                if (response != null){
//                    if (response.getStatus() == 200){
//                        if (response.getData().getPhotos().size() > 0){
//
//                            //albumDetailsList = PhotoManager.getSharedInstance().albumDetailsList;
//                            //albumDetailsList.addAll(PhotoManager.getSharedInstance().albumDetailsList);
//
//                           // if (albumDetailsList.size() > 0){
//                                albumDetailsList.clear();
//                                albumDetailsList.addAll(response.getData().getPhotos());
//                                albumDetailsGridAdapter.notifyDataSetChanged();
////                            }else {
////                                albumDetailsList.addAll(response.getData().getPhotos());
////                                albumDetailsGridAdapter = new AlbumDetailsGridAdapter(AlbumDetailsActivity.this, albumDetailsList);
////                                albumGridView.setAdapter(albumDetailsGridAdapter);
////                            }
//
//                        }else {
//                            Toast.makeText(AlbumDetailsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    }else {
//                        Toast.makeText(AlbumDetailsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(AlbumDetailsActivity.this, "Some problem occurred", Toast.LENGTH_SHORT).show();
//                }
//                AlbumDetailsActivity.ringProgressDialog.dismiss();
//            }
//        });
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view

        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        //populateDummyData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        albumDetailsList.clear();
        pageCount = 0;
        loadMoreData();

        //if (albumDetailsList.size() <= 0){
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
//            swipeRefreshLayout.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            swipeRefreshLayout.setRefreshing(true);

            //populateDummyData();
//                                        }
//                                    }
//            );

        //}
    }
}

package com.snaptech.tsm.UI.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.snaptech.tsm.Manager.PhotoManager;
import com.snaptech.tsm.R;
import com.snaptech.tsm.UI.Adapter.AlbumDetailsGridAdapter;
import com.snaptech.tsm.UI.Fragment.PhotosFragment;
import com.snaptech.tsm.UI.Model.AlbumDetails;
import com.snaptech.tsm.UI.cam.activity.CameraActivity;
import com.snaptech.tsm.Utility.AppError;
import com.snaptech.tsm.Utility.Constants;
import com.snaptech.tsm.Utility.InternetCheck;
import com.snaptech.tsm.WebService.AlbumResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private GridView albumGridView;
    private AlbumDetailsGridAdapter albumDetailsGridAdapter;
    public List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private FloatingActionButton floatingActionButton;
    public static int albumId=0;
    private boolean flag_camera_permission=false;
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
            PhotosFragment.albumName="Album";
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

                if(!checkCameraPermission()) {
                    flag_camera_permission=false;
                    ContextCompat.checkSelfPermission(AlbumDetailsActivity.this, Manifest.permission.CAMERA);
                }
                else
                    flag_camera_permission=true;

                if(flag_camera_permission) {
                    Intent albumIntent = new Intent(AlbumDetailsActivity.this, CameraActivity.class);
                    albumIntent.putExtra("albumId", "" + albumId);
                    startActivity(albumIntent);
                }
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
            if(InternetCheck.isNetworkConnected(AlbumDetailsActivity.this))
                loadMoreData();
            else
                Toast.makeText(AlbumDetailsActivity.this,"Sorry! Please Check your Internet Connection.", Toast.LENGTH_SHORT).show();
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

                if (Constants.flag_logout) {
                    handleSharedPreferencesOnLogout();
                    Intent intent = new Intent(AlbumDetailsActivity.this, LoginScreen.class);
                    startActivity(intent);
                    Toast.makeText(AlbumDetailsActivity.this, "Please Login again.", Toast.LENGTH_LONG).show();
                    finish();
                    Constants.flag_logout = false;
                } else {
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
                                } else {
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
        if(InternetCheck.isNetworkConnected(AlbumDetailsActivity.this))
            loadMoreData();
        else
            Toast.makeText(AlbumDetailsActivity.this,"Sorry! Please Check your Internet Connection.",Toast.LENGTH_SHORT).show();

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
    public void handleSharedPreferencesOnLogout()
    {
//        LoginManager.getSharedInstance().logout(new LoginManager.LoginManagerListener() {
//            @Override
//            public void onCompletion(LoginResponse response, AppError error) {
//
//            }
//        });
        SharedPreferences loginSharedPrefs = AlbumDetailsActivity.this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = loginSharedPrefs.edit();
        editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);
        editor.apply();
    }
    private boolean checkCameraPermission()
    {

        if (Build.VERSION.SDK_INT >= 23) {
            if (AlbumDetailsActivity.this.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                System.out.println("First condition");
                return true;
            } else {

                System.out.println("Second condition");
                //Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(AlbumDetailsActivity.this,new String[]{Manifest.permission.CAMERA}, 1);
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
        System.out.println("Called request permission");
        switch (requestCode) {


            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    System.out.println("Inside case 1");

                    Intent albumIntent = new Intent(AlbumDetailsActivity.this, CameraActivity.class);
                    albumIntent.putExtra("albumId", "" + albumId);
                    startActivity(albumIntent);
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Toast.makeText(AlbumDetailsActivity.this,"Please give camera permission to upload an image.",Toast.LENGTH_SHORT).show();
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
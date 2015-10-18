package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.AlbumDetailsGridAdapter;
import com.app.sircle.UI.Fragment.PhotosFragment;
import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.AlbumResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AlbumDetailsActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener{

    private GridView albumGridView;
    private AlbumDetailsGridAdapter albumDetailsGridAdapter;
    public List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private FloatingActionButton floatingActionButton;
    public static int albumId;
    public static String albumName="";

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setOnRefreshListener(this);

//        if (getIntent() != null){
//            albumId = getIntent().getIntExtra("albumId",0);
//            albumName = getIntent().getStringExtra("albumName");
//        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        albumGridView = (GridView)findViewById(R.id.album_details_grid_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        albumDetailsList.addAll(PhotoManager.getSharedInstance().albumDetailsList);
        albumDetailsGridAdapter = new AlbumDetailsGridAdapter(AlbumDetailsActivity.this, albumDetailsList);
        albumGridView.setAdapter(albumDetailsGridAdapter);

        if (albumDetailsList.size() <= 0){
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(AlbumDetailsActivity.this, AddPhotoTabbedActivity.class);
                startActivity(albumIntent);
            }
        });

    }

    void populateDummyData(){

        HashMap params = new HashMap();
        params.put("album_id",PhotosFragment.albumId);
        params.put("page",1);

        PhotoManager.getSharedInstance().getImages(params, new PhotoManager.GetPhotosManagerListener() {
            @Override
            public void onCompletion(AlbumResponse response, AppError error) {
                if (response != null){
                    if (response.getStatus() == 200){
                        if (response.getData().getPhotos().size() > 0){

                            //albumDetailsList = PhotoManager.getSharedInstance().albumDetailsList;
                            //albumDetailsList.addAll(PhotoManager.getSharedInstance().albumDetailsList);

                           // if (albumDetailsList.size() > 0){
                                albumDetailsList.clear();
                                albumDetailsList.addAll(response.getData().getPhotos());
                                albumDetailsGridAdapter.notifyDataSetChanged();
//                            }else {
//                                albumDetailsList.addAll(response.getData().getPhotos());
//                                albumDetailsGridAdapter = new AlbumDetailsGridAdapter(AlbumDetailsActivity.this, albumDetailsList);
//                                albumGridView.setAdapter(albumDetailsGridAdapter);
//                            }

                        }else {
                            Toast.makeText(AlbumDetailsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(AlbumDetailsActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AlbumDetailsActivity.this, "Some problem occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
}

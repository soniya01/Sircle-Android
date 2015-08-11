package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.GridView;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.AlbumDetailsGridAdapter;
import com.app.sircle.UI.Model.AlbumDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumDetailsActivity extends ActionBarActivity {

    private GridView albumGridView;
    private AlbumDetailsGridAdapter albumDetailsGridAdapter;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        populateDummyData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        albumGridView = (GridView)findViewById(R.id.album_details_grid_view);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        albumDetailsGridAdapter = new AlbumDetailsGridAdapter(this, albumDetailsList);
        albumGridView.setAdapter(albumDetailsGridAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(AlbumDetailsActivity.this, AddPhotoTabbedActivity.class);
                startActivity(albumIntent);
            }
        });

    }

    void populateDummyData(){

        AlbumDetails albumDetails = new AlbumDetails();
        albumDetails.setPhotoID(1);
        albumDetails.setPhotoCaption("Nature");
        albumDetails.setPublishDate(new Date());

        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
    }

}

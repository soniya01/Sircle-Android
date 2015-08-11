package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
}

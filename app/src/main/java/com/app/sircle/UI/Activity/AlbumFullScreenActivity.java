package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.AlbumImagePagerAdapter;
import com.app.sircle.UI.Model.AlbumDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlbumFullScreenActivity extends ActionBarActivity {

    private ViewPager imageViewPager;
    private AlbumImagePagerAdapter albumImagePagerAdapter;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        populateDummyData();
        setContentView(R.layout.activity_album_full_screen);
        imageViewPager = (ViewPager)findViewById(R.id.pager);

        int position = getIntent().getIntExtra("position",0);
        getSupportActionBar().setTitle("");

        albumImagePagerAdapter = new AlbumImagePagerAdapter(this, albumDetailsList);
        imageViewPager.setAdapter(albumImagePagerAdapter);
        imageViewPager.setCurrentItem(position);
    }

    void populateDummyData(){


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_full_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Activate the navigation drawer toggle
        if (item.getItemId() == R.id.action_download) {
            //TODO: download
            int position = imageViewPager.getCurrentItem();
            return true;
        }if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.AlbumImagePagerAdapter;
import com.app.sircle.UI.Model.AlbumDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlbumFullScreenActivity extends Activity {

    private ViewPager imageViewPager;
    private AlbumImagePagerAdapter albumImagePagerAdapter;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateDummyData();
        setContentView(R.layout.activity_album_full_screen);
        imageViewPager = (ViewPager)findViewById(R.id.pager);

        int position = getIntent().getIntExtra("position",0);

        albumImagePagerAdapter = new AlbumImagePagerAdapter(this, albumDetailsList);
        imageViewPager.setAdapter(albumImagePagerAdapter);
        imageViewPager.setCurrentItem(position);

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
    public void onBackPressed() {
        finish();
    }
}

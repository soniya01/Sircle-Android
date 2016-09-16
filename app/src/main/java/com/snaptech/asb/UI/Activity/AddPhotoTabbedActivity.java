package com.snaptech.asb.UI.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;

import com.snaptech.asb.R;
import com.snaptech.asb.UI.Fragment.CameraFragmentUI;
import com.snaptech.asb.UI.Fragment.GalleryFragment;

public class AddPhotoTabbedActivity extends ActionBarActivity {

    private FragmentTabHost mTabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_tabbed);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Click Photo", null),CameraFragmentUI.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Pick from Gallery", null), GalleryFragment.class, null);
    }


    @Override
    public void onBackPressed() {
        finish();
    }


}

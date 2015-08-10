package com.app.sircle.UI.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.app.sircle.R;
import com.app.sircle.UI.Fragment.CameraFragment;
import com.app.sircle.UI.Fragment.GalleryFragment;

public class AddPhotoTabbedActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_tabbed);


        //getActionBar().setDisplayShowHomeEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Tab 1", null),CameraFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Tab 2", null), GalleryFragment.class, null);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}

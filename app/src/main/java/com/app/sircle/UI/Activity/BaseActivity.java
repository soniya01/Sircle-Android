package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

import com.app.sircle.R;
import com.app.sircle.UI.Fragment.DocumentFragment;
import com.app.sircle.UI.Fragment.HomeFragment;
import com.app.sircle.UI.Fragment.LinksFragment;
import com.app.sircle.UI.Fragment.NewsLetterFragment;
import com.app.sircle.UI.Fragment.NotificationFragment;
import com.app.sircle.UI.Fragment.PhotosFragment;
import com.app.sircle.UI.Fragment.SettingsFragment;
import com.app.sircle.UI.Fragment.VideoFragment;
import com.app.sircle.UI.SlidingPane.SlidingPaneFragment;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.Common;

import java.lang.reflect.Field;


public class BaseActivity extends Activity implements  SlidingPaneInterface, SlidingPaneFragment.SlidingPanDelegate{

    private final static String SHOULD_SELECT_LIST_VIEW_ITEM = "shouldSelectListViewItem";
    private final static String SELECTED_MODULE = "selectedModuleIndex";
    private static Integer selectedModuleIndex;
    private boolean shouldSelectListViewItem = true;
    private Handler handler;
    private Fragment fragmentToLoad = null;
    private SlidingPaneLayout slidingPan;
    private Intent nonFragment ;

    private static void loadFragment(Context context, Fragment fragment) {

        FragmentManager fragmentManager = ((Activity) context)
                .getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragment != null) {

            transaction.replace(R.id.main_layout_container, fragment);
        }
        transaction.commit();
        fragmentManager.executePendingTransactions();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOULD_SELECT_LIST_VIEW_ITEM, false);
        outState.putInt(SELECTED_MODULE, selectedModuleIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedModuleIndex = -1;

        if (savedInstanceState != null) {
            shouldSelectListViewItem = savedInstanceState
                    .getBoolean(SHOULD_SELECT_LIST_VIEW_ITEM);
            selectedModuleIndex = savedInstanceState.getInt(SELECTED_MODULE);
        }

        setContentView(R.layout.activity_base);

        this.slidingPan = (SlidingPaneLayout) findViewById(R.id.slidingPane);

        //setSlidingPaneGapWidth(this.slidingPan, 50);

        this.slidingPan.setPanelSlideListener(new PaneListener());

        //Load the first fragment (Home) for the first time
        if (shouldSelectListViewItem == true) {
            this.didSelectListViewItemAtIndex(0);
            this.slidingPan.openPane();
        }

    }

    //SlidingPaneInterface method

    @Override
    public void tappedDrawerIcon() {
        slidingPan.openPane();
    }

    /**
     * Sets gaps to be keep between pane and child view
     *
     * @param slidingPan
     * @param gapWidth
     */
    private void setSlidingPaneGapWidth(SlidingPaneLayout slidingPan, int gapWidth) {
        try {
            Field slidingPaneGapWidth = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            slidingPaneGapWidth.setAccessible(true);
            slidingPaneGapWidth.setInt(slidingPan, gapWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // SlidingPaneLayout Listener
    private class PaneListener implements SlidingPaneLayout.PanelSlideListener {

        @Override
        public void onPanelClosed(View view) {

        }

        @Override
        public void onPanelOpened(View view) {

        }

        @Override
        public void onPanelSlide(View view, float arg1) {
        }
    }

    /**
     * *** Sliding Pane Fragment *****
     */
    @Override
    public void didSelectListViewItemAtIndex(Integer index) {
        // To avoid loading same fragment again

        selectedModuleIndex = index;

        // Replace the MainFragment with the Fragment that needs to be loaded
        // after it is ready
        switch (index) {
            case 0:
                // Not loading any fragment for breaking new as it is already
                // added
                // in the layout
                // In this case fragmentToLoad = null and the previous fragment
                // will
                // be removed from the container
                fragmentToLoad = new HomeFragment();
                break;
            case 1:
                fragmentToLoad = new HomeFragment();
                break;
            case 2:
                fragmentToLoad = new PhotosFragment();
                break;
            case 3:
                fragmentToLoad = new NotificationFragment();
                break;
            case 4:
                fragmentToLoad = new NewsLetterFragment();
                break;
            case 5:
                fragmentToLoad = new DocumentFragment();
                break;
            case 6:
                fragmentToLoad = new VideoFragment();
                break;
            case 7:
                fragmentToLoad = new LinksFragment();
                break;
            case 8:
                fragmentToLoad = new SettingsFragment();
                break;
            case 9:
                fragmentToLoad = null;
                // support email clickable
                //nonFragment = new Intent(this, )
                finish();
                Common.sendEmailToSupport(this);
                break;
            case 10:
                // add sign out functionality and show LoginScreen
                fragmentToLoad = null;
                finish();
            default:
                break;
        }
        // setTitle(moduleTitles[index]);

        if (fragmentToLoad != null){
            handler = new Handler();
            loadFragment(BaseActivity.this, fragmentToLoad);

        }
        handler.post(new Runnable() {

            @Override
            public void run() {
                slidingPan.closePane();
            }
        });


    }

    @Override
    public void onBackPressed() {

    }
}

package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.R;
import com.app.sircle.UI.Fragment.CalendarFragment;
import com.app.sircle.UI.Fragment.CalendarListFragment;
import com.app.sircle.UI.Fragment.CalendarMonthFragment;
import com.app.sircle.UI.Fragment.CalendarTodayFragment;
import com.app.sircle.UI.Fragment.DocumentFragment;
import com.app.sircle.UI.Fragment.HomeFragment;
import com.app.sircle.UI.Fragment.LinksFragment;
import com.app.sircle.UI.Fragment.NewsLetterFragment;
import com.app.sircle.UI.Fragment.NotificationFragment;
import com.app.sircle.UI.Fragment.PhotosFragment;
import com.app.sircle.UI.Fragment.SettingsFragment;
import com.app.sircle.UI.Fragment.MyTabFragment;
import com.app.sircle.UI.Fragment.VideoFragment;
import com.app.sircle.UI.SlidingPane.SlidingPaneAdapter;
import com.app.sircle.Utility.Common;
import com.app.sircle.Utility.Constants;

import java.util.Date;


public class BaseActivity extends ActionBarActivity implements CalendarMonthFragment.OnFragmentInteractionListener,CalendarTodayFragment.OnFragmentInteractionListener,CalendarListFragment.OnFragmentInteractionListener {

    private final static String SHOULD_SELECT_LIST_VIEW_ITEM = "shouldSelectListViewItem";
    private final static String SELECTED_MODULE = "selectedModuleIndex";
    public static Integer selectedModuleIndex;
    private boolean shouldSelectListViewItem = true;
    private Fragment fragmentToLoad = null;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] menuList;
    public static boolean jumpToFragment;
    private  Intent loginIntent = null;

    private void loadFragment(Context context, Fragment fragment) {

        FragmentManager fragmentManager = ((Activity) context)
                .getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragment != null) {

            transaction.replace(R.id.main_layout_container, fragment);

        }
        transaction.commit();
        fragmentManager.executePendingTransactions();

        mDrawerList.setItemChecked(selectedModuleIndex, true);
        mDrawerList.setSelection(selectedModuleIndex);
        if (selectedModuleIndex == 9){
            setTitle(menuList[0]);
        }else setTitle(menuList[selectedModuleIndex]);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (jumpToFragment)
        {
            jumpToFragment = false;
            this.didSelectListViewItemAtIndex(2);
        }

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

        checkIfSessionExpired();

        if (selectedModuleIndex == null || selectedModuleIndex == 0){
            selectedModuleIndex = -1;
        }else {
            shouldSelectListViewItem = true;
        }

        if (savedInstanceState != null) {
            shouldSelectListViewItem = savedInstanceState
                    .getBoolean(SHOULD_SELECT_LIST_VIEW_ITEM);
            selectedModuleIndex = savedInstanceState.getInt(SELECTED_MODULE);
        }
//""
        // if notifictaion is clicked
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            selectedModuleIndex = b.getInt("notificationActivity");
            shouldSelectListViewItem = true;
        }

        setContentView(R.layout.activity_base);

        mDrawerList = (ListView)findViewById(R.id.main_menu_listview);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        menuList = getResources().getStringArray(
                R.array.array_module_name);

        SlidingPaneAdapter adapter = new SlidingPaneAdapter(this,menuList);
        mDrawerList.setAdapter(adapter);

        if (shouldSelectListViewItem){
            if (selectedModuleIndex != -1){
                didSelectListViewItemAtIndex(selectedModuleIndex);
            }else {
                didSelectListViewItemAtIndex(0);
                mDrawerList.setItemChecked(0, true);
                mDrawerList.setSelection(0);
            }
        }


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               selectedModuleIndex = position;
               mDrawerList.setItemChecked(position, true);
               mDrawerList.setSelection(position);
               BaseActivity.this.didSelectListViewItemAtIndex(position);

           }
       });

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(menuList[selectedModuleIndex]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void didSelectListViewItemAtIndex(Integer index) {
        // To avoid loading same fragment again

        selectedModuleIndex = index;

        // Replace the MainFragment with the Fragment that needs to be loaded
        // after it is ready
        switch (index) {
            case 0:
                // Not loading any fragment as it is already
                // added
                // in the layout
                // In this case fragmentToLoad = null and the previous fragment
                // will
                // be removed from the container
                fragmentToLoad = new HomeFragment();
                break;
            case 1:
                fragmentToLoad = new CalendarFragment();

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
                fragmentToLoad = new HomeFragment();
                // support email clickable
                Common.sendEmailToSupport(this);
                break;
            case 10:
                // add sign out functionality and show LoginScreen
                mDrawerLayout.closeDrawer(mDrawerList);
                fragmentToLoad = null;
                selectedModuleIndex = 0;
                handleSharedPreferencesOnLogout();
                Intent loginIntent = new Intent(BaseActivity.this, LoginScreen.class);
                startActivity(loginIntent);
            default:
                break;
        }

        if (fragmentToLoad != null) {
            loadFragment(BaseActivity.this, fragmentToLoad);

        }

    }


    public void checkIfSessionExpired(){
        SharedPreferences loginSharedPreferences = this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        long lastActivity = new Date().getTime()/1000;
        long loggedIn = loginSharedPreferences.getLong(Constants.LOGIN_LOGGED_IN_PREFS_KEY,0);
        long expiresIn = loginSharedPreferences.getLong(Constants.LOGIN_EXPIRES_IN_PREFS_KEY, 0);

        System.out.println("last Activity + logged In + expires in "+lastActivity+ " " + loggedIn + " " + expiresIn);

            if ((lastActivity - loggedIn) > expiresIn){
                Toast.makeText(this, "Session expired. Please login again!", Toast.LENGTH_SHORT).show();
                handleSharedPreferencesOnLogout();
                loginIntent = new Intent(this, LoginScreen.class);
                startActivity(loginIntent);


            }else {
               // loginIntent = new Intent(this, BaseActivity.class);
            }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view



        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // myMenuItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void handleSharedPreferencesOnLogout()
    {
        SharedPreferences loginSharedPrefs = BaseActivity.this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = loginSharedPrefs.edit();
        editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);
        editor.apply();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}

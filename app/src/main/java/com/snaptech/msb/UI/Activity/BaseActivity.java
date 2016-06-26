package com.snaptech.msb.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snaptech.msb.Manager.LoginManager;
import com.snaptech.msb.R;
import com.snaptech.msb.UI.Fragment.CalendarFragment;
import com.snaptech.msb.UI.Fragment.CalendarListFragment;
import com.snaptech.msb.UI.Fragment.CalendarMonthFragment;
import com.snaptech.msb.UI.Fragment.CalendarTodayFragment;
import com.snaptech.msb.UI.Fragment.DocumentFragment;
import com.snaptech.msb.UI.Fragment.HomeFragment;
import com.snaptech.msb.UI.Fragment.InstituteInfo;
import com.snaptech.msb.UI.Fragment.LinksFragment;
import com.snaptech.msb.UI.Fragment.NewsLetterFragment;
import com.snaptech.msb.UI.Fragment.NotificationFragment;
import com.snaptech.msb.UI.Fragment.PhotosFragment;
import com.snaptech.msb.UI.Fragment.SettingsFragment;
import com.snaptech.msb.UI.Fragment.VideoFragment;
import com.snaptech.msb.UI.SlidingPane.SlidingPaneAdapter;
import com.snaptech.msb.Utility.AppError;
import com.snaptech.msb.Utility.Common;
import com.snaptech.msb.Utility.Constants;
import com.snaptech.msb.WebService.LoginResponse;
import com.snaptech.msb.custom.RobotoRegularTextView;

import java.util.Date;
import java.util.Locale;


public class BaseActivity extends AppCompatActivity implements CalendarMonthFragment.OnFragmentInteractionListener,CalendarTodayFragment.OnFragmentInteractionListener,CalendarListFragment.OnFragmentInteractionListener {

    private final static String SHOULD_SELECT_LIST_VIEW_ITEM = "shouldSelectListViewItem";
    private final static String SELECTED_MODULE = "selectedModuleIndex";
    public static Integer selectedModuleIndex;
    private boolean shouldSelectListViewItem = true;
    private Fragment fragmentToLoad = null;
    private Fragment calendarFragmentToLoad = null;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] menuList;
    private String fragmentName;
//    public static boolean jumpToFragment;
    private  Intent loginIntent = null;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RobotoRegularTextView actionBarTitleView;
    SlidingPaneAdapter adapter;
    private SharedPreferences loginSharedPreferences;
    String userType;

    Boolean closeApp;

    private void loadFragment(Context context, android.support.v4.app.Fragment fragment ,String FragmentName) {

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();


        if (fragment != null) {
            mFragmentTransaction.replace(R.id.main_layout_container, fragment).commit();
        }


        mDrawerList.setItemChecked(selectedModuleIndex, true);
        mDrawerList.setSelection(selectedModuleIndex);
        setTitle(FragmentName);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    protected void onResume() {

        super.onResume();
        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOULD_SELECT_LIST_VIEW_ITEM, false);
        outState.putInt(SELECTED_MODULE, selectedModuleIndex);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);
        if (getIntent().getExtras() != null)
        {
            Bundle b = getIntent().getExtras();
            selectedModuleIndex = b.getInt("notificationActivity");
            shouldSelectListViewItem = true;
            didSelectListViewItemAtIndex(selectedModuleIndex);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale("es");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        closeApp = false;

        selectedModuleIndex = -1;

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
        if (getIntent().getExtras() != null)
        {
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
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

        actionBarTitleView = (RobotoRegularTextView)v.findViewById(R.id.title);
        getSupportActionBar().setCustomView(v);


        loginSharedPreferences = getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if(userType!=null) {
            if (userType.equals("admin")) {
                menuList = getResources().getStringArray(R.array.array_module_name_withou_settings);
            } else {
                menuList = getResources().getStringArray(R.array.array_module_name);
            }
        }
        else
        {
            menuList = getResources().getStringArray(R.array.array_module_name);
        }

        adapter = new SlidingPaneAdapter(this,menuList);
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
               adapter.setSelectedIndex(position);
               adapter.notifyDataSetChanged();
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
               // getSupportActionBar().setTitle(menuList[selectedModuleIndex]);
                actionBarTitleView.setText(fragmentName);
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
                fragmentName = getResources().getStringArray(R.array.array_module_name)[0];
                if (!(fragmentToLoad instanceof HomeFragment))
                {
                    fragmentToLoad = new HomeFragment();
                }
                    closeApp = true;

                break;
            case 1:
                fragmentName = getResources().getStringArray(R.array.array_module_name)[1];
                if (!(fragmentToLoad instanceof CalendarFragment))
                {
                    fragmentToLoad = new CalendarFragment();
                }
                closeApp = false;
                break;
            case 2:
                fragmentName = getResources().getStringArray(R.array.array_module_name)[2];
                if (!(fragmentToLoad instanceof PhotosFragment))
                fragmentToLoad = new PhotosFragment();
                closeApp = false;
                break;
            case 3:
                fragmentName = "Messages";
                if (!(fragmentToLoad instanceof NotificationFragment))
                fragmentToLoad = new NotificationFragment();
                closeApp = false;
                break;
            case 4:
                fragmentName = getResources().getStringArray(R.array.array_module_name)[4];
                if (!(fragmentToLoad instanceof NewsLetterFragment))
                fragmentToLoad = new NewsLetterFragment();
                break;
            case 5:
                fragmentName = getResources().getStringArray(R.array.array_module_name)[5];
                if (!(fragmentToLoad instanceof DocumentFragment))
                fragmentToLoad = new DocumentFragment();
                closeApp = false;
                break;
            case 6:
                fragmentName = getResources().getStringArray(R.array.array_module_name)[6];
                if (!(fragmentToLoad instanceof VideoFragment))
                fragmentToLoad = new VideoFragment();
                closeApp = false;
                break;
            case 7:
                fragmentName = getResources().getStringArray(R.array.array_module_name)[7];
                if (!(fragmentToLoad instanceof LinksFragment))
                fragmentToLoad = new LinksFragment();
                closeApp = false;
                break;

            case 8:
                if (userType.equals("admin")) {

                    fragmentName = getResources().getStringArray(R.array.array_module_name)[9];
                    fragmentToLoad = new InstituteInfo();
                    closeApp = false;


                }
                else
                {
                    fragmentName = getResources().getStringArray(R.array.array_module_name)[8];
                    fragmentToLoad = new SettingsFragment();
                }
                closeApp = false;
                break;
            case 9:
                if (userType.equals("admin")) {
                    Common.sendEmailToSupport(this);
                }
                else
                {
                    fragmentName = getResources().getStringArray(R.array.array_module_name)[9];
                    fragmentToLoad = new InstituteInfo();
                    closeApp = false;


                }
                closeApp = false;
                break;
            case 10:
                // add sign out functionality and show LoginScreen

                if (userType.equals("admin")) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                    fragmentToLoad = null;
                    selectedModuleIndex = 0;
                    handleSharedPreferencesOnLogout();
                    closeApp = false;
                    Intent loginIntent = new Intent(BaseActivity.this, LoginScreen.class);
                    startActivity(loginIntent);
                    finish();
                }
                else
                {
                   // mDrawerLayout.closeDrawer(mDrawerList);
                    Common.sendEmailToSupport(this);

                }
                break;
            case 11:

                mDrawerLayout.closeDrawer(mDrawerList);
                fragmentToLoad = null;
                selectedModuleIndex = 0;
                handleSharedPreferencesOnLogout();
                closeApp = false;
                Intent loginIntent = new Intent(BaseActivity.this, LoginScreen.class);
                startActivity(loginIntent);
                finish();

            default:
                break;
        }

//        if (selectedModuleIndex==1)
//        {
//            loadFragment(BaseActivity.this, calendarFragmentToLoad,fragmentName);
//        }
//
//        else

        if (fragmentToLoad != null) {
            loadFragment(BaseActivity.this, fragmentToLoad,fragmentName);

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

        if (closeApp)
        {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        else

        {
            closeApp=true;
            fragmentToLoad = new HomeFragment();
            loadFragment(BaseActivity.this, fragmentToLoad,getResources().getStringArray(R.array.array_module_name)[0]);
            setActionBarTitle(getResources().getStringArray(R.array.array_module_name)[0]);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void handleSharedPreferencesOnLogout()
    {
        LoginManager.getSharedInstance().logout(new LoginManager.LoginManagerListener() {
            @Override
            public void onCompletion(LoginResponse response, AppError error) {

            }});
        SharedPreferences loginSharedPrefs = BaseActivity.this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = loginSharedPrefs.edit();
        editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);
        editor.apply();
    }

    public void setActionBarTitle(String title) {
       View v = getSupportActionBar().getCustomView();

        TextView dummy = (TextView) v.findViewById(R.id.title);

        dummy.setText(title);
    }


    public void setFalse()
    {
        closeApp = false;
    }

    public void setFragmentName(String title)
    {
        fragmentName = title;
    }

public void setDrawerPositionFromHome(int position)
{
    adapter.setSelectedIndex(position);
    adapter.notifyDataSetChanged();
}
}

package com.snaptech.emissio.UI.Activity;

import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snaptech.emissio.BuildConfig;
import com.snaptech.emissio.Manager.ForceUpdateManager;
import com.snaptech.emissio.Manager.LoginManager;
import com.snaptech.emissio.R;
import com.snaptech.emissio.UI.Fragment.CalendarFragment;
import com.snaptech.emissio.UI.Fragment.CalendarListFragment;
import com.snaptech.emissio.UI.Fragment.CalendarMonthFragment;
import com.snaptech.emissio.UI.Fragment.CalendarTodayFragment;
import com.snaptech.emissio.UI.Fragment.DocumentFragment;
import com.snaptech.emissio.UI.Fragment.HomeFragment;
import com.snaptech.emissio.UI.Fragment.InstituteInfo;
import com.snaptech.emissio.UI.Fragment.LinksFragment;
import com.snaptech.emissio.UI.Fragment.NewsLetterFragment;
import com.snaptech.emissio.UI.Fragment.NotificationFragment;
import com.snaptech.emissio.UI.Fragment.PhotosFragment;
import com.snaptech.emissio.UI.Fragment.SettingsFragment;
import com.snaptech.emissio.UI.Fragment.VideoFragment;
import com.snaptech.emissio.UI.SlidingPane.SlidingPaneAdapter;
import com.snaptech.emissio.Utility.AppError;
import com.snaptech.emissio.Utility.Common;
import com.snaptech.emissio.Utility.Constants;
import com.snaptech.emissio.Utility.InternetCheck;
import com.snaptech.emissio.WebService.ForcedUpdateResponse;
import com.snaptech.emissio.WebService.LoginResponse;
import com.snaptech.emissio.custom.RobotoRegularTextView;

import java.util.Date;
import java.util.HashMap;


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
    int versionCode=0;
    Boolean closeApp;

    private void loadFragment(Context context, android.support.v4.app.Fragment fragment ,String FragmentName) {

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();


        if (fragment != null) {
           // mFragmentTransaction.replace(R.id.main_layout_container, fragment).addToBackStack(null).commit();

            mFragmentTransaction.replace(R.id.main_layout_container, fragment).commit();
        }



    //    fragmentManager.executePendingTransactions();

        mDrawerList.setItemChecked(selectedModuleIndex, true);
        mDrawerList.setSelection(selectedModuleIndex);
//        if (selectedModuleIndex == 9){
//            setTitle(menuList[0]);
//        }else setTitle(menuList[selectedModuleIndex]);
        setTitle(FragmentName);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    protected void onResume() {


        HashMap hashMap=new HashMap();

        if(InternetCheck.isNetworkConnected(BaseActivity.this)){
        if(Constants.flag==3) {
            ForceUpdateManager.getSharedInstance().getForcedUpdateData(hashMap, new ForceUpdateManager.GetForcedUpdateManagerListener() {
                @Override
                public void onCompletion(ForcedUpdateResponse data, AppError error) {

                    if(Constants.flag_logout){

                        handleSharedPreferencesOnLogout();
                        Intent intent=new Intent(BaseActivity.this,LoginScreen.class);
                        startActivity(intent);
                        Toast.makeText(BaseActivity.this, "Please Login again.", Toast.LENGTH_LONG).show();
                        finish();
                        Constants.flag_logout=false;
                    }else{
                    //System.out.println("Data received" + data.getMessage() + "version is" + data.getForcedUpdateData().android_version + " actual version code is " + versionCode);
                    versionCode= BuildConfig.VERSION_CODE;
                    checkForcedUpdate(data,error,versionCode);


//                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }



                }
                }
            });
        }}
        super.onResume();
//        if (jumpToFragment)
//        {
//            jumpToFragment = false;
//            this.didSelectListViewItemAtIndex(2);
//        }

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
            fragmentName=b.getString("title");
            //getActionBar().setTitle(fragmentName);
            System.out.println("Title is "+fragmentName);
            actionBarTitleView.setText(fragmentName);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        closeApp = false;

        selectedModuleIndex = -1;

     //   checkIfSessionExpired();

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

        setContentView(R.layout.activity_base);

        mDrawerList = (ListView)findViewById(R.id.main_menu_listview);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        HashMap hashMap=new HashMap();

        System.out.println("Flag is "+Constants.flag);

        //Forced Update
        if(InternetCheck.isNetworkConnected(BaseActivity.this)) {
            if (Constants.flag == 1) {
                ForceUpdateManager.getSharedInstance().getForcedUpdateData(hashMap, new ForceUpdateManager.GetForcedUpdateManagerListener() {
                    @Override
                    public void onCompletion(ForcedUpdateResponse data, AppError error) {
                        if(Constants.flag_logout){

                            handleSharedPreferencesOnLogout();
                            Intent intent=new Intent(BaseActivity.this,LoginScreen.class);
                            startActivity(intent);
                            Toast.makeText(BaseActivity.this, "Please Login again.", Toast.LENGTH_LONG).show();
                            Constants.flag_logout=false;
                            finish();
                        }
                        else{
                       // System.out.println("Data received" + data.getMessage() + "version is" + data.getForcedUpdateData().android_version + " actual version code is " + versionCode);
                        versionCode = BuildConfig.VERSION_CODE;
                        checkForcedUpdate(data, error, versionCode);


//                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }


                    }
                    }
                });
            }
        }
       // int titleId = getResources().getIdentifier("action_bar_title", "id",
                //"android");

       // final int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");

//        try {
//            Integer titleId = (Integer) Class.forName("com.android.internal.R$id")
//                    .getField("action_bar_title").get(null);
//            TextView title = (TextView) getWindow().findViewById(titleId);
//            title.setTextColor(Color.WHITE);
//            title.setTextSize(20);
//
//            // check for null and manipulate the title as see fit
//        } catch (Exception e) {
//            Log.e("Exception", "Failed to obtain action bar title reference");
//        }


//        TextView yourTextView = (TextView) findViewById(titleId);
//        yourTextView.setTextColor(Color.WHITE);
//        yourTextView.setTextSize(20);
//        yourTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/roboto-light.ttf"));

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
      //  ((RobotoRegularTextView)v.findViewById(R.id.title)).setText(this.getTitle());

        actionBarTitleView = (RobotoRegularTextView)v.findViewById(R.id.title);



//assign the view to the actionbar
        getSupportActionBar().setCustomView(v);


        // if notifictaion is clicked
        if (getIntent().getExtras() != null)
        {
            Bundle b = getIntent().getExtras();
            selectedModuleIndex = b.getInt("notificationActivity");
            shouldSelectListViewItem = true;
            fragmentName=b.getString("title");
            //getActionBar().setTitle(fragmentName);
            System.out.println("Title is "+fragmentName);
            actionBarTitleView.setText(fragmentName);
        }

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
        actionBarTitleView.setText(fragmentName);
        super.setTitle(title);

    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    //check forced update
    private void checkForcedUpdate(ForcedUpdateResponse data,AppError error,int versionCode){
        if(versionCode<data.getForcedUpdateData().android_version&&data.getForcedUpdateData().force_update_android==0){

            Constants.flag = 2;
            new AlertDialog.Builder(BaseActivity.this)

                    .setTitle("Update App")
                    .setMessage("Do you want to update the application?")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(R.drawable.emissionsloginlogo)
                    .show();
        }
        else if(versionCode<data.getForcedUpdateData().android_version&&data.getForcedUpdateData().force_update_android==1){


            Constants.flag=3;
//                        Intent intent=new Intent(BaseActivity.this,ForcedUpdateActivity.class);
//                        startActivity(intent);
//                        finish();
            new AlertDialog.Builder(BaseActivity.this)

                    .setCancelable(false)
                    .setTitle("Update App")
                    .setMessage("Please update to continue using the application. This should only take a few moments.")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    })
                    .setIcon(R.drawable.emissionsloginlogo)
                    .show();

        }
        else{
            Constants.flag = 2;
        }
    }
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
                fragmentName = "Home";
                if (!(fragmentToLoad instanceof HomeFragment))
                {
                    fragmentToLoad = new HomeFragment();
                }
                    closeApp = true;

                break;
            case 1:
                if(InternetCheck.isNetworkConnected(BaseActivity.this)){
                fragmentName = "Calendar";
                if (!(fragmentToLoad instanceof CalendarFragment))
                {
                    fragmentToLoad = new CalendarFragment();
                }
                closeApp = false;
                }
                else{
                    fragmentName = "Home";
                    if (!(fragmentToLoad instanceof HomeFragment))
                    {
                        fragmentToLoad = new HomeFragment();
                    }
                    closeApp = true;
                    Toast.makeText(BaseActivity.this,"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                fragmentName = "Photos";
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
                fragmentName = "Newsletters";
                if (!(fragmentToLoad instanceof NewsLetterFragment))
                fragmentToLoad = new NewsLetterFragment();
                closeApp=false;
                break;
            case 5:
                fragmentName = "Documents";
                if (!(fragmentToLoad instanceof DocumentFragment))
                fragmentToLoad = new DocumentFragment();
                closeApp = false;
                break;
            case 6:
                fragmentName = "Videos";
                if (!(fragmentToLoad instanceof VideoFragment))
                fragmentToLoad = new VideoFragment();
                closeApp = false;
                break;
            case 7:
                fragmentName = "Links";
                if (!(fragmentToLoad instanceof LinksFragment))
                fragmentToLoad = new LinksFragment();
                closeApp = false;
                break;

            case 8:
                if (userType.equals("admin")) {

                    fragmentName = "Institute Info";
                    fragmentToLoad = new InstituteInfo();
                    closeApp = false;


                }
                else
                {
                    fragmentName = "Settings";
                    fragmentToLoad = new SettingsFragment();
                }
                closeApp = false;
                break;
            case 9:
                if (userType.equals("admin")) {




                   // fragmentToLoad = new HomeFragment();
                //    mDrawerLayout.closeDrawer(mDrawerList);
                    Common.sendEmailToSupport(this);

//                    mDrawerLayout.closeDrawer(mDrawerList);
//                    fragmentToLoad = null;
//                    selectedModuleIndex = 0;
//                    handleSharedPreferencesOnLogout();
//                    Intent loginIntent = new Intent(BaseActivity.this, LoginScreen.class);
//                    startActivity(loginIntent);
//                    finish();
                }
                else
                {
                    fragmentName = "Institute Info";
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
            loadFragment(BaseActivity.this, fragmentToLoad,"Home");
            setActionBarTitle("Home");
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
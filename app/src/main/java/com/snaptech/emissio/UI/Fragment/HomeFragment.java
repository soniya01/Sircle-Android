package com.snaptech.emissio.UI.Fragment;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.snaptech.emissio.DownLoader.FetchAppData;
import com.snaptech.emissio.Manager.LoginManager;
import com.snaptech.emissio.Manager.NotificationManager;
import com.snaptech.emissio.R;
import com.snaptech.emissio.UI.Activity.BaseActivity;
import com.snaptech.emissio.Utility.Constants;
import com.snaptech.emissio.Utility.InternetCheck;

import java.util.Set;

/**
 * Created by soniya on 7/22/15.
 */
public class HomeFragment extends Fragment {

   // private TextView emailLabel;
  //  private ProgressBar progressBar;
    private Fragment fragmentToLoad = null;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private SharedPreferences loginSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_home_new,
                null, true);



        ((BaseActivity)getActivity())
                .setActionBarTitle("Home");

        Button calendar = (Button) viewFragment.findViewById(R.id.calendarButton);
        Typeface robotLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto-light.ttf");
        calendar.setTypeface(robotLight);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                if(InternetCheck.isNetworkConnected(getActivity())){
                fragmentToLoad = new CalendarFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad, "Calendar",1);

                }
            }
                else{
                    Toast.makeText(getActivity(),"Sorry! Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button photos = (Button) viewFragment.findViewById(R.id.photosButton);
        photos.setTypeface(robotLight);
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new PhotosFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Photos",2);

                }
            }
        });

        Button newsletter = (Button) viewFragment.findViewById(R.id.newsletterButton);
        newsletter.setTypeface(robotLight);
        newsletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new NewsLetterFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Newsletters",4);

                }
            }
        });

        Button documents = (Button) viewFragment.findViewById(R.id.documentsButton);
        documents.setTypeface(robotLight);
        documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new DocumentFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Documents",5);

                }
            }
        });

        Button videos = (Button) viewFragment.findViewById(R.id.videosButton);
        videos.setTypeface(robotLight);
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new VideoFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Videos",6);

                }
            }
        });

        Button links = (Button) viewFragment.findViewById(R.id.linksButton);
        links.setTypeface(robotLight);
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new LinksFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Links",7);

                }
            }
        });

        Button settings = (Button) viewFragment.findViewById(R.id.settingsButton);
        settings.setTypeface(robotLight);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new SettingsFragment();
                if (fragmentToLoad != null) {

                    loginSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginSharedPreferences.edit();
                    String  userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

                    if (!userType.equals("admin")) {
                        loadFragment(getActivity(), fragmentToLoad,"Settings",8);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "This Feature is not applicable to the Admin", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });

        Button notifications = (Button) viewFragment.findViewById(R.id.notificationsButton);
        notifications.setTypeface(robotLight);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new NotificationFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Messages",3);

                }
            }
        });

        Button information = (Button) viewFragment.findViewById(R.id.informationButton);
        information.setTypeface(robotLight);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
//                fragmentToLoad = new HomeFragment();
//                if (fragmentToLoad != null) {
//                    Common.sendEmailToSupport(getActivity());
//                    loadFragment(getActivity(), fragmentToLoad, "Support");
//
//                }
                fragmentToLoad = new InstituteInfo();
                if (fragmentToLoad != null) {

                    loginSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginSharedPreferences.edit();
                    String  userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

                    if (!userType.equals("admin")) {
                        loadFragment(getActivity(), fragmentToLoad,"Institute Info",9);
                    }
                    else
                    {
                        loadFragment(getActivity(), fragmentToLoad,"Institute Info",8);
                    }



                }
            }
        });


        return viewFragment;
    }

    public void fetchAppData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> grpIds = sharedPreferences.getStringSet(Constants.GROUP_IDS,null);
        NotificationManager.grpIds.clear();
        //NotificationManager.grpIds.addAll(grpIds);

        SharedPreferences loginSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        LoginManager.accessToken = loginSharedPreferences.getString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY,null);
        Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER, null);
        new FetchAppData().execute(fetchedDataDelegate);
    }

    private FetchAppData.FetchedDataDelegate fetchedDataDelegate = new FetchAppData.FetchedDataDelegate() {
        @Override
        public void fetchDataDone() {
          //  progressBar.setVisibility(View.GONE);
        }
    };

    private void loadFragment(Context context, Fragment fragment,String title,int position) {



        ((BaseActivity)context)
                .setActionBarTitle(title);


        ((BaseActivity)context)
                .setFalse();

        ((BaseActivity)context)
                .setFragmentName(title);

        ((BaseActivity)context)
                .setDrawerPositionFromHome(position);


        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();


        if (fragment != null) {
            mFragmentTransaction.replace(R.id.main_layout_container, fragment).commit();
        }




    }
}
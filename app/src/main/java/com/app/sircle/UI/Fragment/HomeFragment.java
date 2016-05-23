package com.app.sircle.UI.Fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sircle.DownLoader.FetchAppData;
import com.app.sircle.Manager.LoginManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.BaseActivity;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.Common;
import com.app.sircle.Utility.Constants;

import java.util.List;
import java.util.Set;

/**
 * Created by soniya on 7/22/15.
 */
public class HomeFragment extends Fragment {

    private TextView emailLabel;
    private ProgressBar progressBar;
    private Fragment fragmentToLoad = null;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_home,
                null, true);

        emailLabel = (TextView)viewFragment.findViewById(R.id.activity_home_email_address_label);
        // underlines the email address
        SpannableString content = new SpannableString(getResources().getString(R.string.activity_login_email_address).toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        emailLabel.setText(content);

        progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams pbParam = new LinearLayout.LayoutParams(
                100,
                100);
        pbParam.gravity = Gravity.CENTER;

        //pb.setLayoutParams(pbParam);

       // RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        //layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                ((LinearLayout) viewFragment).addView(progressBar, pbParam);

        fetchAppData();

        ImageButton calendar = (ImageButton) viewFragment.findViewById(R.id.calendarButton);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new CalendarFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad, "Calendar");

                }
            }
        });

        ImageButton photos = (ImageButton) viewFragment.findViewById(R.id.photosButton);
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new PhotosFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Photos");

                }
            }
        });

        ImageButton newsletter = (ImageButton) viewFragment.findViewById(R.id.newsletterButton);
        newsletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new NewsLetterFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"NewsLetters");

                }
            }
        });

        ImageButton documents = (ImageButton) viewFragment.findViewById(R.id.documentsButton);
        documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new DocumentFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Documents");

                }
            }
        });

        ImageButton videos = (ImageButton) viewFragment.findViewById(R.id.videosButton);
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new VideoFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Videos");

                }
            }
        });

        ImageButton links = (ImageButton) viewFragment.findViewById(R.id.linksButton);
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new LinksFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Links");

                }
            }
        });

        ImageButton settings = (ImageButton) viewFragment.findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new SettingsFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Settings");

                }
            }
        });

        ImageButton notifications = (ImageButton) viewFragment.findViewById(R.id.notificationsButton);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new NotificationFragment();
                if (fragmentToLoad != null) {
                    loadFragment(getActivity(), fragmentToLoad,"Notifications");

                }
            }
        });

        ImageButton information = (ImageButton) viewFragment.findViewById(R.id.informationButton);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(TravelBite.this, "test", Toast.LENGTH_SHORT).show();
                fragmentToLoad = new HomeFragment();
                if (fragmentToLoad != null) {
                    Common.sendEmailToSupport(getActivity());
                    loadFragment(getActivity(), fragmentToLoad, "Support");

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
            progressBar.setVisibility(View.GONE);
        }
    };

    private void loadFragment(Context context, Fragment fragment,String title) {

//        FragmentManager fragmentManager = ((Activity) context)
//                .getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        if (fragment != null) {
//
//            transaction.replace(R.id.main_layout_container, fragment);
//
//        }
//        transaction.commit();
//        fragmentManager.executePendingTransactions();

//        mDrawerList.setItemChecked(selectedModuleIndex, true);
//        mDrawerList.setSelection(selectedModuleIndex);
//        if (selectedModuleIndex == 9){
//            setTitle(menuList[0]);
//        }else
        //   getActivity().getSsetTitle(title);
//        mDrawerLayout.closeDrawer(mDrawerList);

        //getActivity().getActionBar().setTitle(title);
      //  (BaseActivity)context.setTitle(title);

        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();


        if (fragment != null) {
            mFragmentTransaction.replace(R.id.main_layout_container, fragment).commit();
        }

        ((BaseActivity)context)
                .setActionBarTitle(title);

    }
}

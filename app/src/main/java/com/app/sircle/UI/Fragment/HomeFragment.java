package com.app.sircle.UI.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sircle.DownLoader.FetchAppData;
import com.app.sircle.Manager.DocumentManager;
import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.LinksManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.Manager.VideoManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.BaseActivity;
import com.app.sircle.UI.Model.Event;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Common;
import com.app.sircle.WebService.DocumentsResponse;
import com.app.sircle.WebService.EventDataReponse;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.LinksResponse;
import com.app.sircle.WebService.NotificationResponse;
import com.app.sircle.WebService.PhotoResponse;
import com.app.sircle.WebService.VideoResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 7/22/15.
 */
public class HomeFragment extends Fragment {

    private TextView emailLabel;
    public ProgressBar progressBar;
    private Fragment fragmentToLoad = null;
    public  ProgressDialog ringProgressDialog;
    public static Context mContext;
    public FetchAppData appData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_home,
                null, true);

        emailLabel = (TextView)viewFragment.findViewById(R.id.activity_home_email_address_label);
       // contentLoadingProgressBar = (ProgressBar)viewFragment.findViewById(R.id.loadingBar);
        // underlines the email address
        SpannableString content = new SpannableString(getResources().getString(R.string.activity_login_email_address).toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        emailLabel.setText(content);

        mContext = getActivity();

//        progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//
//        LinearLayout.LayoutParams pbParam = new LinearLayout.LayoutParams(
//                100,
//                100);
//        pbParam.gravity = Gravity.CENTER;

        //pb.setLayoutParams(pbParam);

       // RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        //layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        //((LinearLayout) viewFragment).addView(progressBar, pbParam);

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
        ringProgressDialog = new ProgressDialog(getActivity());
        ringProgressDialog.setIndeterminate(true);

        appData = new FetchAppData();
        appData.setRingProgressDialog(ringProgressDialog);
        appData.execute(fetchedDataDelegate);
    }

    private FetchAppData.FetchedDataDelegate fetchedDataDelegate = new FetchAppData.FetchedDataDelegate() {
        @Override
        public void fetchDataDone() {
            //progressBar.setVisibility(View.GONE);
            appData.getProgressDialog().dismiss();

        }
    };

    private void loadFragment(Context context, Fragment fragment,String title) {

        FragmentManager fragmentManager = ((Activity) context)
                .getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragment != null) {

            transaction.replace(R.id.main_layout_container, fragment);

        }
        transaction.commit();
        fragmentManager.executePendingTransactions();

//        mDrawerList.setItemChecked(selectedModuleIndex, true);
//        mDrawerList.setSelection(selectedModuleIndex);
//        if (selectedModuleIndex == 9){
//            setTitle(menuList[0]);
//        }else
        //   getActivity().getSsetTitle(title);
//        mDrawerLayout.closeDrawer(mDrawerList);

        //getActivity().getActionBar().setTitle(title);
      //  (BaseActivity)context.setTitle(title);

        ((BaseActivity)context)
                .setActionBarTitle(title);

    }

    public void startProgress(){
        ringProgressDialog = ProgressDialog.show(getActivity(), "", "", true);
    }

    public void stopProgress(){
       ringProgressDialog.dismiss();
    }

}

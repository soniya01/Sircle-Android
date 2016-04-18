package com.app.sircle.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddLinksActivity;
import com.app.sircle.UI.Activity.EventActivity;
import com.app.sircle.UI.Activity.EventsListActivity;
import com.app.sircle.UI.Activity.HolidayActivity;
import com.app.sircle.UI.Activity.SchoolHolidayActivity;
import com.app.sircle.Utility.Constants;
import com.astuetz.PagerSlidingTabStrip;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;
import java.util.Vector;


public class CalendarFragment extends Fragment  {

   // private PagerSlidingTabStrip tabs;
   // private ViewPager pager;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;
   // public static MyPagerAdapter adapter;
    FloatingActionsMenu menuMultipleActions =  null;

    CalendarMonthFragment calendarMonthFragment;
    CalendarTodayFragment calendarTodayFragment;
    CalendarListFragment calendarListFragment;
    private SharedPreferences loginSharedPreferences;




    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        calendarMonthFragment = new CalendarMonthFragment();
        calendarTodayFragment = new CalendarTodayFragment();
        calendarListFragment = new CalendarListFragment();
        //calendarTodayFragment.setListener(this);

        View viewFragment = inflater.inflate(R.layout.fragment_calendar, container, false);

        tabLayout = (TabLayout) viewFragment.findViewById(R.id.tabs);
        viewPager = (ViewPager) viewFragment.findViewById(R.id.viewpager);


      //  tabs = (PagerSlidingTabStrip) viewFragment.findViewById(R.id.tabs);
      //  pager = (ViewPager) viewFragment.findViewById(R.id.pager);


         menuMultipleActions = (FloatingActionsMenu)viewFragment.findViewById(R.id.multiple_actions);

        loginSharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if (!userType.equals("admin"))
        {
            menuMultipleActions.setVisibility(View.GONE);
        }

        final com.getbase.floatingactionbutton.FloatingActionButton actionHoliday = (com.getbase.floatingactionbutton.FloatingActionButton)viewFragment.findViewById(R.id.actionHoliday);
        actionHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapseImmediately();
                Intent addLinkIntent = new Intent(getActivity(), HolidayActivity.class);
                startActivity(addLinkIntent);
            }
        });

        final com.getbase.floatingactionbutton.FloatingActionButton actionEvent = (com.getbase.floatingactionbutton.FloatingActionButton)viewFragment.findViewById(R.id.actionEvent);
        actionEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapseImmediately();
                Intent addLinkIntent = new Intent(getActivity(), EventActivity.class);
                startActivity(addLinkIntent);
            }
        });


        final com.getbase.floatingactionbutton.FloatingActionButton actionSchoolHoliday = (com.getbase.floatingactionbutton.FloatingActionButton)viewFragment.findViewById(R.id.actionSchoolHoliday);
        actionSchoolHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapseImmediately();
                Intent addLinkIntent = new Intent(getActivity(), SchoolHolidayActivity.class);
                startActivity(addLinkIntent);
            }
        });


//        List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
//        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), CalendarMonthFragment.class.getName()));
//        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), CalendarListFragment.class.getName()));
//        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), CalendarTodayFragment.class.getName()));
//
//       // Context mycontext = getActivity();
//        FragmentActivity myContext = (FragmentActivity)getActivity();
//
//        FragmentManager fragManager = myContext.getSupportFragmentManager();
//
//        adapter = new MyPagerAdapter(fragManager,fragments);
//
//        pager.setAdapter(adapter);
//
////        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
////                .getDisplayMetrics());
////        pager.setPageMargin(pageMargin);
//
//        tabs.setViewPager(pager);

       // tabs.setTextColor(Color.parseColor("#FFFFFF"));

        FragmentActivity myContext = (FragmentActivity)getActivity();
//
       FragmentManager fragManager = myContext.getSupportFragmentManager();

        viewPager.setAdapter(new MyAdapter(fragManager));


         //viewPager.(2);


        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                calendarListFragment.populateDummyData();
                viewPager.setCurrentItem(tab.getPosition()); // call this to fix the conflict
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position==1)
                calendarListFragment.populateDummyData();
                // Check if this is the page you want.
            }
        });

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//            // Other overridden methods
//        });

//        if (ViewCompat.isLaidOut(tabLayout)) {
//            tabLayout.setupWithViewPager(viewPager);
//        } else {
//            tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                @Override
//                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    tabLayout.setupWithViewPager(viewPager);
//                    tabLayout.removeOnLayoutChangeListener(this);
//                }
//            });
//        }

       // return x;


        return viewFragment;
    }



//    public class MyPagerAdapter extends FragmentPagerAdapter {
//
////        List<Fragment> fragments = new Vector<Fragment>();
////        fragments.add(Fragment.instantiate(this, Tab1Fragment.class.getName()));
////        fragments.add(Fragment.instantiate(this, Tab2Fragment.class.getName()));
//
//        private final String[] TITLES = { "Month", "List" ,"Term"};
//
//        private List<android.support.v4.app.Fragment> fragments;
//        /**
//         * @param fm
//         * @param fragments
//         */
//        public MyPagerAdapter(FragmentManager fm, List<android.support.v4.app.Fragment> fragments) {
//            super(fm);
//            this.fragments = fragments;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLES[position];
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
//        public android.support.v4.app.Fragment getItem(int position) {
//            return this.fragments.get(position);
//        }
//
//    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {

            switch (position){
                case 0 : return calendarMonthFragment;
                case 1 : return calendarListFragment;
                case 2 : return calendarTodayFragment;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Month";
                case 1 :
                    return "List";
                case 2 :
                    return "Term";
            }
            return null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       // tabs.removeAllViews();
    }


}

package com.app.sircle.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddLinksActivity;
import com.app.sircle.UI.Activity.EventActivity;
import com.app.sircle.UI.Activity.EventsListActivity;
import com.app.sircle.UI.Activity.HolidayActivity;
import com.app.sircle.UI.Activity.SchoolHolidayActivity;
import com.astuetz.PagerSlidingTabStrip;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;
import java.util.Vector;


public class CalendarFragment extends Fragment {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    public static MyPagerAdapter adapter;
    FloatingActionsMenu menuMultipleActions =  null;



    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View viewFragment = inflater.inflate(R.layout.fragment_calendar, container, false);

        tabs = (PagerSlidingTabStrip) viewFragment.findViewById(R.id.tabs);
        pager = (ViewPager) viewFragment.findViewById(R.id.pager);


         menuMultipleActions = (FloatingActionsMenu)viewFragment.findViewById(R.id.multiple_actions);

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


        List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), CalendarMonthFragment.class.getName()));
        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), CalendarListFragment.class.getName()));
        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), CalendarTodayFragment.class.getName()));

       // Context mycontext = getActivity();
        FragmentActivity myContext = (FragmentActivity)getActivity();

        FragmentManager fragManager = myContext.getSupportFragmentManager();

        adapter = new MyPagerAdapter(fragManager,fragments);

        pager.setAdapter(adapter);

//        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
//                .getDisplayMetrics());
//        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
       // tabs.setTextColor(Color.parseColor("#FFFFFF"));

        return viewFragment;
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

//        List<Fragment> fragments = new Vector<Fragment>();
//        fragments.add(Fragment.instantiate(this, Tab1Fragment.class.getName()));
//        fragments.add(Fragment.instantiate(this, Tab2Fragment.class.getName()));

        private final String[] TITLES = { "Month", "List" ,"Term"};

        private List<android.support.v4.app.Fragment> fragments;
        /**
         * @param fm
         * @param fragments
         */
        public MyPagerAdapter(FragmentManager fm, List<android.support.v4.app.Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return this.fragments.get(position);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tabs.removeAllViews();
    }
}

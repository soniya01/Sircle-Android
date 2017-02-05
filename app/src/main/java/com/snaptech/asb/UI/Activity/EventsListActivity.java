package com.snaptech.asb.UI.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.asb.Manager.EventManager;
import com.snaptech.asb.R;
import com.snaptech.asb.UI.Adapter.CalendarDayListAdapter;
import com.snaptech.asb.UI.Model.Event;
import com.snaptech.asb.Utility.AppError;
import com.snaptech.asb.WebService.EventDataReponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventsListActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener{


    private ListView calendarMonthListView;
    private CalendarDayListAdapter calendarMonthListViewAdapter;
    private List<Event> calendarMonthList = new ArrayList<Event>();
    private String date;
    private View footerView;
    private int month, year, day;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog ringProgressDialog;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount, totalRecord;
    boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageCount =1;
        if (getIntent() != null){
            month = getIntent().getIntExtra("month",0);
            year = getIntent().getIntExtra("year",0);
            day = getIntent().getIntExtra("day",0);
            date=getIntent().getStringExtra("date");
        }
        setContentView(R.layout.activity_events_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        calendarMonthListView = (ListView)findViewById(R.id.fragment_month_list_view);

        //calendarMonthList = EventManager.eventList;


        footerView = View.inflate(this, R.layout.list_view_padding_footer, null);
        calendarMonthListView.addFooterView(footerView);
        calendarMonthListView.setOnScrollListener(this);



        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        if (calendarMonthList.size() <= 0){
            populateDummyData();
        }
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
//
//                                        populateDummyData();
//                                    }
//                                }
//        );


        calendarMonthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

if(calendarMonthList.size()!=0) {
    Intent detailIntent = new Intent(EventsListActivity.this, EventDetailActivity.class);
    detailIntent.putExtra("eventId", calendarMonthList.get(position).getId());
    startActivity(detailIntent);
}           }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view



        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void populateDummyData(){

        pageCount=1;
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

      //  String grpIdString = NotificationManager.getSharedInstance().getGroupIds(EventsListActivity.this);

        HashMap map = new HashMap();
     //   map.put("regId", Constants.GCM_REG_ID);

//        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy HH:mm aa");
//        java.util.Date date2 = null;
//        try
//        {
//            date2 = form.parse(date);
//        }
//        catch (ParseException e)
//        {
//
//            e.printStackTrace();
//        }
//        SimpleDateFormat postFormater = new SimpleDateFormat("MMMMM dd, yyyy");
//        String newDateStr = postFormater.format(date);
        map.put("filter_date",""+date);
        //map.put("filter_year", ""+year);
        map.put("page", "1");

        System.out.println("Date sent is "+date);
      //  map.put("groupId", grpIdString);
        //ringProgressDialog = ProgressDialog.show(this, "", "", true);
        EventManager.getSharedInstance().getEventsMonthWise(map, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
                if (data != null) {
                    if (data.getStatus() == 200) {
                        if (data.getEvents() != null){
                            if (data.getEvents().size() > 0) {
                                calendarMonthList.clear();
                                calendarMonthList = data.getEvents();


                                    if (day != 0){
                                        // search data for a particular date
                                        // filter by date
                                        //filterEventsByDate();
//                                        calendarMonthList.clear();
//                                        calendarMonthList.addAll(data.getEvents());
                                        calendarMonthListViewAdapter = new CalendarDayListAdapter(EventsListActivity.this, calendarMonthList);
                                        calendarMonthListView.setAdapter(calendarMonthListViewAdapter);

                                    }else {
                                        ringProgressDialog.dismiss();
                                        calendarMonthListViewAdapter.notifyDataSetChanged();
                                    }
                                    //calendarMonthList.addAll(data.getEventData().getEvents());

//                                } else {
//                                    calendarMonthList.addAll(data.getEventData().getEvents());
//                                    calendarMonthListViewAdapter = new CalendarMonthListAdapter(EventsListActivity.this, calendarMonthList);
//                                    calendarMonthListView.setAdapter(calendarMonthListViewAdapter);
//                                }
                            } else {
                              //  Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                           // Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                   // Toast.makeText(EventsListActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onRefresh() {
       // populateDummyData();
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   populateDummyData();
    }


    public void filterEventsByDateForLoadMore()
    {

        List<Event> filteredList = new ArrayList<>();
        String dayStr = "", monthStr ="";
        dayStr = String.valueOf(day);
        monthStr = String.valueOf(month);

        if (day / 10 < 1)  dayStr = "0"+day;
        if (month / 10 < 1) monthStr = "0"+month;

        String selectedDate = dayStr + "-" + monthStr + "-" +year;
        for (Event event : calendarMonthList){
            String str = event.getStartDate();
            String[] splited = str.split("\\s+");
            String sDate = splited[0]; //03/09/2015
            if (selectedDate.equals(sDate)){
                filteredList.add(event);
            }
        }
//        ringProgressDialog.dismiss();
        //calendarMonthList.clear();
        calendarMonthList.addAll(filteredList);
        //calendarMonthListViewAdapter = new CalendarDayListAdapter(this, calendarMonthList);
        //calendarMonthListView.setAdapter(calendarMonthListViewAdapter);
        calendarMonthListViewAdapter.notifyDataSetChanged();
    }
    public void filterEventsByDate(){
        List<Event> filteredList = new ArrayList<>();
        String dayStr = "", monthStr ="";
        dayStr = String.valueOf(day);
        monthStr = String.valueOf(month);

        if (day / 10 < 1)  dayStr = "0"+day;
        if (month / 10 < 1) monthStr = "0"+month;

        String selectedDate = dayStr + "-" + monthStr + "-" +year;
        for (Event event : calendarMonthList){
            String str = event.getStartDate();
            String[] splited = str.split("\\s+");
            String sDate = splited[0]; //03/09/2015
            if (selectedDate.equals(sDate)){
                filteredList.add(event);
            }
        }
//        ringProgressDialog.dismiss();
        calendarMonthList.clear();
        calendarMonthList.addAll(filteredList);
        calendarMonthListViewAdapter = new CalendarDayListAdapter(this, calendarMonthList);
        calendarMonthListView.setAdapter(calendarMonthListViewAdapter);
        //calendarMonthListViewAdapter.notifyDataSetChanged();
    }


    public void loadMore(){

        pageCount += 1;
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

       // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(EventsListActivity.this);

        HashMap map = new HashMap();
      //  map.put("regId", Constants.GCM_REG_ID);
        map.put("filter_month",""+month);
        map.put("filter_year", ""+year);
        map.put("page", pageCount+"");
        //map.put("groupId", grpIdString);
        //ringProgressDialog = ProgressDialog.show(this, "", "", true);
        EventManager.getSharedInstance().getEventsMonthWise(map, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
                isLoading = false;
                if (data != null) {
                    if (data.getStatus() == 200) {
                        if (data.getEvents() != null) {
                            if (data.getEvents().size() > 0) {
                                //calendarMonthList.addAll(data.getEvents());// = data.getEvents();
                                //      totalRecord = data.getEventData().getTotalRecords();

                                if (day != 0) {
                                    // search data for a particular date
                                    // filter by date
                                    filterEventsByDateForLoadMore();

                                } else {
                                    //ringProgressDialog.dismiss();
                                    calendarMonthListViewAdapter.notifyDataSetChanged();
                                }

                            } else {
                               // Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
 //                       Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(EventsListActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void isScrollCompleted() {

        if (totalRecord == calendarMonthList.size()){

        }else {
            if (this.currentVisibleItemCount > 0 && this.currentScrollState == 0) {
                /*** In this way I detect if there's been a scroll which has completed ***/
                /*** do the work for load more date! ***/
                System.out.println("Load not");
                if(!isLoading){
                    isLoading = true;
                    System.out.println("Load More");
                    loadMore();
                    // Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentScrollState = scrollState;
        isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        currentFirstVisibleItem = firstVisibleItem;
        currentVisibleItemCount = visibleItemCount;
    }
}

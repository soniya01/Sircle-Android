package com.app.sircle.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.CalendarMonthListAdapter;
import com.app.sircle.UI.Model.Event;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.EventDataReponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EventsListActivity extends ActionBarActivity {


    private ListView calendarMonthListView;
    private CalendarMonthListAdapter calendarMonthListViewAdapter;
    private List<Event> calendarMonthList = new ArrayList<Event>();
    private View footerView;
    private int month, year, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null){
            month = getIntent().getIntExtra("month",0);
            year = getIntent().getIntExtra("year",0);
            day = getIntent().getIntExtra("day",0);
        }
        setContentView(R.layout.activity_events_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        populateDummyData();

        calendarMonthListView = (ListView)findViewById(R.id.fragment_month_list_view);
        calendarMonthListViewAdapter = new CalendarMonthListAdapter(this, calendarMonthList);

        footerView = View.inflate(this, R.layout.list_view_padding_footer, null);
        calendarMonthListView.addFooterView(footerView);

        calendarMonthListView.setAdapter(calendarMonthListViewAdapter);
        calendarMonthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(EventsListActivity.this,EventDetailActivity.class);
                detailIntent.putExtra("eventId",calendarMonthList.get(position).getId());
                startActivity(detailIntent);
            }
        });
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

        String grpIdString = "";
        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
            if (i == 0){
                grpIdString = NotificationManager.grpIds.get(i);
            }else {
                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
            }
        }
        HashMap map = new HashMap();
        map.put("regId", Constants.GCM_REG_ID);
        map.put("month",month);
        map.put("year",year);
        map.put("page", 1);
        map.put("groupId", grpIdString);

        EventManager.getSharedInstance().getEventsMonthWise(map, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
                if (data != null) {
                    if (data.getStatus() == 200) {
                        if (data.getEventData().getEvents() != null){
                            if (data.getEventData().getEvents().size() > 0) {
                                if (calendarMonthList.size() > 0) {
                                    calendarMonthList.clear();

                                    if (day != 0){
                                        // search data for a particular date
                                        Calendar cal = Calendar.getInstance();
                                        cal.set(Calendar.YEAR, year);
                                        cal.set(Calendar.MONTH, month);
                                        cal.set(Calendar.DAY_OF_MONTH, day);
                                        Date date = new Date();
                                        date = cal.getTime();
                                    }
                                    calendarMonthList.addAll(data.getEventData().getEvents());
                                    calendarMonthListViewAdapter.notifyDataSetChanged();
                                } else {
                                    calendarMonthList.addAll(data.getEventData().getEvents());
                                    calendarMonthListViewAdapter = new CalendarMonthListAdapter(EventsListActivity.this, calendarMonthList);
                                    calendarMonthListView.setAdapter(calendarMonthListViewAdapter);
                                }
                            } else {
                                Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(EventsListActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(EventsListActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

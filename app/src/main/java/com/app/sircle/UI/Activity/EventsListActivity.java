package com.app.sircle.UI.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.CalendarMonthListAdapter;
import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.Event;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.EventData;

import java.util.ArrayList;
import java.util.List;

public class EventsListActivity extends ActionBarActivity {


    private ListView calendarMonthListView;
    private CalendarMonthListAdapter calendarMonthListViewAdapter;
    private List<Event> calendarMonthList = new ArrayList<Event>();
    private View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        CalendarMonthlyListData calendarmonthlyData = new CalendarMonthlyListData();

        calendarmonthlyData.setEventTitle("PDF SAMPLE");
        calendarmonthlyData.setEventDate("Wednesday 27 May 2015");
        calendarmonthlyData.setEventTime("11:00");

        EventManager.getSharedInstance().getEventsMonthWise(null, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventData data, AppError error) {
                if (error == null && data != null) {
                    calendarMonthList.addAll(data.getEvents());
                    calendarMonthListViewAdapter = new CalendarMonthListAdapter(EventsListActivity.this, calendarMonthList);
                    calendarMonthListView.setAdapter(calendarMonthListViewAdapter);
                } else {
                    calendarMonthListViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}

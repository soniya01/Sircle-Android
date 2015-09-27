package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.R;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.EventDetailGroups;
import com.app.sircle.WebService.EventDetailResponse;
import com.app.sircle.WebService.PostResponse;

import java.util.HashMap;

public class EventDetailActivity extends Activity {
    private TextView eventTime, eventLocation, eventTitle, eventCategory, eventInfo, eventGroups;
    private Button saveButton, deleteButton;
    private String eventId, eventStartDate, eventEndDate, eventTitleString, eventLocationString, eventDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null){
            eventId = getIntent().getExtras().getString("eventId");
        }
        setContentView(R.layout.activity_event_detail);
        eventTime = (TextView)findViewById(R.id.event_detail_time);
        eventLocation = (TextView)findViewById(R.id.event_detail_location);
        eventTitle = (TextView)findViewById(R.id.event_detail_title);
        eventCategory = (TextView)findViewById(R.id.event_detail_category);
        eventInfo = (TextView)findViewById(R.id.event_detail_info);
        eventGroups = (TextView)findViewById(R.id.event_detail_groups);
        saveButton = (Button)findViewById(R.id.event_detail_save);
        deleteButton = (Button)findViewById(R.id.event_detail_delete);

        // add event to calendar
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                intent.setType("vnd.android.cursor.item/event");

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventStartDate);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,eventEndDate);
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                intent.putExtra(CalendarContract.Events.TITLE, eventTitleString);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDetail);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocationString);
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
                startActivity(intent);
                // Initialize Calendar service with valid OAuth credentials
//                Calendar service = new Calendar.Builder()
//                        .setApplicationName("applicationName").build();
//
//                // Quick-add an event
//                String eventText = "Appointment at Somewhere on June 3rd 10am-10:25am";
//                Event createdEvent =
//                        service.events().quickAdd('primary').setText(eventText).execute();

               // System.out.println(createdEvent.getId());
            }
        });

        // delete an event
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap params = new HashMap();
                params.put("eventId",eventId);
                EventManager.getSharedInstance().deleteEvent(params, new EventManager.AddEventsManagerListener() {
                    @Override
                    public void onCompletion(PostResponse response, AppError error) {
                        if (response != null) {
                            if (response.getStatus() == 200) {
                                Toast.makeText(EventDetailActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EventDetailActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(EventDetailActivity.this, "Some problem occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        getEventDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void getEventDetail(){
        HashMap params = new HashMap();
        params.put("eid",eventId);
        EventManager.getSharedInstance().getEventDetails(params, new EventManager.EventManagerListener() {
            @Override
            public void onCompletion(EventDetailResponse eventDetailResponse, AppError error) {
                if (eventDetailResponse != null){
                    if (eventDetailResponse.getStatus() == 200){
                        if (eventDetailResponse.getData() != null){
                            String dateString = eventDetailResponse.getData().getEventInfo().getStartDate() + " to " + eventDetailResponse.getData().getEventInfo().getEndDate()
                                    + "\n" +eventDetailResponse.getData().getEventInfo().getStartTime() + " to " +eventDetailResponse.getData().getEventInfo().getEndTime();
                            eventTime.setText(dateString);
                            eventInfo.setText(eventDetailResponse.getData().getEventInfo().getDetail());
                            eventCategory.setText(eventDetailResponse.getData().getEventInfo().getCategory());
                            eventGroups.setText("Groups: "+eventDetailResponse.getData().getEventGroups());
                            eventLocation.setText("At: " +eventDetailResponse.getData().getEventInfo().getLocation());
                            //eventLocation.setText(eventDetailResponse.getData().get);
                            for (EventDetailGroups groups : eventDetailResponse.getData().getEventGroups()){
                               String groupString = groups.getName() + " ,";
                                eventGroups.setText("Groups: "+groupString);

                            }
                            eventTitle.setText(eventDetailResponse.getData().getEventInfo().getTitle());

                            eventLocationString = eventDetailResponse.getData().getEventInfo().getLocation();
                            eventTitleString = eventDetailResponse.getData().getEventInfo().getTitle();
                            eventDetail = eventDetailResponse.getData().getEventInfo().getDetail();
                        }else {
                            Toast.makeText(EventDetailActivity.this,eventDetailResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(EventDetailActivity.this,eventDetailResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EventDetailActivity.this,"Some problem occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

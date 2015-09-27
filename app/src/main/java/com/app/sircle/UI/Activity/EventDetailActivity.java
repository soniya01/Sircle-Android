package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.R;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.EventDetailResponse;
import com.app.sircle.WebService.PostResponse;

public class EventDetailActivity extends Activity {
    private TextView eventTime, eventLocation, eventTitle, eventCategory, eventInfo, eventGroups;
    private Button saveButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        eventTime = (TextView)findViewById(R.id.event_detail_time);
        eventLocation = (TextView)findViewById(R.id.event_detail_location);
        eventTitle = (TextView)findViewById(R.id.event_detail_title);
        eventCategory = (TextView)findViewById(R.id.event_detail_category);
        eventInfo = (TextView)findViewById(R.id.event_detail_info);
        eventGroups = (TextView)findViewById(R.id.event_detail_groups);
        saveButton = (Button)findViewById(R.id.event_detail_save);
        deleteButton = (Button)findViewById(R.id.event_detail_delete);

        //save button add to calendar
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // delete an event
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.getSharedInstance().deleteEvent(null, new EventManager.AddEventsManagerListener() {
                    @Override
                    public void onCompletion(PostResponse response, AppError error) {
                        if (response != null){
                            if (response.getStatus() == 200){
                                Toast.makeText(EventDetailActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(EventDetailActivity.this,response.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(EventDetailActivity.this,"Some problem occurred",Toast.LENGTH_SHORT).show();
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
        EventManager.getSharedInstance().getEventDetails(null, new EventManager.EventManagerListener() {
            @Override
            public void onCompletion(EventDetailResponse eventDetailResponse, AppError error) {
                if (eventDetailResponse != null){
                    if (eventDetailResponse.getStatus() == 200){
                        if (eventDetailResponse.getData() != null){
                            eventTime.setText(eventDetailResponse.getData().eventWeekDays);
                            eventInfo.setText(eventDetailResponse.getData().getEventInfo());
                            eventCategory.setText(eventDetailResponse.getData().getEventType());
                            eventGroups.setText("Groups: "+eventDetailResponse.getData().getEventGroups());
                            //eventLocation.setText(eventDetailResponse.getData().get);
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

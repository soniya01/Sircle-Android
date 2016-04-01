package com.app.sircle.UI.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.PostResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SchoolHolidayActivity extends ActionBarActivity {

    private EditText title, desc;
    private View footerView;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private Button addButton;
    Calendar myCalendar;
    EditText startDateEditText,endDateEditText;
    private NotificationsGroupAdapter notificationsGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_holiday);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        title = (EditText)findViewById(R.id.holidayEventTitle);
        addListView = (ListView) findViewById(R.id.activity_schoolHoliday_list_view);
        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new NotificationsGroupAdapter(SchoolHolidayActivity.this, notificationGroupList);
        addListView.setAdapter(notificationsGroupAdapter);

        if (notificationGroupList.size() <= 0){
            populateDummyData();
        }

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button) footerView.findViewById(R.id.add_button);
        addButton.setText("Add Event");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().equals("")
                        || startDateEditText.getText().toString().equals("")
                        || endDateEditText.getText().toString().equals("")) {
                    Toast.makeText(SchoolHolidayActivity.this, "Please add all the details!", Toast.LENGTH_SHORT).show();
                } else {
                    String grpIdString = "";
                    for (int i = 0; i < NotificationManager.grpIds.size(); i++) {
                        if (i == 0) {
                            grpIdString = NotificationManager.grpIds.get(i);
                        } else {
                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i);
                        }
                    }

                    HashMap params = new HashMap();
                    params.put("event_type", "3");
                    params.put("grp", grpIdString);
                    params.put("title", title.getText().toString());
                    params.put("strdate", startDateEditText.getText().toString());
                    params.put("enddate", endDateEditText.getText().toString());

                    EventManager.getSharedInstance().addEvent(params, new EventManager.AddEventsManagerListener() {
                        @Override
                        public void onCompletion(PostResponse response, AppError error) {
                            if (response != null) {
                                Toast.makeText(SchoolHolidayActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                if (response.getStatus() == 200) {
                                    finish();
                                }
                            } else {
                                Toast.makeText(SchoolHolidayActivity.this, "some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
        addListView.addFooterView(footerView);

        //populateDummyData();

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, groupNames);
//        addListView.setAdapter(arrayAdapter);
        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        startDateEditText = (EditText)findViewById(R.id.holidayEventStartDate);

        startDateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //   Date date = new Date();
                new DatePickerDialog(SchoolHolidayActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        endDateEditText = (EditText)findViewById(R.id.holidayEventEndDate);

        endDateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //   Date date = new Date();
                new DatePickerDialog(SchoolHolidayActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDateEditText.setText(sdf.format(myCalendar.getTime()));
        endDateEditText.setText(sdf.format(myCalendar.getTime()));
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
        getMenuInflater().inflate(R.menu.menu_school_holiday, menu);
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

    public void populateDummyData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("regId", Constants.GCM_REG_ID);
        NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {

                        if (response.getData().size() > 0){
                            SchoolHolidayActivity.this.notificationGroupList.addAll(NotificationManager.groupList);
                            notificationsGroupAdapter.notifyDataSetChanged();
                        }

//                        if (SchoolHolidayActivity.this.notificationGroupList.size() > 0) {
//                            SchoolHolidayActivity.this.notificationGroupList.clear();
//                            SchoolHolidayActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter.notifyDataSetChanged();
//                            // update group notifictaion for all groups
//                            //updateAllGroup();
//
//                        } else {
//                            //SettingsActivity.this.notificationGroupList.clear();
//                            SchoolHolidayActivity.this.notificationGroupList.addAll(response.getData());
//                            notificationsGroupAdapter = new NotificationsGroupAdapter(SchoolHolidayActivity.this, notificationGroupList);
//                            addListView.setAdapter(notificationsGroupAdapter);
//
//                        }

                        Toast.makeText(SchoolHolidayActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SchoolHolidayActivity.this, "Some error occurred!",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SchoolHolidayActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}

package com.app.sircle.UI.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.NotificationsGroupAdapter;
import com.app.sircle.UI.Model.EventCategory;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.CategoryResponse;
import com.app.sircle.WebService.GroupResponse;
import com.app.sircle.WebService.PostResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EventActivity extends ActionBarActivity implements View.OnClickListener{

    AlertDialog alert;
    private Button selectCategoryButton, addButton;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private List<String> eventCategory = new ArrayList<>();
    private NotificationsGroupAdapter notificationsGroupAdapter;
    private ListView categoryListView;
    private ArrayAdapter<String> adapter;
    private EditText title, location, detail;
    private Button categoryButton, minutes, hours, days,startDate, endDate, startTime, endTime;
    private DatePickerDialog datePickerDialog;
    private CheckBox repeat;
    private int isRepeat;
    private String startDateString, endDateStr, startTimeStr, endTimeStr;
    public  List<String> hourList = new ArrayList<>();
    public  List<String> minList = new ArrayList<>();
    public  List<String> daysList = new ArrayList<>();
    int startYear,startMonth,startDay, mins, hour, secs;
    ProgressDialog ringProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        populateHour();

        addListView = (ListView) findViewById(R.id.activity_schoolHoliday_list_view);
        title = (EditText)findViewById(R.id.eventTypeEditText);
        detail = (EditText)findViewById(R.id.eventTypeEditText);
        location = (EditText)findViewById(R.id.eventTypeEditText);
        startDate = (Button)findViewById(R.id.holidayEventStartDate);
        endDate = (Button)findViewById(R.id.holidayEventEndDate);
        startTime = (Button)findViewById(R.id.startTime);
        endTime = (Button)findViewById(R.id.endTime);
        categoryButton = (Button)findViewById(R.id.selectCategoryButton);
        days = (Button)findViewById(R.id.days);
        hours = (Button)findViewById(R.id.hours);
        minutes = (Button)findViewById(R.id.minutes);
        repeat = (CheckBox)findViewById(R.id.activity_event_repeat);

        days.setOnClickListener(this);
        minutes.setOnClickListener(this);
        hours.setOnClickListener(this);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRepeat = isChecked ? 1 : 0;
            }
        });

        addButton = (Button)findViewById(R.id.add_button);
        setListViewHeightBasedOnChildren(addListView);


        notificationsGroupAdapter = new NotificationsGroupAdapter(EventActivity.this, notificationGroupList);
        addListView.setAdapter(notificationsGroupAdapter);

        populateDummyData();

        addListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        addListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //event_type:event_type_id,
                // title:title,loc:loc,event_cat:event_cat,grp:grp,
                // strdate:strdate,enddate:enddate,strtime:strtime,
                // endtime:endtime,detail:det,rem_days:rem_days,
                // rem_hours:rem_hours,rem_mins:rem_mins,repeats:repeats,
                // repeat_type_id:repeat_type_id,repeat_type:repeat_type_name,
                // repeat_every : number, repeat_end_type_id: end_type_id,
                // repeat_end_type : end_type_text, rep_after_occurence, rep_ondate,
                // repeat_week_days: (Array), grp, repeat_monthly_on

                String grpIdString = "";
                for (int i = 0; i< NotificationManager.grpIds.size(); i++){
                    if (i == 0){
                        grpIdString = NotificationManager.grpIds.get(i);
                    }else {
                        grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
                    }
                }

                HashMap params = new HashMap();
                params.put("event_type","2");
                params.put("title",title.getText().toString());
                params.put("loc",location.getText().toString());
                params.put("event_cat",categoryButton.getText().toString());
                params.put("grp",grpIdString);
                params.put("strdate",startDateString);
                params.put("enddate",endDateStr);
                params.put("strtime",startTimeStr);
                params.put("endtime",endTimeStr);
                params.put("detail",detail.getText().toString());
                params.put("rem_days",days.getText().toString());
                params.put("rem_hours",hours.getText().toString());
                params.put("rem_mins",minutes.getText().toString());
                params.put("repeats",isRepeat);

                EventManager.getSharedInstance().addEvent(params, new EventManager.AddEventsManagerListener() {
                    @Override
                    public void onCompletion(PostResponse response, AppError error) {
                        if (response != null){
                            Toast.makeText(EventActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 200){
                                finish();
                            }
                        }else {
                            Toast.makeText(EventActivity.this, "some error occurred",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        selectCategoryButton = (Button) findViewById(R.id.selectCategoryButton);

        selectCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //String names[] = {"Arts", "Sports", "Excursion", "Academics", "Performance", "Other"};

                EventManager.getSharedInstance().getEventCategory(new EventManager.GetEventsCategoryManagerListener() {
                    @Override
                    public void onCompletion(CategoryResponse response, AppError error) {
                        if (response != null){
                            if (response.getData().size() > 0){
                                eventCategory.clear();
                                for (EventCategory eventCategory : response.getData()){
                                    EventActivity.this.eventCategory.add(eventCategory.getCategory());
                                }
                                adapter = new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_list_item_1, eventCategory
                                ) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                                        text.setTextColor(Color.BLACK);
                                        return view;
                                    }
                                };
                                categoryListView.setAdapter(adapter);
                            }else {
                                Toast.makeText(EventActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(EventActivity.this, "some error occurred",Toast.LENGTH_SHORT).show();
                        }
                        //EventActivity.this.eventCategory.addAll(eventCategoryList);
                    }
                });

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.dialog_list_view, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Select Category");
                categoryListView = (ListView) convertView.findViewById(R.id.listView1);
                // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
                // lv.setAdapter(adapter);
                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, eventCategory
                ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                categoryListView.setAdapter(adapter);

                //  alertDialog.show();

                alert = alertDialog.show();


                categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override

                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        String text = textView.getText().toString();
                        selectCategoryButton.setText(text);
                        alert.dismiss();
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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

        // get group names call to be added

        // get event category
        ringProgressDialog = ProgressDialog.show(this, "", "", true);
        HashMap<String, String> map = new HashMap<>();
        map.put("regId", Constants.GCM_REG_ID);
        NotificationManager.getSharedInstance().getAllGroups(map, new NotificationManager.GroupsManagerListener() {
            @Override
            public void onCompletion(GroupResponse response, AppError error) {
                ringProgressDialog.dismiss();
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {

                        if (notificationGroupList.size() > 0) {
                            notificationGroupList.clear();
                            notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter.notifyDataSetChanged();
                            // update group notifictaion for all groups
                            //updateAllGroup();

                        } else {
                            //SettingsActivity.this.notificationGroupList.clear();
                            notificationGroupList.addAll(response.getData());
                            notificationsGroupAdapter = new NotificationsGroupAdapter(EventActivity.this, notificationGroupList);
                            addListView.setAdapter(notificationsGroupAdapter);

                        }

                        Toast.makeText(EventActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(SettingsActivity.this, response.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(EventActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onClick(final View v) {

        TimePickerDialog timePickerDialog;
        Calendar c = Calendar.getInstance();
         startYear = c.get(Calendar.YEAR);
         startMonth = c.get(Calendar.MONTH);
         startDay = c.get(Calendar.DAY_OF_MONTH);
        mins = 0; hour = 0;
        secs = 0;

        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startYear = year;
                startMonth = monthOfYear;
                startDay = dayOfMonth;

                if (v == startDate){
                    startDateString = dayOfMonth + "/" + monthOfYear+ "/" +year;
                    startDate.setText(startDateString);
                }else if (v == endDate){
                    endDateStr = dayOfMonth + "/" + monthOfYear+ "/" +year;
                    endDate.setText(endDateStr);
                }
            }
        };

        TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                mins = minute;

                if (v == startTime){
                    startTimeStr = hour + ":"+mins+ ":00";
                    startTime.setText(startTimeStr);
                }else if (v == endTime){
                    endTimeStr = hour + ":"+mins+ ":00";
                    endTime.setText(endTimeStr);
                }
            }
        };

        if (v == startDate || v == endDate){
            datePickerDialog = new DatePickerDialog(EventActivity.this, dateListener, startYear, startMonth, startDay);
            datePickerDialog.show();

        }
        if (v == startTime || v == endTime){
            timePickerDialog = new TimePickerDialog(EventActivity.this, timeListener, hour, mins, true);
            timePickerDialog.show();

        }
        if (v == days){
            // open date picker
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.dialog_list_view, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("1 hour ");
            categoryListView = (ListView) convertView.findViewById(R.id.listView1);
            // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
            // lv.setAdapter(adapter);
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_checked, daysList
            ) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setTextColor(Color.BLACK);
                    return view;
                }
            };
            categoryListView.setAdapter(adapter);

            //  alertDialog.show();
            alert = alertDialog.show();

            categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    String text = textView.getText().toString();
                    days.setText(text);
                    alert.dismiss();
                }
            });

        }if (v == minutes){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.dialog_list_view, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("1 day ");
            categoryListView = (ListView) convertView.findViewById(R.id.listView1);
            // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
            // lv.setAdapter(adapter);
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_checked, minList
            ) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setTextColor(Color.BLACK);
                    return view;
                }
            };
            categoryListView.setAdapter(adapter);

            //  alertDialog.show();
            alert = alertDialog.show();

            categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    String text = textView.getText().toString();
                    minutes.setText(text);
                    alert.dismiss();
                }
            });
        }
        if (v == hours){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.dialog_list_view, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("0 Hour ");
            categoryListView = (ListView) convertView.findViewById(R.id.listView1);
            // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
            // lv.setAdapter(adapter);
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_checked, hourList
            ) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setTextColor(Color.BLACK);
                    return view;
                }
            };
            categoryListView.setAdapter(adapter);

            //  alertDialog.show();
            alert = alertDialog.show();

            categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    String text = textView.getText().toString();
                    hours.setText(text);
                    alert.dismiss();
                }
            });
        }
    }


    public void populateHour(){

        hourList.add("1 hour");
        hourList.add("2 hours");
        hourList.add("3 hours");
        hourList.add("4 hours");
        hourList.add("5 hours");
        hourList.add("6 hours");
        hourList.add("7 hours");
        hourList.add("8 hours");
        hourList.add("9 hours");
        hourList.add("10 hours");
        hourList.add("11 hours");
        hourList.add("12 hours");
        hourList.add("13 hours");
        hourList.add("14 hours");
        hourList.add("15 hours");
        hourList.add("16 hours");
        hourList.add("17 hours");
        hourList.add("18 hours");
        hourList.add("19 hours");
        hourList.add("20 hours");
        hourList.add("21 hours");
        hourList.add("22 hours");
        hourList.add("23 hours");

        daysList.add("1 day");
        daysList.add("2 days");
        daysList.add("3 days");
        daysList.add("4 days");
        daysList.add("5 days");
        daysList.add("6 days");
        daysList.add("7 days");
        daysList.add("8 days");
        daysList.add("9 days");
        daysList.add("10 days");
        daysList.add("11 days");
        daysList.add("12 days");
        daysList.add("13 days");
        daysList.add("14 days");
        daysList.add("15 days");
        daysList.add("16 days");
        daysList.add("17 days");
        daysList.add("18 days");
        daysList.add("19 days");
        daysList.add("20 days");
        daysList.add("21 days");
        daysList.add("22 days");
        daysList.add("23 days");
        daysList.add("24 days");
        daysList.add("25 days");
        daysList.add("26 days");
        daysList.add("27 days");
        daysList.add("28 days");
        daysList.add("29 days");
        daysList.add("30 days");

        minList.add("1 minute");
        minList.add("2 minutes");
        minList.add("3 minutes");
        minList.add("4 minutes");
        minList.add("5 minutes");
        minList.add("6 minutes");
        minList.add("7 minutes");
        minList.add("8 minutes");
        minList.add("9 minutes");
        minList.add("10 minutes");
        minList.add("11 minutes");
        minList.add("12 minutes");
        minList.add("13 minutes");
        minList.add("14 minutes");
        minList.add("15 minutes");
        minList.add("16 minutes");
        minList.add("17 minutes");
        minList.add("18 minutes");
        minList.add("19 minutes");
        minList.add("20 minutes");
        minList.add("21 minutes");
        minList.add("21 minutes");
        minList.add("23 minutes");
        minList.add("24 minutes");
        minList.add("25 minutes");
        minList.add("26 minutes");
        minList.add("27 minutes");
        minList.add("28 minutes");
        minList.add("29 minutes");
        minList.add("30 minutes");
        minList.add("31 minutes");
        minList.add("32 minutes");
        minList.add("33 minutes");
        minList.add("34 minutes");
        minList.add("35 minutes");
        minList.add("36 minutes");
        minList.add("37 minutes");
        minList.add("38 minutes");
        minList.add("39 minutes");
        minList.add("40 minutes");
        minList.add("41 minutes");
        minList.add("42 minutes");
        minList.add("43 minutes");
        minList.add("44 minutes");
        minList.add("45 minutes");
        minList.add("46 minutes");
        minList.add("47 minutes");
        minList.add("48 minutes");
        minList.add("49 minutes");
        minList.add("50 minutes");
        minList.add("51 minutes");
        minList.add("52 minutes");
        minList.add("53 minutes");
        minList.add("54 minutes");
        minList.add("55 minutes");
        minList.add("56 minutes");
        minList.add("57 minutes");
        minList.add("58 minutes");
        minList.add("59 minutes");

    }
}

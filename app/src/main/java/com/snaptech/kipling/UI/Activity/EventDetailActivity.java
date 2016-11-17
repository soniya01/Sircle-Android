package com.snaptech.kipling.UI.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.snaptech.kipling.Manager.EventManager;
import com.snaptech.kipling.R;
import com.snaptech.kipling.Utility.AppError;
import com.snaptech.kipling.Utility.Constants;
import com.snaptech.kipling.Utility.InternetCheck;
import com.snaptech.kipling.WebService.EventDetailResponse;
import com.snaptech.kipling.WebService.PostResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class EventDetailActivity extends AppCompatActivity {
    private TextView eventStartDate,eventEndDate, eventLocation, eventTitle, eventCategory, eventInfo, eventGroups;
    private Button saveButton, deleteButton;
    private String eventId,eventTitleString, eventLocationString, eventDetail;
    ProgressDialog ringProgressDialog;
    private boolean flagparse=false;
    private SharedPreferences loginSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null){
            eventId = getIntent().getExtras().getString("eventId");
        }
        setContentView(R.layout.activity_event_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        eventStartDate = (TextView)findViewById(R.id.event_start_date);
        eventEndDate = (TextView)findViewById(R.id.event_end_date);
        eventLocation = (TextView)findViewById(R.id.event_detail_location);
        eventTitle = (TextView)findViewById(R.id.event_detail_title);
        eventCategory = (TextView)findViewById(R.id.event_detail_category);
        // eventInfo = (TextView)findViewById(R.id.event_detail_info);
        eventGroups = (TextView)findViewById(R.id.event_detail_groups);
        saveButton = (Button)findViewById(R.id.event_detail_save);
        //  deleteButton = (Button)findViewById(R.id.event_detail_delete);
        eventLocation.setText("");
        // add event to calendar
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date startDate=toDate(eventStartDate.getText().toString());
                Date endDate=toDate(eventEndDate.getText().toString());
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");

                Calendar cal1=Calendar.getInstance();
                cal1.setTime(startDate);
                Calendar cal2= Calendar.getInstance();
                cal2.setTime(endDate);

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal1.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,cal2.getTimeInMillis());

                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, flagparse);
                flagparse=false;


                intent.putExtra(CalendarContract.Events.TITLE, eventTitle.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, eventCategory.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation.getText().toString());
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
                startActivity(intent);

//                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT).setData(CalendarContract.Events.CONTENT_URI);
//                intent.setType("vnd.android.cursor.item/event");
//
//                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, eventStartDate);
//                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,eventEndDate);
//                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
//                intent.putExtra(CalendarContract.Events.TITLE, eventTitleString);
//                intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDetail);
//                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocationString);
//                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
//                startActivity(intent);
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
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

        if(InternetCheck.isNetworkConnected(EventDetailActivity.this))
            getEventDetail();
        else
            Toast.makeText(EventDetailActivity.this,"Compruebe internet",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);
        if (getIntent().getExtras() != null)
        {
            eventId = getIntent().getExtras().getString("eventId");
            if(InternetCheck.isNetworkConnected(EventDetailActivity.this))
                getEventDetail();
            else
                Toast.makeText(EventDetailActivity.this,"Compruebe internet",Toast.LENGTH_SHORT).show();
//            shouldSelectListViewItem = true;
//            didSelectListViewItemAtIndex(selectedModuleIndex);
        }

    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//
//
//
//        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//     MenuItem menuItem =  menu.findItem(R.id.delete).setVisible(true);
//
//
////                    SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
////            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spanString.length(), 0); //fix the color to white
////            menuItem.setTitle(spanString);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
//        for(int i = 0; i < menu.size(); i++) {
//            MenuItem item = menu.getItem(i);
//            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
//            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spanString.length(), 0); //fix the color to white
//            item.setTitle(spanString);
//        }
        // menu.findItem(R.id.delete).setTitle(Html.fromHtml("<font color='#ffffff'>Settings</font>"));

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_event_detail, menu);
//
//        LayoutInflater layoutInflater = getLayoutInflater();
//        final LayoutInflater.Factory existingFactory = layoutInflater.getFactory();
//
//        try {
//        Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
//        field.setAccessible(true);
//        field.setBoolean(layoutInflater, false);
//
//        getLayoutInflater().setFactory(new LayoutInflater.Factory() {
//            @Override
//            public View onCreateView(String name, Context context,
//                                     AttributeSet attrs) {
//                if (name .equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")) {
//                    try {
//                        LayoutInflater f = getLayoutInflater();
//                        final View view = f.createView(name, null, attrs);
//
//                        new Handler().post(new Runnable() {
//                            public void run() {
//// set the background drawable
//                                view.setBackgroundResource(R.drawable.fab_label_background);
//// set the text color
//                                ((TextView) view).setTextColor(Color.WHITE);
//                            }
//                        });
//                        return view;
//                    } catch (InflateException e) {
//                    } catch (ClassNotFoundException e) {
//                    }
//                }
//
//                return null;
//            }
//        });
//        } catch (NoSuchFieldException e) {
//            // ...
//        } catch (IllegalArgumentException e) {
//            // ...
//        } catch (IllegalAccessException e) {
//            // ...
//        }
//        return super.onCreateOptionsMenu(menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.delete) {
            //TODO: download
            HashMap params = new HashMap();
            params.put("event_id",eventId);
            EventManager.getSharedInstance().deleteEvent(params, new EventManager.AddEventsManagerListener() {
                @Override
                public void onCompletion(PostResponse response, AppError error) {
                    if (response != null) {
                        if (response.getStatus() == 200) {
                            //  Toast.makeText(EventDetailActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            //  Toast.makeText(EventDetailActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(EventDetailActivity.this,"Compruebe internet",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return true;
        }

        else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getEventDetail(){

        ringProgressDialog = ProgressDialog.show(this, "", "", true);
        HashMap params = new HashMap();
        params.put("event_id",eventId);
        EventManager.getSharedInstance().getEventDetails(params, new EventManager.EventManagerListener() {
            @Override
            public void onCompletion(EventDetailResponse eventDetailResponse, AppError error) {
                ringProgressDialog.dismiss();
                if (Constants.flag_logout) {

                    handleSharedPreferencesOnLogout();
                    Intent intent = new Intent(EventDetailActivity.this, LoginScreen.class);
                    startActivity(intent);
                    Toast.makeText(EventDetailActivity.this, "Por favor introduzcalo de nuevo.", Toast.LENGTH_LONG).show();                    finish();
                    Constants.flag_logout = false;

                } else {
                    if (eventDetailResponse != null) {
                        if (eventDetailResponse.getStatus() == 200) {
                            if (eventDetailResponse.getData() != null) {
                                eventTitle.setText(eventDetailResponse.getData().getEventTitle());
                                eventStartDate.setText(eventDetailResponse.getData().getEventStartDate());
                                eventEndDate.setText(eventDetailResponse.getData().getEventEndDate());


                                // eventInfo
                                //
                                //
                                // .setText(eventDetailResponse.getData().getEventDescription());
                                //  eventCategory.setText(eventDetailResponse.getData().getCategory());
                                // eventGroups.setText("Groups: "+eventDetailResponse.getData().getEventGroups());
                                eventLocation.setText(eventDetailResponse.getData().getEventLocation());
                                eventCategory.setText(eventDetailResponse.getData().getEventDescription());
                                //eventLocation.setText(eventDetailResponse.getData().get);
//                            String groupString = "";
//                            for (EventDetailGroups groups : eventDetailResponse.getData().getEventGroups()){
//                                groupString = groups.getName() + "," + groupString ;
//                            }
//                            eventGroups.setText("Groups: "+groupString);


                                // eventLocationString = eventDetailResponse.getData().getEventInfo().getLocation();
                                // eventTitleString = eventDetailResponse.getData().getEventInfo().getTitle();
                                // eventDetail = eventDetailResponse.getData().getEventInfo().getDetail();
                            } else {
                                //   Toast.makeText(EventDetailActivity.this,eventDetailResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            //Toast.makeText(EventDetailActivity.this,eventDetailResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EventDetailActivity.this,"Compruebe internet",Toast.LENGTH_SHORT).show();                    }
                }
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view



        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        loginSharedPreferences = getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String  userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if (!userType.equals("admin")) {
            menu.findItem(R.id.delete).setVisible(false);
        }




        return super.onPrepareOptionsMenu(menu);
    }
    public Date toDate(String dateString){
        //String startDateString = "06/27/2007";
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate=null;
        try {
            startDate = df.parse(dateString);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            flagparse=true;
            try{
                startDate = df2.parse(dateString);
                String newDateString = df.format(startDate);
                System.out.println(newDateString);
            }catch(ParseException e2){

            }
            e.printStackTrace();
        }
        return startDate;
    }
    public void handleSharedPreferencesOnLogout()
    {
//        LoginManager.getSharedInstance().logout(new LoginManager.LoginManagerListener() {
//            @Override
//            public void onCompletion(LoginResponse response, AppError error) {
//
//            }});
        SharedPreferences loginSharedPrefs = EventDetailActivity.this.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = loginSharedPrefs.edit();
        editor.putString(Constants.LOGIN_ACCESS_TOKEN_PREFS_KEY, null);
        editor.apply();
    }
}//Compruebe internet
//Por favor introduzcalo de nuevo.
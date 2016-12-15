package com.snaptech.coxandkingshr.UI.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.coxandkingshr.Manager.EventManager;
import com.snaptech.coxandkingshr.Manager.NotificationManager;
import com.snaptech.coxandkingshr.R;
import com.snaptech.coxandkingshr.UI.Adapter.AddGroupAdapter;
import com.snaptech.coxandkingshr.UI.Model.NotificationGroups;
import com.snaptech.coxandkingshr.Utility.AppError;
import com.snaptech.coxandkingshr.Utility.Constants;
import com.snaptech.coxandkingshr.WebService.GroupResponse;
import com.snaptech.coxandkingshr.WebService.PostResponse;

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
    private AddGroupAdapter notificationsGroupAdapter;
    String dateType,startDate,endDate;
    public static CheckBox allCheckBox;
    private ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_holiday);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        title = (EditText)findViewById(R.id.holidayEventTitle);
        NotificationManager.grpIds.clear();
        addListView = (ListView) findViewById(R.id.activity_schoolHoliday_list_view);
        allCheckBox = (CheckBox) findViewById(R.id.checkAll);
        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.TRUE);
                    }
                } else {
                    for (int i = 0; i < notificationGroupList.size(); i++) {
                        // listData[i] = listView.getAdapter().getItem(i).toString();
                        notificationGroupList.get(i).setActive(Boolean.FALSE);
                    }
                }
                //  NotificationManager.grpIds.clear();
                notificationsGroupAdapter.notifyDataSetChanged();
            }
        });
        setListViewHeightBasedOnChildren(addListView);
        notificationGroupList = NotificationManager.groupList;
        notificationsGroupAdapter = new AddGroupAdapter(SchoolHolidayActivity.this, notificationGroupList);
        addListView.setAdapter(notificationsGroupAdapter);

        if (notificationGroupList.size() <= 0){
            populateDummyData();
        }

        footerView = View.inflate(this, R.layout.list_view_add_footer, null);
        addButton = (Button) footerView.findViewById(R.id.add_button);
        addButton.setText("Add School Holiday");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (title.getText().toString().equals("")
                        || startDateEditText.getText().toString().equals("")
                        || endDateEditText.getText().toString().equals("")) {
                    Toast.makeText(SchoolHolidayActivity.this, "Please add all the details!", Toast.LENGTH_SHORT).show();
                } else {
//                    String grpIdString = "";
//                    for (int i = 0; i < NotificationManager.grpIds.size(); i++) {
//                        if (i == 0) {
//                            grpIdString = NotificationManager.grpIds.get(i);
//                        } else {
//                            grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i);
//                        }
//                    }

                    // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(SchoolHolidayActivity.this);
                    if (title.getText().toString().length() <= 2) {

                        Toast.makeText(SchoolHolidayActivity.this, "Title should be of atleast 3 characters", Toast.LENGTH_LONG).show();
                    } else {
                        ringProgressDialog = ProgressDialog.show(SchoolHolidayActivity.this, "", "Adding School Holiday", true);

                        String grpIdString = "";

                        for (int i = 0; i < notificationGroupList.size(); i++) {
                            // listData[i] = listView.getAdapter().getItem(i).toString();
                            // notificationGroupList.get(i).setActive(1);


                            if (notificationGroupList.get(i).getActive() == Boolean.TRUE) {
                                if (grpIdString.equals("")) {
                                    grpIdString = notificationGroupList.get(i).getId();
                                } else {
                                    grpIdString = grpIdString + "," + notificationGroupList.get(i).getId();
                                }
                                //NotificationManager.grpIds.add( notificationGroupList.get(i).getId());
                            }
                        }

                        HashMap params = new HashMap();
                        params.put("event_type", "SH");
                        params.put("group_id", grpIdString);
                        params.put("event_name", title.getText().toString());
                        params.put("event_from_date", startDate);
                        params.put("event_to_date", endDate);

                        EventManager.getSharedInstance().addEvent(params, new EventManager.AddEventsManagerListener() {
                            @Override
                            public void onCompletion(PostResponse response, AppError error) {
                                ringProgressDialog.dismiss();
                                if (response != null) {

                                    if (response.getStatus() == 200) {
                                        Toast.makeText(SchoolHolidayActivity.this, "School Holiday Added", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(SchoolHolidayActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SchoolHolidayActivity.this, "some error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
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
                dateType = "StartDate";


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
                dateType = "EndDate";
                new DatePickerDialog(SchoolHolidayActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }


    private void updateLabel() {

       // String myFormat = "MM/dd/yy"; //In which you need put here

        String myFormat = "dd-MM-yyyy hh:mm:ss aa";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (dateType.equals("StartDate"))
        {
            startDate = sdf.format(myCalendar.getTime());
            startDateEditText.setText(startDate.substring(0,10));
        }
        else
        {
            endDate = sdf.format(myCalendar.getTime());
            endDateEditText.setText(endDate.substring(0,10));
        }
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

                        if (response.getData().getGroups().size() > 0){
                            SchoolHolidayActivity.this.notificationGroupList.addAll(NotificationManager.groupList);
                            notificationsGroupAdapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(addListView);
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

                      //  Toast.makeText(SchoolHolidayActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SchoolHolidayActivity.this, "Some error occurred!",Toast.LENGTH_SHORT).show();
                    }

                } else {
                   // Toast.makeText(SchoolHolidayActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
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

}

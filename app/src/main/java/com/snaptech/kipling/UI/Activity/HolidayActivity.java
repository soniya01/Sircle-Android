package com.snaptech.kipling.UI.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.snaptech.kipling.Manager.EventManager;
import com.snaptech.kipling.R;
import com.snaptech.kipling.Utility.AppError;
import com.snaptech.kipling.WebService.PostResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class HolidayActivity extends ActionBarActivity {

    Calendar myCalendar;
    EditText startDateEditText,endDateEditText, title;
    private Button add;
    String dateType,startDate,endDate;
    private ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        add = (Button)findViewById(R.id.add_button);
        title  = (EditText)findViewById(R.id.holidayEventTitle);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title2=title.getText().toString();
                if(title2.length()<=2){

                    Toast.makeText(HolidayActivity.this,"Título debe ser al menos de 3 caracteres",Toast.LENGTH_LONG).show();
                    title.requestFocus();
                }
                else {

                    ringProgressDialog = ProgressDialog.show(HolidayActivity.this, "", "Adding Holiday", true);

                    HashMap params = new HashMap();
                    params.put("event_type", "PH");
                    //params.put("grp",1);
                    params.put("event_name", title.getText().toString());
                    params.put("event_from_date", startDate);
                    params.put("event_to_date", endDate);

                    EventManager.getSharedInstance().addEvent(params, new EventManager.AddEventsManagerListener() {
                        @Override
                        public void onCompletion(PostResponse response, AppError error) {
                            ringProgressDialog.dismiss();
                            if (response != null) {

                                if (response.getStatus() == 200) {
                                    Toast.makeText(HolidayActivity.this, "Holiday Added!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(HolidayActivity.this, "some error occurred", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(HolidayActivity.this, "some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


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
                new DatePickerDialog(HolidayActivity.this, date, myCalendar
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
                new DatePickerDialog(HolidayActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy hh:mm:ss aa"; //In which you need put here
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
        getMenuInflater().inflate(R.menu.menu_holiday, menu);
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
}

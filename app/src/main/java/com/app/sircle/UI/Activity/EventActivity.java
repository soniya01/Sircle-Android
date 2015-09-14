package com.app.sircle.UI.Activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.R;
import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.EventCategory;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends ActionBarActivity {

    AlertDialog alert;
    Button selectCategoryButton;
    private ListView addListView;
    private List<NotificationGroups> notificationGroupList = new ArrayList<NotificationGroups>();
    private List<String> groupNames = new ArrayList<String>();
    private List<String> eventCategory = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        addListView = (ListView) findViewById(R.id.activity_schoolHoliday_list_view);
        setListViewHeightBasedOnChildren(addListView);


        populateDummyData();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, groupNames);
        addListView.setAdapter(arrayAdapter);
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


        selectCategoryButton = (Button) findViewById(R.id.selectCategoryButton);

        selectCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String names[] = {"Arts", "Sports", "Excursion", "Academics", "Performance", "Other"};

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.dialog_list_view, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Select Category");
                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
                // lv.setAdapter(adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
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

                lv.setAdapter(adapter);
                //  alertDialog.show();

                alert = alertDialog.show();


                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        EventManager.getSharedInstance().getEventCategory(new EventManager.GetEventsCategoryManagerListener() {
            @Override
            public void onCompletion(List<EventCategory> eventCategoryList, AppError error) {
                if (error == null && eventCategoryList != null){
                    if (eventCategoryList.size() > 0){
                        for (EventCategory eventCategory : eventCategoryList){
                            EventActivity.this.eventCategory.add(eventCategory.getCategory());
                        }
                    }
                }else {

                }
                //EventActivity.this.eventCategory.addAll(eventCategoryList);
            }
        });
        NotificationGroups n1 = new NotificationGroups();
        n1.setName("Group 1");

        notificationGroupList.add(n1);

        groupNames.add("All");
        groupNames.add(notificationGroupList.get(0).getName());
        groupNames.add("Group 2");
        groupNames.add(notificationGroupList.get(0).getName());
        groupNames.add(notificationGroupList.get(0).getName());
        groupNames.add(notificationGroupList.get(0).getName());
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

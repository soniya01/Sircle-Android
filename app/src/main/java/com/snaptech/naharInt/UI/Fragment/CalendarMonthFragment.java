package com.snaptech.naharInt.UI.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.snaptech.naharInt.Manager.EventManager;
import com.snaptech.naharInt.Manager.LogoutManager;
import com.snaptech.naharInt.R;
import com.snaptech.naharInt.UI.Activity.EventsListActivity;
import com.snaptech.naharInt.UI.Activity.LoginScreen;
import com.snaptech.naharInt.UI.Adapter.CaldroidSampleCustomFragment;
import com.snaptech.naharInt.UI.Model.Event;
import com.snaptech.naharInt.Utility.AppError;
import com.snaptech.naharInt.Utility.Constants;
import com.snaptech.naharInt.WebService.EventDataReponse;
import com.snaptech.naharInt.custom.App;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarMonthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarMonthFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static int month, year;
    private CaldroidFragment caldroidFragment;
    private ProgressDialog progressDialog;
    private View viewFragment;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarMonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarMonthFragment newInstance(String param1, String param2) {
        CalendarMonthFragment fragment = new CalendarMonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarMonthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewFragment = inflater.inflate(R.layout.fragment_calendar_month, container, false);
        // Inflate the layout for this fragment
        // caldroidFragment = new CaldroidFragment();
        caldroidFragment = new CaldroidSampleCustomFragment();



        // caldroidFragment.setMinDate(new Date());

        if (Constants.dateAvailabe.equals(""))
        {
            Bundle args = new Bundle();
            final Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);

            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);
        }
        else
        {
            // String dateString = "03/26/2012 11:49:00 AM";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(Constants.dateAvailabe);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Bundle args = new Bundle();

            Calendar cal = Calendar.getInstance();
            cal.setTime(convertedDate);
            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);

            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);


        }




        //  getCalendarEvents();


//        String myFormat = "MM/dd/yy"; //In which you need put here
//      final  SimpleDateFormat formatter = new SimpleDateFormat(myFormat, Locale.US);

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                //  Toast.makeText(getActivity().getApplicationContext(), formatter.format(date), Toast.LENGTH_SHORT).show();
                //date.

                System.out.println("Date selected is "+date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Intent addLinkIntent = new Intent(getActivity(), EventsListActivity.class);
                addLinkIntent.putExtra("month",month);
                addLinkIntent.putExtra("year",year);
                String day=Calendar.DAY_OF_MONTH+"";
                addLinkIntent.putExtra("day",calendar.get(Calendar.DAY_OF_MONTH));

//                if(day.trim().length()==1){
//                    addLinkIntent.putExtra("date","0"+calendar.get(Calendar.DAY_OF_MONTH)+"-"+month+"-"+year);
//
//                }
                // else{
                addLinkIntent.putExtra("date",calendar.get(Calendar.DAY_OF_MONTH)+"-"+month+"-"+year);
                // }

                startActivity(addLinkIntent);

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                CalendarMonthFragment.this.month = month;
                CalendarMonthFragment.this.year = year;
                // Constants.dateAvailabe="01/"+month+"/"+year;
                getCalendarEvents();
                //  Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                // Toast.makeText(getActivity().getApplicationContext(), "Long click " + formatter.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                caldroidFragment.setShowNavigationArrows(false);
                //  caldroidFragment.monthTitleTextView.setPaintFlags(monthTitleTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                // monthTitleTextView.setText(monthTitle.toUpperCase(Locale.getDefault()));

                // Toast.makeText(getActivity().getApplicationContext(),"Caldroid view is created",Toast.LENGTH_SHORT).show();
            }

        };

        caldroidFragment.setCaldroidListener(listener);


        FragmentActivity myContext = (FragmentActivity)getActivity();


        FragmentTransaction t = myContext.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();


        Button todayButton = (Button)viewFragment.findViewById(R.id.todayButton);


        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caldroidFragment.moveToDate(new Date());

                //  caldroidFragment = new CaldroidFragment();

                //  Bundle args = new Bundle();
                final Calendar cal = Calendar.getInstance();
//                args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
//                args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
                // caldroidFragment.c
                // caldroidFragment.setArguments(args);

                //  caldroidFragment.refreshView();

                month = cal.get(Calendar.MONTH) + 1;
                year = cal.get(Calendar.YEAR);

                getCalendarEvents();


                // give access to the app features

            }

        });

        return viewFragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void getCalendarEvents(){

//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

        // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
        //object.put("regId", Constants.GCM_REG_ID);
        object.put("filter_month",""+month);
        object.put("filter_year", ""+year);
        object.put("page", "1");
        //  object.put("groupId", grpIdString);
        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100,100);
        //layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        ((FrameLayout) viewFragment).addView(progressBar, layoutParams);

        EventManager.getSharedInstance().getEventsMonthWise(object, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
                progressBar.setVisibility(View.GONE);
                if(data!=null){
                    progressBar.setVisibility(View.GONE);
                    if (data.getStatus()==401)
                    {
                        progressBar.setVisibility(View.GONE);
                        //Logout User
                        LogoutManager.getSharedInstance().handleUserLogoutPreferences();
                        Intent loginIntent = new Intent(App.getAppContext(), LoginScreen.class);
                        startActivity(loginIntent);
                        getActivity().finish();

                    }
                    else {
                        if (data.getEvents() != null){
                            if (data.getEvents().size() > 0){
                                HashMap<Date,Integer> dates = new HashMap();
                                for (Event event:  data.getEvents()){

                                    List<Date> dateList=null;
                                    Date date;
                                    Date to_date;
                                    String str = event.getStartDate();
                                    String str_to_date=event.getEndDate();
                                    String[] splited = str.split("\\s+");
                                    String[] splitted_to_date=str_to_date.split("\\s+");
                                    String to_date_String=splitted_to_date[0];
                                    String dateString = splited[0];
                                    if(!event.getStartDate().equalsIgnoreCase(event.getEndDate()))
                                        dateList=getDates(dateString,to_date_String);
                                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                    Calendar cal1=Calendar.getInstance();
                                    Calendar cal2=Calendar.getInstance();
                                    try {
                                        if(dateList!=null) {


                                            System.out.println("Start date is " + dateString + " End date is " + to_date_String + " In between dates " + format.format(dateList.get(0)) + " and " + dateList.get(dateList.size() - 1));

                                            for (int i=0;i<dateList.size();i++){

                                                cal2.setTime(dateList.get(i));
                                                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                                                if(!sameDay){
                                                    date=format.parse(format.format(dateList.get(i)));
                                                    dates.put(date,R.drawable.circular_border);
                                                }
                                            }
                                        }
                                        else {
                                            date = format.parse(dateString);
                                            boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                                            if(!sameDay){
                                                dates.put(date, R.drawable.circular_border);
                                            }
                                            // dates.put(date,R.drawable.camera_icon);
                                            System.out.println("Date ->" + date);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    dateList=null;
                                }
                                progressBar.setVisibility(View.GONE);
                                caldroidFragment.setBackgroundResourceForDates(dates);
                                caldroidFragment.refreshView();

                            }else {
                                progressBar.setVisibility(View.GONE);
                            }
                        }else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                }
                else{

                }
            }
        });
    }
    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
            System.out.println("Inside start date "+date1.getTime()+" and outside is "+date2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

}
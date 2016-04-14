package com.app.sircle.UI.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddLinksActivity;
import com.app.sircle.UI.Activity.EventsListActivity;
import com.app.sircle.UI.Model.Event;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.EventDataReponse;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
        caldroidFragment = new CaldroidFragment();
       // caldroidFragment.setMinDate(new Date());

        if (Constants.dateAvailabe.equals(""))
        {
            Bundle args = new Bundle();
            final Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);

            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);
        }
        else
        {
           // String dateString = "03/26/2012 11:49:00 AM";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);

            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);


        }




        getCalendarEvents();


        String myFormat = "MM/dd/yy"; //In which you need put here
      final  SimpleDateFormat formatter = new SimpleDateFormat(myFormat, Locale.US);

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
              //  Toast.makeText(getActivity().getApplicationContext(), formatter.format(date), Toast.LENGTH_SHORT).show();
                //date.

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Intent addLinkIntent = new Intent(getActivity(), EventsListActivity.class);
                addLinkIntent.putExtra("month",month);
                addLinkIntent.putExtra("year",year);
                addLinkIntent.putExtra("day",calendar.get(Calendar.DAY_OF_MONTH));
                startActivity(addLinkIntent);

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                CalendarMonthFragment.this.month = month;
                CalendarMonthFragment.this.year = year;
                Constants.dateAvailabe="01/"+month+"/"+year;
                getCalendarEvents();
              //  Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
               // Toast.makeText(getActivity().getApplicationContext(), "Long click " + formatter.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
               // Toast.makeText(getActivity().getApplicationContext(),"Caldroid view is created",Toast.LENGTH_SHORT).show();
            }

        };

        caldroidFragment.setCaldroidListener(listener);


        FragmentActivity myContext = (FragmentActivity)getActivity();


        FragmentTransaction t = myContext.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

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

        String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
        object.put("regId", Constants.GCM_REG_ID);
        object.put("month",month);
        object.put("year", year);
        object.put("page", 1);
        object.put("groupId", grpIdString);
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

                if (data != null){
                    if (data.getEventData().getEvents() != null){
                        if (data.getEventData().getEvents().size() > 0){
                            HashMap<Date,Integer> dates = new HashMap();
                            for (Event event:  data.getEventData().getEvents()){
                                String dateString = event.getStartDate();
                                Date date;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    date = format
                                            .parse(dateString);


                                    dates.put(date, android.R.color.holo_blue_light);
                                   // dates.put(date,R.drawable.camera_icon);
                                    System.out.println("Date ->" + date);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }






}

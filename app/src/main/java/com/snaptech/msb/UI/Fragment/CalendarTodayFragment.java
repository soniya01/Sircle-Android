package com.snaptech.msb.UI.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.snaptech.msb.Manager.EventManager;
import com.snaptech.msb.R;
import com.snaptech.msb.UI.Adapter.TermsAdapter;
import com.snaptech.msb.UI.Model.Terms;
import com.snaptech.msb.Utility.AppError;
import com.snaptech.msb.Utility.Constants;
import com.snaptech.msb.custom.App;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarTodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarTodayFragment extends Fragment {

    private ListView termsListView;
    private CaldroidFragment dialogCaldroidFragment;
    private TermsAdapter termsListViewAdapter;
    List<Terms> termsList = new ArrayList<Terms>();
    private View footerView;
    CaldroidListener listener;
    Bundle state ;


    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void setCalendarDate(String date);
    }

   // public void setListener(OnHeadlineSelectedListener listener) {
   //     mCallback = listener;
    //}

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarTodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarTodayFragment newInstance(String param1, String param2) {
        CalendarTodayFragment fragment = new CalendarTodayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarTodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //onAttachFragment(getTargetFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_calendar_today, container, false);

        View viewFragment = inflater.inflate(R.layout.fragment_calendar_today,
                null, true);

        state = savedInstanceState;

        termsListView = (ListView)viewFragment.findViewById(R.id.fragment_term_list_view);
        termsListViewAdapter = new TermsAdapter(getActivity(),termsList,this);
        termsListView.setAdapter(termsListViewAdapter);

       // if (EventManager.getSharedInstance().termsList.size() <= 0){
          populateDummyData();

       // }

        //populateDummyData();

        // footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        // calendarMonthListView.addFooterView(footerView);

//        termsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // open calendar with month and year
//
//            }
//        });


        listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
//                Toast.makeText(getApplicationContext(), formatter.format(date),
//                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
//                Toast.makeText(getApplicationContext(), text,
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
//                Toast.makeText(getApplicationContext(),
//                        "Long click " + formatter.format(date),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
//                if (caldroidFragment.getLeftArrowButton() != null) {
//                    Toast.makeText(getApplicationContext(),
//                            "Caldroid view is created", Toast.LENGTH_SHORT)
//                            .show();
//                }
            }

        };



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

    public void populateDummyData(){

        EventManager.getSharedInstance().getAllTerms(null, new EventManager.GetAllTermsManagerListener() {
            @Override
            public void onCompletion(List<Terms> termslist, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (termslist.size() > 0) {

                        termsList.clear();
                       termsList.addAll(termslist);
                        termsListViewAdapter.notifyDataSetChanged();
//                        if (CalendarTodayFragment.this.termsList.size() > 0) {
//                            CalendarTodayFragment.this.termsList.clear();
//                            CalendarTodayFragment.this.termsList.addAll(termsList);
//                            termsListViewAdapter.notifyDataSetChanged();
//                            // update group notifictaion for all groups
//                            //updateAllGroup();
//
//                        } else {
//                            //SettingsActivity.this.notificationGroupList.clear();
//                            CalendarTodayFragment.this.termsList.addAll(termsList);
//
//                        }

                       // Toast.makeText(CalendarTodayFragment.this, "", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(App.getAppContext(), "Sorry no terms data found",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(App.getAppContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void showDialogCalendar(String date)
    {
        //String dateString = "03/26/2012 11:49:00 AM";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date convertedDate = new Date();
//        try {
//            convertedDate = dateFormat.parse(date);
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        Calendar thatDay = Calendar.getInstance();
//        thatDay.setTime(convertedDate);
//
//      //  Toast.makeText(getActivity(),convertedDate.toString(),Toast.LENGTH_SHORT).show();
//
//        dialogCaldroidFragment = new CaldroidFragment();
//        dialogCaldroidFragment.setCaldroidListener(listener);
//
//
//        // If activity is recovered from rotation
//        final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
//        if (state != null) {
//            dialogCaldroidFragment.restoreDialogStatesFromKey(
//                    getFragmentManager(), state,
//                    "DIALOG_CALDROID_SAVED_STATE", dialogTag);
//            Bundle args = dialogCaldroidFragment.getArguments();
//            if (args == null) {
//                args = new Bundle();
//              //  Bundle args = new Bundle();
//               // final Calendar cal = Calendar.getInstance();
//                args.putInt(CaldroidFragment.MONTH, thatDay.get(Calendar.MONTH) + 1);
//                args.putInt(CaldroidFragment.YEAR, thatDay.get(Calendar.YEAR));
//                dialogCaldroidFragment.setArguments(args);
//            }
//        } else {
//            // Setup arguments
//            Bundle bundle = new Bundle();
//            bundle.putInt(CaldroidFragment.MONTH, thatDay.get(Calendar.MONTH) + 1);
//            bundle.putInt(CaldroidFragment.YEAR, thatDay.get(Calendar.YEAR));
//            // Setup dialogTitle
//            dialogCaldroidFragment.setArguments(bundle);
//        }
//
//        dialogCaldroidFragment.show(getFragmentManager(),
//                dialogTag);
//
//        dialogCaldroidFragment.setSelectedDates(convertedDate,convertedDate);
       // mCallback.setCalendarDate("ok");
        Constants.dateAvailabe = date;
        CalendarFragment.viewPager.setCurrentItem(0);
       // CalendarMonthFragment.setCalendarDate();
       // mCallback = new OnHeadlineSelectedListener();
       // mCallback.setCalendarDate(date);

    }




}

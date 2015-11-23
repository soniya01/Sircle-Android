package com.app.sircle.UI.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.EventManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.EventDetailActivity;
import com.app.sircle.UI.Activity.EventsListActivity;
import com.app.sircle.UI.Adapter.CalendarMonthListAdapter;
import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.Event;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.EventData;
import com.app.sircle.WebService.EventDataReponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarListFragment extends Fragment {


    private ListView calendarMonthListView;
    private CalendarMonthListAdapter calendarMonthListViewAdapter;
    private List<Event> calendarMonthList = new ArrayList<Event>();
    private View footerView;
    ProgressDialog ringProgressDialog;

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
     * @return A new instance of fragment CalendarListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarListFragment newInstance(String param1, String param2) {
        CalendarListFragment fragment = new CalendarListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarListFragment() {
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
        View viewFragment = inflater.inflate(R.layout.fragment_calendar_list,
                null, true);

        //populateDummyData();
        calendarMonthListView = (ListView)viewFragment.findViewById(R.id.fragment_month_list_view);
        //calendarMonthList = EventManager.eventList;
        calendarMonthListViewAdapter = new CalendarMonthListAdapter(getActivity(), calendarMonthList);
        calendarMonthListView.setAdapter(calendarMonthListViewAdapter);

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        calendarMonthListView.addFooterView(footerView);

        calendarMonthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getActivity(),EventDetailActivity.class);
                detailIntent.putExtra("eventId",calendarMonthList.get(position).getId());
                startActivity(detailIntent);
            }
        });

        //if (calendarMonthList.size() <= 0){
            populateDummyData();
        //}

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

        String grpIdString = "";
        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
            if (i == 0){
                grpIdString = NotificationManager.grpIds.get(i);
            }else {
                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
            }
        }
        HashMap map = new HashMap();
        map.put("regId", Constants.GCM_REG_ID);
        map.put("month",CalendarMonthFragment.month);
        map.put("year", CalendarMonthFragment.year);
        map.put("page",1);
        map.put("groupId",grpIdString);

        ringProgressDialog = ProgressDialog.show(getActivity(), "", "", true);

        EventManager.getSharedInstance().getEventsMonthWise(map, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
                ringProgressDialog.dismiss();
                if (data != null){
                    if (data.getEventData().getEvents() != null){
                        if (data.getEventData().getEvents().size() > 0){
                            calendarMonthList.addAll(data.getEventData().getEvents());
                            calendarMonthListViewAdapter.notifyDataSetChanged();


                        }else {
                            Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Some problem occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /////
//        EventManager.getSharedInstance().getAllEvents(map, new EventManager.GetMonthwiseEventsManagerListener() {
//            @Override
//            public void onCompletion(EventDataReponse data, AppError error) {
//               ringProgressDialog.dismiss();
//                if (data != null) {
//                    if (data.getStatus() == 200){
//                        if (data.getEventData().getEvents().size() > 0){
//                            calendarMonthList.addAll(data.getEventData().getEvents());
//                            calendarMonthListViewAdapter.notifyDataSetChanged();
//
//                        }else {
//                            Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

}

package com.snaptech.msb.UI.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snaptech.msb.Manager.EventManager;
import com.snaptech.msb.R;
import com.snaptech.msb.UI.Activity.EventDetailActivity;
import com.snaptech.msb.UI.Adapter.CalendarMonthListAdapter;
import com.snaptech.msb.UI.Model.Event;
import com.snaptech.msb.Utility.AppError;
import com.snaptech.msb.WebService.EventDataReponse;
import com.snaptech.msb.custom.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarListFragment extends Fragment implements AbsListView.OnScrollListener{


    private ListView calendarMonthListView;
    private CalendarMonthListAdapter calendarMonthListViewAdapter;
    private List<Event> calendarMonthList = new ArrayList<Event>();
    private View footerView;
    ProgressDialog ringProgressDialog;
    private View viewFragment;
    int currentFirstVisibleItem,currentVisibleItemCount,currentScrollState,pageCount, totalRecord;
    boolean isLoading;
    Context myContext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

  //  String grpIdString;

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

        pageCount = 1;
        viewFragment = inflater.inflate(R.layout.fragment_calendar_list,
                null, true);

        myContext = getActivity();

        calendarMonthListView = (ListView)viewFragment.findViewById(R.id.fragment_month_list_view);
        //calendarMonthList = EventManager.eventList;


        footerView = View.inflate(myContext, R.layout.list_view_padding_footer, null);
        calendarMonthListView.addFooterView(footerView);
        calendarMonthListView.setOnScrollListener(this);

        TextView emptyText = (TextView)viewFragment.findViewById(R.id.empty);
        calendarMonthListView.setEmptyView(emptyText);

        calendarMonthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(myContext,EventDetailActivity.class);
                detailIntent.putExtra("eventId",calendarMonthList.get(position).getId());
                startActivity(detailIntent);
            }
        });

        //if (calendarMonthList.size() <= 0){
           // grpIdString  = NotificationManager.getSharedInstance().getGroupIds(myContext);

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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentScrollState = scrollState;
        isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        currentFirstVisibleItem = firstVisibleItem;
        currentVisibleItemCount = visibleItemCount;
    }

    private void isScrollCompleted() {

        if (totalRecord == calendarMonthList.size()){

        }else {
            if (this.currentVisibleItemCount > 0 && this.currentScrollState == 0) {
                /*** In this way I detect if there's been a scroll which has completed ***/
                /*** do the work for load more date! ***/
                System.out.println("Load not");
                if(!isLoading){
                    isLoading = true;
                    System.out.println("Load More");
                    loadMoreData();
                    // Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    public void loadMoreData() {
        pageCount += 1;
//        String grpIdString = "";
//        for (int i = 0; i < NotificationManager.grpIds.size(); i++) {
//            if (i == 0) {
//                grpIdString = NotificationManager.grpIds.get(i);
//            } else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i);
//            }
//        }



        HashMap map = new HashMap();
      //  map.put("regId", Constants.GCM_REG_ID);
        map.put("month",""+CalendarMonthFragment.month);
        map.put("year", ""+CalendarMonthFragment.year);
        map.put("page", ""+pageCount);
     //   map.put("groupId", grpIdString);

        EventManager.getSharedInstance().getEventsMonthWise(map, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
                isLoading = false;
                if (data != null){
                    if (data.getEvents() != null){
                        if (data.getEvents().size() > 0){
                            if (calendarMonthList.size() == 0){
                                calendarMonthList.addAll(data.getEvents());
                                calendarMonthListViewAdapter = new CalendarMonthListAdapter(App.getAppContext(), calendarMonthList);
                                calendarMonthListView.setAdapter(calendarMonthListViewAdapter);

                            }else {
                                calendarMonthList.addAll(data.getEvents());
                                calendarMonthListViewAdapter.notifyDataSetChanged();
                            }
                        }else {
                         //   Toast.makeText(myContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                       // Toast.makeText(myContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(App.getAppContext(), "Some problem occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });



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

    @Override
    public void onResume() {
        super.onResume();
       // populateDummyData();
    }

    public void populateDummyData(){

        pageCount=1;
//        String grpIdString = "";
//        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
//            if (i == 0){
//                grpIdString = NotificationManager.grpIds.get(i);
//            }else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
//            }
//        }

       // String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap map = new HashMap();
      //  map.put("regId", Constants.GCM_REG_ID);
        map.put("filter_month",""+CalendarMonthFragment.month);
        map.put("filter_year", ""+CalendarMonthFragment.year);
        map.put("page", "1");
      //  map.put("groupId", grpIdString);

        //ringProgressDialog = ProgressDialog.show(getActivity(), "", "", true);

        final ProgressBar progressBar = new ProgressBar(App.getAppContext(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        //layoutParams.gravity = Gravity.CENTER;
        ((RelativeLayout) viewFragment).addView(progressBar, layoutParams);

        EventManager.getSharedInstance().getEventsMonthWise(map, new EventManager.GetMonthwiseEventsManagerListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
               // ringProgressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                //isLoading = false;
                if (data != null){
                    if (data.getEvents() != null){
                        if (data.getEvents().size() > 0){
                            if (calendarMonthList.size() == 0){
                               // totalRecord = data.getEventData().getTotalRecords();
                                calendarMonthList.clear();
                                calendarMonthList.addAll(data.getEvents());
                                calendarMonthListViewAdapter = new CalendarMonthListAdapter(App.getAppContext(), calendarMonthList);
                                calendarMonthListView.setAdapter(calendarMonthListViewAdapter);

                            }else {
                                calendarMonthList.clear();
                                calendarMonthList.addAll(data.getEvents());
                                calendarMonthListViewAdapter.notifyDataSetChanged();
                            }
                        }else {
                         //  calendarMonthList.clear();
//                            //
                         //   calendarMonthListViewAdapter.notifyDataSetChanged();
                          //  Toast.makeText(myContext, data.getMessage(), Toast.LENGTH_SHORT).show();

                            if (calendarMonthListViewAdapter!=null)
                            {
                                calendarMonthList.clear();
//                            //
                                calendarMonthListViewAdapter.notifyDataSetChanged();
                                //  Toast.makeText(myContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }else {

                        if (calendarMonthListViewAdapter!=null)
                        {
                            calendarMonthList.clear();
//                            //
                            calendarMonthListViewAdapter.notifyDataSetChanged();
                          //  Toast.makeText(myContext, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }else {
                    Toast.makeText(App.getAppContext(), "Some problem occurred", Toast.LENGTH_SHORT).show();
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

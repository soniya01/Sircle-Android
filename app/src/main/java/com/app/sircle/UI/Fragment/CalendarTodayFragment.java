package com.app.sircle.UI.Fragment;

import android.app.Activity;
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
import com.app.sircle.R;
import com.app.sircle.UI.Adapter.CalendarMonthListAdapter;
import com.app.sircle.UI.Adapter.TermsAdapter;
import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.Utility.AppError;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarTodayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarTodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarTodayFragment extends Fragment {

    private ListView termsListView;
    private TermsAdapter termsListViewAdapter;
    private List<Terms> termsList = new ArrayList<Terms>();
    private View footerView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_calendar_today, container, false);

        View viewFragment = inflater.inflate(R.layout.fragment_calendar_today,
                null, true);

        termsListView = (ListView)viewFragment.findViewById(R.id.fragment_term_list_view);

        populateDummyData();

        // footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        // calendarMonthListView.addFooterView(footerView);

        termsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    public void populateDummyData(){

        EventManager.getSharedInstance().getAllTerms(null, new EventManager.GetAllTermsManagerListener() {
            @Override
            public void onCompletion(List<Terms> termsList, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (termsList.size() > 0) {

                        if (CalendarTodayFragment.this.termsList.size() > 0) {
                            CalendarTodayFragment.this.termsList.clear();
                            CalendarTodayFragment.this.termsList.addAll(termsList);
                            termsListViewAdapter.notifyDataSetChanged();
                            // update group notifictaion for all groups
                            //updateAllGroup();

                        } else {
                            //SettingsActivity.this.notificationGroupList.clear();
                            CalendarTodayFragment.this.termsList.addAll(termsList);
                            termsListViewAdapter = new TermsAdapter(getActivity(), termsList);
                            termsListView.setAdapter(termsListViewAdapter);
                        }

                       // Toast.makeText(CalendarTodayFragment.this, "", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Sorry no terms data found",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}

package com.snaptech.coxandkingshr.UI.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaptech.coxandkingshr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituteInfo extends Fragment {


    public InstituteInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_institute_info, container, false);
    }

}
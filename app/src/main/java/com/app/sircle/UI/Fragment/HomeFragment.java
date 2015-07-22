package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.app.sircle.R;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;

/**
 * Created by soniya on 7/22/15.
 */
public class HomeFragment extends Fragment {

    private ImageButton drawerImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_home,
                null, true);

        drawerImageButton = (ImageButton) viewFragment.findViewById(R.id.fragment_home_drawer_icon);

        drawerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SlidingPaneInterface) getActivity()).tappedDrawerIcon();
            }
        });

        return viewFragment;
    }
}

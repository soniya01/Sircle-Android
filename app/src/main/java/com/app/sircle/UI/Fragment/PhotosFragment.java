package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;

/**
 * Created by soniya on 23/07/15.
 */
public class PhotosFragment extends Fragment {


    private ImageButton drawerImageButton;
    private ListView albumListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_photos, null , true);

        drawerImageButton = (ImageButton) viewFragment.findViewById(R.id.fragment_home_drawer_icon);

        drawerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SlidingPaneInterface) getActivity()).tappedDrawerIcon();
            }
        });

        albumListView = (ListView)viewFragment.findViewById(R.id.fragment_photos_list_view);
        albumListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        return viewFragment;
    }
}

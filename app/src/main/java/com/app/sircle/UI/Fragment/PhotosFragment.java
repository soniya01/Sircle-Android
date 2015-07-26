package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.PhotosListViewAdapter;
import com.app.sircle.UI.Model.Photo;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by soniya on 23/07/15.
 */
public class PhotosFragment extends Fragment {


    private ImageButton drawerImageButton;
    private ListView albumListView;
    private PhotosListViewAdapter photosListViewAdapter;
    private List<Photo> photos = new ArrayList<Photo>();

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
        populateDummyData();

        albumListView = (ListView)viewFragment.findViewById(R.id.fragment_photos_list_view);
        photosListViewAdapter = new PhotosListViewAdapter(getActivity(), photos);
        albumListView.setAdapter(photosListViewAdapter);
        albumListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return viewFragment;
    }

    private void populateDummyData() {

        Photo photo = new Photo();
        photo.setAlbumID(1);
        photo.setAlbumTitle("Sports Day");
        photo.setAlbumCoverImageURL("http://img.youtube.com/vi/GDFUdMvacI0/0.jpg");
        photo.setNumberOfPhotos(5);
        photo.setPublishDate(new Date());

        photos.add(photo);
        photos.add(photo);
        photos.add(photo);
        photos.add(photo);
        photos.add(photo);

    }



}

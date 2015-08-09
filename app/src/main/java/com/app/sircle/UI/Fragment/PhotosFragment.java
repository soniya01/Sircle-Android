package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddAlbumActivity;
import com.app.sircle.UI.Activity.AlbumDetailsActivity;
import com.app.sircle.UI.Adapter.PhotosListViewAdapter;
import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.Photo;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by soniya on 23/07/15.
 */
public class PhotosFragment extends Fragment {

    private ListView albumListView;
    private PhotosListViewAdapter photosListViewAdapter;
    private List<Photo> photos = new ArrayList<Photo>();
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private View footerView;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_photos, null , true);

        populateDummyData();

        albumListView = (ListView)viewFragment.findViewById(R.id.fragment_photos_list_view);
        floatingActionButton = (FloatingActionButton)viewFragment.findViewById(R.id.fab);
        photosListViewAdapter = new PhotosListViewAdapter(getActivity(), photos);
        albumListView.setAdapter(photosListViewAdapter);
        albumListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent albumIntent = new Intent(getActivity(), AlbumDetailsActivity.class);
                startActivity(albumIntent);

            }
        });

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        albumListView.addFooterView(footerView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(getActivity(), AddAlbumActivity.class);
                startActivity(albumIntent);

            }
        });

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

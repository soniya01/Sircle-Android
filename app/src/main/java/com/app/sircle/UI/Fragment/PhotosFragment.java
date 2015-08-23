package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddAlbumActivity;
import com.app.sircle.UI.Activity.AlbumDetailsActivity;
import com.app.sircle.UI.Adapter.PhotosListViewAdapter;
import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.Photo;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.AppError;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private View viewFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_photos, null , true);

        albumListView = (ListView)viewFragment.findViewById(R.id.fragment_photos_list_view);
       // photosListViewAdapter = new PhotosListViewAdapter(getActivity(), photos);
        //albumListView.setAdapter(photosListViewAdapter);
        populateDummyData();

        floatingActionButton = (FloatingActionButton)viewFragment.findViewById(R.id.fab);
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
                //TODO: add album api call

                Intent albumIntent = new Intent(getActivity(), AddAlbumActivity.class);
                startActivity(albumIntent);

            }
        });

        return viewFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void populateDummyData() {

        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ((RelativeLayout)viewFragment).addView(progressBar, params);

        HashMap map = new HashMap();
        map.put("regId", "id");
        map.put("groupId", "1");
        map.put("val", "val");

        PhotoManager.getSharedInstance().getAlbums(map, new PhotoManager.GetAlbumsManagerListener() {
            @Override
            public void onCompletion(List<Photo> photos, AppError error) {
                progressBar.setVisibility(View.GONE);
                if (photos != null){
                    if (photos.size() > 0){
                        PhotosFragment.this.photos.clear();
                        PhotosFragment.this.photos.addAll(photos);
                        if (PhotosFragment.this.photos.size() >0){
                            photosListViewAdapter.notifyDataSetChanged();
                        }else {
                            photosListViewAdapter = new PhotosListViewAdapter(getActivity(), photos);
                            albumListView.setAdapter(photosListViewAdapter);
                        }
                    }
                }else {
                    if (photos == null){
                        Toast.makeText(getActivity(), "Sorry no data available", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

//        Photo photo = new Photo();
//        photo.setAlbumID(1);
//        photo.setAlbumTitle("Sports Day");
//        photo.setAlbumCoverImageURL("http://img.youtube.com/vi/GDFUdMvacI0/0.jpg");
//        photo.setNumberOfPhotos(5);
//        photo.setPublishDate(new Date());
//
//        photos.add(photo);
//        photos.add(photo);
//        photos.add(photo);
//        photos.add(photo);
//        photos.add(photo);

    }



}

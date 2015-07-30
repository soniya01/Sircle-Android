package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.app.sircle.R;
import com.app.sircle.UI.Adapter.AlbumDetailsGridAdapter;
import com.app.sircle.UI.Model.AlbumDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumDetailsActivity extends Activity {

    private GridView albumGridView;
    private AlbumDetailsGridAdapter albumDetailsGridAdapter;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        populateDummyData();

        albumGridView = (GridView)findViewById(R.id.album_details_grid_view);
        albumDetailsGridAdapter = new AlbumDetailsGridAdapter(this, albumDetailsList);
        albumGridView.setAdapter(albumDetailsGridAdapter);

    }

    void populateDummyData(){

        AlbumDetails albumDetails = new AlbumDetails();
        albumDetails.setPhotoID(1);
        albumDetails.setPhotoCaption("Nature");
        albumDetails.setPublishDate(new Date());

        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
        albumDetailsList.add(albumDetails);
    }

}

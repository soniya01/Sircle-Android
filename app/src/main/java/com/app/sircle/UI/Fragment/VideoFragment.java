package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.VideoActivity;
import com.app.sircle.UI.Adapter.VideoListViewAdapter;
import com.app.sircle.UI.Model.Video;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment {

    private ListView videoListView;
    private VideoListViewAdapter videoListViewAdapter;
    private List<Video> videoList = new ArrayList<Video>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_video,
                null, true);

        populateDummyData();

        videoListView = (ListView)viewFragment.findViewById(R.id.fragment_video_list_view);
        videoListViewAdapter = new VideoListViewAdapter(getActivity(), videoList);

        videoListView.setAdapter(videoListViewAdapter);
        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // open video playing activity
                Intent videoActivity = new Intent(getActivity(), VideoActivity.class);
                videoActivity.putExtra("videoUrl",videoList.get(position).videoEmbedURL.toString());
                startActivity(videoActivity);
            }
        });

        return viewFragment;
    }

    public void populateDummyData(){
        Video video = new Video();
        video.setVideoEmbedURL("https://www.youtube.com/watch?v=1uTVK2dXTUk");
        video.setVideoSource("Youtube");
        videoList.add(video);
        videoList.add(video);
        videoList.add(video);
        videoList.add(video);
    }




}

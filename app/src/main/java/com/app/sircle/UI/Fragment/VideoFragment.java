package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.VideoActivity;
import com.app.sircle.UI.Adapter.VideoListViewAdapter;
import com.app.sircle.UI.Model.Video;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.DeveloperKey;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment {


    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

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
               // Intent videoActivity = new Intent(getActivity(), VideoActivity.class);
               // videoActivity.putExtra("videoUrl", videoList.get(position).videoEmbedURL.toString());
               // startActivity(videoActivity);

             Intent  intent = YouTubeStandalonePlayer.createVideoIntent(
                        getActivity(), DeveloperKey.DEVELOPER_KEY, "cdgQpa1pUUE", 0, false, false);

                if (intent != null) {
                    if (canResolveIntent(intent)) {
                        startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
                    } else {
                        // Could not resolve the intent - must need to install or update the YouTube API service.
                        YouTubeInitializationResult.SERVICE_MISSING
                                .getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
                    }
                }
            }
        });

        return viewFragment;
    }

    public void populateDummyData(){
        Video video = new Video();
        video.setVideoEmbedURL("https://www.youtube.com/watch?v=cdgQpa1pUUE");
        video.setVideoSource("Youtube");
        videoList.add(video);
        videoList.add(video);
        videoList.add(video);
        videoList.add(video);
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }



    }

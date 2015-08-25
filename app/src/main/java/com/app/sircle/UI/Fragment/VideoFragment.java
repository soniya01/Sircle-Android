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
import android.widget.Toast;

import com.app.sircle.Manager.VideoManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.VideoActivity;
import com.app.sircle.UI.Adapter.VideoListViewAdapter;
import com.app.sircle.UI.Model.Video;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.DeveloperKey;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VideoFragment extends Fragment {

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    private ListView videoListView;
    private VideoListViewAdapter videoListViewAdapter;
    private List<Video> videoList = new ArrayList<Video>();
    private View footerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_video,
                null, true);



        videoListView = (ListView)viewFragment.findViewById(R.id.fragment_video_list_view);

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        videoListView.addFooterView(footerView);
        videoListViewAdapter = new VideoListViewAdapter(getActivity(), videoList);
        videoListView.setAdapter(videoListViewAdapter);

        populateDummyData();

        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = YouTubeStandalonePlayer.createVideoIntent(
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

        HashMap object = new HashMap();
        object.put("regId", "id");
        object.put("groupId",1);
        object.put("val", "val");

        VideoManager.getSharedInstance().getAllVideos(object, new VideoManager.VideoManagerListener() {
            @Override
            public void onCompletion(List<Video> videoList, AppError error) {
                if (error == null || error.getErrorCode() == AppError.NO_ERROR){
                    if (videoList.size() > 0){
                        if (VideoFragment.this.videoList.size() == 0){
                            VideoFragment.this.videoList = videoList;
                            videoListViewAdapter = new VideoListViewAdapter(getActivity(), VideoFragment.this.videoList);
                            videoListView.setAdapter(videoListViewAdapter);
                        }else {
                            VideoFragment.this.videoList = videoList;
                            videoListViewAdapter.notifyDataSetChanged();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Sorry no data available", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection",Toast.LENGTH_SHORT).show();
                }

            }
        });
//        Video video = new Video();
//        video.setVideoEmbedURL("https://www.youtube.com/watch?v=cdgQpa1pUUE");
//        video.setVideoSource("Youtube");
//        videoList.add(video);
//        videoList.add(video);
//        videoList.add(video);
//        videoList.add(video);
//        videoList.add(video);
//        videoList.add(video);
//        videoList.add(video);
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (VideoFragment.this.videoList.size() > 0 ){
            videoListViewAdapter.notifyDataSetChanged();
        }
    }
}

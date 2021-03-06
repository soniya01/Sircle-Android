package com.snaptech.msb.UI.Fragment;



import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.snaptech.msb.Manager.NotificationManager;
import com.snaptech.msb.Manager.VideoManager;
import com.snaptech.msb.R;
import com.snaptech.msb.UI.Activity.VimeoWebviewActivity;
import com.snaptech.msb.UI.Adapter.VideoListViewAdapter;
import com.snaptech.msb.UI.Model.Video;
import com.snaptech.msb.Utility.AppError;
import com.snaptech.msb.Utility.DeveloperKey;
import com.snaptech.msb.WebService.VideoResponse;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    private ListView videoListView;
    private VideoListViewAdapter videoListViewAdapter;
    private List<Video> videoList = new ArrayList<Video>();
    private View footerView, viewFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    int totalRecord;
    int currentFirstVisibleItem, currentVisibleItemCount, currentScrollState, pageCount;
    boolean isLoading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewFragment = inflater.inflate(R.layout.fragment_video,
                null, true);


        pageCount = 1;
        videoListView = (ListView) viewFragment.findViewById(R.id.fragment_video_list_view);

//        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
//        videoListView.addFooterView(footerView, null, false);

        swipeRefreshLayout = (SwipeRefreshLayout) viewFragment.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(VideoFragment.this);

        VideoFragment.this.videoList = VideoManager.getSharedInstance().videoList;

        videoListViewAdapter = new VideoListViewAdapter(getActivity(), VideoFragment.this.videoList);
        videoListView.setAdapter(videoListViewAdapter);
        videoListView.setOnScrollListener(this);

        if (VideoFragment.this.videoList.size() <= 0) {
           // populateDummyData();

            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);

                                            populateDummyData();
                                        }
                                    }
            );
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
//            swipeRefreshLayout.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            swipeRefreshLayout.setRefreshing(true);
//
//                                            populateDummyData();
//                                        }
//                                    }
//            );
        }


        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (videoList.get(position).getVideoType() != null){

                    if (videoList.get(position).getVideoType().equals("youtube")) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                                getActivity(), DeveloperKey.DEVELOPER_KEY, videoList.get(position).getVideoId(), 0, false, false);

                        if (intent != null) {
                            if (canResolveIntent(intent)) {
                                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
                            } else {
                                // Could not resolve the intent - must need to install or update the YouTube API service.
                                YouTubeInitializationResult.SERVICE_MISSING
                                        .getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
                            }
                        }
                    } else {
                        Intent intent = new Intent(getActivity(), VimeoWebviewActivity.class);
                        intent.putExtra("VideoUrl", videoList.get(position).getVideoId());
                        startActivity(intent);

                    }

            }
                else
                {

                    Toast.makeText(getActivity(), "Something went wrong please try after some time", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return viewFragment;
    }

    public void populateDummyData() {

//        final ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        ((RelativeLayout) viewFragment).addView(progressBar, layoutParams);

//        String grpIdString = "";
//        for (int i = 0; i < NotificationManager.grpIds.size(); i++) {
//            if (i == 0) {
//                grpIdString = NotificationManager.grpIds.get(i);
//            } else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i);
//            }
//        }

     //   String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        HashMap object = new HashMap();
       // object.put("regId", Constants.GCM_REG_ID);
        //object.put("groupId", grpIdString);
        object.put("page", "1");

        System.out.println("Populate Dummy");

        VideoManager.getSharedInstance().getAllVideos(object, new VideoManager.VideoManagerListener() {
            @Override
            public void onCompletion(VideoResponse response, AppError error) {
               // progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {
                        if (response.getStatus() == 200) {
                            if (response.getData().getVideos().size() > 0) {

                               // totalRecord = response.getData().getTotalRecords();
                                videoList.clear();
                                videoList.addAll(VideoManager.videoList);
                                videoListViewAdapter.notifyDataSetChanged();

                            } else {
                              //  Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           // Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    @Override
    public void onResume() {
        super.onResume();
       // populateDummyData();
    }

    private void isScrollCompleted() {

        if (totalRecord == videoList.size()) {

        } else {
            if (this.currentVisibleItemCount > 0 && this.currentScrollState == 0) {
                /*** In this way I detect if there's been a scroll which has completed ***/
                /*** do the work for load more date! ***/
                System.out.println("Load not");
                if (!isLoading) {
                    isLoading = true;
                    System.out.println("Load More");
                    loadMoreData();
                    // Toast.makeText(getActivity(),"Load More",Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    @Override
    public void onRefresh() {
        //populateDummyData();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentScrollState = scrollState;
        isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        currentFirstVisibleItem = firstVisibleItem;
        currentVisibleItemCount = visibleItemCount;
    }

    public void loadMoreData() {
        pageCount += 1;
//        String grpIdString = "";
//        for (int i = 0; i < NotificationManager.grpIds.size(); i++) {
//            if (i == 0) {
//                grpIdString = NotificationManager.grpIds.get(i);
//            } else {
//                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i);
//            }
//        }

//        String grpIdString = NotificationManager.getSharedInstance().getGroupIds(getActivity());

        System.out.println("Load Dummy");

        HashMap object = new HashMap();
       // object.put("regId", Constants.GCM_REG_ID);
        //object.put("groupId", grpIdString);
        object.put("page", "1");

        VideoManager.getSharedInstance().getAllVideos(object, new VideoManager.VideoManagerListener() {
            @Override
            public void onCompletion(VideoResponse response, AppError error) {
                isLoading = false;
                if (error == null || error.getErrorCode() == AppError.NO_ERROR) {
                    if (response != null) {
                        if (response.getStatus() == 200) {
                            if (response.getData().getVideos().size() > 0) {

                                videoList.addAll(VideoManager.videoList);
                                videoListViewAdapter.notifyDataSetChanged();

                            } else {
                            //    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           // Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


}

package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.Model.Video;
import com.app.sircle.Utility.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class VideoListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Video> videoList = new ArrayList<Video>();
    private LayoutInflater inflater;

    public VideoListViewAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
        inflater  = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder ;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_view_video_row,
                    parent, false);
            viewHolder.videoImageView = (ImageView)convertView.findViewById(R.id.fragment_video_image_view);
            viewHolder.videoSourceLabel = (TextView)convertView.findViewById(R.id.fragment_video_source_label);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String videoUrl = "https://www.youtube.com/watch?v=1uTVK2dXTUk";

        String videoThumbnailUrl = extractYoutubeThumbnail(videoUrl);
        viewHolder.videoSourceLabel.setText("Youtube");

        Picasso.with(context)
                .load(videoThumbnailUrl)
                .into(viewHolder.videoImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //imageViewMap.invalidate();

                    }

                    @Override
                    public void onError() {
                    }
                });

        return convertView;
    }

    public String extractYoutubeThumbnail(String videoUrl){

        String[] urlParts = videoUrl.split("v=");
        String[] videoUrlParams = null;
        String videoThumbnailLink="";
        if (urlParts.length == 2){
            videoThumbnailLink = urlParts[1];
            if (urlParts[1].contains("&")){
                videoUrlParams = urlParts[1].split("&");
                videoThumbnailLink = videoUrlParams[0];

            }
            videoThumbnailLink = Constants.YOUTUBE_VIDEO_BASE_IMAGE_URL+videoThumbnailLink+Constants.YOUTUBE_VIDEO_BASE_IMAGE_FILE_URL;

        }

        return videoThumbnailLink;
    }


    static class ViewHolder {

        private ImageView videoImageView;
        private TextView videoSourceLabel;
    }
}

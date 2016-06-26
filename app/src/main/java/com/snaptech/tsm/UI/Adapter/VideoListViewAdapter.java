package com.snaptech.tsm.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snaptech.tsm.R;
import com.snaptech.tsm.UI.Model.Video;
import com.snaptech.tsm.Utility.Constants;

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
           // viewHolder.videoImageView = (ImageView)convertView.findViewById(R.id.fragment_video_image_view);
            viewHolder.videoSourceLabel = (TextView)convertView.findViewById(R.id.fragment_video_source_label);
            viewHolder.videoDate = (TextView) convertView.findViewById(R.id.links_row_publish_label_day);
            viewHolder.videoTime = (TextView) convertView.findViewById(R.id.links_row_publish_label_time);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String videoUrl = videoList.get(position).getVideoEmbedURL();//.split("embed/");//"https://www.youtube.com/watch?v=cdgQpa1pUUE";

       // String videoThumbnailUrl =  videoList.get(position).getVideoThumbURL();//extractYoutubeThumbnail(videoUrl);
        viewHolder.videoSourceLabel.setText(videoList.get(position).getName());

        String str = videoList.get(position).getPublishDate();
        String[] splited = str.split("\\s+");

        viewHolder.videoDate.setText(splited[0]);
        viewHolder.videoTime.setText(splited[1]);

//        if (!videoThumbnailUrl.equals("")){
//            Picasso.with(context)
//                    .load(videoThumbnailUrl)
//                    .into(viewHolder.videoImageView, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError() {
//                        }
//                    });
//        }

        return convertView;
    }

    public String extractYoutubeThumbnail(String videoUrl){

        String[] urlParts = videoUrl.split("embed/");
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

      //  private ImageView videoImageView;
        private TextView videoSourceLabel;
        private TextView videoDate, videoTime;
    }
}

package com.snaptech.mslgroupindia.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snaptech.mslgroupindia.R;
import com.snaptech.mslgroupindia.UI.Model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 23/07/15.
 */
public class PhotosListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Photo> photoList = new ArrayList<Photo>();
    private LayoutInflater inflater;

    public PhotosListViewAdapter(Context mContext, List<Photo> photoList) {
        this.mContext = mContext;
        this.photoList = photoList;
        inflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return photoList.get(position).albumID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_view_photos_row,
                    parent, false);
           // viewHolder.albumImageView = (ImageView) convertView.findViewById(R.id.photos_row_album_image);
            viewHolder.albumLabelName = (TextView) convertView.findViewById(R.id.notification_row_title_label_name);
            viewHolder.imagesCountLabel = (TextView) convertView.findViewById(R.id.notification_row_desc_label);
            viewHolder.albumDate = (TextView) convertView.findViewById(R.id.links_row_publish_label_day);
            viewHolder.albumTime = (TextView) convertView.findViewById(R.id.links_row_publish_label_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        Picasso.with(mContext)
//                .load(photoList.get(position).albumCoverImageURL)
//                .into(viewHolder.albumImageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        viewHolder.imagesCountLabel.setText(String.valueOf(photoList.get(position).numberOfPhotos)+" Photos");
        viewHolder.albumLabelName.setText(photoList.get(position).albumTitle);

        String str = photoList.get(position).getPublishDate();
        String[] splited = str.split("\\s+");

        viewHolder.albumDate.setText(splited[0]);
        viewHolder.albumTime.setText(splited[1]);

        return convertView;
    }

    static class ViewHolder {

      //  private ImageView albumImageView;
        private TextView albumLabelName, imagesCountLabel;
        private TextView albumDate, albumTime;
    }
}

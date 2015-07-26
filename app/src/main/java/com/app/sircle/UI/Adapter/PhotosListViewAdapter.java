package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.Model.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
            viewHolder.albumImageView = (ImageView) convertView.findViewById(R.id.photos_row_album_image);
            viewHolder.albumLabelName = (TextView) convertView.findViewById(R.id.photos_row_album_label_name);
            viewHolder.imagesCountLabel = (TextView) convertView.findViewById(R.id.photos_row_album_images_count_label);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext)
                .load(photoList.get(position).albumCoverImageURL)
                .into(viewHolder.albumImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

        viewHolder.imagesCountLabel.setText(String.valueOf(photoList.get(position).numberOfPhotos));
        viewHolder.albumLabelName.setText(photoList.get(position).albumTitle);

        return convertView;
    }

    static class ViewHolder {

        private ImageView albumImageView;
        private TextView albumLabelName, imagesCountLabel;
    }
}

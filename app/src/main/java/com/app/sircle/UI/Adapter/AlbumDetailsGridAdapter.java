package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.app.sircle.UI.Model.AlbumDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 27/07/15.
 */
public class AlbumDetailsGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private LayoutInflater inflater;

    public AlbumDetailsGridAdapter(Context mContext, List<AlbumDetails> albums) {
        this.mContext = mContext;
        this.albumDetailsList = albums;
        inflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return albumDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return albumDetailsList.get(position).getPhotoID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        // get screen dimensions
        //Bitmap image = decodeFile(_filePaths.get(position), 120, 120);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(120,
                120));
        //imageView.setImageBitmap(image);

        // image view click listener
        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            //Intent i = new Intent(mContext, FullScreenViewActivity.class);
            //i.putExtra("position", _postion);

        }

    }
}

package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.AlbumFullScreenActivity;
import com.app.sircle.UI.Model.AlbumDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 27/07/15.
 */
public class AlbumDetailsGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private LayoutInflater inflater;
    private View footerView;

    public AlbumDetailsGridAdapter(Context mContext, List<AlbumDetails> albums) {
        this.mContext = mContext;
        this.albumDetailsList = albums;
        inflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return albumDetailsList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return albumDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_view_albums,
                    parent, false);
            viewHolder.albumImageView = (ImageView) convertView.findViewById(R.id.grid_album_image_view);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == albumDetailsList.size()){
            footerView  = View.inflate(mContext, R.layout.list_view_padding_footer, null);
            return footerView;
        }

        // get screen dimensions
        Picasso.with(mContext)
                .load(albumDetailsList.get(position).getPhotoLargeURL())
                .into(viewHolder.albumImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        viewHolder.albumImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // image view click listener
        viewHolder.albumImageView.setOnClickListener(new OnImageClickListener(position));

        return convertView;
    }

    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "image clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(mContext, AlbumFullScreenActivity.class);
            i.putExtra("position", _postion);
            i.putExtra("url",albumDetailsList.get(_postion).getPhotoLargeURL());
            i.putExtra("caption",albumDetailsList.get(_postion).getPhotoCaption());
            mContext.startActivity(i);

        }

    }

    static class ViewHolder {

        private ImageView albumImageView;
    }
}

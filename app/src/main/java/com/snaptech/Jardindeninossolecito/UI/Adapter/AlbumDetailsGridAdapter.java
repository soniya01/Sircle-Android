package com.snaptech.Jardindeninossolecito.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.snaptech.Jardindeninossolecito.R;
import com.snaptech.Jardindeninossolecito.UI.Activity.AlbumFullScreenActivity;
import com.snaptech.Jardindeninossolecito.UI.Model.AlbumDetails;
import com.snaptech.Jardindeninossolecito.Utility.InternetCheck;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        return albumDetailsList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder ;
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
            footerView  = View.inflate(this.mContext, R.layout.list_view_padding_footer, null);
            return footerView;
        }

        // get screen dimensions

        if(albumDetailsList.size()!=0) {
            if (!albumDetailsList.get(position).getFilePath().equals("")) {

                //  loadImageInBackground(mContext, viewHolder.albumImageView, albumDetailsList.get(position).getFilePath());
                Picasso.with(this.mContext)
                        .load(albumDetailsList.get(position).getFilePath()).fit().centerCrop()
                        .into(viewHolder.albumImageView, new Callback() {
                            @Override
                            public void onSuccess() {

                                if (position == albumDetailsList.size() - 1) {
                                    // AlbumDetailsActivity.ringProgressDialog.dismiss();
                                }
                            }


                            @Override
                            public void onError() {
                                // AlbumDetailsActivity.ringProgressDialog.dismiss();
                            }
                        });
            }

        }
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
            // Toast.makeText(mContext, "image clicked", Toast.LENGTH_SHORT).show();
            if(InternetCheck.isNetworkConnected(mContext)){

                if(albumDetailsList.size()!=0) {
                    Intent i = new Intent(mContext, AlbumFullScreenActivity.class);
                    i.putExtra("position", _postion);
                    i.putExtra("url", albumDetailsList.get(_postion).getFilePath());
                    i.putExtra("caption", albumDetailsList.get(_postion).getFileName());
                    mContext.startActivity(i);
                }
                else{
                    Toast.makeText(mContext,"Compruebe internet",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(mContext,"Compruebe internet",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public static class ViewHolder {

        public ImageView albumImageView;
    }

    public void loadImageInBackground(Context context, final ImageView img, final String url) {
        Target target = new Target() {

            @Override
            public void onPrepareLoad(Drawable arg0) {
            }

            @Override
            public void onBitmapLoaded(Bitmap arg0, Picasso.LoadedFrom arg1) {
                img.setImageBitmap(arg0);
            }

            @Override
            public void onBitmapFailed(Drawable arg0) {
            }
        };

        Picasso.with(context)
                .load(url)
                .into(target);
    }
}
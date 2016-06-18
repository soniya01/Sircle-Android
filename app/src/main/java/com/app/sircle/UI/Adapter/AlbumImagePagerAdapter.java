package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.CustomView.TouchImageView;
import com.app.sircle.UI.Model.AlbumDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlbumImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private LayoutInflater layoutInflater;


    public AlbumImagePagerAdapter(Context context, List<AlbumDetails> albumDetailsList) {
        this.context = context;
        this.albumDetailsList = albumDetailsList;
        this.layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return albumDetailsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TouchImageView photoImageView;
        TextView titleLabel, countLabel;

        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = layoutInflater.inflate(R.layout.view_pager_album, container,
                false);

        photoImageView = (TouchImageView) viewLayout.findViewById(R.id.album_pager_image);
        titleLabel = (TextView)viewLayout.findViewById(R.id.album_image_title_label);
        countLabel = (TextView)viewLayout.findViewById(R.id.albums_image_no_label);

        titleLabel.setText(albumDetailsList.get(position).getFileName());

        countLabel.setText((position+1)+"/"+albumDetailsList.size());

        // get screen dimensions
        Picasso.with(context)
                .load(albumDetailsList.get(position).getFilePath()).fit().centerCrop()
                .into(photoImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

                ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}

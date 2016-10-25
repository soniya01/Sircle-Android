package com.snaptech.emissio.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.snaptech.emissio.DownLoader.ImageLoader;
import com.snaptech.emissio.R;
import com.snaptech.emissio.UI.Model.ImageData;

import java.util.List;

/**
 * Created by soniya on 10/08/15.
 */
public class CamerarollAdapter extends BaseAdapter{

    private Context context;
    private List<ImageData> imageData;
    private LayoutInflater layoutInflater;

    public CamerarollAdapter(Context context, List<ImageData> imageData){
        this.context = context;
        this.imageData = imageData;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public List<ImageData> getImageData() {
        return imageData;
    }

    public void setImageData(List<ImageData> imageData) {
        this.imageData = imageData;
    }

    @Override
    public int getCount() {
        return imageData.size();
    }

    @Override
    public Object getItem(int position) {
        return imageData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grid_row_camera_images, parent, false);
        }

        ImageView cameraImageView = (ImageView)convertView.findViewById(R.id.grid_image_view_picture);
        ImageData data = (ImageData) getItem(position);
//
       ImageLoader.fetchImage(context, data, cameraImageView);

        return convertView;
    }

}

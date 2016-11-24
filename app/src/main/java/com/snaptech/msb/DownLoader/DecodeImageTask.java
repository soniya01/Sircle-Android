package com.snaptech.msb.DownLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.snaptech.msb.UI.Model.ImageData;

import java.lang.ref.WeakReference;

/**
 * Created by vishnu on 26/05/15.
 */
public class DecodeImageTask extends AsyncTask<Void, Void, Bitmap> {

    private static final int THUMBNAIL_WIDTH = 80;
    private static final int THUMBNAIL_HEIGHT = 80;

    private Context context;
    private ImageData imageData;
    private final WeakReference<ImageView> imageViewWeakReference;

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public DecodeImageTask(Context context, ImageData imageData, ImageView imageView){
        this.context = context;
        this.imageData = imageData;
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return ImageManager.getInstance().decodeSampledBitmapFromImageDataWithSampleSizeEight(imageData, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled()){
            bitmap = null;
        }

        //check to see if the weak reference is still around. It will be null if it is garbage collected.
        if(imageViewWeakReference != null && bitmap != null){
            ImageView imageView = imageViewWeakReference.get();
            DecodeImageTask decodeImageTask = ImageLoader.getDecodeImageTask(imageView);
            if(this == decodeImageTask && imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}

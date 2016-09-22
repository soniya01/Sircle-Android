package com.snaptech.spanishdemo.DownLoader;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.snaptech.spanishdemo.UI.Model.ImageData;

import java.lang.ref.WeakReference;

/**
 * Created by soniya on 10/08/15.
 */
public class ImageLoader {

    /**
     * Starts an async task to fetch the image
     * @param context The current context
     * @param imageData  The wrapper for path and rotation of the image
     * @param imageView The imageview to pass to the async task
     */
    public static void fetchImage(Context context, ImageData imageData, ImageView imageView){
        if(cancelPotentialDownload(imageData, imageView)) {
            DecodeImageTask decodeImageTask = new DecodeImageTask(context, imageData, imageView);
            AsyncDrawable asyncDrawable = new AsyncDrawable(decodeImageTask);
            imageView.setImageDrawable(asyncDrawable);
            decodeImageTask.execute();
        }
    }

    /**
     * Stops the possible download in progress on this ImageView since a new one is about to start
     * @param imageData  The wrapper for path and rotation of the image
     * @param imageView The associated image view
     * @return true if the task is cancelled, false otherwise.
     */
    private static boolean cancelPotentialDownload(ImageData imageData, ImageView imageView){
        DecodeImageTask decodeImageTask = getDecodeImageTask(imageView);
        if(decodeImageTask != null){
            String imagePath = decodeImageTask.getImageData().getPath();
            if(imagePath == null || (!imagePath.equals(imageData.getPath()))){
                decodeImageTask.cancel(true);
            }else {
                // The same URL is already being downloaded.
                return false;
            }

        }
        return true;
    }


    //returns the task associated with the image view
    public static DecodeImageTask getDecodeImageTask(ImageView imageView){
        if(imageView != null){
            final Drawable drawable = imageView.getDrawable();
            if(drawable instanceof AsyncDrawable){
                AsyncDrawable asyncDrawable = (AsyncDrawable)drawable;
                return  asyncDrawable.getDecodeImageTask();
            }
        }
        return null;
    }


    static class AsyncDrawable extends ColorDrawable {

        private final WeakReference<DecodeImageTask> decodeImageTaskWeakReference;

        public AsyncDrawable( DecodeImageTask decodeImageTask){
            super(Color.GRAY);
            decodeImageTaskWeakReference = new WeakReference<DecodeImageTask>(decodeImageTask);
        }

        public DecodeImageTask getDecodeImageTask(){
            return decodeImageTaskWeakReference.get();
        }
    }

}

package com.snaptech.omeyocan.DownLoader;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;

import com.snaptech.omeyocan.UI.Model.ImageData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vishnu on 26/05/15.
 */
public class ImageManager {

    public static final int COMMON_FIRST_PIXEL_X_COORDINATE_IN_BITMAP = 0;
    public static final int COMMON_FIRST_PIXEL_Y_COORDINATE_IN_BITMAP = 0;

    private String[] projection = new String[]{
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.MIME_TYPE
    };

    public static final String CAMERA_IMAGE_BUCKET_NAME =
            Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Camera";

    public static final String CAMERA_IMAGE_BUCKET_ID =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    private static ImageManager instance;

    private ImageManager(){

    }

    public static ImageManager getInstance(){
        if(instance == null){
            instance = new ImageManager();
        }
        return instance;
    }

    public Bitmap getLastUsedImage(Context context){
        Bitmap bitmap = null;
        final Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        // Put it in the image view
        if (cursor.moveToFirst()) {
            String imageLocation = cursor.getString(1);
            File imageFile = new File(imageLocation);
            if (imageFile.exists()) {
                bitmap = BitmapFactory.decodeFile(imageLocation);
            }
            cursor.close();
        }
        return bitmap;
    }

    public List<ImageData> getCameraImagePaths(Context context) {
        final String[] projection = { MediaStore.Images.Media.DATA, "orientation" };
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
        List<ImageData> result = new ArrayList<ImageData>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                ImageData imageData = new ImageData();
                String data = cursor.getString(dataColumn);
                int rotation = cursor.getInt(1);
                imageData.setPath(data);
                imageData.setRotation(rotation);
                result.add(imageData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap =  BitmapFactory.decodeFile(path, options);

        return bitmap;
    }

    public Bitmap decodeSampledBitmapFromImageData(ImageData imageData, int reqWidth, int reqHeight) {

        Bitmap b = BitmapFactory.decodeFile(imageData.getPath());
        int height = b.getHeight();
        int width = b.getWidth();
        b = null;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageData.getPath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap =  BitmapFactory.decodeFile(imageData.getPath(), options);

        bitmap = rotateImageIfRequired(bitmap, imageData.getRotation());

        return bitmap;
    }

    public Bitmap decodeSampledBitmapFromImageDataWithSampleSizeEight(ImageData imageData, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageData.getPath(), options);

        // Calculate inSampleSize
        options.inSampleSize = 8;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap =  BitmapFactory.decodeFile(imageData.getPath(), options);

        bitmap = rotateImageIfRequired(bitmap, imageData.getRotation());

        return bitmap;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, int rotation) {


        if(rotation!=0){
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        }else{
            return img;
        }
    }

    public Bitmap decodeSampledBitmapFromByteArray(byte[] imageData, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        bitmap = null;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
    }

    public Bitmap rotateBitmap(int angle, Bitmap bitmap){
        Matrix mat = new Matrix();
        mat.postRotate(angle);  // angle is the desired angle you wish to rotate
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
        return bitmap;
    }

    public Bitmap flip(Bitmap d)
    {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap src = d;
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }


    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public void createBitmapFromView(View view) {

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        view.getWindowVisibleDisplayFrame(frame);
        int width = view.getWidth();
        int height = view.getHeight();


//        EditImageActivity.savedBitmap = Bitmap.createBitmap(bmp, COMMON_FIRST_PIXEL_X_COORDINATE_IN_BITMAP,
//                COMMON_FIRST_PIXEL_Y_COORDINATE_IN_BITMAP, width,
//                height);
        view.setDrawingCacheEnabled(false);
        bmp.recycle();
        bmp = null;
    }
}

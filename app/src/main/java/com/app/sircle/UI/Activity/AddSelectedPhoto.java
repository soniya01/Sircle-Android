package com.app.sircle.UI.Activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.sircle.DownLoader.ImageManager;
import com.app.sircle.Manager.PhotoManager;
import com.app.sircle.R;
import com.app.sircle.UI.Fragment.CameraFragment;
import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.UI.Model.ImageData;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.PhotoUploadResponse;

import java.io.File;
import java.util.HashMap;

import retrofit.mime.TypedFile;

public class AddSelectedPhoto extends ActionBarActivity {

    private ImageView newPhoto;
    private EditText desc;
    private Button addPhotoButton;
    private boolean backCameraShown;
    private int rotationAngle = 90;
    private int rotationAngle_front_camera = 270;
    private String albumId;
    private ImageData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_selected_photo);

        if (getIntent().getExtras() != null){
            albumId = getIntent().getStringExtra("albumId");
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newPhoto  = (ImageView) findViewById(R.id.activity_add_new_photo_image);
        desc = (EditText)findViewById(R.id.activity_add_new_photo_desc);
        addPhotoButton = (Button)findViewById(R.id.activity_add_new_photo);

        displayClickedImage();

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: post the photos and albums created to the server
                String descText = desc.getText().toString();
                BaseActivity.jumpToFragment = true;

                HashMap params = new HashMap();
                params.put("alb_id", String.valueOf(AlbumDetailsActivity.albumId));
                //params.put("alb_id",AlbumDetailsActivity.albumId);
                params.put("caption", descText);

                String photoName = data.getPath();
                File photo = new File(photoName );
                TypedFile typedImage = new TypedFile("image/jpeg", photo);

                PhotoManager.getSharedInstance().uploadImage(params, typedImage, new PhotoManager.PhotoManagerListener() {
                    @Override
                    public void onCompletion(PhotoUploadResponse response, AppError error) {
                        if (response != null) {
                            if (response.getStatus() == 200) {
                                Toast.makeText(AddSelectedPhoto.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddSelectedPhoto.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void displayClickedImage() {
        ViewTreeObserver viewTreeObserver = this.newPhoto.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                Bundle extras = getIntent().getExtras();
                backCameraShown = extras.getBoolean(CameraFragment.INTENT_EXTRA_BACK_CAMERA_SHOWN);

                if (extras != null && extras.containsKey("data")) {
                    data = (ImageData) extras.get("data");
                    new DecodeBitmapTask(data).execute();
                } else {
                    new DecodeBitmapTask(null).execute();
                }

                ViewTreeObserver viewTreeObserver = AddSelectedPhoto.this.newPhoto
                        .getViewTreeObserver();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                } else {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_selected_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DecodeBitmapTask extends AsyncTask<Void, Void, Bitmap> {

        private ImageData data;

        public DecodeBitmapTask(ImageData data) {
            this.data = data;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            ImageManager instance = ImageManager.getInstance();
            Bitmap bitmap = null;
            if (data == null) {
                    bitmap = instance.decodeSampledBitmapFromByteArray(CameraFragment.imageData, 400, 400);


                if (backCameraShown) {
                    bitmap = instance.rotateBitmap(rotationAngle, bitmap);
                } else {
                    bitmap = instance.rotateBitmap(rotationAngle_front_camera, bitmap);
                    bitmap = instance.flip(bitmap);
                }
                bitmap = resizeBitMapForDisplay(bitmap);
            } else {
                bitmap = instance.decodeSampledBitmapFromImageData(data, 400, 400);
                try {
                    bitmap = resizeGalleryImage(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return bitmap;
        }

        private Bitmap resizeGalleryImage(Bitmap bitmap) {

            // ImageView dimensions
            int imageViewWidth = newPhoto.getWidth();
            int imageViewHeight = newPhoto.getHeight();

            // Get height and width of bitmap
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();

            float bitmapRatio = (float)bitmapHeight / (float)bitmapWidth;
            float imageViewRatio = (float)imageViewHeight / (float)imageViewWidth;

            if (bitmapHeight > bitmapWidth) {
                // Portrait
                bitmap = Bitmap.createScaledBitmap(bitmap, imageViewWidth, (int)((float)bitmapHeight * ((float)imageViewWidth / (float)bitmapWidth)), true);
                bitmapWidth = bitmap.getWidth();
                bitmapHeight = bitmap.getHeight();

                if (bitmapHeight < imageViewHeight) {

                    // Scale to match the height
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int)((float)bitmapWidth * ((float)imageViewHeight / (float)bitmapHeight)), imageViewHeight, true);
                    bitmapWidth = bitmap.getWidth();

                    // Crop width
                    bitmap = Bitmap.createBitmap(bitmap, (int) ((float) (bitmapWidth - imageViewWidth) / 2.0f), 0, imageViewWidth, imageViewHeight);

                } else {
                    // Crop height
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, imageViewWidth, imageViewHeight);
                }
            } else {
                // Landscape
                bitmap = Bitmap.createScaledBitmap(bitmap, (int)((float)bitmapWidth * ((float)imageViewHeight / (float)bitmapHeight)), imageViewHeight, true);
                bitmapWidth = bitmap.getWidth();
                bitmapHeight = bitmap.getHeight();


                if (bitmapWidth < imageViewWidth) {

                    // Scale to match the width
                    bitmap = Bitmap.createScaledBitmap(bitmap, imageViewWidth, (int)((float)bitmapHeight * ((float)imageViewWidth / (float)bitmapWidth)), true);
                    bitmapHeight = bitmap.getHeight();

                    // Crop Height
                    bitmap = Bitmap.createBitmap(bitmap, 0, (int)((float)(bitmapHeight - imageViewHeight) / 2.0f), imageViewWidth, imageViewHeight);

                } else {
                    // Crop Width
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, imageViewWidth, imageViewHeight);
                }
            }

            return bitmap;
        }

        private Bitmap resizeBitMapForDisplay (Bitmap bitmap) {

            // Screen Dimensions
            DisplayMetrics displayMetrics = AddSelectedPhoto.this.getBaseContext().getResources().getDisplayMetrics();
            int deviceWidth = displayMetrics.widthPixels;
            int deviceHeight = displayMetrics.heightPixels;

            // ImageView dimensions
            int imageViewWidth = newPhoto.getWidth();
            int imageViewHeight = newPhoto.getHeight();

            // Get height and width of bitmap
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();

//                //bitmap = Bitmap.createScaledBitmap(bitmap, Constant.BREW_CAM_PREVIEW_WIDTH, Constant.BREW_CAM_PREVIEW_HEIGHT, true);
//
//                int x = (int)((bitmap.getWidth() - imageViewWidth) / 2.0f);
//                int y = (int)((bitmap.getHeight() - imageViewHeight) / 2.0f);
//                int height = (int)getResources().getDimension(R.dimen.activity_title_bar_height);
//                bitmap = Bitmap.createBitmap(bitmap, x, y - height / 2, imageViewWidth, imageViewHeight);
//
//            } else {
//                if (BREW_CAM_SHOULD_RESIZE == true) {
//
                   // bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                    //bitmap = Bitmap.createBitmap(bitmap, 0, 0, imageViewWidth, imageViewHeight);
////
//                } else {
                    // Scale to preview size

            bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmapWidth * ((float) 900 / (float) bitmapHeight)),600, true);
//            bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((float) bitmapWidth * ((float) BREW_CAM_PREVIEW_HEIGHT / (float) bitmapHeight)),
//                    BREW_CAM_PREVIEW_HEIGHT, true);

            bitmap = Bitmap.createBitmap(bitmap, (int) (((float) bitmap.getWidth() - (float) imageViewWidth) / 2.0f), 0, 600, 600);
               // }
           // }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            newPhoto.setImageBitmap(bitmap);
            bitmap = null;
        }

    }
}

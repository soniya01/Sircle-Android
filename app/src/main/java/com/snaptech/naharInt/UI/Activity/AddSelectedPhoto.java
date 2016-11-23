package com.snaptech.naharInt.UI.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snaptech.naharInt.DownLoader.ImageManager;
import com.snaptech.naharInt.Manager.PhotoManager;
import com.snaptech.naharInt.R;
import com.snaptech.naharInt.UI.Fragment.CameraFragmentUI;
import com.snaptech.naharInt.UI.Model.ImageData;
import com.snaptech.naharInt.Utility.AppError;
import com.snaptech.naharInt.WebService.PhotoUploadResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import retrofit.mime.TypedFile;

public class AddSelectedPhoto extends AppCompatActivity {

    private ImageView newPhoto;
    private EditText desc;
    private Button addPhotoButton;
    private boolean backCameraShown;
    private int rotationAngle = 90;
    private int rotationAngle_front_camera = 270;
    private String albumId;
    private ImageData data;
    private ProgressDialog ringProgressDialog;

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

       // Bundle extras = getIntent().getExtras();

       // Bitmap bitmap = (Bitmap)extras.get("data");

     //  newPhoto.setImageBitmap(Constants.myBitmap);



       displayClickedImage();

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: post the photos and albums created to the server
//                final ProgressBar progressBar = new ProgressBar(AddSelectedPhoto.this,null,android.R.attr.progressBarStyleLarge);
//                progressBar.setIndeterminate(true);
//                progressBar.setVisibility(View.VISIBLE);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
//                //layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//                layoutParams.gravity = Gravity.CENTER;
//                LinearLayout layout = (LinearLayout)findViewById(R.id.container);
//                (layout).addView(progressBar, layoutParams);

                String descText = desc.getText().toString();

                if(descText.length()<=2){

                    Toast.makeText(AddSelectedPhoto.this,"Caption should be of atleast 3 characters",Toast.LENGTH_SHORT).show();
                    desc.requestFocus();
                }else {
                    ringProgressDialog = ProgressDialog.show(AddSelectedPhoto.this, "", "Uploading image..", true);


//                BaseActivity.jumpToFragment = true;

                    HashMap params = new HashMap();

                    params.put("album_id", String.valueOf(albumId));
                    //params.put("alb_id",AlbumDetailsActivity.albumId);
                    params.put("caption_name", descText);

                    String photoName = data.getPath();
                    // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                    File photo = new File(photoName);
                    TypedFile typedImage = new TypedFile("image/jpeg", photo);
                    //params.put("file",typedImage);
                    PhotoManager.getSharedInstance().uploadImage(params, typedImage, new PhotoManager.PhotoManagerListener() {
                        @Override
                        public void onCompletion(PhotoUploadResponse response, AppError error) {
//                        progressBar.setVisibility(View.GONE);
                            ringProgressDialog.dismiss();
                            if (response != null) {
                                if (response.getStatus() == 200) {
                                    Toast.makeText(AddSelectedPhoto.this, "Photo Saved Successfully", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    //  Toast.makeText(AddSelectedPhoto.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
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
                backCameraShown = extras.getBoolean(CameraFragmentUI.INTENT_EXTRA_BACK_CAMERA_SHOWN);

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view



        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
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
                    bitmap = instance.decodeSampledBitmapFromByteArray(CameraFragmentUI.imageData, 400, 400);


                if (backCameraShown) {
                    bitmap = instance.rotateBitmap(rotationAngle, bitmap);
                } else {
                    bitmap = instance.rotateBitmap(rotationAngle_front_camera, bitmap);
                    bitmap = instance.flip(bitmap);
                }
                bitmap = resizeBitMapForDisplay(bitmap);
            } else {
                bitmap = instance.decodeSampledBitmapFromImageData(data, 400, 400);
//                try {
//                    bitmap = resizeGalleryImage(bitmap);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
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

    private File savebitmap(String filename) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            // make a new bitmap from your file
            Bitmap bitmap = BitmapFactory.decodeFile(file.getName());

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }

    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("File Error", "Error writing bitmap", e);
        }

        return imageFile;
    }
}

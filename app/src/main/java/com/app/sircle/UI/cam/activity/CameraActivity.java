package com.app.sircle.UI.cam.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddSelectedPhoto;
import com.app.sircle.UI.cam.Config;
import com.app.sircle.UI.cam.fragment.CameraFragment;
import com.app.sircle.UI.cam.listener.CameraFragmentListener;
import com.app.sircle.Utility.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Activity displaying the camera preview.
 */


public class CameraActivity extends FragmentActivity implements CameraFragmentListener {

    private Context mContext, aContext;
    private CameraFragment fragment;

    private int height;
    private int width;
    private int compress;
    private Config.CameraFace face;
    private CompressFormat format;
    private int PICK_IMAGE_REQUEST = 1;

    private ImageButton btnCapture, btnSwap, btnFlash,btnClose,btnGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initVals();
        initViews();
    }


    @Override
    protected void onResume() {
        super.onResume();
        btnCapture.setEnabled(true);
    }

    /**
     * On fragment notifying about a non-recoverable problem with the camera.
     */
    @Override
    public void onCameraError() {
        Toast.makeText(aContext,"Camera Error", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * After taking the picture.
     */
    @Override
    public void onPictureTaken(Bitmap actualBitmap)
    {
        int length = actualBitmap.getWidth() <= actualBitmap.getHeight() ? actualBitmap.getWidth() : actualBitmap.getHeight();
//        Bitmap bitmap = Bitmap.createScaledBitmap(actualBitmap, length, length, true);
        Bitmap bitmap = Bitmap.createBitmap(actualBitmap, 0, 0, length, length);


        Constants.myBitmap = bitmap;

        Intent intent = new Intent(CameraActivity.this, AddSelectedPhoto.class);


       // intent.putExtra("data", bitmap);
       // intent.putExtra(INTENT_EXTRA_BACK_CAMERA_SHOWN, backCameraShown);
        startActivity(intent);


//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                getString(R.string.app_name));
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                showSavingPictureErrorToast();
//                return;
//            }
//        }
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
//        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "STYFI_" + timeStamp +
//                (format == CompressFormat.JPEG ? ".jpg" : ".png"));
//
//        try {
//            FileOutputStream stream = new FileOutputStream(mediaFile);
//            bitmap.compress(format, compress, stream);
//        } catch (IOException e) {
//            showSavingPictureErrorToast();
//            e.printStackTrace();
//            return;
//        }
//        MediaScannerConnection.scanFile(mContext, new String[]{mediaFile.toString()}, new String[]{"image/jpeg"}, null);
//        Intent intent = new Intent();
//        intent.setData(Uri.fromFile(mediaFile));
//        intent.putExtra(Config.KEY_MEDIAFILE, mediaFile);
//        setResult(RESULT_OK, intent);
//        finish();
    }

    private void showSavingPictureErrorToast() {
        Toast.makeText(this, "Error Saving Picture", Toast.LENGTH_SHORT).show();
    }

    private void initVals() {
        mContext = CameraActivity.this;
        aContext = mContext.getApplicationContext();
        getBundleValues();
    }

    private void initViews() {
        btnCapture = (ImageButton) findViewById(R.id.btn_capture);
        btnSwap = (ImageButton) findViewById(R.id.btn_switch);
        btnFlash = (ImageButton) findViewById(R.id.btn_flash);
        btnClose = (ImageButton) findViewById(R.id.closeCamera);
        btnGallery = (ImageButton) findViewById(R.id.gallery);

        fragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.camera_fragment);
        fragment.setCam(height, width, compress, face);
//        setFlashVisibility();
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapCamera();
            }
        });
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlash();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takePicture();
                finish();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // takePicture();
                selectImageFromGallery();
            }
        });
    }

    private void getBundleValues() {
        Bundle bundle = getIntent().getExtras();
        if (null == bundle || Bundle.EMPTY == bundle) {
            height = width = 1080;
            compress = 100;
            format = CompressFormat.JPEG;
            face = Config.CameraFace.BACK;
        } else {
            height = bundle.getInt(Config.KEY_HEIGHT, 1080);
            width = bundle.getInt(Config.KEY_WIDTH, 1080);
            compress = bundle.getInt(Config.KEY_COMPRESSION, 40);
            if (bundle.containsKey(Config.KEY_IMG_FORMAT))
                format = (CompressFormat) bundle.get(Config.KEY_IMG_FORMAT);
            else
                format = CompressFormat.JPEG;

            if (bundle.containsKey(Config.KEY_CAM_FACE))
                face = (Config.CameraFace) bundle.get(Config.KEY_CAM_FACE);
            else
                face = Config.CameraFace.BACK;
        }
    }

    /**
     * called when click on change camera button(front/back).
     */
    private void swapCamera() {
        btnSwap.setEnabled(false);
        fragment.switchCam();
        btnSwap.setEnabled(true);
    }

    /**
     * called when click on capture button.
     */
    private void takePicture() {
        btnCapture.setEnabled(false);
        fragment.takePicture();
    }

//    private void setFlashVisibility() {
//        btnFlash.setVisibility(fragment.hasFlash() ? View.VISIBLE : View.GONE);
//    }

    private void toggleFlash() {
        fragment.toggleFlash();
    }

    private void selectImageFromGallery()
    {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Constants.myBitmap = bitmap;

                Intent intent = new Intent(CameraActivity.this, AddSelectedPhoto.class);


                // intent.putExtra("data", bitmap);
                // intent.putExtra(INTENT_EXTRA_BACK_CAMERA_SHOWN, backCameraShown);
                startActivity(intent);

                // Log.d(TAG, String.valueOf(bitmap));

//                ImageView imageView = (ImageView) findViewById(R.id.imageView);
//                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

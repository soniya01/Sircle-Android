package com.snaptech.quaderia.UI.Fragment;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.snaptech.quaderia.R;
import com.snaptech.quaderia.UI.Activity.AddSelectedPhoto;
import com.snaptech.quaderia.UI.CustomView.CameraPreview;
import com.snaptech.quaderia.UI.Model.ImageData;
import com.snaptech.quaderia.UI.cam.listener.CameraFragmentListener;
import com.snaptech.quaderia.UI.cam.listener.CameraOrientationListener;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CameraFragmentUI extends Fragment implements View.OnClickListener {


    public static final String INTENT_EXTRA_BACK_CAMERA_SHOWN = "com.propaganda3.boulevardia.backCameraShown";
    private static final String TOAST_NO_FLASH = "Device does not support flash";
    private static final String TOAST_NO_FRONT_CAMERA = "Front camera does not exist";
    private static final String PICTURE = "picture";
    private String albumId;
    //to store the image data of the clicked image
    public static byte[] imageData;
  //  private ImageButton imageButtonSwitchCamera;
  //  private ImageButton imageButtonClickPhoto;
  //  private CheckBox checkBoxToggleFlash;
    private FrameLayout cameraPreviewLayout;
    private Camera camera;
    private CameraPreview cameraPreview;
    private boolean backCameraShown;

    private int cameraId;
    private int displayOrientation;
    private int layoutOrientation;
    private CameraOrientationListener orientationListener;
 //   private CameraFragmentListener listener;

    //callback for handling picture taken by camera
    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            enableButtons(true);
         /*   imageData = data;

            final BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
            sizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, sizeOptions);
            //Log.d(TAG, "Bitmap is " + sizeOptions.outWidth + "x"+ sizeOptions.outHeight);

            // Now use the size to determine the ratio you want to shrink it
            final float widthSampling = sizeOptions.outWidth / 300;
            sizeOptions.inJustDecodeBounds = false;

            // Note this drops the fractional portion, making it smaller
            sizeOptions.inSampleSize = (int) widthSampling;
            //Log.d(TAG, "Sample size = " + sizeOptions.inSampleSize);

    // Scale by the smallest amount so that image is at least the desired
    // size in each direction
            //final Bitmap result = BitmapFactory.decodeByteArray(data, 0, data.length,sizeOptions);

            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, sizeOptions);
            //imageDataList  = ImageManager.getInstance().getCameraImagePaths(getActivity());*/




            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            int rotation = 0;
            if (cameraId == 1) {
                rotation = (displayOrientation + orientationListener.getRememberedOrientation() + layoutOrientation) % (-360);

                if (rotation == 90) {
                    rotation = -90;
                }
                if (rotation == 270) {
                    rotation = 90;
                }

                if (rotation == 0)
                {
                    rotation = 90;
                }

            } else if (cameraId == 0) {
                rotation = (displayOrientation + orientationListener.getRememberedOrientation() + layoutOrientation) % 360;
            }

            if (rotation != 0) {
                Bitmap oldBitmap = bitmap;
                Matrix matrix = new Matrix();
                matrix.postRotate(rotation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
               // Constants.myBitmap = bitmap;
                oldBitmap.recycle();
            }


            String path = getRealPathFromURI(getImageUri(getActivity().getApplicationContext(), bitmap));
            ImageData imageData1 = new ImageData();
            imageData1.setPath(path);

            Intent intent = new Intent(getActivity(), AddSelectedPhoto.class);
            intent.putExtra("data", imageData1);
            intent.putExtra("albumId", albumId);
            //intent.putExtra(INTENT_EXTRA_BACK_CAMERA_SHOWN, backCameraShown);
            startActivity(intent);

            getActivity().finish();


        }
    };
    private boolean flashOn;
    private List<ImageData> imageDataList;

    /**
     * activity getting attached.
     */
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (!(activity instanceof CameraFragmentListener))
            throw new IllegalArgumentException("Activity has to implement CameraFragmentListener interface");

       // listener = (CameraFragmentListener) activity;
        orientationListener = new CameraOrientationListener(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_camera, container, false);
        setupViews(viewFragment);
        return viewFragment;
    }

    // Linking the id to their views
    private void setupViews(View view) {

        cameraPreviewLayout = (FrameLayout) view.findViewById(R.id.camera_preview);
//        imageButtonSwitchCamera = (ImageButton) view.findViewById(R.id.cam_image_button_switch_camera);
//        checkBoxToggleFlash = (CheckBox) view.findViewById(R.id.cam_checkbox_toggle_flash);
//        imageButtonClickPhoto = (ImageButton) view.findViewById(R.id.cam_image_button_snap);

//        imageButtonSwitchCamera.setOnClickListener(this);
//        imageButtonClickPhoto.setOnClickListener(this);
//
//        checkBoxToggleFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                toggleFlash(isChecked);
//            }
//        });

    }

    /**
     * Used to enable or disable the buttons
     *
     * @param flag - send true to enable button and false to disable buttons.
     */
    private void enableButtons(boolean flag) {
//        imageButtonSwitchCamera.setEnabled(flag);
//        imageButtonClickPhoto.setEnabled(flag);
//        checkBoxToggleFlash.setEnabled(flag);
    }

    private void attachCameraToView(boolean isBackCameraShown) {
        cameraPreview = new CameraPreview(getActivity(), camera, isBackCameraShown);
        cameraPreviewLayout.addView(cameraPreview);
        determineDisplayOrientation();
    }

    private Camera getCameraInstance(int cameraID) {
        cameraId = cameraID;
        Camera c = null;
        try {
            c = Camera.open(cameraID);
           // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable

    }

    private void showBackCamera() {
        releaseCamera();
        camera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);

        cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

        determineDisplayOrientation();

        if (camera != null) {
            if (cameraPreview != null) {
                removePreview();
            }
            toggleFlash(true);//turnOnFlash();
            backCameraShown = true;
            attachCameraToView(backCameraShown);
        }
    }

    private void showFrontCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();

        if (numberOfCameras > 1) {
            releaseCamera();
            camera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_FRONT);
            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            determineDisplayOrientation();
            backCameraShown = false;
            removePreview();
            attachCameraToView(backCameraShown);
           // toggleFlashButtonVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(getActivity(), TOAST_NO_FRONT_CAMERA, Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleFlashButtonVisibility(int visibility) {
        //checkBoxToggleFlash.setVisibility(visibility);
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release(); // release the camera for other applications
            camera = null;
        }

        if (cameraPreview != null) {
            cameraPreview.destroyDrawingCache();
            cameraPreview.setCamera(null);
        }
    }

    private void removePreview() {
        cameraPreviewLayout.removeView(cameraPreview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.cam_image_button_switch_camera:
//                enableButtons(false);
//                if (backCameraShown) {
//                    showFrontCamera();
//                } else {
//                    showBackCamera();
//                    checkBoxToggleFlash.setVisibility(View.VISIBLE);
//                }
//                enableButtons(true);
//                break;
//            case R.id.cam_image_button_snap:
//                enableButtons(false);
//                camera.takePicture(null, null, pictureCallback);
//                break;
            default:

        }
    }

    public void takePicture(String albumId)
    {
        this.albumId = albumId;
        camera.takePicture(null, null, pictureCallback);
    }

    public  void switchCam()
    {
        if (backCameraShown) {
                    showFrontCamera();
                } else {
                    showBackCamera();

                }
    }

    public void addImageToGallery(final String filePath) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        inImage.recycle();
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public void toggleFlash(boolean value) {

        if (isFlashSupported()) {
            Camera.Parameters parameters = camera.getParameters();

            parameters.setFlashMode(value ? Camera.Parameters.FLASH_MODE_ON : Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);

            if (value) {
                camera.startPreview();
            }
        } else {
            Toast.makeText(getActivity(), "no flash", Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleFlash() {
        if (hasFlash()) {
            // Already checked that camera is not null in hasFlash() so bypassing null check for camera
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON == parameters.getFlashMode() ?
                    Camera.Parameters.FLASH_MODE_OFF : Camera.Parameters.FLASH_MODE_ON);
            camera.setParameters(parameters);
        }
    }

    public boolean hasFlash() {
        if (camera == null) {
            return false;
        }

        Camera.Parameters parameters = camera.getParameters();

        if (parameters.getFlashMode() == null) {
            return false;
        }

        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        return !(supportedFlashModes == null || supportedFlashModes.isEmpty()
                || supportedFlashModes.size() == 1 && supportedFlashModes.get(0).equals(Camera.Parameters.FLASH_MODE_OFF));
    }

    private boolean isFlashSupported() {
        // if device support camera flash?
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageData = null;
//        if (BaseActivity.jumpToFragment) {
//            getActivity().finish();
//        }

        toggleFlashButtonVisibility(View.VISIBLE);
        showBackCamera();
        enableButtons(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * Determine the current display orientation and rotate the camera preview
     * accordingly.
     */
    public void determineDisplayOrientation() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);

        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;

            case Surface.ROTATION_90:
                degrees = 90;
                break;

            case Surface.ROTATION_180:
                degrees = 180;
                break;

            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int displayOrientation;

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayOrientation = (cameraInfo.orientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
        }

        this.displayOrientation = displayOrientation;
        this.layoutOrientation = degrees;

        camera.setDisplayOrientation(displayOrientation);
    }



}

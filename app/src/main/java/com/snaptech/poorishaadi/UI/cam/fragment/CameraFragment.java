package com.snaptech.poorishaadi.UI.cam.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;


import com.snaptech.poorishaadi.UI.cam.Config;
import com.snaptech.poorishaadi.UI.cam.listener.CameraFragmentListener;
import com.snaptech.poorishaadi.UI.cam.listener.CameraOrientationListener;
import com.snaptech.poorishaadi.UI.cam.view.CameraPreview;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Fragment for displaying the camera preview.
 */
public class CameraFragment extends Fragment implements SurfaceHolder.Callback, Camera.PictureCallback {
    public static final String TAG = "CameraFragment";

    private static final int PICTURE_SIZE_MAX_WIDTH = 1280;
    private static final int PREVIEW_SIZE_MAX_WIDTH = 640;

    private int cameraId;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private CameraFragmentListener listener;
    private int displayOrientation;
    private int layoutOrientation;
    private int height, width, compression;

    private CameraOrientationListener orientationListener;

    /**
     * activity getting attached.
     */
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (!(activity instanceof CameraFragmentListener))
            throw new IllegalArgumentException("Activity has to implement CameraFragmentListener interface");

        listener = (CameraFragmentListener) activity;
        orientationListener = new CameraOrientationListener(activity);
    }

    /**
     * creating view for fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CameraPreview previewView = new CameraPreview(getActivity());
        previewView.getHolder().addCallback(this);
        return previewView;
    }

    /**
     * On fragment getting paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        orientationListener.disable();
        stopCameraPreview();
    }

    /**
     * A picture has been taken.
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
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
        } else if (cameraId == 0) {
            rotation = (displayOrientation + orientationListener.getRememberedOrientation() + layoutOrientation) % 360;
        }

        if (rotation != 0) {
            Bitmap oldBitmap = bitmap;
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            oldBitmap.recycle();
        }

//        String path = getRealPathFromURI(getImageUri(getActivity().getApplicationContext(), bitmap));
//        ImageData imageData1 = new ImageData();
//        imageData1.setPath(path);

        listener.onPictureTaken(bitmap);
    }

    /**
     * On camera preview surface created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        orientationListener.enable();

        try {
            camera = Camera.open(cameraId);

        } catch (Exception e) {
            e.printStackTrace();
            listener.onCameraError();
            return;
        }
        startCameraPreview();
    }

    /**
     * On camera preview surface changed.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * On camera preview surface getting destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void setCam(int height, int width, int compression, Config.CameraFace face) {

        cameraId = face.getValue();
        this.height = height;
        this.width = width;
        this.compression = compression;
    }

    /**
     * called from camera activity for displaying front/back camera in fragment.
     */
    public void switchCam() {
        orientationListener.disable();
        stopCameraPreview();

        if (cameraId == CameraInfo.CAMERA_FACING_BACK) {
            cameraId = CameraInfo.CAMERA_FACING_FRONT;
        } else if (cameraId == CameraInfo.CAMERA_FACING_FRONT) {
            cameraId = CameraInfo.CAMERA_FACING_BACK;
        }
        orientationListener.enable();
        camera = Camera.open(cameraId);
        determineDisplayOrientation();
        setupCamera();
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onCameraError();
        }
    }

    /**
     * Take a picture and notify the listener once the picture is taken.
     */
    public void takePicture() {
        orientationListener.rememberOrientation();
        camera.takePicture(null, null, this);
    }

    /**
     * Start the camera preview.
     */
    private synchronized void startCameraPreview() {
        determineDisplayOrientation();
        setupCamera();
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onCameraError();
        }
    }

    /**
     * Stop the camera preview.
     */
    private synchronized void stopCameraPreview() {
        try {
            camera.stopPreview();
            camera.release();
        } catch (Exception exception) {
            Log.i(TAG, "Exception during stopping camera preview");
        }
    }

    /**
     * Determine the current display orientation and rotate the camera preview
     * accordingly.
     */
    public void determineDisplayOrientation() {
        CameraInfo cameraInfo = new CameraInfo();
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

        if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
            displayOrientation = (cameraInfo.orientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
        }

        this.displayOrientation = displayOrientation;
        this.layoutOrientation = degrees;

        camera.setDisplayOrientation(displayOrientation);
    }

    /**
     * Setup the camera parameters.
     */
    private void setupCamera() {
        Camera.Parameters parameters = camera.getParameters();
        List<Size> previewSizes = parameters.getSupportedPreviewSizes();
        Size size = getOptimalPreviewSize(previewSizes, width, height);

        width = size.width;
        height = size.height;

        parameters.setPreviewSize(size.width, size.height);
        parameters.setPictureSize(size.width, size.height);

        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
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

    public void toggleFlash() {
        if (hasFlash()) {
            // Already checked that camera is not null in hasFlash() so bypassing null check for camera
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON == parameters.getFlashMode() ?
                    Camera.Parameters.FLASH_MODE_OFF : Camera.Parameters.FLASH_MODE_ON);
            camera.setParameters(parameters);
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        inImage.recycle();
        return Uri.parse(path);
    }
}

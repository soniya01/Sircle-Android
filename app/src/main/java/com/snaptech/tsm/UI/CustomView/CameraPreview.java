package com.snaptech.tsm.UI.CustomView;

import android.content.Context;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

/**
 * Created by soniya on 10/08/15.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    List<Camera.Size> mSupportedPreviewSizes;
    Camera.Size mPreviewSize;
    private boolean backCameraShown;

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public CameraPreview(Context context, Camera camera, boolean backCameraShown) {
        super(context);
        this.backCameraShown = backCameraShown;
        this.camera = camera;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            Log.d("Surface Created", "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (surfaceHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
            e.printStackTrace();
        }

        try {
            // set preview size and make any resize, rotate or
            // reformatting changes here
            Camera.Parameters params = camera.getParameters();
            List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();
            Camera.Size size = getOptimalPreviewSize(previewSizes, width, height);
            params.setPreviewSize(size.width, size.height);
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);

            mPreviewSize = size;
            mSupportedPreviewSizes = previewSizes;

            camera.setDisplayOrientation(90);

            // start preview with new settings

            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

        } catch (Exception e){
            Log.d("Surface changed", "Error starting camera preview: " + e.getMessage());
        }



    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio=(double)h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width/ size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        try {
            mSupportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
            if (mSupportedPreviewSizes != null) {
                    mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        float ratio = 0f;

        if(mPreviewSize != null) {
            if (mPreviewSize.height >= mPreviewSize.width) {
                ratio = (float) mPreviewSize.height / (float) mPreviewSize.width;
            } else {
                ratio = (float) mPreviewSize.width / (float) mPreviewSize.height;
            }
        }

        int newWidth = (int)width;
        int newHeight = (int)(width * ratio);

        if(mPreviewSize != null) {
            //EditImageActivity.BREW_CAM_PREVIEW_WIDTH = mPreviewSize.width;
            //EditImageActivity.BREW_CAM_PREVIEW_HEIGHT = mPreviewSize.height;
        }
        setMeasuredDimension(newWidth , newHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            int imageHeight = ((View)this.getParent()).getHeight();
            int imageWidth = ((View)this.getParent()).getWidth();


//                if (bottom <= imageHeight) {
//                    EditImageActivity.BREW_CAM_SHOULD_RESIZE = true;
//                    int newImageWidth = (int) ((float) right * ((float) imageHeight / (float) bottom));
//
//                    (this).layout(0, 0, newImageWidth, imageHeight);
//                    EditImageActivity.BREW_CAM_PREVIEW_WIDTH = newImageWidth;
//                    EditImageActivity.BREW_CAM_PREVIEW_HEIGHT = imageHeight;
//                } else if (right >= imageWidth) {
//                    EditImageActivity.BREW_CAM_SHOULD_RESIZE = false;
//                    int newImageHeight = (int) ((float) bottom * ((float) imageWidth / (float) right));
//                    EditImageActivity.BREW_CAM_PREVIEW_WIDTH = imageWidth;
//                    EditImageActivity.BREW_CAM_PREVIEW_HEIGHT = newImageHeight;
//                    (this).layout(0, 0, imageWidth, newImageHeight);
//
//                }
        }
    }



    public void stopPreview(){
        if(camera != null){
            camera.stopPreview();
        }
    }

    public void releaseCamera(){
        if(camera != null){
            camera.release();
        }
    }
}

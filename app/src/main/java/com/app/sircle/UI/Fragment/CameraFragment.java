package com.app.sircle.UI.Fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddSelectedPhoto;
import com.app.sircle.UI.Activity.BaseActivity;
import com.app.sircle.UI.CustomView.CameraPreview;
import com.app.sircle.UI.Model.ImageData;

import java.util.List;

public class CameraFragment extends Fragment implements View.OnClickListener{


    private static final String TOAST_NO_FLASH = "Device does not support flash";
    private static final String TOAST_NO_FRONT_CAMERA = "Front camera does not exist";
    public  static final String INTENT_EXTRA_BACK_CAMERA_SHOWN = "com.propaganda3.boulevardia.backCameraShown";
    private static final String PICTURE = "picture";

    private ImageButton imageButtonSwitchCamera;
    private ImageButton imageButtonClickPhoto;
    private CheckBox checkBoxToggleFlash;

    private FrameLayout cameraPreviewLayout;

    private Camera camera;
    private CameraPreview cameraPreview;

    private boolean backCameraShown;
    private boolean flashOn;

    private List<ImageData> imageDataList;

    //to store the image data of the clicked image
    public static byte[] imageData;

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
        imageButtonSwitchCamera = (ImageButton) view.findViewById(R.id.cam_image_button_switch_camera);
        checkBoxToggleFlash = (CheckBox) view.findViewById(R.id.cam_checkbox_toggle_flash);
        imageButtonClickPhoto = (ImageButton) view.findViewById(R.id.cam_image_button_snap);

        imageButtonSwitchCamera.setOnClickListener(this);
        imageButtonClickPhoto.setOnClickListener(this);

        checkBoxToggleFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleFlash(isChecked);
            }
        });

    }

    /**
     * Used to enable or disable the buttons
     *
     * @param flag - send true to enable button and false to disable buttons.
     */
    private void enableButtons(boolean flag)
    {
        imageButtonSwitchCamera.setEnabled(flag);
        imageButtonClickPhoto.setEnabled(flag);
        checkBoxToggleFlash.setEnabled(flag);
    }

    private void attachCameraToView(boolean isBackCameraShown) {
        cameraPreview = new CameraPreview(getActivity(), camera, isBackCameraShown);
        cameraPreviewLayout.addView(cameraPreview);
    }

    private Camera getCameraInstance(int cameraID) {
        Camera c = null;
        try {
            c = Camera.open(cameraID); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable

    }


    private void showBackCamera() {
        releaseCamera();
        camera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);
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
            backCameraShown = false;
            removePreview();
            attachCameraToView(backCameraShown);
            toggleFlashButtonVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(getActivity(), TOAST_NO_FRONT_CAMERA, Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleFlashButtonVisibility(int visibility) {
        checkBoxToggleFlash.setVisibility(visibility);
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
            case R.id.cam_image_button_switch_camera:
                enableButtons(false);
                if (backCameraShown) {
                    showFrontCamera();
                } else {
                    showBackCamera();
                    checkBoxToggleFlash.setVisibility(View.VISIBLE);
                }
                enableButtons(true);
                break;
            case R.id.cam_image_button_snap:
                enableButtons(false);
                camera.takePicture(null, null, pictureCallback);
                break;
            default:

        }
    }

    //callback for handling picture taken by camera
    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            enableButtons(true);
            imageData = data;
            Intent intent = new Intent(getActivity(), AddSelectedPhoto.class);
            intent.putExtra(INTENT_EXTRA_BACK_CAMERA_SHOWN, backCameraShown);
            startActivity(intent);
        }
    };


    private void toggleFlash(boolean value) {

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
        if (BaseActivity.jumpToFragment){
            getActivity().finish();
        }
        imageData = null;
        toggleFlashButtonVisibility(View.VISIBLE);
        showBackCamera();
        enableButtons(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

}
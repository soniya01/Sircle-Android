package com.app.sircle.UI.cam.listener;

import android.graphics.Bitmap;

import com.app.sircle.UI.Model.ImageData;


public interface CameraFragmentListener {
    /**
     * A non-recoverable camera error has happened.
     */
    void onCameraError();

    /**
     * A picture has been taken.
     *
     */
    void onPictureTaken(Bitmap bitmap);
}

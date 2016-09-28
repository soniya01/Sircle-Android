package com.snaptech.montesorriamerican.UI.cam.listener;

import android.graphics.Bitmap;


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

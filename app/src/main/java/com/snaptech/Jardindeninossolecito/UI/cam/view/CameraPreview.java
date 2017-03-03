package com.snaptech.Jardindeninossolecito.UI.cam.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * SurfaceView for displaying a squared camera preview.
 *
 */
public class CameraPreview extends SurfaceView {
	
    private static final double ASPECT_RATIO_43 = 3.0 / 4.0;
    private static final double ASPECT_RATIO_11 = 1 / 1;
    private static final double ASPECT_RATIO_32 = 2.0 / 3.0;
    private static final double ASPECT_RATIO_53 = 3.0 / 5.0;
    private static final double ASPECT_RATIO_16_9 = 9.0 / 16.0;
    private static final double ASPECT_RATIO_31 = 1.0 / 3.0;

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreview(Context context) {
        super(context);
        
    }

    /**
     * Measure the view and its content to determine the measured width and the
     * measured height.
     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        if (width > height * ASPECT_RATIO_43) {
//             width = (int) (height * ASPECT_RATIO_43 + .5);
//        } else {
//            height = (int) (width / ASPECT_RATIO_43 + .5);
//        }
//        setMeasuredDimension(width, height);
//    }
}

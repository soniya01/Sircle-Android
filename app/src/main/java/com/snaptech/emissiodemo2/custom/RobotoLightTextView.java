package com.snaptech.emissiodemo2.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mosesafonso on 16/05/16.
 */
public class RobotoLightTextView extends TextView {

    public RobotoLightTextView(Context context, AttributeSet attrs)
    {
       super(context,attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/roboto-light.ttf"));
    }

}

package com.snaptech.institutocolombres.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mosesafonso on 27/05/16.
 */
public class RobotoRegularTextView extends TextView {

    public RobotoRegularTextView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/roboto-regular.ttf"));
    }
}

package com.snaptech.kapruddin2016.WebService.Common;

import com.snaptech.kapruddin2016.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

package com.grayjam.sircle.WebService.Common;

import com.grayjam.sircle.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

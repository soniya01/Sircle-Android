package com.app.sircle.WebService.Common;

import com.app.sircle.Utility.AppError;
/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

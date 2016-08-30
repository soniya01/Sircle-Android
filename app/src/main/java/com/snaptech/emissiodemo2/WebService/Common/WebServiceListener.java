package com.snaptech.emissiodemo2.WebService.Common;

import com.snaptech.emissiodemo2.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

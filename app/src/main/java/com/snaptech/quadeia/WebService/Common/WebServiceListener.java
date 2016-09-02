package com.snaptech.quadeia.WebService.Common;

import com.snaptech.quadeia.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

package com.snaptech.ezeego1.WebService.Common;

import com.snaptech.ezeego1.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

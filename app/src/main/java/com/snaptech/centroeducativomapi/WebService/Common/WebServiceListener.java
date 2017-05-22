package com.snaptech.centroeducativomapi.WebService.Common;

import com.snaptech.centroeducativomapi.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

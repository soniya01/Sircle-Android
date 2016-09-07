package com.grayjam.quadeia.WebService.Common;

import com.grayjam.quadeia.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

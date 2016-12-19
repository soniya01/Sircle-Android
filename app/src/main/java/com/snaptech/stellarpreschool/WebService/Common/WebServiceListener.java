package com.snaptech.stellarpreschool.WebService.Common;

import com.snaptech.stellarpreschool.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

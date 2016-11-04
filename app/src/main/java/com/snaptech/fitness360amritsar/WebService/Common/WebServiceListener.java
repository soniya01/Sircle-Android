package com.snaptech.fitness360amritsar.WebService.Common;

import com.snaptech.fitness360amritsar.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

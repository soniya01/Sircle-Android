package com.snaptech.CentroEducativoLoyola.WebService.Common;

import com.snaptech.CentroEducativoLoyola.Utility.AppError;

/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceListener {

    public void onCompletion(Object responseObject, AppError error);
}

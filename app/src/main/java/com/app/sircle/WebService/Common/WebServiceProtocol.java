package com.app.sircle.WebService.Common;

import com.inhandhealth.patient.WebService.Common.WebServiceListener;

import java.util.HashMap;
import java.util.Objects;


/**
 * Created by soniya on 3/30/15.
 */
public interface WebServiceProtocol {

    public void executePostWithURL(String url, HashMap<String, String> params, Object requestObject, Class responseClass, WebServiceListener webserviceListener) ;

    public void executePutWithURL(String url, HashMap<String, String> params, Object requestObject, Class responseClass, WebServiceListener webserviceListener);

    public void executeGetWithURL(String url, HashMap<String, String> params, Object requestObject, Class responseClass, WebServiceListener webserviceListener) ;
}

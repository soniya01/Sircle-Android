package com.app.sircle.WebService;

import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.Common.RetrofitImplementation;
import com.app.sircle.WebService.Common.WebServiceListener;

import java.util.HashMap;

/**
 * Created by soniya on 12/08/15.
 */
public class LoginService {

    public static void login(HashMap<String, String> params, final GetLoginResponseWebServiceListener getLoginResponseWebServiceListener){

        RetrofitImplementation retrofitImplementation = new RetrofitImplementation();
        retrofitImplementation.executePostWithURL(Constants.LOGIN_API_PATH, params, null,LoginResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object response, AppError error) {
                LoginResponse loginResponse = (LoginResponse)response;
                getLoginResponseWebServiceListener.onCompletion(loginResponse, error);
            }
        });
    }

    public interface GetLoginResponseWebServiceListener{

        public void onCompletion(LoginResponse loginResponse, AppError error);
    }
}

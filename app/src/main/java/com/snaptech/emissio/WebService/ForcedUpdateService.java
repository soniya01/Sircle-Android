package com.snaptech.emissio.WebService;

import com.snaptech.emissio.Manager.ForceUpdateManager;
import com.snaptech.emissio.Utility.AppError;
import com.snaptech.emissio.Utility.Constants;
import com.snaptech.emissio.WebService.Common.RetrofitImplementation;
import com.snaptech.emissio.WebService.Common.WebServiceListener;

import java.util.HashMap;

/**
 * Created by Aniket on 17-08-2016.
 */
public class ForcedUpdateService {

    private static ForcedUpdateService sharedInstance;
    private static RetrofitImplementation retrofitImplementation;

    private ForcedUpdateService(){

    }
    public static ForcedUpdateService getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new ForcedUpdateService();
            retrofitImplementation = new RetrofitImplementation();
        }
        return sharedInstance;
    }
    public void getForcedUpdateData(HashMap object, final GetForcedServiceManagerListener getForcedServiceManagerListener){
        retrofitImplementation.executeGetWithURL(Constants.FORCED_UPDATE_URL, object, null, ForcedUpdateResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR && responseObject != null){
                    //EventData eventData = ((EventDataReponse)responseObject).getEventData();
                    getForcedServiceManagerListener.onCompletion((ForcedUpdateResponse)responseObject, new AppError());
                }else {
                    getForcedServiceManagerListener.onCompletion(null, error);
                }
            }
        });
    }
    public interface GetForcedServiceManagerListener{

        public void onCompletion(ForcedUpdateResponse response, AppError error);
    }
}

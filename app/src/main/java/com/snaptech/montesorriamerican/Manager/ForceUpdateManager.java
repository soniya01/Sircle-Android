package com.snaptech.montesorriamerican.Manager;

import com.snaptech.montesorriamerican.Utility.AppError;
import com.snaptech.montesorriamerican.WebService.ForcedUpdateResponse;
import com.snaptech.montesorriamerican.WebService.ForcedUpdateService;

import java.util.HashMap;

/**
 * Created by Vikas on 17-08-2016.
 */
public class ForceUpdateManager {

//    private static EventManager sharedInstance;
//    public static List<Terms> termsList = new ArrayList<>();
//    public  static List<Event> eventList = new ArrayList<>();
//    public  static List<EventCategory> eventCategoryList = new ArrayList<>();
//    public static Event eventDetail;
//    public static EventDetailResponseData eventDetailResponseData;

    private static ForceUpdateManager sharedInstance;
    public static ForcedUpdateResponse forcedUpdateResponse;


    private ForceUpdateManager(){

    }
    public static ForceUpdateManager getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new ForceUpdateManager();
        }
        return sharedInstance;
    }

    public void getForcedUpdateData(HashMap requestObject, final GetForcedUpdateManagerListener getForcedUpdateManagerListener){


        ForcedUpdateService.getSharedInstance().getForcedUpdateData(requestObject, new ForcedUpdateService.GetForcedServiceManagerListener() {
            @Override
            public void onCompletion(ForcedUpdateResponse data, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR) {
                    if (data != null) {

                    forcedUpdateResponse=data;
                    }

                    getForcedUpdateManagerListener.onCompletion(data, new AppError());
                } else {
                    getForcedUpdateManagerListener.onCompletion(data, error);
                }
            }
        });
    }
    public interface GetForcedUpdateManagerListener{
        public void onCompletion(ForcedUpdateResponse data, AppError error);
    }
}
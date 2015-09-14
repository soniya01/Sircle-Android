package com.app.sircle.Manager;

import com.app.sircle.UI.Model.EventCategory;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.EventData;
import com.app.sircle.WebService.EventWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 15/08/15.
 */
public class EventManager {

    private static EventManager sharedInstance;

    private EventManager(){

    }

    public static EventManager getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new EventManager();
        }
        return sharedInstance;
    }

    public void getAllTerms(HashMap<String, String> map, final GetAllTermsManagerListener getAllTermsManagerListener){

        EventWebService.getSharedInstance().getAllTerms(map, new EventWebService.GetAllTermsServiceListener() {
            @Override
            public void onCompletion(List<Terms> termsList, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR) {

                    getAllTermsManagerListener.onCompletion(termsList, new AppError());
                } else {
                    getAllTermsManagerListener.onCompletion(termsList, error);
                }
            }
        });
    }

    public void getEventsMonthWise(HashMap requestObject, final GetMonthwiseEventsManagerListener getMonthwiseEventsManagerListener ){

        EventWebService.getSharedInstance().getMonthWiseEvents(requestObject, new EventWebService.GetMonthwiseEventsServiceListener() {
            @Override
            public void onCompletion(EventData data, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR) {
                    getMonthwiseEventsManagerListener.onCompletion(data, new AppError());
                } else {
                    getMonthwiseEventsManagerListener.onCompletion(data, error);
                }
            }
        });
    }

    public void getAllEvents(HashMap requestObject, final GetMonthwiseEventsManagerListener getMonthwiseEventsManagerListener){

        EventWebService.getSharedInstance().getCalendarEvents(requestObject, new EventWebService.GetMonthwiseEventsServiceListener() {
            @Override
            public void onCompletion(EventData data, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR) {
                    getMonthwiseEventsManagerListener.onCompletion(data, new AppError());
                }else {
                    getMonthwiseEventsManagerListener.onCompletion(data, error);
                }
            }
        });
    }

    public void getEventCategory(final GetEventsCategoryManagerListener getEventsCategoryManagerListener){
        EventWebService.getSharedInstance().getAllEventCategory(new EventWebService.GetEventsCategoryServiceListener() {
            @Override
            public void onCompletion(List<EventCategory> eventCategoryList, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR && eventCategoryList != null) {
                    getEventsCategoryManagerListener.onCompletion(eventCategoryList, new AppError());
                }else {
                    getEventsCategoryManagerListener.onCompletion(null, error);
                }
            }
        });
    }

    public interface EventManagerListener{
        public void onCompletion(AppError error);
    }


    public interface GetAllTermsManagerListener{
        public void onCompletion(List<Terms> termsList, AppError error);
    }

    public interface GetMonthwiseEventsManagerListener{
        public void onCompletion(EventData data, AppError error);
    }

    public interface GetEventsCategoryManagerListener{
        public void onCompletion(List<EventCategory> eventCategoryList, AppError error);
    }
}

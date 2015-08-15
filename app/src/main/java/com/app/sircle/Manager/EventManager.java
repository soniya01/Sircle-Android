package com.app.sircle.Manager;

import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.Utility.AppError;
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
                if (error.getErrorCode() == AppError.NO_ERROR){

                    getAllTermsManagerListener.onCompletion(termsList, new AppError());
                }else {
                    getAllTermsManagerListener.onCompletion(termsList, error);
                }
            }
        });
    }

    public void getEventsMonthWise(HashMap requestObject, final GetMonthwiseEventsManagerListener getMonthwiseEventsManagerListener ){

        EventWebService.getSharedInstance().getMonthWiseEvents(requestObject, new EventWebService.GetMonthwiseEventsServiceListener() {
            @Override
            public void onCompletion(List<CalendarMonthlyListData> eventList, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR) {
                    getMonthwiseEventsManagerListener.onCompletion(eventList, new AppError());
                }
                getMonthwiseEventsManagerListener.onCompletion(eventList, error);
            }
        });
    }

    public void getAllEvents(HashMap requestObject, final GetMonthwiseEventsManagerListener getMonthwiseEventsManagerListener){

        EventWebService.getSharedInstance().getCalendarEvents(requestObject, new EventWebService.GetMonthwiseEventsServiceListener() {
            @Override
            public void onCompletion(List<CalendarMonthlyListData> eventList, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR) {
                    getMonthwiseEventsManagerListener.onCompletion(eventList, new AppError());
                }
                getMonthwiseEventsManagerListener.onCompletion(eventList, error);
            }
        });
    }

    public void getEventCategory(final GetMonthwiseEventsManagerListener getMonthwiseEventsManagerListener){
        EventWebService.getSharedInstance().getAllEventCategory(new EventWebService.GetMonthwiseEventsServiceListener() {
            @Override
            public void onCompletion(List<CalendarMonthlyListData> eventList, AppError error) {
                //TODO: decide EventCategory data and create a model for same
                if (error.getErrorCode() == AppError.NO_ERROR) {
                    getMonthwiseEventsManagerListener.onCompletion(eventList, new AppError());
                }
                getMonthwiseEventsManagerListener.onCompletion(eventList, error);
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
        public void onCompletion(List<CalendarMonthlyListData> eventsList, AppError error);
    }
}

package com.app.sircle.WebService;

import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.Common.RetrofitImplementation;
import com.app.sircle.WebService.Common.WebServiceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soniya on 15/08/15.
 */
public class EventWebService {

    public static List<Terms> termsList = new ArrayList<Terms>();
    private static EventWebService sharedInstance;
    private static RetrofitImplementation retrofitImplementation;

    private EventWebService(){

    }

    public static EventWebService getSharedInstance(){
        if (sharedInstance == null){
            sharedInstance = new EventWebService();
            retrofitImplementation = new RetrofitImplementation();
        }
        return sharedInstance;
    }

    public void getAllTerms(HashMap<String, String> map, final GetAllTermsServiceListener getAllTermsServiceListener){

        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_ALL_TERMS_API_PATH, map, null, Terms.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                termsList = (ArrayList<Terms>) responseObject;
                if (error.getErrorCode() == AppError.NO_ERROR){
                    getAllTermsServiceListener.onCompletion(termsList, new AppError());
                }else {
                    getAllTermsServiceListener.onCompletion(termsList, error);
                }

            }
        });
    }

    public void getMonthWiseEvents(HashMap object, final GetMonthwiseEventsServiceListener getMonthwiseEventsServiceListener){

        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_MONTH_WISE_API_PATH, null, object, CalendarMonthlyListData.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                List<CalendarMonthlyListData> eventList = (ArrayList<CalendarMonthlyListData>) responseObject;
                if (error.getErrorCode() == AppError.NO_ERROR){
                    getMonthwiseEventsServiceListener.onCompletion(eventList, new AppError());
                }else {
                    getMonthwiseEventsServiceListener.onCompletion(eventList, error);
                }
            }
        });
    }

    public void getCalendarEvents(HashMap object, final GetMonthwiseEventsServiceListener getMonthwiseEventsServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_ALL_EVENTS_API_PATH, null, object, CalendarMonthlyListData.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                List<CalendarMonthlyListData> eventList = (ArrayList<CalendarMonthlyListData>) responseObject;
                if (error.getErrorCode() == AppError.NO_ERROR){
                    getMonthwiseEventsServiceListener.onCompletion(eventList, new AppError());
                }else {
                    getMonthwiseEventsServiceListener.onCompletion(eventList, error);
                }
            }
        });
    }

    public void getAllEventCategory( final GetMonthwiseEventsServiceListener getMonthwiseEventsServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_CATEGORY_API_PATH, null, null, CalendarMonthlyListData.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                //TODO: decide what will be the eventcategory data
                List<CalendarMonthlyListData> eventList = (ArrayList<CalendarMonthlyListData>) responseObject;
                if (error.getErrorCode() == AppError.NO_ERROR){
                    getMonthwiseEventsServiceListener.onCompletion(eventList, new AppError());
                }else {
                    getMonthwiseEventsServiceListener.onCompletion(eventList, error);
                }
            }
        });
    }

    public void deleteEvent(HashMap requestObject, EventWebServiceListener eventManagerListener){
        //TODO: how to call delete using retrofit
    }



    public interface EventWebServiceListener{
        public void onCompletion(AppError error);
    }

    public interface GetAllTermsServiceListener{

        public void onCompletion(List<Terms> termsList, AppError error);
    }

    public interface GetMonthwiseEventsServiceListener{

        public void onCompletion(List<CalendarMonthlyListData> eventList, AppError error);
    }
}

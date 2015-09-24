package com.app.sircle.WebService;

import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.EventCategory;
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

    public void addEvent(HashMap params, final PostWebServiceListener postWebServiceListener){
        retrofitImplementation.executePostWithURL(Constants.EVENTS_ADD_NEW_EVENT_API_PATH, params, null, PostResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                postWebServiceListener.onCompletion((PostResponse)responseObject, error);
            }
        });
    }

    public void getAllTerms(HashMap<String, String> map, final GetAllTermsServiceListener getAllTermsServiceListener){

        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_ALL_TERMS_API_PATH, null, null, TermsResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                termsList = (ArrayList<Terms>) ((TermsResponse)responseObject).getData();
                if (error.getErrorCode() == AppError.NO_ERROR){
                    getAllTermsServiceListener.onCompletion(termsList, new AppError());
                }else {
                    getAllTermsServiceListener.onCompletion(termsList, error);
                }

            }
        });
    }

    public void getMonthWiseEvents(HashMap object, final GetMonthwiseEventsServiceListener getMonthwiseEventsServiceListener){

        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_MONTH_WISE_API_PATH, object, null, EventDataReponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR && responseObject != null){
                   // EventData eventData = ((EventDataReponse)responseObject).getEventData();
                    getMonthwiseEventsServiceListener.onCompletion((EventDataReponse)responseObject, new AppError());
                }else {
                    getMonthwiseEventsServiceListener.onCompletion(null, error);
                }
            }
        });
    }

    public void getCalendarEvents(HashMap object, final GetMonthwiseEventsServiceListener getMonthwiseEventsServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_ALL_EVENTS_API_PATH, object, null, EventDataReponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR && responseObject != null){
                    //EventData eventData = ((EventDataReponse)responseObject).getEventData();
                    getMonthwiseEventsServiceListener.onCompletion((EventDataReponse)responseObject, new AppError());
                }else {
                    getMonthwiseEventsServiceListener.onCompletion(null, error);
                }
            }
        });
    }

    public void getAllEventCategory( final GetEventsCategoryServiceListener getEventsCategoryServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_CATEGORY_API_PATH, null, null, CategoryResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                //TODO: decide what will be the eventcategory data
                if (error.getErrorCode() == AppError.NO_ERROR && responseObject != null){
                    //List<EventCategory> eventList = (ArrayList<EventCategory>) (((CategoryResponse)responseObject).getData());
                    getEventsCategoryServiceListener.onCompletion((CategoryResponse)responseObject, new AppError());
                }else {
                    getEventsCategoryServiceListener.onCompletion(null, error);
                }
            }
        });
    }

    public void getEventDetails(HashMap map, final EventWebServiceListener eventWebServiceListener){
        retrofitImplementation.executeGetWithURL(Constants.EVENTS_GET_DETAILS_API_PATH, map, null, EventDetailResponse.class, new WebServiceListener() {
            @Override
            public void onCompletion(Object responseObject, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR && responseObject != null){
                    eventWebServiceListener.onCompletion((EventDetailResponse)responseObject, new AppError());
                }else {
                    eventWebServiceListener.onCompletion(null, error);
                }
            }
        });
    }

    public void deleteEvent(HashMap requestObject, EventWebServiceListener eventManagerListener){
        //TODO: how to call delete using retrofit
    }

    public interface PostWebServiceListener{
        public void onCompletion(PostResponse response,AppError error);
    }

    public interface EventWebServiceListener{
        public void onCompletion(EventDetailResponse response,AppError error);
    }

    public interface GetAllTermsServiceListener{

        public void onCompletion(List<Terms> termsList, AppError error);
    }

    public interface GetMonthwiseEventsServiceListener{

        public void onCompletion(EventDataReponse response, AppError error);
    }

    public interface GetEventsCategoryServiceListener{
        public void onCompletion(CategoryResponse response, AppError error);
    }
}

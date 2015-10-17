package com.app.sircle.Manager;

import com.app.sircle.UI.Model.Event;
import com.app.sircle.UI.Model.EventCategory;
import com.app.sircle.UI.Model.Terms;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.CategoryResponse;
import com.app.sircle.WebService.EventData;
import com.app.sircle.WebService.EventDataReponse;
import com.app.sircle.WebService.EventDetailResponse;
import com.app.sircle.WebService.EventWebService;
import com.app.sircle.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by soniya on 15/08/15.
 */
public class EventManager {

    private static EventManager sharedInstance;
    public static List<Terms> termsList = new ArrayList<>();
    public  static List<Event> eventList = new ArrayList<>();
    public  static List<EventCategory> eventCategoryList = new ArrayList<>();
    public static Event eventDetail;

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
                    EventManager.termsList.clear();
                    EventManager.termsList = termsList;

                    getAllTermsManagerListener.onCompletion(termsList, new AppError());
                } else {
                    getAllTermsManagerListener.onCompletion(termsList, error);
                }
            }
        });
    }

    public void getEventsMonthWise(final HashMap requestObject, final GetMonthwiseEventsManagerListener getMonthwiseEventsManagerListener ){

        EventWebService.getSharedInstance().getMonthWiseEvents(requestObject, new EventWebService.GetMonthwiseEventsServiceListener() {
            @Override
            public void onCompletion(EventDataReponse data, AppError error) {
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
            public void onCompletion(EventDataReponse data, AppError error) {
                if (error.getErrorCode() == AppError.NO_ERROR) {
                    if (data != null && data.getEventData() != null){
                        eventList.clear();
                        EventManager.eventList = data.getEventData().getEvents();
                    }

                    getMonthwiseEventsManagerListener.onCompletion(data, new AppError());
                } else {
                    getMonthwiseEventsManagerListener.onCompletion(data, error);
                }
            }
        });
    }

    public void getEventCategory(final GetEventsCategoryManagerListener getEventsCategoryManagerListener){
        EventWebService.getSharedInstance().getAllEventCategory(new EventWebService.GetEventsCategoryServiceListener() {
            @Override
            public void onCompletion(CategoryResponse response, AppError error) {
                if (response != null && response.getData() != null) {
                    eventCategoryList.clear();
                    eventCategoryList = response.getData();
                    getEventsCategoryManagerListener.onCompletion(response, new AppError());
                } else {
                    getEventsCategoryManagerListener.onCompletion(response, error);
                }
            }
        });
    }

    public void addEvent(HashMap object, final AddEventsManagerListener addEventsManagerListener){
        EventWebService.getSharedInstance().addEvent(object, new EventWebService.PostWebServiceListener() {
            @Override
            public void onCompletion(PostResponse response, AppError error) {
                addEventsManagerListener.onCompletion(response, error);
            }
        });
    }

    public void deleteEvent(HashMap object, final AddEventsManagerListener addEventsManagerListener){
        EventWebService.getSharedInstance().deleteEvent(object, new EventWebService.PostWebServiceListener() {
            @Override
            public void onCompletion(PostResponse response, AppError error) {
                addEventsManagerListener.onCompletion(response, error);
            }
        });
    }

    public void getEventDetails(HashMap object, final EventManagerListener eventManagerListener){
        EventWebService.getSharedInstance().getEventDetails(object, new EventWebService.EventWebServiceListener() {
            @Override
            public void onCompletion(EventDetailResponse response, AppError error) {
                if (response != null){

                    if (response.getData() != null && response.getData().getEventInfo() != null){
                        eventDetail = null;
                        eventDetail = response.getData().getEventInfo();
                    }

                }
                eventManagerListener.onCompletion(response, error);
            }
        });
    }

    public interface EventManagerListener{
        public void onCompletion(EventDetailResponse eventDetailResponse, AppError error);
    }



    public interface GetAllTermsManagerListener{
        public void onCompletion(List<Terms> termsList, AppError error);
    }

    public interface GetMonthwiseEventsManagerListener{
        public void onCompletion(EventDataReponse data, AppError error);
    }

    public interface GetEventsCategoryManagerListener{
        public void onCompletion(CategoryResponse response, AppError error);
    }

    public interface AddEventsManagerListener{
        public void onCompletion(PostResponse response, AppError error);
    }
}

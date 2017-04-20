package com.snaptech.CentroEducativoLoyola.Manager;

import com.snaptech.CentroEducativoLoyola.UI.Model.Event;
import com.snaptech.CentroEducativoLoyola.UI.Model.EventCategory;
import com.snaptech.CentroEducativoLoyola.UI.Model.Terms;
import com.snaptech.CentroEducativoLoyola.Utility.AppError;
import com.snaptech.CentroEducativoLoyola.WebService.CategoryResponse;
import com.snaptech.CentroEducativoLoyola.WebService.EventDataReponse;
import com.snaptech.CentroEducativoLoyola.WebService.EventDetailResponse;
import com.snaptech.CentroEducativoLoyola.WebService.EventDetailResponseData;
import com.snaptech.CentroEducativoLoyola.WebService.EventWebService;
import com.snaptech.CentroEducativoLoyola.WebService.PostResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by soniya on 15/08/15.
 */
public class  EventManager {

    private static EventManager sharedInstance;
    public static List<Terms> termsList = new ArrayList<>();
    public  static List<Event> eventList = new ArrayList<>();
    public  static List<EventCategory> eventCategoryList = new ArrayList<>();
    public static Event eventDetail;
    public static EventDetailResponseData eventDetailResponseData;

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
                    if (data != null && data.getEvents() != null){
                        eventList.clear();
                        EventManager.eventList = data.getEvents();
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
                    eventCategoryList = response.getData().getCategories();
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

                    if (response.getData() != null && response.getData() != null){
                      //  eventDetail = null;
                        eventDetailResponseData = response.getData();
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

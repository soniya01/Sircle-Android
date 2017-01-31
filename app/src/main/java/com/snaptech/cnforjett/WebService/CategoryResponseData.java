package com.snaptech.cnforjett.WebService;

import com.snaptech.cnforjett.UI.Model.EventCategory;

import java.util.List;

/**
 * Created by mosesafonso on 30/05/16.
 */
public class CategoryResponseData {

    public List<EventCategory> categories;

    public List<EventCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<EventCategory> categories) {
        this.categories = categories;
    }

}

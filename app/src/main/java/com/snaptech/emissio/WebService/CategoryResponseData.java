package com.snaptech.emissio.WebService;

import com.snaptech.emissio.UI.Model.EventCategory;

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

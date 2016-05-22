package com.app.sircle.WebService;

import com.app.sircle.UI.Model.NotificationGroups;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 18/08/15.
 */
public class LoginResponse {

   public  UserData data;
   // public List<UserData> data = new ArrayList<UserData>();
    public String message;
    public int status;

//    public List<UserData>  getUserData() {
//        return data;
//    }
//
//    public void setUserData(List<UserData> data) {
//        this.data = data;
//    }

    public UserData getUserData()
    {
        return data;
    }

    public void setUserData(UserData data)
    {
        this.data = data;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

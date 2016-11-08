package com.snaptech.kapruddin2016.WebService;

/**
 * Created by soniya on 18/08/15.
 */
public class LoginResponse {

   //public  UserData data;

    public Customer data;
   // public List<UserData> data = new ArrayList<UserData>();
    public String message;
    public int code;

//    public List<UserData>  getUserData() {
//        return data;
//    }
//
//    public void setUserData(List<UserData> data) {
//        this.data = data;
//    }

    public Customer getUserData()
    {
        return data;
    }

    public void setCustomer(Customer data)
    {
        this.data = data;
    }



    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.snaptech.asb.WebService;

/**
 * Created by mosesafonso on 24/07/16.
 */
public class LogoutStatusResponse {

    //public  UserData data;

    //  public Customer data;
    // public List<UserData> data = new ArrayList<UserData>();
    public String message;
    public int code;

    public LogoutData data;
//    public List<UserData>  getUserData() {
//        return data;
//    }
//
//    public void setUserData(List<UserData> data) {
//        this.data = data;
//    }

    public LogoutData getData()
    {
        return data;
    }

    public void setData(LogoutData data)
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

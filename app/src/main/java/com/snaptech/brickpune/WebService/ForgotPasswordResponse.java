package com.snaptech.brickpune.WebService;

/**
 * Created by mosesafonso on 16/06/16.
 */
public class ForgotPasswordResponse {

    //public  UserData data;

  //  public Customer data;
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

//    public Customer getUserData()
//    {
//        return data;
//    }
//
//    public void setCustomer(Customer data)
//    {
//        this.data = data;
//    }



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

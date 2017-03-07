package com.snaptech.institutocolombres.WebService;

/**
 * Created by Vikas on 16-08-2016.
 */

//{
//        "code": 200,
//        "message": "No Error",
//        "data": {
//        "id": 1,
//        "android_version": 0,
//        "ios_version": 0,
//        "android_url": "https://play.google.com/store/apps/details?id=com.snaptech.emissio",
//        "ios_url": null,
//        "force_update_android": 0,
//        "force_update_ios": 0
//        }
//        }
public class ForcedUpdateResponse {

    public int code;
    public String message;
    public ForcedUpdateData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ForcedUpdateData getForcedUpdateData() {
        return data;
    }

    public void setForcedUpdateData(ForcedUpdateData forcedUpdateData) {
        this.data = forcedUpdateData;
    }
}

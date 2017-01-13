package com.snaptech.techfitemissio.UI.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by soniya on 10/08/15.
 */
public class ImageData implements Parcelable{

    private String path;
    private int rotation;

    public ImageData(){

    }

    public ImageData(Parcel parcel){
        setPath(parcel.readString());
        setRotation(parcel.readInt());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getPath());
        dest.writeInt(getRotation());
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        public ImageData createFromParcel(Parcel source) {
            return new ImageData(source);
        }

        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

}

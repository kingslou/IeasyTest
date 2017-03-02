package com.ieasy360.launcher;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YiMuTian on 2017/3/1.
 */

public class TransEntity implements Parcelable {

    private String transType;

    private Bundle data;


    public Bundle getData() {
        if (data == null) {
            data = new Bundle();
        }
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.transType);
        dest.writeBundle(this.data);
    }

    public TransEntity() {
    }

    protected TransEntity(Parcel in) {
        this.transType = in.readString();
        this.data = in.readBundle();
    }

    public static final Creator<TransEntity> CREATOR = new Creator<TransEntity>() {
        @Override
        public TransEntity createFromParcel(Parcel source) {
            return new TransEntity(source);
        }

        @Override
        public TransEntity[] newArray(int size) {
            return new TransEntity[size];
        }
    };
}

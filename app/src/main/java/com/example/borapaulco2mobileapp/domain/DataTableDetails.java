package com.example.borapaulco2mobileapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class DataTableDetails implements Parcelable {

    private String mContact;
    private String mThreshold;
    private String mDate;


    protected DataTableDetails(Parcel in) {
        mContact = in.readString();
        mThreshold = in.readString();
        mDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mContact);
        dest.writeString(mThreshold);
        dest.writeString(mDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataTableDetails> CREATOR = new Creator<DataTableDetails>() {
        @Override
        public DataTableDetails createFromParcel(Parcel in) {
            return new DataTableDetails(in);
        }

        @Override
        public DataTableDetails[] newArray(int size) {
            return new DataTableDetails[size];
        }
    };

    public DataTableDetails() {
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public void setmThreshold(String mThreshold) {
        this.mThreshold = mThreshold;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmContact() {
        return mContact;
    }

    public String getmThreshold() {
        return mThreshold;
    }

    public String getmDate() {
        return mDate;
    }
}

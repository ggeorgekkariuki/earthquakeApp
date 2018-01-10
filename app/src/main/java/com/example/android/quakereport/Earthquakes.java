package com.example.android.quakereport;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PROBOOK on 06-Dec-17.
 */

public class Earthquakes extends ArrayList<Earthquakes> {

    private String mLocation;
    private  double mMagnitude;
    private long mTimeInMilliseconds;
    private String mUrl;

    public Earthquakes (  double magnitude,String location, long timeInMilliseconds, String url){
        mLocation = location;
        mMagnitude = magnitude;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }
    public String getmUrl() {
        return mUrl;
    }


}

package com.example.android.quakereport;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PROBOOK on 06-Dec-17.
 */

public class Earthquakes {

    private String mPlaces;
    private String mMagnitude;
    private String mDate;

    public Earthquakes ( String magnitude,String place, String date){
        mPlaces = place;
        mMagnitude = magnitude;
        mDate = date;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }

    public String getmPlaces() {
        return mPlaces;
    }
}

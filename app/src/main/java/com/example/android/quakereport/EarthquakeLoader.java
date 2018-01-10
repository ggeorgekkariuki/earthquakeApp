package com.example.android.quakereport;
//Make sure the AsyncTaskLoader is v4
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by PROBOOK on 08-Jan-18.
 */

public class EarthquakeLoader extends AsyncTaskLoader <List<Earthquakes>> {

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquakes> loadInBackground() {
        if (mUrl == null){ return null;}

        List<Earthquakes> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }

    private String mUrl;
    private final static String LOG_TAG = EarthquakeLoader.class.getName();
}

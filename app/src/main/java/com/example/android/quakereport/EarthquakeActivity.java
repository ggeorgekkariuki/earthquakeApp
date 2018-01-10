/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
//Use this line for the Loader to actually work!
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquakes>>{

    private EarthquakeAdapter mAdapter;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public static final String USGS_HTTP_STRING = "https://earthquake.usgs.gov/fdsnws/event/1/" +
            "query";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    //An empty view when there are no earthquakes to display
    private TextView mEmptyStateTextView;
    //A progress bar global variable
    private ProgressBar mProgressBarView;

    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquakes> ());

        // Set the adapter on the {@link ListView}
//       // so the list can be populated in the user interface
       earthquakeListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquakes currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        //If there is no internet or there are no earthquakes to display
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_list);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        //The Progress Bar view
        mProgressBarView = (ProgressBar) findViewById(R.id.progress_bar);


        //Check if there is a connected network
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            //A progress bar to show the level of progress
            mProgressBarView.setVisibility(View.GONE);

            //Set the Empty text viewto display a no internet error message
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }

    @Override
    public Loader<List<Earthquakes>> onCreateLoader(int id, Bundle args) {
        // Replace the body of onCreateLoader() method to read the user’s latest preferences for the minimum magnitude
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default)
        );
        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        // Construct a proper URI with their preference
        Uri basseUri = Uri.parse(USGS_HTTP_STRING);
        Uri.Builder uriBuiler = basseUri.buildUpon();

        uriBuiler.appendQueryParameter("format", "geojson");
        uriBuiler.appendQueryParameter("limit","10");
        uriBuiler.appendQueryParameter("minmagnitude",minMagnitude);
        uriBuiler.appendQueryParameter("orderby", orderBy);

        // Create a new Loader for that URI.
        EarthquakeLoader e = new EarthquakeLoader(EarthquakeActivity.this, uriBuiler.toString() );
        return e;
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquakes>> loader, List<Earthquakes> data) {
        //Set the visibility to gone once load has finished
        mProgressBarView.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        mAdapter.clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquakes>> loader) {
        mAdapter.clear();

    }
    //Now we've got a menu with a "Settings" item that opens our SettingsActivity!
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

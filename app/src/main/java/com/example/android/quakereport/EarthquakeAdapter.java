package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PROBOOK on 06-Dec-17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquakes> {

    public EarthquakeAdapter (Context context, ArrayList<Earthquakes> earthquakes){
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Check if the existing view is being re-used, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_view,
                    parent, false);
        }

        //Get the Earthquake Object at this position
        Earthquakes currentEarthquake = getItem(position);

        //Find the Text View for the city in the earthquake_view.xml
        TextView cityTextView = (TextView) listItemView.findViewById(R.id.city);
        //Set the city
        cityTextView.setText(currentEarthquake.getmPlaces());

        //Find the text view for the magnitude in the earthquake_view.xml
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        //Set the magnitude
        magnitudeTextView.setText(currentEarthquake.getmMagnitude());

        //Find the text view for the date in the earthquake_view.xml
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        //set the date
        dateTextView.setText(currentEarthquake.getmDate());

        return listItemView;
    }
}

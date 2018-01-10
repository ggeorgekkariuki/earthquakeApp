package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PROBOOK on 06-Dec-17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquakes> {



    public EarthquakeAdapter (Context context, List<Earthquakes> earthquakes){
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


        //Set the CITY into a string variable
        String location = currentEarthquake.getmLocation();
        TextView locationOffset = (TextView) listItemView.findViewById(R.id.location_offset);
        TextView cityTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        //
        if(location.contains(STRING_SPLITTING_WORD)){
            String [] directionTo = location.split("of");

            String part1 = directionTo[0] + "of"; //88km N of

            locationOffset.setText(part1);

            String part2 = directionTo[1];//Yelizovo, Russia
            //Find the Text View for the city in the earthquake_view.xml

            cityTextView.setText(part2);
        } else {
            locationOffset.setText(R.string.near_the);
            cityTextView.setText(location);
        }

        //MAGNITUDE
        double magTude = currentEarthquake.getmMagnitude();
        //A method to convert the magnitude
        String mag = formatMagnitude(magTude);
        //Find the text view for the MAGNITUDE in the earthquake_view.xml
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        //Set the magnitude
        magnitudeTextView.setText(mag);

        //Set the right BACKGROUND colour
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        //This DATE OBJECT will be used for the date and time text fields
        Date dateObject = new Date(currentEarthquake.getmTimeInMilliseconds());

        //Find the text view for the DATE in the earthquake_view.xml
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        //A method to convert the DATE
        String date = formatDate(dateObject);
        //set the DATE
        dateTextView.setText(date);

        //Find the text view for TIME
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        //A method to convert the Time
        String time = formatTime(dateObject);
        //set the TIME
        timeTextView.setText(time);

        return listItemView;
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM DD, yyyy");
        return formatter.format(dateObject);
    };

    private String formatTime (Date dateObject){
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(dateObject);
    };

    private String formatMagnitude (double mag){
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(mag);
    };


    //The word that splits the world
    private static final String STRING_SPLITTING_WORD = "of";

    //A method to choose the right BACKGROUND COLOR for the Magnitude
    private int getMagnitudeColor(double magnitude){
        int magSize = (int) magnitude;
        int magnitudeColor;

        switch (magSize){
            case 0:
            case 1: int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
            magnitudeColor = magnitude1Color;
            break;
            case 2: int magnitude2Color = ContextCompat.getColor(getContext(), R.color.magnitude2);
            magnitudeColor = magnitude2Color;
            break;
            case 3: int magnitude3Color = ContextCompat.getColor(getContext(), R.color.magnitude3);
            magnitudeColor = magnitude3Color;
            break;
            case 4: int magnitude4Color = ContextCompat.getColor(getContext(), R.color.magnitude4);
            magnitudeColor = magnitude4Color;
            break;
            case 5: int magnitude5Color = ContextCompat.getColor(getContext(), R.color.magnitude5);
            magnitudeColor = magnitude5Color;
            break;
            case 6: int magnitude6Color = ContextCompat.getColor(getContext(), R.color.magnitude6);
            magnitudeColor = magnitude6Color;
            break;
            case 7: int magnitude7Color = ContextCompat.getColor(getContext(), R.color.magnitude7);
            magnitudeColor = magnitude7Color;
            break;
            case 8: int magnitude8Color = ContextCompat.getColor(getContext(), R.color.magnitude8);
            magnitudeColor = magnitude8Color;
            break;
            case 9: int magnitude9Color = ContextCompat.getColor(getContext(), R.color.magnitude9);
            magnitudeColor = magnitude9Color;
            break;
            default: int magnitude10Color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
            magnitudeColor = magnitude10Color;
            break;
        }
        return magnitudeColor;
    }

}

package com.example.android.quakereport.screens.earthquakesListActivity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakesListAdapter extends ArrayAdapter<EarthquakesData> {

    public EarthquakesListAdapter(@NonNull Context context, ArrayList<EarthquakesData> earthquakesData) {
        super(context, 0, earthquakesData);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_cell, parent, false);
        }

        // Get the {@link EarthquakesData} object located at this position in the list
        EarthquakesData currentEarthquake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitudeTextView);
        // Get the version name from the current EarthquakesData object and
        // set this text on the name TextView
        String earthquakeMagnitude = extractMagnitude(currentEarthquake.getMagnitude());
        magnitudeTextView.setText(earthquakeMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView proximityTextView = listItemView.findViewById(R.id.locationProximityTextView);
        // Get the version number from the current EarthquakesData object and
        // set this text on the number TextView
        String earthquakeProximity = extractProximity(currentEarthquake.getLocation());
        proximityTextView.setText(earthquakeProximity);

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView locationTextView = listItemView.findViewById(R.id.locationTextView);
        // Get the version number from the current EarthquakesData object and
        // set this text on the number TextView
        String earthquakeLocation = extractLocation(currentEarthquake.getLocation());
        locationTextView.setText(earthquakeLocation);

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        TextView dateTextView = listItemView.findViewById(R.id.dateTextView);
        // Get the image resource ID from the current EarthquakesData object and
        // set the image to iconView
        Date date = new Date(currentEarthquake.getDate());
        String earthquakeTime = formatDate(date)+ "\n" +formatTime(date);
        dateTextView.setText(earthquakeTime);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    private static String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(dateObject);
    }

    private static String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private static String extractProximity(String locationString){
        String extractedProximity;
        if (locationString.contains("of")){
            extractedProximity = locationString.substring(0, locationString.indexOf("of")) + "of";
        } else {
            extractedProximity = "Near to";
        }
        return extractedProximity;
    }

    private static String extractLocation(String completeLocation){
        String extractedLocation;
        if (completeLocation.contains("of")){
            extractedLocation = completeLocation.substring(completeLocation.indexOf("of")+3);
        } else {
            extractedLocation = completeLocation;
        }
        return extractedLocation;
    }

    private  static String extractMagnitude(Double extractedMagnitude){
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(extractedMagnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}

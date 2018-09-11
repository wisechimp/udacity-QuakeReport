package com.example.android.quakereport.screens.EarthquakesListActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.R;

import java.util.ArrayList;

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
        magnitudeTextView.setText(currentEarthquake.getMagnitude());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView locationTextView = listItemView.findViewById(R.id.locationTextView);
        // Get the version number from the current EarthquakesData object and
        // set this text on the number TextView
        locationTextView.setText(currentEarthquake.getLocation());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        TextView dateTextView = listItemView.findViewById(R.id.dateTextView);
        // Get the image resource ID from the current EarthquakesData object and
        // set the image to iconView
        dateTextView.setText(currentEarthquake.getDate());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}

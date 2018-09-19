package com.example.android.quakereport.screens.earthquakesListActivity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EarthquakesJsonQuery {

    /**
     * Create a private constructor because no one should ever create a {@link EarthquakesJsonQuery} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private EarthquakesJsonQuery() {
    }

    /**
     * Return a list of {@link EarthquakesData} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthquakesData> extractEarthquakes(String string) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthquakesData> earthquakes = new ArrayList<>();

        /*
        Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        is formatted, a JSONException exception object will be thrown.
        Catch the exception so the app doesn't crash, and print the error message to the logs.
        */
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject jsonRootObject = new JSONObject(string);
            JSONArray jsonFeaturesArray = jsonRootObject.optJSONArray("features");


            for(int i=0; i < jsonFeaturesArray.length(); i++){
                JSONObject JSONEarthquakeObject = jsonFeaturesArray.getJSONObject(i);
                JSONObject JSONEarthquakeProperties = JSONEarthquakeObject.getJSONObject("properties");

                Double earthquakeMagnitudeDouble = JSONEarthquakeProperties.optDouble("mag");

                String completeLocation = JSONEarthquakeProperties.optString("place");

                long earthquakeTimeInMilliseconds = JSONEarthquakeProperties.optLong("time");

                String earthquakeUrl = JSONEarthquakeProperties.optString("url");

                earthquakes.add(new EarthquakesData(earthquakeMagnitudeDouble, completeLocation, earthquakeTimeInMilliseconds, earthquakeUrl));
            }

        } catch (JSONException e) {
            /*
            If an error is thrown when executing any of the above statements in the "try" block,
            catch the exception here, so the app doesn't crash. Print a log message
            with the message from the exception.
            */
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}
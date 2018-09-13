package com.example.android.quakereport.screens.earthquakesListActivity;

public class EarthquakesData {

    private Double mMagnitude;
    private String mLocation;
    private Long mDate;
    private String mUrl;

    public EarthquakesData (Double magnitude, String location, Long date, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
        mUrl = url;
    }

    public Double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public Long getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}

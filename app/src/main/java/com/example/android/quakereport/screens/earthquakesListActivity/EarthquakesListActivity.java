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
package com.example.android.quakereport.screens.earthquakesListActivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.screens.earthquakeSettings.EarthquakesSettings;
import com.example.android.quakereport.screens.earthquakeWebView.EarthquakeDetailActivity;

import java.util.ArrayList;

public class EarthquakesListActivity extends AppCompatActivity {

    private final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private ArrayList<EarthquakesData> mEarthquakesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        preferredEarthquakesRequesrURL();
        final ProgressBar loadingProgress = findViewById(R.id.loading_spinner);
        final TextView errorMessageTV = findViewById(R.id.emptyListText);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected){
            errorMessageTV.setText("No internet connection");
        }


        //Reference the View Model
        EarthquakesListViewModel viewModel = ViewModelProviders.of(this).get(EarthquakesListViewModel.class);

        final EarthquakesListAdapter adapter = new EarthquakesListAdapter(this, mEarthquakesData);
        Log.i("Init mEarDat", String.valueOf(mEarthquakesData.size()));

        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);

        viewModel.getEarthquakesData ().observe(this, new Observer<ArrayList<EarthquakesData>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<EarthquakesData> earthquakes) {
                mEarthquakesData = earthquakes;
                if (earthquakes != null) {
                    adapter.addAll(earthquakes);
                }
                adapter.notifyDataSetChanged();
                listView.setEmptyView(errorMessageTV);
                loadingProgress.setVisibility(View.INVISIBLE);
                Log.i("mEarData", "onChanged: " + String.valueOf(mEarthquakesData.size()));
            }
        });
    }

    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
            intent.putExtra("EarthquakeURL", mEarthquakesData.get(position).getUrl());
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, EarthquakesSettings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String preferredEarthquakesRequesrURL() {
        String url;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", "time");
        url = uriBuilder.toString();
        Log.i("Preferred string", "preferredEarthquakesRequesrURL: " + "is " + url);
        return url;
    }
}

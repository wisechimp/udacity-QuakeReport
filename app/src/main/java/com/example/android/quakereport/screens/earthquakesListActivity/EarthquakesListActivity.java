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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.screens.earthquakeWebView.EarthquakeDetailActivity;

import java.util.ArrayList;

public class EarthquakesListActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakesListActivity.class.getName();
    private ArrayList<EarthquakesData> mEarthquakesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create an ArrayList of EarthquakesData objects
        mEarthquakesData = EarthquakesJsonQuery.extractEarthquakes();

        // Create an {@link EarthquakesDataAdapter}, whose data source is a list of
        // {@link EarthquakesData}s. The adapter knows how to create list item views for each item
        // in the list.
        EarthquakesListAdapter adapter = new EarthquakesListAdapter(this, mEarthquakesData);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
            intent.putExtra("EarthquakeURL", mEarthquakesData.get(position).getUrl());
            startActivity(intent);
        }
    };
}

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
package com.example.android.quakereport.screens.EarthquakesListActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.quakereport.R;

import java.util.ArrayList;

public class EarthquakesListActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakesListActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create an ArrayList of EarthquakesData objects
        ArrayList<EarthquakesData> earthquakesData = EarthquakesJsonQuery.extractEarthquakes();

        // Create an {@link EarthquakesDataAdapter}, whose data source is a list of
        // {@link EarthquakesData}s. The adapter knows how to create list item views for each item
        // in the list.
        EarthquakesListAdapter adapter = new EarthquakesListAdapter(this, earthquakesData);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}

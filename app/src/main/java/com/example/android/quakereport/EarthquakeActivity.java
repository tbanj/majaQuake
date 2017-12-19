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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {


    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    /** Adapter for the list of earthquakes */
    private QuakeAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Start the AsyncTask to fetch the earthquake data
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        itemsAdapter = new QuakeAdapter(this, new ArrayList<quakes>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(itemsAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                quakes currentEarthquake = itemsAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<quakes>> {

        /**
         * {@link AsyncTask} to perform the network request on a background thread, and then
         * update the UI with the list of earthquakes in the response.
         *
         * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
         * an output type. Our task will take a String URL, and return an Earthquake. We won't do
         * progress updates, so the second generic is just Void.
         *
         * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
         * The doInBackground() method runs on a background thread, so it can run long-running code
         * (like network activity), without interfering with the responsiveness of the app.
         * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
         * UI thread, so it can use the produced data to update the UI.
         */
        @Override
        protected List<quakes> doInBackground(String... urls) {
        // Perform the HTTP request for earthquake data and process the response.

            // Don't perform the request if there are no URLs, or the first URL is null.
            if(urls.length< 1 || urls[0]== null){
                return null;
            }

            List<quakes> result = QueryUtils.fetchEarthquakeData(urls[0]);

            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of earthquake data from a previous
         * query to USGS. Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<quakes> data) {
            // If there is no result, do nothing.
            // Clear the adapter of previous earthquake data
           itemsAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                itemsAdapter.addAll(data);
            }

        }
    }


}

package com.example.android.quakereport.screens.earthquakesListActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class EarthquakesListViewModel extends AndroidViewModel {

    private static MutableLiveData<ArrayList<EarthquakesData>> mEarthquakesData = new MutableLiveData<>();

    public EarthquakesListViewModel(@NonNull Application application) {
        super(application);

    }

    public static class EarthquakesDataRequest extends AsyncTask<String, Void, ArrayList<EarthquakesData>> {

        @Override
        protected ArrayList<EarthquakesData> doInBackground(String... strings) {

            // Create URL object
            URL url = createUrl(strings[0]);
            Log.i("URL", strings[0]);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("http request IOEx", "doInBackground: failed");
            }

            ArrayList<EarthquakesData> result = EarthquakesJsonQuery.extractEarthquakes(jsonResponse);
            Log.i("EarData", "doneInBackground: " + String.valueOf(result.size()));
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<EarthquakesData> earthquakesData) {
            super.onPostExecute(earthquakesData);
            mEarthquakesData.setValue(earthquakesData);
            setmEarthquakesData(mEarthquakesData);
            Log.i("Are we thru?", "onPostExecute: " + String.valueOf(mEarthquakesData.getValue()));
        }
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("CreateURL error", "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.getResponseCode();

            // Only get the response if the connection was successful
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Connection failed", "error code: " + String.valueOf(urlConnection.getResponseCode()));
            }

        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e("Connection I/O Error", String.valueOf(e));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public MutableLiveData<ArrayList<EarthquakesData>> getEarthquakesData() {
        Log.i("Are we", "gettingEarthquakesData: " + String.valueOf(mEarthquakesData.getValue()));
        return mEarthquakesData;
    }

    private static void setmEarthquakesData(MutableLiveData<ArrayList<EarthquakesData>> mEarthquakesData) {
        EarthquakesListViewModel.mEarthquakesData = mEarthquakesData;
    }
}

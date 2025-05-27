package com.example.growcast;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class NetworkUtility {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY = "dd89b00231fe037f3063de2540ed00f1";

    public static WeatherItem createHttpRequest(final String cityName) {
        Log.i("CIty Name : ",cityName);
        URL url = createURL(BASE_URL + cityName + "&appid=" + API_KEY); //URL object created
        String json = null;
        try {
            if (url != null) {
                // If there is a valid URL object, start the connection establishment phase
                json = setUpConnection(url);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null || json.isEmpty()) {
            return null;
        }
        // If there is a valid JSON, extract the needed data using the scrapData method
        return scrapData(json);
    }

    static URL createURL(final String LINK) {
        try {
            return new URL(LINK);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String setUpConnection(final URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            // Connection establishment phase
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            Log.e("Utility",urlConnection.getResponseMessage());
            Log.e("Utility",""+urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == 200) {
                // If the connection is successful, obtain the input stream and call the readInputStream method to read the raw data
                inputStream = urlConnection.getInputStream();
                return readInputStream(inputStream); // Returns the string
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return null;
    }


    private static String readInputStream(InputStream inputStream) throws IOException {
        // Read the raw data here
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = br.readLine();
        while (line != null) {
            builder.append(line).append("\n");
            line = br.readLine();
        }
        br.close();
        return builder.toString();
    }


// ...

    static WeatherItem scrapData(final String json) {
        try {
            JSONObject rootObject = new JSONObject(json);
            String description = rootObject.optJSONArray("weather").optJSONObject(0).optString("description");
            JSONObject jsonObject = rootObject.optJSONObject("main");
            double temperature = jsonObject.optDouble("temp") - 273.15; // Convert temperature from Kelvin to Celsius
            double feels = jsonObject.optDouble("feels_like") - 273.15; // Convert temperature from Kelvin to Celsius
            double humidity = jsonObject.optDouble("humidity");

            DecimalFormat decimalFormat = new DecimalFormat("#"); // Format without decimal places
            int roundedTemperature = Integer.parseInt(decimalFormat.format(temperature));
            int roundedFeels = Integer.parseInt(decimalFormat.format(feels));

            return new WeatherItem(description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}

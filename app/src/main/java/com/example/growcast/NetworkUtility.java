package com.example.growcast;

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

public class NetworkUtility {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY = "dd89b00231fe037f3063de2540ed00f1";

    public static WeatherItem createHttpRequest(final String cityName) {

        URL url = createURL(BASE_URL + cityName + "&appid=" + API_KEY);//URL object created
        String json = null;
        try {
            if (url != null) {
                //incase of valid URL object , connection establishment phase is started
                json = setUpConnection(url);
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null || json.isEmpty())
            return null;
        //in case of valid JSON scrap data is called which is used to fetch nedded data from the JSON data
        return scrapData(json);
    }

    private static URL createURL(final String LINK) {
        try {
            return new URL(LINK);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String setUpConnection(final URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            //connection establishment phase
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                //in case of successfull connection input stream is obtained and readInputStream method is called to read the raw data
                inputStream = urlConnection.getInputStream();
                return readInputStream(inputStream);//returns string
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
        //raw data is read here
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

    private static WeatherItem scrapData(final String json) {
        try {
            JSONObject rootObject;
            rootObject = new JSONObject(json);
            String description = rootObject.optJSONArray("weather").optJSONObject(0).optString("description");
            JSONObject jsonObject = rootObject.optJSONObject("main");
            double temperature = jsonObject.optDouble("temp");
            double feels = jsonObject.optDouble("feels_like");
            double humidity = jsonObject.optDouble("humidity");
            return new WeatherItem(description, temperature, feels, humidity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

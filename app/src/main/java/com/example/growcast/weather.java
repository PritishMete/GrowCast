package com.example.growcast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class weather extends AppCompatActivity implements LoaderManager.LoaderCallbacks<WeatherItem> {

    // API Key for OpenWeatherMap
    private static final String API_KEY = "2469624757d5e8e00ee047890b315187";

    // API Endpoint for weather data
    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/weather?q=city_name_here&appid=" + API_KEY;

    private TextView temperatureValueTextView;
    private TextView descriptionValueTextView;
    private TextView feelsLikeValueTextView;

    private TextView humidityValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        init();
        LoaderManager.getInstance(this).initLoader(1, null, this);

    }

    private void init() {
        // Initialize TextViews
        temperatureValueTextView = findViewById(R.id.temperatureValueTextView);
        descriptionValueTextView = findViewById(R.id.descriptionValueTextView);
        feelsLikeValueTextView = findViewById(R.id.feelsLikeValueTextView);
        humidityValueTextView = findViewById(R.id.humidityValueTextView);
    }


    @NonNull
    @Override
    public Loader<WeatherItem> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e(" ","Started");
        return new WeatherLoader(weather.this, "KOLKATA");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<WeatherItem> loader, WeatherItem data) {
        // Set temperature value to temperatureValueTextView

        temperatureValueTextView.setText(String.valueOf(data.getTemperature()));
        humidityValueTextView.setText(String.valueOf(data.getHumidity()));
        descriptionValueTextView.setText(String.valueOf(data.getDescription()));
        feelsLikeValueTextView.setText(String.valueOf(data.getFeels()));

        // Display a toast message with temperature and humidity
        Toast.makeText(this, data.getTemperature() + " " + data.getHumidity() + " " +data.getDescription() + " " + data.getFeels(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLoaderReset(@NonNull Loader<WeatherItem> loader) {

    }
}

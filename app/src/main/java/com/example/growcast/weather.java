package com.example.growcast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class weather extends AppCompatActivity implements LoaderManager.LoaderCallbacks<WeatherItem> {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    // API Key for OpenWeatherMap
    private static final String API_KEY = "dd89b00231fe037f3063de2540ed00f1";

    private TextView temperatureValueTextView;
    private TextView descriptionValueTextView;
    private TextView feelsLikeValueTextView;
    private TextView humidityValueTextView;
    private Button refreshButton;
    private TextView citynameTextView; // Added cityNameTextView
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        // Create an instance of WeatherItem
//        WeatherItem weatherItem = new WeatherItem("haze", 25.5, 26.0, 60.0);
        String weatherCondition = "haze"; // Replace with the actual weather condition
        String description;
        if (weatherCondition.equals("haze")) {
            description = "haze";
        } else if (weatherCondition.equals("thunderstorm")) {
            description = "thunderstorm";
        }else if (weatherCondition.equals("broken clouds")) {
            description = "broken clouds";
        } else if (weatherCondition.equals("clear sky")) {
            description = "clear sky";
        }else {
            // Set a default description if none of the conditions match
            description = "unknown";
        }
        WeatherItem weatherItem = new WeatherItem(description);

        // Get a reference to the ImageView in your layout
        ImageView weatherImageView = findViewById(R.id.weatherImageView);

        // Set the weather image using the setWeatherImage method
        weatherItem.setWeatherImage(weatherImageView);
        initViews();

        // Request location permission
        requestLocationPermission();

        // Set click listener for refresh button
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshWeather();
            }
        });
    }

    private void initViews() {
        // Initialize TextViews
        temperatureValueTextView = findViewById(R.id.temperatureValueTextView);
        descriptionValueTextView = findViewById(R.id.descriptionValueTextView);
        feelsLikeValueTextView = findViewById(R.id.feelsLikeValueTextView);
        humidityValueTextView = findViewById(R.id.humidityValueTextView);
        citynameTextView = findViewById(R.id.citynameTextView); // Initialize cityNameTextView
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        // Initialize Refresh Button
        refreshButton = findViewById(R.id.refreshButton);
    }
    private void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }



    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted, fetch location
            fetchLocation();
        }
    }

    private void fetchLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Fetch the location name using Geocoder
                        String cityName = getLocationName(latitude, longitude);

                        if (cityName != null) {
                            // Update the city name TextView
                            citynameTextView.setText(cityName);

                            // Fetch the user's email ID
                            String userEmail = getUserEmail();

                            // Upload the latitude, longitude, and location name to the database
                            uploadLocation(latitude, longitude, cityName, userEmail);

                            // Load weather data using the fetched location
                            LoaderManager.getInstance(this).initLoader(1, null, this);
                        } else {
                            Toast.makeText(this, "Unable to get location name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Error getting location", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    private String getLocationName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                return address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    private void uploadLocation(double latitude, double longitude, String cityName, String userEmail) {
        // Modify the email ID to use a valid path format
        String modifiedEmail = userEmail.replace(".", "_");


        // Upload latitude, longitude, city name, and modified email ID to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference locationRef = databaseReference.child("locations").child(modifiedEmail);
        locationRef.child("latitude").setValue(latitude);
        locationRef.child("longitude").setValue(longitude);
        locationRef.child("cityName").setValue(cityName);
    }

    private void refreshWeather() {
        showLoading();

        // Clear existing weather data
        temperatureValueTextView.setText("");
        humidityValueTextView.setText("");
        descriptionValueTextView.setText("");
        feelsLikeValueTextView.setText("");

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Show loading progress

            // Fetch the updated weather data
            fetchLocation();
        } else {
            // Request location permission
            requestLocationPermission();
        }
    }

    @NonNull
    @Override
    public Loader<WeatherItem> onCreateLoader(int id, @Nullable Bundle args) {

        // Show loading progress
        showLoading();
        // Create a WeatherLoader with the city name or other location information
        return new WeatherLoader(this, "Kolkata");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<WeatherItem> loader, WeatherItem data) {
        if (data != null) {
            // Set weather data to the corresponding TextViews
            temperatureValueTextView.setText(String.format(Locale.getDefault(), "%.1f °C", data.getTemperature()));
            humidityValueTextView.setText(String.format(Locale.getDefault(), "%.1f °C", data.getHumidity()));
            descriptionValueTextView.setText(data.getDescription());
            feelsLikeValueTextView.setText(String.format(Locale.getDefault(), "%.1f °C", data.getFeels()));
        } else {
            Toast.makeText(this, "Error loading weather data", Toast.LENGTH_SHORT).show();
        }
        // Hide loading progress
        hideLoading();

    }
    private void hideLoading() {
        loadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<WeatherItem> loader) {
        // Reset or clear any data if needed
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, fetch location
                fetchLocation();
            } else {
                // Location permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

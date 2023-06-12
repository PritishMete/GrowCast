package com.example.growcast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class weather extends AppCompatActivity implements LoaderManager.LoaderCallbacks<WeatherItem> {

    private static final String API_KEY = "dd89b00231fe037f3063de2540ed00f1";
    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=" + API_KEY;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private TextView temperatureValueTextView;
    private TextView descriptionValueTextView;
    private TextView feelsLikeValueTextView;
    private TextView humidityValueTextView;

    private FusedLocationProviderClient fusedLocationClient;
    private String currentCity;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        init();


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (checkLocationPermission()) {
            // Check if location data already exists in the Firebase database
            checkLocationDataInFirebase();
        } else {
            requestLocationPermission();
        }
    }

    private void init() {
        temperatureValueTextView = findViewById(R.id.temperatureValueTextView);
        descriptionValueTextView = findViewById(R.id.descriptionValueTextView);
        feelsLikeValueTextView = findViewById(R.id.feelsLikeValueTextView);
        humidityValueTextView = findViewById(R.id.humidityValueTextView);
    }

    private boolean checkLocationPermission() {
        int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
        );
    }

    private void checkLocationDataInFirebase() {
        String userIdentifier = getCurrentUserIdentifier();
        if (userIdentifier != null) {
            // Query the Firebase database to check if location data exists for the current user
            databaseReference.child("locations").child(userIdentifier).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Location data exists, retrieve it and search weather
                        LocationData locationData = dataSnapshot.getValue(LocationData.class);
                        if (locationData != null) {
                            double latitude = locationData.getLatitude();
                            double longitude = locationData.getLongitude();
                            String cityName = locationData.getCityName();
                            currentCity = cityName;
                            searchWeather(cityName);
                        }
                    } else {
                        // Location data doesn't exist, request current location
                        getCurrentLocation();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "Failed to read location data", databaseError.toException());
                }
            });
        } else {
            // User identifier is null, handle the case as desired
            // In this example, we simply request the location again
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String cityName = getCityNameFromCoordinates(latitude, longitude);
                    if (cityName != null) {
                        currentCity = cityName;
                        storeLocationToFirebase(latitude, longitude, cityName);
                        searchWeather(cityName);
                    } else {
                        Toast.makeText(weather.this, "Failed to get city name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(weather.this, "Failed to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getCityNameFromCoordinates(double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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

    private void storeLocationToFirebase(double latitude, double longitude, String cityName) {
        String userIdentifier = getCurrentUserIdentifier();
        if (userIdentifier != null) {
            Map<String, Object> locationData = new HashMap<>();
            locationData.put("latitude", latitude);
            locationData.put("longitude", longitude);
            locationData.put("cityName", cityName);

            databaseReference.child("locations").child(userIdentifier).setValue(locationData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Firebase", "Location stored successfully");
                            } else {
                                Log.e("Firebase", "Failed to store location", task.getException());
                            }
                        }
                    });
        } else {
            // User identifier is null, handle the case as desired
            // In this example, we simply log an error message
            Log.e("Firebase", "Failed to store location: User identifier is null");
        }
    }

    private void searchWeather(String cityName) {
        LoaderManager.getInstance(this).restartLoader(1, null, this);
    }

    @NonNull
    @Override
    public Loader<WeatherItem> onCreateLoader(int id, @Nullable Bundle args) {
        return new WeatherLoader(weather.this, currentCity);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<WeatherItem> loader, WeatherItem data) {
        temperatureValueTextView.setText(String.valueOf(data.getTemperature()) + " °c" );
        descriptionValueTextView.setText(data.getDescription());
        feelsLikeValueTextView.setText(String.valueOf(data.getFeels()) + " °c");
        humidityValueTextView.setText(String.valueOf(data.getHumidity()) + " %");
        TextView locationNameTextView = findViewById(R.id.cityname);
        locationNameTextView.setText(currentCity);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<WeatherItem> loader) {
        // No implementation needed
    }

    private String getCurrentUserIdentifier() {
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null;
    }
}

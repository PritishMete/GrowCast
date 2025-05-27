package com.example.growcast;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class BlankFragment extends Fragment implements OnMapReadyCallback {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
            // Get the current location data
            LocationData currentLocation = new LocationData(22.8097497, 88.2247724, "My Location");

            // Add a marker for the current location
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(currentLocation.getCityName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }
}
package com.example.testing.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.testing.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textview.MaterialTextView;

public class MapFragment extends Fragment {

    private MaterialTextView map_LBL_title;
    GoogleMap gMap;
    FrameLayout map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize the map view
        MapView mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        zoom(mapView);

        return view;
    }
    private void zoom(MapView mapView){
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // Set map properties
                gMap = googleMap;
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                gMap.getUiSettings().setZoomControlsEnabled(true);

                // Add a marker at the specified location and move the camera to that location
                LatLng israel = new LatLng(31.0461, 34.8516);
                gMap.addMarker(new MarkerOptions().position(israel).title("Marker Title"));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(israel, 15));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(israel));

            }
        });
    }

    private void findViews(View view) {
        map_LBL_title = view.findViewById(R.id.map_view);
    }

    public void zoomOnRecord(String coordinates){
        int endOfFirst = 0;
        double latitude ;
        double longitude ;
        for(int i = 0; i < coordinates.length(); i++) {
            if(coordinates.charAt(i) == ',') {
                endOfFirst = i;
                break;
            }
        }

        latitude = Double.parseDouble(coordinates.substring(0,endOfFirst));
        longitude = Double.parseDouble(coordinates.substring(endOfFirst+1));
        LatLng receivedLocation = new LatLng(latitude, longitude);
        gMap.addMarker(new MarkerOptions().position(receivedLocation).title("Marker Title"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(receivedLocation, 15));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(receivedLocation));
    }
}
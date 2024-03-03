package com.example.obstacle_race.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.obstacle_race.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textview.MaterialTextView;


public class MapFragment extends Fragment {
    private GoogleMap mMap;
    private MaterialTextView locationDetails;
    private OnMapReadyCallback callBack = googleMap -> {mMap = googleMap;};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.activity_map_fragment,container,false);
        findViews(view);
        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        // Async
        supportMapFragment.getMapAsync(callBack);
        return view;
    }

    private void findViews(View view) {
        locationDetails= view.findViewById(R.id.map_LBL_data);
    }

    public void locateOnMap(double x , double y){
        if (x==0.0 && y==0.0) {
            locationDetails.setVisibility(View.VISIBLE);
            locationDetails.setText("No information about location");
        }
        else{
            locationDetails.setVisibility(View.INVISIBLE);
            LatLng point = new LatLng(x,y);
            mMap.addMarker(new MarkerOptions().position(point).title(""));
            moveToCurrentLocation(point);
        }

    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    }


}

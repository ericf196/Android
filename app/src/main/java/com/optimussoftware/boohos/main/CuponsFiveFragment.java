package com.optimussoftware.boohos.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.optimussoftware.boohos.R;


public class CuponsFiveFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;


    public CuponsFiveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_cupons_five, container, false);
        mMapView = (MapView) mView.findViewById(R.id.maps_cupon);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(this);

        return mView;
    }

   /* public void onMapReady(GoogleMap googleMap){

        MapsInitializer.initialize(getActivity());
        mGoogleMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Estatua de la Libertad").snippet("control"));

    }*/


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap mMap) {
        mGoogleMap = mMap;

        MapsInitializer.initialize(getActivity().getApplication());
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(-34, 151);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("Estatua de la Libertad").snippet("control"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}

package com.example.concordia_campus_guide.LocationFragment;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.concordia_campus_guide.ClassConstants;

import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class LocationFragment extends Fragment{
    MapView mMapView;
    private GoogleMap mMap;
    private LocationFragmentViewModel mViewModel;
    private GeoJsonLayer mLayer;
    private Button loyolaBtn;
    private Button sgwBtn;
    private Boolean myLocationPermissionsGranted = false;

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment_fragment, container, false);
        initComponent(rootView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        initMap();
        setupClickListeners();
        getLocationPermission();
        return rootView;
    }

    private void initComponent(View rootView) {
        mMapView = rootView.findViewById(R.id.mapView);
        sgwBtn = rootView.findViewById(R.id.SGWBtn);
        loyolaBtn = rootView.findViewById(R.id.loyolaBtn);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel.class);
    }

    private void initMap() {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setMapStyle(googleMap);
                mMap = googleMap;
                //mMap.setMapType(googleMap.MAP_TYPE_TERRAIN);
                setupPolygons(mMap);
                uiSettingsForMap(mMap);
                zoomInLocation(45.494999, -73.577854);
            }
        });
    }

    private void setMapStyle(GoogleMap googleMap) {
        try {
            googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), mViewModel.getMapStyle()));
        } catch (Resources.NotFoundException e) {
            Log.e("MAPACTIVITY", "Can't find style. Error: ", e);
        }
    }

    private void setupClickListeners() {
        setupLoyolaBtnClickListener();
        setupSGWBtnClickListener();
    }

    private void setupLoyolaBtnClickListener() {
        loyolaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.458205, -73.640438);
            }
        });
    }

    private void setupSGWBtnClickListener(){
        sgwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.494999, -73.577854);
            }
        });
    }

    private void setupPolygons(GoogleMap map) {
        mLayer = mViewModel.loadPolygons(map, getContext());
        setupPolygonClickListener();
        mLayer.addLayerToMap();
        setupMarkerClickListener(map); 
    }

    

    public void setupPolygonClickListener(){
        mLayer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
            @Override
            public void onFeatureClick(GeoJsonFeature geoJsonFeature) {
                //TODO: Make function that pops up the info card for the building (via the building-code)
                //Important null check do not remove!
                if(geoJsonFeature != null){
                    //replace code here
                    System.out.println("Clicked on "+geoJsonFeature.getProperty("code"));
                }
            }
        });
    }

    public boolean setupMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //TODO: Make function that pops up the info card for the building (via the building-code)
                System.out.println(marker.getTag());
                return false;
            }
        });
        return true;
    }

    private void uiSettingsForMap(GoogleMap mMap){
        if(myLocationPermissionsGranted){
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
    }

    private void zoomInLocation(double latitude, double longitude) {
        LatLng curr = new LatLng(latitude,longitude);
        float zoomLevel = 18.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curr, zoomLevel));
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(requestPermission()){
            myLocationPermissionsGranted = true;
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    ClassConstants.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean requestPermission(){
        return (checkSelfPermission(getContext(), ClassConstants.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == ClassConstants.LOCATION_PERMISSION_REQUEST_CODE)
            myLocationPermissionsGranted = (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED);
    }
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
}

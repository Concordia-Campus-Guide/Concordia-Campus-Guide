package com.example.concordia_campus_guide.LocationFragment;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Adapters.FloorPickerAdapter;
import com.example.concordia_campus_guide.BuildingCode;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Interfaces.OnFloorPickerOnClickListener;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;


import java.util.HashMap;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class LocationFragment extends Fragment implements OnFloorPickerOnClickListener {

    MapView mMapView;

    private GoogleMap mMap;
    private LocationFragmentViewModel mViewModel;
    private GeoJsonLayer mLayer;
    private Button loyolaBtn;
    private Button sgwBtn;
    private GridView mFloorPickerGv;

    private static final String TAG = "LocationFragment";
    private Boolean myLocationPermissionsGranted = false;
    private HashMap<String, GroundOverlay> buildingsGroundOverlays;
    private FloorPickerAdapter currentFloorPickerAdapter;

    private Button selectedFloor;

    /**
     * @return it will return a new object of this fragment
     */
    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment_fragment, container, false);
        getLocationPermission();
        initComponent(rootView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        setupClickListeners();
        initMap();

        return rootView;
    }


    /**
     * @param rootView is the object that contains the parts displayed on the fragment
     */
    private void initComponent(View rootView) {
        mMapView = rootView.findViewById(R.id.mapView);
        sgwBtn = rootView.findViewById(R.id.SGWBtn);
        loyolaBtn = rootView.findViewById(R.id.loyolaBtn);
        mFloorPickerGv = rootView.findViewById(R.id.FloorPickerGv);
        mFloorPickerGv.setVisibility(View.GONE);
        buildingsGroundOverlays = new HashMap<>();
    }

    private void setupFloorPickerAdapter(String buildingCode, String[] availableFloors) {
        mFloorPickerGv.setVisibility(View.VISIBLE);

        currentFloorPickerAdapter = new FloorPickerAdapter(getContext(), availableFloors, buildingCode, this);
        mFloorPickerGv.setAdapter(currentFloorPickerAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(com.example.concordia_campus_guide.LocationFragment.LocationFragmentViewModel.class);
    }


    /**
     * The purpose of this method is to init the map and fill it up with the required
     * information to display them to the user
     */
    private void initMap() {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setMapStyle(googleMap);
                mMap = googleMap;
                setupPolygons(mMap);
                initFloorPlans();
                uiSettingsForMap(mMap);
                zoomInLocation(45.494999, -73.577854);
            }
        });
    }


    /**
     * The purpose of this method is to figure the style of the map to display
     */
    private void initFloorPlans() {
        HashMap<String, GroundOverlayOptions> temp = mViewModel.getBuildingGroundOverlays();
        for(String key: temp.keySet()){
            buildingsGroundOverlays.put(key, mMap.addGroundOverlay(temp.get(key)));
        }
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


    /**
     * The purpose of this method is to setup the click listener for both
     * SGW and LOYALA Campuses buttons.
     */
    private void setupClickListeners() {
        setupLoyolaBtnClickListener();
        setupSGWBtnClickListener();
    }


    /**
     * The purpose of this method is to handle the "on click" of Loyala button
     */
    private void setupLoyolaBtnClickListener() {
        loyolaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.458205, -73.640438);
            }
        });
    }

    /**
     * The purpose of this method is to handle the "on click" of SGW button
     */
    private void setupSGWBtnClickListener(){
        sgwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(45.494999, -73.577854);
            }
        });
    }

    /**
     * The purpose of this method is display the polygon on the map and
     * call the right method for onClick polygon or on click the marker.
     * @param map is the map to be used in our application
     */
    private void setupPolygons(GoogleMap map) {
        mLayer = mViewModel.loadPolygons(map, getContext());
        mLayer.addLayerToMap();

        setupPolygonClickListener();
        setupBuildingMarkerClickListener(map);
        setupZoomListener(map);
        classRoomCoordinateTool(map);
    }

    private void setupZoomListener(final GoogleMap map) {
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if(map.getCameraPosition().zoom > 20){
                    mLayer.removeLayerFromMap();
                    //setup a different marker clickListener
                }
                else{
                    mLayer.addLayerToMap();
                    setupBuildingMarkerClickListener(map);
                }
            }
        });
    }


    /**
     * The purpose of this method is handle the onclick polygon
     * and to open the info card according to the clicked building.
     */
    public void setupPolygonClickListener(){
        mLayer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
            @Override
            public void onFeatureClick(GeoJsonFeature geoJsonFeature) {
                if(geoJsonFeature != null){
                    if(geoJsonFeature.getProperty("floorsAvailable")!= null) {
                        String[] floorsAvailable = geoJsonFeature.getProperty("floorsAvailable").split(",");
                        setupFloorPickerAdapter(geoJsonFeature.getProperty("code"), floorsAvailable);
                    }
                    String buildingCode = geoJsonFeature.getProperty("code");
                    ((MainActivity)getActivity()).showInfoCard(buildingCode);
                    System.out.println("Clicked on "+buildingCode);
                }
                else {
                    mFloorPickerGv.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * The purpose of this method is handle the onclick marker
     * and to open the info card according to the clicked building.
     */
    public boolean setupBuildingMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //TODO: Make function that pops up the info card for the building (via the building-code)
                String buildingCode = ((BuildingCode) marker.getTag()).toString();
                ((MainActivity)getActivity()).showInfoCard(buildingCode);
                System.out.println(buildingCode);
                return false;
            }
        });
        return true;
    }

    /**
     * The purpose of this method is to display the tools used with google
     * maps such as Current Location
     * @param mMap is the map used in the application
     */
    public boolean setupClassMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i(TAG,marker.getTag().toString());
                return false;
            }
        });
        return true;
    }


    /**
     * set up related to UI for the map
     * @param mMap
     */
    private void uiSettingsForMap(GoogleMap mMap){
        if(myLocationPermissionsGranted){
            mMap.setMyLocationEnabled(true);
        }
        mMap.setIndoorEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    /**
     * @param latitude is the chosen latitude on the map to zoom in
     * @param longitude is the chosen longitude on the map to zoom in
     */
    private void zoomInLocation(double latitude, double longitude) {
        LatLng curr = new LatLng(latitude,longitude);
        float zoomLevel = 18.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curr, zoomLevel));
    }

    /**
     * The purpose of this application is to ask the user for their permission
     * of using their current location.
     */
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

    private void classRoomCoordinateTool(GoogleMap map){
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i(TAG,"\"coordinates\" : [" + latLng.longitude + ", " + latLng.latitude + "]");
            }
        });
    }


    /**
     * @return the method returns true if the user accepts to give the application permission
     * for using their current location.
     */
    private boolean requestPermission(){
        return (checkSelfPermission(getContext(), ClassConstants.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onFloorPickerOnClick(int position, View view) {
        if (selectedFloor != null) selectedFloor.setEnabled(true);
        selectedFloor = (Button)view;
        view.setEnabled(false);
        mViewModel.setFloorPlan(buildingsGroundOverlays.get(currentFloorPickerAdapter.getBuildingCode()), currentFloorPickerAdapter.getBuildingCode(), currentFloorPickerAdapter.getFloorsAvailable()[position], getContext());

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
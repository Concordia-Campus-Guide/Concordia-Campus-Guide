package com.example.concordia_campus_guide.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.activities.MainActivity;
import com.example.concordia_campus_guide.activities.PathsActivity;
import com.example.concordia_campus_guide.adapters.DirectionWrapper;
import com.example.concordia_campus_guide.adapters.FloorPickerAdapter;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.helper.CurrentLocation;
import com.example.concordia_campus_guide.helper.CurrentLocationPermissionRequest;
import com.example.concordia_campus_guide.helper.DirectionsPolyLinesDrawer;
import com.example.concordia_campus_guide.helper.ViewModelFactory;
import com.example.concordia_campus_guide.helper.ui_helpers.POIMarkers;
import com.example.concordia_campus_guide.helper.ui_helpers.RoomMarkers;
import com.example.concordia_campus_guide.interfaces.OnFloorPickerOnClickListener;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;
import com.example.concordia_campus_guide.view_models.LocationFragmentViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.geojson.GeoJsonLayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationFragment extends Fragment implements OnFloorPickerOnClickListener {

    MapView mMapView;

    private static boolean MY_LOCATION_PERMISSION_GRANTED = false;

    private ImageButton myLocationBtn;
    private Button loyolaBtn;
    private Button sgwBtn;

    private GoogleMap mMap;
    private LocationFragmentViewModel mViewModel;
    private GeoJsonLayer mLayer;
    private GridView mFloorPickerGv;

    private CurrentLocation currentLocation;
    private CurrentLocationPermissionRequest currentLocationPermissionRequest;

    private HashMap<String, GroundOverlay> buildingsGroundOverlays;
    private FloorPickerAdapter currentFloorPickerAdapter;
    private POIMarkers poiMarkers;
    private RoomMarkers roomMarkers;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment_fragment, container, false);
        initComponents(rootView);
        mViewModel = new ViewModelProvider(getActivity(), new ViewModelFactory(this.getActivity().getApplication())).get(LocationFragmentViewModel.class);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        currentLocationPermissionRequest.getLocationPermission(this);
        MY_LOCATION_PERMISSION_GRANTED = currentLocationPermissionRequest.requestPermission(getContext());
        setupClickListeners();
        currentLocation.updateLocationEvery5Seconds();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupPOIListListener();
    }

    /**
     * @param rootView is the object that contains the parts displayed on the fragment
     */
    private void initComponents(View rootView) {
        mMapView = rootView.findViewById(R.id.mapView);
        sgwBtn = rootView.findViewById(R.id.SGWBtn);
        loyolaBtn = rootView.findViewById(R.id.loyolaBtn);
        myLocationBtn = rootView.findViewById(R.id.myLocation);
        mFloorPickerGv = rootView.findViewById(R.id.FloorPickerGv);
        mFloorPickerGv.setVisibility(View.GONE);

        buildingsGroundOverlays = new HashMap<>();

        poiMarkers = new POIMarkers();
        roomMarkers = new RoomMarkers();

        currentLocation = new CurrentLocation(getContext());
        currentLocationPermissionRequest= new CurrentLocationPermissionRequest();
    }

    private void setupFloorPickerAdapter(Building building) {
        mFloorPickerGv.setVisibility(View.VISIBLE);
        currentFloorPickerAdapter = new FloorPickerAdapter(getContext(), building.getAvailableFloors(), building.getBuildingCode(), this);
        mFloorPickerGv.setAdapter(currentFloorPickerAdapter);
        mViewModel.setFloorPlan(buildingsGroundOverlays.get(currentFloorPickerAdapter.getBuildingCode()), building.getBuildingCode(), building.getAvailableFloors().get(building.getAvailableFloors().size() - 1), mMap);
        setupRoomMarkers();
    }

    /**
     * The purpose of this method is to init the map and fill it up with the required
     * information to display them to the user
     */
    public void initMap() {
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(ClassConstants.LOCATION_FRAGMENT_TAG, e.getMessage());
        }
        mMapView.getMapAsync(googleMap -> {
            setMapStyle(googleMap);
            mMap = googleMap;
            setupPolygons(mMap);
            initFloorPlans();
            uiSettingsForMap(mMap);
            setFirstLocationToDisplay();
        });
    }


    public void deselectAll(){
        removeMarkersFromMap(poiMarkers.getPointsOfInterest());
        removeMarkersFromMap(roomMarkers.getMarkers());
        mFloorPickerGv.setVisibility(View.GONE);
    }

    private void removeMarkersFromMap(List<Marker> markers){
        for(Marker marker: markers) marker.remove();
        markers.clear();
    }

    private void setFirstLocationToDisplay() {
        setFirstLocationToDisplayOnSuccess();
        setFirstLocationToDisplayOnFailure();
    }

    @SuppressLint("MissingPermission")
    private void setFirstLocationToDisplayOnSuccess() {
        currentLocation.getFusedLocationProviderClient().getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        mMap.setMyLocationEnabled(true);
                        if (getActivity() instanceof MainActivity)
                            zoomInLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                        else
                            zoomInLocation(mViewModel.getInitialZoomLocation());
                    } else {
                        zoomInLocation(mViewModel.getInitialZoomLocation());
                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void setFirstLocationToDisplayOnFailure() {
        currentLocation.getFusedLocationProviderClient().getLastLocation().addOnFailureListener(getActivity(), e -> zoomInLocation(mViewModel.getInitialZoomLocation()));
    }

    /**
     * The purpose of this method is to figure the style of the map to display
     */
    private void initFloorPlans() {
        Map<String, Building> buildings = mViewModel.getBuildings();
        for (Map.Entry<String, Building> entry : buildings.entrySet()) {
            String key = entry.getKey();
            Building value = entry.getValue();

            if (value.getGroundOverlayOption() != null)
                buildingsGroundOverlays.put(key, mMap.addGroundOverlay(buildings.get(key).getGroundOverlayOption()));
        }
    }

    /**
     * set up related to UI for the map
     *
     * @param mMap
     */
    @SuppressLint("MissingPermission")
    private void uiSettingsForMap(GoogleMap mMap) {
        if (MY_LOCATION_PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setIndoorEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
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
     * The purpose of this method is display the polygon on the map and
     * call the right method for onClick polygon or on click the marker.
     *
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
        map.setOnCameraMoveListener(() -> {
            if (map.getCameraPosition().zoom > 19) {
                mLayer.removeLayerFromMap();
            } else {
                mLayer.addLayerToMap();
            }
        });
    }
    private void classRoomCoordinateTool(GoogleMap map) {
        map.setOnMapClickListener(latLng -> Log.i(ClassConstants.LOCATION_FRAGMENT_TAG, "\"coordinates\" : [" + latLng.longitude + ", " + latLng.latitude + "]"));
    }



    /**
     * The purpose of this method is handle the onclick marker
     * and to open the info card according to the clicked building.
     */
    public boolean setupBuildingMarkerClickListener(GoogleMap map) {
        map.setOnMarkerClickListener(marker -> {
            if (marker.getTag() != null) {
                if (marker.getTag() != null && (marker.getTag().toString().contains(ClassConstants.ROOM_TAG) || marker.getTag().toString().contains(ClassConstants.POI_TAG))) {
                    String roomCode = marker.getTag().toString().split("_")[1];
                    String floorCode = marker.getTag().toString().split("_")[2];
                    onRoomClick(roomCode, floorCode);
                }
                else {
                    Building building = mViewModel.getBuildingFromCode(marker.getTag().toString());
                    String buildingCode = (marker.getTag()).toString();
                    if (getActivity() instanceof MainActivity)
                        ((MainActivity) getActivity()).showInfoCard(buildingCode);
                    onBuildingClick(building);
                }
            }
            return false;
        });
        return true;
    }

    private void setupClickListeners() {
        setupLoyolaBtnClickListener();
        setupSGWBtnClickListener();
        setCurrentLocationBtn();
    }

    private void setCurrentLocationBtn() {
        myLocationBtn.setOnClickListener(v -> {
            mFloorPickerGv.setVisibility(View.GONE);
            popUpRequestPermission();
        });
    }

    /**
     * The purpose of this method is to handle the "on click" of Loyala button
     */
    private void setupLoyolaBtnClickListener() {
        loyolaBtn.setOnClickListener(view -> zoomInLocation(mViewModel.getZoomLocation(ClassConstants.LOYOLA_CENTER_BUILDING_LABEL)));
    }

    /**
     * The purpose of this method is to handle the "on click" of SGW button
     */
    private void setupSGWBtnClickListener() {
        sgwBtn.setOnClickListener(view -> zoomInLocation(mViewModel.getZoomLocation(ClassConstants.SGW_CENTER_BUILDING_LABEL)));
    }

    @SuppressLint("MissingPermission")
    private void popUpRequestPermission() {
        MY_LOCATION_PERMISSION_GRANTED = currentLocationPermissionRequest.requestPermission(getContext());
        if (!MY_LOCATION_PERMISSION_GRANTED) {
            currentLocationPermissionRequest.getLocationPermission(this);
            if (MY_LOCATION_PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                displayCurrentLocation();
                return;
            }
        }

        if (MY_LOCATION_PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            displayCurrentLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void displayCurrentLocation() {
        currentLocation.getFusedLocationProviderClient().getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        mMap.setMyLocationEnabled(true);
                        zoomInLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                    }
                });
    }
    /**
     * The purpose of this method is handle the onclick polygon
     * and to open the info card according to the clicked building.
     */
    public void setupPolygonClickListener() {
        mLayer.setOnFeatureClickListener(geoJsonFeature -> {
            if (geoJsonFeature != null) {
                Building building = mViewModel.getBuildingFromGeoJsonFeature(geoJsonFeature);
                onBuildingClick(building);
                String buildingCode = geoJsonFeature.getProperty("code");
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).showInfoCard(buildingCode);
            }
        });
    }

    private void onBuildingClick(Building building) {
        if (building.getAvailableFloors() != null) {
            setupFloorPickerAdapter(building);
        } else {
            mFloorPickerGv.setVisibility(View.GONE);
        }
    }

    public void setIndoorPaths(Place from, Place to) {
        SharedPreferences preferences = getActivity().getSharedPreferences(ClassConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mViewModel.parseWalkingPointList(AppDatabase.getInstance(getContext()), preferences, from, to);
    }

    public void drawOutdoorPaths(final List<DirectionWrapper> outdoorDirections) {
        DirectionsPolyLinesDrawer directionsPolyLinesDrawer = new DirectionsPolyLinesDrawer();
        mMapView.getMapAsync(googleMap -> directionsPolyLinesDrawer.drawOutdoorPath(outdoorDirections, googleMap));
    }

    public void setShuttlePaths(String polyline) {
        mMapView.getMapAsync(googleMap -> mViewModel.drawShuttlePath(googleMap, polyline));
    }

    private void setupRoomMarkers(){
        mViewModel.getListOfRoom().observe(getViewLifecycleOwner(), roomList -> {

            for (Marker marker : roomMarkers.getMarkers()) marker.remove();
            roomMarkers.getMarkers().clear();

            for(RoomModel roomModel: roomList){
                if(roomModel != null){
                    roomMarkers.addRoomToMap(roomModel,mViewModel,mMap,getContext());
                }
            }
        });
    }

    private void onRoomClick(String roomCode, String floorCode){
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).showPlaceSmallCard(mViewModel.getRoomByRoomCodeAndFloorCode(roomCode, floorCode));
    }

    private void setupPOIListListener() {
        mViewModel.getListOfPOI().observe(getViewLifecycleOwner(), priorityQueue -> {

            for (Marker marker : poiMarkers.getPointsOfInterest()) marker.remove();
            poiMarkers.getPointsOfInterest().clear();

            int position = 1;
            do {
                WalkingPoint polled = priorityQueue.poll();
                if (polled != null) {
                    poiMarkers.addPOItoMap(polled, position,mViewModel,mMap);
                }
                position++;
            } while (position <= 10);
        });
    }


    @Override
    public void onFloorPickerOnClick(int position, View view) {
        String floorSelected = currentFloorPickerAdapter.getFloorsAvailable().get(position);
        String buildingSelected = currentFloorPickerAdapter.getBuildingCode();
        mViewModel.setFloorPlan(buildingsGroundOverlays.get(currentFloorPickerAdapter.getBuildingCode()), buildingSelected, floorSelected, mMap);
        setupRoomMarkers();
        poiMarkers.setVisiblePOIMarkers(floorSelected, buildingSelected);
    }

    private void zoomInLocation(LatLng center) {
        float zoomLevel = 16.5f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoomLevel));
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

    public List<WalkingPoint> getWalkingPointList() {
        return mViewModel.getWalkingPointsList();
    }

    public void setDifferentInitialView(LatLng latLng){
        mViewModel.setInitialZoomLocation(latLng);
    }
}
package com.example.concordia_campus_guide.Fragments.LocationFragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.location.Location;
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

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.Adapters.FloorPickerAdapter;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Helper.CurrentLocation;
import com.example.concordia_campus_guide.Helper.CurrentLocationPermissionRequest;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.Interfaces.OnFloorPickerOnClickListener;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationFragment extends Fragment implements OnFloorPickerOnClickListener {

    MapView mMapView;

    private ImageButton myLocationBtn;
    private Button loyolaBtn;
    private Button sgwBtn;

    private GoogleMap mMap;
    private LocationFragmentViewModel mViewModel;
    private GeoJsonLayer mLayer;
    private GridView mFloorPickerGv;

    private CurrentLocation currentLocationClass;
    private CurrentLocationPermissionRequest currentLocationPermissionRequest;

    private HashMap<String, GroundOverlay> buildingsGroundOverlays;
    private FloorPickerAdapter currentFloorPickerAdapter;
    private List<Marker> poiMarkers;
    private List<Marker> roomMarkers;

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
        initComponents(rootView);
        mViewModel = new ViewModelProvider(getActivity(), new ViewModelFactory(this.getActivity().getApplication())).get(LocationFragmentViewModel.class);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        currentLocationPermissionRequest.getLocationPermission(this);
        setupClickListeners();
        currentLocationClass.updateLocationEvery5Seconds();


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
        poiMarkers = new ArrayList<>();
        roomMarkers = new ArrayList<>();


        currentLocationClass= new CurrentLocation(getContext());
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
        removeMarkersFromMap(poiMarkers);
        removeMarkersFromMap(roomMarkers);
        mFloorPickerGv.setVisibility(View.GONE);
    }

    private void setFirstLocationToDisplay() {
        setFirstLocationToDisplayOnSuccess();
        setFirstLocationToDisplayOnFailure();
    }

    @SuppressLint("MissingPermission")
    private void setFirstLocationToDisplayOnSuccess() {
        currentLocationClass.getFusedLocationProviderClient().getLastLocation()
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
        currentLocationClass.getFusedLocationProviderClient().getLastLocation().addOnFailureListener(getActivity(), e -> zoomInLocation(mViewModel.getInitialZoomLocation()));
    }

    private void setCurrentLocationBtn() {
        myLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloorPickerGv.setVisibility(View.GONE);
                popUpRequestPermission();
            }
        });
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
        setCurrentLocationBtn();
    }

    @SuppressLint("MissingPermission")
    private void popUpRequestPermission() {
        if (!ClassConstants.MY_LOCATION_PERMISSION_GRANTED) {
            currentLocationPermissionRequest.getLocationPermission(this);
            if (ClassConstants.MY_LOCATION_PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                initMap();
                return;
            }
        }

        if (ClassConstants.MY_LOCATION_PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            initMap();
        }
    }

    /**
     * The purpose of this method is to handle the "on click" of Loyala button
     */
    private void setupLoyolaBtnClickListener() {
        loyolaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(mViewModel.getLoyolaZoomLocation());
            }
        });
    }

    /**
     * The purpose of this method is to handle the "on click" of SGW button
     */
    private void setupSGWBtnClickListener() {
        sgwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomInLocation(mViewModel.getSGWZoomLocation());
            }
        });
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
        mViewModel.parseWalkingPointList(AppDatabase.getInstance(getContext()), (RoomModel) from, (RoomModel) to);
    }

    public void drawOutdoorPaths(final List<DirectionWrapper> outdoorDirections) {
        mMapView.getMapAsync(googleMap -> mViewModel.drawOutdoorPath(outdoorDirections, googleMap));
    }

    public void setShuttlePaths(String polyline) {
        mMapView.getMapAsync(googleMap -> mViewModel.drawShuttlePath(googleMap, polyline));
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

    private void setupRoomMarkers(){
        mViewModel.getListOfRoom().observe(getViewLifecycleOwner(), roomList -> {

            for (Marker marker : roomMarkers) marker.remove();
            roomMarkers.clear();

            for(RoomModel roomModel: roomList){
                if(roomModel != null){
                    addRoomToMap(roomModel);
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

            for (Marker marker : poiMarkers) marker.remove();
            poiMarkers.clear();

            int position = 1;
            do {
                WalkingPoint polled = priorityQueue.poll();
                if (polled != null) {
                    addPOItoMap(polled, position);
                }
                position++;
            } while (position <= 10);
        });
    }

    private void removeMarkersFromMap(List<Marker> markers){
        for(Marker marker: markers) marker.remove();
        markers.clear();
    }

    private void addRoomToMap(RoomModel room){
        BitmapDescriptor roomIcon = mViewModel.getCustomSizedIcon("class_markers/marker.png", getContext(), 30, 30);
        if(room != null){
            LatLng latLng = new LatLng(room.getCenterCoordinates().getLatitude(), room.getCenterCoordinates().getLongitude());

            String tag = ClassConstants.ROOM_TAG + "_" + room.getRoomCode() +"_" + room.getFloorCode();
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(roomIcon)
                    .alpha(0.2f)
                    .visible(true)
                    .title(room.getRoomCode());

            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(tag);

            roomMarkers.add(marker);
        }
    }

    private void addPOItoMap(WalkingPoint poi, int position) {
        if (poi != null) {

            LatLng latLng = new LatLng(poi.getCoordinate().getLongitude(), poi.getCoordinate().getLatitude());

            if (position == 1)
                zoomInLocation(latLng);

            String tag = ClassConstants.POI_TAG + "_" + poi.getPlaceCode() + "_" + poi.getFloorCode();
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(tag)
                    .position(latLng)
                    .icon(mViewModel.getCurrentPOIIcon());

            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(tag);

            poiMarkers.add(marker);
        }
    }

    private void setVisiblePOIMarkers(String floorSelected, String buildingSelected) {
        for (Marker marker : poiMarkers) {
            String floorCode = marker.getTag().toString().split("_")[2];
            marker.setVisible(floorCode.equalsIgnoreCase(buildingSelected + "-" + floorSelected));
        }
    }


    /**
     * set up related to UI for the map
     *
     * @param mMap
     */
    @SuppressLint("MissingPermission")
    private void uiSettingsForMap(GoogleMap mMap) {
        if (ClassConstants.MY_LOCATION_PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setIndoorEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
    }

    private void zoomInLocation(LatLng center) {
        float zoomLevel = 16.5f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoomLevel));
    }

    private void classRoomCoordinateTool(GoogleMap map) {
        map.setOnMapClickListener(latLng -> Log.i(ClassConstants.LOCATION_FRAGMENT_TAG, "\"coordinates\" : [" + latLng.longitude + ", " + latLng.latitude + "]"));
    }


    @Override
    public void onFloorPickerOnClick(int position, View view) {
        String floorSelected = currentFloorPickerAdapter.getFloorsAvailable().get(position);
        String buildingSelected = currentFloorPickerAdapter.getBuildingCode();
        mViewModel.setFloorPlan(buildingsGroundOverlays.get(currentFloorPickerAdapter.getBuildingCode()), buildingSelected, floorSelected, mMap);
        setupRoomMarkers();
        setVisiblePOIMarkers(floorSelected, buildingSelected);
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
//package com.example.concordia_campus_guide.Helper;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.res.Resources;
//import android.util.Log;
//
//import com.example.concordia_campus_guide.Activities.MainActivity;
//import com.example.concordia_campus_guide.ClassConstants;
//import com.example.concordia_campus_guide.Models.Building;
//import com.example.concordia_campus_guide.R;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.model.MapStyleOptions;
//
//import java.util.Map;
//
//
//public class MapSetup {
//
//    private GoogleMap mMap;
//    private Context context;
//    MapView mMapView;
//
//    public MapSetup(Context context){
//
//        this.context = context;
//    }
//
//
//    /**
//     * The purpose of this method is to init the map and fill it up with the required
//     * information to display them to the user
//     */
//    public void initMap() {
//        try {
//            MapsInitializer.initialize(context.getApplicationContext());
//        } catch (Exception e) {
//            Log.e(ClassConstants.LOCATION_FRAGMENT_TAG, e.getMessage());
//        }
//        mMapView.getMapAsync(googleMap -> {
//            setMapStyle(googleMap);
//            mMap = googleMap;
//            setupPolygons(mMap);
//            initFloorPlans();
//            uiSettingsForMap(mMap);
//            setFirstLocationToDisplay();
//        });
//    }
//
//    /**
//     * set up related to UI for the map
//     *
//     */
//    @SuppressLint("MissingPermission")
//    private void uiSettingsForMap() {
//        if (ClassConstants.MY_LOCATION_PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//        }
//        mMap.setIndoorEnabled(false);
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
//        mMap.getUiSettings().setTiltGesturesEnabled(true);
//        mMap.getUiSettings().setMapToolbarEnabled(false);
//        mMap.getUiSettings().setZoomControlsEnabled(false);
//    }
//
//
//    private void setMapStyle() {
//        try {
//            mMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            context, getMapStyle()));
//        } catch (Resources.NotFoundException e) {
//            Log.e("MAPACTIVITY", "Can't find style. Error: ", e);
//        }
//    }
//
//    /**
//     * @return return the map style
//     */
//    private int getMapStyle() {
//        return R.raw.mapstyle_retro;
//    }
//
//    /**
//     * The purpose of this method is to figure the style of the map to display
//     */
//    private void initFloorPlans() {
//        Map<String, Building> buildings = mViewModel.getBuildings();
//        for (Map.Entry<String, Building> entry : buildings.entrySet()) {
//            String key = entry.getKey();
//            Building value = entry.getValue();
//
//            if (value.getGroundOverlayOption() != null)
//                buildingsGroundOverlays.put(key, mMap.addGroundOverlay(buildings.get(key).getGroundOverlayOption()));
//        }
//    }
//
//
//
//
//}

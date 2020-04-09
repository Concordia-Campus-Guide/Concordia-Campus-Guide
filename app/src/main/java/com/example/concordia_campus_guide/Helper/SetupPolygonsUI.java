//package com.example.concordia_campus_guide.Helper;
//
//import android.util.Log;
//
//import com.example.concordia_campus_guide.Activities.MainActivity;
//import com.example.concordia_campus_guide.ClassConstants;
//import com.example.concordia_campus_guide.Models.Building;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.maps.android.geojson.GeoJsonLayer;
//
//public class SetupPolygonsUI {
//
//    private GeoJsonLayer mLayer;
//    /**
//     * The purpose of this method is display the polygon on the map and
//     * call the right method for onClick polygon or on click the marker.
//     *
//     * @param map is the map to be used in our application
//     */
//    private void setupPolygons(GoogleMap map) {
//        mLayer = mViewModel.loadPolygons(map, getContext());
//        mLayer.addLayerToMap();
//
//        setupPolygonClickListener();
//        setupBuildingMarkerClickListener(map);
//        setupZoomListener(map);
//        classRoomCoordinateTool(map);
//    }
//
//    private void setupZoomListener(final GoogleMap map) {
//        map.setOnCameraMoveListener(() -> {
//            if (map.getCameraPosition().zoom > 19) {
//                mLayer.removeLayerFromMap();
//            } else {
//                mLayer.addLayerToMap();
//            }
//        });
//    }
//    private void classRoomCoordinateTool(GoogleMap map) {
//        map.setOnMapClickListener(latLng -> Log.i(ClassConstants.LOCATION_FRAGMENT_TAG, "\"coordinates\" : [" + latLng.longitude + ", " + latLng.latitude + "]"));
//    }
//
//
//
//    /**
//     * The purpose of this method is handle the onclick marker
//     * and to open the info card according to the clicked building.
//     */
//    public boolean setupBuildingMarkerClickListener(GoogleMap map) {
//        map.setOnMarkerClickListener(marker -> {
//            if (marker.getTag() != null) {
//                if (marker.getTag() != null && (marker.getTag().toString().contains(ClassConstants.ROOM_TAG) || marker.getTag().toString().contains(ClassConstants.POI_TAG))) {
//                    String roomCode = marker.getTag().toString().split("_")[1];
//                    String floorCode = marker.getTag().toString().split("_")[2];
//                    onRoomClick(roomCode, floorCode);
//                }
//                else {
//                    Building building = mViewModel.getBuildingFromCode(marker.getTag().toString());
//                    String buildingCode = (marker.getTag()).toString();
//                    if (getActivity() instanceof MainActivity)
//                        ((MainActivity) getActivity()).showInfoCard(buildingCode);
//                    onBuildingClick(building);
//                }
//            }
//            return false;
//        });
//        return true;
//    }
//
//}

package com.example.concordia_campus_guide.Helper.UIHelpers;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Global.ApplicationState;
import com.example.concordia_campus_guide.Helper.CurrentLocation;
import com.example.concordia_campus_guide.Helper.POIIcon;
import com.example.concordia_campus_guide.Models.PoiType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class POIMarkers {
    private List<Marker> poiMarkers;

    public List<Marker> getPoiMarkers() {
        return poiMarkers;
    }

    public POIMarkers(){
        poiMarkers = new ArrayList<>();
    }

    public void addPOItoMap(WalkingPoint poi, int position, LocationFragmentViewModel locationFragmentViewModel,GoogleMap googleMap) {
        if (poi != null) {

            LatLng latLng = new LatLng(poi.getCoordinate().getLongitude(), poi.getCoordinate().getLatitude());

            if (position == 1)
                zoomInLocation(latLng,googleMap);

            String tag = ClassConstants.POI_TAG + "_" + poi.getPlaceCode() + "_" + poi.getFloorCode();
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(tag)
                    .position(latLng)
                    .icon(locationFragmentViewModel.getCurrentPOIIcon());

            Marker marker = googleMap.addMarker(markerOptions);
            marker.setTag(tag);

            poiMarkers.add(marker);
        }
    }

    public void setVisiblePOIMarkers(String floorSelected, String buildingSelected) {
        for (Marker marker : poiMarkers) {
            String floorCode = marker.getTag().toString().split("_")[2];
            marker.setVisible(floorCode.equalsIgnoreCase(buildingSelected + "-" + floorSelected));
        }
    }


    private void zoomInLocation(LatLng center, GoogleMap googleMap) {
        float zoomLevel = 16.5f;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoomLevel));
    }
}

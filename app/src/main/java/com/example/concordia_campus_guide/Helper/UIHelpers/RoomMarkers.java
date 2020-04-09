package com.example.concordia_campus_guide.Helper.UIHelpers;

import android.content.Context;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class RoomMarkers {

    private List<Marker> roomMarkers;

    public List<Marker> getRoomMarkers() {
        return roomMarkers;
    }

    public RoomMarkers(){
        roomMarkers = new ArrayList<>();
    }

    public void addRoomToMap(RoomModel room, LocationFragmentViewModel locationFragmentViewModel, GoogleMap googleMap, Context context){
        BitmapDescriptor roomIcon = locationFragmentViewModel.getCustomSizedIcon("class_markers/marker.png", context, 30, 30);
        if(room != null){
            LatLng latLng = new LatLng(room.getCenterCoordinates().getLatitude(), room.getCenterCoordinates().getLongitude());
            String tag = ClassConstants.ROOM_TAG + "_" + room.getRoomCode() +"_" + room.getFloorCode();
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(roomIcon)
                    .alpha(0.2f)
                    .visible(true)
                    .title(room.getRoomCode());

            Marker marker = googleMap.addMarker(markerOptions);
            marker.setTag(tag);

            roomMarkers.add(marker);
        }
    }



}

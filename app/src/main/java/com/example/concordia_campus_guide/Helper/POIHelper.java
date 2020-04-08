package com.example.concordia_campus_guide.Helper;

import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class POIHelper {



//    public void addPOItoMap(WalkingPoint poi, int position) {
//        if (poi != null) {
//
//            LatLng latLng = new LatLng(poi.getCoordinate().getLongitude(), poi.getCoordinate().getLatitude());
//
//            if (position == 1)
//                zoomInLocation(latLng);
//
//            String tag = POI_TAG + "_" + poi.getPlaceCode() + "_" + poi.getFloorCode();
//            MarkerOptions markerOptions = new MarkerOptions()
//                    .title(tag)
//                    .position(latLng)
//                    .icon(mViewModel.getCurrentPOIIcon());
//
//            Marker marker = mMap.addMarker(markerOptions);
//            marker.setTag(tag);
//
//            poiMarkers.add(marker);
//        }
//    }
}

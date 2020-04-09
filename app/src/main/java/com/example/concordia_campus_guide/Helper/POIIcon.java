package com.example.concordia_campus_guide.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PoiType;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POIIcon {

    private BitmapDescriptor currentPOIIcon;
    private static final Logger LOGGER = Logger.getLogger("POIIcon");

    public void setCurrentPOIIcon(@PoiType String poiType, Context context) {
        currentPOIIcon = getCustomSizedIcon("point_of_interest_icons/poi_" + poiType.toLowerCase() + ".png", context, 60, 60);
    }

    public BitmapDescriptor getCurrentPOIIcon() {
        return currentPOIIcon;
    }

    public BitmapDescriptor getCustomSizedIcon(String filename, Context context, int height, int width) {
        InputStream deckFile = null;
        BitmapDescriptor smallMarkerIcon = null;
        try {
            deckFile = context.getAssets().open(filename);
            Bitmap b = BitmapFactory.decodeStream(deckFile);
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
            deckFile.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return smallMarkerIcon;
    }

    public PriorityQueue<WalkingPoint> getPOIinOrder(AppDatabase appDatabase, List<WalkingPoint> allPOI, CurrentLocation currentLocation) {
        PriorityQueue<WalkingPoint> orderedList = new PriorityQueue<>((WalkingPoint p1, WalkingPoint p2) -> {
            Coordinates currentCoordinates;

            if (currentLocation == null) {

                //This building has inverted lat/lng in order to us th geojsons.
                Building hallBuilding = appDatabase.buildingDao().getBuildingByBuildingCode("H");
                Double currentLat = hallBuilding != null ? hallBuilding.getCenterCoordinates().getLatitude() : 0;
                Double currentLng = hallBuilding != null ? hallBuilding.getCenterCoordinates().getLongitude() : 0;

                //Current location should be inversed: lat->lng and lng->lat
                currentCoordinates = new Coordinates(currentLng, currentLat);
            } else {
                currentCoordinates = new Coordinates(currentLocation.getCurrentLocation().getLatitude(), currentLocation.getCurrentLocation().getLongitude());
            }

            double distanceFromP1 = p1.getCoordinate().getEuclideanDistanceFrom(currentCoordinates);
            double distanceFromP2 = p2.getCoordinate().getEuclideanDistanceFrom(currentCoordinates);

            //Compare walking points: If p1 is closer to the current location than p2, it will have a higher position in priority queue
            if (distanceFromP1 < distanceFromP2) return -1;
            else if (distanceFromP1 > distanceFromP2) return 1;
            return 0;
        });
        orderedList.addAll(allPOI);
        return orderedList;
    }

}

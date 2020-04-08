package com.example.concordia_campus_guide.Fragments.LocationFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Global.ApplicationState;
import com.example.concordia_campus_guide.Helper.DrawPolygons;
import com.example.concordia_campus_guide.Helper.FloorPlan;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PoiType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Double.parseDouble;

public class LocationFragmentViewModel extends ViewModel {

    private Map<String, Building> buildings = new HashMap<>();
    private AppDatabase appDatabase;
    private MutableLiveData<PriorityQueue<WalkingPoint>> poiList = new MutableLiveData<>();
    private MutableLiveData<List<RoomModel>> roomList = new MutableLiveData<>();
    private BitmapDescriptor currentPOIIcon;
    private Location currentLocation;

    private FloorPlan floorPlan;
    private DrawPolygons drawPolygons;

    /**
     * @return return the map style
     */
    public int getMapStyle() {
        return R.raw.mapstyle_retro;
    }

    //EV centerCoordinates
    private LatLng initialZoomLocation = ClassConstants.initialZoomLocation;

    public static final Logger LOGGER = Logger.getLogger("LocationFragmentViewModel");

    private HashMap<String, List<WalkingPoint>> walkingPointsMap = new HashMap<>();
    private List<WalkingPoint> walkingPoints;

    public LocationFragmentViewModel(AppDatabase appDb) {
        this.appDatabase = appDb;
    }

    /**
     * The purpose of this method to load the overlay polygon on the map.
     *
     * @param map                is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return It will return the layer to the LocationFragmentView to display on the map
     */
    public GeoJsonLayer loadPolygons(GoogleMap map, Context applicationContext) {
        drawPolygons = new DrawPolygons();
        GeoJsonLayer layer = initLayer(map, applicationContext);
        drawPolygons.loadPolygons(layer,map,applicationContext,buildings);
        return layer;
    }

    /**
     * The purpose of this method is to initiate the layer
     *
     * @param map                is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return the initiated layer or it will throw an exception if it didn't find the
     * GeoJson File
     */
    private GeoJsonLayer initLayer(GoogleMap map, Context applicationContext) {
        GeoJsonLayer layer = null;

        try {
            JSONObject geoJsonLayer = ApplicationState.getInstance(applicationContext).getBuildings().getGeoJson();
            layer = new GeoJsonLayer(map, geoJsonLayer);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return layer;
    }


    public Building getBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        drawPolygons = new DrawPolygons();
        return drawPolygons.getBuildingFromGeoJsonFeature(feature);
    }

    /**
     * @param buildingCode it represents which building we will be covering
     * @return Int of drawable resource's bitmap representation
     */
    public void setFloorPlan(GroundOverlay groundOverlay, String buildingCode, String floor, GoogleMap mMap) {
        floorPlan = new FloorPlan(appDatabase);
        floorPlan.setFloorPlan(groundOverlay,buildingCode,floor,mMap,walkingPointsMap);
    }

    public Building getBuildingFromCode(String buildingCode) {
        return buildings.get(buildingCode);
    }

    public Map<String, Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(Map<String, Building> buildings) {
        this.buildings = buildings;
    }

    public LatLng getInitialZoomLocation() {
        return initialZoomLocation;
    }

    public void setInitialZoomLocation(LatLng latLng){
        initialZoomLocation = latLng;
    }

    public LatLng getLoyolaZoomLocation() {
        return buildings.get(ClassConstants.loyolaCenterBuildingLabel).getCenterCoordinatesLatLng();
    }

    public LatLng getSGWZoomLocation() {
        return buildings.get(ClassConstants.sgwCenterBuildingLabel).getCenterCoordinatesLatLng();
    }


    public void setListOfPOI(@PoiType String poiType, Context context) {
        List<WalkingPoint> allPOI = appDatabase.walkingPointDao().getAllPointsForPointType(poiType);
        setCurrentPOIIcon(poiType, context);
        this.poiList.postValue(getPOIinOrder(allPOI));
    }

    private PriorityQueue<WalkingPoint> getPOIinOrder(List<WalkingPoint> allPOI) {
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
                currentCoordinates = new Coordinates(currentLocation.getLatitude(), currentLocation.getLongitude());
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


    LiveData<List<RoomModel>> getListOfRoom() {
        return floorPlan.getListOfRoom();
    }

    LiveData<PriorityQueue<WalkingPoint>> getListOfPOI() {
        return poiList;
    }

    private void setCurrentPOIIcon(@PoiType String poiType, Context context) {
        currentPOIIcon = getCustomSizedIcon("point_of_interest_icons/poi_" + poiType.toLowerCase() + ".png", context, 60, 60);
    }

    BitmapDescriptor getCurrentPOIIcon() {
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

    public void drawOutdoorPath(List<DirectionWrapper> outdoorDirections, GoogleMap map) {
        for (DirectionWrapper directionWrapper : outdoorDirections) {
            int color = 0;
            if (directionWrapper.getTransitDetails() != null) {
                color = Color.parseColor(directionWrapper.getTransitDetails().line.color);
            }
            PolylineOptions polylineOptions = stylePolyLine(directionWrapper.getDirection().getTransportType(), color);
            List<com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.LatLng> polyline = directionWrapper.getPolyline().decodePath();

            for (int i = 0; i < polyline.size(); i++) {
                polylineOptions.add(new LatLng(polyline.get(i).lat, polyline.get(i).lng));
            }
            map.addPolyline(polylineOptions);
        }
    }

    public PolylineOptions stylePolyLine(String type, int color) {
        PolylineOptions polylineOptions = new PolylineOptions().width(20);
        if (type.equals(ClassConstants.WALKING)) {
            polylineOptions.pattern(ClassConstants.WALK_PATTERN);
        }
        if (color != 0) {
            polylineOptions.color(color);
        } else {
            polylineOptions.color(Color.rgb(147, 35, 57));
        }
        return polylineOptions;
    }

    public RoomModel getRoomByRoomCodeAndFloorCode(String roomCode, String floorCode){
        return appDatabase.roomDao().getRoomByRoomCodeAndFloorCode(roomCode, floorCode);
    }

    public List<WalkingPoint> getWalkingPointsList() {
        return walkingPoints;
    }

    public void parseWalkingPointList(AppDatabase appDatabase, RoomModel from, RoomModel to) {
        PathFinder pf = new PathFinder(appDatabase, from, to);
        walkingPoints = pf.getPathToDestination();

        List<WalkingPoint> floorWalkingPointList;
        for (WalkingPoint wp : walkingPoints) {
            floorWalkingPointList = walkingPointsMap.getOrDefault(wp.getFloorCode(), new ArrayList<WalkingPoint>());
            floorWalkingPointList.add(wp);
            walkingPointsMap.put(wp.getFloorCode(), floorWalkingPointList);
        }
    }

    public void drawShuttlePath(GoogleMap mMap, String polyline) {
        List<LatLng> route = PolyUtil.decode(polyline);
        mMap.addPolyline(new PolylineOptions().addAll(route).color(R.color.colorAppTheme).width(20));
    }
}
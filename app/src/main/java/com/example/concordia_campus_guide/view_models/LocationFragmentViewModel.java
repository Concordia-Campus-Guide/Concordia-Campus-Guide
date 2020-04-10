package com.example.concordia_campus_guide.view_models;

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

import com.example.concordia_campus_guide.adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.global.ApplicationState;
import com.example.concordia_campus_guide.helper.PathFinder;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.PoiType;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;
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

    private GeoJsonLayer floorLayer;
    private Map<String, Building> buildings = new HashMap<>();
    private AppDatabase appDatabase;
    private MutableLiveData<PriorityQueue<WalkingPoint>> poiList = new MutableLiveData<>();
    private MutableLiveData<List<RoomModel>> roomList = new MutableLiveData<>();
    private BitmapDescriptor currentPOIIcon;
    private Location currentLocation;

    //EV centerCoordinates
    private LatLng initialZoomLocation = ClassConstants.INITIAL_ZOOM_LOCATION;

    public static final Logger LOGGER = Logger.getLogger("LocationFragmentViewModel");
    public static final String FLOORS_AVAILABLE = "floorsAvailable";

    private Polyline currentlyDisplayedLine;
    private HashMap<String, List<WalkingPoint>> walkingPointsMap = new HashMap<>();
    private List<WalkingPoint> walkingPoints;

    public LocationFragmentViewModel(AppDatabase appDb) {
        this.appDatabase = appDb;
    }

    /**
     * @return return the map style
     */
    public int getMapStyle() {
        return R.raw.mapstyle_retro;
    }

    /**
     * The purpose of this method to load the overlay polygon on the map.
     *
     * @param map                is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return It will return the layer to the LocationFragmentView to display on the map
     */
    public GeoJsonLayer loadPolygons(GoogleMap map, Context applicationContext) {
        GeoJsonLayer layer = initLayer(map, applicationContext);
        setPolygonStyle(layer, map);
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


    public GeoJsonLayer initMarkersLayer(GoogleMap map, JSONObject jsonFile) {
        GeoJsonLayer layer = null;
        try {
            layer = new GeoJsonLayer(map, jsonFile);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return layer;
    }

    public void setBuildingGroundOverlayOptions(Building building) {
        building.setGroundOverlayOption(new GroundOverlayOptions()
                .position(new LatLng(building.getCenterCoordinates().getLatitude(), building.getCenterCoordinates().getLongitude()), building.getWidth(), building.getHeight())
                .image(BitmapDescriptorFactory.fromAsset("buildings_floorplans/" + building.getBuildingCode().toLowerCase() + "_" + building.getAvailableFloors().get(building.getAvailableFloors().size() - 1).toLowerCase() + ".png"))
                .bearing(building.getBearing()));
    }

    public Building getBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        Coordinates centerPos = getCenterPositionBuildingFromGeoJsonFeature(feature);

        List<String> floorsAvailable = getFloorsFromBuildingFromGeoJsonFeature(feature);
        float buildingWidth = (feature.getProperty("width") != null) ? Float.parseFloat(feature.getProperty("width")) : -1;
        float buildingHeight = (feature.getProperty("height") != null) ? Float.parseFloat(feature.getProperty("height")) : -1;
        float buildingBearing = (feature.getProperty("bearing") != null) ? Float.parseFloat(feature.getProperty("bearing")) : -1;
        String buildingCode = feature.getProperty("code");
        return new Building(centerPos, floorsAvailable, buildingWidth, buildingHeight, buildingBearing, null, buildingCode, null, null, null, null, null);
    }

    public Coordinates getCenterPositionBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        String[] coordinatesString = feature.getProperty("center").split(", ");
        return new Coordinates(Double.parseDouble(coordinatesString[1]), Double.parseDouble(coordinatesString[0]));
    }

    public List<String> getFloorsFromBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        List<String> floorsAvailable = null;

        if (feature.getProperty(FLOORS_AVAILABLE) != null)
            floorsAvailable = Arrays.asList(feature.getProperty(FLOORS_AVAILABLE).split(","));

        return floorsAvailable;
    }

    /**
     * @param layer the GeoJson layer containing features to style.
     * @param map   the google map where layer will be displayed and markers will be added.
     */
    public void setPolygonStyle(GeoJsonLayer layer, GoogleMap map) {
        for (GeoJsonFeature feature : layer.getFeatures()) {
            feature.setPolygonStyle(getPolygonStyle());
            Building building = getBuildingFromGeoJsonFeature(feature);
            buildings.put(feature.getProperty("code"), building);
            if (feature.getProperty(FLOORS_AVAILABLE) != null)
                setBuildingGroundOverlayOptions(building);

            String[] coordinates = feature.getProperty("center").split(", ");
            LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
            addBuildingMarker(map, centerPos, feature.getProperty("code"));
        }
    }

    /**
     * The purpose of this method is to add a marker on the specified building.
     *
     * @param map           is the map used in our application.
     * @param centerPos     is the latitude and longitude of the building's center
     * @param buildingLabel is the Building on which the method will add a marker
     */
    public void addBuildingMarker(GoogleMap map, LatLng centerPos, String buildingLabel) {
        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(centerPos)
                        .icon(BitmapDescriptorFactory.fromBitmap(createBitmapMarkerIcon(buildingLabel)))
                        .flat(true)
                        .anchor(0.5f, 0.5f)
                        .alpha(0.90f)
                        .title(buildingLabel)
        );
        marker.setTag(buildingLabel);
    }

    /**
     * The purpose of this method is to create the marker
     *
     * @param label the label of the building
     * @return it will BitmapDescriptor object to use it as an icon for the marker on the map.
     */
    public Bitmap createBitmapMarkerIcon(String label){
        int width = 160;
        int height = 130;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(100.f);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setShadowLayer(6, 0, 0, Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(label, width/2f, height/1.2f, paint);

        return bitmap;
    }

    /**
     * The purpose of this method is the polygons style after setting their
     * FillColor, StrokeColor and StrokeWidth
     *
     * @return it returns the polygon style.
     */
    public GeoJsonPolygonStyle getPolygonStyle() {
        GeoJsonPolygonStyle geoJsonPolygonStyle = new GeoJsonPolygonStyle();
        geoJsonPolygonStyle.setFillColor(Color.argb(51, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeColor(Color.argb(255, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeWidth(6);
        return geoJsonPolygonStyle;
    }

    /**
     * @param buildingCode it represents which building we will be covering
     * @return Int of drawable resource's bitmap representation
     */
    public void setFloorPlan(GroundOverlay groundOverlay, String buildingCode, String floor, GoogleMap mMap) {
        String fileName = buildingCode.toLowerCase()+"_"+floor.toLowerCase();
        groundOverlay.setImage(BitmapDescriptorFactory.fromAsset("buildings_floorplans/"+fileName+".png"));
        setFloorMarkers(buildingCode, floor, mMap);
    }

    public void setFloorMarkers(String buildingCode, String floor, GoogleMap mMap) {
        if (floorLayer != null) {
            floorLayer.removeLayerFromMap();
        }
        setListOfRooms(buildingCode+"-"+floor);
        if (currentlyDisplayedLine != null) {
            currentlyDisplayedLine.remove();
        }
        PolylineOptions displayedPolylineOption = getFloorPolylines(buildingCode + "-" + floor);
        currentlyDisplayedLine = mMap.addPolyline(displayedPolylineOption);
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
        return buildings.get(ClassConstants.LOYOLA_CENTER_BUILDING_LABEL).getCenterCoordinatesLatLng();
    }

    public LatLng getSGWZoomLocation() {
        return buildings.get(ClassConstants.SGW_CENTER_BUILDING_LABEL).getCenterCoordinatesLatLng();
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

    public void setListOfRooms(String floorCode) {
        List<RoomModel> allRoomsOnFloor = appDatabase.roomDao().getAllRoomsByFloorCode(floorCode);
        this.roomList.postValue(allRoomsOnFloor);
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


    public LiveData<List<RoomModel>> getListOfRoom() {
        return roomList;
    }

    public LiveData<PriorityQueue<WalkingPoint>> getListOfPOI() {
        return poiList;
    }

    private void setCurrentPOIIcon(@PoiType String poiType, Context context) {
        currentPOIIcon = getCustomSizedIcon("point_of_interest_icons/poi_" + poiType.toLowerCase() + ".png", context, 60, 60);
    }

    public BitmapDescriptor getCurrentPOIIcon() {
        return currentPOIIcon;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
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

    public PolylineOptions getFloorPolylines(String floorCode) {
        // previously drawindoorpaths
        List<WalkingPoint> floorWalkingPoints = walkingPointsMap.get(floorCode);
        PolylineOptions option = new PolylineOptions();
        if (floorWalkingPoints == null) {
            return option;
        }
        for (int i = 0; i < floorWalkingPoints.size() - 1; i++) {
            LatLng point1 = floorWalkingPoints.get(i).getCoordinate().getLatLng();
            LatLng point2 = floorWalkingPoints.get(i + 1).getCoordinate().getLatLng();
            option.add(point1, point2);
        }
        return option
                .width(10)
                .pattern(ClassConstants.WALK_PATTERN)
                .color(Color.rgb(147, 35, 57))
                .visible(true);
    }

    public void drawOutdoorPath(List<DirectionWrapper> outdoorDirections, GoogleMap map) {
        for (DirectionWrapper directionWrapper : outdoorDirections) {
            int color = 0;
            if (directionWrapper.getTransitDetails() != null) {
                color = Color.parseColor(directionWrapper.getTransitDetails().line.color);
            }
            PolylineOptions polylineOptions = stylePolyLine(directionWrapper.getDirection().getTransportType(), color);
            List<com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.LatLng> polyline = directionWrapper.getPolyline().decodePath();

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

    public void drawShuttlePath(GoogleMap mMap, String polyline) {
        List<LatLng> route = PolyUtil.decode(polyline);
        mMap.addPolyline(new PolylineOptions().addAll(route).color(R.color.colorAppTheme).width(20));
    }
}
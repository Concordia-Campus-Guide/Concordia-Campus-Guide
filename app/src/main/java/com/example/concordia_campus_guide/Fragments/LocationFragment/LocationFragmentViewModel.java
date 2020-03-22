package com.example.concordia_campus_guide.Fragments.LocationFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.concordia_campus_guide.Global.ApplicationState;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Double.parseDouble;

public class LocationFragmentViewModel extends ViewModel {
    private GeoJsonLayer floorLayer;
    private HashMap<String, Building> buildings = new HashMap<>();
    private PolylineOptions displayedPolylineOption;
    private Polyline currentlyDisplayedLine;
    private HashMap<String, List<WalkingPoint>> walkingPointsMap = new HashMap<>();
    private List<WalkingPoint> walkingPoints;

    //Polyline styling
    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYLINE = Arrays.asList(GAP, DASH);

    /**
     * @return return the map style
     */
    public int getMapStyle(){
        return R.raw.mapstyle_retro;
    }

    /**
     * The purpose of this method to load the overlay polygon on the map.
     * @param map is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return It will return the layer to the LocationFragmentView to display on the map
     */
    public GeoJsonLayer loadPolygons(GoogleMap map, Context applicationContext){
        GeoJsonLayer layer = initLayer(map, applicationContext);
        setPolygonStyle(layer,map, applicationContext);
        return  layer;
    }

    /**
     * The purpose of this method is to initiate the layer
     * @param map is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return the initiated layer or it will throw an exception if it didn't find the
     *  GeoJson File
     */
    private GeoJsonLayer initLayer(GoogleMap map, Context applicationContext){
        GeoJsonLayer layer = null;

        try {
            JSONObject geoJsonLayer = ApplicationState.getInstance(applicationContext).getBuildings().getGeoJson();
            layer = new GeoJsonLayer(map, geoJsonLayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layer;
    }


    public GeoJsonLayer initMarkersLayer(GoogleMap map, JSONObject jsonFile){
        GeoJsonLayer layer = null;
        try {
            layer = new GeoJsonLayer(map, jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layer;
    }

    public void setBuildingGroundOverlayOptions(Building building){
        building.setGroundOverlayOption(new GroundOverlayOptions()
                .position(new LatLng(building.getCenterCoordinates().getLatitude(), building.getCenterCoordinates().getLongitude()), building.getWidth(), building.getHeight())
                .image(BitmapDescriptorFactory.fromAsset("buildings_floorplans/"+building.getBuildingCode().toLowerCase()+"_"+building.getAvailableFloors().get(building.getAvailableFloors().size()-1).toLowerCase()+".png"))
                .bearing(building.getBearing()));
    }

    public Building getBuildingFromGeoJsonFeature(GeoJsonFeature feature){
        Coordinates centerPos = getCenterPositionBuildingFromGeoJsonFeature(feature);

        List<String> floorsAvailable = getFloorsFromBuildingFromGeoJsonFeature(feature);
        float building_width = (feature.getProperty("width") != null)? Float.parseFloat(feature.getProperty("width")): -1;
        float building_height  = (feature.getProperty("height") != null)? Float.parseFloat(feature.getProperty("height")) : -1;
        float building_bearing = (feature.getProperty("bearing") != null)? Float.parseFloat(feature.getProperty("bearing")) : -1;
        String building_code = feature.getProperty("code");
        return new Building(centerPos, floorsAvailable, building_width, building_height, building_bearing, null, building_code, null, null, null, null, null);
    }

    public Coordinates getCenterPositionBuildingFromGeoJsonFeature(GeoJsonFeature feature){
        String[] coordinatesString = feature.getProperty("center").split(", ");
        Coordinates coordinatesDouble = new Coordinates(Double.parseDouble(coordinatesString[1]), Double.parseDouble(coordinatesString[0]));
        return coordinatesDouble;
    }
    public List<String> getFloorsFromBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        List<String> floorsAvailable = null;

        if (feature.getProperty("floorsAvailable") != null)
            floorsAvailable = Arrays.asList(feature.getProperty("floorsAvailable").split(","));

        return floorsAvailable;
    }
    /**
     * @param layer the GeoJson layer containing features to style.
     * @param map the google map where layer will be displayed and markers will be added.
     */
    public void setPolygonStyle(GeoJsonLayer layer, GoogleMap map, Context context){
        for (GeoJsonFeature feature : layer.getFeatures()){
            feature.setPolygonStyle(getPolygonStyle());
            Building building = getBuildingFromGeoJsonFeature(feature);
            buildings.put(feature.getProperty("code"), building);
            if(feature.getProperty("floorsAvailable") != null)
                setBuildingGroundOverlayOptions(building);

            String[] coordinates = feature.getProperty("center").split(", ");
            LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
            addBuildingMarker(map, centerPos, feature.getProperty("code"), context);
        }
    }

    /**
     * The purpose of this method is to add a marker on the specified building.
     * @param map is the map used in our application.
     * @param centerPos is the latitude and longitude of the building's center
     * @param buildingLabel is the Building on which the method will add a marker
     */
    public void addBuildingMarker(GoogleMap map, LatLng centerPos, String buildingLabel, Context context) {
        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(centerPos)
                        .icon(styleMarker(buildingLabel,context))
                        .flat(true)
                        .anchor(0.5f,0.5f)
                        .alpha(0.90f)
                        //This line should be included whenever we test the UI for the marker:
                        .title(buildingLabel)
        );
        marker.setTag(buildingLabel);
    }

    /**
     * The purpose of this method is setup the style of the marker.
     * @param buildingLabel the label of the building
     * @param context the context of the LocationFragment
     * @return it will BitmapDescriptor object to use it as an icon for the marker on the map.
     */
    public BitmapDescriptor styleMarker(String buildingLabel, Context context) {
        int height = 150;
        int width = 150;
        InputStream deckFile = null;
        try {
            deckFile = context.getAssets().open("BuildingLabels/" + buildingLabel.toLowerCase()+".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap b = BitmapFactory.decodeStream(deckFile);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
        return  smallMarkerIcon;
    }

    /**
     * The purpose of this method is the polygons style after setting their
     * FillColor, StrokeColor and StrokeWidth
     * @return it returns the polygon style.
     */
    public GeoJsonPolygonStyle getPolygonStyle() {
        GeoJsonPolygonStyle geoJsonPolygonStyle = new GeoJsonPolygonStyle();
        geoJsonPolygonStyle.setFillColor(Color.argb(51, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeColor(Color.argb(255, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeWidth(6);
        return geoJsonPolygonStyle;
    }

    private void getPointStyle(GeoJsonLayer layer) {
        GeoJsonPointStyle geoJsonPointStyle = new GeoJsonPointStyle();
        geoJsonPointStyle.setVisible(true);
        geoJsonPointStyle.setAlpha(0.05f);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            feature.setPointStyle(geoJsonPointStyle);
        }
    }

    /**
     * @param buildingCode it represents which building we will be covering
     * @return Int of drawable resource's bitmap representation
     */
    public void setFloorPlan(GroundOverlay groundOverlay, String buildingCode, String floor, Context context, GoogleMap mMap) {
        String fileName = buildingCode.toLowerCase()+"_"+floor.toLowerCase();
        groundOverlay.setImage(BitmapDescriptorFactory.fromAsset("buildings_floorplans/"+fileName+".png"));
        if (floorLayer != null) {
            floorLayer.removeLayerFromMap();
        }
        JSONObject geoJson = ApplicationState.getInstance(context).getRooms().getGeoJson(buildingCode+"-"+floor);
        floorLayer = initMarkersLayer(mMap, geoJson);
        getPointStyle(floorLayer);
        floorLayer.addLayerToMap();
        if (currentlyDisplayedLine != null) {
             currentlyDisplayedLine.remove();
        }
        displayedPolylineOption = drawPath(buildingCode + "-" + floor);
        currentlyDisplayedLine = mMap.addPolyline(displayedPolylineOption);
    }


    public Building getBuildingFromeCode(String buildingCode) {
        return buildings.get(buildingCode);
    }
    public HashMap<String, Building> getBuildings() {
        return buildings;
    }
    public void setBuildings(HashMap<String, Building> buildings) {
        this.buildings = buildings;
    }
    public LatLng getInitialZoomLocation(){
                return buildings.get("EV").getCenterCoordinatesLatLng();
    }
    public LatLng getLoyolaZoomLocation(){
        return buildings.get("VL").getCenterCoordinatesLatLng();
    }
    public LatLng getSGWZoomLocation(){
        return buildings.get("H").getCenterCoordinatesLatLng();
    }

    public void parseWalkingPointList(Context context, RoomModel from, RoomModel to) {
        PathFinder pf = new PathFinder(context ,from, to);
        walkingPoints = pf.getPathToDestination();

        List<WalkingPoint> floorWalkingPointList;
        for(WalkingPoint wp: walkingPoints) {
            floorWalkingPointList = walkingPointsMap.getOrDefault(wp.getFloorCode(), new ArrayList<WalkingPoint>());
            floorWalkingPointList.add(wp);
            walkingPointsMap.put(wp.getFloorCode(), floorWalkingPointList);
        }
    }

    public PolylineOptions drawPath(String floorCode) {
        List<WalkingPoint> floorWalkingPoints = walkingPointsMap.get(floorCode);
        PolylineOptions option = new PolylineOptions();
        if(floorWalkingPoints == null) {
            return option;
        }
        for(int i=0; i < floorWalkingPoints.size() - 1; i++) {
            LatLng point1 = floorWalkingPoints.get(i).getCoordinate().getLatLng();
            LatLng point2 = floorWalkingPoints.get(i + 1).getCoordinate().getLatLng();
            option.add(point1, point2);
        }
        return option
                .width(10)
                .pattern(PATTERN_POLYLINE)
                .color(Color.rgb(147,35, 57))
                .visible(true);
    }
}
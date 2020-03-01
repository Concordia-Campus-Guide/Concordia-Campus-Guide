package com.example.concordia_campus_guide.LocationFragment;
import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.BuildingCode;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.Double.parseDouble;

public class LocationFragmentViewModel extends ViewModel {
    private HashMap<String, Building> buildings = new HashMap<>();
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
        setPolygonStyle(layer,map);
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
            layer = new GeoJsonLayer(map, R.raw.buildingcoordinates, applicationContext);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return layer;
    }

    public void setBuildingGroundOverlayOptions(Building building){
        building.setGroundOverlayOption(new GroundOverlayOptions()
                .position(building.getCenterCoordinates(), building.getWidth(), building.getHeight())
                .image(BitmapDescriptorFactory.fromAsset("buildings_floorplans/"+building.getBuildingCode().toLowerCase()+"_"+building.getAvailableFloors()[building.getAvailableFloors().length-1].toLowerCase()+".png"))
                .bearing(building.getBearing()));
    }

    public Building getBuildingFromGeoJsonFeature(GeoJsonFeature feature){
        LatLng centerPos = getCenterPositionBuildingFromGeoJsonFeature(feature);

        String[] floorsAvailable = getFloorsFromBuildingFromGeoJsonFeature(feature);

        float building_width = -1;

        if(feature.getProperty("width") != null)
            building_width = Float.parseFloat(feature.getProperty("width"));

        float building_height = -1;

        if(feature.getProperty("height") != null)
            building_height  = Float.parseFloat(feature.getProperty("height"));

        float building_bearing = -1;
        if (feature.getProperty("bearing") != null)
            building_bearing = Float.parseFloat(feature.getProperty("bearing"));

        String building_code = feature.getProperty("code");

        return new Building(centerPos,floorsAvailable, building_code, building_width, building_height, building_bearing);
    }

    public LatLng getCenterPositionBuildingFromGeoJsonFeature(GeoJsonFeature feature){
        String[] coordinates = feature.getProperty("center").split(", ");
        LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
        return centerPos;
    }
    public String[] getFloorsFromBuildingFromGeoJsonFeature(GeoJsonFeature feature){
        String[] floorsAvailable = null;

        if(feature.getProperty("floorsAvailable") != null)
            floorsAvailable = feature.getProperty("floorsAvailable").split(",");

        return floorsAvailable;
    }


    /**
     * @param layer the GeoJson layer containing features to style.
     * @param map the google map where layer will be displayed and markers will be added.
     */
    private void setPolygonStyle(GeoJsonLayer layer, GoogleMap map){
        for (GeoJsonFeature feature : layer.getFeatures()){
            feature.setPolygonStyle(getPolygonStyle());
            Building building = getBuildingFromGeoJsonFeature(feature);
            buildings.put(feature.getProperty("code"), building);
            if(feature.getProperty("floorsAvailable") != null)
                setBuildingGroundOverlayOptions(building);

            String[] coordinates = feature.getProperty("center").split(", ");
            LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
            addBuildingMarker(map, centerPos, Enum.valueOf(BuildingCode.class, feature.getProperty("code")));
        }
    }

    /**
     * The purpose of this method is to add a marker on the specified building.
     * @param map is the map used in our application.
     * @param centerPos is the latitude and longitude of the building's center
     * @param buildingCode is the Building on which the method will add a marker
     */
    private void addBuildingMarker(GoogleMap map, LatLng centerPos, BuildingCode buildingCode) {
        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(centerPos)
                        .icon(BitmapDescriptorFactory.fromResource(getIcon(buildingCode)))
                        .flat(true)
                        .anchor(0.5f,0.5f)
                        .alpha(0.90f)
                        .title(buildingCode.toString())
        );
        marker.setTag(buildingCode);
    }

    /**
     * Add classroom markers to map
     * @param map
     * @param centerPos
     * @param classNumber
     */
    private void addClassroomMarker(GoogleMap map, LatLng centerPos, String classNumber) {
        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(centerPos)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.h))
                        .flat(true)
                        .anchor(0.5f,0.5f)
                        .alpha(0.0f)
        );
        marker.setTag(classNumber);
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
    /**
     * @param buildingCode it represents which building we will be covering
     * @return Int of drawable resource's bitmap representation
     */
    public int getIcon(BuildingCode buildingCode){
        switch (buildingCode) {
            case H:
                return R.drawable.h;
            case LB:
                return R.drawable.lb;
            case CJ:
                return R.drawable.cj;
            case AD:
                return R.drawable.ad;
            case CC:
                return R.drawable.cc;
            case EV:
                return R.drawable.ev;
            case FG:
                return R.drawable.fg;
            case GM:
                return R.drawable.gm;
            case MB:
                return R.drawable.mb;
            case FB:
                return R.drawable.fb;
            case SP:
                return R.drawable.sp;
            case BB:
                return R.drawable.bb;
            case FC:
                return R.drawable.fc;
            case DO:
                return R.drawable.dome;
            case GE:
                return R.drawable.ge;
            case HA:
                return R.drawable.ha;
            case HB:
                return R.drawable.hb;
            case HC:
                return R.drawable.hc;
            case BH:
                return R.drawable.bh;
            case JR:
                return R.drawable.jr;
            case PC:
                return R.drawable.pc;
            case PS:
                return R.drawable.ps;
            case PT:
                return R.drawable.pt;
            case PY:
                return R.drawable.py;
            case QA:
                return R.drawable.qa;
            case RA:
                return R.drawable.ra;
            case RF:
                return R.drawable.rf;
            case SC:
                return R.drawable.sc;
            case SH:
                return R.drawable.sh;
            case SI:
                return R.drawable.si;
            case TA:
                return R.drawable.ta;
            case VE:
                return R.drawable.ve;
            case VL:
                return R.drawable.vl;
            default:
                return -1;
        }
    }

    public void setFloorPlan(GroundOverlay groundOverlay, String buildingCode, String floor, Context context) {
        groundOverlay.setImage(BitmapDescriptorFactory.fromAsset("buildings_floorplans/"+buildingCode.toLowerCase()+"_"+floor.toLowerCase()+".png"));
    }

    public Building getBuildingFromeCode(String buildingCode) {
        return buildings.get(buildingCode);
    }
    public HashMap<String, Building> getBuildings() {
        return buildings;
    }
}
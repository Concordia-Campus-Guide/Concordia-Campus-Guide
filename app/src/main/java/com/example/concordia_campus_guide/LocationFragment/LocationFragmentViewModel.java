package com.example.concordia_campus_guide.LocationFragment;
import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.BuildingCode;
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
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class LocationFragmentViewModel extends ViewModel {

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

    /**
     * Generate the hall building overlays
     * @return the generate ground overlay option
     */
    public GroundOverlayOptions getHallBuildingOverlay(){
        return new GroundOverlayOptions()
                .position(new LatLng(45.4972685, -73.5789475), (float) 68, (float) 68)
                .image(BitmapDescriptorFactory.fromResource(R.drawable.hall_9))
                .bearing((float) 34);
    }

    public void setHallFloorplan(GroundOverlay groundOverlay, int floorNb){
        if(floorNb == 8)  setHall8Floorplan(groundOverlay);
        else if(floorNb == 9) setHall9Floorplan(groundOverlay);
    }

    private void setHall8Floorplan(GroundOverlay groundOverlay){
        groundOverlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.hall_8));
    }

    private void setHall9Floorplan(GroundOverlay groundOverlay){
        groundOverlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.hall_9));
    }

    /**
     * Generate the John Molson  building overlays
     * @return the generate ground overlay option
     */
    public GroundOverlayOptions getMBBuildingOverlay(){
        //400 x 570 / 645 x 645
        //actual dimensions 22.58 x 64 -> 36.41 x 72
        return new GroundOverlayOptions()
                //45.495245, -73.578941
                .position(new LatLng( 45.495305, -73.578885), (float) 65, (float) 65)
                .image(BitmapDescriptorFactory.fromResource(R.drawable.mb_1))
                .bearing((float) 127);
    }

    public void setMBFloorplan(GroundOverlay groundOverlay, int floorNb){
        if(floorNb == -1)  setMBS2Floorplan(groundOverlay);
        else if(floorNb == 1)  setMB1Floorplan(groundOverlay);
    }

    private void setMBS2Floorplan(GroundOverlay groundOverlay){
        groundOverlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.mb_s2));
    }

    private void setMB1Floorplan(GroundOverlay groundOverlay){
        groundOverlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.mb_1));
    }

    /**
     * Generate the Vanier Library building overlays
     * @return the generate ground overlay option
     */
    public GroundOverlayOptions getVLBuildingOverlay(){
        //w: 66.5   h: 69.98
        //80x45
        return new GroundOverlayOptions()
                .position(new LatLng( 45.45909, -73.63844), (float) 80, (float) 45)
                .image(BitmapDescriptorFactory.fromResource(R.drawable.vl_2))
                .bearing((float) 210);
    }

    public void setVLFloorplan(GroundOverlay groundOverlay, int floorNb){
        if(floorNb == 1)  setVL1Floorplan(groundOverlay);
        else if(floorNb == 2) setVL2Floorplan(groundOverlay);
    }

    private void setVL1Floorplan(GroundOverlay groundOverlay){
        groundOverlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.vl_1));
    }

    private void setVL2Floorplan(GroundOverlay groundOverlay){
        groundOverlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.vl_2));
    }

    /**
     * @param layer the GeoJson layer containing features to style.
     * @param map the google map where layer will be displayed and markers will be added.
     */
    private void setPolygonStyle(GeoJsonLayer layer, GoogleMap map){
        for (GeoJsonFeature feature : layer.getFeatures()){
            feature.setPolygonStyle(getPolygonStyle());
            String[] coordinates = feature.getProperty("center").split(", ");
            LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
            addBuildingMarker(map, centerPos, Enum.valueOf(BuildingCode.class, feature.getProperty("code")));
        }
        int x =213;
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

    public ArrayList<String> getFloorsAvailable(BuildingCode buildingCode){
        ArrayList<String> floorsAvailable = new ArrayList<>();
        switch(buildingCode){
            case H:
                floorsAvailable.add("hall_9");
                floorsAvailable.add("hall_8");
                return floorsAvailable;
            case MB:
                floorsAvailable.add("mb_1");
                floorsAvailable.add("mb_S2");
                return floorsAvailable;
            case VL:
                floorsAvailable.add("vl_2");
                floorsAvailable.add("vl_1");
            default:
                return floorsAvailable;
        }
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
}

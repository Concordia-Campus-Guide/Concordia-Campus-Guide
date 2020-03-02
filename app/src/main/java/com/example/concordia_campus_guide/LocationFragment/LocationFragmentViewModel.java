package com.example.concordia_campus_guide.LocationFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Double.parseDouble;

public class LocationFragmentViewModel extends ViewModel {
    private GeoJsonLayer floorLayer;

    private HashMap<String, GroundOverlayOptions> buildingsGroundOverlays = new HashMap<>();
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
    public GeoJsonLayer loadPolygons(GoogleMap map, Context applicationContext, JSONObject jsonObject){
        GeoJsonLayer layer = initLayer(map, applicationContext, jsonObject);
        return  layer;
    }

    /**
     * The purpose of this method is to initiate the layer
     * @param map is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return the initiated layer or it will throw an exception if it didn't find the
     *  GeoJson File
     */
    private GeoJsonLayer initLayer(GoogleMap map, Context applicationContext, JSONObject jsonFile){
        GeoJsonLayer layer = null;
        try {
            layer = new GeoJsonLayer(map, jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layer;
    }

    /**
     * Generate the hall building overlays
     * @return the generate ground overlay option
     */
    public void getBuildingOverlay( GeoJsonFeature feature){
        String[] coordinates = feature.getProperty("center").split(", ");
        LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
        String[] floorsAvailable = feature.getProperty("floorsAvailable").split(",");
        buildingsGroundOverlays.put(feature.getProperty("code"), new GroundOverlayOptions()
                .position(centerPos, Float.parseFloat(feature.getProperty("width")), Float.parseFloat(feature.getProperty("height")))
                .image(BitmapDescriptorFactory.fromAsset("buildings_floorplans/"+feature.getProperty("code").toLowerCase()+"_"+floorsAvailable[floorsAvailable.length-1].toLowerCase()+".png"))
                .bearing(Float.parseFloat(feature.getProperty("bearing"))));
    }
    public HashMap<String, GroundOverlayOptions> getBuildingGroundOverlays(){
        return buildingsGroundOverlays;
    }

    /**
     * @param layer the GeoJson layer containing features to style.
     * @param map the google map where layer will be displayed and markers will be added.
     */
    public void setPolygonStyle(GeoJsonLayer layer, GoogleMap map, Context context){
        for (GeoJsonFeature feature : layer.getFeatures()){
            feature.setPolygonStyle(getPolygonStyle());

            if(feature.getProperty("floorsAvailable") != null)
                getBuildingOverlay(feature);

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
        geoJsonPointStyle.setVisible(false);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            feature.setPointStyle(geoJsonPointStyle);
        }
    }


    public void setFloorPlan(GroundOverlay groundOverlay, String buildingCode, String floor, Context context, GoogleMap mMap) {
        String fileName = buildingCode.toLowerCase()+"_"+floor.toLowerCase();
        if (floorLayer != null) {
            floorLayer.removeLayerFromMap();
        }
        floorLayer = loadPolygons(mMap, context, getJsonObject("buildings_floors_json/" + fileName  + ".json", context));
        getPointStyle(floorLayer);
        System.out.println(floorLayer.getFeatures());
        floorLayer.addLayerToMap();
        groundOverlay.setImage(BitmapDescriptorFactory.fromAsset("buildings_floorplans/"+fileName+".png"));

    }

    public JSONObject getJsonObject(String fileName, Context context) {
        JSONObject jObect = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            jObect = new JSONObject(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObect;
    }
}
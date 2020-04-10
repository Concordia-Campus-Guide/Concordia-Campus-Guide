package com.example.concordia_campus_guide.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.global.ApplicationState;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Coordinates;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Double.parseDouble;

public class DrawPolygons {

    public static final Logger LOGGER = Logger.getLogger("DrawPolygons");

    /**
     * The purpose of this method to load the overlay polygon on the map.
     *
     * @param map                is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return It will return the layer to the LocationFragmentView to display on the map
     */
    public GeoJsonLayer loadPolygons(GoogleMap map, Context applicationContext, Map<String, Building> buildings) {
        GeoJsonLayer layer = initLayer(map, applicationContext);
        setPolygonStyle(layer, map, applicationContext,buildings);
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


    /**
     * @param layer the GeoJson layer containing features to style.
     * @param map   the google map where layer will be displayed and markers will be added.
     */
    public void setPolygonStyle(GeoJsonLayer layer, GoogleMap map, Context context, Map<String, Building> buildings) {
        for (GeoJsonFeature feature : layer.getFeatures()) {
            feature.setPolygonStyle(getPolygonStyle());
            Building building = getBuildingFromGeoJsonFeature(feature);
            buildings.put(feature.getProperty("code"), building);
            if (feature.getProperty(ClassConstants.FLOORS_AVAILABLE) != null)
                setBuildingGroundOverlayOptions(building);

            String[] coordinates = feature.getProperty("center").split(", ");
            LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
            addBuildingMarker(map, centerPos, feature.getProperty("code"), context);
        }
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


    public List<String> getFloorsFromBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        List<String> floorsAvailable = null;

        if (feature.getProperty(ClassConstants.FLOORS_AVAILABLE) != null)
            floorsAvailable = Arrays.asList(feature.getProperty(ClassConstants.FLOORS_AVAILABLE).split(","));

        return floorsAvailable;
    }


    public Coordinates getCenterPositionBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        String[] coordinatesString = feature.getProperty("center").split(", ");
        return new Coordinates(Double.parseDouble(coordinatesString[1]), Double.parseDouble(coordinatesString[0]));
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
     * The purpose of this method is to add a marker on the specified building.
     *
     * @param map           is the map used in our application.
     * @param centerPos     is the latitude and longitude of the building's center
     * @param buildingLabel is the Building on which the method will add a marker
     */
    public void addBuildingMarker(GoogleMap map, LatLng centerPos, String buildingLabel, Context context) {
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

}

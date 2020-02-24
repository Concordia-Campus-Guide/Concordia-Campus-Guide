package com.example.concordia_campus_guide.LocationFragment;


import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.BuildingCode;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;

import static java.lang.Double.parseDouble;


public class LocationFragmentViewModel extends ViewModel {
    public int getMapStyle(){
        return R.raw.mapstyle;
    }

    public void loadPolygons(GoogleMap map, Context applicationContext){
        GeoJsonLayer layer = null;
        try {
            layer = new GeoJsonLayer(map, R.raw.buildingcoordinates, applicationContext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (GeoJsonFeature feature : layer.getFeatures()){
            feature.setPolygonStyle(getPolygonStyle());
            String[] coordinates = feature.getProperty("center").split(", ");
            LatLng centerPos = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
            addMarker(map, centerPos, Enum.valueOf(BuildingCode.class, feature.getProperty("code")));
        }


        layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
            @Override
            public void onFeatureClick(GeoJsonFeature geoJsonFeature) {
                //TODO: Make function that pops up the info card for the building (via the building-code)
                if(geoJsonFeature != null){
                    System.out.println("Clicked on "+geoJsonFeature.getProperty("code"));
                }
            }
        });

        layer.addLayerToMap();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                System.out.println(marker.getTag());
                return false;
            }
        });

    }

    private void addMarker(GoogleMap map, LatLng centerPos, BuildingCode buildingCode) {
        Marker marker = map.addMarker(
                new MarkerOptions()
                .position(centerPos)
                .icon(BitmapDescriptorFactory.fromResource(getIcon(buildingCode)))
                .flat(true)
                .anchor(0.5f,0.5f)
                .alpha(0.85f)

        );
        marker.setTag(buildingCode);

    }

    private GeoJsonPolygonStyle getPolygonStyle() {
        GeoJsonPolygonStyle geoJsonPolygonStyle = new GeoJsonPolygonStyle();
        geoJsonPolygonStyle.setFillColor(Color.argb(51, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeColor(Color.argb(255, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeWidth(6);
        return geoJsonPolygonStyle;
    }

    private int getIcon(BuildingCode buildingCode){

        switch (buildingCode){
            case H:
                return R.drawable.hall;
            case LB:
                return R.drawable.hall;
        }
        return  -1;
    }


}

package com.example.concordia_campus_guide.LocationFragment;


import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;

import static java.lang.Double.parseDouble;


public class LocationFragmentViewModel extends ViewModel {
    public int getMapStyle(){
        return R.raw.mapstyle_retro;
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
        GeoJsonPolygonStyle geoJsonPolygonStyle = new GeoJsonPolygonStyle();
        geoJsonPolygonStyle.setFillColor(Color.argb(51, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeColor(Color.argb(255, 18, 125, 159));
        geoJsonPolygonStyle.setStrokeWidth(6);


        for (GeoJsonFeature feature : layer.getFeatures()){
            feature.setPolygonStyle(geoJsonPolygonStyle);
            String[] coordinates = feature.getProperty("center").split(", ");
            LatLng center = new LatLng(parseDouble(coordinates[1]), parseDouble(coordinates[0]));
            map.addMarker(
                    new MarkerOptions()
                    .position(center)
                    .title(feature.getProperty("code"))
            );

        }
        layer.addLayerToMap();
    }


}

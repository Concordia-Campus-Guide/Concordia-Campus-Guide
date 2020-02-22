package com.example.concordia_campus_guide.LocationFragment;


import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;


public class LocationFragmentViewModel extends ViewModel {
    public int getMapStyle(){
        return R.raw.mapstyle_retro;
    }

    public void loadPolygons(GoogleMap map, Context applicationContext){
        GeoJsonLayer layer = null;
        try {
            layer = new GeoJsonLayer(map, R.raw.google, applicationContext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GeoJsonPolygonStyle geoJsonPolygonStyle = new GeoJsonPolygonStyle();
        geoJsonPolygonStyle.setFillColor(Color.GREEN);

        for (GeoJsonFeature feature : layer.getFeatures()){
            feature.setPolygonStyle(geoJsonPolygonStyle);
        }

        layer.addLayerToMap();
    }


}

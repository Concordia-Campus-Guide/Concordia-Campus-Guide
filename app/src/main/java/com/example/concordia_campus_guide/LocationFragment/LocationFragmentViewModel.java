package com.example.concordia_campus_guide.LocationFragment;


import android.app.Application;
import android.content.Context;

import com.example.concordia_campus_guide.Models.Features;
import com.example.concordia_campus_guide.Models.Geometry;
import com.google.android.gms.maps.model.LatLng;
import com.example.concordia_campus_guide.Models.Buildings;
import com.example.concordia_campus_guide.R;



import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class LocationFragmentViewModel extends ViewModel {
    public int getMapStyle(){
        return R.raw.mapstyle_retro;
    }

//    public List<Polygon> createListOfPolyons(Buildings buildings, GoogleMap googleMap){
//        List<Polygon> polygons = new ArrayList<>();
//        for(BuildingInfo building : buildings.getBuildings()){
//            Polygon polygon = googleMap.addPolygon(getPolygon(building));
//            polygons.add(polygon);
//        }
//        return polygons;
//    }
//
//    public List<LatLng> createListOfCoordinates(List<List<Double>> points){
//        List <LatLng> listOfCoordinates= new ArrayList<>();
//        for (List <Double> point : points){
//            listOfCoordinates.add(new LatLng(point.get(0),point.get(1)));
//        }
//        return  listOfCoordinates;
//    }
//
//
//    public PolygonOptions getPolygon(BuildingInfo buildingInfo){
//        List<LatLng> listOfLatLng  = createListOfCoordinates(buildingInfo.getCoordinates());
//        PolygonOptions opts=new PolygonOptions();
//        for (LatLng point : listOfLatLng) {
//            opts.add(point);
//        }
//        return opts;
//    }

    public void readJsonFile(Context context){
        String json;
        try {
            InputStream is = context.getAssets().open("buildingsCoordinates.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            //GeoJSONObject geoJSON = GeoJSON.parse(json);
            Features ko = new Gson().fromJson(json,Features.class);
            int x =4;
            //buildings = new Gson().fromJson(json, Buildings.class);
        } catch (IOException e){
            e.printStackTrace();
        }
       // return buildings;
    }


}

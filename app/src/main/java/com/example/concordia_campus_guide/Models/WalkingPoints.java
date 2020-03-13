package com.example.concordia_campus_guide.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WalkingPoints {
        private List<WalkingPoint> walkingPoints;

        public WalkingPoints(){ walkingPoints = new ArrayList<WalkingPoint>(); }

        public WalkingPoints(List<WalkingPoint> walkingPoints){  this.walkingPoints = walkingPoints; }

        public List<WalkingPoint> getWalkingPoints(){
            return walkingPoints;
        }

        public void setWalkingPoints(List<WalkingPoint> walkingPoints){ this.walkingPoints = walkingPoints; }


//        public JSONObject getGeoJson(){
//            JSONObject toReturn = new JSONObject();
//            try{
//                toReturn.put("features", getInnerGeoJson());
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//            return toReturn;
//        }

//        public JSONArray getInnerGeoJson(){
//            JSONArray features = new JSONArray();
//
//            try{
//                for(WalkingPoint walkingPoint: walkingPoints){
//                    JSONObject buildingGeoJson = walkingPoint.getGeoJson();
//                    if(buildingGeoJson!=null) features.put(walkingPoint.getGeoJson());
//                }
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//
//            return features;
//        }
}


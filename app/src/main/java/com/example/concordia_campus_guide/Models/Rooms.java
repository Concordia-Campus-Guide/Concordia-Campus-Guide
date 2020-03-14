package com.example.concordia_campus_guide.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Rooms {

    private List<RoomModel> Rooms;

    public Rooms(){}

    public Rooms(List<RoomModel> rooms) {
        Rooms = rooms;
    }

    public List<RoomModel> getRooms() {
        return Rooms;
    }

    public List<RoomModel> getRoomsByFloor(String floor){
        ArrayList<RoomModel> list = new ArrayList<>();
        List<RoomModel> rooms = getRooms();
        for(RoomModel room: rooms){
            if(room.getFloorCode().equals(floor)){
                list.add(room);
            }
        }
        return list;
    }

    public JSONObject getGeoJson(String floor){
        JSONObject toReturn = new JSONObject();
        try{
            toReturn.put("type", "FeatureCollection");
            toReturn.put("features", getInnerGeoJson(floor));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return toReturn;
    }

    private JSONArray getInnerGeoJson(String floor){
        JSONArray features = new JSONArray();
        List<RoomModel> rooms = getRoomsByFloor(floor);

        try{
            for(RoomModel roomModel: rooms){
                JSONObject roomGeoJSON = roomModel.getGeoJson();
                if(roomGeoJSON!=null) features.put(roomGeoJSON);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return features;
    }
}

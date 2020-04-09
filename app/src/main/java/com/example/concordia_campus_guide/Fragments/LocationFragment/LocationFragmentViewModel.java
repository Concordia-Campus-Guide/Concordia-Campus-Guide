package com.example.concordia_campus_guide.Fragments.LocationFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Global.ApplicationState;
import com.example.concordia_campus_guide.Helper.BuildingsAndTheirCode;
import com.example.concordia_campus_guide.Helper.CurrentLocation;
import com.example.concordia_campus_guide.Helper.DrawPolygons;
import com.example.concordia_campus_guide.Helper.FloorPlan;
import com.example.concordia_campus_guide.Helper.ManipulateWalkingPoints;
import com.example.concordia_campus_guide.Helper.POIIcon;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.PoiType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Double.parseDouble;

public class LocationFragmentViewModel extends ViewModel {

    private AppDatabase appDatabase;
    private MutableLiveData<PriorityQueue<WalkingPoint>> poiList = new MutableLiveData<>();
    private FloorPlan floorPlan;
    private DrawPolygons drawPolygons;
    private BuildingsAndTheirCode buildingsAndTheirCode;

    private CurrentLocation currentLocation;
    private POIIcon poiIcon;
    private ManipulateWalkingPoints manipulateWalkingPoints;

    //EV centerCoordinates
    private LatLng initialZoomLocation = ClassConstants.initialZoomLocation;

    public static final Logger LOGGER = Logger.getLogger("LocationFragmentViewModel");

    private HashMap<String, List<WalkingPoint>> walkingPointsMap = new HashMap<>();

    public LocationFragmentViewModel(AppDatabase appDb) {
        this.appDatabase = appDb;
        this.poiIcon = new POIIcon();
        this.floorPlan = new FloorPlan(appDatabase);
        this.manipulateWalkingPoints = new ManipulateWalkingPoints();
        this.buildingsAndTheirCode = new BuildingsAndTheirCode();
    }

    /**
     * The purpose of this method to load the overlay polygon on the map.
     *
     * @param map                is the map used in our application.
     * @param applicationContext is the Context of the LocationFragmentView page
     * @return It will return the layer to the LocationFragmentView to display on the map
     */
    public GeoJsonLayer loadPolygons(GoogleMap map, Context applicationContext) {
        drawPolygons = new DrawPolygons();
        return drawPolygons.loadPolygons(map,applicationContext,buildingsAndTheirCode.getBuildings());
    }

    public Building getBuildingFromGeoJsonFeature(GeoJsonFeature feature) {
        drawPolygons = new DrawPolygons();
        return drawPolygons.getBuildingFromGeoJsonFeature(feature);
    }

    /**
     * @param buildingCode it represents which building we will be covering
     * @return Int of drawable resource's bitmap representation
     */
    public void setFloorPlan(GroundOverlay groundOverlay, String buildingCode, String floor, GoogleMap mMap) {
        floorPlan.setFloorPlan(groundOverlay,buildingCode,floor,mMap,walkingPointsMap);
    }


    public LatLng getZoomLocation(String location) {
        return buildingsAndTheirCode.getZoomLocation(location);
    }

    public Building getBuildingFromCode(String buildingCode) {
        return buildingsAndTheirCode.getBuildings().get(buildingCode);
    }

    public Map<String, Building> getBuildings() {
        return buildingsAndTheirCode.getBuildings();
    }



    public LatLng getInitialZoomLocation() {
        return initialZoomLocation;
    }

    public void setInitialZoomLocation(LatLng latLng){
        initialZoomLocation = latLng;
    }

    public void setListOfPOI(@PoiType String poiType, Context context) {
        List<WalkingPoint> allPOI = appDatabase.walkingPointDao().getAllPointsForPointType(poiType);
        poiIcon.setCurrentPOIIcon(poiType, context);
        this.poiList.postValue(poiIcon.getPOIinOrder(appDatabase,allPOI,currentLocation));
    }


    public BitmapDescriptor getCustomSizedIcon(String filename, Context context, int height, int width) {
        return poiIcon.getCustomSizedIcon(filename,context,height,width);
    }

    public BitmapDescriptor getCurrentPOIIcon(){
        return poiIcon.getCurrentPOIIcon();
    }

    public LiveData<List<RoomModel>> getListOfRoom() {
        return floorPlan.getRoomsInAFloor().getListOfRoom();
    }

    public LiveData<PriorityQueue<WalkingPoint>> getListOfPOI() {
        return poiList;
    }

    public RoomModel getRoomByRoomCodeAndFloorCode(String roomCode, String floorCode){
        return appDatabase.roomDao().getRoomByRoomCodeAndFloorCode(roomCode, floorCode);
    }

    public void parseWalkingPointList(AppDatabase appDatabase, RoomModel from, RoomModel to){
        manipulateWalkingPoints.parseWalkingPointList(appDatabase, from, to, walkingPointsMap);
    }

    public List<WalkingPoint> getWalkingPointsList(){
       return manipulateWalkingPoints.getWalkingPointsList();
    }



    public void drawShuttlePath(GoogleMap mMap, String polyline) {
        List<LatLng> route = PolyUtil.decode(polyline);
        mMap.addPolyline(new PolylineOptions().addAll(route).color(R.color.colorAppTheme).width(20));
    }

    /**
     * @return return the map style
     */
    public int getMapStyle() {
        return R.raw.mapstyle_retro;
    }
}
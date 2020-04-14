package com.example.concordia_campus_guide.view_models;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.adapters.DirectionWrapper;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Floor;
import com.example.concordia_campus_guide.models.PathInfoCard;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.models.PointType;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;

import java.util.ArrayList;
import java.util.List;

public class PathsViewModel extends ViewModel {

    public static final int EARTH_RADIUS_KM = 6371;

    private double distanceBetweenPoints;
    private AppDatabase appDB;
    private ArrayList<PathInfoCard> infoCardList;

    public PathsViewModel(AppDatabase appDB) {
        this.infoCardList = new ArrayList<>();
        this.appDB = appDB;
    }

    public ArrayList<PathInfoCard> getInfoCardList() {
        return infoCardList;
    }

    public Place getDestination() {
        return SelectingToFromState.getTo();
    }

    public String getDestinationLocationDisplayName() {
        return getDestination().getDisplayName();
    }

    public Place getInitialLocation() {
        return SelectingToFromState.getFrom();
    }

    public String getInitialLocationDisplayName() {
        return getInitialLocation().getDisplayName();
    }

    public Place getEntrance(Place place) {
        if (!isPlaceIndoor(place)) return place;
        String floorCode = place instanceof RoomModel ? ((RoomModel) place).getFloorCode() : ((Floor) place).getFloorCode();
        Building building = appDB.buildingDao().getBuildingByBuildingCode(floorCode.substring(0, floorCode.indexOf('-')).toUpperCase());
        String entranceFloor = building.getBuildingCode() + "-" + building.getEntranceFloor();
        return appDB.roomDao().getRoomByIdAndFloorCode("entrance", entranceFloor);
    }

    public boolean arePlacesSeparatedByATunnel(Place from, Place to) {
        if (from instanceof RoomModel && to instanceof RoomModel) {
            String floorCode = ((RoomModel) from).getFloorCode();
            String fromBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            floorCode = ((RoomModel) to).getFloorCode();
            String toBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            return fromBuilding.equalsIgnoreCase("H") && toBuilding.equalsIgnoreCase("MB") ||
                    fromBuilding.equalsIgnoreCase("MB") && toBuilding.equalsIgnoreCase("H");
        }
        return false;
    }

    public boolean areInSameBuilding(Place from, Place to) {
        if (!isPlaceIndoor(from) || !isPlaceIndoor(to)) return false;

        String floorCode = from instanceof RoomModel ? ((RoomModel) from).getFloorCode() : ((Floor) from).getFloorCode();
        String fromBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

        floorCode = to instanceof RoomModel ? ((RoomModel) to).getFloorCode() : ((Floor) to).getFloorCode();
        String toBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

        return fromBuilding.equalsIgnoreCase(toBuilding);
    }

    public boolean isPlaceIndoor(Place place) {
        return place instanceof Floor || place instanceof RoomModel;
    }

    public void adaptOutdoorDirectionsToInfoCardList(List<DirectionWrapper> directionWrappers) {
        for (DirectionWrapper dw : directionWrappers) {
            PathInfoCard card = new PathInfoCard(dw.getDirection().getTransportType(), dw.getDirection().getDuration() / 60, dw.getDirection().getDescription());
            this.infoCardList.add(card);
        }
    }

    public void adaptIndoorDirectionsToInfoCardList(List<WalkingPoint> walkingPointList, Context context) {
        for (int i = 0; i < walkingPointList.size() - 1; i++) {
            WalkingPoint startWalkingPoint = walkingPointList.get(i);
            WalkingPoint endWalkingPoint = walkingPointList.get(i + 1);
            distanceBetweenPoints += getDistanceFromLatLonInKm(startWalkingPoint.getLatitude(), startWalkingPoint.getLongitude(), endWalkingPoint.getLatitude(), startWalkingPoint.getLongitude());
            if (startWalkingPoint.getPointType().equals(PointType.CLASSROOM)) {
                addCardToList(context.getString(R.string.leave_classroom), "Classroom");
            }
            addIndoorDescriptionToList(startWalkingPoint, endWalkingPoint, context);
        }
    }

    private void addCardToList(String description, String type) {
        PathInfoCard pathInfoCard;
        long timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
        pathInfoCard = new PathInfoCard(type, timeTakenInMinutes, description);
        infoCardList.add(pathInfoCard);
        distanceBetweenPoints = 0;
    }

    private void addIndoorDescriptionToList(WalkingPoint startWalkingPoint, WalkingPoint endWalkingPoint, Context context) {
        @PointType String pt = endWalkingPoint.getPointType();
        switch (pt) {
            case PointType.ELEVATOR:
                if (!startWalkingPoint.getPointType().equals(PointType.ELEVATOR)) {
                    addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "Elevator");
                }
                break;
            case PointType.ENTRANCE:
                addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "Entrance");
                break;
            case PointType.STAFF_ELEVATOR:
                if (!startWalkingPoint.getPointType().equals(PointType.STAFF_ELEVATOR)) {
                    addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "Staff_Elevator");
                }
                break;
            case PointType.STAIRS:
                if (!startWalkingPoint.getPointType().equals(PointType.STAIRS)) {
                    addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "Stairs");
                }
                break;
            case PointType.CLASSROOM:
                if (!startWalkingPoint.getPointType().equals(PointType.CLASSROOM)) {
                    addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "Classroom");
                }
                break;
            case PointType.WASHROOM:
                if (!startWalkingPoint.getPointType().equals(PointType.WASHROOM)) {
                    addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "washroom");
                }
                break;
            case PointType.LOUNGES:
                if (!startWalkingPoint.getPointType().equals(PointType.LOUNGES)) {
                    addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "Lounges");
                }
                break;
            case PointType.WATER_FOUNTAINS:
                if (!startWalkingPoint.getPointType().equals(PointType.WATER_FOUNTAINS)) {
                    addCardToList(context.getString(R.string.walk_towards) + " " + endWalkingPoint.getPlaceCode(), "waterfountain");
                }
                break;
            default:
                break;
        }
    }

    public void adaptShuttleToInfoCardList() {
        PathInfoCard pathInfoCard = new PathInfoCard("SHUTTLE", 20, "Take shuttle");
        infoCardList.add(pathInfoCard);
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    public double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = EARTH_RADIUS_KM; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double angle =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double center = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1 - angle));
        return earthRadius * center; // Distance in km
    }
}

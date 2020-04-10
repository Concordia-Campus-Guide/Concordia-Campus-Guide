package com.example.concordia_campus_guide.view_models;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.global.SelectingToFromState;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.PathInfoCard;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.models.PointType;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.WalkingPoint;

import java.util.ArrayList;
import java.util.List;

public class PathsViewModel extends ViewModel {

    double distanceBetweenPoints;
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

    public Place getInitialLocation() {
        return SelectingToFromState.getFrom();
    }

    public Place getEntrance(Place place) {
        if (place instanceof RoomModel) {
            String floorCode = ((RoomModel) place).getFloorCode();
            Building building = appDB.buildingDao().getBuildingByBuildingCode(floorCode.substring(0, floorCode.indexOf('-')).toUpperCase());
            String entranceFloor = building.getBuildingCode() + "-" + building.getEntranceFloor();
            return appDB.roomDao().getRoomByIdAndFloorCode("entrance", entranceFloor);
        }
        return place;
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
        if (from instanceof RoomModel && to instanceof RoomModel) {
            String floorCode = ((RoomModel) from).getFloorCode();
            String fromBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            floorCode = ((RoomModel) to).getFloorCode();
            String toBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            return fromBuilding.equalsIgnoreCase(toBuilding);
        }
        return false;
    }

    public void adaptOutdoorDirectionsToInfoCardList(List<DirectionWrapper> directionWrappers) {
        for (DirectionWrapper dw : directionWrappers) {
            PathInfoCard card = new PathInfoCard(dw.getDirection().getTransportType(), dw.getDirection().getDuration() / 60, dw.getDirection().getDescription());
            this.infoCardList.add(card);
        }
    }

    public void adaptIndoorDirectionsToInfoCardList(List<WalkingPoint> walkingPointList) {
        for (int i = 0; i < walkingPointList.size() - 1; i++) {
            WalkingPoint startWalkingPoint = walkingPointList.get(i);
            WalkingPoint endWalkingPoint = walkingPointList.get(i + 1);
            distanceBetweenPoints += getDistanceFromLatLonInKm(startWalkingPoint.getCoordinate().getLatitude(), startWalkingPoint.getCoordinate().getLongitude(), endWalkingPoint.getCoordinate().getLatitude(), startWalkingPoint.getCoordinate().getLongitude());
            if (startWalkingPoint.getPointType() == PointType.CLASSROOM) {
                addCardToList("Leave classroom", "Classroom");
            } else if (startWalkingPoint.getPointType() == PointType.ENTRANCE) {
                addCardToList("Walk towards entrance", "Entrance");
            }
            addIndoorDescriptionToList(startWalkingPoint, endWalkingPoint);
        }
    }

    private void addCardToList(String description, String type) {
        PathInfoCard pathInfoCard;
        long timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
        pathInfoCard = new PathInfoCard(type, timeTakenInMinutes, description);
        infoCardList.add(pathInfoCard);
        distanceBetweenPoints = 0;
    }

    private void addIndoorDescriptionToList(WalkingPoint startWalkingPoint, WalkingPoint endWalkingPoint) {
        PointType pt = endWalkingPoint.getPointType();
        switch (pt) {
            case ELEVATOR:
                if (startWalkingPoint.getPointType() != PointType.ELEVATOR) {
                    addCardToList("Walk towards elevator", "Elevator");
                }
                break;
            case ENTRANCE:
                addCardToList("Walk towards building entrance", "Entrance");
                break;
            case STAFF_ELEVATOR:
                if (startWalkingPoint.getPointType() != PointType.STAFF_ELEVATOR) {
                    addCardToList("Walk towards staff elevator", "Staff_Elevator");
                }
                break;
            case STAIRS:
                if (startWalkingPoint.getPointType() != PointType.STAIRS) {
                    addCardToList("Walk towards stairs", "Stairs");
                }
                break;
            case CLASSROOM:
                if (startWalkingPoint.getPointType() != PointType.CLASSROOM) {
                    addCardToList("Walk towards classroom " + endWalkingPoint.getPlaceCode(), "Classroom");
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
        double earthRadius = ClassConstants.EARTH_RADIUS_KM; // Radius of the earth in km
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

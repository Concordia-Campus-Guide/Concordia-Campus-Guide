package com.example.concordia_campus_guide.Activities;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.PathInfoCard;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;

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

    public Place getTo() {
        return SelectingToFromState.getTo();
    }

    public Place getFrom() {
        return SelectingToFromState.getFrom();
    }

    public Place getEntrance(Place place) {
        if (place instanceof RoomModel) {
            String floorCode = ((RoomModel) place).getFloorCode();
            String buildingCode = floorCode.substring(0, floorCode.indexOf('-')).toUpperCase();
            return appDB.roomDao().getRoomByIdAndFloorCode("entrance", buildingCode + "-1");
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
            long timeTakenInMinutes;
            String description = "";
            PathInfoCard infoCardTypes;
            if (startWalkingPoint.getPointType() == PointType.CLASSROOM) {
                description = "Leave classroom";
                timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                infoCardTypes = new PathInfoCard("Classroom", timeTakenInMinutes, description);
                infoCardList.add(infoCardTypes);
                distanceBetweenPoints = 0;
            } else if (startWalkingPoint.getPointType() == PointType.ENTRANCE) {
                description = "Walk towards entrance";
                timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                infoCardTypes = new PathInfoCard("ENTRANCE", timeTakenInMinutes, description);
                infoCardList.add(infoCardTypes);
                distanceBetweenPoints = 0;
            }
            addIndoorDescriptionToList(startWalkingPoint, endWalkingPoint);
        }
    }

    private void addIndoorDescriptionToList(WalkingPoint startWalkingPoint, WalkingPoint endWalkingPoint) {
        String description;
        long timeTakenInMinutes;
        PathInfoCard infoCardTypes;
        PointType pt = endWalkingPoint.getPointType();
        switch (pt) {
            case ELEVATOR:
                if (startWalkingPoint.getPointType() != PointType.ELEVATOR) {
                    description = "Walk towards elevator";
                    timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                    infoCardTypes = new PathInfoCard("Elevator", timeTakenInMinutes, description);
                    infoCardList.add(infoCardTypes);
                    distanceBetweenPoints = 0;
                }
                break;
            case ENTRANCE:
                description = "Walk towards building entrance";
                timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                infoCardTypes = new PathInfoCard("Entrance", timeTakenInMinutes, description);
                infoCardList.add(infoCardTypes);
                distanceBetweenPoints = 0;
                break;
            case STAFF_ELEVATOR:
                if (startWalkingPoint.getPointType() != PointType.STAFF_ELEVATOR) {
                    description = "Walk towards staff elevator";
                    timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                    infoCardTypes = new PathInfoCard("Staff_Elevator", timeTakenInMinutes, description);
                    infoCardList.add(infoCardTypes);
                    distanceBetweenPoints = 0;
                }
                break;
            case STAIRS:
                if (startWalkingPoint.getPointType() != PointType.STAIRS) {
                    description = "Walk towards stairs";
                    timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                    infoCardTypes = new PathInfoCard("Stairs", timeTakenInMinutes, description);
                    infoCardList.add(infoCardTypes);
                    distanceBetweenPoints = 0;
                }
                break;
            case CLASSROOM:
                if (startWalkingPoint.getPointType() != PointType.CLASSROOM) {
                    description = "Walk towards classroom " + endWalkingPoint.getFloorCode();
                    timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                    infoCardTypes = new PathInfoCard("Classroom", timeTakenInMinutes, description);
                    infoCardList.add(infoCardTypes);
                    distanceBetweenPoints = 0;
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
        double earthRadius = 6371; // Radius of the earth in km
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

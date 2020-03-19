package com.example.concordia_campus_guide.Helper;

import android.content.Context;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.List;

public class IndoorPathHeuristic {

    Context context;

    public IndoorPathHeuristic(Context context) {
        this.context = context;
    }

    /**
     * The heuristic is based on the distance between the goal and destination coordinates, the further a point from a destination, the bigger the heuristic it will have.
     * @param currentCoordinate
     * @param destinationPoint
     * @return
     */
    protected double computeHeuristic(final WalkingPoint currentCoordinate, final WalkingPoint destinationPoint) {

        if (!currentCoordinate.getFloorCode().equalsIgnoreCase(destinationPoint.getFloorCode())) {
            String currentBuildingCode = currentCoordinate.getFloorCode().split("-")[0];
            String destinationBuildingCode = destinationPoint.getFloorCode().split("-")[0];

            WalkingPoint accessPoint;
            if (!currentBuildingCode.equals(destinationBuildingCode)) {
                accessPoint = getNearestAccessPointForFloor(currentCoordinate, PointType.ENTRANCE);
                if (accessPoint != null)
                    return getEuclideanDistance(currentCoordinate, accessPoint)
                            + getEuclideanDistance(accessPoint, destinationPoint);
            }

            accessPoint = getNearestAccessPointForFloor(currentCoordinate, PointType.ELEVATOR);
                return getEuclideanDistance(currentCoordinate, accessPoint)
                        + getEuclideanDistance(accessPoint, destinationPoint);
        }

        return getEuclideanDistance(currentCoordinate, destinationPoint);
    }

    /**
     * This method returns the nearest access point in a given floor (given the access point type).
     * @param currentPoint
     * @param accessPointType
     * @return
     */
    protected WalkingPoint getNearestAccessPointForFloor(final WalkingPoint currentPoint, PointType accessPointType) {
        final List<WalkingPoint> accessPtList = AppDatabase.getInstance(context).walkingPointDao()
                .getAllAccessPointsOnFloor(currentPoint.getFloorCode(), accessPointType);
        return getClosestPointToCurrentPointFromList(currentPoint, accessPtList);
    }

    /**
     * This method returns the closest access point based on a source location. This is where the logic is.
     * @param currentPoint
     * @param accessPtList
     * @return
     */
    protected WalkingPoint getClosestPointToCurrentPointFromList(final WalkingPoint currentPoint,
                                                                 final List<WalkingPoint> accessPtList) {
        WalkingPoint closestPoint = null;
        if (!accessPtList.isEmpty()) {
            closestPoint = accessPtList.get(0);
            for (int i = 1; i < accessPtList.size(); i++) {
                final double closestPointDistance = getEuclideanDistance(currentPoint, closestPoint);
                final double otherPointDistance = getEuclideanDistance(currentPoint, accessPtList.get(i));
                closestPoint = closestPointDistance > otherPointDistance ? accessPtList.get(i) : closestPoint;
            }
        }
        return closestPoint;
    }

    /**
     * This method returns the euclidean distance between two points based on the pythagorean theorem.
     * @param firstCoordinate
     * @param secondCoordinate
     * @return
     */
    protected double getEuclideanDistance(final WalkingPoint firstCoordinate, final WalkingPoint secondCoordinate) {
        final double resultLatDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(0)
                - secondCoordinate.getCoordinate().toListDouble().get(0));
        final double resultLongDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(1)
                - secondCoordinate.getCoordinate().toListDouble().get(1));

        return Math.sqrt(Math.pow(resultLatDiff, 2) + Math.pow(resultLongDiff, 2));
    }
}

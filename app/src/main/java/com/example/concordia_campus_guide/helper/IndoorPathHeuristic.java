package com.example.concordia_campus_guide.helper;

import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.models.PointType;
import com.example.concordia_campus_guide.models.WalkingPoint;

import java.util.Arrays;
import java.util.List;

public class IndoorPathHeuristic {

    AppDatabase appDatabase;
    private boolean isStaffAccessibility;
    private boolean isReducedAccessibility;

    public IndoorPathHeuristic(AppDatabase appDatabase, boolean isStaffAccessiblity, boolean isReducedAccessiblity) {
        this.appDatabase = appDatabase;
        this.isStaffAccessibility = isStaffAccessiblity;
        this.isReducedAccessibility = isReducedAccessiblity;
    }

    /**
     * The heuristic is based on the distance between the goal and destination coordinates, the further a point from a destination, the bigger the heuristic it will have.
     *
     * @param currentPoint
     * @param destinationPoint
     * @return
     */
    public double computeHeuristic(final WalkingPoint currentPoint, final WalkingPoint destinationPoint) {

        if (!currentPoint.getFloorCode().equalsIgnoreCase(destinationPoint.getFloorCode())) {
            String currentBuildingCode = currentPoint.getFloorCode().split("-")[0];
            String destinationBuildingCode = destinationPoint.getFloorCode().split("-")[0];

            WalkingPoint accessPoint;
            if (!currentBuildingCode.equals(destinationBuildingCode)) {
                accessPoint = getNearestAccessPointForFloor(currentPoint, PointType.ENTRANCE);
                if (accessPoint != null)
                    return getEuclideanDistance(currentPoint, accessPoint)
                            + getEuclideanDistance(accessPoint, destinationPoint);
            }

            // TODO #160: Make sure to change the heuristic to reflect the distance to the nearest accessible point type
            accessPoint = getNearestAccessPointForFloor(currentPoint, PointType.ELEVATOR);

            // If the accessibility is not reduced, return the nearest access point that is accessible by everyone
            if (!isReducedAccessibility) {
                WalkingPoint stairAccessPoint = getNearestAccessPointForFloor(currentPoint, PointType.STAIRS);
                if (stairAccessPoint != null)
                    accessPoint = getClosestPointToCurrentPointFromList(currentPoint, Arrays.asList(accessPoint, stairAccessPoint));
            }

            // If staff access points can be considered, return the nearest access point that is accessible by only staff
            if (isStaffAccessibility) {
                WalkingPoint staffAccessPoint = getNearestAccessPointForFloor(currentPoint, PointType.STAFF_ELEVATOR);
                if (staffAccessPoint != null)
                    accessPoint = getClosestPointToCurrentPointFromList(currentPoint, Arrays.asList(accessPoint, staffAccessPoint));
            }
            return getEuclideanDistance(currentPoint, accessPoint)
                    + getEuclideanDistance(accessPoint, destinationPoint);
        }

        return getEuclideanDistance(currentPoint, destinationPoint);
    }

    /**
     * This method returns the nearest access point in a given floor (given the access point type).
     *
     * @param currentPoint
     * @param accessPointType
     * @return
     */
    public WalkingPoint getNearestAccessPointForFloor(final WalkingPoint currentPoint, @PointType String accessPointType) {
        final List<WalkingPoint> accessPtList = appDatabase.walkingPointDao()
                .getAllAccessPointsOnFloor(currentPoint.getFloorCode(), accessPointType);
        return getClosestPointToCurrentPointFromList(currentPoint, accessPtList);
    }

    /**
     * This method returns the closest access point based on a source location. This is where the logic is.
     *
     * @param currentPoint
     * @param accessPtList
     * @return
     */
    public WalkingPoint getClosestPointToCurrentPointFromList(final WalkingPoint currentPoint,
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
     *
     * @param firstCoordinate
     * @param secondCoordinate
     * @return
     */
    public double getEuclideanDistance(final WalkingPoint firstCoordinate, final WalkingPoint secondCoordinate) {
        return firstCoordinate.getCoordinate().getEuclideanDistanceFrom(secondCoordinate.getCoordinate());
    }
}

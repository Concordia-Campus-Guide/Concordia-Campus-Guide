package com.example.concordia_campus_guide.Helper;

import android.content.Context;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;

import java.util.List;

public class IndoorPathHeuristic {

    Context context;

    public IndoorPathHeuristic(Context context){
        this.context = context;
    }

    protected double computeHeuristic(final WalkingPoint currentCoordinate, final WalkingPoint destinationPoint) {
        if (!currentCoordinate.getFloorCode().equalsIgnoreCase(destinationPoint.getFloorCode())) {
            final WalkingPoint accessPoint = getNearestAccessPointForFloor(currentCoordinate);
            return getEuclideanDistance(currentCoordinate, accessPoint)
                    + getEuclideanDistance(accessPoint, destinationPoint);
        }

        return getEuclideanDistance(currentCoordinate, destinationPoint);
    }

    protected WalkingPoint getNearestAccessPointForFloor(final WalkingPoint currentPoint) {
        final List<WalkingPoint> accessPtList = AppDatabase.getInstance(context).walkingPointDao()
                .getAllAccessPointsOnFloor(currentPoint.getFloorCode(), PointType.ELEVATOR);
        return getClosestPointToCurrentPointFromList(currentPoint, accessPtList);
    }

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

    protected double getEuclideanDistance(final WalkingPoint firstCoordinate, final WalkingPoint secondCoordinate) {
        final double resultLatDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(0)
                - secondCoordinate.getCoordinate().toListDouble().get(0));
        final double resultLongDiff = Math.abs(firstCoordinate.getCoordinate().toListDouble().get(1)
                - secondCoordinate.getCoordinate().toListDouble().get(1));

        return Math.sqrt(Math.pow(resultLatDiff, 2) + Math.pow(resultLongDiff, 2));
    }
}

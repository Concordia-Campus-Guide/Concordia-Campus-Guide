package com.example.concordia_campus_guide.models.helpers;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.models.routes.TransportType;
import java.util.List;

public class RouteBuilderDirector {
    public void constructTransitRoute(RouteBuilder builder, List<TransportType> steps, String departureTime, String arrivalTime, String duration) {
        builder.setSteps(steps);
        builder.setDepartureTime(departureTime);
        builder.setArrivalTime(arrivalTime);
        builder.setDuration(duration);
        builder.setSummary("");
        builder.setMainTransportType(ClassConstants.TRANSIT);
    }

    public void constructTransitRouteWithoutArrivalOrDepartureTime(RouteBuilder builder, List<TransportType> steps, String duration) {
        builder.setSteps(steps);
        builder.setDepartureTime("");
        builder.setArrivalTime("");
        builder.setDuration(duration);
        builder.setSummary("");
        builder.setMainTransportType(ClassConstants.TRANSIT);
    }

    public void constructWalkingRoute(RouteBuilder builder, String duration, String summary) {
        builder.setSteps(null);
        builder.setDepartureTime("");
        builder.setArrivalTime("");
        builder.setDuration(duration);
        builder.setSummary(summary);
        builder.setMainTransportType(ClassConstants.WALKING);
    }

    public void constructDrivingRoute(RouteBuilder builder, String duration, String summary) {
        builder.setSteps(null);
        builder.setDepartureTime("");
        builder.setArrivalTime("");
        builder.setDuration(duration);
        builder.setSummary(summary);
        builder.setMainTransportType(ClassConstants.DRIVING);
    }

    public void constructShuttleRoute(RouteBuilder builder, String departureTime, String arrivalTime, String duration, String summary) {
        builder.setSteps(null);
        builder.setDepartureTime(departureTime);
        builder.setArrivalTime(arrivalTime);
        builder.setDuration(duration);
        builder.setSummary(summary);
        builder.setMainTransportType(ClassConstants.SHUTTLE);

    }

    public void constructShuttleRouteWithSummaryOnly(RouteBuilder builder, String summary) {
        builder.setSteps(null);
        builder.setDepartureTime("");
        builder.setArrivalTime("");
        builder.setDuration("");
        builder.setSummary(summary);
        builder.setMainTransportType(ClassConstants.SHUTTLE);
    }
}

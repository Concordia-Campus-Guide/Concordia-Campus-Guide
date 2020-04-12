package com.example.concordia_campus_guide.models.routes;

import com.example.concordia_campus_guide.ClassConstants;

import java.util.List;

public class Route {
    private List<TransportType> steps;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String summary;
    private @ClassConstants.TransportType
    String mainTransportType;

    public Route(List<TransportType> steps, String departureTime, String arrivalTime, String duration, String summary, String mainTransportType) {
        this.steps = steps;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.summary = summary;
        this.mainTransportType = mainTransportType;
    }

    public List<TransportType> getSteps() {
        return steps;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getSummary() {
        return summary;
    }

    public String getMainTransportType() {
        return mainTransportType;
    }

}
package com.example.concordia_campus_guide.models.helpers;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.models.routes.Route;
import com.example.concordia_campus_guide.models.routes.TransportType;

import java.util.List;

public class RouteBuilder {
    private List<TransportType> steps;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String summary;
    private @ClassConstants.TransportType
    String mainTransportType;

    public void setSteps(List<TransportType> steps) {
        this.steps = steps;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setMainTransportType(String mainTransportType) {
        this.mainTransportType = mainTransportType;
    }

    public Route getRoute() {
        return new Route(steps, departureTime, arrivalTime, duration, summary, mainTransportType);
    }

}

package com.example.concordia_campus_guide.Models.Routes;

import com.example.concordia_campus_guide.ClassConstants;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<TransportType> steps;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String summary;
    private @ClassConstants.TransportType String mainTransportType;

    // When TRANSIT is chosen
    public Route(String departureTime, String arrivalTime, String duration, String mainTransportType) {
        this.steps = new ArrayList<>();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.summary = "";
        this.mainTransportType = mainTransportType;
    }

    // When TRANSIT is chosen but there's no arrivalTime and departureTime associated to it
    public Route(String duration, String mainTransportType) {
        this.steps = new ArrayList<>();
        this.departureTime = "";
        this.arrivalTime = "";
        this.duration = duration;
        this.summary = "";
        this.mainTransportType = mainTransportType;
    }

    // When WALKING or DRIVING is chosen
    public Route(String duration, String summary, String mainTransportType) {
        this.steps = null; // for WALKING and DRIVING, no need to have the steps since they won't be displayed in the Routes overview section of Routes Activity page
        this.departureTime = "";
        this.arrivalTime = "";
        this.duration = duration;
        this.summary = summary;
        this.mainTransportType = mainTransportType;
    }

    public List<TransportType> getSteps() {
        return steps;
    }

    public void setSteps(List<TransportType> steps) {
        this.steps = steps;
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
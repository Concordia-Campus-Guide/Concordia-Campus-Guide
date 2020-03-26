package com.example.concordia_campus_guide.Models.Routes;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private List<TransportType> steps;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String summary;

    // For test
    public Route() {
        this.steps = new ArrayList<>();
    }

    // When TRANSIT is chosen
    public Route(String departureTime, String arrivalTime, String duration) {
        this.steps = new ArrayList<>();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.summary = "";
    }

    // When WALKING or DRIVING is chosen
    public Route(String duration, String summary) {
        this.steps = new ArrayList<>();
        this.departureTime = "";
        this.arrivalTime = "";
        this.duration = duration;
        this.summary = summary;
    }

    public void addDirection(TransportType direction){
        steps.add(direction);
    }

    public List<TransportType> getSteps() {
        return steps;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}

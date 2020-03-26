package com.example.concordia_campus_guide.Models;

public class CalendarEvent {
    private String title;
    private String location;
    private String startTime;

    public CalendarEvent(String title, String location, String startTime) {
        this.title = title;
        this.location = location;
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}

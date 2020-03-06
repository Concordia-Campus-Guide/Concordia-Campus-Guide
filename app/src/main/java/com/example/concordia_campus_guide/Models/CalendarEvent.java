package com.example.concordia_campus_guide.Models;

public class CalendarEvent {
    private String title;
    private String location;

    public CalendarEvent(String title, String location) {
        this.title = title;
        this.location = location;
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

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "title='" + title + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

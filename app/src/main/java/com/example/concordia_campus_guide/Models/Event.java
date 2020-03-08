package com.example.concordia_campus_guide.Models;


public class Event {
   // @ColumnInfo (name = "id")
    private long id;

    //@ColumnInfo(name = "description")
    private String description;

   // @ColumnInfo(name ="location")
   // @Embedded
    private Place location;

    public Event(String description, Place location) {
        this.description = description;
        this.location = location;
    }

    public long getId() {  return id; }

    public void setId(long id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Place getLocation() { return location; }

    public void setLocation(Place location) { this.location = location; }
}

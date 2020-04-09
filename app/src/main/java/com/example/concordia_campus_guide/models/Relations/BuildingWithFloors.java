package com.example.concordia_campus_guide.models.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Floor;

import java.util.List;

public class BuildingWithFloors {

    @Embedded
    private Building building;

    public BuildingWithFloors() {
        // do nothing
    }

    @Relation(
            parentColumn = "building_code",
            entityColumn = "building_code"
    )
    private List<Floor> floors;
    
    public Building getBuilding() {
        return building;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

}

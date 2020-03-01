package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Buildings {

    private List<Building> Buildings;

    /**
     * Constructor initializes Buildings to empty ArrayList
     */
    public Buildings(){ this.Buildings = new ArrayList<Building>(); }

    /**
     * Constructor initializes Buildings to List<Building> buildings parameter
     */
    public Buildings(List<Building> buildings){ this.Buildings = buildings; }

    /**
     * Returns first building which has the buildingCode parameter as building code
     * Since building codes are unique, this approach is acceptable.
     *
     * Returns null if nothing is found
     *
     * @return building object if building with specified buildingCode is found, null if not found
     */
    public Building getBuilding(String buildingCode){
        for(Building building: Buildings){
            if(building.getBuilding_Code().equals(buildingCode)){
                return building;
            }
        }
        return null;
    }

    /**
     * @return buildings as list of Building objects
     */
    public List<Building> getBuildings(){ return Buildings; }

    /**
     * Sets buildings param to Buildings attribute
     *
     * @param buildings represents List of Building objects
     */
    public void setBuildings(List<Building> buildings){ this.Buildings = buildings; }

}

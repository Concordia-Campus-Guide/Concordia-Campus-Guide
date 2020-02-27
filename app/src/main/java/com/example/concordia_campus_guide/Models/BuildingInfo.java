package com.example.concordia_campus_guide.Models;

public class BuildingInfo {

    private String Campus;
    private String Building;
    private String Building_Long_Name;
    private String Address;

    public BuildingInfo(){
    }

    public BuildingInfo(String campus, String building, String building_Long_Name, String address){
        this.Address = address;
        this.Campus = campus;
        this.Building = building;
        this.Building_Long_Name = building_Long_Name;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public void setBuilding_Long_Name(String building_Long_Name) {
        Building_Long_Name = building_Long_Name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCampus() {
        return Campus;
    }

    public String getBuilding() {
        return Building;
    }

    public String getBuilding_Long_Name() {
        return Building_Long_Name;
    }

    public String getAddress() {
        return Address;
    }
}

package com.example.concordia_campus_guide.Models;

import android.text.TextUtils;

import java.util.List;

public class Building {

    private String Campus;
    private String Building_Code;
    private String Building_Long_Name;
    private String Address;
    private List<String> Departments;
    private List<String> Services;

    public Building(){
    }

    public Building(String campus, String buildingCode, String building_Long_Name, String address, List<String> departments, List<String> services){
        this.Address = address;
        this.Campus = campus;
        this.Building_Code = buildingCode;
        this.Building_Long_Name = building_Long_Name;
        this.Departments = departments;
        this.Services = services;
    }

    public void setDepartments(List<String> departments) {
        Departments = departments;
    }


    public void setServices(List<String> services) {
        Services = services;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public void setBuilding_Code(String building_Code) {
        Building_Code = building_Code;
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

    public String getBuilding_Code() {
        return Building_Code;
    }

    public String getBuilding_Long_Name() {
        return Building_Long_Name;
    }

    public String getAddress() {
        return Address;
    }

    public List<String> getServices() {
        return Services;
    }

    public String getServicesString(){
        if(Services.isEmpty()){
            return "";
        }
        return TextUtils.join(", ", Services);
    }

    public List<String> getDepartments() {
        return Departments;
    }

    public String getDepartmentsString(){
        if(Departments.isEmpty()){
            return "";
        }
        return TextUtils.join(", ", Departments);
    }
}

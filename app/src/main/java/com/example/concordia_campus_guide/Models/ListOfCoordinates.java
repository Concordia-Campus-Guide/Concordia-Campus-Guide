package com.example.concordia_campus_guide.Models;

import java.util.List;

public class ListOfCoordinates {

    private List<Coordinates> listOfCoordinates;

    public ListOfCoordinates() {
    }

    public ListOfCoordinates(List<Coordinates> listOfCoordinates) {
        this.listOfCoordinates = listOfCoordinates;
    }

    public List<Coordinates> getListOfCoordinates() {
        return listOfCoordinates;
    }

    public void setListOfCoordinates(List<Coordinates> listOfCoordinates) {
        this.listOfCoordinates = listOfCoordinates;
    }
}

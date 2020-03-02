package com.example.concordia_campus_guide.Models;

import java.util.List;
import java.util.Queue;

public class Profile {
    private boolean handicapAccess;
    private boolean staffAccess;
    private Language language;
    List<Place> savedPlaces;
    Queue<Place> history;

    public void toggleHandicap(){}

    public void toggleStaff(){}

    public void setLanguage(Language language){
        this.language = language;
    }
}

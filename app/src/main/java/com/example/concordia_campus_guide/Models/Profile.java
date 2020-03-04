package com.example.concordia_campus_guide.Models;

import java.util.List;
import java.util.Queue;

import androidx.room.ColumnInfo;

public class Profile {
    @ColumnInfo (name ="handicapAccess")
    private boolean handicapAccess;

    @ColumnInfo (name ="staffAccess")
    private boolean staffAccess;

    @ColumnInfo (name = "language")
    private Language language;

    List<Place> savedPlaces;

    Queue<Place> history;

    public void toggleHandicap(){}

    public void toggleStaff(){}

    public void setLanguage(Language language){
        this.language = language;
    }
}

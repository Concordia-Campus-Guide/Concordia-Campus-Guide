package com.example.concordia_campus_guide.Global;

import android.location.Location;

import com.example.concordia_campus_guide.Models.Place;

public class SelectingToFromState {

    private static Place from;
    private static Place to;
    private static Location myCurrentLocation;

    private static boolean quickSelect = true;
    private static boolean selectTo = false;
    private static boolean selectFrom = false;

    private SelectingToFromState() {}

    public static Place getFrom() {
        return from;
    }

    public static void setFrom(Place from) {
        SelectingToFromState.from = from;
    }

    public static Place getTo() {
        return to;
    }

    public static void setTo(Place to) {
        SelectingToFromState.to = to;
    }

    public static Location getMyCurrentLocation() {
        return myCurrentLocation;
    }

    public static void setMyCurrentLocation(Location myCurrentLocation) {
        SelectingToFromState.myCurrentLocation = myCurrentLocation;
    }

    public static boolean isQuickSelect() {
        return quickSelect;
    }

    public static void setQuickSelectToTrue() {
        selectFrom = false;
        selectTo = false;
        quickSelect = true;
    }

    public static boolean isSelectTo() {
        return selectTo;
    }

    public static void setSelectToToTrue() {
        quickSelect = false;
        selectFrom = false;
        selectTo = true;
    }

    public static boolean isSelectFrom() {
        return selectFrom;
    }

    public static void setSelectFromToTrue() {
        quickSelect = false;
        selectTo = false;
        selectFrom = true;
    }

    public static void reset(){
        from = null;
        to = null;
        myCurrentLocation = null;
        setQuickSelectToTrue();
    }
}

package com.example.concordia_campus_guide.Global;

import android.location.Location;

import com.example.concordia_campus_guide.Models.Place;

public class SelectingToFromState {

    private static Place from;
    private static Place to;
    private static Location myCurrentLocation;

    private static boolean QuickSelect = true;
    private static boolean SelectTo = false;
    private static boolean SelectFrom = false;

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
        return QuickSelect;
    }

    public static void setQuickSelectToTrue() {
        SelectFrom = false;
        SelectTo = false;
        QuickSelect = true;
    }

    public static boolean isSelectTo() {
        return SelectTo;
    }

    public static void setSelectToToTrue() {
        QuickSelect = false;
        SelectFrom = false;
        SelectTo = true;
    }

    public static boolean isSelectFrom() {
        return SelectFrom;
    }

    public static void setSelectFromToTrue() {
        QuickSelect = false;
        SelectTo = false;
        SelectFrom = true;
    }

    public static void reset(){
        from = null;
        to = null;
        myCurrentLocation = null;
        setQuickSelectToTrue();
    }
}

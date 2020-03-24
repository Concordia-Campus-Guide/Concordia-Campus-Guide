package com.example.concordia_campus_guide.Activities;

import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathsViewModel extends ViewModel {
    Place from;
    Place to;

    public Place getTo() {
        return SelectingToFromState.getTo();
    }

    public Place getFrom() {
        return SelectingToFromState.getFrom();
    }
}

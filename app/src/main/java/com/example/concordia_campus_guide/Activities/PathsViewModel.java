package com.example.concordia_campus_guide.Activities;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Place;

public class PathsViewModel extends ViewModel {

    public Place getTo() {
        return SelectingToFromState.getTo();
    }

    public Place getFrom() {
        return SelectingToFromState.getFrom();
    }
}

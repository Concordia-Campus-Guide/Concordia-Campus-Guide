package com.example.concordia_campus_guide.Interfaces;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.Models.Routes.Route;

import java.util.List;

public interface DirectionsApiCallbackListener {
    void directionsApiCallBack(DirectionsResult result, List<Route> routeOptions);
}

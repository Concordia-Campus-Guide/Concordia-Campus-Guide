package com.example.concordia_campus_guide.interfaces;

import com.example.concordia_campus_guide.googleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.models.Routes.Route;

import java.util.List;

public interface DirectionsApiCallbackListener {
    void directionsApiCallBack(DirectionsResult result, List<Route> routeOptions);
}

package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PathInfoCardViewModel extends ViewModel {
    public PathInfoCardViewModel(){}

    public int getIcon(String type) {
        switch (type) {
            case "ELEVATOR":
                return R.drawable.ic_elevator_white;
            case "CLASSROOM":
                return R.drawable.walk_selection;
            case "ENTRANCE":
                return R.drawable.ic_exit_to_app_24px_1;
            case "STAFF_ELEVATOR":
                return R.drawable.ic_staff_elevator_white;
            case "STAIRS":
                return R.drawable.ic_stairs_white;
            case "TRANSIT":
                return R.drawable.ic_directions_bus_black_24dp;
            case "DRIVING":
                return R.drawable.ic_directions_car_red;
            case "BICYCLING":
                return R.drawable.ic_directions_bike_black_24dp;
            case "WALKING":
                return R.drawable.ic_directions_walk_red;
            case "SHUTTLE":
                return R.drawable.ic_shuttle_red;
            default:
                return -1;
        }

    }
}

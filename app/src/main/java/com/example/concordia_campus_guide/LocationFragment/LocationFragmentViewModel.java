package com.example.concordia_campus_guide.LocationFragment;

import com.example.concordia_campus_guide.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import androidx.lifecycle.ViewModel;

public class LocationFragmentViewModel extends ViewModel {

    public int getMapStyle(){
        return R.raw.mapstyle_retro;
    }

    public PolygonOptions getPolygon(){
        return new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(45.497178, -73.579550),
                        new LatLng(45.497708, -73.579035),
                        new LatLng(45.497385, -73.578332),
                        new LatLng(45.496832, -73.578842),
                        new LatLng(45.497178, -73.579550));
    }


}

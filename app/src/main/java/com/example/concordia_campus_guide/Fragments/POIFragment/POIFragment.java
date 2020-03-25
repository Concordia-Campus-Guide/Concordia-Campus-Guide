package com.example.concordia_campus_guide.Fragments.POIFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concordia_campus_guide.Adapters.PointOfInterestVPAdapter;
import com.example.concordia_campus_guide.Helper.ViewModelFactory;
import com.example.concordia_campus_guide.Interfaces.OnPOIClickListener;
import com.example.concordia_campus_guide.Models.PoiType;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

public class POIFragment extends Fragment {

    private ViewPager2 poiVP;
    private POIFragmentViewModel mViewModel;

    public static POIFragment newInstance() {
        return new POIFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.point_of_interest_fragment, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        poiVP = rootView.findViewById(R.id.poiVp);
        setupViewPager();
    }

    private void setupViewPager() {
        List<String> services = new ArrayList<>();
        for (Field field : PoiType.class.getFields()) {
            String type = field.getName();
            services.add(type);
        }
        PointOfInterestVPAdapter poiViewPagerAdapter = new PointOfInterestVPAdapter(getContext(), services, getOnClickListener());
        poiVP.setAdapter(poiViewPagerAdapter);
    }

    private OnPOIClickListener getOnClickListener() {
        return new OnPOIClickListener() {
            @Override
            public void onClick(@PoiType String pointType, View view) {
                Logger.getLogger("POI").info("POI is clicked: " + pointType);
                for(WalkingPoint poi: mViewModel.getPOIOfType(pointType))
                    Logger.getLogger("POI").info("Walking points fetched: " + poi.getId());
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getActivity().getApplication())).get(POIFragmentViewModel.class);
    }

}
package com.example.concordia_campus_guide.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.concordia_campus_guide.adapters.PointOfInterestVPAdapter;
import com.example.concordia_campus_guide.view_models.LocationFragmentViewModel;
import com.example.concordia_campus_guide.helper.ViewModelFactory;
import com.example.concordia_campus_guide.interfaces.OnPOIClickListener;
import com.example.concordia_campus_guide.models.PoiType;
import com.example.concordia_campus_guide.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class POIFragment extends Fragment {

    private ViewPager2 poiVP;
    private TabLayout tabLayout;
    private LocationFragmentViewModel mViewModel;

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
        tabLayout = rootView.findViewById(R.id.dotsTab);
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
        new TabLayoutMediator(tabLayout, poiVP, (tab, position) -> {
            //empty, if removed would crash the app since the function cannot be null
        }
        ).attach();
    }

    private OnPOIClickListener getOnClickListener() {
        return (pointType, view) -> mViewModel.setListOfPOI(pointType, getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity(), new ViewModelFactory(this.getActivity().getApplication())).get(LocationFragmentViewModel.class);
    }

}
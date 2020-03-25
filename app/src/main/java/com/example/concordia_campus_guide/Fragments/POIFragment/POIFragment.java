package com.example.concordia_campus_guide.Fragments.POIFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concordia_campus_guide.Adapters.PointOfInterestVPAdapter;
import com.example.concordia_campus_guide.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

public class POIFragment extends Fragment {

    private ViewPager2 poiVP;
    private TabLayout tabLayout;
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
        tabLayout = rootView.findViewById(R.id.dotsTab);
        setupViewPager();
    }

    private void setupViewPager() {
        List<String> services = new ArrayList<>();
        //TESTING PURPOSE, WILL BE REMOVED IN NEXT PR
        services.add("elevators");
        services.add("lounges");
        services.add("bathrooms");
        services.add("water_fountains");
        services.add("escalators");
        services.add("elevators");
        services.add("lounges");
        services.add("bathrooms");
        services.add("water_fountains");
        services.add("escalators");
        PointOfInterestVPAdapter poiViewPagerAdapter = new PointOfInterestVPAdapter(getContext(), services);
        poiVP.setAdapter(poiViewPagerAdapter);
        new TabLayoutMediator(tabLayout, poiVP, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //empty, if removed would crash the app since the function cannot be null
            }
        }
        ).attach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(POIFragmentViewModel.class);
    }

}
package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;

import java.util.List;

public class PathInfoCardFragment extends Fragment {

    private PathInfoCardViewModel mViewModel;
    private LocationFragmentViewModel locationFragmentViewModel;

    public static PathInfoCardFragment newInstance() {
        return new PathInfoCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.path_info_card_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PathInfoCardViewModel.class);
        locationFragmentViewModel = ViewModelProviders.of(this).get(LocationFragmentViewModel.class);
        populateDirections(locationFragmentViewModel.getWalkingPoints());
    }

    public void populateDirections(List<WalkingPoint> walkingPointList) {

    }
}

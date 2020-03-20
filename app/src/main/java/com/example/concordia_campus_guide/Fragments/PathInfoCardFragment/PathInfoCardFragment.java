package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;

import java.util.List;

public class PathInfoCardFragment extends Fragment {

    private PathInfoCardViewModel mViewModel;

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
        populateDirections();
    }

    private List<WalkingPoint> getWalkingPoints() {
//        RoomModel from = (RoomModel) SelectingToFromState.getFrom();
//        RoomModel to = (RoomModel) SelectingToFromState.getTo();
//        PathFinder pf = new PathFinder(getContext(), from, to);
        RoomModel src = new RoomModel(new Double[]{-73.57901685, 45.49761115}, "927", "H-9");
        RoomModel destination = new RoomModel(new Double[]{-73.57854277, 45.49739565}, "918", "H-8");
        PathFinder pf = new PathFinder(getContext(), src, destination);
        return pf.getPathToDestination();

    }

    public void populateDirections(){
        List<WalkingPoint> walkingPoints = getWalkingPoints();

    }
}

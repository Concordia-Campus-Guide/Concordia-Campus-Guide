package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concordia_campus_guide.Adapters.DirectionsRecyclerViewAdapter;
import com.example.concordia_campus_guide.Fragments.LocationFragment.LocationFragmentViewModel;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;

import java.util.ArrayList;
import java.util.List;

public class PathInfoCardFragment extends Fragment {

    private PathInfoCardViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    public static PathInfoCardFragment newInstance() {
        return new PathInfoCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.path_info_card_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.directions_recycle_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<Direction> directionList = new ArrayList<>();
        directionList.add(new Direction());
        directionList.add(new Direction());
        directionList.add(new Direction());
        directionList.add(new Direction());
        directionList.add(new Direction());
        // specify an adapter for recycler view
        mAdapter = new DirectionsRecyclerViewAdapter(getContext(), directionList);
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
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

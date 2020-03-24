package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concordia_campus_guide.Adapters.DirectionsRecyclerViewAdapter;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.TransitType;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PathInfoCardFragment extends Fragment {
    private List<WalkingPoint> walkingPointList;
    private List<Direction> directionList;
    private double totalDuration = 0;

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
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PathInfoCardViewModel.class);
        walkingPointList = getWalkingPoints();
        populateDirections();
        calculateDistance();
    }

    private List<WalkingPoint> getWalkingPoints() {
//        RoomModel from = (RoomModel) SelectingToFromState.getFrom();
//        RoomModel to = (RoomModel) SelectingToFromState.getTo();
//        PathFinder pf = new PathFinder(getContext(), from, to);
        RoomModel src = new RoomModel(new Coordinates(45.49761115, -73.57901685), "963", "H-9");
        RoomModel destination = new RoomModel(new Coordinates(45.49739565, -73.57854277), "863", "H-8");
        PathFinder pf = new PathFinder(AppDatabase.getInstance(getContext()), src, destination);
        return pf.getPathToDestination();

    }

    public void populateDirections() {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.paths_image_buttons);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        for (int i = 0; i < walkingPointList.size(); i++) {
            switch (walkingPointList.get(i).getPointType()) {
                case ELEVATOR:
                    if (i != 0 && walkingPointList.get(i - 1).getPointType() != PointType.ELEVATOR) {
                        createImageButton(layout, layoutParams, R.drawable.ic_elevator_white);
                        setDividerTextView(layout, layoutParams);
                    }
                    break;
                case STAIRS:
                    if (i != 0 && walkingPointList.get(i - 1).getPointType() != PointType.STAIRS) {
                        createImageButton(layout, layoutParams, R.drawable.ic_stairs_white);
                        setDividerTextView(layout, layoutParams);
                    }
                    break;
                case STAFF_ELEVATOR:
                    if (i != 0 && walkingPointList.get(i - 1).getPointType() != PointType.STAFF_ELEVATOR) {
                        createImageButton(layout, layoutParams, R.drawable.ic_staff_elevator_white);
                        setDividerTextView(layout, layoutParams);
                    }
                    break;
            }
        }

        createImageButton(layout, layoutParams, R.drawable.ic_directions_walk_black_24dp);
    }

    public void createImageButton(LinearLayout layout, LinearLayout.LayoutParams layoutParams, int imageId) {
        ImageButton btn = new ImageButton(getContext());
        btn.setLayoutParams(layoutParams);
        btn.setImageResource(imageId);
        btn.setColorFilter(Color.rgb(147, 35, 57));
        btn.setBackgroundColor(getResources().getColor(R.color.toolbarIconColor));
        layout.addView(btn);
    }

    public void setDividerTextView(LinearLayout layout, LinearLayout.LayoutParams layoutParams) {
        TextView divider = new TextView(getContext());
        divider.setText(">");
        divider.setGravity(Gravity.CENTER);
        divider.setLayoutParams(layoutParams);
        layout.addView(divider);
    }

    public void setStartAndEndTime() {
        TextView totalDurationTextView = (TextView) getView().findViewById(R.id.total_time);
        totalDurationTextView.setText(totalDuration + "min");
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        TextView startTimeTextView = (TextView) getView().findViewById(R.id.start_time);
        startTimeTextView.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        TextView endTimeTextView = (TextView) getView().findViewById(R.id.arrival_time);
        Calendar arrivalCalendar = GregorianCalendar.getInstance();
        arrivalCalendar.setTime(new Date());
        arrivalCalendar.add(Calendar.MINUTE, (int) totalDuration);
        endTimeTextView.setText(arrivalCalendar.get(Calendar.HOUR_OF_DAY) + ":" + arrivalCalendar.get(Calendar.MINUTE));
    }

    public void calculateDistance() {
        directionList = new ArrayList<Direction>();
        double totalDistance = 0;
        double distanceBetweenTwoPoints = 0;

        for (int i = 0; i < walkingPointList.size() - 1; i++) {
            WalkingPoint startWalkingPoint = walkingPointList.get(i);
            WalkingPoint endWalkingPoint = walkingPointList.get(i + 1);
            distanceBetweenTwoPoints = getDistanceFromLatLonInKm(startWalkingPoint.getCoordinate().getLatitude(), startWalkingPoint.getCoordinate().getLongitude(), endWalkingPoint.getCoordinate().getLatitude(), startWalkingPoint.getCoordinate().getLongitude());
            totalDistance += distanceBetweenTwoPoints;
            // on average a person walks 5km / hr
            long timeTakenInMinutes = (long) (distanceBetweenTwoPoints * 60 / 5);
            String description = "";
            if (i == 0 && startWalkingPoint.getPointType() == PointType.CLASSROOM) {
                description = "Leave classroom";
            }
            switch (endWalkingPoint.getPointType()) {
                case ELEVATOR:
                    if (i != 0 && startWalkingPoint.getPointType() != endWalkingPoint.getPointType().ELEVATOR) {
                        description = "Walk towards elevator";
                        distanceBetweenTwoPoints = 0;
                    }
                    break;
                case ENTRANCE:
                    description = "Enter building";
                    distanceBetweenTwoPoints = 0;
                    break;
                case STAFF_ELEVATOR:
                    if (i != 0 && startWalkingPoint.getPointType() != endWalkingPoint.getPointType().STAFF_ELEVATOR) {
                        description = "Walk towards staff elevator";
                        distanceBetweenTwoPoints = 0;
                    }
                    break;
                case STAIRS:
                    if (i != 0 && startWalkingPoint.getPointType() != endWalkingPoint.getPointType().STAIRS) {
                        description = "Walk towards stairs";
                        distanceBetweenTwoPoints = 0;
                    }
                    break;
                case CLASSROOM:
                    description = "Walk towards classroom";
                    distanceBetweenTwoPoints = 0;
                    break;
            }
            if (description.length() != 0) {
                Direction direction = new Direction(startWalkingPoint.getCoordinate().getLatLng(), endWalkingPoint.getCoordinate().getLatLng(), TransitType.WALK, description, distanceBetweenTwoPoints);
                direction.setDuration(timeTakenInMinutes);
                directionList.add(direction);
            }
        }

        totalDuration = (totalDistance * 60.0 / 5.0);
        // specify an adapter for recycler view
        mAdapter = new DirectionsRecyclerViewAdapter(getContext(), directionList);
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        this.setStartAndEndTime();
    }

    public double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return d;
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }


}

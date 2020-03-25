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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.Adapters.DirectionsRecyclerViewAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.TransitType;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PathInfoCardFragment extends Fragment {
    private List<WalkingPoint> walkingPointList;
    private List<Direction> directionList;
    private RecyclerView recyclerView;
    List<DirectionWrapper> directionsResults;
    double totalDistance;
    double distanceBetweenPoints;

    public static PathInfoCardFragment newInstance() {
        return new PathInfoCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.path_info_card_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.directions_recycle_view);
        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        directionList = new ArrayList<>();

        Serializable temporaryDirectionsResults = getArguments().getSerializable("directionsResult");
        directionsResults = (ArrayList<DirectionWrapper>) temporaryDirectionsResults;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        walkingPointList = (ArrayList<WalkingPoint>) getArguments().getSerializable("walkingPoints");
        populateIndoorDirectionsList();
        populateOutdoorDirectionsList();
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

    public void setStartAndEndTime(double totalDuration) {
        TextView totalDurationTextView = (TextView) getView().findViewById(R.id.total_time);
        totalDurationTextView.setText(totalDuration + "min");
        Date date = new Date();   // given date
        Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        TextView startTimeTextView = (TextView) getView().findViewById(R.id.start_time);
        startTimeTextView.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        TextView endTimeTextView = (TextView) getView().findViewById(R.id.arrival_time);
        Calendar arrivalCalendar = Calendar.getInstance();
        arrivalCalendar.setTime(new Date());
        arrivalCalendar.add(Calendar.MINUTE, (int) totalDuration);
        endTimeTextView.setText(arrivalCalendar.get(Calendar.HOUR_OF_DAY) + ":" + arrivalCalendar.get(Calendar.MINUTE));
    }

    public void populateIndoorDirectionsList() {
        double totalDuration = 0;
        distanceBetweenPoints = 0;
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.paths_image_buttons);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        for (int i = 0; i < walkingPointList.size() - 1; i++) {
            WalkingPoint startWalkingPoint = walkingPointList.get(i);
            WalkingPoint endWalkingPoint = walkingPointList.get(i + 1);
            setIndoorComponents(startWalkingPoint, endWalkingPoint, layout, layoutParams);
        }

        totalDuration = (totalDistance * 60.0 / 5.0);
        this.setStartAndEndTime(totalDuration);
        createImageButton(layout, layoutParams, R.drawable.ic_directions_walk_black_24dp);
        setRecyclerView();
    }

    public void setIndoorComponents(WalkingPoint startWalkingPoint, WalkingPoint endWalkingPoint, LinearLayout layout, LinearLayout.LayoutParams layoutParams) {
        distanceBetweenPoints += getDistanceFromLatLonInKm(startWalkingPoint.getCoordinate().getLatitude(), startWalkingPoint.getCoordinate().getLongitude(), endWalkingPoint.getCoordinate().getLatitude(), startWalkingPoint.getCoordinate().getLongitude());
        totalDistance += distanceBetweenPoints;
        // on average a person walks 5km / hr
        long timeTakenInMinutes;
        String description = "";
        if (startWalkingPoint.getPointType() == PointType.CLASSROOM) {
            description = "Leave classroom";
            timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
            addIndoorDirection(startWalkingPoint.getCoordinate().getLatLng(), endWalkingPoint.getCoordinate().getLatLng(), description, distanceBetweenPoints, timeTakenInMinutes);
            distanceBetweenPoints = 0;
        }
        PointType pt = endWalkingPoint.getPointType();
        switch (pt) {
            case ELEVATOR:
                if (startWalkingPoint.getPointType() != pt.ELEVATOR) {
                    description = "Walk towards elevator";
                    timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                    addIndoorDirection(startWalkingPoint.getCoordinate().getLatLng(), endWalkingPoint.getCoordinate().getLatLng(), description, distanceBetweenPoints, timeTakenInMinutes);
                    distanceBetweenPoints = 0;
                    createImageButton(layout, layoutParams, R.drawable.ic_elevator_white);
                    setDividerTextView(layout, layoutParams);
                }
                break;
            case ENTRANCE:
                description = "Enter building";
                timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                addIndoorDirection(startWalkingPoint.getCoordinate().getLatLng(), endWalkingPoint.getCoordinate().getLatLng(), description, distanceBetweenPoints, timeTakenInMinutes);
                distanceBetweenPoints = 0;
                break;
            case STAFF_ELEVATOR:
                if (startWalkingPoint.getPointType() != pt.STAFF_ELEVATOR) {
                    description = "Walk towards staff elevator";
                    timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                    addIndoorDirection(startWalkingPoint.getCoordinate().getLatLng(), endWalkingPoint.getCoordinate().getLatLng(), description, distanceBetweenPoints, timeTakenInMinutes);
                    distanceBetweenPoints = 0;
                    createImageButton(layout, layoutParams, R.drawable.ic_staff_elevator_white);
                    setDividerTextView(layout, layoutParams);
                }
                break;
            case STAIRS:
                if (startWalkingPoint.getPointType() != pt.STAIRS) {
                    description = "Walk towards stairs";
                    timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                    addIndoorDirection(startWalkingPoint.getCoordinate().getLatLng(), endWalkingPoint.getCoordinate().getLatLng(), description, distanceBetweenPoints, timeTakenInMinutes);
                    distanceBetweenPoints = 0;
                    createImageButton(layout, layoutParams, R.drawable.ic_stairs_white);
                    setDividerTextView(layout, layoutParams);
                }
                break;
            case CLASSROOM:
                description = "Walk towards classroom";
                timeTakenInMinutes = (long) (distanceBetweenPoints * 60 / 5);
                addIndoorDirection(startWalkingPoint.getCoordinate().getLatLng(), endWalkingPoint.getCoordinate().getLatLng(), description, distanceBetweenPoints, timeTakenInMinutes);
                distanceBetweenPoints = 0;
                break;
            default:
                break;
        }
    }

    public void addIndoorDirection(LatLng startLatLng, LatLng endLatLng, String description, double distance, double minutes) {
        Direction direction = new Direction(startLatLng, endLatLng, TransitType.WALK, description, distance);
        direction.setDuration(minutes);
        directionList.add(direction);
    }

    public double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c; // Distance in km
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    public void populateOutdoorDirectionsList() {
        for (DirectionWrapper direction : directionsResults) {
            double minute = direction.getDirection().getDuration() / 60;
            direction.getDirection().setDuration(minute);
            directionList.add(direction.getDirection());
        }
        setRecyclerView();
    }

    public void setRecyclerView() {
        // specify an adapter for recycler view
        RecyclerView.Adapter mAdapter = new DirectionsRecyclerViewAdapter(getContext(), directionList);
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


}

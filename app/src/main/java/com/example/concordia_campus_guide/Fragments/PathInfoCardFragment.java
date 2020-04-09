package com.example.concordia_campus_guide.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concordia_campus_guide.Adapters.DirectionsRecyclerViewAdapter;
import com.example.concordia_campus_guide.Models.PathInfoCard;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.PointType;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.example.concordia_campus_guide.R;
import com.example.concordia_campus_guide.ViewModels.PathInfoCardViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PathInfoCardFragment extends Fragment {
    private RecyclerView recyclerView;
    List<PathInfoCard> directionsResults;
    private double totalDuration;

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
        updateDirectionResults();
        return view;
    }

    private void updateDirectionResults() {
        Serializable temporaryDirectionsResults = getArguments().getSerializable("directionsResult");
        directionsResults = (ArrayList<PathInfoCard>) temporaryDirectionsResults;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        totalDuration = 0;
        //wait sufficient time for the google outdoor direction api callback
        (new Handler()).postDelayed(this::drawIndoorOutdoorInfo, 2500);
    }

    public void drawIndoorOutdoorInfo() {
        LinearLayout layout = getView().findViewById(R.id.paths_image_buttons);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        String type = "";
        for (int i = 0; i < directionsResults.size(); i++) {
            totalDuration += directionsResults.get(i).getDuration();
            if(!directionsResults.get(i).getType().equals(type)){
                type = directionsResults.get(i).getType();
                if (i!=0) {
                    setDividerTextView(layout, layoutParams);
                }
                makeIcon(layout, layoutParams, type);
            }
        }
        setStartAndEndTime();
        setRecyclerView();
    }

    private void makeIcon(LinearLayout layout, LinearLayout.LayoutParams layoutParams, String type) {
        int icon = this.getResources().getIdentifier("ic_directions_"+type.toLowerCase(),"drawable", this.getActivity().getPackageName());
        createImageButton(layout, layoutParams, icon);
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
        divider.setLayoutParams(layoutParams);
        divider.setGravity(Gravity.CENTER);
        layout.addView(divider);
    }

    public void setStartAndEndTime() {
        TextView totalDurationTextView = (TextView) getView().findViewById(R.id.total_time);
        totalDurationTextView.setText(Math.ceil(totalDuration) + "min");
        Date date = new Date();   // given date
        Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        TextView startTimeTextView = (TextView) getView().findViewById(R.id.start_time);
        startTimeTextView.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        TextView endTimeTextView = (TextView) getView().findViewById(R.id.arrival_time);
        Calendar arrivalCalendar = Calendar.getInstance();
        arrivalCalendar.setTime(new Date());
        arrivalCalendar.add(Calendar.MINUTE, (int) totalDuration);
        endTimeTextView.setText(String.format("%02d:%02d", arrivalCalendar.get(Calendar.HOUR_OF_DAY), arrivalCalendar.get(Calendar.MINUTE)));
    }

    public void setRecyclerView() {
        updateDirectionResults();
        // specify an adapter for recycler view
        RecyclerView.Adapter mAdapter = new DirectionsRecyclerViewAdapter(getContext(), directionsResults);
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


}

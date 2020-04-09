package com.example.concordia_campus_guide.Fragments;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concordia_campus_guide.Adapters.DirectionsRecyclerViewAdapter;
import com.example.concordia_campus_guide.Models.PathInfoCard;
import com.example.concordia_campus_guide.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PathInfoCardFragment extends Fragment {
    private RecyclerView mDestinationRv;
    private List<PathInfoCard> mDirectionsResults;
    private double totalDuration;

    public static PathInfoCardFragment newInstance() {
        return new PathInfoCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.path_info_card_fragment, container, false);

        initComponents(view);
        updateDirectionResults();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //wait sufficient time for the google outdoor direction api callback
        (new Handler()).postDelayed(this::drawIndoorOutdoorInfo, 2500);
    }

    private void initComponents(View view) {
        mDestinationRv = view.findViewById(R.id.directions_recycle_view);
        setupDestinationRv(view);
    }

    private void setupDestinationRv(View view) {
        mDestinationRv.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mDestinationRv.setLayoutManager(layoutManager);
    }

    private void updateDirectionResults() {
        Serializable temporaryDirectionsResults = getArguments().getSerializable("directionsResult");
        mDirectionsResults = (ArrayList<PathInfoCard>) temporaryDirectionsResults;
    }

    public void drawIndoorOutdoorInfo() {
        LinearLayout layout = getView().findViewById(R.id.paths_image_buttons);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        String directionResultType = "";

        for (int i = 0; i < mDirectionsResults.size(); i++) {

            totalDuration += mDirectionsResults.get(i).getDuration();
            if (!mDirectionsResults.get(i).getType().equals(directionResultType)) {
                directionResultType = mDirectionsResults.get(i).getType();
                if (i != 0) {
                    setDividerTextView(layout, layoutParams);
                }
                setupDirectionIcon(layout, layoutParams, directionResultType);
            }
        }
        setStartAndEndTime();
        setRecyclerView();
    }

    private void setupDirectionIcon(LinearLayout layout, LinearLayout.LayoutParams layoutParams, String type) {
        int directionIcon = this.getResources().getIdentifier("ic_directions_" + type.toLowerCase(), "drawable", this.getActivity().getPackageName());
        createDirectionImageButton(layout, layoutParams, directionIcon);
    }

    public void createDirectionImageButton(LinearLayout layout, LinearLayout.LayoutParams layoutParams, int imageId) {
        ImageButton btn = new ImageButton(getContext());
        btn.setLayoutParams(layoutParams);
        btn.setImageResource(imageId);
        btn.setColorFilter(R.color.colorAppTheme);
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
        TextView totalDurationTv = getView().findViewById(R.id.total_time);

        totalDurationTv.setText(Math.ceil(totalDuration) + getResources().getString(R.string.minutes));

        setupStartTimeTv();

        setupArrivalTimeTv();
    }

    private void setupArrivalTimeTv() {
        TextView endTimeTextView = getView().findViewById(R.id.arrival_time);

        Calendar arrivalCalendar = Calendar.getInstance();
        arrivalCalendar.setTime(new Date());
        arrivalCalendar.add(Calendar.MINUTE, (int) totalDuration);

        endTimeTextView.setText(String.format("%02d:%02d", arrivalCalendar.get(Calendar.HOUR_OF_DAY), arrivalCalendar.get(Calendar.MINUTE)));
    }

    private void setupStartTimeTv() {
        TextView startTimeTextView = (TextView) getView().findViewById(R.id.start_time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        startTimeTextView.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
    }

    public void setRecyclerView() {
        updateDirectionResults();

        RecyclerView.Adapter destinationRvAdapter = new DirectionsRecyclerViewAdapter(getContext(), mDirectionsResults);

        mDestinationRv.setAdapter(destinationRvAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mDestinationRv.getContext(),
                DividerItemDecoration.VERTICAL);
        mDestinationRv.addItemDecoration(dividerItemDecoration);
    }


}

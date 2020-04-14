package com.example.concordia_campus_guide.fragments;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concordia_campus_guide.adapters.DirectionsRecyclerViewAdapter;
import com.example.concordia_campus_guide.models.PathInfoCard;
import com.example.concordia_campus_guide.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class PathInfoCardFragment extends Fragment {
    private RecyclerView mDestinationRv;
    private List<PathInfoCard> mDirectionsResults;
    private double totalDuration;

    public static PathInfoCardFragment newInstance() {
        return new PathInfoCardFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.path_info_card_fragment, container, false);

        initComponents(view);
        updateDirectionResults();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // wait sufficient time for the google outdoor direction api callback
        (new Handler()).postDelayed(this::drawIndoorOutdoorInfo, 2500);
    }

    private void initComponents(final View view) {
        mDestinationRv = view.findViewById(R.id.directions_recycle_view);
        setupDestinationRv();
    }

    private void setupDestinationRv() {
        mDestinationRv.setHasFixedSize(false);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mDestinationRv.setLayoutManager(layoutManager);
    }

    private void updateDirectionResults() {
        final Serializable temporaryDirectionsResults = getArguments().getSerializable("directionsResult");
        mDirectionsResults = (ArrayList<PathInfoCard>) temporaryDirectionsResults;
    }

    public void drawIndoorOutdoorInfo() {
        final LinearLayout layout = getView().findViewById(R.id.paths_image_buttons);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                WRAP_CONTENT, MATCH_PARENT, 1);
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

    private void setupDirectionIcon(final LinearLayout layout, final LinearLayout.LayoutParams layoutParams,
            final String type) {
        final int directionIcon = this.getResources().getIdentifier("ic_directions_" + type.toLowerCase(), "drawable",
                this.getActivity().getPackageName());
        createDirectionImageButton(layout, layoutParams, directionIcon);
    }

    public void createDirectionImageButton(final LinearLayout layout, final LinearLayout.LayoutParams layoutParams,
            final int imageId) {
        final ImageButton btn = new ImageButton(getContext());
        btn.setLayoutParams(layoutParams);
        btn.setImageResource(imageId);
        btn.setColorFilter(getResources().getColor(R.color.colorAppTheme));
        btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.toolbarIconColor));
        layout.addView(btn);
    }

    public void setDividerTextView(final LinearLayout layout, final LinearLayout.LayoutParams layoutParams) {
        final TextView divider = new TextView(getContext());
        divider.setText(">");
        divider.setLayoutParams(layoutParams);
        divider.setGravity(Gravity.CENTER);
        layout.addView(divider);
    }

    public void setStartAndEndTime() {
        final TextView totalDurationTv = getView().findViewById(R.id.total_time);

        totalDurationTv.setText(Math.ceil(totalDuration) + getResources().getString(R.string.minutes));

        setupStartTimeTv();

        setupArrivalTimeTv();
    }

    private void setupArrivalTimeTv() {
        final TextView endTimeTextView = getView().findViewById(R.id.arrival_time);

        final Calendar arrivalCalendar = Calendar.getInstance();
        arrivalCalendar.setTime(new Date());
        arrivalCalendar.add(Calendar.MINUTE, (int) totalDuration);

        endTimeTextView.setText(String.format("%02d:%02d", arrivalCalendar.get(Calendar.HOUR_OF_DAY),
                arrivalCalendar.get(Calendar.MINUTE)));
    }

    private void setupStartTimeTv() {
        final TextView startTimeTextView = (TextView) getView().findViewById(R.id.start_time);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        startTimeTextView
                .setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
    }

    public void setRecyclerView() {
        updateDirectionResults();

        final RecyclerView.Adapter destinationRvAdapter = new DirectionsRecyclerViewAdapter(getContext(),
                mDirectionsResults);

        mDestinationRv.setAdapter(destinationRvAdapter);
        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mDestinationRv.getContext(),
                DividerItemDecoration.VERTICAL);
        mDestinationRv.addItemDecoration(dividerItemDecoration);
    }


}

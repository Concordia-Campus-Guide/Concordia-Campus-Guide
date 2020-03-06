package com.example.concordia_campus_guide.Fragments;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.R;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    private CalendarViewModel mViewModel;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CALENDAR}, 101);

        } else {
            ArrayList<CalendarEvent> events = new ArrayList<>(mViewModel.getCalendarEvents(getContext()));
            for (CalendarEvent event : events) {
                System.out.println(event.toString());
            }
        }
    }


}

package com.example.concordia_campus_guide.Fragments.CalendarFragment;

import android.database.Cursor;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalendarFragment extends Fragment {

    private CalendarViewModel mViewModel;
    private TextView nextClass;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);

        nextClass = view.findViewById(R.id.next_class_directions);

        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);

        if (!hasReadPermission()) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CALENDAR}, 101);
        } else {
            Cursor cursor = mViewModel.getCalendarCursor(getContext());
            CalendarEvent event = mViewModel.getCalendarEvent(cursor);

            if(event != null){
                Date eventDate = new Date((Long.parseLong(event.getStartTime())));
                String timeUntil = getTimeUntilString(eventDate.getTime(), System.currentTimeMillis());

                nextClass.setText(event.getTitle() + " in " + timeUntil + " @ " + event.getLocation());
            }else{
                nextClass.setText("No classes today");
            }
        }
    }

    private boolean hasReadPermission(){
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED;
    }

    private String getTimeUntilString(long eventTime, long currentTime){
        long differenceInMillis = eventTime - currentTime;

        String timeUntil = String.format("%02d hours and %02d minutes",
                TimeUnit.MILLISECONDS.toHours(differenceInMillis),
                TimeUnit.MILLISECONDS.toMinutes(differenceInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(differenceInMillis)));

        return timeUntil;
    }
}

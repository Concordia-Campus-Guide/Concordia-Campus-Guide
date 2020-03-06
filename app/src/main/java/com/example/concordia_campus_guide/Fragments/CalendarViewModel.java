package com.example.concordia_campus_guide.Fragments;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Instances;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Models.CalendarEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarViewModel extends ViewModel {

    // The indices for the projection array above.
    private static final int PROJECTION_TITLE_INDEX = 0;
    private static final int PROJECTION_LOCATION_INDEX = 1;

    public static final String[] INSTANCE_PROJECTION = new String[] {
            Instances.TITLE,
            Instances.EVENT_LOCATION,
            Instances.DURATION
    };

    public List<CalendarEvent> getCalendarEvents(Context context) {
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        long startTime = new Date().getTime();
        long endTime = startTime + 28800000;
        ContentUris.appendId(eventsUriBuilder, startTime);
        ContentUris.appendId(eventsUriBuilder, endTime);
        Uri eventsUri = eventsUriBuilder.build();
        Cursor cursor = null;
        cursor = context.getContentResolver().query(
                eventsUri,
                INSTANCE_PROJECTION,
                null,
                null,
                CalendarContract.Instances.DTSTART + " ASC"
        );

        ArrayList<CalendarEvent> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            String eventTitle = null;
            String eventLocation = null;

            eventTitle = cursor.getString(PROJECTION_TITLE_INDEX);
            eventLocation = cursor.getString(PROJECTION_LOCATION_INDEX);

            list.add(new CalendarEvent(eventTitle, eventLocation));
        }

        return list;
    }
}

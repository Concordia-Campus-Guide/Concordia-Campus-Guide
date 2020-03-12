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
    private static final int PROJECTION_START_INDEX = 2;

    public static final String[] INSTANCE_PROJECTION = new String[] {
            Instances.TITLE,
            Instances.EVENT_LOCATION,
            Instances.DTSTART
    };

    public Cursor getCalendarCursor(Context context) {
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        long startTime = new Date().getTime();
        // TODO: for #17 set the proper time we want, currently set to get classes in the next +8 hours.
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
                // TODO: for #17 convert DTSTART from full UTC date to only the time in order to get correct starting time of class.
                CalendarContract.Instances.DTSTART + " ASC"
        );

        return cursor;
    }

    public List<CalendarEvent> getCalendarEvents(Cursor cursor) {
        ArrayList<CalendarEvent> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            String eventTitle = null;
            String eventLocation = null;
            String eventStart = null;

            eventTitle = cursor.getString(PROJECTION_TITLE_INDEX);
            eventLocation = cursor.getString(PROJECTION_LOCATION_INDEX);
            eventStart = cursor.getString(PROJECTION_START_INDEX);

            list.add(new CalendarEvent(eventTitle, eventLocation, eventStart));
        }

        return list;
    }
}

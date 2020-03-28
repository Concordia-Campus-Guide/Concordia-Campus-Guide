package com.example.concordia_campus_guide.Models.Helpers;

import android.Manifest;
import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Instances;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Activities.SearchActivity;
import com.example.concordia_campus_guide.Models.CalendarEvent;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CalendarViewModel extends AndroidViewModel {
    Context context;

    public CalendarViewModel(@NonNull Application application){
        super(application);
        context = application.getApplicationContext();
    }

    // The indices for the projection array above.
    private static final int PROJECTION_TITLE_INDEX = 0;
    private static final int PROJECTION_LOCATION_INDEX = 1;
    private static final int PROJECTION_START_INDEX = 2;

    public static final String[] INSTANCE_PROJECTION = new String[] {
            Instances.TITLE,
            Instances.EVENT_LOCATION,
            Instances.DTSTART
    };

    public CalendarEvent getEvent(AppCompatActivity activity) {

        if (!hasReadPermission()) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_CALENDAR}, 101);
        } else {

            Cursor cursor = getCalendarCursor();
            return getCalendarEvent(cursor);
        }
        return null;
    }

    public String getNextClassString(CalendarEvent event){
        String nextClassString = "";
        if(incorrectlyFormatted(event.getLocation())){
            return "Event is incorrectly formatted";
        }
        if(event != null){
            Date eventDate = new Date((Long.parseLong(event.getStartTime())));
            String timeUntil = getTimeUntilString(eventDate.getTime(), System.currentTimeMillis());
            nextClassString = event.getTitle() +  " in " + timeUntil;
        }
        return  nextClassString;
    }

    public boolean incorrectlyFormatted(String location) {
        String pattern = "([A-z]+-\\d+, \\d+)";
        return !location.matches(pattern);
    }

    public Cursor getCalendarCursor() {
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI
                .buildUpon();
        long startTime = new Date().getTime();
        long endTime = startTime + 28800000;
        ContentUris.appendId(eventsUriBuilder, startTime);
        ContentUris.appendId(eventsUriBuilder, endTime);
        Uri eventsUri = eventsUriBuilder.build();
        Cursor cursor = context.getContentResolver().query(
                eventsUri,
                INSTANCE_PROJECTION,
                null,
                null,
                Instances.BEGIN + " ASC"
        );

        return cursor;
    }

    public CalendarEvent getCalendarEvent(Cursor cursor) {
        while (cursor.moveToNext()) {
           String eventTitle = cursor.getString(PROJECTION_TITLE_INDEX);
           String eventLocation = cursor.getString(PROJECTION_LOCATION_INDEX);
           String eventStart = cursor.getString(PROJECTION_START_INDEX);

           if(eventTitle.contains("Lecture: ")||eventTitle.contains("Tutorial: ") || eventTitle.contains("Lab: ")){
               return new CalendarEvent(eventTitle, eventLocation, eventStart);
           }
        }
        return null;
    }

    private boolean hasReadPermission(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)
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

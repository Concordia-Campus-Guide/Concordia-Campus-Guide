package com.example.concordia_campus_guide.Models.Helpers;

import android.Manifest;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
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

import com.example.concordia_campus_guide.Models.CalendarEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CalendarViewModel extends AndroidViewModel {
    Context context;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public CalendarViewModel(@NonNull Application application){
        super(application);
        context = application.getApplicationContext();
    }

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_TITLE_INDEX = 1;
    private static final int PROJECTION_LOCATION_INDEX = 2;
    private static final int PROJECTION_START_INDEX = 3;

    protected static final String[] INSTANCE_PROJECTION = new String[] {
            Instances.EVENT_ID,
            Instances.TITLE,
            Instances.EVENT_LOCATION,
            Instances.DTSTART
    };

    public CalendarEvent getEvent(AppCompatActivity activity) {

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (!hasReadPermission()) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALENDAR);
        }
        if(!hasWritePermission()){
            listPermissionsNeeded.add(Manifest.permission.WRITE_CALENDAR);
        }
        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }

        if(hasWritePermission() && hasReadPermission()){
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

        Date eventDate = new Date((Long.parseLong(event.getStartTime())));
        String timeUntil = getTimeUntilString(eventDate.getTime(), System.currentTimeMillis());
        nextClassString = event.getTitle() +  " in " + timeUntil;

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
        return context.getContentResolver().query(
                eventsUri,
                INSTANCE_PROJECTION,
                null,
                null,
                Instances.BEGIN + " ASC"
        );
    }

    public CalendarEvent getCalendarEvent(Cursor cursor) {
        while (cursor.moveToNext()) {
           long eventID = cursor.getLong(PROJECTION_ID_INDEX);
           String eventTitle = cursor.getString(PROJECTION_TITLE_INDEX);
           String eventLocation = cursor.getString(PROJECTION_LOCATION_INDEX);
           String eventStart = cursor.getString(PROJECTION_START_INDEX);

           if(eventTitle.contains("Lecture: ")||eventTitle.contains("Tutorial: ") || eventTitle.contains("Lab: ")){
               //add uri to description of event
               addDescriptionToEvent(eventID, eventLocation);
               return new CalendarEvent(eventTitle, eventLocation, eventStart);
           }
        }
        return null;
    }

    private void addDescriptionToEvent(Long eventId, String eventLocation){
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();

        String roomCode = eventLocation.split(",")[1].trim();
        String floorCode = eventLocation.split(",")[0].trim();
        String description = "<a href=\"app://conumaps?room="+roomCode+"&floor="+floorCode+"\">Go to ConUMaps</a>";

        values.put(CalendarContract.Events.DESCRIPTION, description);

        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);

        int row = cr.update(uri, values, null, null);
    }

    private boolean hasReadPermission(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasWritePermission(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)
                == PackageManager.PERMISSION_GRANTED;
    }

    public String getTimeUntilString(long eventTime, long currentTime){
        long differenceInMillis = eventTime - currentTime;

        return String.format("%02d hours and %02d minutes",
                TimeUnit.MILLISECONDS.toHours(differenceInMillis),
                TimeUnit.MILLISECONDS.toMinutes(differenceInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(differenceInMillis)));
    }
}

package com.example.concordia_campus_guide.models.helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Instances;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.models.CalendarEvent;
import com.example.concordia_campus_guide.R;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.provider.CalendarContract.Events.DESCRIPTION;
import static android.provider.CalendarContract.Instances.DTSTART;
import static android.provider.CalendarContract.Instances.EVENT_ID;
import static android.provider.CalendarContract.Instances.EVENT_LOCATION;
import static android.provider.CalendarContract.Instances.TITLE;

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
            EVENT_ID,
            TITLE,
            EVENT_LOCATION,
            DTSTART
    };

    public CalendarEvent getEvent(AppCompatActivity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(ClassConstants.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!hasReadPermission() && !hasWritePermission() && !sharedPreferences.getBoolean(String.valueOf(R.string.ignore),false)) {
            askForPermission(activity);
            editor.putBoolean(String.valueOf(R.string.ignore),true);
            editor.commit();
        }

        if(hasWritePermission() && hasReadPermission()){
            Cursor cursor = getCalendarCursor();
            return getCalendarEvent(cursor);
        }
        return null;
    }

    public String getNextClassString(Context context, CalendarEvent event){
        String nextClassString = "";
        if(incorrectlyFormatted(event.getLocation())){
            return context.getResources().getString(R.string.incorrect_format_event);
        }

        Date eventDate = new Date((Long.parseLong(event.getStartTime())));
        String timeUntil = getTimeUntilString(context, eventDate.getTime(), System.currentTimeMillis());
        nextClassString = event.getTitle() + " " + context.getResources().getString(R.string.in) + " " + timeUntil;

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

        values.put(DESCRIPTION, description);

        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);

        cr.update(uri, values, null, null);
    }

    public boolean hasReadPermission(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)
                == PERMISSION_GRANTED;
    }

    public boolean hasWritePermission(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)
                == PERMISSION_GRANTED;
    }

    @SuppressLint("DefaultLocale")
    public String getTimeUntilString(Context context, long eventTime, long currentTime){
        long differenceInMillis = eventTime - currentTime;

        return String.format("%02d %s %s %02d minutes",
                TimeUnit.MILLISECONDS.toHours(differenceInMillis),
                context.getResources().getString(R.string.hours),
                context.getResources().getString(R.string.and),
                TimeUnit.MILLISECONDS.toMinutes(differenceInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(differenceInMillis)));
    }


    public void askForPermission(AppCompatActivity activity){
        if (!hasReadPermission() && !hasWritePermission()) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_CALENDAR,Manifest.permission.READ_CALENDAR}, 101);
        }
    }
}

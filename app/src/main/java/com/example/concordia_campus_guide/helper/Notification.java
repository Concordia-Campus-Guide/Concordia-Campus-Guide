package com.example.concordia_campus_guide.helper;

import android.os.Handler;

import com.example.concordia_campus_guide.activities.MainActivity;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.global.NotificationStatus;
import com.example.concordia_campus_guide.models.CalendarEvent;
import com.example.concordia_campus_guide.models.Helpers.CalendarViewModel;
import com.example.concordia_campus_guide.models.RoomModel;

public class Notification {
    private MainActivity mainActivity;
    private AppDatabase appDatabase;
    private CalendarEvent previousCalendarEvent;
    private NotificationStatus notifyUser;

    public Notification(MainActivity mainActivity, AppDatabase appDatabase) {
        this.mainActivity = mainActivity;
        this.appDatabase = appDatabase;
        this.previousCalendarEvent = new CalendarEvent("", "", "");
        notifyUser = NotificationStatus.getInstance();
    }

    public void checkUpCalendarEvery5Minutes() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CalendarEvent calendarEvent = getNextClassCalendar();
                if (calendarEvent != null && notifyUser.getNotifyUser() && !calendarEvent.getTitle().equalsIgnoreCase(previousCalendarEvent.getTitle())) {
                    notifyUser.setNotifyUser(false);
                    previousCalendarEvent = calendarEvent;
                    mainActivity.popUp(calendarEvent);
                }
                handler.postDelayed(this, 6000);
            }
        }, 6000);
    }

    public boolean validateCalendarEvent(CalendarEvent calendarEvent) {
        CalendarViewModel calendarViewModel = new CalendarViewModel(mainActivity.getApplication());
        return calendarViewModel.incorrectlyFormatted(calendarEvent.getLocation());
    }

    public CalendarEvent getNextClassCalendar() {
        CalendarViewModel calendarViewModel = new CalendarViewModel(mainActivity.getApplication());
        return calendarViewModel.getEvent(mainActivity);
    }

    public RoomModel getRoom(String location) {
        if (location == null) {
            return null;
        }

        int indexOfSeparation = location.indexOf(',');
        String floorCode = location.substring(0, indexOfSeparation);
        String roomCode = location.substring(indexOfSeparation + 2);

        return appDatabase.roomDao().getRoomByRoomCodeAndFloorCode(roomCode, floorCode);
    }

    public boolean roomExistsInDb(String location) {
        if (location == null) {
            return false;
        }

        int indexOfSeparation = location.indexOf(',');
        String floorCode = location.substring(0, indexOfSeparation);
        String roomCode = location.substring(indexOfSeparation + 2);

        RoomModel room = appDatabase.roomDao().getRoomByRoomCodeAndFloorCode(roomCode, floorCode);

        return room != null;
    }
}

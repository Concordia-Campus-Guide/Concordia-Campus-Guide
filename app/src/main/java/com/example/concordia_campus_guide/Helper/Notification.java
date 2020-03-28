package com.example.concordia_campus_guide.Helper;

import android.os.Handler;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Global.NotificationStatus;
import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.Models.Helpers.CalendarViewModel;
import com.example.concordia_campus_guide.Models.RoomModel;

public class Notification {
    private MainActivity mainActivity;
    private AppDatabase appDatabase;
    private CalendarEvent previousCalendarEvent;
    private NotificationStatus notifyUser;

    public Notification(MainActivity mainActivity, AppDatabase appDatabase){
        this.mainActivity =  mainActivity;
        this.appDatabase  = appDatabase;
        this.previousCalendarEvent = new CalendarEvent("","","");
        notifyUser = NotificationStatus.getInstance();
    }

    public void checkUpCalendarEvery5Minutes() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                CalendarEvent calendarEvent = getNextClassCalendar();
                if(calendarEvent  != null && notifyUser.getNotifyUser()){
                    if(!calendarEvent.getTitle().equalsIgnoreCase(previousCalendarEvent.getTitle())){
                        notifyUser.setNotifyUser(false);
                        previousCalendarEvent = calendarEvent;
                        mainActivity.popUp(calendarEvent);
                    }
                }
                handler.postDelayed(this, 3000);
            }
        }, 3000);
    }

    public boolean validateCalendarEvent(CalendarEvent calendarEvent){
        CalendarViewModel calendarViewModel = new CalendarViewModel(mainActivity.getApplication());
        return calendarViewModel.incorrectlyFormatted(calendarEvent.getLocation());
    }

    public CalendarEvent getNextClassCalendar(){
        CalendarViewModel calendarViewModel = new CalendarViewModel(mainActivity.getApplication());
        return  calendarViewModel.getEvent(mainActivity);
    }

    public RoomModel getRoom(String location){
        if(location == null) { return null; }

        int indexOfSeparation = location.indexOf(',');
        String floorCode = location.substring(0,indexOfSeparation);
        String roomCode  = location.substring(indexOfSeparation+2);

        return appDatabase.roomDao().getRoomByRoomCodeAndFloorCode(roomCode,floorCode);
    }

    public boolean roomExistsInDb(String location){
        if(location == null) { return false; }

        int indexOfSeparation = location.indexOf(',');
        String floorCode = location.substring(0,indexOfSeparation);
        String roomCode  = location.substring(indexOfSeparation+2);

        RoomModel room = appDatabase.roomDao().getRoomByRoomCodeAndFloorCode(roomCode,floorCode);

        if(room != null){
            return true;
        } else{
          return false;
        }
    }
}

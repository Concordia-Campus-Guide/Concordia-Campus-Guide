package com.example.concordia_campus_guide.Helper;

import android.os.Handler;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.Models.Helpers.CalendarViewModel;
import com.example.concordia_campus_guide.Models.RoomModel;

public class Notification {
    private MainActivity mainActivity;
    private AppDatabase appDatabase;
    private CalendarEvent previousCalendarEvent;
    private CalendarViewModel calendarViewModel;


    public Notification(MainActivity mainActivity, AppDatabase appDatabase){
        this.mainActivity =  mainActivity;
        this.appDatabase  = appDatabase;
        this.previousCalendarEvent = new CalendarEvent("","","");
        this.calendarViewModel = new CalendarViewModel(mainActivity.getApplication());
    }

    public void checkUpCalendarEvery5Minutes() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                CalendarEvent calendarEvent = getNextClassCalendar();
                if(calendarEvent  != null && validateCalendarEvent(calendarEvent)){
                    if(!calendarEvent.getTitle().equalsIgnoreCase(previousCalendarEvent.getTitle())){
                        previousCalendarEvent = calendarEvent;
                        mainActivity.popUp(calendarEvent);
                    }
                }
                handler.postDelayed(this, 3000);
            }
        }, 3000);
    }

    public boolean validateCalendarEvent(CalendarEvent calendarEvent){
        return calendarViewModel.incorrectlyFormatted(calendarEvent.getLocation());
    }

    public CalendarEvent getNextClassCalendar(){
        return  calendarViewModel.getEvent(mainActivity);
    }

    public RoomModel getRoom(String location){
        if(location == null) { return null; }

        int indexOfSeparation = location.indexOf(',');
        String floorCode = location.substring(0,indexOfSeparation);
        String roomCode  = location.substring(indexOfSeparation+2);

        return appDatabase.roomDao().getRoomByRoomCodeAndFloorCode(roomCode,floorCode);
    }
}

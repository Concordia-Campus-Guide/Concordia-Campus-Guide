package com.example.concordia_campus_guide.Helper;

import android.os.Handler;

import com.example.concordia_campus_guide.Activities.MainActivity;
import com.example.concordia_campus_guide.Models.CalendarEvent;
import com.example.concordia_campus_guide.Models.Helpers.CalendarViewModel;



public class Notification {
    MainActivity mainActivity;

    public Notification(MainActivity mainActivity){
        this.mainActivity =  mainActivity;
    }

    public void checkUpCalendarEvery5Minutes() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                CalendarEvent calendarEvent = getNextClassCalendar();
                if(calendarEvent  != null &&  Long.parseLong(calendarEvent.getStartTime()) <= 3600000){
                    mainActivity.popUp(calendarEvent);
                }
                handler.postDelayed(this, 30000);
            }
        }, 30000);
    }

    public CalendarEvent getNextClassCalendar(){
        CalendarViewModel calendarViewModel = new CalendarViewModel(mainActivity.getApplication());
        return  calendarViewModel.getEvent(mainActivity);
    }




}

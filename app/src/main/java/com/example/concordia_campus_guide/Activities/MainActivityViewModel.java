package com.example.concordia_campus_guide.Activities;

import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivityViewModel extends ViewModel {

    public String displayTimeToNextClass(String date) {
        Date eventDate = new Date((Long.parseLong(date)));
        long differenceInMillis = eventDate.getTime() - System.currentTimeMillis();
        String timeUntil = timeUntilMethod(differenceInMillis);
        return cleanTimeDisplay(timeUntil);
    }

    public String cleanTimeDisplay(String timeUntil) {
        String temp = timeUntil.substring(0, timeUntil.indexOf("and"));
        if (temp.contains("00")) {
            return timeUntil.substring(timeUntil.indexOf("and") + 3);
        } else {
            return timeUntil;
        }
    }

    public String timeUntilMethod(long differenceInMillis) {
        return String.format("%02d hours and %02d minutes",
                TimeUnit.MILLISECONDS.toHours(differenceInMillis),
                TimeUnit.MILLISECONDS.toMinutes(differenceInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(differenceInMillis)));
    }
}

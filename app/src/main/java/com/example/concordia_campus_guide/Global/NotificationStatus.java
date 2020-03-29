package com.example.concordia_campus_guide.Global;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Helper.Notification;

import androidx.room.Room;

public class NotificationStatus {
    private static NotificationStatus instance;
    private boolean notifyUser;

    private NotificationStatus(){
        notifyUser = true;
    }

    public static NotificationStatus getInstance(){
        if (instance == null) {
            instance = new NotificationStatus();
        }
        return instance;
    }
    public boolean getNotifyUser() {
        return notifyUser;
    }

    public void setNotifyUser(boolean notifyUser) {
        this.notifyUser = notifyUser;
    }

}

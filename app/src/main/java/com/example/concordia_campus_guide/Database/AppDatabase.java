package com.example.concordia_campus_guide.Database;

import android.content.Context;

import com.example.concordia_campus_guide.Database.Converters.StringListConverter;
import com.example.concordia_campus_guide.Database.Daos.FloorDao;
import com.example.concordia_campus_guide.Models.Floor;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Floor.class}, version = 1)
@TypeConverters({StringListConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "ConUMaps.db";
    private static AppDatabase instance;

    public abstract FloorDao floorDao();
    
    private AppDatabase () {}

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME).build();
        }
        return instance;
    }


}
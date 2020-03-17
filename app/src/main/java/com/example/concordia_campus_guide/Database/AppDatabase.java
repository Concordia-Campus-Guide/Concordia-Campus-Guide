package com.example.concordia_campus_guide.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.concordia_campus_guide.Database.Converters.StringListConverter;
import com.example.concordia_campus_guide.Database.Daos.BuildingDao;
import com.example.concordia_campus_guide.Database.Daos.FloorDao;
import com.example.concordia_campus_guide.Database.Daos.RoomDao;
import com.example.concordia_campus_guide.Database.Daos.ShuttleDao;
import com.example.concordia_campus_guide.Database.Daos.WalkingPointDao;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.Models.WalkingPoint;

@Database(entities = {Building.class, Floor.class, RoomModel.class, WalkingPoint.class,  Shuttle.class},exportSchema = false, version = 1)
@TypeConverters({StringListConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "ConUMaps.db";
    private static AppDatabase instance;

    public abstract FloorDao floorDao();
    public abstract RoomDao roomDao();
    public abstract BuildingDao buildingDao();
    public abstract ShuttleDao shuttleDao();
    public abstract WalkingPointDao walkingPointDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
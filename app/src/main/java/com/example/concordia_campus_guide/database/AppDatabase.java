package com.example.concordia_campus_guide.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.concordia_campus_guide.database.converters.StringListConverter;
import com.example.concordia_campus_guide.database.daos.BuildingDao;
import com.example.concordia_campus_guide.database.daos.FloorDao;
import com.example.concordia_campus_guide.database.daos.RoomDao;
import com.example.concordia_campus_guide.database.daos.ShuttleDao;
import com.example.concordia_campus_guide.database.daos.WalkingPointDao;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Floor;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.Shuttle;
import com.example.concordia_campus_guide.models.WalkingPoint;

@Database(entities = {Building.class, Floor.class, RoomModel.class, WalkingPoint.class,  Shuttle.class},exportSchema = false, version = 2)
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
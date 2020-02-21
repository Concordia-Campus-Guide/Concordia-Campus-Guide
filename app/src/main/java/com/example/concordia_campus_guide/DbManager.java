package com.example.concordia_campus_guide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.List;

import static com.example.concordia_campus_guide.DbContract.FeedEntry.COLUMN_NAME_COORDINATION;
import static com.example.concordia_campus_guide.DbContract.FeedEntry.COLUMN_NAME_COORDINATIONS_SIDES;
import static com.example.concordia_campus_guide.DbContract.FeedEntry.COLUMN_NAME_TITLE;
import static com.example.concordia_campus_guide.DbContract.FeedEntry.TABLE_NAME;

public class DbManager extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    DbContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_COORDINATION + " TEXT," +
                    DbContract.FeedEntry.COLUMN_NAME_COORDINATIONS_SIDES + " TEXT )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_SELECT_ENTRIES =
            "SELECT * FROM " + TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ConuMaps.db";

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public boolean addData (){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE,"Hall Building");
        contentValues.put(COLUMN_NAME_COORDINATION,"45.497178,-73.579550");
        contentValues.put(COLUMN_NAME_COORDINATIONS_SIDES,"45.497178,-73.579550,45.497708,-73.579035,45.497385,-73.578332,45.496832,-73.578842,45.497178,-73.579550");
        long result = db.insert(TABLE_NAME,null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return  true;
        }
    }

}

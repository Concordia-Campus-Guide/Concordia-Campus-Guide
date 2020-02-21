package com.example.concordia_campus_guide;

import android.provider.BaseColumns;

import java.nio.file.FileAlreadyExistsException;

public class DbContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Buildings";
        public static final String COLUMN_NAME_TITLE = "name";
        public static final String COLUMN_NAME_COORDINATION = "coordination";
        public static final String COLUMN_NAME_COORDINATIONS_SIDES = "cornerCoordination";
    }


}

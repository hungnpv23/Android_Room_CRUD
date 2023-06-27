package com.example.practicepe_roomdb.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.practicepe_roomdb.dataEntity;

@androidx.room.Database(entities = {dataEntity.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "data.db";
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract dataDAO dataDao();
}

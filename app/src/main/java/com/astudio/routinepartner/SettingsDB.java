package com.astudio.routinepartner;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Database Object
@Database(entities = {Settings.class}, version = 1, exportSchema = false)
public abstract class SettingsDB extends RoomDatabase {

    private static volatile SettingsDB INSTANCE = null;
    public abstract SettingsDAO settingDao();
    static final ExecutorService DatabaseWriteExecutor = Executors.newSingleThreadExecutor();

    // Database가 열려있지 않은 경우 연다
    static SettingsDB getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (SettingsDB.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SettingsDB.class, "settings_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    // Database를 close한다
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
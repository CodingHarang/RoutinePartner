package com.example.routinepartner;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Database Object
@Database(entities = {ActInfo.class}, version = 1)
public abstract class ActInfoDB extends RoomDatabase {

    private static volatile ActInfoDB INSTANCE = null;
    public abstract ActInfoDAO actInfoDao();
    static final ExecutorService DatabaseWriteExecutor = Executors.newFixedThreadPool(4);

    // Database가 열려있지 않은 경우 연다
    static ActInfoDB getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ActInfoDB.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ActInfoDB.class, "act_info_database").build();
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

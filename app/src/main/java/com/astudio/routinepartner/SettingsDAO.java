package com.astudio.routinepartner;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

// Data Access Object
@Dao
public interface SettingsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Settings settings);

    @Query("DELETE FROM settings")
    void deleteAll();

    @Query("SELECT * FROM settings")
    Settings[] getAll();

    @Query("DELETE FROM settings WHERE :order = settings.`order`")
    void deleteByOrder(int order);

}
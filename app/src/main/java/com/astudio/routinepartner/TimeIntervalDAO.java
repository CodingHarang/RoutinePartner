package com.astudio.routinepartner;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

// Data Access Object
@Dao
public interface TimeIntervalDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TimeInterval timeInterval);

    @Query("DELETE FROM timeinterval")
    void deleteAll();

    @Query("SELECT * FROM timeinterval")
    TimeInterval getAll();
}
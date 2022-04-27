package com.astudio.routinepartner;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

// Data Access Object
@Dao
public interface ActInfoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ActInfo actInfo);

    @Query("DELETE FROM actInfo")
    void deleteAll();

    @Query("SELECT * FROM actInfo")
    ActInfo[] getAll();
}
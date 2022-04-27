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

    @Query("DELETE FROM actInfo WHERE :id = actInfo.id")
    void deleteByActId(int id);

    @Query("SELECT * FROM actInfo")
    ActInfo[] getAll();

    // 오늘 한 일 불러오기
    @Query("SELECT * FROM actInfo WHERE :year = actInfo.year AND :month = actInfo.month AND :date = actInfo.date")
    ActInfo[] getItemByDate(int year, int month, int date);

    // 카테고리별 불러오기
    @Query("SELECT * FROM actInfo WHERE :category = actInfo.category")
    ActInfo[] getItemByCategory(String category);
}
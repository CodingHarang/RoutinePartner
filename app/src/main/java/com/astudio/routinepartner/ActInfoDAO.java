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
    @Query("SELECT * FROM actInfo WHERE (((:Syear * 10000 + :Smonth * 100 + :Sdate) <= (actInfo.year * 10000 + actInfo.month * 100 + actInfo.date)) AND ((:Eyear * 10000 + :Emonth * 100 + :Edate) >= (actInfo.year * 10000 + actInfo.month * 100 + actInfo.date)))")
    ActInfo[] getItemByDate(int Syear, int Smonth, int Sdate, int Eyear, int Emonth, int Edate);

    // 카테고리별 불러오기
    @Query("SELECT * FROM actInfo WHERE :category = actInfo.category")
    ActInfo[] getItemByCategory(String category);

    // 데이터 수정
    @Query("UPDATE actInfo SET Category = :CCategory, Year = :CYear, Month = :CMonth, Date = :CDate, StartHour = :CShour, StartMinute = :CSminute, EndHour = :CEhour, EndMinute = :CEminute WHERE id = :id")
    void updateData(int id, String CCategory, int CYear, int CMonth, int CDate, int CShour, int CSminute, int CEhour, int CEminute);

    @Query("UPDATE actInfo SET Category = :CCategory WHERE Category = :OCategory")
    void updateCategoryName(String OCategory, String CCategory);

    @Query("DELETE FROM actInfo WHERE Category = :DCategory")
    void deleteByCategory(String DCategory);
}
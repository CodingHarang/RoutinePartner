package com.astudio.routinepartner;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database에 저장되는 객체
@Entity(tableName = "timeinterval")
public class TimeInterval {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "interval")
    private int Interval;

    public void setInterval(int i) {
        this.Interval = i;
    }

    public int getInterval() {
        return this.Interval;
    }
}
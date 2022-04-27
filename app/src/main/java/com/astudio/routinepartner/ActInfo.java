package com.astudio.routinepartner;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database에 저장되는 객체
@Entity(tableName = "actInfo")
public class ActInfo {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "year")
    private int Year;
    @ColumnInfo(name = "month")
    private int Month;
    @ColumnInfo(name = "date")
    private int Date;
    @ColumnInfo(name = "category")
    private String Category;
    @ColumnInfo(name = "startHour")
    private int StartHour;
    @ColumnInfo(name = "startMinute")
    private int StartMinute;
    @ColumnInfo(name = "endHour")
    private int EndHour;
    @ColumnInfo(name = "endMinute")
    private int EndMinute;

    public int getId() { return this.id; }

    public int getYear() {
        return this.Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }

    public int getMonth() {
        return this.Month;
    }

    public void setMonth(int month) {
        this.Month = month;
    }

    public int getDate() {
        return this.Date;
    }

    public void setDate(int date) {
        this.Date = date;
    }

    public String getCategory() {
        return this.Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public int getStartHour() {
        return this.StartHour;
    }

    public void setStartHour(int startHour) {
        this.StartHour = startHour;
    }

    public int getStartMinute() {
        return this.StartMinute;
    }

    public void setStartMinute(int startMinute) {
        this.StartMinute = startMinute;
    }

    public int getEndHour() {
        return this.EndHour;
    }

    public void setEndHour(int endHour) {
        this.EndHour = endHour;
    }

    public int getEndMinute() {
        return this.EndMinute;
    }

    public void setEndMinute(int endMinute) {
        this.EndMinute = endMinute;
    }
}
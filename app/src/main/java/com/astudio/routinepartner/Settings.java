package com.astudio.routinepartner;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database에 저장되는 객체
@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "categoryName")
    private String Category;

    @ColumnInfo(name = "color")
    private long Color;

    // 1이면 횟수 2면 시간
    @ColumnInfo(name = "goalType")
    private int GoalType;

    @ColumnInfo(name = "goal")
    private int Goal;

    @ColumnInfo(name = "affectingStat")
    private int AffectingStat;

    // 1, 2, 3
    @ColumnInfo(name = "order")
    private int Order;

    public void setCategory(String category) {
        this.Category = category;
    }

    public String getCategory() {
        return this.Category;
    }

    public void setColor(long color) {
        this.Color = color;
    }

    public long getColor() {
        return this.Color;
    }

    public void setGoalType(int goalType) {
        this.GoalType = goalType;
    }

    public int getGoalType() {
        return this.GoalType;
    }

    public void setGoal(int goal) {
        this.Goal = goal;
    }

    public int getGoal() {
        return this.Goal;
    }

    public void setAffectingStat(int affectingStat) {
        this.AffectingStat = affectingStat;
    }

    public int getAffectingStat() {
        return this.AffectingStat;
    }

    public void setOrder(int order) {
        this.Order = order;
    }

    public int getOrder() {
        return this.Order;
    }
}
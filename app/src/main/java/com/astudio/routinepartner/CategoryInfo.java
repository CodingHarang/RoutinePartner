package com.astudio.routinepartner;

public class CategoryInfo {
    private String Name;
    private String Color;
    private String Stat;
    private String GoalType;
    private int Goal;

    public CategoryInfo(String name, String color, String stat) {
        Name = name;
        Color = color;
        Stat = stat;
    }

    public CategoryInfo(String name, String color, String stat, String goalType, int goal) {
        Name = name;
        Color = color;
        Stat = stat;
        GoalType = goalType;
        Goal = goal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getStat() {
        return Stat;
    }

    public void setStat(String stat) {
        Stat = stat;
    }

    public String getGoalType() {
        return GoalType;
    }

    public void setGoalType(String goalType) {
        GoalType = goalType;
    }

    public int getGoal() {
        return Goal;
    }

    public void setGoal(int goal) {
        Goal = goal;
    }
}

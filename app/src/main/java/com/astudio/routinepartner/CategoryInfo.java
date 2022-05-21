package com.astudio.routinepartner;

public class CategoryInfo {
    private String Name;
    private Long Color;
    private int Stat;
    private int GoalType;
    private int Goal;
    private int Order;

    public CategoryInfo(String name, Long color, int stat, int goalType, int goal) {
        Name = name;
        Color = color;
        Stat = stat;
        GoalType = goalType;
        Goal = goal;
    }

    public CategoryInfo(String name, Long color, int stat, int goalType, int goal, int order) {
        Name = name;
        Color = color;
        Stat = stat;
        GoalType = goalType;
        Goal = goal;
        Order = order;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getColor() {
        return Color;
    }

    public void setColor(Long color) {
        Color = color;
    }

    public int getStat() {
        return Stat;
    }

    public void setStat(int stat) {
        Stat = stat;
    }

    public int getGoalType() {
        return GoalType;
    }

    public void setGoalType(int goalType) {
        GoalType = goalType;
    }

    public int getGoal() {
        return Goal;
    }

    public void setGoal(int goal) {
        Goal = goal;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }
}

package com.astudio.routinepartner;

import java.util.ArrayList;
import java.util.Arrays;

public class SavedSettings {
    public static ArrayList<String> CategoryList = new ArrayList<>(Arrays.asList());
    public static ArrayList<Long> ColorList = new ArrayList<>(Arrays.asList());
    // 1은 횟수, 2는 시간
    public static ArrayList<Integer> GoalType = new ArrayList<>(Arrays.asList());
    public static ArrayList<Integer> Goal = new ArrayList<>(Arrays.asList());
    // 1은 지능, 2는 재미
    public static ArrayList<Integer> AffectingStat = new ArrayList<>(Arrays.asList());
    public static ArrayList<Integer> Order = new ArrayList<>(Arrays.asList());
    public static boolean isRefreshed = false;
    public static ArrayList<String> StatList = new ArrayList<>(Arrays.asList("지능", "재미", "체력", "포만감", "잔고", "자아실현"));
    public static int TimeInterval;
}
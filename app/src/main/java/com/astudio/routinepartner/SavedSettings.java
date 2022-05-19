package com.astudio.routinepartner;

import java.util.ArrayList;
import java.util.Arrays;

public class SavedSettings {
    public static ArrayList<String> CategoryList = new ArrayList<>(Arrays.asList("식사", "수면", "공부"));
    public static ArrayList<Integer> ColorList = new ArrayList<>(Arrays.asList(0XFF85C8F5, 0xFF077ECE, 0XFFFFFF00));

    // 1은 횟수, 2는 시간
    public static ArrayList<Integer> GoalType = new ArrayList<>(Arrays.asList(1, 1, 2));

    public static ArrayList<Integer> GoalList = new ArrayList<>(Arrays.asList(3, 7, 4));

    // 1은 지능, 2는 재미
    public static ArrayList<Integer> AffectingStat = new ArrayList<>(Arrays.asList(4, 2, 4));






    public static ArrayList<String> StatList = new ArrayList<>(Arrays.asList("지능", "재미", "체력", "포만감", "잔고", "자아실현"));
}
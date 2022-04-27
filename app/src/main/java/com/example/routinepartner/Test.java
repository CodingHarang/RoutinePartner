package com.example.routinepartner;

import java.util.ArrayList;

public class Test {
    ArrayList<TestItem> TestItemList;
    ArrayList<ArrayList<TestItem>> TestItemAllDayList = new ArrayList<>();
    ArrayList<String> CategoryList = new ArrayList<>();

    public class TestItem{
        String Cate;
        int Year, Month, Day, StartTime, StartMin, EndTime, EndMin;

        TestItem(int Y, int M, int D, String C, int ST, int SM, int ET, int EM){
            Year = Y;
            Month = M;
            Day = D;
            Cate = C;
            StartTime = ST;
            StartMin = SM;
            EndTime = ET;
            EndMin = EM;
        }
    }

    public void testAddDayOne(){
        TestItemList= new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 3, "수면", 0, 0, 7, 0));
        TestItemList.add(new TestItem(2022, 4, 3, "식사", 8, 0, 9, 0));
        TestItemList.add(new TestItem(2022, 4, 3, "기타", 9, 0, 13, 0));
        TestItemList.add(new TestItem(2022, 4, 3, "공부", 13, 0, 17, 0));
        TestItemList.add(new TestItem(2022, 4, 3, "식사",17 , 0, 18, 0));
        TestItemList.add(new TestItem(2022, 4, 3, "기타", 18, 0, 21, 0));
        TestItemList.add(new TestItem(2022, 4, 3, "공부", 21, 0, 22, 0));
        TestItemList.add(new TestItem(2022, 4, 3, "수면", 22, 0, 0, 0));

        TestItemAllDayList.add(TestItemList);

    }

    public void testAddDayTwo(){
        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 4, "수면", 0, 0, 8, 30));
        TestItemList.add(new TestItem(2022, 4, 4, "식사", 9, 0, 10, 40));
        TestItemList.add(new TestItem(2022, 4, 4, "기타", 10, 40, 13, 23));
        TestItemList.add(new TestItem(2022, 4, 4, "공부", 13, 23, 16, 0));
        TestItemList.add(new TestItem(2022, 4, 4,"식사",16 , 10, 18, 30));
        TestItemList.add(new TestItem(2022, 4, 4, "기타", 18, 30, 21, 0));
        TestItemList.add(new TestItem(2022, 4, 4, "공부", 21, 0, 23, 30));
        TestItemList.add(new TestItem(2022, 4, 4, "수면", 23, 30, 0, 0));
        TestItemAllDayList.add(TestItemList);

        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 5, "수면", 0, 0, 6, 50));
        TestItemList.add(new TestItem(2022, 4, 5, "식사", 7, 0, 8, 30));
        TestItemList.add(new TestItem(2022, 4, 5, "기타", 8, 40, 12, 0));
        TestItemList.add(new TestItem(2022, 4, 5, "공부", 12, 10, 14, 0));
        TestItemList.add(new TestItem(2022, 4, 5,"식사",14 , 10, 15, 0));
        TestItemList.add(new TestItem(2022, 4, 5, "기타", 15, 30, 20, 20));
        TestItemList.add(new TestItem(2022, 4, 5, "공부", 20, 30, 22, 30));
        TestItemList.add(new TestItem(2022, 4, 5, "수면", 22, 50, 0, 0));
        TestItemAllDayList.add(TestItemList);

        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 6, "수면", 0, 0, 9, 0));
        TestItemList.add(new TestItem(2022, 4, 6, "식사", 9, 0, 10, 40));
        TestItemList.add(new TestItem(2022, 4, 6, "기타", 10, 40, 13, 23));
        TestItemList.add(new TestItem(2022, 4, 6, "공부", 13, 23, 16, 0));
        TestItemList.add(new TestItem(2022, 4, 6,"식사",16 , 10, 18, 30));
        TestItemList.add(new TestItem(2022, 4, 6, "기타", 18, 30, 21, 0));
        TestItemList.add(new TestItem(2022, 4, 6, "공부", 21, 0, 23, 30));
        TestItemList.add(new TestItem(2022, 4, 6, "수면", 23, 30, 0, 0));
        TestItemAllDayList.add(TestItemList);

        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 7, "수면", 0, 0, 6, 30));
        TestItemList.add(new TestItem(2022, 4, 7, "식사", 6, 30, 7, 30));
        TestItemList.add(new TestItem(2022, 4, 7, "기타", 7, 40, 12, 0));
        TestItemList.add(new TestItem(2022, 4, 7,"식사",12 , 10, 13, 0));
        TestItemList.add(new TestItem(2022, 4, 7, "기타", 13, 30, 20, 20));
        TestItemList.add(new TestItem(2022, 4, 7, "공부", 20, 30, 22, 30));
        TestItemList.add(new TestItem(2022, 4, 7, "수면", 22, 50, 0, 0));
        TestItemAllDayList.add(TestItemList);

        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 8, "수면", 0, 0, 6, 30));
        TestItemList.add(new TestItem(2022, 4, 8, "식사", 6, 30, 7, 30));
        TestItemList.add(new TestItem(2022, 4, 8, "기타", 7, 40, 12, 0));
        TestItemList.add(new TestItem(2022, 4, 8,"식사",12 , 10, 13, 0));
        TestItemList.add(new TestItem(2022, 4, 8, "기타", 13, 30, 20, 20));
        TestItemList.add(new TestItem(2022, 4, 8, "공부", 20, 30, 22, 30));
        TestItemList.add(new TestItem(2022, 4, 8, "수면", 20, 0, 0, 0));
        TestItemAllDayList.add(TestItemList);
//
        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 9, "수면", 0, 0, 6, 30));
        TestItemList.add(new TestItem(2022, 4, 9, "식사", 6, 30, 7, 30));
        TestItemList.add(new TestItem(2022, 4, 9, "기타", 7, 40, 12, 0));
        TestItemList.add(new TestItem(2022, 4, 9,"식사",12 , 10, 13, 0));
        TestItemList.add(new TestItem(2022, 4, 9, "기타", 13, 30, 20, 20));
        TestItemList.add(new TestItem(2022, 4, 9, "공부", 20, 30, 22, 30));
        TestItemList.add(new TestItem(2022, 4, 9, "수면", 21, 0, 0, 0));
        TestItemAllDayList.add(TestItemList);

        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 10, "수면", 0, 0, 13, 30));
        TestItemList.add(new TestItem(2022, 4, 10, "식사", 14, 30, 15, 0));
        TestItemList.add(new TestItem(2022, 4, 10, "공부", 15, 10, 21, 0));
        TestItemList.add(new TestItem(2022, 4, 10,"식사",21 , 10, 22, 0));
        TestItemList.add(new TestItem(2022, 4, 10, "기타", 22, 30, 0, 0));
        TestItemAllDayList.add(TestItemList);

        TestItemList = new ArrayList<>();
        TestItemList.add(new TestItem(2022, 4, 11, "수면", 0, 0, 13, 30));
        TestItemList.add(new TestItem(2022, 4, 11, "식사", 14, 30, 15, 0));
        TestItemList.add(new TestItem(2022, 4, 11, "공부", 15, 10, 21, 0));
        TestItemList.add(new TestItem(2022, 4, 11, "기타", 22, 30, 0, 0));
        TestItemAllDayList.add(TestItemList);
    }
}

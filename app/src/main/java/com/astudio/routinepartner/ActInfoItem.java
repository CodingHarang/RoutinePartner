package com.astudio.routinepartner;

// ActInfo의 데이터를 불러와 ActInfoItem에 저장
public class ActInfoItem {

    String Category;
    int ItemId, Year, Month, Date, StartHour, StartMinute, EndHour, EndMinute;

    public ActInfoItem(int id, int year, int month, int date, String category, int startHour, int startMinute, int endHour, int endMinute) {
        ItemId = id;
        Year = year;
        Month = month;
        Date = date;
        Category = category;
        StartHour = startHour;
        StartMinute = startMinute;
        EndHour = endHour;
        EndMinute = endMinute;
    }
}
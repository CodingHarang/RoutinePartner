package com.example.routinepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    PieChartView PieChart;
    ArrayList<String> TimeData = new ArrayList<String>(Arrays.asList("00/00/08/30", "08/30/09/10","10/00/14/00","14/00/16/50","17/00/21/30","21/30/24/00"));
    ArrayList<Integer> TimeList = new ArrayList<Integer>();
    ArrayList<Float> AngleList = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PieChart = (PieChartView) findViewById(R.id.PieChartView);

        for(int i = 0; i<TimeData.size(); i++){
            timeParsing(TimeData.get(i));
            timeToAngle(TimeList);
        }

        try{
            Log.v("데이터 넘김", "success");
            PieChart.Data.addAll(AngleList); //타임 리스트에 있는 시간에 관한 데이터를 파이차트뷰의 데이터리스트로 넘김
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void timeParsing(String GetTime){ //시간에 관한 데이터를 스트링으로 받을 경우 파싱
        ArrayList<Integer> TempTimeList = new ArrayList<Integer>();
        String EachTime[] = GetTime.split("/");
        for(int i = 0; i < 4; i++){
            TempTimeList.add(Integer.parseInt(EachTime[i]));
            Log.v("타임파싱", "내용: " + TempTimeList.get(i));
        }
        TimeList = TempTimeList;
    }

    public void timeToAngle(ArrayList<Integer> arr){
        int BeforeTime, AfterTime;
        Float StartAngle, DrawAngle;

        BeforeTime = TimeList.get(0)*60 + TimeList.get(1);
        AfterTime = TimeList.get(2)*60 + TimeList.get(3);
        StartAngle = BeforeTime * 0.25f - 90;
        DrawAngle = (AfterTime - BeforeTime) * 0.25f;

        AngleList.add(StartAngle);
        AngleList.add(DrawAngle);
        Log.v("스타트앵글", "내용: " + AngleList.get(0));
        Log.v("드로우앵글", "내용: " + AngleList.get(1));
    }
}
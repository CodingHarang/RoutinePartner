package com.example.routinepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    PieChartView PieChart;
    ArrayList<String> TimeData = new ArrayList<String>(Arrays.asList("00/00/08/30", "08/30/09/10","10/00/14/00","14/00/16/50","17/00/21/30","21/30/00/00"));
    ArrayList<Integer> TimeList = new ArrayList<Integer>();
    ArrayList<Float> AngleList = new ArrayList<Float>();
    ArrayList<Integer> YesterDayTimeList = new ArrayList<>(Arrays.asList(22, 30, 0, 0));
    ArrayList<Float> YesterDayAngleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PieChart = (PieChartView) findViewById(R.id.PieChartView);
        ImageButton BtnChart = (ImageButton) findViewById(R.id.BtnChart);


        BtnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BarIntent = new Intent(getApplicationContext(), BarChartActivity.class);
                startActivity(BarIntent);
            }
        });


        for(int i = 0; i<TimeData.size(); i++){
            timeParsing(TimeData.get(i));
            timeToAngle(TimeList);
        }

        timeToAngleYesterday(YesterDayTimeList);

        try{
            PieChart.Data.addAll(AngleList); //타임 리스트에 있는 시간에 관한 데이터를 파이차트뷰의 데이터리스트로 넘김
            PieChart.YesterdayData.addAll(YesterDayAngleList);
            Log.v("데이터 넘김", "" + YesterDayAngleList);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

//        PieChartAutoReset();
    }




    public void timeParsing(String GetTime){ //시간에 관한 데이터를 스트링으로 받을 경우 파싱
        ArrayList<Integer> TempTimeList = new ArrayList<Integer>();
        String EachTime[] = GetTime.split("/");
        for(int i = 0; i < 4; i++){
            TempTimeList.add(Integer.parseInt(EachTime[i]));
        }
        TimeList = TempTimeList;
    }
    
    //숫자로 받을 경우 바로 TimeLsit에 추가

    public void timeToAngle(ArrayList<Integer> arr){
        int BeforeTime, AfterTime;
        Float StartAngle, DrawAngle;

        BeforeTime = TimeList.get(0)*60 + TimeList.get(1);
        AfterTime = TimeList.get(2)*60 + TimeList.get(3);

        if(AfterTime == 0){
            AfterTime = 1440;
        }

        StartAngle = BeforeTime * 0.25f - 90;
        DrawAngle = (AfterTime - BeforeTime) * 0.25f;

        AngleList.add(StartAngle);
        AngleList.add(DrawAngle);
    }

    public void timeToAngleYesterday(ArrayList<Integer> arr){
        int BeforeTime, AfterTime;
        Float StartAngle, DrawAngle;

        BeforeTime = YesterDayTimeList.get(0)*60 + YesterDayTimeList.get(1);
        AfterTime = YesterDayTimeList.get(2)*60 + YesterDayTimeList.get(3);

        if(AfterTime == 0){
            AfterTime = 1440;
        }

        StartAngle = BeforeTime * 0.25f - 90;
        DrawAngle = (AfterTime - BeforeTime) * 0.25f;

        YesterDayAngleList.add(StartAngle);
        YesterDayAngleList.add(DrawAngle);
    }



    public void PieChartAutoReset(){ //0시마다 원형시간표 리셋
        AlarmManager PieReset = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent RePie = new Intent(MainActivity.this, AlarmRecever.class);
        PendingIntent ResetSender = PendingIntent.getBroadcast(MainActivity.this, 0, RePie, 0);

        Calendar ResetCal = Calendar.getInstance();
        ResetCal.setTimeInMillis(System.currentTimeMillis());
        ResetCal.set(Calendar.HOUR_OF_DAY, 0);
        ResetCal.set(Calendar.MINUTE, 0);
        ResetCal.set(Calendar.SECOND, 0);

        PieReset.setInexactRepeating(AlarmManager.RTC_WAKEUP, ResetCal.getTimeInMillis()+AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, ResetSender);

        SimpleDateFormat Format = new SimpleDateFormat("MM/dd kk:mm:ss");
        String SetResetTime = Format.format(new Date(ResetCal.getTimeInMillis()+AlarmManager.INTERVAL_DAY));
    }

    public class AlarmRecever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            AngleList.clear();
        }
    }
}
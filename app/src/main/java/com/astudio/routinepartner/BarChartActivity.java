package com.astudio.routinepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BarChartActivity extends AppCompatActivity {
    BarChart Bar_Chart;
    Calendar ChartCalender = Calendar.getInstance();
    EditText DateWhen, EditStart, EditEnd;
    String[] ActItems = {"행동 선택","식사", "수면", "공부", "기타"};
    String CurrentCategory, EditStartText, EditEndText;
    Boolean CategoryValue = false, DateCompareValue = false;
    ArrayList<String> DayList = new ArrayList<>();
    ArrayList<Float> TimeList = new ArrayList<>();
    Spinner ChartSpinner;
    Button BtnMakeChart;
//    int SYear, SMonth, SDay, EYear, EMonth, EDay;


    DatePickerDialog.OnDateSetListener ChartDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            ChartCalender.set(Calendar.YEAR, year);
            ChartCalender.set(Calendar.MONTH, month);
            ChartCalender.set(Calendar.DAY_OF_MONTH, day);
            updateDate();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        Bar_Chart = (BarChart) findViewById(R.id.BarChart);
        EditStart = (EditText) findViewById(R.id.EditStart);
        EditEnd = (EditText) findViewById(R.id.EditEnd);
        ChartSpinner = (Spinner) findViewById(R.id.ChartSpinner);
        BtnMakeChart = (Button) findViewById(R.id.BtnMakeChart);

        bringDataFromTest();


        //스피너에 관한 부분

        ArrayAdapter<String> ChartAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ActItems);

        ChartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ChartSpinner.setAdapter(ChartAdapter);
        ChartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        CategoryValue = false;
                        CurrentCategory = ActItems[i];
                        DayList.clear();
                        TimeList.clear();
                        break;
                    case 1:
                        CategoryValue = true;
                        DayList.clear();
                        TimeList.clear();
                        CurrentCategory = ActItems[i];
                        getData();
                        break;
                    case 2:
                        CategoryValue = true;
                        DayList.clear();
                        TimeList.clear();
                        CurrentCategory = ActItems[i];
                        getData();
                        break;
                    case 3:
                        CategoryValue = true;
                        DayList.clear();
                        TimeList.clear();
                        CurrentCategory = ActItems[i];
                        getData();
                        break;
                    case 4:
                        CategoryValue = true;
                        DayList.clear();
                        TimeList.clear();
                        CurrentCategory = ActItems[i];
                        getData();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                CategoryValue = false;
                CurrentCategory = ActItems[0];
                DayList.clear();
                TimeList.clear();
            }
        });


        //날짜 입력에 관한 부분

        EditStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateWhen = EditStart;
                new DatePickerDialog(BarChartActivity.this, ChartDatePicker, ChartCalender.get(Calendar.YEAR),
                        ChartCalender.get(Calendar.MONTH), ChartCalender.get(Calendar.DAY_OF_MONTH)).show();

//                SYear = ChartCalender.get(Calendar.YEAR);
//                SMonth = ChartCalender.get(Calendar.MONTH);
//                SDay = ChartCalender.get(Calendar.DAY_OF_MONTH);
//
//                Log.v("값 확인", ""+SYear+"  "+SMonth+"  "+SDay);
            }
        });

        EditEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateWhen = EditEnd;
                new DatePickerDialog(BarChartActivity.this, ChartDatePicker, ChartCalender.get(Calendar.YEAR),
                        ChartCalender.get(Calendar.MONTH), ChartCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //차트 그리기 + 입력된 값이 잘못됐을 경우 토스트 메세지 띄우기

        BtnMakeChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compareDate();

                if(!CategoryValue&&!DateCompareValue){
                    Toast.makeText(getApplicationContext(), "카테고리 선택과 날짜 설정을 다시 해주세요", Toast.LENGTH_SHORT).show();
                }else if(!DateCompareValue){
                    Toast.makeText(getApplicationContext(), "날짜 설정이 올바르지 않아요", Toast.LENGTH_SHORT).show();
                }else if(!CategoryValue){
                    Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    drawBarChart(DayList, TimeList);
                }
            }
        });
    }


    //날짜 입력받고 텍스트 설정에 관한 부분

    public void updateDate(){
        String Format = "yyyy/MM/dd";
        SimpleDateFormat SDF = new SimpleDateFormat(Format, Locale.KOREA);

        DateWhen.setText(SDF.format(ChartCalender.getTime()));

        if(DateWhen == EditStart){
            EditStartText = SDF.format(ChartCalender.getTime());
        }else if(DateWhen == EditEnd){
            EditEndText = SDF.format(ChartCalender.getTime());
        }
    }

    //입력받은 날짜 비교에 관한 부분

    public void compareDate(){
        if((EditStart.getText().length() == 0) || (EditEnd.getText().length() == 0)){
            DateCompareValue = false;
        }else if(EditStartText.compareTo(EditEndText) <= 0){
            DateCompareValue = true;
        }else
            DateCompareValue = false;
    }


    //차트 설정에 관한 부분

    private void drawBarChart(ArrayList<String> daylist, ArrayList<Float> timelist){

        YAxis Yraxis, Ylaxis;

        Yraxis = Bar_Chart.getAxisRight();
        Yraxis.setDrawLabels(false);
        Yraxis.setDrawAxisLine(false);
        Yraxis.setDrawGridLines(false);

        Ylaxis = Bar_Chart.getAxisLeft();
        Ylaxis.setDrawLabels(false);
        Ylaxis.setDrawAxisLine(false);
        Ylaxis.setDrawGridLines(false);

        XAxis Xaxis = Bar_Chart.getXAxis();
        Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        Xaxis.setDrawAxisLine(false);
        Xaxis.setDrawGridLines(false);

        Bar_Chart.clear();
        Bar_Chart.setHighlightPerTapEnabled(false); //그래프 각 항목 터치시 하이라이트 안되게 설정
        Bar_Chart.setHighlightPerDragEnabled(false);
        Bar_Chart.setTouchEnabled(true);
        Bar_Chart.setDragEnabled(true);
        Bar_Chart.setScaleEnabled(false);
        Bar_Chart.setPinchZoom(false);
        Bar_Chart.setDrawGridBackground(false);
        Bar_Chart.setDrawValueAboveBar(true); //수치가 그래프 위에 표시
        Bar_Chart.setDescription(null);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i < daylist.size(); i++){
            entries.add(new BarEntry(timelist.get(i), i));
        }

        BarDataSet depenses = new BarDataSet(entries, "시간");
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);


        BarData data = new BarData(daylist, depenses);
        depenses.setColors(ColorTemplate.LIBERTY_COLORS);

        Bar_Chart.setData(data);
        Bar_Chart.getLegend().setEnabled(false); //하단 라벨 안보이게 설정
        Bar_Chart.setVisibleXRangeMaximum(8);
        Bar_Chart.animateXY(1000, 1000);
        Bar_Chart.invalidate();

    }


    //카테고리에 따른 값 계산


    //test code
    ArrayList<ArrayList<Test>> TestAllDayList = new ArrayList<>();
    Test t = new Test();

    private void bringDataFromTest() {
        t.testAddDayOne();
        t.testAddDayTwo();
    }
    //

    private void getData(){
        float CategoryTime;
        String CurrentDay;
        //선택한 기간의 데이터 가져오기
        //그 안에서 선택된 카테고리의 값만 가져오기
        for(int i = 0; i < t.TestItemAllDayList.size(); i++){
            CurrentDay = Integer.toString(t.TestItemAllDayList.get(i).get(0).Month)+"/"+Integer.toString(t.TestItemAllDayList.get(i).get(0).Day);
            //Integer.toString(t.TestItemAllDayList.get(i).get(0).Year)+"/n"+
            CategoryTime = 0;
            for(int j = 0; j < t.TestItemAllDayList.get(i).size() ; j++){
                if(t.TestItemAllDayList.get(i).get(j).Cate.equals(CurrentCategory)){
                    float time;
                    float CalSTime = t.TestItemAllDayList.get(i).get(j).StartTime, CalETime = t.TestItemAllDayList.get(i).get(j).EndTime,
                            CalSMin = t.TestItemAllDayList.get(i).get(j).StartMin, CalEMin = t.TestItemAllDayList.get(i).get(j).EndMin;

                    if(CalETime == 0){
                        CalETime = 24;
                    }

                    time = ((CalETime*60+CalEMin)-(CalSTime*60+CalSMin))/60;
                    CategoryTime += time;
                }
            }
            TimeList.add(CategoryTime);
            DayList.add(CurrentDay);
        }
    }
}
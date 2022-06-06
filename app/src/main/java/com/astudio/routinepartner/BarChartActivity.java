package com.astudio.routinepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class BarChartActivity extends AppCompatActivity {
    protected ArrayList<ActInfo> ActInfoList;
    protected ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();
    protected ArrayList<ActInfoItem> ActInfoItemListTemp = new ArrayList<ActInfoItem>();
    BarChart Bar_Chart;
    Calendar ChartCalender = Calendar.getInstance();
    EditText DateWhen, EditStart, EditEnd;
    String[] ActItems = {"Select","Eat", "Sleep", "Study", "Etc"};
    String CurrentCategory, EditStartText, EditEndText;
    Boolean CategoryValue = false, DateCompareValue = false, DefaultData = true;
    ArrayList<String> DayList = new ArrayList<>();
    ArrayList<Float> TimeList = new ArrayList<>();
    ArrayList<ArrayList<Float>> WeekTimeList = new ArrayList<>();
    Spinner ChartSpinner;
    Button BtnMakeChart;
    TextView PercnetText, ProgressDataText;
    ProgressBar CircularProgressBar;
    ArrayList<String> CatetoryList = new ArrayList<>(Arrays.asList("선택"));
    XAxis Xaxis;

    int Syear, Smonth, Sday, Eyear, Emonth, Eday, SDate, EDate, Chartdata = 7;


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
    protected void onStart() {
        super.onStart();
        CircularProgressBar.setProgress(0, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        Bar_Chart = (BarChart) findViewById(R.id.BarChart);
        EditStart = (EditText) findViewById(R.id.EditStart);
        EditEnd = (EditText) findViewById(R.id.EditEnd);
        ChartSpinner = (Spinner) findViewById(R.id.ChartSpinner);
        BtnMakeChart = (Button) findViewById(R.id.BtnMakeChart);
        PercnetText = findViewById(R.id.ProgressPercent);
        CircularProgressBar = findViewById(R.id.CirCularprogressBar);
        ProgressDataText = findViewById(R.id.ProgressDataText);

        CircularProgressBar.setMax(100);
        CatetoryList.addAll(SavedSettings.CategoryList);

        YAxis Yraxis, Ylaxis;

        Yraxis = Bar_Chart.getAxisRight();
        Yraxis.setDrawLabels(false);
        Yraxis.setDrawAxisLine(false);
        Yraxis.setDrawGridLines(false);

        Ylaxis = Bar_Chart.getAxisLeft();
        Ylaxis.setDrawLabels(false);
        Ylaxis.setDrawAxisLine(false);
        Ylaxis.setDrawGridLines(false);

        Ylaxis.setAxisMinimum(0);

        Xaxis = Bar_Chart.getXAxis();
        Xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        Xaxis.setDrawAxisLine(false);
        Xaxis.setDrawGridLines(false);
        Xaxis.setCenterAxisLabels(false);
        Xaxis.setTextColor(0xFFBDBDBD);

        Bar_Chart.setHighlightPerTapEnabled(false); //그래프 각 항목 터치시 하이라이트 안되게 설정
        Bar_Chart.setHighlightPerDragEnabled(false);
        Bar_Chart.setTouchEnabled(true);
        Bar_Chart.setDragEnabled(true);
        Bar_Chart.setScaleEnabled(false);
        Bar_Chart.setPinchZoom(false);
        Bar_Chart.setDrawGridBackground(false);
        Bar_Chart.setDrawValueAboveBar(true); //수치가 그래프 위에 표시
        Bar_Chart.setDescription(null);
        Bar_Chart.setNoDataText("데이터가 없습니다.");

        weekChart();


        //스피너에 관한 부분
        ArrayAdapter<String> ChartAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CatetoryList);

        ChartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ChartSpinner.setAdapter(ChartAdapter);
        ChartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CurrentCategory = CatetoryList.get(i);
                CategoryValue = true;
                if(i == 0){
                    CategoryValue = false;
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
                new DatePickerDialog(BarChartActivity.this, R.style.MyDatePickerStyle, ChartDatePicker, ChartCalender.get(Calendar.YEAR),
                        ChartCalender.get(Calendar.MONTH), ChartCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateWhen = EditEnd;
                new DatePickerDialog(BarChartActivity.this, R.style.MyDatePickerStyle, ChartDatePicker, ChartCalender.get(Calendar.YEAR),
                        ChartCalender.get(Calendar.MONTH), ChartCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //차트 그리기 + 입력된 값이 잘못됐을 경우 토스트 메세지 띄우기
        BtnMakeChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compareDate();

                if(!CategoryValue&&!DateCompareValue){
                    Toast.makeText(getApplicationContext(), "카테고리 선택과 날짜 설정을 다시 해주세요.", Toast.LENGTH_SHORT).show();
                }else if(!DateCompareValue){
                    Toast.makeText(getApplicationContext(), "날짜 설정이 올바르지 않아요.", Toast.LENGTH_SHORT).show();
                }else if(!CategoryValue){
                    Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    DefaultData = false;
                    CircularProgressBar.setProgress(0);
                    DayList.clear();
                    TimeList.clear();
                    Bar_Chart.clear();
                    getData();
                    getCategory();
                    drawBarChart(DayList, TimeList);
                    if(DayList.size() == 0){
                        Bar_Chart.setNoDataText("데이터가 없습니다.");
                    }
                    GoalPercent();
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
            Syear = ChartCalender.get(Calendar.YEAR);
            Smonth = ChartCalender.get(Calendar.MONTH) + 1;
            Sday = ChartCalender.get(Calendar.DAY_OF_MONTH);
            SDate = ChartCalender.get(Calendar.DATE);

            EditStartText = SDF.format(ChartCalender.getTime());
        }else if(DateWhen == EditEnd){
            Eyear = ChartCalender.get(Calendar.YEAR);
            Emonth = ChartCalender.get(Calendar.MONTH) + 1;
            Eday = ChartCalender.get(Calendar.DAY_OF_MONTH);
            EDate = ChartCalender.get(Calendar.DATE);

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

    private void drawBarChart(ArrayList<String> daylist, ArrayList<Float> timelist) {

        Bar_Chart.clear();


        ArrayList<BarEntry> entries = new ArrayList<>();
        if (daylist.size() != 0) {
            for (int i = 0; i < daylist.size(); i++) {
                entries.add(new BarEntry(i, timelist.get(i)));
            }

            BarDataSet barDataSet = new BarDataSet(entries, "시간");
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            barDataSet.setValueTextSize(10f);

            BarData data = new BarData(barDataSet);

            data.setBarWidth(0.4f);

            //그래프 위치 설정
            for (int d = 0; d < Chartdata; d++) {
                if (daylist.size() == d) {
                    Xaxis.setAxisMinimum(barDataSet.getXMin() - 0.5f - (Chartdata - d) * 0.5f);
                    Xaxis.setAxisMaximum(barDataSet.getXMax() + 0.5f + (Chartdata - d) * 0.5f);
                    break;
                } else {
                    Xaxis.setAxisMinimum(barDataSet.getXMin() - 0.5f);
                    Xaxis.setAxisMaximum(barDataSet.getXMax() + 0.5f);
                }
            }

            Xaxis.setGranularity(1f);

            Bar_Chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(daylist));

            barDataSet.setColors(Color.parseColor("#AEDDEF"));

            if(entries.size() == 0){
                Bar_Chart.setNoDataText("데이터가 없습니다.");
            }else{
                Bar_Chart.setData(data);
                Bar_Chart.getLegend().setEnabled(false); //하단 라벨 안보이게 설정
                Bar_Chart.setVisibleXRange(0, Chartdata);
                Bar_Chart.getBarData().setValueTextColor(0xFFBDBDBD);
                Bar_Chart.animateXY(1000, 1000);
            }
            Bar_Chart.invalidate();
        }
    }

    public void drawStackedBarChart(ArrayList<String> daylist, ArrayList<ArrayList<Float>> weektimelist){
        Bar_Chart.clear();
        ArrayList<Float> weekroutine = new ArrayList<>();
        ArrayList<Long> ColorList = new ArrayList<>();
        ColorList.addAll(SavedSettings.ColorList);
        int[] ColorA = new int[ColorList.size()];
        int num = 0;
        for(Long color : ColorList){
            ColorA[num] = color.intValue();
            num++;
        }

        float[] week;

        ArrayList<BarEntry> entries = new ArrayList<>();
        if (daylist.size() != 0 || weektimelist.size() != 0) {
            for (int i = 0; i < daylist.size(); i++) {
                for(int j = 0; j < weektimelist.size();j++){
                    weekroutine.add(weektimelist.get(j).get(i));

                }
                week = new float[weekroutine.size()];
                int n = 0;
                for(float temp : weekroutine){
                    week[n] = temp;
                    n++;
                }
                entries.add(new BarEntry(i, week));
                weekroutine.clear();
            }

            BarDataSet barDataSet = new BarDataSet(entries, "시간");
            barDataSet.setColors(ColorA);
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            barDataSet.setValueTextSize(10f);

            BarData data = new BarData(barDataSet);

            data.setBarWidth(0.4f);

            //그래프 위치 설정
            for (int d = 0; d < Chartdata; d++) {
                if (daylist.size() == d) {
                    Xaxis.setAxisMinimum(barDataSet.getXMin() - 0.5f - (Chartdata - d) * 0.5f);
                    Xaxis.setAxisMaximum(barDataSet.getXMax() + 0.5f + (Chartdata - d) * 0.5f);
                    break;
                } else {
                    Xaxis.setAxisMinimum(barDataSet.getXMin() - 0.5f);
                    Xaxis.setAxisMaximum(barDataSet.getXMax() + 0.5f);
                }
            }

            Xaxis.setGranularity(1f);

            Bar_Chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(daylist));

            if(entries.size() == 0){
                Bar_Chart.setNoDataText("일주일 간의 데이터가 없습니다.");
            }else{
                Bar_Chart.setData(data);
                Bar_Chart.getLegend().setEnabled(false); //하단 라벨 안보이게 설정
                Bar_Chart.setVisibleXRange(0, Chartdata);
                Bar_Chart.getBarData().setValueTextColor(0xFFBDBDBD);
                Bar_Chart.animateXY(1000, 1000);
            }

            Bar_Chart.invalidate();
        }
    }

    //선택한 기간의 데이터 가져오기
    ArrayList<ArrayList<ActInfoItem>> AllDayList = new ArrayList<>();

    private void getData(){
        ActInfoItemList.clear();
        ActInfoItemListTemp.clear();
        AllDayList.clear();
        ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
        ActInfoDAO mActInfoDao = db.actInfoDao();
        ActInfoList = new ArrayList<>(Arrays.asList(mActInfoDao.getItemByDate(Syear, Smonth, Sday, Eyear, Emonth, Eday)));
        ArrayList<ActInfo> ActDayList = new ArrayList<>();


        for(int i = 0; i < ActInfoList.size(); i++){
            ActInfoItemListTemp.add(new ActInfoItem(ActInfoList.get(i).getId(), ActInfoList.get(i).getCategory(), ActInfoList.get(i).getYear(),
                    ActInfoList.get(i).getMonth(), ActInfoList.get(i).getDate(), ActInfoList.get(i).getStartHour(), ActInfoList.get(i).getStartMinute(),
                    ActInfoList.get(i).getEndHour(), ActInfoList.get(i).getEndMinute()));
        }

        Collections.sort(ActInfoItemListTemp, new Comparator<ActInfoItem>(){
            public int compare(ActInfoItem o1, ActInfoItem o2) {
                if (o1.Year == o2.Year) {
                    if (o1.Month == o2.Month) {
                        if (o1.Date == o2.Date) return 0;
                        return o1.Date < o2.Date ? -1 : 1;
                    }return o1.Month < o2.Month ? -1 : 1;
                }
                    return o1.Year < o2.Year ? -1 : 1;
            }
        });

        for(int i = 0; i < ActInfoItemListTemp.size(); i++){
            //만약 날이 바뀌면 기존 리스트 AllDayList에 추가하고 새로운 ActInfoItemList 생성
            if((i>0)&&((ActInfoItemListTemp.get(i).Year != ActInfoItemListTemp.get(i-1).Year || ActInfoItemListTemp.get(i).Month != ActInfoItemListTemp.get(i-1).Month)
                    || ActInfoItemListTemp.get(i).Date != ActInfoItemListTemp.get(i-1).Date)){
                AllDayList.add(ActInfoItemList);
                ActInfoItemList = new ArrayList<>();
                ActInfoItemList.add(ActInfoItemListTemp.get(i));
            } //아니면 기존 리스트에 추가
            else{
                ActInfoItemList.add(ActInfoItemListTemp.get(i));
            }
        }
        AllDayList.add(ActInfoItemList);

    }

    //카테고리에 따른 값 계산
    private void getCategory(){
        float CategoryTime;
        String CurrentDay;
        //그 안에서 선택된 카테고리의 값만 가져오기
        for(int i = 0; i < AllDayList.size(); i++) {
            if (AllDayList.get(i).size() != 0) {
                CurrentDay = Integer.toString(AllDayList.get(i).get(0).Month) + "/" + Integer.toString(AllDayList.get(i).get(0).Date);
                DayList.add(CurrentDay);
            }
            CategoryTime = 0;
            for (int j = 0; j < AllDayList.get(i).size(); j++) {
                if (AllDayList.get(i).get(j).Category.equals(CurrentCategory)) {
                    float time;
                    float CalSTime = AllDayList.get(i).get(j).StartHour, CalETime = AllDayList.get(i).get(j).EndHour,
                            CalSMin = AllDayList.get(i).get(j).StartMinute, CalEMin = AllDayList.get(i).get(j).EndMinute;

                    if (CalETime == 0) {
                        CalETime = 24;
                    }

                    time = ((CalETime * 60 + CalEMin) - (CalSTime * 60 + CalSMin)) / 60;
                    CategoryTime += time;
                }
            }
            TimeList.add(CategoryTime);
        }
        ArrayList<Float> tempList = new ArrayList<>();
        tempList.addAll(TimeList);
        WeekTimeList.add(tempList);
    }

    //목표달성률
    private void GoalPercent(){
        int i = 0;
        int index, CurGoalType, Curgoal, SuccessGoal = 0, SizeOfData = AllDayList.size();

        index = SavedSettings.CategoryList.indexOf(CurrentCategory);
        CurGoalType = SavedSettings.GoalType.get(index);
        Curgoal = SavedSettings.Goal.get(index);
        if(CurGoalType == 1){
            float CategoryNum;
            for(int  k= 0; k < AllDayList.size(); k++) {
                CategoryNum = 0;
                for (int j = 0; j < AllDayList.get(k).size(); j++) {
                    if (AllDayList.get(k).get(j).Category.equals(CurrentCategory)) {
                        CategoryNum++;
                    }
                }
                if(CategoryNum >= Curgoal){
                    SuccessGoal++;
                }
            }

        }else{
            float CategoryTime;
            for(int  k= 0; k < AllDayList.size(); k++) {
                CategoryTime = 0;
                for (int j = 0; j < AllDayList.get(k).size(); j++) {
                    if (AllDayList.get(k).get(j).Category.equals(CurrentCategory)) {
                        float time;
                        float CalSTime = AllDayList.get(k).get(j).StartHour, CalETime = AllDayList.get(k).get(j).EndHour,
                                CalSMin = AllDayList.get(k).get(j).StartMinute, CalEMin = AllDayList.get(k).get(j).EndMinute;

                        if (CalETime == 0) {
                            CalETime = 24;
                        }

                        time = ((CalETime * 60 + CalEMin) - (CalSTime * 60 + CalSMin)) / 60;
                        CategoryTime += time;
                    }
                }
                if(CategoryTime >= Curgoal){
                    SuccessGoal++;
                }
            }
        }

        CircularProgressBar.setMax(SizeOfData);
        CircularProgressBar.setProgress(SuccessGoal, true);
        PercnetText.setText(""+(Math.round((double)SuccessGoal/(double)SizeOfData*100))+"%");
        ProgressDataText.setText("총 "+(EDate-SDate+1)+"일 동안 "+SuccessGoal+"번 목표를 달성했습니다.");

    }

    //stacked bar chart
    public void weekChart(){
        Syear = ChartCalender.get(Calendar.YEAR);
        Smonth = ChartCalender.get(Calendar.MONTH)+1;
        Sday = ChartCalender.get(Calendar.DATE)-6;
        Eyear = ChartCalender.get(Calendar.YEAR);
        Emonth = ChartCalender.get(Calendar.MONTH)+1;
        Eday = ChartCalender.get(Calendar.DATE);
        DefaultData = true;
        WeekTimeList.clear();

        getData();

        for(int i = 0; i < SavedSettings.CategoryList.size(); i++){
            DayList.clear();
            TimeList.clear();
            CurrentCategory = SavedSettings.CategoryList.get(i);
            getCategory();
        }
        drawStackedBarChart(DayList, WeekTimeList);
    }

}
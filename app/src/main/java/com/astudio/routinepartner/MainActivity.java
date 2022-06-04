package com.astudio.routinepartner;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    AlertDialog Dialog;
    NumberPicker[] NumPickers = new NumberPicker[10];
    Button Btn1, Btn2, Btn3, Btn4, Btn5, BtnCalendar, BtnOK, BtnCancel, BtnAddTestData, BtnDeleteAll, BtnShowPieChart,BtnSelectDay;
    SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
    Calendar cal;
    int Year, Month, Date;
    ImageButton BtnShowList;
    EditText EdtCategory;
    TextView TxtTime;
    int CategoryNum, PieYear, PieMonth, PieDay, YesterdayYear, YesterdayMonth, YesterdayDay;
    Calendar PieCalendar = Calendar.getInstance();
    Calendar YesterdayCal = Calendar.getInstance();

    PieChartView PieChart;
    ArrayList<Integer> TimeList = new ArrayList<Integer>();
    ArrayList<Float> AngleList = new ArrayList<Float>();
    ArrayList<Float> YesterDayAngleList = new ArrayList<>();


    protected ArrayList<ActInfo> ActInfoList;
    protected ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();
    protected ArrayList<ActInfoItem> ActInfoYesterdayItemList = new ArrayList<ActInfoItem>();


    LottieAnimationView PetView;
    String Action ="";
    int ActionInt;
    Context MainContext;
    RadarChart PetStateChart;
    ArrayList<String> LableList=new ArrayList<>();
    ArrayList<Integer> LableListInt=new ArrayList<>();
    boolean NotEnd=false;

    @Override
    protected void onStart() {
        super.onStart();
        if(SavedSettings.isRefreshed == false) {
            refreshSettings();
        }
        int CategoryNum = SavedSettings.CategoryList.size();
        Log.i("onStart", "" + CategoryNum);
        for(int i = 0; i < CategoryNum; i++) {
            if (i == 0) {
                GradientDrawable RS1 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square1);
                RS1.setColor(SavedSettings.ColorList.get(0).intValue());
                Btn1.setVisibility(View.VISIBLE);
                Btn1.setText(SavedSettings.CategoryList.get(0));
                Btn1.setBackgroundResource(R.drawable.round_square1);
                Btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cal = Calendar.getInstance();
                        BtnCalendar.setText(SDF.format(cal.getTime()));
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(0));
                    }
                });
            }
            if (i == 1) {
                GradientDrawable RS2 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square2);
                RS2.setColor(SavedSettings.ColorList.get(1).intValue());
                Btn2.setVisibility(View.VISIBLE);
                Btn2.setText(SavedSettings.CategoryList.get(1));
                Btn2.setBackgroundResource(R.drawable.round_square2);
                Btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cal = Calendar.getInstance();
                        BtnCalendar.setText(SDF.format(cal.getTime()));
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(1));
                    }
                });
            }
            if (i == 2) {
                GradientDrawable RS3 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square3);
                RS3.setColor(SavedSettings.ColorList.get(2).intValue());
                Btn3.setVisibility(View.VISIBLE);
                Btn3.setText(SavedSettings.CategoryList.get(2));
                Btn3.setBackgroundResource(R.drawable.round_square3);
                Btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cal = Calendar.getInstance();
                        BtnCalendar.setText(SDF.format(cal.getTime()));
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(2));
                    }
                });
            }
            if (i == 3) {
                GradientDrawable RS4 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square4);
                RS4.setColor(SavedSettings.ColorList.get(3).intValue());
                Btn4.setVisibility(View.VISIBLE);
                Btn4.setText(SavedSettings.CategoryList.get(3));
                Btn4.setBackgroundResource(R.drawable.round_square4);
                Btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cal = Calendar.getInstance();
                        BtnCalendar.setText(SDF.format(cal.getTime()));
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(3));
                    }
                });
            }
            if (i == 4) {
                GradientDrawable RS5 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square5);
                RS5.setColor(SavedSettings.ColorList.get(4).intValue());
                Btn5.setVisibility(View.VISIBLE);
                Btn5.setText(SavedSettings.CategoryList.get(4));
                Btn5.setBackgroundResource(R.drawable.round_square5);
                Btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cal = Calendar.getInstance();
                        BtnCalendar.setText(SDF.format(cal.getTime()));
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(4));
                    }
                });
            }
        }
        timeToAngle(PieYear, PieMonth, PieDay);
        timeToAngleYesterday(YesterdayYear, YesterdayMonth, YesterdayDay);
        Toast.makeText(getApplicationContext(), "" + AngleList.size(), Toast.LENGTH_SHORT).show();
        sendDataToPieChart();
        PieChart.update();
        if(SavedSettings.isRefreshed == false) {
            RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget);

            ArrayList<Integer> CategoryBtnId = new ArrayList<Integer>(Arrays.asList(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5));
            for(int j = 0; j < 5; j++) {
                if (j < CategoryNum) {
                    views.setInt(CategoryBtnId.get(j), "setVisibility", View.VISIBLE);
                    views.setTextViewText(CategoryBtnId.get(j), SavedSettings.CategoryList.get(j));
                    views.setInt(CategoryBtnId.get(j), "setBackgroundColor", SavedSettings.ColorList.get(j).intValue());
                    views.setTextViewTextSize(CategoryBtnId.get(j), 0, 75f);
                } else {
                    views.setInt(CategoryBtnId.get(j), "setVisibility", View.GONE);
                }
            }
            Log.i("aaa", "" + CategoryNum);
            //AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(AppWidgetManager.getInstance(getApplicationContext()).getAppWidgetIds(new ComponentName(this.getPackageName(), WidgetProvider.class.getName())), views);
            //AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(new ComponentName(this.getPackageName(), WidgetProvider.class.getName()), views);
            finish();
            startActivity(getIntent());
        }
        Intent intent = new Intent(this, WidgetProvider.class);
        intent.setAction("SETTINGS_CHANGED");
        this.sendBroadcast(intent);
        //Log.i("WidgetId", "" + intent.getAction());
        SavedSettings.isRefreshed = true;
        setRadarData();
        //Log.i("in StartActivity", "" + AppWidgetManager.getInstance(getApplicationContext()).getAppWidgetIds(new ComponentName(this.getPackageName(), WidgetProvider.class.getName()))[0]);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //<--------------------------------------------------------------------YJS
        //<--------------------------------------------------------------------YJS
        PetStateChart=findViewById(R.id.RadarChart);
        if(SavedSettings.isRefreshed == false) {
            refreshSettings();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Btn1 = findViewById(R.id.btn1);
        Btn2 = findViewById(R.id.btn2);
        Btn3 = findViewById(R.id.btn3);
        Btn4 = findViewById(R.id.btn4);
        Btn5 = findViewById(R.id.btn5);
        Btn1.setVisibility(View.GONE);
        Btn2.setVisibility(View.GONE);
        Btn3.setVisibility(View.GONE);
        Btn4.setVisibility(View.GONE);
        Btn5.setVisibility(View.GONE);
        BtnShowList = findViewById(R.id.btnShowList);
        BtnAddTestData = findViewById(R.id.btnAddTestData);
        BtnDeleteAll = findViewById(R.id.btnDeleteAll);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add, null);

        EdtCategory = dialogView.findViewById(R.id.edtCategory);
        BtnCalendar = dialogView.findViewById(R.id.btnCalendar);
        BtnOK = dialogView.findViewById(R.id.btnOK);
        BtnCancel = dialogView.findViewById(R.id.btnCancel);
        YJS.numPickerSetting(dialogView, NumPickers);

        builder.setView(dialogView);
        Dialog = builder.create();

        DatePickerDialog.OnDateSetListener DatePickerDiag = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                BtnCalendar.setText(SDF.format(cal.getTime()));
                Toast.makeText(getApplicationContext(), "" + cal.get(Calendar.YEAR) + " " + (cal.get(Calendar.MONTH) + 1) + " " + cal.get(Calendar.DATE), Toast.LENGTH_SHORT).show();
            }
        };
        BtnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = cal.get(Calendar.YEAR);
                Month = cal.get(Calendar.MONTH);
                Date = cal.get(Calendar.DATE);
                new DatePickerDialog(getContext(), R.style.MyDatePickerStyle, DatePickerDiag, Year, Month, Date).show();
            }
        });
        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = cal.get(Calendar.YEAR);
                Month = cal.get(Calendar.MONTH);
                Date = cal.get(Calendar.DATE);
                makeData();
                timeToAngle(PieYear, PieMonth, PieDay);
                timeToAngleYesterday(YesterdayYear, YesterdayMonth, YesterdayDay);
                Toast.makeText(getApplicationContext(), "" + AngleList.size(), Toast.LENGTH_SHORT).show();
                sendDataToPieChart();
                PieChart.update();
                PetView.setRepeatCount(1);  //[PSY] 추가코드
                PetView.playAnimation();    //[PSY] 추가코드
            }
        });
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.dismiss();
            }
        });
        BtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });

        BtnAddTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTestData();
            }
        });
        BtnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                ActInfoDAO mActInfoDao = db.actInfoDao();
                mActInfoDao.deleteAll();
                setRadarData();//[PSY] 추가코드
                PSY.InteractionNum=0; //[PSY] 추가코드
                PreferenceManage.clear(MainContext); //[PSY] 추가코드
                Toast.makeText(getApplicationContext(), "All Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        //-------------------------------------------------------------------->YJS
        //-------------------------------------------------------------------->YJS

        //<--------------------------------------------------------------------LSY
        //<--------------------------------------------------------------------LSY

        PieYear = PieCalendar.get(Calendar.YEAR);
        PieMonth = PieCalendar.get(Calendar.MONTH)+1;
        PieDay = PieCalendar.get(Calendar.DAY_OF_MONTH);

        YesterdayCal.add(PieCalendar.DATE, -1);

        YesterdayYear = YesterdayCal.get(Calendar.YEAR);
        YesterdayMonth = YesterdayCal.get(Calendar.MONTH)+1;
        YesterdayDay = YesterdayCal.get(Calendar.DAY_OF_MONTH);

        BtnShowPieChart = findViewById(R.id.btnShowPieChart);
        ImageButton BtnChart = (ImageButton) findViewById(R.id.BtnChart);
        ImageButton SettingButton = findViewById(R.id.SettingBtn);
        BtnSelectDay = findViewById(R.id.SelectDayBtn);
        updateDate();


        PieChart = (PieChartView) findViewById(R.id.PieChartView);
        BtnShowPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        BtnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  BarIntent = new Intent(getApplicationContext(), BarChartActivity.class);
                startActivity(BarIntent);
            }
        });

        //설정 창으로 넘어가는 버튼

        SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SettingIntent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(SettingIntent);
            }
        });
        
        //원형시간표 날짜 선택 버튼

        DatePickerDialog.OnDateSetListener PieDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                PieCalendar.set(Calendar.YEAR, year);
                PieCalendar.set(Calendar.MONTH, month);
                PieCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateDate();

                PieYear = year;
                PieMonth = month+1;
                PieDay = day;

                YesterdayCal = new GregorianCalendar(year, month, day);
                YesterdayCal.add(YesterdayCal.DATE, -1);

                YesterdayYear = YesterdayCal.get(Calendar.YEAR);
                YesterdayMonth = YesterdayCal.get(Calendar.MONTH)+1;
                YesterdayDay = YesterdayCal.get(Calendar.DAY_OF_MONTH);

                timeToAngle(PieYear, PieMonth, PieDay);
                timeToAngleYesterday(YesterdayYear, YesterdayMonth, YesterdayDay);
                sendDataToPieChart();
                PieChart.update();
            }
        };

        BtnSelectDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, R.style.MyDatePickerStyle, PieDatePicker, PieCalendar.get(Calendar.YEAR),
                        PieCalendar.get(Calendar.MONTH), PieCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //앱이 켜질때 저장되어있던 카테고리 값 셋팅

        SetCategoryAdapter.CategoryItem.clear();
        for(int i = 0; i < SavedSettings.CategoryList.size(); i++){
            SetCategoryAdapter.CategoryItem.add(new CategoryInfo(SavedSettings.CategoryList.get(i), SavedSettings.ColorList.get(i),
                    SavedSettings.AffectingStat.get(i), SavedSettings.GoalType.get(i), SavedSettings.Goal.get(i), SavedSettings.Order.get(i)));
        }



        //-------------------------------------------------------------------->LSY
        //-------------------------------------------------------------------->LSY

        //<--------------------------------------------------------------------PSY
        //<--------------------------------------------------------------------PSY


        MainContext=this;

        PetView=findViewById(R.id.lottie);
        PetView.setAnimation("HappyDog.json");
        PetView.setRepeatCount(2);
        PetView.playAnimation();

        PetView.addAnimatorListener(new Animator.AnimatorListener() {
            FadeInAndOut FadeAnimation=new FadeInAndOut();
            @Override
            public void onAnimationStart(Animator animator) {
                ImageView img=(ImageView) findViewById(R.id.imageView);
                switch(Action){
                    case "1"://지능
                        img.setImageResource(R.drawable.book);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "2"://재미
                        img.setImageResource(R.drawable.petball);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "3"://체력
                        img.setImageResource(R.drawable.petbed);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "4"://포만감
                        img.setImageResource(R.drawable.foodbowlnew);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "5"://잔고
                        img.setImageResource(R.drawable.coin);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "6"://자아실현
                        //img.setImageResource(R.drawable.dogbed_brown);
                        //FadeAnimation.fadeOutImage(img);
                        break;


                    default:
                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                PetView.setOnClickListener((view -> {
                    Action ="";
                    PetView.setRepeatCount(1);
                    PetView.playAnimation();
                }));

                PetView.setOnLongClickListener((view) -> {
                    Action ="interaction";
                    PSY.InteractionNum++;
                    ImageView HeartImage=findViewById(R.id.HeartImage);
                    HeartImage.setImageResource(R.drawable.heartvector);
                    FadeAnimation.fadeInImage(HeartImage);
                    PetView.setRepeatCount(1);
                    PetView.playAnimation();

                    setRadarData();
                    return true;
                });

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });



        /*ArrayList<String> CategoryStat=new ArrayList<>(Arrays.asList("포만감","체력","지능","체력"));
        //ArrayList<String> CategoryList = new ArrayList<>(Arrays.asList("식사", "수면", "공부", "운동"));
        ArrayList<Integer> Order=new ArrayList<>(Arrays.asList(1,2,3,4));
        ArrayList<String> StatList = SavedSettings.StatList;
        ArrayList<Integer> AffectingStat=SavedSettings.AffectingStat;

        PetStateChart=findViewById(R.id.RadarChart);

        for(int stat:AffectingStat){
            if(!LableListInt.contains(stat))
                LableListInt.add(stat);  //4 2 1
        }
        setRadarDataFirst();

        String[] labels=new String[LableListInt.size()];
        int size=0;
        for(int item:LableListInt){
            labels[size]=item;
            size++;
        }*/
        ArrayList<String> CategoryStat=new ArrayList<>(Arrays.asList("포만감","체력","지능","체력"));
        //ArrayList<String> CategoryList = new ArrayList<>(Arrays.asList("식사", "수면", "공부", "운동"));
        ArrayList<Integer> Order=new ArrayList<>(Arrays.asList(1,2,3,4));
        ArrayList<String> StatList = SavedSettings.StatList;
        //ArrayList<String> StatList = SavedSettings.StatList;
        ArrayList<Integer> AffectingStat=SavedSettings.AffectingStat;  //4, 2, 4, 1, 2
        ArrayList<String> CategoryList=SavedSettings.CategoryList;  //"취침", "식사", "공부", "게임", "운동"

        if(!CategoryStat.isEmpty()){
            for(String stat:CategoryStat){
                if(!LableList.contains(stat))
                    LableList.add(stat);
            }
        }

        if(!AffectingStat.isEmpty()){
            for(int stat:AffectingStat){
                if(!LableListInt.contains(stat))
                    LableListInt.add(stat);  //4 2 1
            }
        }

        String[] labels=new String[LableListInt.size()+1];
        for(int i=0;i<LableListInt.size();i++){
            switch(LableListInt.get(i)){
                case 1: labels[i]="지능"; LableList.add(labels[i]); break;
                case 2: labels[i]="재미"; LableList.add(labels[i]); break;
                case 3: labels[i]="체력"; LableList.add(labels[i]); break;
                case 4: labels[i]="포만감"; LableList.add(labels[i]); break;
                case 5: labels[i]="잔고"; LableList.add(labels[i]); break;
                case 6: labels[i]="자아실현"; LableList.add(labels[i]); break;

            }
        }
        labels[LableListInt.size()]="애정";  //애정 부분 추가



        /*String[] labels=new String[LableList.size()];
        int size=0;
        for(String item:LableList){
            labels[size]=item;
            size++;
        }*/

        XAxis xAxis=PetStateChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextColor(0xFF898989);
        xAxis.setTextSize(8f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(100);
        YAxis yAxis=PetStateChart.getYAxis();
        yAxis.setDrawLabels(false);
        yAxis.setLabelCount(5,true);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(100);
        yAxis.setDrawLabels(false);
        Legend legend=PetStateChart.getLegend();
        legend.setTextColor(0xFF898989);
        PetStateChart.getDescription().setEnabled(false);

        /*for(IDataSet<?> set:PetStateChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }//이 부분 문제

        PetStateChart.invalidate();*/

        //-------------------------------------------------------------------->PSY
        //-------------------------------------------------------------------->PSY
    }

    //<--------------------------------------------------------------------YJS
    //<--------------------------------------------------------------------YJS
    public Context getContext() {
        return this;
    }
    public void refreshSettings() {
        SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
        SettingsDAO mSettingsDao = db.settingDao();
        TimeIntervalDAO mTimeIntervalDao = db.timeIntervalDao();
        //Log.i("CategoryNum", "" + mSettingsDao.getAll().length);
        int CategoryNum = mSettingsDao.getAll().length;
        //Log.i("in Main", "" + CategoryNum);
        if(CategoryNum == 0) {
            Settings settings = new Settings();
            settings.setCategory("수면");
            settings.setColor(0xFFCCCCFFL);
            settings.setGoalType(2);
            settings.setGoal(7);
            settings.setAffectingStat(3);
            settings.setOrder(1);
            mSettingsDao.insert(settings);
            settings.setCategory("식사");
            settings.setColor(0xFFCCFFFFL);
            settings.setGoalType(1);
            settings.setGoal(3);
            settings.setAffectingStat(4);
            settings.setOrder(2);
            mSettingsDao.insert(settings);
            TimeInterval timeInterval = new TimeInterval();
            timeInterval.setInterval(3);
            mTimeIntervalDao.insert(timeInterval);
            setRadarData();
        }
        SavedSettings.CategoryList.clear();
        SavedSettings.ColorList.clear();
        SavedSettings.GoalType.clear();
        SavedSettings.Goal.clear();
        SavedSettings.AffectingStat.clear();
        SavedSettings.Order.clear();
        for(int i = 0; i < mSettingsDao.getAll().length; i++) {
            SavedSettings.CategoryList.add(mSettingsDao.getAll()[i].getCategory());
            SavedSettings.ColorList.add(mSettingsDao.getAll()[i].getColor());
            SavedSettings.GoalType.add(mSettingsDao.getAll()[i].getGoalType());
            SavedSettings.Goal.add(mSettingsDao.getAll()[i].getGoal());
            SavedSettings.AffectingStat.add(mSettingsDao.getAll()[i].getAffectingStat());
            SavedSettings.Order.add(mSettingsDao.getAll()[i].getOrder());
        }
        SavedSettings.TimeInterval = mTimeIntervalDao.getAll().getInterval();
            //Log.i("initializing","" + mSettingsDao.getAll().length);
        //Log.i("initializing Done","");
        SavedSettings.isRefreshed = true;
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
    public void showList() {
        Intent intent = new Intent(this, ActInfoListActivity.class);
        startActivity(intent);
        NotEnd=true;
    }

    public void makeData() {
        Calendar cal = Calendar.getInstance();
        String CategoryName;
        int SAMPM, Shour, Sminute, EAMPM, Ehour, Eminute;
        CategoryName = EdtCategory.getText().toString();

        SAMPM = NumPickers[0].getValue();
        Shour = NumPickers[2].getValue();
        Sminute = NumPickers[4].getValue();
        EAMPM = NumPickers[1].getValue();
        Ehour = NumPickers[3].getValue();
        Eminute = NumPickers[5].getValue();
//        TxtTime.setText(CategoryName + "\n" + Year + "-" + Month + "-" + Date + "  " + SAMPM + " " + Shour + ":" + Sminute + " ~ " + EAMPM + " " + Ehour + ":" + Eminute);

        addToActDB(CategoryName, Year, Month + 1, Date, SAMPM == 0 ? Shour : Shour + 12, Sminute, EAMPM == 0 ? Ehour : Ehour + 12, Eminute);


        ArrayList<Integer> AffectingStat=SavedSettings.AffectingStat;
        ArrayList<String> StatList=SavedSettings.StatList;
        //Action=CategoryName;  //[PSY] 추가코드
        ActionInt=CategoryList.indexOf(CategoryName);  //취침은 CategoryList에서 0번째 위치->0번째 위치한 AffectingStat 이 무엇인지
        if(ActionInt>=0){
          int AffectingStatIndex=AffectingStat.get(ActionInt);
          switch(StatList.get(AffectingStatIndex-1)){  //"지능0", "재미1", "체력2", "포만감3", "잔고4", "자아실현5"
              case "지능": Action="1"; break;
              case "재미": Action="2"; break;
              case "체력": Action="3"; break;
              case "포만감": Action="4"; break;
              case "잔고": Action="5"; break;
              case "자아실현": Action="6"; break;
          }
        }
        setRadarData();       //[PSY] 추가코드

        Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
        Dialog.dismiss();
    }

    public void openDialog() {
        Dialog.show();
        for(int i = 0; i < 6; i++) {
            NumPickers[i].setValue(0);
        }
    }

    public void makeTestData() {
        /*for(int i = 1; i < 31; i++) {
            addToActDB("취침", 2022, 4, i, 0, 0, 6, 0);
            addToActDB("식사", 2022, 4, i, 8, 0, 9, 0);
            addToActDB("공부", 2022, 4, i, 10, 0, 12, 0);
            addToActDB("식사", 2022, 4, i, 13, 0, 14, 0);
            addToActDB("운동", 2022, 4, i, 16 , 0, 18, 0);
            addToActDB("식사", 2022, 4, i, 18, 0, 19, 0);
            addToActDB("게임", 2022, 4, i, 20, 0, 22, 0);
            addToActDB("취침", 2022, 4, i, 22, 0, 24, 0);
        }*/
        for(int i = 1; i < 31; i++) {//31로 돌려놓기
            addToActDB("수면", 2022, 5, i, 0, 0, 6, 0);
            addToActDB("식사", 2022, 5, i, 8, 0, 9, 0);
            addToActDB("공부", 2022, 5, i, 10, 0, 12, 0);
            addToActDB("식사", 2022, 5, i, 13, 0, 14, 0);
            //addToActDB("운동", 2022, 5, i, 16 , 0, 18, 0);
            addToActDB("식사", 2022, 5, i, 18, 0, 19, 0);
            //addToActDB("게임", 2022, 5, i, 20, 0, 22, 0);
            addToActDB("수면", 2022, 5, i, 22, 0, 24, 0);
        }
        for(int i = 0; i < SavedSettings.CategoryList.size(); i++) {
            //addToSettingsDB(SavedSettings.CategoryList.get(i), SavedSettings.ColorList.get(i), SavedSettings.GoalType.get(i), SavedSettings.Goal.get(i), SavedSettings.AffectingStat.get(i), SavedSettings.Order.get(i));
            Log.i("11111", "--" + SavedSettings.CategoryList.get(i)+ "  -  " + SavedSettings.ColorList.get(i)+ " " + SavedSettings.GoalType.get(i)+ SavedSettings.Goal.get(i)+ SavedSettings.AffectingStat.get(i)+ SavedSettings.Order.get(i));
        }
        setRadarData(); //[PSY] 추가 코드
    }

    public void addToActDB(String Category, int Year, int Month, int Date, int Shour, int Sminute, int Ehour, int Eminute) {

        ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
        ActInfoDAO mActInfoDao = db.actInfoDao();
        ActInfo actInfo = new ActInfo();
        actInfo.setCategory(Category);
        actInfo.setYear(Year);
        actInfo.setMonth(Month);
        actInfo.setDate(Date);
        actInfo.setStartHour(Shour);
        actInfo.setStartMinute(Sminute);
        actInfo.setEndHour(Ehour);
        actInfo.setEndMinute(Eminute);
        mActInfoDao.insert(actInfo);

    }

    public void addToSettingsDB(String Category, long Color, int GoalType, int Goal, int AffectingStat, int Order) {

        SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
        SettingsDAO mSettingsDao = db.settingDao();
        Settings settings = new Settings();
        settings.setCategory(Category);
        settings.setColor(Color);
        settings.setGoalType(GoalType);
        settings.setGoal(Goal);
        settings.setAffectingStat(AffectingStat);
        settings.setOrder(Order);
        mSettingsDao.insert(settings);

    }

    //-------------------------------------------------------------------->YJS
    //-------------------------------------------------------------------->YJS

    //<--------------------------------------------------------------------LSY
    //<--------------------------------------------------------------------LSY
    private void sendDataToPieChart(){
        PieChart.reset();
        PieChart.CategoryList.clear();
        try{
            PieChart.Data.addAll(AngleList); //타임 리스트에 있는 시간에 관한 데이터를 파이차트뷰의 데이터리스트로 넘김
            Log.v("시간", ""+AngleList);
            PieChart.CategoryList.addAll(PieCategoryList);
            Log.v("카테고리", ""+PieCategoryList);
            if(YesterDayAngleList != null){
                PieChart.YesterdayData.addAll(YesterDayAngleList);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    ArrayList<String> PieCategoryList = new ArrayList<>();

    //숫자로 받을 경우 바로 TimeLsit에 추가

//    public void timeToAngle(){
//        Calendar cal = Calendar.getInstance();
//
//        AngleList.clear();
//        PieCategoryList.clear();
//        ActInfoItemList.clear();
//        AngleList.clear();
//        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
//            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
//            ActInfoDAO mActInfoDao = db.actInfoDao();
//            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE))));
//        });
//        for(int i = 0; i < ActInfoList.size(); i++) {
//            ActInfoItemList.add(new ActInfoItem(ActInfoList.get(i).getId(), ActInfoList.get(i).getCategory(), ActInfoList.get(i).getYear(), ActInfoList.get(i).getMonth(), ActInfoList.get(i).getDate(), ActInfoList.get(i).getStartHour(), ActInfoList.get(i).getStartMinute(), ActInfoList.get(i).getEndHour(), ActInfoList.get(i).getEndMinute()));
//        }
//        Collections.sort(ActInfoItemList, new Comparator<ActInfoItem>(){
//            public int compare(ActInfoItem o1, ActInfoItem o2) {
//                if(o1.StartHour == o2.StartHour) {
//                    if(o1.StartMinute == o2.StartMinute) return 0;
//                    return o1.StartMinute < o2.StartMinute ? -1 : 1;
//                }
//                return o1.StartHour < o2.StartHour ? -1 : 1;
//            }
//        });
//        for(int i = 0; i < ActInfoItemList.size(); i++) {
//            AngleList.add(ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f + 270);
//            AngleList.add(ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f);
//            PieCategoryList.add(ActInfoItemList.get(i).Category);
//            Log.i("" + (ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f), "" + (ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f));
//            Log.v("카테고리", ""+ActInfoItemList.get(i).Category);
//        }
//
//    }


    public void timeToAngle(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();

        AngleList.clear();
        PieCategoryList.clear();
        ActInfoItemList.clear();
        AngleList.clear();
        ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
        ActInfoDAO mActInfoDao = db.actInfoDao();
        ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(year, month, day, year, month, day)));

        for (int i = 0; i < ActInfoList.size(); i++) {
            ActInfoItemList.add(new ActInfoItem(ActInfoList.get(i).getId(), ActInfoList.get(i).getCategory(), ActInfoList.get(i).getYear(), ActInfoList.get(i).getMonth(), ActInfoList.get(i).getDate(), ActInfoList.get(i).getStartHour(), ActInfoList.get(i).getStartMinute(), ActInfoList.get(i).getEndHour(), ActInfoList.get(i).getEndMinute()));
        }
        Collections.sort(ActInfoItemList, new Comparator<ActInfoItem>() {
            public int compare(ActInfoItem o1, ActInfoItem o2) {
                if (o1.StartHour == o2.StartHour) {
                    if (o1.StartMinute == o2.StartMinute) return 0;
                    return o1.StartMinute < o2.StartMinute ? -1 : 1;
                }
                return o1.StartHour < o2.StartHour ? -1 : 1;
            }
        });
        for (int i = 0; i < ActInfoItemList.size(); i++) {
            AngleList.add(ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f + 270);
            if(ActInfoItemList.get(i).EndHour == 0 && ActInfoItemList.get(i).EndMinute == 0){
                ActInfoItemList.get(i).EndHour = 24;
            }
            AngleList.add(ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f);
            PieCategoryList.add(ActInfoItemList.get(i).Category);
            Log.i("" + (ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f), "" + (ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f));
        }
    }


//    public void timeToAngleYesterday(){
//        int BeforeTime, AfterTime;
//        float StartAngle, DrawAngle;
//
//        YesterDayAngleList.clear();
//
//        Calendar cal = Calendar.getInstance();
//        ActInfoYesterdayItemList.clear();
//        YesterDayAngleList.clear();
//        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
//            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
//            ActInfoDAO mActInfoDao = db.actInfoDao();
//            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)-1, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)-1)));
//        });
//        Collections.sort(ActInfoList, new Comparator<ActInfo>(){
//            public int compare(ActInfo o1, ActInfo o2) {
//                if(o1.getStartHour() == o2.getStartHour()) {
//                    if(o1.getStartMinute() == o2.getStartMinute()) return 0;
//                    return o1.getStartMinute() < o2.getStartMinute() ? -1 : 1;
//                }
//                return o1.getStartHour() < o2.getStartHour() ? -1 : 1;
//            }
//        });
//        int YdSize = ActInfoList.size()-1;
//        if((YdSize > 0) && (ActInfoList.get(YdSize).getCategory().equals(PieCategoryList.get(0))) && (ActInfoList.get(YdSize).getEndHour() == 24)
//        && (AngleList.get(0) == 270)){
//            ActInfoYesterdayItemList.add(new ActInfoItem(ActInfoList.get(YdSize).getId(), ActInfoList.get(YdSize).getCategory(), ActInfoList.get(YdSize).getYear(), ActInfoList.get(YdSize).getMonth(), ActInfoList.get(YdSize).getDate(), ActInfoList.get(YdSize).getStartHour(), ActInfoList.get(YdSize).getStartMinute(), ActInfoList.get(YdSize).getEndHour(), ActInfoList.get(YdSize).getEndMinute()));
//            BeforeTime = ActInfoYesterdayItemList.get(0).StartHour*60 + ActInfoYesterdayItemList.get(0).StartMinute;
//            AfterTime = ActInfoYesterdayItemList.get(0).EndHour*60 + ActInfoYesterdayItemList.get(0).EndMinute;
//
//            if(AfterTime == 0){
//                AfterTime = 1440;
//            }
//
//            StartAngle = BeforeTime * 0.25f - 90;
//            DrawAngle = (AfterTime - BeforeTime) * 0.25f;
//
//            YesterDayAngleList.add(StartAngle);
//            YesterDayAngleList.add(DrawAngle);
//        }
//    }


    public void timeToAngleYesterday(int year, int month, int day){
        int BeforeTime, AfterTime;
        float StartAngle, DrawAngle;

        YesterDayAngleList.clear();

        Calendar cal = Calendar.getInstance();
        ActInfoYesterdayItemList.clear();
        YesterDayAngleList.clear();
        ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
        ActInfoDAO mActInfoDao = db.actInfoDao();
        ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(year, month, day, year, month, day)));

        Collections.sort(ActInfoList, new Comparator<ActInfo>(){
            public int compare(ActInfo o1, ActInfo o2) {
                if(o1.getStartHour() == o2.getStartHour()) {
                    if(o1.getStartMinute() == o2.getStartMinute()) return 0;
                    return o1.getStartMinute() < o2.getStartMinute() ? -1 : 1;
                }
                return o1.getStartHour() < o2.getStartHour() ? -1 : 1;
            }
        });
        int YdSize = ActInfoList.size()-1;
        if((YdSize >= 0) && (PieCategoryList.size() > 0) && (AngleList.size() > 0) && (ActInfoList.get(YdSize).getCategory().equals(PieCategoryList.get(0))) && (ActInfoList.get(YdSize).getEndHour() == 0)
                && (AngleList.get(0) == 270)){
            ActInfoYesterdayItemList.add(new ActInfoItem(ActInfoList.get(YdSize).getId(), ActInfoList.get(YdSize).getCategory(), ActInfoList.get(YdSize).getYear(), ActInfoList.get(YdSize).getMonth(), ActInfoList.get(YdSize).getDate(), ActInfoList.get(YdSize).getStartHour(), ActInfoList.get(YdSize).getStartMinute(), ActInfoList.get(YdSize).getEndHour(), ActInfoList.get(YdSize).getEndMinute()));
            BeforeTime = ActInfoYesterdayItemList.get(0).StartHour*60 + ActInfoYesterdayItemList.get(0).StartMinute;
            AfterTime = ActInfoYesterdayItemList.get(0).EndHour*60 + ActInfoYesterdayItemList.get(0).EndMinute;

            if(AfterTime == 0){
                AfterTime = 1440;
            }

            StartAngle = BeforeTime * 0.25f - 90;
            DrawAngle = (AfterTime - BeforeTime) * 0.25f;

            YesterDayAngleList.add(StartAngle);
            YesterDayAngleList.add(DrawAngle);
        }
    }

    public void updateDate(){
        String Format = "yyyy/MM/dd";
        SimpleDateFormat SDF = new SimpleDateFormat(Format, Locale.KOREA);

        BtnSelectDay.setText(SDF.format(PieCalendar.getTime()));
    }

    //-------------------------------------------------------------------->LSY
    //-------------------------------------------------------------------->LSY


    //<--------------------------------------------------------------------PSY
    //<--------------------------------------------------------------------PSY
    //ArrayList<String> CategoryList=new ArrayList<>(Arrays.asList("식사","취침","공부","운동"));
    ArrayList<String> CategoryList=SavedSettings.CategoryList;  //"식사","취침","공부","운동","게임"
    ArrayList<String> CategoryStat =SavedSettings.StatList;  // "지능", "재미", "체력", "포만감", "잔고", "자아실현"
    ArrayList<Integer> AffectingStat=SavedSettings.AffectingStat;
    ArrayList<Integer> GoalType=SavedSettings.GoalType;
    int AffectionEntry=0;
    String today;
    //ArrayList<String> CategoryStat = new ArrayList<>(Arrays.asList("포만감", "체력", "지능", "체력"));

    public void setRadarData(){
        ArrayList<RadarEntry> visitors=new ArrayList<>();

        ArrayList<ArrayList<Float>> ByDateCategoryDataList=new ArrayList<>(); //하루의 모든 기록 속에 들어있는 데이터를 카테고리별로 분류해서 넣어놓는 용도
        ArrayList<Float> TotalCategoryDataList=new ArrayList<>();
        ArrayList<Float> TotalStatDataList=new ArrayList<>();
        ArrayList<ArrayList<ActInfoItem>> DayList=new ArrayList<>();  //ArrayList<ActInfoItem>->하루 기록 리스트->그거의 리스트: 날짜별 기록을 가지는 리스트
        Calendar cal = Calendar.getInstance();

        PSY PetStateManage=new PSY();
        today=""+cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE);

        ActInfoItemList.clear();
        DayList.clear();
        TotalStatDataList.clear();
            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
            ActInfoDAO mActInfoDao = db.actInfoDao();
            //ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE))));
            ActInfoList=new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getAll()));

        /*for(int stat:AffectingStat){
            if(!LableListInt.contains(stat))
                LableListInt.add(stat);  //4 2 1
        }*/

        ArrayList<String> DateList=new ArrayList<>();
        for(int i=0;i<ActInfoList.size();i++){
            String Date=ActInfoList.get(i).getYear()+"/"+ActInfoList.get(i).getMonth()+"/"+ActInfoList.get(i).getDate();
            if(!DateList.contains(Date)){
                DateList.add(Date);
            }
        }
        for(int i=0;i<DateList.size();i++){
            DayList.add(new ArrayList<>());
        }
        for(int i=0;i<DateList.size();i++){
            String[] splitdate=DateList.get(i).split("/");
            for(int j=0;j<ActInfoList.size();j++){
                if((ActInfoList.get(j).getYear()==Integer.parseInt(splitdate[0]))&&(ActInfoList.get(j).getMonth()==Integer.parseInt(splitdate[1]))&&(ActInfoList.get(j).getDate()==Integer.parseInt(splitdate[2]))){
                    DayList.get(i).add(new ActInfoItem(ActInfoList.get(j).getId(), ActInfoList.get(j).getCategory(), ActInfoList.get(j).getYear(), ActInfoList.get(j).getMonth(), ActInfoList.get(j).getDate(), ActInfoList.get(j).getStartHour(), ActInfoList.get(j).getStartMinute(), ActInfoList.get(j).getEndHour(), ActInfoList.get(j).getEndMinute()));
                }
            }
        }
        //한 카테고리 내 여러 시간 기록이 있을테니 arraylist에 저장
        //어레이리스트 속의 총합 시간 계산
        //총합 시간이 목표와 부합하는지 체크
        //부합하면 +점수 부합하지 않으면 -점수
        for(int i=0;i<LableListInt.size();i++){  //AffectingStat: 3 4 3 1 2 /LableListInt: 3 4 1 2
            TotalStatDataList.add(0f);
            Log.i("LableListInt",""+LableListInt.get(i));
        }

        for(int i=0;i<CategoryList.size();i++){
            Log.i("CategoryStat",""+AffectingStat.get(i)+" "+CategoryList.get(i));
        }

        int NumOfFail=0;

        for(int i=0;i<DayList.size();i++){  //DayList.get(i) 가 하나의 날짜를 나타냄 ex) 5/20의 모든 시간 기록 담고있음
            //한 날짜의 모든 시간 기록을 카테고리별로 분류  ex) 수면, 식사, 게임, 운동.....
            for(int n=0;n<CategoryList.size();n++){
                ByDateCategoryDataList.add(new ArrayList<>());//카테고리별로 기록 넣어둘 공간 할당
            }
            for(int j=0;j<DayList.get(i).size();j++){  //ex) DayList.get(i): 5/20 DayList.get(i).get(j): 5/20 9~11 Study
                int index=CategoryList.indexOf(DayList.get(i).get(j).Category);  //CategoryList(index) "식사(0)","취침(1)","공부(2)","운동(3)","게임(4)"
                if(index>=0){
                    ByDateCategoryDataList.get(index).add(PetStateManage.calTimeValue(DayList.get(i).get(j).Category,DayList.get(i).get(j).StartHour,DayList.get(i).get(j).StartMinute,DayList.get(i).get(j).EndHour,DayList.get(i).get(j).EndMinute));
                }
            }//이 for문에서는 하루 내의 기록들을 다룬다. 따라서 이 반복문이 끝나면 하루에 대한 데이터가 모두 카테고리별로 정리


            for(int k=0;k<CategoryList.size();k++){
                TotalCategoryDataList.add(PetStateManage.calCategoryData(ByDateCategoryDataList.get(k)));
                TotalCategoryDataList.set(k,PetStateManage.applyGoal(TotalCategoryDataList.get(k),CategoryList.get(k), GoalType.get(k),(DateList.get(i).compareTo(today)==0)));

                if(DateList.get(i).compareTo(today)==0){
                    NumOfFail=PetStateManage.subtractInteractionNum(TotalCategoryDataList);
                }

                //하루 기록들에서 카테고리 데이터의 총합을 계산해 목표 달성 여부에 따른 증감 값을 반영하여 설정
            }
            for(int l=0;l<LableListInt.size();l++){
                for(int m=0;m< AffectingStat.size();m++){
                    if(LableListInt.get(l)==AffectingStat.get(m)){
//                        if(!(TotalStatDataList.get(l)<=0&&TotalCategoryDataList.get(m)<0)||!(TotalStatDataList.get(l)>=100&&TotalCategoryDataList.get(m)>0)){
//                            TotalStatDataList.set(l,TotalStatDataList.get(l)+TotalCategoryDataList.get(m));
//                        }
                        if(TotalStatDataList.get(l)==0&&TotalCategoryDataList.get(m)<0){
                            TotalStatDataList.set(l,0f);
                        }else if(TotalStatDataList.get(l)==100&&TotalCategoryDataList.get(m)>0){
                            TotalStatDataList.set(l,100f);
                        }else{
                            TotalStatDataList.set(l,TotalStatDataList.get(l)+TotalCategoryDataList.get(m));
                        }
                    }
                }
            }//이 부분 다시
            ByDateCategoryDataList.clear();
            TotalCategoryDataList.clear();

        }//이 for문 내부에서는 i값 유지(계속 같은 날짜)

        for(int i=0;i<LableListInt.size();i++){
            if(!TotalStatDataList.isEmpty()){
                visitors.add(new RadarEntry(TotalStatDataList.get(i)));
            }else{
                visitors.add(new RadarEntry(0f));
            }
        }

        if(today.compareTo(PreferenceManage.getString(MainContext,"Date"))!=0){
            AffectionEntry=(PSY.InteractionNum-NumOfFail)+PreferenceManage.getInt(MainContext,"AffectionNum");
        }else{
            if(!NotEnd)
            AffectionEntry=PreferenceManage.getInt(MainContext,"InteractionNum")+PSY.InteractionNum-NumOfFail;
        }
        NotEnd=false;

        if(AffectionEntry<0){
            visitors.add(new RadarEntry(0f));
            PSY.InteractionNum+=NumOfFail-PSY.InteractionNum;
        }else if(AffectionEntry>=20){
            visitors.add(new RadarEntry(100));
        }else{
            visitors.add(new RadarEntry(AffectionEntry*5));
        }
        Log.i("InteractionNum","InteractionNum: "+PSY.InteractionNum+" FailNum: "+NumOfFail+" AffectionEntry: "+AffectionEntry);

        RadarDataSet set1=new RadarDataSet(visitors,"펫 상태");
        set1.setColor(Color.BLUE);  //선 색깔 변경
        set1.setDrawFilled(true);
        set1.setFillColor(Color.BLUE);  //내부 색깔 변경
        set1.setLineWidth(0.5f);
        set1.setValueTextSize(3f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);

        RadarData data=new RadarData();
        data.addDataSet(set1);

        PetStateChart.setData(data);
        PetStateChart.invalidate();
    }


    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManage.setInt(MainContext,"InteractionNum",PSY.InteractionNum);
        PreferenceManage.setInt(MainContext,"AffectionNum",AffectionEntry);
        PreferenceManage.setString(MainContext,"Date",today);
    }

    //-------------------------------------------------------------------->PSY
    //-------------------------------------------------------------------->PSY
}
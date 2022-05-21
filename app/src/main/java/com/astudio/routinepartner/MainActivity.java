package com.astudio.routinepartner;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntegerRes;
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
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    AlertDialog Dialog;
    NumberPicker[] NumPickers = new NumberPicker[10];
    Button Btn1, Btn2, Btn3, Btn4, Btn5, BtnOK, BtnCancel, BtnAddTestData, BtnDeleteAll, BtnShowPieChart;
    ImageButton BtnShowList;
    EditText EdtCategory;
    TextView TxtTime;
    int CategoryNum;

    PieChartView PieChart;
    ArrayList<Integer> TimeList = new ArrayList<Integer>();
    ArrayList<Float> AngleList = new ArrayList<Float>();
    ArrayList<Float> YesterDayAngleList = new ArrayList<>();


    protected ArrayList<ActInfo> ActInfoList;
    protected ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();
    protected ArrayList<ActInfoItem> ActInfoYesterdayItemList = new ArrayList<ActInfoItem>();


    LottieAnimationView PetView;
    TextView PetName;
    String Action ="";
    int ActionInt;
    Context MainContext;
    String text_PetName="";
    RadarChart PetStateChart;
    ArrayList<String> LableList=new ArrayList<>();
    ArrayList<Integer> LableListInt=new ArrayList<>();


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //<--------------------------------------------------------------------YJS
        //<--------------------------------------------------------------------YJS
        SettingsDB.DatabaseWriteExecutor.execute(() -> {
            SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
            SettingsDAO mSettingsDao = db.settingDao();
            int CategoryNum = mSettingsDao.getAll().length;
            for(int i = 0; i < CategoryNum; i++) {

            }
            SavedSettings.CategoryList.set(0, mSettingsDao.getAll()[0].getCategory());
        });
        /*GradientDrawable RS1 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square1);
        GradientDrawable RS2 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square2);
        GradientDrawable RS3 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square3);
        GradientDrawable RS4 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square4);
        GradientDrawable RS5 = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.round_square5);
        RS1.setColor(SavedSettings.ColorList.get(0).intValue());
        RS2.setColor(SavedSettings.ColorList.get(1).intValue());
        RS3.setColor(SavedSettings.ColorList.get(2).intValue());
        RS4.setColor(SavedSettings.ColorList.get(3).intValue());
        RS5.setColor(SavedSettings.ColorList.get(4).intValue());*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        CategoryNum = SavedSettings.CategoryList.size();
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
        BtnOK = dialogView.findViewById(R.id.btnOK);
        BtnCancel = dialogView.findViewById(R.id.btnCancel);
        YJS.numPickerSetting(dialogView, NumPickers);

        builder.setView(dialogView);
        Dialog = builder.create();


        for(int i = 0; i < CategoryNum; i++){
            if(i == 0) {
                Btn1.setVisibility(View.VISIBLE);
                Btn1.setText(SavedSettings.CategoryList.get(0));
                Btn1.setBackgroundResource(R.drawable.round_square1);
                Btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(0));
                    }
                });
            }
            if(i == 1) {
                Btn2.setVisibility(View.VISIBLE);
                Btn2.setText(SavedSettings.CategoryList.get(1));
                Btn2.setBackgroundResource(R.drawable.round_square2);
                Btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(1));
                    }
                });
            }
            if(i == 2) {
                Btn3.setVisibility(View.VISIBLE);
                Btn3.setText(SavedSettings.CategoryList.get(2));
                Btn3.setBackgroundResource(R.drawable.round_square3);
                Btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(2));
                    }
                });
            }
            if(i == 3) {
                Btn4.setVisibility(View.VISIBLE);
                Btn4.setText(SavedSettings.CategoryList.get(3));
                Btn4.setBackgroundResource(R.drawable.round_square4);
                Btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(3));
                    }
                });
            }
            if(i == 4) {
                Btn5.setVisibility(View.VISIBLE);
                Btn5.setText(SavedSettings.CategoryList.get(4));
                Btn5.setBackgroundResource(R.drawable.round_square5);
                Btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog();
                        EdtCategory.setText(SavedSettings.CategoryList.get(4));
                    }
                });
            }
        }


        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeData();
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
                ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                    ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                    ActInfoDAO mActInfoDao = db.actInfoDao();
                    mActInfoDao.deleteAll();
                });
                SettingsDB.DatabaseWriteExecutor.execute(() -> {
                    SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                    SettingsDAO mSettingsDao = db.settingDao();
                    mSettingsDao.deleteAll();
                });
                Toast.makeText(getApplicationContext(), "All Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        //-------------------------------------------------------------------->YJS
        //-------------------------------------------------------------------->YJS

        //<--------------------------------------------------------------------LSY
        //<--------------------------------------------------------------------LSY


        BtnShowPieChart = findViewById(R.id.btnShowPieChart);
        ImageButton BtnChart = (ImageButton) findViewById(R.id.BtnChart);
        ImageButton SettingButton = findViewById(R.id.SettingBtn);

        PieChart = (PieChartView) findViewById(R.id.PieChartView);
        BtnShowPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeToAngle();
                timeToAngleYesterday();
                Toast.makeText(getApplicationContext(), "" + AngleList.size(), Toast.LENGTH_SHORT).show();
                sendDataToPieChart();
                PieChart.update();
            }
        });

        BtnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BarIntent = new Intent(getApplicationContext(), BarChartActivity.class);
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


        //앱이 켜질때 저장되어있던 카테고리 값 셋팅

        SetCategoryAdapter.CategoryItem.clear();
        for(int i = 0; i < SavedSettings.CategoryList.size(); i++){
            SetCategoryAdapter.CategoryItem.add(new CategoryInfo(SavedSettings.CategoryList.get(i), SavedSettings.ColorList.get(i),
                    SavedSettings.AffectingStat.get(i), SavedSettings.GoalType.get(i), SavedSettings.Goal.get(i)));
        }



        //-------------------------------------------------------------------->LSY
        //-------------------------------------------------------------------->LSY

        //<--------------------------------------------------------------------PSY
        //<--------------------------------------------------------------------PSY


        MainContext=this;
        text_PetName=PreferenceManage.getString(MainContext,"rebuild");
        if(text_PetName.isEmpty()){
            text_PetName="";
        }
        PetName=(TextView) findViewById(R.id.PetName);
        PetName.setText(text_PetName);

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
                    case "4":
                        //img.setImageResource(R.drawable.foodbowl);
                        //FadeAnimation.fadeOutImage(img);
                        break;
                    case "3":
                        img.setImageResource(R.drawable.dogbed_brown);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "1":
                        img.setImageResource(R.drawable.book);
                        FadeAnimation.fadeOutImage(img);
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
                    ImageView HeartImage=findViewById(R.id.HeartImage);
                    HeartImage.setImageResource(R.drawable.petheart);
                    FadeAnimation.fadeInImage(HeartImage);
                    PetView.setRepeatCount(1);
                    PetView.playAnimation();
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


        PetStateChart=findViewById(R.id.RadarChart);

        /*for(String stat:CategoryStat){
            if(!LableList.contains(stat))
                LableList.add(stat);
        }*/

        for(int stat:AffectingStat){
            if(!LableListInt.contains(stat))
                LableListInt.add(stat);  //4 2 1
        }

        String[] labels=new String[LableListInt.size()];
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

        setRadarDataFirst();

        /*String[] labels=new String[LableList.size()];
        int size=0;
        for(String item:LableList){
            labels[size]=item;
            size++;
        }*/

        XAxis xAxis=PetStateChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextColor(0xFFBDBDBD);
        xAxis.setTextSize(8f);
        YAxis yAxis=PetStateChart.getYAxis();
        yAxis.setDrawLabels(false);
        yAxis.setLabelCount(5,false);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(100);
        yAxis.setDrawLabels(false);
        Legend legend=PetStateChart.getLegend();
        legend.setTextColor(0xFFBDBDBD);

        PetStateChart.getDescription().setEnabled(false);

        for(IDataSet<?> set:PetStateChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        PetStateChart.invalidate();


        ((ImageView)findViewById(R.id.Restart)).setOnClickListener(view -> {
            setRadarData();
        });

        //-------------------------------------------------------------------->PSY
        //-------------------------------------------------------------------->PSY
    }

    //<--------------------------------------------------------------------YJS
    //<--------------------------------------------------------------------YJS
    public void showList() {
        Intent intent = new Intent(this, ActInfoListActivity.class);
        startActivity(intent);
    }

    public void makeData() {
        Calendar cal = Calendar.getInstance();
        String CategoryName;
        int Year, Month, Date, SAMPM, Shour, Sminute, EAMPM, Ehour, Eminute;
        CategoryName = EdtCategory.getText().toString();
        Year = cal.get(Calendar.YEAR);
        Month = cal.get(Calendar.MONTH) + 1;
        Date = cal.get(Calendar.DATE);
        SAMPM = NumPickers[0].getValue();
        Shour = NumPickers[2].getValue();
        Sminute = NumPickers[4].getValue();
        EAMPM = NumPickers[1].getValue();
        Ehour = NumPickers[3].getValue();
        Eminute = NumPickers[5].getValue();
//        TxtTime.setText(CategoryName + "\n" + Year + "-" + Month + "-" + Date + "  " + SAMPM + " " + Shour + ":" + Sminute + " ~ " + EAMPM + " " + Ehour + ":" + Eminute);

        addToActDB(CategoryName, Year, Month, Date, SAMPM == 0 ? Shour : Shour + 12, Sminute, EAMPM == 0 ? Ehour : Ehour + 12, Eminute);


        ArrayList<Integer> AffectingStat=SavedSettings.AffectingStat;
        //Action=CategoryName;  //[PSY] 추가코드
        ActionInt=CategoryList.indexOf(CategoryName);  //취침은 CategoryList에서 0번째 위치->0번째 위치한 AffectingStat 이 무엇인지
        if(ActionInt>=0){
          int AffectingStatIndex=AffectingStat.indexOf(ActionInt);
          switch(AffectingStatIndex){  //"지능", "재미", "체력", "포만감", "잔고", "자아실현"
              case 1: Action="1"; break;
              case 2: Action="2"; break;
              case 3: Action="3"; break;
              case 4: Action="4"; break;
              case 5: Action="5"; break;
              case 6: Action="6"; break;
          }
        }
        //setRadarData();       //[PSY] 추가코드

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
        for(int i = 1; i < 31; i++) {
            addToActDB("취침", 2022, 4, i, 0, 0, 6, 0);
            addToActDB("식사", 2022, 4, i, 8, 0, 9, 0);
            addToActDB("공부", 2022, 4, i, 10, 0, 12, 0);
            addToActDB("식사", 2022, 4, i, 13, 0, 14, 0);
            addToActDB("운동", 2022, 4, i, 16 , 0, 18, 0);
            addToActDB("식사", 2022, 4, i, 18, 0, 19, 0);
            addToActDB("게임", 2022, 4, i, 20, 0, 22, 0);
            addToActDB("취침", 2022, 4, i, 22, 0, 24, 0);
        }
        for(int i = 1; i < 32; i++) {
            addToActDB("취침", 2022, 5, i, 0, 0, 6, 0);
            addToActDB("식사", 2022, 5, i, 8, 0, 9, 0);
            addToActDB("공부", 2022, 5, i, 10, 0, 12, 0);
            addToActDB("식사", 2022, 5, i, 13, 0, 14, 0);
            addToActDB("운동", 2022, 5, i, 16 , 0, 18, 0);
            addToActDB("식사", 2022, 5, i, 18, 0, 19, 0);
            addToActDB("게임", 2022, 5, i, 20, 0, 22, 0);
            addToActDB("취침", 2022, 5, i, 22, 0, 24, 0);
        }
        for(int i = 0; i < SavedSettings.CategoryList.size(); i++) {
            addToSettingsDB(SavedSettings.CategoryList.get(i), SavedSettings.ColorList.get(i), SavedSettings.GoalType.get(i), SavedSettings.Goal.get(i), SavedSettings.AffectingStat.get(i), SavedSettings.Order.get(i));
            Log.i("11111", "--" + SavedSettings.CategoryList.get(i)+ "  -  " + SavedSettings.ColorList.get(i)+ " " + SavedSettings.GoalType.get(i)+ SavedSettings.Goal.get(i)+ SavedSettings.AffectingStat.get(i)+ SavedSettings.Order.get(i));
        }
    }

    public void addToActDB(String Category, int Year, int Month, int Date, int Shour, int Sminute, int Ehour, int Eminute) {
        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
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
        });
    }

    public void addToSettingsDB(String Category, long Color, int GoalType, int Goal, int AffectingStat, int Order) {

        SettingsDB.DatabaseWriteExecutor.execute(() -> {
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
        });

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

    public void timeToAngle(){
        Calendar cal = Calendar.getInstance();
        CountDownLatch CDL = new CountDownLatch(1);

        AngleList.clear();
        PieCategoryList.clear();
        ActInfoItemList.clear();
        AngleList.clear();
        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
            ActInfoDAO mActInfoDao = db.actInfoDao();
            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE))));
            CDL.countDown();
        });
        try {
            CDL.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < ActInfoList.size(); i++) {
            ActInfoItemList.add(new ActInfoItem(ActInfoList.get(i).getId(), ActInfoList.get(i).getCategory(), ActInfoList.get(i).getYear(), ActInfoList.get(i).getMonth(), ActInfoList.get(i).getDate(), ActInfoList.get(i).getStartHour(), ActInfoList.get(i).getStartMinute(), ActInfoList.get(i).getEndHour(), ActInfoList.get(i).getEndMinute()));
        }
        Collections.sort(ActInfoItemList, new Comparator<ActInfoItem>(){
            public int compare(ActInfoItem o1, ActInfoItem o2) {
                if(o1.StartHour == o2.StartHour) {
                    if(o1.StartMinute == o2.StartMinute) return 0;
                    return o1.StartMinute < o2.StartMinute ? -1 : 1;
                }
                return o1.StartHour < o2.StartHour ? -1 : 1;
            }
        });
        for(int i = 0; i < ActInfoItemList.size(); i++) {
            AngleList.add(ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f + 270);
            AngleList.add(ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f);
            PieCategoryList.add(ActInfoItemList.get(i).Category);
            Log.i("" + (ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f), "" + (ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f));
            Log.v("카테고리", ""+ActInfoItemList.get(i).Category);
        }


    }

    public void timeToAngleYesterday(){
        int BeforeTime, AfterTime;
        float StartAngle, DrawAngle;

        YesterDayAngleList.clear();

        Calendar cal = Calendar.getInstance();
        CountDownLatch CDL = new CountDownLatch(1);
        ActInfoYesterdayItemList.clear();
        YesterDayAngleList.clear();
        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
            ActInfoDAO mActInfoDao = db.actInfoDao();
            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)-1, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)-1)));
            CDL.countDown();
        });
        try {
            CDL.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        if((YdSize > 0) && (ActInfoList.get(YdSize).getCategory().equals(PieCategoryList.get(0))) && (ActInfoList.get(YdSize).getEndHour() == 24)
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

    //-------------------------------------------------------------------->LSY
    //-------------------------------------------------------------------->LSY


    //<--------------------------------------------------------------------PSY
    //<--------------------------------------------------------------------PSY
    //ArrayList<String> CategoryList=new ArrayList<>(Arrays.asList("식사","취침","공부","운동"));
    ArrayList<String> CategoryList=SavedSettings.CategoryList;
    ArrayList<String> CategoryStat =SavedSettings.StatList;
    //ArrayList<String> CategoryStat = new ArrayList<>(Arrays.asList("포만감", "체력", "지능", "체력"));
    ArrayList<Integer> CategoryStatInt=SavedSettings.AffectingStat;
    ArrayList<Integer> Order=new ArrayList<>(Arrays.asList(1,2,3,4));

    public void setRadarDataFirst(){

        ArrayList<RadarEntry> visitors=new ArrayList<>();
        ArrayList<Float> GoalSuccessCheck=new ArrayList<>();
        PSY BeforeDataManage=new PSY();
        ArrayList<Float> TestArray=new ArrayList<>();

        for(int i=0;i<CategoryList.size();i++){
            float GoalSuccessCheckValue=PreferenceManage.getFloat(MainContext,"CategoryValue"+i);
            GoalSuccessCheck.add(GoalSuccessCheckValue);
            //visitors.add(new RadarEntry(PreferenceManage.getFloat(MainContext,"Value"+i)));
        }

        for(int i=0;i<GoalSuccessCheck.size();i++){
            TestArray.add(0f);
        }

        /*for(int i=0;i<CategoryList.size();i++){

            if(BeforeDataManage.isGoalAchieved(GoalSuccessCheck).get(i)){
                GoalSuccessCheck.set(i, GoalSuccessCheck.get(i)+10);
            }else{
                GoalSuccessCheck.set(i, GoalSuccessCheck.get(i)*0.5f);
            }
        }

        for(int i=0;i<LableList.size();i++){
            ArrayList<Float> RadarStatDataList=new ArrayList<>();
            for(int j=0;j<CategoryStat.size();j++){
                int index=LableList.indexOf(CategoryStat.get(j));
                if(index>=0){
                    RadarStatDataList.add(i, GoalSuccessCheck.get(j));
                }
            }
            visitors.add(new RadarEntry(RadarStatDataList.get(i)));
        }*/

        /*if(!GoalSuccessCheck.containsAll(TestArray)){
            for(int i=0;i<CategoryList.size();i++){

                if(BeforeDataManage.isGoalAchieved(GoalSuccessCheck).get(i)){
                    GoalSuccessCheck.set(i, GoalSuccessCheck.get(i)+10);
                }else{
                    GoalSuccessCheck.set(i, GoalSuccessCheck.get(i)*0.5f);
                }
            }

            for(int i=0;i<LableList.size();i++){
                ArrayList<Float> RadarStatDataList=new ArrayList<>();
                for(int j=0;j<CategoryStat.size();j++){
                    int index=LableList.indexOf(CategoryStat.get(j));
                    if(index>=0){
                        RadarStatDataList.add(i, GoalSuccessCheck.get(j));
                    }
                }
                visitors.add(new RadarEntry(RadarStatDataList.get(i)));
            }
        }else{
            for(int i=0;i<LableList.size();i++){
                visitors.add(new RadarEntry(0));
            }
        }*/

        for(int i=0;i<LableList.size();i++){
            visitors.add(new RadarEntry(0));
        }

        RadarDataSet set1=new RadarDataSet(visitors,"펫 상태");
        set1.setColor(Color.BLUE);
        set1.setLineWidth(0.5f);
        set1.setValueTextSize(3f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);

        RadarData data=new RadarData();
        data.addDataSet(set1);

        PetStateChart.setData(data);
        PetStateChart.invalidate();
    }

    public void setRadarData(){
        ArrayList<RadarEntry> visitors=new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        ArrayList<ArrayList<Float>> RadarCategoryDataList=new ArrayList<>(); //각 카테고리별로 총합 시간(횟수) 데이터 저장
        ArrayList<Float> TotalCategoryDataList=new ArrayList<>();
        ArrayList<Float> TotalStatDataList=new ArrayList<>();

        /*for(int i=0;i<CategoryList.size();i++){
            RadarCategoryDataList.set(i,new ArrayList<>(Arrays.asList(0f)));
        }*/
        for(int i=0;i<CategoryList.size();i++){
            RadarCategoryDataList.add(new ArrayList<>());
        }

        PSY PetStateManage=new PSY();

        CountDownLatch CDL = new CountDownLatch(1);
        ActInfoItemList.clear();
        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
            ActInfoDAO mActInfoDao = db.actInfoDao();
            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE))));
            CDL.countDown();
        });
        try {
            CDL.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < ActInfoList.size(); i++) {
            ActInfoItemList.add(new ActInfoItem(ActInfoList.get(i).getId(), ActInfoList.get(i).getCategory(), ActInfoList.get(i).getYear(), ActInfoList.get(i).getMonth(), ActInfoList.get(i).getDate(), ActInfoList.get(i).getStartHour(), ActInfoList.get(i).getStartMinute(), ActInfoList.get(i).getEndHour(), ActInfoList.get(i).getEndMinute()));
        }

        for(int i = 0; i < ActInfoItemList.size(); i++) {
            float TimeData=PetStateManage.calTimeValue(ActInfoItemList.get(i).Category,ActInfoItemList.get(i).StartHour,ActInfoItemList.get(i).StartMinute,ActInfoItemList.get(i).EndHour,ActInfoItemList.get(i).EndMinute);
            int index=CategoryList.indexOf(ActInfoItemList.get(i).Category);
            /*for(int j=0;i<CategoryList.size();j++){
                if(ActInfoItemList.get(i).Category==CategoryList.get(j)){
                    index=j;
                }
            }*/
            if(index>=0){
                RadarCategoryDataList.get(index).add(TimeData);
            }else{
                for(int k=0;k<CategoryList.size();k++){
                    RadarCategoryDataList.get(k).add(0f);
                }
            }
        }

        /* ArrayList<String> CategoryList = new ArrayList<>(Arrays.asList("식사", "수면", "공부", "운동"));
        ArrayList<Integer> GoalList = new ArrayList<>(Arrays.asList(2, 7, 4, 1));
        ArrayList<Integer> GoalType = new ArrayList<>(Arrays.asList(2, 1, 1, 1));
        ArrayList<String> StatList = new ArrayList<>(Arrays.asList("지능", "재미", "체력", "포만감", "잔고", "자아실현"));
        ArrayList<String> CategoryStat = new ArrayList<>(Arrays.asList("포만감", "체력", "지능", "체력"));
        lables={"포만감", "체력", "지능"}*/

        if(!TotalCategoryDataList.isEmpty()){
            TotalCategoryDataList.clear();
        }
        if(!TotalStatDataList.isEmpty()){
            TotalStatDataList.clear();
        }
        for(int i=0;i<LableList.size();i++){
            for(int j=0;j<CategoryList.size();j++){
                float value=PetStateManage.calCategoryData(RadarCategoryDataList.get(j));
                TotalCategoryDataList.add(value);  //각 카테고리별로 시간이 들어감
                PreferenceManage.setFloat(MainContext,"CategoryValue"+j,TotalCategoryDataList.get(j));
                int index=LableList.indexOf(CategoryStat.get(j));
                if(index>=0){
                    TotalStatDataList.add(index,value);
                }
                /*if(LableList.get(i)==CategoryStat.get(j)){
                    TotalStatDataList.add(i,value);
                }*/
            }

            float VisitorsData=TotalStatDataList.get(i);
            if(VisitorsData>=6){
                VisitorsData=VisitorsData/3*20;
            }else{
                VisitorsData=VisitorsData/2*20;
            }
            visitors.add(new RadarEntry(VisitorsData));
            PreferenceManage.setFloat(MainContext,"Value"+i,VisitorsData);
        }

        RadarCategoryDataList.clear();

        /*for(int i=0;i<CategoryList.size();i++){
            float value=PetStateManage.calCategoryData(RadarCategoryDataList.get(i));

            TotalCategoryDataList.add(value);//카테고리는 4개지만 스텟이 3개  스탯이 같으면 같은 곳에
            visitors.add(new RadarEntry(value));
            PreferenceManage.setFloat(MainContext,"Value"+i,value);
            RadarCategoryDataList.get(i).clear();
        }*/

        RadarDataSet set1=new RadarDataSet(visitors,"펫 상태");
        set1.setColor(Color.BLUE);
        set1.setLineWidth(0.5f);
        set1.setValueTextSize(3f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);


        RadarData data=new RadarData();
        data.addDataSet(set1);

        PetStateChart.setData(data);
        PetStateChart.invalidate();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.setup_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.NameSet:{
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("펫 이름 설정");
                builder.setMessage("이름을 입력하시오.");
                final EditText editText=new EditText(MainActivity.this);
                builder.setView(editText);

                builder.setPositiveButton("입력", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        String name;
                        name=editText.getText().toString();
                        PetName.setText(name);
                        PreferenceManage.setString(MainContext, "rebuild", name);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show();
            } break;

            default:
        }
        return true;
    }



    //-------------------------------------------------------------------->PSY
    //-------------------------------------------------------------------->PSY
}
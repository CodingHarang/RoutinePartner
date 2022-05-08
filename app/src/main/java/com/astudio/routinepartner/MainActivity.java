package com.astudio.routinepartner;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.RadarChart;
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
    Button BtnEat, BtnStudy, BtnSleep, BtnEtc, BtnOK, BtnShowList, BtnAddTestData, BtnDeleteAll, BtnShowPieChart;
    EditText EdtCategory;
    TextView TxtTime;

    PieChartView PieChart;
    ArrayList<Integer> TimeList = new ArrayList<Integer>();
    ArrayList<Float> AngleList = new ArrayList<Float>();
    ArrayList<Float> YesterDayAngleList = new ArrayList<>();


    protected ArrayList<ActInfo> ActInfoList;
    protected ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();
    protected ArrayList<ActInfoItem> ActInfoYesterdayItemList = new ArrayList<ActInfoItem>();


    LottieAnimationView PetView;
    TextView PetName;
    String Action ="meal"; //ActInfoItem 에서 가져옴
    Context MainContext;
    String text_PetName="";
    RadarChart PetStateChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //<--------------------------------------------------------------------YJS
        //<--------------------------------------------------------------------YJS
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TxtTime = findViewById(R.id.txtTime);
        BtnEat = findViewById(R.id.btnEat);
        BtnStudy = findViewById(R.id.btnStudy);
        BtnSleep = findViewById(R.id.btnSleep);
        BtnEtc = findViewById(R.id.btnEtc);
        BtnShowList = findViewById(R.id.btnShowList);
        BtnAddTestData = findViewById(R.id.btnAddTestData);
        BtnDeleteAll = findViewById(R.id.btnDeleteAll);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add, null);

        EdtCategory = dialogView.findViewById(R.id.edtCategory);
        BtnOK = dialogView.findViewById(R.id.btnOK);
        YJS.numPickerSetting(dialogView, NumPickers);

        builder.setView(dialogView);
        Dialog = builder.create();

        BtnEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                EdtCategory.setText("Eat");
            }
        });
        BtnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                EdtCategory.setText("Study");
            }
        });
        BtnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                EdtCategory.setText("Sleep");
            }
        });
        BtnEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
                EdtCategory.setText("Edit Here");
            }
        });

        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeData();
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
                Toast.makeText(getApplicationContext(), "All Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        //-------------------------------------------------------------------->YJS
        //-------------------------------------------------------------------->YJS

        //<--------------------------------------------------------------------LSY
        //<--------------------------------------------------------------------LSY


        BtnShowPieChart = findViewById(R.id.btnShowPieChart);
        ImageButton BtnChart = (ImageButton) findViewById(R.id.BtnChart);

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
                    case "Eat":
                        img.setImageResource(R.drawable.foodbowl);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "Sleep":
                        img.setImageResource(R.drawable.bed);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "Study":
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
                    HeartImage.setImageResource(R.drawable.heart);
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


        PetStateChart=findViewById(R.id.RadarChart);
        setRadarData();

        String[] lables={"체력","포만감","지능","재미","사교"};

        XAxis xAxis=PetStateChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(lables));
        xAxis.setTextSize(4f);
        YAxis yAxis=PetStateChart.getYAxis();
        yAxis.setDrawLabels(false);
        yAxis.setLabelCount(5,false);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(50);
        yAxis.setDrawLabels(false);

        for(IDataSet<?> set:PetStateChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        PetStateChart.invalidate();

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
        TxtTime.setText(CategoryName + "\n" + Year + "-" + Month + "-" + Date + "  " + SAMPM + " " + Shour + ":" + Sminute + " ~ " + EAMPM + " " + Ehour + ":" + Eminute);

        addToDB(CategoryName, Year, Month, Date, SAMPM == 0 ? Shour : Shour + 12, Sminute, EAMPM == 0 ? Ehour : Ehour + 12, Eminute);
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
            addToDB("Sleep", 2022, 4, i, 0, 0, 7, 0);
            addToDB("Etc", 2022, 4, i, 8, 0, 9, 0);
            addToDB("Study", 2022, 4, i, 9, 0, 13, 0);
            addToDB("Eat", 2022, 4, i, 13, 0, 17, 0);
            addToDB("Etc", 2022, 4, i, 17 , 0, 18, 0);
            addToDB("Study", 2022, 4, i, 18, 0, 21, 0);
            addToDB("Eat", 2022, 4, i, 21, 0, 22, 0);
            addToDB("Sleep", 2022, 4, i, 22, 0, 24, 0);
        }
        for(int i = 1; i < 32; i++) {
            addToDB("Sleep", 2022, 5, i, 0, 0, 7, 0);
            addToDB("Etc", 2022, 5, i, 8, 0, 9, 0);
            addToDB("Study", 2022, 5, i, 9, 0, 13, 0);
            addToDB("Eat", 2022, 5, i, 13, 0, 17, 0);
            addToDB("Etc", 2022, 5, i, 17 , 0, 18, 0);
            addToDB("Study", 2022, 5, i, 18, 0, 21, 0);
            addToDB("Eat", 2022, 5, i, 21, 0, 22, 0);
            addToDB("Sleep", 2022, 5, i, 22, 0, 24, 0);
        }
    }

    public void addToDB(String Category, int Year, int Month, int Date, int Shour, int Sminute, int Ehour, int Eminute) {
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
            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE-1), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE-1))));
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



    /*public void PieChartAutoReset(){ //0시마다 원형시간표 리셋
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
        String SetResetTime = Format.format(new Date(ResetCal.getTimeInMillis()+ AlarmManager.INTERVAL_DAY));
    }*/

    /*public class AlarmRecever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            AngleList.clear();
        }
    }*/
    //-------------------------------------------------------------------->LSY
    //-------------------------------------------------------------------->LSY


    //<--------------------------------------------------------------------PSY
    //<--------------------------------------------------------------------PSY

    public void setRadarData(){
        ArrayList<RadarEntry> visitors=new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        float val1=0,val2=0,val3=0,val4=0,val5=0;
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
            PetStateManage.calRadarValue(ActInfoItemList.get(i).Category,ActInfoItemList.get(i).StartHour,ActInfoItemList.get(i).StartMinute,ActInfoItemList.get(i).EndHour,ActInfoItemList.get(i).EndMinute);
        }

        val1=PetStateManage.calCategoryData("Sleep");
        val2=PetStateManage.calCategoryData("Eat");
        val3=PetStateManage.calCategoryData("Study");

        visitors.add(new RadarEntry(val1));
        visitors.add(new RadarEntry(val2));
        visitors.add(new RadarEntry(val3));
        visitors.add(new RadarEntry(val4));
        visitors.add(new RadarEntry(val5));

        RadarDataSet set1=new RadarDataSet(visitors,"펫 상태");
        set1.setColor(Color.BLUE);
        set1.setLineWidth(0.5f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);


        RadarData data=new RadarData();
        data.addDataSet(set1);
        data.setValueTextSize(3f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.BLACK);

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
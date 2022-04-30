package com.astudio.routinepartner;

import android.animation.Animator;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import android.app.PendingIntent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    AlertDialog Dialog;
    NumberPicker[] NumPickers = new NumberPicker[10];
    Button BtnEat, BtnStudy, BtnSleep, BtnEtc, BtnOK, BtnShowList, BtnAddTestData, BtnDeleteAll, BtnGetAll, BtnShowPieChart;
    EditText EdtCategory;
    TextView TxtTime;

    PieChartView PieChart;
    ArrayList<String> TimeData = new ArrayList<String>(Arrays.asList("00/00/08/30", "08/30/09/10","10/00/14/00","14/00/16/50","17/00/21/30","21/30/00/00"));
    ArrayList<Integer> TimeList = new ArrayList<Integer>();
    ArrayList<Float> AngleList = new ArrayList<Float>();
    ArrayList<Integer> YesterDayTimeList = new ArrayList<>(Arrays.asList(22, 30, 0, 0));
    ArrayList<Float> YesterDayAngleList = new ArrayList<>();


    protected ArrayList<ActInfo> ActInfoList;
    protected ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();


    LottieAnimationView PetView;
    TextView PetName;
    String action="meal"; //ActInfoItem 에서 가져옴
    Context MainContext;
    String text_PetName="";


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
        BtnGetAll = findViewById(R.id.btnGetAll);

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
        BtnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActInfoItemList.clear();
                ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                    ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                    ActInfoDAO mActInfoDao = db.actInfoDao();
                    ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getAll()));
                    for(int i = 0; i < ActInfoList.size(); i++) {
                        ActInfoItemList.add(new ActInfoItem(ActInfoList.get(i).getId(), ActInfoList.get(i).getCategory(), ActInfoList.get(i).getYear(), ActInfoList.get(i).getMonth(), ActInfoList.get(i).getDate(), ActInfoList.get(i).getStartHour(), ActInfoList.get(i).getStartMinute(), ActInfoList.get(i).getEndHour(), ActInfoList.get(i).getEndMinute()));
                    }
                });
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
                switch(action){
                    case "meal":
                        img.setImageResource(R.drawable.foodbowl);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "sleep":
                        img.setImageResource(R.drawable.bed);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    case "study":
                        img.setImageResource(R.drawable.book);
                        FadeAnimation.fadeOutImage(img);
                        break;
                    default:
                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                PetView.setOnClickListener((view -> {
                    action="";
                    PetView.setRepeatCount(1);
                    PetView.playAnimation();
                }));

                PetView.setOnLongClickListener((view) -> {
                    action="interaction";
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
        try{
            PieChart.Data.addAll(AngleList); //타임 리스트에 있는 시간에 관한 데이터를 파이차트뷰의 데이터리스트로 넘김
            PieChart.CategoryList.addAll(PieCategoryList);
            //PieChart.YesterdayData.addAll(YesterDayAngleList);
            Log.v("데이터 넘김", "" + YesterDayAngleList);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    ArrayList<String> PieCategoryList = new ArrayList<>();

    //숫자로 받을 경우 바로 TimeLsit에 추가

    public void timeToAngle(){
        Calendar cal = Calendar.getInstance();
        CountDownLatch CDL = new CountDownLatch(1);
        ActInfoItemList.clear();
        AngleList.clear();
        ActInfoDB.DatabaseWriteExecutor.execute(() -> {
            ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
            ActInfoDAO mActInfoDao = db.actInfoDao();
            ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE))));
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
            AngleList.add(ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f + 270);
            AngleList.add(ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f);
            PieCategoryList.add(ActInfoItemList.get(i).Category);
            Log.i("" + (ActInfoItemList.get(i).StartHour * 15 + ActInfoItemList.get(i).StartMinute * 0.25f), "" + (ActInfoItemList.get(i).EndHour * 15 + ActInfoItemList.get(i).EndMinute * 0.25f - ActInfoItemList.get(i).StartHour * 15 - ActInfoItemList.get(i).StartMinute * 0.25f));
            Log.v("카테고리", ""+ActInfoItemList.get(i).Category);
        }
    }

    /*public void timeToAngleYesterday(ArrayList<Integer> arr){
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
    }*/



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
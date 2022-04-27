package com.astudio.routinepartner;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlertDialog Dialog;
    NumberPicker[] NumPickers = new NumberPicker[10];
    Button BtnEat, BtnStudy, BtnSleep, BtnEtc, BtnOK, BtnShowList, BtnAddTestData, BtnDeleteAll, BtnGetAll;
    EditText EdtCategory;
    TextView TxtTime;

    protected ArrayList<ActInfo> AllActInfoList;
    protected ArrayList<ActInfoItem> AllActInfoItemList = new ArrayList<ActInfoItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--------------------------------------------------------------------YJS
        //--------------------------------------------------------------------YJS
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
                AllActInfoItemList.clear();
                ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                    ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                    ActInfoDAO mActInfoDao = db.actInfoDao();
                    AllActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getAll()));
                    for(int i = 0; i < AllActInfoList.size(); i++) {
                        AllActInfoItemList.add(new ActInfoItem(AllActInfoList.get(i).getId(), AllActInfoList.get(i).getCategory(), AllActInfoList.get(i).getYear(), AllActInfoList.get(i).getMonth(), AllActInfoList.get(i).getDate(), AllActInfoList.get(i).getStartHour(), AllActInfoList.get(i).getStartMinute(), AllActInfoList.get(i).getEndHour(), AllActInfoList.get(i).getEndMinute()));
                    }
                });
            }
        });
        //--------------------------------------------------------------------YJS
        //--------------------------------------------------------------------YJS


    }











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
        for(int i = 1; i < 30; i++) {
            addToDB("Sleep", 2022, 4, i, 0, 0, 7, 0);
            addToDB("Etc", 2022, 4, i, 8, 0, 9, 0);
            addToDB("Study", 2022, 4, i, 9, 0, 13, 0);
            addToDB("Eat", 2022, 4, i, 13, 0, 17, 0);
            addToDB("Etc", 2022, 4, i, 17 , 0, 18, 0);
            addToDB("Study", 2022, 4, i, 18, 0, 21, 0);
            addToDB("Eat", 2022, 4, i, 21, 0, 22, 0);
            addToDB("Sleep", 2022, 4, i, 22, 0, 0, 0);
        }
        for(int i = 1; i < 31; i++) {
            addToDB("Sleep", 2022, 5, i, 0, 0, 7, 0);
            addToDB("Etc", 2022, 5, i, 8, 0, 9, 0);
            addToDB("Study", 2022, 5, i, 9, 0, 13, 0);
            addToDB("Eat", 2022, 5, i, 13, 0, 17, 0);
            addToDB("Etc", 2022, 5, i, 17 , 0, 18, 0);
            addToDB("Study", 2022, 5, i, 18, 0, 21, 0);
            addToDB("Eat", 2022, 5, i, 21, 0, 22, 0);
            addToDB("Sleep", 2022, 5, i, 22, 0, 0, 0);
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
}
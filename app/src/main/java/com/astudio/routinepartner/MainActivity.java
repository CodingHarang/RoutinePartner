package com.astudio.routinepartner;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlertDialog Dialog;
    NumberPicker[] NumPickers = new NumberPicker[10];
    Button BtnDialog, BtnAdd, BtnDeleteAll, BtnOK, BtnShowList;
    EditText EdtCategory;
    TextView TxtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TxtTime = findViewById(R.id.txtTime);
        BtnDialog = findViewById(R.id.btnDialog);
        BtnDeleteAll = findViewById(R.id.btnDeleteAll);
        BtnShowList = findViewById(R.id.btnShowList);


        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        NumPickers[0] = dialogView.findViewById(R.id.numPickerAMPMS);
        NumPickers[1] = dialogView.findViewById(R.id.numPickerAMPME);
        NumPickers[2] = dialogView.findViewById(R.id.numPickerHourS);
        NumPickers[3] = dialogView.findViewById(R.id.numPickerHourE);
        NumPickers[4] = dialogView.findViewById(R.id.numPickerMinuteS);
        NumPickers[5] = dialogView.findViewById(R.id.numPickerMinuteE);
        EdtCategory = dialogView.findViewById(R.id.edtCategory);
        BtnOK = dialogView.findViewById(R.id.btnOK);

        for(int i = 2; i < 4; i++) {
            NumPickers[i].setTextSize(40);
            NumPickers[i].setMaxValue(12);
            NumPickers[i].setMinValue(0);
            NumPickers[i].setBackgroundColor(0xFF000000);
            NumPickers[i].setTextColor(0xFFFFFFFF);
        }
        for(int i = 4; i < 6; i++) {
            NumPickers[i].setTextSize(40);
            NumPickers[i].setMaxValue(59);
            NumPickers[i].setMinValue(0);
            NumPickers[i].setBackgroundColor(0xFF000000);
            NumPickers[i].setTextColor(0xFFFFFFFF);
        }
        for(int i = 0; i < 2; i++) {
            NumPickers[i].setTextSize(30);
            NumPickers[i].setMaxValue(1);
            NumPickers[i].setMinValue(0);
            NumPickers[i].setBackgroundColor(0xFF000000);
            NumPickers[i].setTextColor(0xFFFFFFFF);
            NumPickers[i].setDisplayedValues(new String[] {"AM", "PM"});
        }

        builder.setView(dialogView);
        Dialog = builder.create();
        BtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.show();
                for(int i = 0; i < 6; i++) {
                    NumPickers[i].setValue(0);
                }
            }
        });

        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                String CategoryName, Year, Month, Date, SAMPM, Shour, Sminute, EAMPM, Ehour, Eminute;
                CategoryName = EdtCategory.getText().toString();
                Year = Integer.toString(cal.get(Calendar.YEAR));
                Month = Integer.toString(cal.get(Calendar.MONTH) + 1);
                Date = Integer.toString(cal.get(Calendar.DATE));
                SAMPM = NumPickers[0].getValue() == 0 ? "AM" : "PM";
                Shour = Integer.toString(NumPickers[2].getValue());
                Sminute = Integer.toString(NumPickers[4].getValue());
                EAMPM = NumPickers[1].getValue() == 0 ? "AM" : "PM";
                Ehour = Integer.toString(NumPickers[3].getValue());
                Eminute = Integer.toString(NumPickers[5].getValue());
                TxtTime.setText(CategoryName + "\n" + Year + "-" + Month + "-" + Date + "  " + SAMPM + " " + Shour + ":" + Sminute + " ~ " + EAMPM + " " + Ehour + ":" + Eminute);

                ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                    ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                    ActInfoDAO mActInfoDao = db.actInfoDao();
                    ActInfo actInfo = new ActInfo();
                    actInfo.setYear(Integer.parseInt(Year));
                    actInfo.setMonth(Integer.parseInt(Month));
                    actInfo.setDate(Integer.parseInt(Date));
                    actInfo.setCategory(CategoryName);
                    actInfo.setStartHour(SAMPM == "AM" ? Integer.parseInt(Shour) : Integer.parseInt(Shour) + 12);
                    actInfo.setStartMinute(Integer.parseInt(Sminute));
                    actInfo.setEndHour(EAMPM == "AM" ? Integer.parseInt(Ehour) : Integer.parseInt(Ehour) + 12);
                    actInfo.setEndMinute(Integer.parseInt(Eminute));
                    mActInfoDao.insert(actInfo);
                });
                Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();

                Dialog.dismiss();
            }
        });

        /*BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()) {
                    ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                        ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                        ActInfoDAO mActInfoDao = db.actInfoDao();
                        ActInfo actInfo = new ActInfo();
                        actInfo.setYear(Integer.parseInt(EdtYear.getText().toString()));
                        actInfo.setMonth(Integer.parseInt(EdtMonth.getText().toString()));
                        actInfo.setDate(Integer.parseInt(EdtDate.getText().toString()));
                        actInfo.setCategory(EdtCategory.getText().toString());
                        actInfo.setStartHour(Integer.parseInt(EdtStartHour.getText().toString()));
                        actInfo.setStartMinute(Integer.parseInt(EdtStartMinute.getText().toString()));
                        actInfo.setEndHour(Integer.parseInt(EdtEndHour.getText().toString()));
                        actInfo.setEndMinute(Integer.parseInt(EdtEndMinute.getText().toString()));
                        mActInfoDao.insert(actInfo);
                    });
                    Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fill Empty Please", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

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

        BtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });
    }

    public void showList() {
        Intent intent = new Intent(this, ActInfoListActivity.class);
        startActivity(intent);
    }

}
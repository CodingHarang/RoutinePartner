package com.example.routinepartner;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlertDialog Dialog;
    NumberPicker[] NumPickers = new NumberPicker[10];
    Button BtnDialog, BtnAdd, BtnDeleteAll, BtnOK, BtnShowList;
    EditText EdtYear, EdtMonth, EdtDate, EdtCategory, EdtStartHour, EdtStartMinute, EdtEndHour, EdtEndMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        BtnDialog = findViewById(R.id.btnDialog);
        BtnAdd = findViewById(R.id.btnAdd);
        BtnDeleteAll = findViewById(R.id.btnDeleteAll);
        BtnShowList = findViewById(R.id.btnShowList);


        EdtYear = (EditText) findViewById(R.id.edtYear);
        EdtMonth = (EditText) findViewById(R.id.edtMonth);
        EdtDate = (EditText) findViewById(R.id.edtDate);
        EdtCategory = (EditText) findViewById(R.id.edtCategory);
        EdtStartHour = (EditText) findViewById(R.id.edtStartHour);
        EdtStartMinute = (EditText) findViewById(R.id.edtStartMinute);
        EdtEndHour = (EditText) findViewById(R.id.edtEndHour);
        EdtEndMinute = (EditText) findViewById(R.id.edtEndMinute);


        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        NumPickers[0] = dialogView.findViewById(R.id.numPicker1);
        NumPickers[1] = dialogView.findViewById(R.id.numPicker2);
        NumPickers[2] = dialogView.findViewById(R.id.numPicker3);
        NumPickers[3] = dialogView.findViewById(R.id.numPicker4);
        for(int i = 0; i < 4; i+=2) {
            NumPickers[i].setTextSize(100);
            NumPickers[i].setMaxValue(12);
            NumPickers[i].setMinValue(0);
            NumPickers[i].setBackgroundColor(0xFF000000);
            NumPickers[i].setTextColor(0xFFFFFFFF);
        }
        for(int i = 1; i < 4; i+=2) {
            NumPickers[i].setTextSize(100);
            NumPickers[i].setMaxValue(59);
            NumPickers[i].setMinValue(0);
            NumPickers[i].setBackgroundColor(0xFF000000);
            NumPickers[i].setTextColor(0xFFFFFFFF);
        }

        BtnOK = dialogView.findViewById(R.id.btnOK);

        builder.setView(dialogView);
        Dialog = builder.create();
        BtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.show();
            }
        });

        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                EdtYear.setText(Integer.toString(cal.get(Calendar.YEAR)));
                EdtMonth.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                EdtDate.setText(Integer.toString(cal.get(Calendar.DATE)));
                EdtStartHour.setText(Integer.toString(NumPickers[0].getValue()));
                EdtStartMinute.setText(Integer.toString(NumPickers[1].getValue()));
                EdtEndHour.setText(Integer.toString(NumPickers[2].getValue()));
                EdtEndMinute.setText(Integer.toString(NumPickers[3].getValue()));
                EdtCategory.setText("data");
                Dialog.dismiss();
            }
        });

        BtnAdd.setOnClickListener(new View.OnClickListener() {
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

    public boolean checkEmpty() {
        try {
            Integer.parseInt(EdtYear.getText().toString());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
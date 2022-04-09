package com.cookandroid.routinepartner;

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

    AlertDialog dialog;
    NumberPicker[] numPickers = new NumberPicker[10];
    Button btnDialog, btnAdd, btnDeleteAll, btnOK, btnShowList;
    EditText edtYear, edtMonth, edtDate, edtCategory, edtStartHour, edtStartMinute, edtEndHour, edtEndMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        btnDialog = findViewById(R.id.btnDialog);
        btnAdd = findViewById(R.id.btnAdd);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnShowList = findViewById(R.id.btnShowList);


        edtYear = (EditText) findViewById(R.id.edtYear);
        edtMonth = (EditText) findViewById(R.id.edtMonth);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtCategory = (EditText) findViewById(R.id.edtCategory);
        edtStartHour = (EditText) findViewById(R.id.edtStartHour);
        edtStartMinute = (EditText) findViewById(R.id.edtStartMinute);
        edtEndHour = (EditText) findViewById(R.id.edtEndHour);
        edtEndMinute = (EditText) findViewById(R.id.edtEndMinute);


        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        numPickers[0] = dialogView.findViewById(R.id.numPicker1);
        numPickers[1] = dialogView.findViewById(R.id.numPicker2);
        numPickers[2] = dialogView.findViewById(R.id.numPicker3);
        numPickers[3] = dialogView.findViewById(R.id.numPicker4);
        for(int i = 0; i < 4; i+=2) {
            numPickers[i].setTextSize(100);
            numPickers[i].setMaxValue(12);
            numPickers[i].setMinValue(0);
            numPickers[i].setBackgroundColor(0xFF000000);
            numPickers[i].setTextColor(0xFFFFFFFF);
        }
        for(int i = 1; i < 4; i+=2) {
            numPickers[i].setTextSize(100);
            numPickers[i].setMaxValue(59);
            numPickers[i].setMinValue(0);
            numPickers[i].setBackgroundColor(0xFF000000);
            numPickers[i].setTextColor(0xFFFFFFFF);
        }

        btnOK = dialogView.findViewById(R.id.btnOK);

        builder.setView(dialogView);
        dialog = builder.create();
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                edtYear.setText(Integer.toString(cal.get(Calendar.YEAR)));
                edtMonth.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                edtDate.setText(Integer.toString(cal.get(Calendar.DATE)));
                edtStartHour.setText(Integer.toString(numPickers[0].getValue()));
                edtStartMinute.setText(Integer.toString(numPickers[1].getValue()));
                edtEndHour.setText(Integer.toString(numPickers[2].getValue()));
                edtEndMinute.setText(Integer.toString(numPickers[3].getValue()));
                edtCategory.setText("data");
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()) {
                    ActInfoDB.databaseWriteExecutor.execute(() -> {
                        ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                        ActInfoDao mActInfoDao = db.actInfoDao();
                        ActInfo actInfo = new ActInfo();
                        actInfo.setYear(Integer.parseInt(edtYear.getText().toString()));
                        actInfo.setMonth(Integer.parseInt(edtMonth.getText().toString()));
                        actInfo.setDate(Integer.parseInt(edtDate.getText().toString()));
                        actInfo.setCategory(edtCategory.getText().toString());
                        actInfo.setStartHour(Integer.parseInt(edtStartHour.getText().toString()));
                        actInfo.setStartMinute(Integer.parseInt(edtStartMinute.getText().toString()));
                        actInfo.setEndHour(Integer.parseInt(edtEndHour.getText().toString()));
                        actInfo.setEndMinute(Integer.parseInt(edtEndMinute.getText().toString()));
                        mActInfoDao.insert(actInfo);
                    });
                    Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fill Empty Please", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActInfoDB.databaseWriteExecutor.execute(() -> {
                    ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                    ActInfoDao mActInfoDao = db.actInfoDao();
                    mActInfoDao.deleteAll();
                });
                Toast.makeText(getApplicationContext(), "All Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });


    }



    public boolean checkEmpty() {
        try {
            Integer.parseInt(edtYear.getText().toString());
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
package com.astudio.routinepartner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.*;


// 정한 날짜의 ActInfo를 확인하고 수정하는 Activity
public class ActInfoListActivity extends AppCompatActivity {

    Button BtnGetList, BtnSdate, BtnEdate, Btndate;
    ActInfoRecyclerViewAdapter MAdapter;
    RecyclerView MRecyclerView;
    RecyclerView.LayoutManager MLayoutManager;
    ArrayList<ActInfo> ActInfoList;
    ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();
    int Syear, Smonth, Sdate, Eyear, Emonth, Edate;
    Calendar cal = Calendar.getInstance();

    SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_act_info_list);

        BtnGetList = findViewById(R.id.btnGetList);
        MLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        MRecyclerView = findViewById(R.id.actInfoRecyclerView);
        MAdapter = new ActInfoRecyclerViewAdapter(getApplicationContext());
        MRecyclerView.setAdapter(MAdapter);
        MRecyclerView.setLayoutManager(MLayoutManager);
        BtnSdate = findViewById(R.id.btnSdate);
        BtnEdate = findViewById(R.id.btnEdate);

        Syear = cal.get(Calendar.YEAR);
        Smonth = cal.get(Calendar.MONTH) + 1;
        Sdate = cal.get(Calendar.DATE);
        Eyear = cal.get(Calendar.YEAR);
        Emonth = cal.get(Calendar.MONTH) + 1;
        Edate = cal.get(Calendar.DATE);

        BtnSdate.setText(SDF.format(cal.getTime()));
        BtnEdate.setText(SDF.format(cal.getTime()));

        MAdapter.setActivityContext(getContext());
        BtnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownLatch CDL = new CountDownLatch(1);
                MAdapter.clearView();
                ActInfoItemList.clear();
                ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                    ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                    ActInfoDAO mActInfoDao = db.actInfoDao();
                    ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(Syear, Smonth, Sdate, Eyear, Emonth, Edate)));
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
                        if(o1.Year == o2.Year) {
                            if(o1.Month == o2.Month) {
                                if(o1.Date == o2.Date) {
                                    if(o1.StartHour == o2.StartHour) {
                                        if(o1.StartMinute == o2.StartMinute) return 0;
                                        return o1.StartMinute < o2.StartMinute ? -1 : 1;
                                    }
                                    return o1.StartHour < o2.StartHour ? -1 : 1;
                                }
                                return o1.Date < o2.Date ? -1 : 1;
                            }
                            return o1.Month < o2.Month ? -1 : 1;
                        }
                        return o1.Year < o2.Year ? -1 : 1;
                    }
                });
                for(int i = 0; i < ActInfoList.size(); i++) {
                    MAdapter.addItem(ActInfoItemList.get(i));
                }
                MAdapter.notifyDataSetChanged();
            }
        });

        DatePickerDialog.OnDateSetListener DatePickerDiag = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                updateDate(Btndate);
            }
        };
        BtnSdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btndate = BtnSdate;
                new DatePickerDialog(getContext(), DatePickerDiag, Syear, Smonth - 1, Sdate).show();
            }
        });
        BtnEdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Btndate = BtnEdate;
                new DatePickerDialog(getContext(), DatePickerDiag, Eyear, Emonth - 1, Edate).show();
            }
        });
    }
    void updateDate(Button Btndate){
        Btndate.setText(SDF.format(cal.getTime()));
        if(Btndate == BtnSdate) {
            Syear = cal.get(Calendar.YEAR);
            Smonth = cal.get(Calendar.MONTH) + 1;
            Sdate = cal.get(Calendar.DATE);
            Toast.makeText(getApplicationContext(), "Syear" + Syear + Smonth + Sdate, Toast.LENGTH_SHORT).show();
        } else {
            Eyear = cal.get(Calendar.YEAR);
            Emonth = cal.get(Calendar.MONTH) + 1;
            Edate = cal.get(Calendar.DATE);
            Toast.makeText(getApplicationContext(), "Eyear" + Eyear + Emonth + Edate, Toast.LENGTH_SHORT).show();
        }
    }
    Context getContext() {
        return this;
    }
}
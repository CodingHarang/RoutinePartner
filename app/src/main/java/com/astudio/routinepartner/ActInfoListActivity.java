package com.astudio.routinepartner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// 정한 날짜의 ActInfo를 확인하고 수정하는 Activity
public class ActInfoListActivity extends AppCompatActivity {

    Button BtnGetList;
    protected ActInfoRecyclerViewAdapter MAdapter;
    protected RecyclerView MRecyclerView;
    protected RecyclerView.LayoutManager MLayoutManager;
    protected ArrayList<ActInfo> ActInfoList;
    protected ArrayList<ActInfoItem> ActInfoItemList = new ArrayList<ActInfoItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_info_list);

        BtnGetList = findViewById(R.id.btnGetList);
        MLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        MRecyclerView = findViewById(R.id.actInfoRecyclerView);
        MAdapter = new ActInfoRecyclerViewAdapter(getApplicationContext());
        MRecyclerView.setAdapter(MAdapter);
        MRecyclerView.setLayoutManager(MLayoutManager);



        BtnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownLatch CDL = new CountDownLatch(1);
                MAdapter.clearView();
                // database에서 data를 불러오는 task는 main thread에서 할 수 없음
                // main thread 와 multi thread는 따로 움직여서 다른 일을 같이 처리하면 순서의 역전이 일어난다
                ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                    ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                    ActInfoDAO mActInfoDao = db.actInfoDao();
                    ActInfoList = new ArrayList<ActInfo>(Arrays.asList(mActInfoDao.getItemByDate(2022, 4, 28)));
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
                for(int i = 0; i < ActInfoList.size(); i++) {
                    MAdapter.addItem(ActInfoItemList.get(i));
                }
                // ui를 바꾸는 task는 무조건 main thread에서만 일어나야 한다
                MAdapter.notifyDataSetChanged();
            }
        });
    }
}